package com.game.chessmate.GameFiles.PlayingPieces;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.game.chessmate.GameFiles.ChessBoard;
import com.game.chessmate.GameFiles.Field;
import com.game.chessmate.GameFiles.GameState;
import com.game.chessmate.GameFiles.Networking.NetworkManager;
import com.game.chessmate.GameFiles.Vector;
import com.game.chessmate.OptionsActivity;
import com.game.chessmate.R;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * The type Chess piece.
 */
abstract public class ChessPiece extends View {

    /**
     * The Current position.
     *
     * @currentPosition current Field of this PlayingPiece
     * @targetPosition Target Field of this PlayingPiece when moving.
     * @sprite Bitmap of this PlayingPiece
     * @colour Color of this PlayingPiece
     * @offset Offset Vector when the PlayingPiece is moving from Field A to B
     * @updateMovementOffset true if the RenderThread should update the position of this PlayingPiece when moving
     * @movementSpeed movement Speed of the Playing Piece. The higher the slower.
     * @updateView true if this view should be invalidated in the future by the RenderThread.
     */
    protected Field currentPosition;
    private Field targetPosition;
    private Bitmap sprite;
    /**
     * The Colour.
     */
    protected ChessPieceColour colour;
    private Vector offset;
    private boolean updateMovementOffset;
    private int movementSpeed = 15;
    private boolean updateView;
    private boolean firstMove = true;
    private boolean isProtected = false;
    private boolean isCaptured = false;
    private ChessBoard board;
    protected boolean opponentEncountered = false;
    private boolean isChampion=false;
    private boolean isSwapped=false;
    private ChessPiece swapPiece=null;
    private MediaPlayer moveSound_start;
    private MediaPlayer moveSound_end;
    private Context context;
    protected ArrayList<Field> isChecking = new ArrayList<>();

    /**
     * Instantiates a new Chess piece.
     *
     * @param context  the context
     * @param position the position of this ChessPiece
     * @param sprite   the sprite of this ChessPiece
     * @param colour   the colour of this ChessPiece
     */
    protected ChessPiece(Context context, Field position, Bitmap sprite, ChessPieceColour colour) {
        super(context);
        this.context = context;
        this.currentPosition = position;
        this.targetPosition = null;
        this.colour = colour;
        this.offset = new Vector(0, 0);
        this.updateMovementOffset = false;
        this.updateView = false;
        this.sprite = sprite;
        this.moveSound_end = MediaPlayer.create(context,R.raw.chessmatemove_end);
        this.moveSound_start.setVolume(1.0f,1.0f);
        this.moveSound_end.setVolume(1.0f,1.0f);
    }

    /**
     * Instantiates a new Chess piece.
     *
     * @param context  the context
     * @param attrs    the attrs
     * @param position the position
     * @param sprite   the sprite
     * @param colour   the colour
     */
    protected ChessPiece(Context context, @Nullable AttributeSet attrs, Field position, Bitmap sprite, ChessPieceColour colour) {
        super(context, attrs);
        this.context = context;
        this.currentPosition = position;
        this.targetPosition = null;
        this.colour = colour;
        this.offset = new Vector(0, 0);
        this.updateMovementOffset = false;
        this.updateView = false;
        this.sprite = sprite;
        this.moveSound_end = MediaPlayer.create(context,R.raw.chessmatemove_end);
    }

    /**
     * Gets playing piece type.
     *
     * @return the playing piece type
     */
    abstract public ChessPieceType getPlayingPieceType();

    /**
     * Gets legal fields.
     *
     * @return the legal fields
     */
    abstract public ArrayList<Field> getLegalFields();


    /**
     * Draws the bitmap of this PlayingPiece to the canvas in the position of the containing rectangle.
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        Field field = this.currentPosition;
        if (isCaptured) {
            Paint paint = new Paint();
            paint.setColor(Color.TRANSPARENT);
            canvas.drawBitmap(this.sprite, field.getRectangle().left + (int) offset.getX(), field.getRectangle().top + (int) offset.getY(), paint);
        } else {
            canvas.drawBitmap(this.sprite, field.getRectangle().left + (int) offset.getX(), field.getRectangle().top + (int) offset.getY(), null);
        }
    }

    private void startPlayingMoveSound(){
        board = ChessBoard.getInstance();
        if (!board.isSoundOn()){
            return;
        }
        try {
            moveSound_start = MediaPlayer.create(this.context, R.raw.chessmatemove_start);
            moveSound_start.start();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Marks this PlayingPiece for the Render-thread to update and start moving to @targetField
     *
     * @param targetField the target field
     */
    public void move(Field targetField) {
        startPlayingMoveSound();

        Field [][] currentFields=ChessBoard.getInstance().getBoardFields();

        if (this.isChampion){
            this.getPosition().setRectangleDefaultColor();
            this.getPosition().invalidate();
        }

        if (targetField.hasPiece()&&targetField.getCurrentPiece().isProtected==false) {
            if (targetField.getCurrentPiece().getColour() != this.colour) {
                targetField.getCurrentPiece().capture();
            }
        }
        else if(targetField.hasPiece()&&targetField.getCurrentPiece().isProtected==true) {
            targetField.getCurrentPiece().setProtected(false);
        }

        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++){
                if (currentFields[i][j].isProtected()) {
                    currentFields[i][j].setRectangleDefaultColor();
                    currentFields[i][j].setProtected(false);
                    currentFields[i][j].getCurrentPiece().setProtected(false);
                    currentFields[i][j].invalidate();

                }
            }
        }

        this.targetPosition = targetField;
        this.updateMovementOffset = true;
        this.setUpdateView(true);


    }

    /**
     * Calculates the Vector between Field A and Field B and updates the position of the PlayingPiece
     * with @offset until it reaches its destination Field B.
     */
    public void updateOffsets() {
        Vector start = new Vector(currentPosition.getRectangle().left, currentPosition.getRectangle().top);
        Vector target = new Vector(targetPosition.getRectangle().left, targetPosition.getRectangle().top);
        Vector vector = target.sub(start);

        if ((offset.getX() != vector.getX()) || (offset.getY() != vector.getY())) {
            offset = offset.add(vector.div(this.movementSpeed));
            this.setUpdateView(true);
        }
        else if(!isSwapped){
            afterMove();
        }
    }

    /**
     * Cleanup work after move. Update Positions of chessPieces and update fields.
     */
    private void afterMove() {
        stopMoveSoundPlayEndSound();

        if (ChessBoard.getInstance().isCardActivated()){
            Log.i("GAMESTATE", "afterCardstart: " + ChessBoard.getInstance().getGameState());
            if (ChessBoard.getInstance().getGameState() == GameState.ACTIVE) {
                NetworkManager.sendCard(ChessBoard.getInstance().getCurrentCard().getId(),currentPosition, targetPosition);
            }
        }

        else {
            Log.i("GAMESTATE", "afterMovestart: " + ChessBoard.getInstance().getGameState());
            if (ChessBoard.getInstance().getGameState() == GameState.ACTIVE) {
                NetworkManager.sendMove(currentPosition, targetPosition);
            }
        }
        this.updateMovementOffset = false;
        this.offset = new Vector(0, 0);
        currentPosition.setCurrentPiece(null);
        this.currentPosition = targetPosition;
        targetPosition.setCurrentPiece(this);

        //swap-Move (card)
        swapPiece=null;
        isSwapped=false;

        if (this.isChampion()){
            targetPosition.markChampion();
            targetPosition.invalidate();
        }

        this.setUpdateView(true);

        if (ChessBoard.getInstance().getGameState() == GameState.WAITING) {
            ChessBoard.getInstance().setGameState(GameState.ACTIVE);
        }
        else if(ChessBoard.getInstance().getGameState() == GameState.ACTIVE) {
            ChessBoard.getInstance().setGameState(GameState.WAITING);
        }


        Log.i("GAMESTATE","afterMoveend: " + ChessBoard.getInstance().getGameState());

        if (ChessBoard.getInstance().isCardActivated()){
            Log.i("GAMESTATE", "afterCardend: " + ChessBoard.getInstance().getGameState());
            ChessBoard.getInstance().setCardActivated(false);
        }
        else {
            Log.i("GAMESTATE", "afterMoveend: " + ChessBoard.getInstance().getGameState());
        }
    }

    private void stopMoveSoundPlayEndSound(){
        board = ChessBoard.getInstance();
        if (!board.isSoundOn()){
            return;
        }
        moveSound_start.stop();
        moveSound_start.reset();
        moveSound_end.reset();
        moveSound_end = MediaPlayer.create(context,R.raw.chessmatemove_end);
        moveSound_end.start();
    }

    /**
     * Removes the drawing of this current piece from the canvas.
     */
    public void capture() {
        this.board = ChessBoard.getInstance();

        if (board.getLocalPlayer().getColor() == this.colour) {
            board.getLocalPlayer().addChessPiecesCaptured(this);
            board.getLocalPlayer().removeChessPiecesAlive(this);
        } else if (board.getEnemyPlayer().getColor() == this.colour) {
            board.getEnemyPlayer().addChessPiecesCaptured(this);
            board.getEnemyPlayer().removeChessPiecesAlive(this);
        }
        Log.d(TAG, "capture: localplayercaptured" + board.getLocalPlayer().getChessPiecesCaptured());
        Log.d(TAG, "capture: enemyplayercaptured" + board.getEnemyPlayer().getChessPiecesCaptured());

        this.isCaptured = true;
        this.setUpdateView(true);
    }

    // Fields the King can move during Cheat Function on
    public ArrayList<Field> cheatMoves() {
        Field[][] currentFields = ChessBoard.getInstance().getBoardFields();


        ArrayList<Field> cheatFields = new ArrayList<>();


        for (int fieldX = 0; fieldX < currentFields.length; fieldX++) {
            for (int fieldY = 0; fieldY < currentFields[fieldX].length; fieldY++) {
                if (!currentFields[fieldX][fieldY].hasPiece()) {
                    cheatFields.add(currentFields[fieldX][fieldY]);
                }

            }
        }
        Log.d("AMOUNT", String.valueOf(cheatFields.size()));
        return cheatFields;
    }

    public ArrayList<Field> getCheatFunctionMoves() {

        ArrayList<Field> legalMoves;
        ArrayList<Field> cheatMoves;
        legalMoves = getLegalFields();
        cheatMoves = cheatMoves();

        int size = cheatMoves.size();
        for (int i = 0; i < legalMoves.size(); i++) {
            if (!cheatMoves.contains(legalMoves)) {
                size++;
            }

        }
        ArrayList<Field> result = new ArrayList<>(size);

        for (int i = 0; i < cheatMoves.size(); i++) {
            result.add(cheatMoves.get(i));
        }
        for (int j = 0; j < legalMoves.size(); j++) {
            if (!result.contains(legalMoves.get(j))) {
                result.add(legalMoves.get(j));
            }
        }


        Log.d("ALLLL FIIIIELDS", String.valueOf(result.size()));
        // for (Field f: result){
        //   Log.d("Pawn cheat Moves",f.getChessCoordinates());}
        return result;
    }

    /**
     * Update movement offset boolean.
     *
     * @return the boolean
     */
    public boolean updateMovementOffset() {
        return this.updateMovementOffset;
    }

    /**
     * Gets update view.
     *
     * @return the update view
     */
    public boolean getUpdateView() {
        return this.updateView;
    }

    /**
     * Sets update view.
     *
     * @param update the update
     */
    public void setUpdateView(boolean update) {
        this.updateView = update;
    }

    /**
     * Set first move.
     *
     * @param value the value
     */
    public void setFirstMove(boolean value){
        this.firstMove = value;
    }

    /**
     * Get first move boolean.
     *
     * @return the boolean
     */
    public boolean getFirstMove(){
        return this.firstMove;
    }

    /**
     * Set position.
     *
     * @param field the field
     */
    public void setPosition(Field field){this.currentPosition=field;}

    /**
     * Set protected.
     *
     * @param isProtected the is protected
     */
    public void setProtected(boolean isProtected){this.isProtected=isProtected;}

    /**
     * Gets position.
     *
     * @return the position
     */
    public Field getPosition() {
        return this.currentPosition;
    }

    /**
     * Gets drawable.
     *
     * @return the drawable
     */
    public Bitmap getDrawable() {
        return this.sprite;
    }

    /**
     * Gets colour.
     *
     * @return the colour
     */
    public ChessPieceColour getColour() {
        return this.colour;
    }

    /**
     * Sets current position.
     *
     * @param field the field
     */
    public void setCurrentPosition(Field field) {
        this.currentPosition = field;
    }

    /**
     * Sets color.
     *
     * @param colour the colour
     */
    public void setColor(ChessPieceColour colour) {
        this.colour=colour;
    }

    public void setChampion(){
        if (this.getPlayingPieceType()==ChessPieceType.KNIGHT)
            isChampion=true;
    }

    public boolean isChampion(){return this.isChampion;}

    /**
     * Method determines whether king is checked, by calling isChecked()-method of king. isChecked()-method is overridden in king.
     *
     * @param boardFields current layout of fields and their pieces from chessboard
     * @return boolean whether king is in check
     */
    public boolean isChecked(Field[][] boardFields){
        ChessPiece localKing = ChessBoard.getInstance().getLocalKing();
        return localKing.isChecked(ChessBoard.getInstance().getBoardFields());
    }

    /**
     * Calculated legalFields that can be moved to when king is in check. Normal legalFields are taken.
     * Only fields that free the king out of check are copied into legalMovesInCheck Array.
     *
     * @return ArrayList of fields that can be moved to by piece (that frees king out of check), if king is in check
     */
    //king is in check - this legal moves is called instead -- same for every chesspiece
    public ArrayList<Field> getLegalMovesInCheck(){
        ArrayList<Field> legalFields = this.getLegalFields();
        Field[][] currentFields = ChessBoard.getInstance().getBoardFields();
        ChessPiece localKing = ChessBoard.getInstance().getLocalKing();
        ArrayList<Field> legalMovesInCheck = new ArrayList<Field>();
        localKing.isChecked(currentFields); //to set isChecking

        for (Field f : legalFields) {
            if (!wouldbeChecked(currentFields, f)) {//checks whether king would be in check if currentpieces position were field - same for king
                legalMovesInCheck.add(f);
            }
            if(localKing.getIsChecking().contains(f)){//if piece can kill threatening piece
                legalMovesInCheck.add(f);
            }
        }
        return legalMovesInCheck;
    }

    /**
     * Method determines whether king would still be checked, if piece were to move to Field f. Method is overridden in king.
     *
     * @param currentFields current layout of fields and their pieces from chessboard
     * @param f field that is theoretically moved to
     * @return boolean whether king would be in check if piece moved to field f
     */
    protected boolean wouldbeChecked(Field[][] currentFields, Field f){
        ChessPiece localKing = ChessBoard.getInstance().getLocalKing();
        Field realPosition = this.currentPosition;
        this.currentPosition = f; //what would happen if chesspiece had this field as position
        boolean result = localKing.isChecked(currentFields);//would king still be in check?
        this.currentPosition = realPosition; //resetting to real position
        return result;
    }


    public void setTargetPosition(Field position){this.targetPosition=position;}

    public void setUpdateMovementOffset(boolean Boolean){this.updateMovementOffset=Boolean;}

    public void resetOffset(){this.offset=new Vector(0,0);}

    public Field getTargetPosition(){return this.targetPosition;}

    public void setSwapPiece(ChessPiece piece){
        isSwapped=true;
        swapPiece=piece;
    }
    public ArrayList<Field> getIsChecking(){
        return this.isChecking;
    }
}
package com.game.chessmate.GameFiles.PlayingPieces;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.game.chessmate.GameFiles.ChessBoard;
import com.game.chessmate.GameFiles.Field;
import com.game.chessmate.GameFiles.Vector;

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
    private boolean isProtected=false;
    private boolean isCaptured = false;
    private ChessBoard board;
    protected boolean opponentEncountered = false;

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
        this.currentPosition = position;
        this.targetPosition = null;
        this.colour = colour;
        this.offset = new Vector(0,0);
        this.updateMovementOffset = false;
        this.updateView = false;
        this.sprite = sprite;
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
        this.currentPosition = position;
        this.targetPosition = null;
        this.colour = colour;
        this.offset = new Vector(0,0);
        this.updateMovementOffset = false;
        this.updateView = false;
        this.sprite = sprite;
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
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        Field field = this.currentPosition;
        if (isCaptured) {
            Paint paint = new Paint();
            paint.setColor(Color.TRANSPARENT);
            canvas.drawBitmap(this.sprite, field.getRectangle().left + (int)offset.getX(), field.getRectangle().top + (int)offset.getY(), paint);
        }
        else {
            canvas.drawBitmap(this.sprite, field.getRectangle().left + (int)offset.getX(), field.getRectangle().top + (int)offset.getY(), null);
        }
    }

    /**
     * Marks this PlayingPiece for the Render-thread to update and start moving to @targetField
     *
     * @param targetField the target field
     */
    public void move(Field targetField) {
        if (targetField.hasPiece()) {
            if (targetField.getCurrentPiece().getColour() != this.colour) {
                targetField.getCurrentPiece().capture();
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

        if((offset.getX() != vector.getX()) || (offset.getY() != vector.getY())){
            offset = offset.add(vector.div(this.movementSpeed));
            this.setUpdateView(true);
        }
        else {
            afterMove();
        }
    }

    /**
     * Cleanup work after move. Update Positions of chessPieces and update fields.
     */
    private void afterMove() {
        this.updateMovementOffset = false;
        this.offset = new Vector(0,0);
        currentPosition.setCurrentPiece(null);
        this.currentPosition = targetPosition;
        targetPosition.setCurrentPiece(this);

        this.setUpdateView(true);
    }

    /**
     * Removes the drawing of this current piece from the canvas.
     */
    public void capture() {
        this.board = ChessBoard.getInstance();

        if (board.getLocalPlayer().getColor() == this.colour) {
            board.getLocalPlayer().addChessPiecesCaptured(this);
            board.getLocalPlayer().removeChessPiecesAlive(this);
        }
        else if(board.getEnemyPlayer().getColor() == this.colour) {
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
            for (int j = 0; j < legalMoves.size(); j++) {
                if (!cheatMoves.contains(legalMoves.get(j))) {
                    result.add(legalMoves.get(j));
                }
            }
        }
        Log.d("foreach", "wir sind hier");
        for (Field f: result){
            Log.d("Pawn cheat Moves",f.getChessCoordinates());}
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


}

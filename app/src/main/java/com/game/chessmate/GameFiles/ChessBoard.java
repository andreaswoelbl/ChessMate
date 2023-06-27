package com.game.chessmate.GameFiles;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.game.chessmate.GameActivity;
import com.game.chessmate.GameFiles.PlayingPieces.Bishop;
import com.game.chessmate.GameFiles.PlayingPieces.ChessPiece;
import com.game.chessmate.GameFiles.PlayingPieces.ChessPieceColour;
import com.game.chessmate.GameFiles.PlayingPieces.ChessPieceType;
import com.game.chessmate.GameFiles.PlayingPieces.King;
import com.game.chessmate.GameFiles.PlayingPieces.Knight;
import com.game.chessmate.GameFiles.PlayingPieces.Pawn;
import com.game.chessmate.GameFiles.PlayingPieces.Queen;
import com.game.chessmate.GameFiles.PlayingPieces.Rook;

import java.util.ArrayList;

/**
 * The ChessBoard class handles creation and maintenance of the ChessBoard
 */
public class ChessBoard {
    // Thread-Save Singleton
    private static final class InstanceHolder {
        /**
         * The Instance.
         */
        static final ChessBoard INSTANCE = new ChessBoard();
    }

    /**
     * Get instance chess board.
     *
     * @return the chess board
     */
    public static ChessBoard getInstance(){ return ChessBoard.InstanceHolder.INSTANCE; }

    /**
     * @view the ViewGroup (BoardView) containing all its children (Fields)
     * @boardFields the 2D Field array resembling the chessboard.
     * @fieldSize calculated size of 1 Field, calculation is done with the screen metrics.
     * @boardSize size of the chessboard, n x n
     * @piecesPlayer1 the ChessPieces that belong to player1
     * @piecesPlayer2 the ChessPieces that belong to player2
     * @legalMoves contains the Fields that the selected ChessPiece is allowed to move to.
     */
    private BoardView view;
    private Field[][] boardFields;
    private int fieldSize;
    private final int boardSize = 8;
    private Player localPlayer;
    private Player enemyPlayer;
    private boolean isInverted;

    private ChessBoard() {
        this.boardFields = new Field[8][8];
        localPlayer = new Player(ChessPieceColour.WHITE);
        enemyPlayer = new Player(ChessPieceColour.BLACK);
        isInverted = localPlayer.getColor() == ChessPieceColour.WHITE ? false : true;
    }

    /**
     * Initializes the 2D Array of Fields, its ChessPieces and calculates the Field size(Rectangle size) with the width of the canvas
     * that is being drawn on.
     *
     * @param view  the canvas which contains the Chessboard
     * @param width the width
     */
    public void initChessBoard(BoardView view, int width){
        this.view = view;
        this.fieldSize = calculateRectSize(width);
        initFields();
        initPiecesLocalPlayer(localPlayer.getColor());
        initPiecesEnemyPlayer(enemyPlayer.getColor());
    }

    /**
     * Initializes the Field Views of the Chessboard and adds them to them ViewGroup (BoardView).
     * The currentChessPiece on the Fields will be null at the start.
     */
    private void initFields() {
        for (int i = 0; i < boardFields.length; i++) {
            for (int j = 0; j < boardFields[i].length; j++) {
                Field field = new Field(i, j, view.getContext(), null);
                boardFields[i][j] = field;
                view.addView(field);
                boardFields[i][j].setCurrentPiece(null);
            }
        }
    }

    /**
     * Initializes the pieces for player1. See initPieces for details on the creation.
     *
     */
    private void initPiecesLocalPlayer(ChessPieceColour color) {
        Bitmap pawn = localPlayer.getColor() == ChessPieceColour.WHITE ? ResourceLoader.getPawnWhite() : ResourceLoader.getPawnBlack();
        Bitmap rook = localPlayer.getColor() == ChessPieceColour.WHITE ? ResourceLoader.getRookWhite() : ResourceLoader.getRookBlack();
        Bitmap knight = localPlayer.getColor() == ChessPieceColour.WHITE ? ResourceLoader.getKnightWhite() : ResourceLoader.getKnightBlack();
        Bitmap bishop = localPlayer.getColor() == ChessPieceColour.WHITE ? ResourceLoader.getBishopWhite() : ResourceLoader.getBishopBlack();
        Bitmap queen = localPlayer.getColor() == ChessPieceColour.WHITE ? ResourceLoader.getQueenWhite() : ResourceLoader.getQueenBlack();
        Bitmap king = localPlayer.getColor() == ChessPieceColour.WHITE ? ResourceLoader.getKingWhite() : ResourceLoader.getKingBlack();

        initPieces(ChessPieceType.PAWN,  6, 0, 8, localPlayer.getChessPiecesAlive(), pawn, color);
        initPieces(ChessPieceType.ROOK,  7, 0, 1, localPlayer.getChessPiecesAlive(), rook,  color);
        initPieces(ChessPieceType.ROOK,  7, 7, 1, localPlayer.getChessPiecesAlive(), rook,  color);
        initPieces(ChessPieceType.KNIGHT,  7, 1, 1, localPlayer.getChessPiecesAlive(), knight, color);
        initPieces(ChessPieceType.KNIGHT,  7, 6, 1, localPlayer.getChessPiecesAlive(), knight, color);
        initPieces(ChessPieceType.BISHOP,  7, 2, 1, localPlayer.getChessPiecesAlive(), bishop, color);
        initPieces(ChessPieceType.BISHOP,  7, 5, 1, localPlayer.getChessPiecesAlive(), bishop, color);
        initPieces(ChessPieceType.QUEEN,  7, 4, 1, localPlayer.getChessPiecesAlive(), queen, color);
        initPieces(ChessPieceType.KING,  7, 3, 1, localPlayer.getChessPiecesAlive(), king, color);
    }

    /**
     * Initializes the pieces for player2. See initPieces for details on the creation.
     *
     */
    private void initPiecesEnemyPlayer(ChessPieceColour color) {
        Bitmap pawn = enemyPlayer.getColor() == ChessPieceColour.WHITE ? ResourceLoader.getPawnWhite() : ResourceLoader.getPawnBlack();
        Bitmap rook = enemyPlayer.getColor() == ChessPieceColour.WHITE ? ResourceLoader.getRookWhite() : ResourceLoader.getRookBlack();
        Bitmap knight = enemyPlayer.getColor() == ChessPieceColour.WHITE ? ResourceLoader.getKnightWhite() : ResourceLoader.getKnightBlack();
        Bitmap bishop = enemyPlayer.getColor() == ChessPieceColour.WHITE ? ResourceLoader.getBishopWhite() : ResourceLoader.getBishopBlack();
        Bitmap queen = enemyPlayer.getColor() == ChessPieceColour.WHITE ? ResourceLoader.getQueenWhite() : ResourceLoader.getQueenBlack();
        Bitmap king = enemyPlayer.getColor() == ChessPieceColour.WHITE ? ResourceLoader.getKingWhite() : ResourceLoader.getKingBlack();

        initPieces(ChessPieceType.PAWN,  1, 0, 8, enemyPlayer.getChessPiecesAlive(), pawn, color);
        initPieces(ChessPieceType.ROOK,  0, 0, 1, enemyPlayer.getChessPiecesAlive(), rook, color);
        initPieces(ChessPieceType.ROOK,  0, 7, 1, enemyPlayer.getChessPiecesAlive(), rook, color);
        initPieces(ChessPieceType.KNIGHT,  0, 1, 1, enemyPlayer.getChessPiecesAlive(), knight, color);
        initPieces(ChessPieceType.KNIGHT,  0, 6, 1, enemyPlayer.getChessPiecesAlive(), knight, color);
        initPieces(ChessPieceType.BISHOP,  0, 2, 1, enemyPlayer.getChessPiecesAlive(), bishop, color);
        initPieces(ChessPieceType.BISHOP,  0, 5, 1, enemyPlayer.getChessPiecesAlive(), bishop, color);
        initPieces(ChessPieceType.QUEEN,  0, 4, 1, enemyPlayer.getChessPiecesAlive(), queen, color);
        initPieces(ChessPieceType.KING,  0, 3, 1, enemyPlayer.getChessPiecesAlive(), king, color);
    }

    /**
     * Handles onTouchEvent fired by the BoardView (onTouchEvent) when the View is clicked on.
     * Cycles through the Chessboard fields and checks if the coordinates from the touchEvent match with
     * the coordinates from one of the Fields (Rectangles); it then translates the coordinates from the 2D Array
     * to chessboard coordinates and logs them.
     * Uses Rectangle that was clicked on to determine field and with that chess piece that was clicked on. Then calls Method
     * to determine legalMoves of ChessPiece and calls method to draw legalMoves.
     *
     * @param event the event with the x and y coordinates of the touch event.
     */

    private  Field startPossition;
    private  Field endPossition;
    private  ChessPiece movedPiece;
    private  boolean moveWasLegal = false;

    /**
     * Handles onTouchEvent fired by the BoardView (onTouchEvent) when the View is clicked on.
     * Cycles through the Chessboard fields and checks if the coordinates from the touchEvent match with
     * the coordinates from one of the Fields (Rectangles); it then translates the coordinates from the 2D Array
     * to chessboard coordinates and logs them.
     * Uses Rectangle that was clicked on to determine field and with that chess piece that was clicked on. Then calls Method
     * to determine legalMoves of ChessPiece and calls method to draw legalMoves.
     *
     * @param event the event with the x and y coordinates of the touch event.
     */
    public void handleFieldClick(MotionEvent event) {
        int touchX = (int)event.getX();
        int touchY = (int)event.getY();
        Rect rect;

        for (int i = 0; i < boardFields.length; i++) {
            for (int j = 0; j < boardFields[i].length; j++) {
                rect = boardFields[i][j].getRectangle();

                if (rect.contains(touchX, touchY)) {
                    Field clickedField = boardFields[i][j];

                    if(clickedField.getCurrentPiece() != null){
                        if(clickedField.getCurrentPiece().getColour() == localPlayer.getColor()){
                            localPlayer.setLastSelectedField(null);
                            resetLegalMoves();
                        }
                    }
                    if(localPlayer.getLastSelectedField() == null){ //this is the first click on a field
                        if (clickedField.getCurrentPiece() != null) {
                            localPlayer.setLastSelectedField(clickedField);
                            // position for CheatFunction
                            // Log.d("position1", lastSelectedField.toString());
                            startPossition = clickedField;
                            movedPiece = startPossition.getCurrentPiece();
                            localPlayer.setLegalMovesSelected(clickedField.getCurrentPiece().getLegalFields());
                            if(!localPlayer.getLegalMovesSelected().isEmpty()){
                                drawLegalMoves(localPlayer.getLegalMovesSelected());
                            }

                            if (GameActivity.cheatButtonStatus()) {
                                localPlayer.setLegalMovesSelected(clickedField.getCurrentPiece().getCheatFunctionMoves());
                            } else {
                                localPlayer.setLegalMovesSelected(clickedField.getCurrentPiece().getLegalFields());
                            }
                            if (!localPlayer.getLegalMovesSelected().isEmpty()) {
                                drawLegalMoves(localPlayer.getLegalMovesSelected());
                            }
                        }
                    }else{//this is the second click
                        if(localPlayer.getLegalMovesSelected().contains(clickedField)){

                            // postition for CheatFunction
                            endPossition = clickedField;
                            // Log.d("position2", clickedField.toString());

                            /*
                            if(clickedField.getCurrentPiece() != null){
                                if(clickedField.getCurrentPiece().getColour() != player1.getColor()){
                                    clickedField.getCurrentPiece().capture();
                                    Log.d("debug", "CAPTURE");
                                }
                            }

                             */
                            localPlayer.getLastSelectedField().getCurrentPiece().move(clickedField);
                            localPlayer.getLastSelectedField().getCurrentPiece().setFirstMove(false); //so that pawn has limited legal moves next time
                            localPlayer.setLastSelectedField(null);
                            resetLegalMoves();

                            if (GameActivity.cheatButtonStatus()) {
                                //TODO  Pawn first move of 2 Fields is still false
                                localPlayer.setLegalMovesForCheat(movedPiece.getLegalFields());
                                if ((localPlayer.getLegalMovesForCheat().contains(endPossition))) {
                                    moveWasLegal = true;
                                    Log.d("Move********TRUE", String.valueOf(moveWasLegal));
                                } else {moveWasLegal = false;
                                    Log.d("Move_______FALSE", String.valueOf(moveWasLegal));
                                }
                            }


                        }else{
                            localPlayer.setLastSelectedField(null);
                        }
                    }

                }
            }
        }
    }


    /**
     * Getwas move legal boolean.
     *
     * @return the boolean
     */
    public  boolean getwasMoveLegal(){
        return moveWasLegal;
    }

    /**
     * Gets start possition.
     *
     * @return the start possition
     */
    public  Field getStartPossition() {
        return startPossition;
    }

    /**
     * Gets end possition.
     *
     * @return the end possition
     */
    public  Field getEndPossition() {
        return endPossition;
    }

    private void resetLegalMoves() {
        for(Field f : localPlayer.getLegalMovesSelected()) {
            f.setRectangleDefaultColor();
            f.setAsIllegal();
            f.setUpdate(true);
        }
    }

    /**
     * Sets all fields in ArrayList fieldsToMove as legal to move to. Then calls redraw of view to mark the fields as legal.
     * When a new fieldsToMove Array is passed, the old Fields will be reset to their original color.
     * @param fieldsToMove ArrayList of Fields that are legal for the currently selected ChessPiece to move to
     */
    private void drawLegalMoves(ArrayList<Field> fieldsToMove) {
        resetLegalMoves();

        if(!fieldsToMove.isEmpty()){
            for(Field f : fieldsToMove){
                f.setAsLegal();
                f.invalidate();
            }
        }
    }

    /**
     * Calculates the size of a Rectangle in a Field width the help of the screen size metrics width
     * A Rectangle takes integer but the division of the width width delivers float, so the offset
     * must be considered.
     *
     * @param width The size of the screen
     * @return returns the size 1 Rectangle should have
     */
    public int calculateRectSize(int width) {
        float canvasWidth = width;
        float offset = canvasWidth % 8;
        int rectSize = (int)canvasWidth / this.boardSize - (int)offset;
        return rectSize;
    }

    /**
     *
     * @param type the type of ChessPiece to init. ex. ChessPieceType.Pawn.
     * @sprite the bitmap of this ChessPiece.
     * @param row the row in which the ChessPieces should be placed.
     * @param offset the startPoint in the 2D Array boardFields at which the placement of the ChessPieces should begin.
     *               the row param is the the row, the offset param is the column.
     * @param length specifies the amount of ChessPieces of the param @type that should be placed next to each other. (ex. pawns 1-8).
     * @param piecesPlayer the array that contains the players pieces.
     */
    private void initPieces(ChessPieceType type, int row, int offset, int length, ArrayList<ChessPiece> piecesPlayer, Bitmap sprite, ChessPieceColour colour) {
        for (int j = offset; j < offset + length; j++) {
            Field field = boardFields[row][j];
            ChessPiece piece = null;

            switch(type) {
                case PAWN: piece = new Pawn(field, sprite, view.getContext(), null, colour); break;
                case ROOK: piece = new Rook(field, sprite, view.getContext(), null, colour); break;
                case BISHOP: piece = new Bishop(field, sprite, view.getContext(), null, colour); break;
                case KNIGHT: piece = new Knight(field, sprite, view.getContext(), null, colour); break;
                case QUEEN: piece = new Queen(field, sprite, view.getContext(), null, colour); break;
                case KING: piece = new King(field, sprite, view.getContext(), null, colour); break;
                default: throw new UnsupportedOperationException();
            }
            piecesPlayer.add(piece);
            field.setCurrentPiece(piece);
            view.addView((View)piece);
        }
    }

    /**
     * Gets field size.
     *
     * @return the field size
     */
    public int getFieldSize() {
        return fieldSize;
    }

    /**
     * Get board fields field [ ] [ ].
     *
     * @return the field [ ] [ ]
     */
    public Field[][] getBoardFields() {
        return boardFields;
    }

    /**
     * Gets pieces player 1.
     *
     * @return the pieces player 1
     */
    public ArrayList<ChessPiece> getPiecesPlayer1() {
        return localPlayer.getChessPiecesAlive();
    }

    /**
     * Gets pieces player 2.
     *
     * @return the pieces player 2
     */
    public ArrayList<ChessPiece> getPiecesPlayer2() {
        return enemyPlayer.getChessPiecesAlive();
    }

    /**
     * Gets player 1.
     *
     * @return the player 1
     */
    public Player getLocalPlayer() {
        return localPlayer;
    }

    /**
     * Gets player 2.
     *
     * @return the player 2
     */
    public Player getEnemyPlayer() {
        return enemyPlayer;
    }
}

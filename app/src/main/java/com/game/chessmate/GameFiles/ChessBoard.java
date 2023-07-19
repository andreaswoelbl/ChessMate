package com.game.chessmate.GameFiles;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.game.chessmate.GameActivity;
import com.game.chessmate.GameFiles.Networking.NetworkManager;
import com.game.chessmate.GameFiles.PlayingPieces.Bishop;
import com.game.chessmate.GameFiles.PlayingPieces.ChessPiece;
import com.game.chessmate.GameFiles.PlayingPieces.ChessPieceColour;
import com.game.chessmate.GameFiles.PlayingPieces.ChessPieceType;
import com.game.chessmate.GameFiles.PlayingPieces.King;
import com.game.chessmate.GameFiles.PlayingPieces.Knight;
import com.game.chessmate.GameFiles.PlayingPieces.Pawn;
import com.game.chessmate.GameFiles.PlayingPieces.Queen;
import com.game.chessmate.GameFiles.PlayingPieces.Rook;
import com.game.chessmate.R;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

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
    public static ChessBoard getInstance() {
        return ChessBoard.InstanceHolder.INSTANCE;
    }

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
    private GameState gameState;

    private ChessPiece localKing;

    private Deck deck;
    private boolean isCardActivated=false;
    private Card currentCard;

    private ChessPieceColour localPlayerColor;
    private ChessPiece clickedPiece;
    private ChessPieceColour clickedPieceColor;
    private ChessPieceType clickedPieceType;


    private boolean soundOn;

    private ChessBoard() {
        this.boardFields = new Field[8][8];
        soundOn = true;
        deck = new Deck();
    }

    /**
     * Initializes the 2D Array of Fields, its ChessPieces and calculates the Field size(Rectangle size) with the width of the canvas
     * that is being drawn on.
     *
     * @param view  the canvas which contains the Chessboard
     * @param width the width
     */
    public void initChessBoard(BoardView view, int width) {
        this.view = view;
        this.fieldSize = calculateRectSize(width);
        initFields();
        localPlayer = new Player(NetworkManager.getInitialColor());
        enemyPlayer = new Player(NetworkManager.getInitialColor());
        if(NetworkManager.getInitialColor()==ChessPieceColour.WHITE){
            enemyPlayer = new Player(ChessPieceColour.BLACK);
        } else {
            enemyPlayer = new Player(ChessPieceColour.WHITE);
        }
        initPiecesLocalPlayer(localPlayer.getColor());
        initPiecesEnemyPlayer(enemyPlayer.getColor());

        localPlayer.setCards(deck.getInitialCards());
        enemyPlayer.setCards(deck.getInitialCards());
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

        initPieces(ChessPieceType.PAWN, 6, 0, 8, localPlayer.getChessPiecesAlive(), pawn, color);
        initPieces(ChessPieceType.ROOK, 7, 0, 1, localPlayer.getChessPiecesAlive(), rook, color);
        initPieces(ChessPieceType.ROOK, 7, 7, 1, localPlayer.getChessPiecesAlive(), rook, color);
        initPieces(ChessPieceType.KNIGHT, 7, 1, 1, localPlayer.getChessPiecesAlive(), knight, color);
        initPieces(ChessPieceType.KNIGHT, 7, 6, 1, localPlayer.getChessPiecesAlive(), knight, color);
        initPieces(ChessPieceType.BISHOP, 7, 2, 1, localPlayer.getChessPiecesAlive(), bishop, color);
        initPieces(ChessPieceType.BISHOP, 7, 5, 1, localPlayer.getChessPiecesAlive(), bishop, color);
        if (color == ChessPieceColour.WHITE) {
            initPieces(ChessPieceType.QUEEN, 7, 3, 1, localPlayer.getChessPiecesAlive(), queen, color);
            initPieces(ChessPieceType.KING, 7, 4, 1, localPlayer.getChessPiecesAlive(), king, color);
        } else {
            initPieces(ChessPieceType.QUEEN, 7, 4, 1, localPlayer.getChessPiecesAlive(), queen, color);
            initPieces(ChessPieceType.KING, 7, 3, 1, localPlayer.getChessPiecesAlive(), king, color);
        }

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

        initPieces(ChessPieceType.PAWN, 1, 0, 8, enemyPlayer.getChessPiecesAlive(), pawn, color);
        initPieces(ChessPieceType.ROOK, 0, 0, 1, enemyPlayer.getChessPiecesAlive(), rook, color);
        initPieces(ChessPieceType.ROOK, 0, 7, 1, enemyPlayer.getChessPiecesAlive(), rook, color);
        initPieces(ChessPieceType.KNIGHT, 0, 1, 1, enemyPlayer.getChessPiecesAlive(), knight, color);
        initPieces(ChessPieceType.KNIGHT, 0, 6, 1, enemyPlayer.getChessPiecesAlive(), knight, color);
        initPieces(ChessPieceType.BISHOP, 0, 2, 1, enemyPlayer.getChessPiecesAlive(), bishop, color);
        initPieces(ChessPieceType.BISHOP, 0, 5, 1, enemyPlayer.getChessPiecesAlive(), bishop, color);
        if (color == ChessPieceColour.WHITE) {
            initPieces(ChessPieceType.QUEEN, 0, 4, 1, enemyPlayer.getChessPiecesAlive(), queen, color);
            initPieces(ChessPieceType.KING, 0, 3, 1, enemyPlayer.getChessPiecesAlive(), king, color);
        } else {
            initPieces(ChessPieceType.QUEEN, 0, 3, 1, enemyPlayer.getChessPiecesAlive(), queen, color);
            initPieces(ChessPieceType.KING, 0, 4, 1, enemyPlayer.getChessPiecesAlive(), king, color);
        }

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

    private Field startPosition;
    private Field endPosition;
    private ChessPiece movedPiece;
    private boolean moveWasLegal=true;

    /**
     * Handles onTouchEvent fired by the BoardView (onTouchEvent) when the View is clicked on.
     * Cycles through the Chessboard fields and checks if the coordinates from the touchEvent match with
     * the coordinates from one of the Fields (Rectangles); it then translates the coordinates from the 2D Array
     * to chessboard coordinates and logs them.
     * Uses Rectangle that was clicked on to determine field and with that chess piece that was clicked on. Then calls Method
     * to determine legalMoves of ChessPiece and calls method to draw legalMoves. Later moves ChessPieces accordingly as well.
     *
     * @param event the event with the x and y coordinates of the touch event.
     */
    public void handleFieldClick(MotionEvent event) {
        Log.i(TAG, "handleFieldClick: " + gameState);
        if (gameState == GameState.WAITING) {
            return;
        }

        int touchX = (int)event.getX();
        int touchY = (int)event.getY();
        Rect rect;

        for (int i = 0; i < boardFields.length; i++) {
            for (int j = 0; j < boardFields[i].length; j++) {
                rect = boardFields[i][j].getRectangle();

                if (rect.contains(touchX, touchY)) {
                    Field clickedField = boardFields[i][j];

                    if (clickedField.getCurrentPiece() != null) {
                        if (clickedField.getCurrentPiece().getColour() == localPlayer.getColor()) {
                            localPlayer.setLastSelectedField(null);
                            resetLegalMoves();
                        }
                    }

                    //check which pieces are threatening and colour their fields
                    localKing = getLocalKing();
                    if(localKing.isChecked(boardFields)){
                        for(Field f : localKing.getIsChecking()){
                            f.setAsChecking();
                            f.invalidate();
                        }
                        if(checkMate()){
                            Log.d("debug", "I LOST!!!");
                            gameState = gameState.LOOSE;
                            //send gamestate win to enemy
                        }
                    }

                    if (localPlayer.getLastSelectedField() == null) { //this is the first click on a field
                        if (clickedField.getCurrentPiece() != null) {
                            if (clickedField.getCurrentPiece().getColour() == localPlayer.getColor()) { //only local player is allowed to move
                                localPlayer.setLastSelectedField(clickedField);
                                // position for CheatFunction
                                // Log.d("position1", lastSelectedField.toString());
                                startPosition = clickedField;
                                movedPiece = startPosition.getCurrentPiece();
                                if (GameActivity.cheatButtonStatus()) {
                                    localPlayer.setLegalMovesSelected(clickedField.getCurrentPiece().getCheatFunctionMoves());
                                } else {
                                    localPlayer.setLegalMovesSelected(clickedField.getCurrentPiece().getLegalFields());
                                }

                                //overwrite normal legal moves if king is in check
                                localKing = getLocalKing();
                                if(localKing.isChecked(boardFields)){
                                    localPlayer.setLegalMovesSelected(clickedField.getCurrentPiece().getLegalMovesInCheck());
                                }

                                if (!localPlayer.getLegalMovesSelected().isEmpty()) {
                                    drawLegalMoves(localPlayer.getLegalMovesSelected());
                                }
                            }
                        }
                    } else {//this is the second click
                        if (localPlayer.getLegalMovesSelected().contains(clickedField)) {

                            endPosition = clickedField;

                            if (GameActivity.cheatButtonStatus()) {
                                localPlayer.setLegalMovesForCheat(movedPiece.getLegalFields());
                                if ((localPlayer.getLegalMovesForCheat().contains(endPosition))) {
                                    moveWasLegal = true;
                                    localPlayer.setWasLeganMove(true);
                                    Log.d("Move********TRUE", String.valueOf(moveWasLegal));
                                } else {
                                    moveWasLegal = false;
                                    localPlayer.setWasLeganMove(false);
                                    Log.d("Move_______FALSE", String.valueOf(moveWasLegal));
                                }
                            }
                            localPlayer.getLastSelectedField().getCurrentPiece().move(clickedField);
                            localPlayer.getLastSelectedField().getCurrentPiece().setFirstMove(false); //so that pawn has limited legal moves next time
                            localPlayer.setLastSelectedField(null);
                            resetLegalMoves();
                            resetCheckedFields();
                            localKing = getLocalKing();
                        } else {
                            localPlayer.setLastSelectedField(null);
                        }

                        resetLegalMoves();
                    }
                    if(localKing.isChecked(boardFields)) {
                        for (Field f : localKing.getIsChecking()) {
                            f.setAsChecking();
                            f.invalidate();
                        }
                    }
                }
            }
        }
    }

    private void resetCheckedFields() {
        ChessPiece localKing = getLocalKing();
        for (Field f : localKing.getIsChecking()){
            f.setRectangleDefaultColor();
            f.setUpdate(true);
        }
    }

    private boolean checkMate() {
        boolean result = true;
        for(ChessPiece p : localPlayer.getChessPiecesAlive()){
            if(!p.getLegalMovesInCheck().isEmpty()){
                result = false; //no, not all legalMoves in check are empty
            }
        }
        return result;
    }


    //searches for local king and returns - could also be used for game over
    public ChessPiece getLocalKing() {
        for (int i = 0; i < boardFields.length; i++) {
            for (int j = 0; j < boardFields[i].length; j++) {
                if (boardFields[i][j].getCurrentPiece() != null) {
                    if (boardFields[i][j].getCurrentPiece().getPlayingPieceType() == ChessPieceType.KING && boardFields[i][j].getCurrentPiece().getColour() == localPlayer.getColor()) {
                        return boardFields[i][j].getCurrentPiece();
                    }

                }
            }
        }
        return null;
    }



    public void doCardAction(MotionEvent event, int id) {
        int touchX = (int) event.getX();
        int touchY = (int) event.getY();
        Rect rect;
        ArrayList<Field> legalMoves;

        Card[] cards = localPlayer.getCurrentCards();
        localPlayerColor = localPlayer.getColor();
        isCardActivated=true;
        currentCard=cards[id];


        for (int i = 0; i < boardFields.length; i++) {
            for (int j = 0; j < boardFields[i].length; j++) {
                rect = boardFields[i][j].getRectangle();

                if (rect.contains(touchX, touchY)) {
                    Field clickedField = boardFields[i][j];

                    //define variables if clickedField is not empty
                    if (clickedField.getCurrentPiece() != null) {
                        clickedPiece = clickedField.getCurrentPiece();
                        clickedPieceColor = clickedField.getCurrentPiece().getColour();
                        clickedPieceType = clickedField.getCurrentPiece().getPlayingPieceType();
                    }

                    //do action depending on card
                    switch (cards[id].getId()) {
                        case 0: //cowardice
                            if (localPlayer.getLastSelectedField() == null) { //first click
                                if (clickedPiece != null && clickedPieceType == ChessPieceType.PAWN && clickedPieceColor != localPlayerColor) {
                                    afterFirstClickAfterCardSelection(clickedField, cards[id].cowardice(1, clickedPiece, clickedField));
                                } else
                                    GameActivity.unselectAfterCardActivation();
                            }
                            else { //second click
                                if (localPlayer.getLegalMovesSelected().contains(clickedField)) {
                                    cards[id].cowardice(2, localPlayer.getLastSelectedField().getCurrentPiece(), clickedField);
                                    afterCardActivation(id);
                                } else {
                                    localPlayer.setLastSelectedField(null);
                                    resetLegalMoves();
                                    GameActivity.unselectAfterCardActivation();
                                }
                            }
                            break;

                        case 1: //darkMirror
                            if (localPlayer.getLastSelectedField() == null) { //first click
                                if (clickedPiece != null && clickedPieceType == ChessPieceType.PAWN && clickedPieceColor == localPlayerColor) {
                                    afterFirstClickAfterCardSelection(clickedField, cards[id].darkMirror(1, clickedPiece, clickedField));
                                } else
                                    GameActivity.unselectAfterCardActivation();
                            }
                            else { //second click
                                if (localPlayer.getLegalMovesSelected().contains(clickedField)) {
                                    cards[id].darkMirror(2, localPlayer.getLastSelectedField().getCurrentPiece(), clickedField);
                                    afterCardActivation(id);
                                } else {
                                    localPlayer.setLastSelectedField(null);
                                    resetLegalMoves();
                                    GameActivity.unselectAfterCardActivation();
                                }

                            }
                            break;

                        case 2: //deathDance
                            if (localPlayer.getLastSelectedField() == null) { //first click
                                if (clickedPiece != null && clickedPieceColor == localPlayerColor) {
                                    afterFirstClickAfterCardSelection(clickedField, cards[id].deathDance(1, clickedPiece, null));
                                } else
                                    GameActivity.unselectAfterCardActivation();
                            }
                            else { //second click
                                if (clickedPiece != null && clickedPieceColor != localPlayerColor) {
                                    cards[id].deathDance(2, localPlayer.getLastSelectedField().getCurrentPiece(), clickedPiece);
                                    afterCardActivation(id);
                                } else {
                                    localPlayer.setLastSelectedField(null);
                                    resetLegalMoves();
                                    GameActivity.unselectAfterCardActivation();
                                }
                            }
                            break;

                        case 3: //disintegration
                            if (clickedPiece != null && clickedPieceType == ChessPieceType.PAWN && clickedPieceColor == localPlayerColor) {
                                cards[id].disintegration(clickedPiece);
                                afterCardActivation(id);
                            }
                            else
                                GameActivity.unselectAfterCardActivation();
                            break;

                        case 4: //champion
                            if (clickedPiece != null && clickedPieceType == ChessPieceType.KNIGHT) {
                                cards[id].champion(clickedPiece);
                                afterCardActivation(id);
                            }
                            else
                                GameActivity.unselectAfterCardActivation();
                            break;

                        case 5: //rebirth
                            if (localPlayer.getLastSelectedField() == null) { //first click
                                if (clickedPiece != null && clickedPieceColor != localPlayerColor) {
                                    afterFirstClickAfterCardSelection(clickedField, cards[id].rebirth(1, clickedPiece, null));
                                } else
                                    GameActivity.unselectAfterCardActivation();
                            }
                            else {//second click
                                if (localPlayer.getLegalMovesSelected().contains(clickedField)) {
                                    cards[id].rebirth(2, localPlayer.getLastSelectedField().getCurrentPiece(), clickedField);
                                    afterCardActivation(id);
                                } else {
                                    localPlayer.setLastSelectedField(null);
                                    resetLegalMoves();
                                    GameActivity.unselectAfterCardActivation();
                                }
                            }
                            break;

                        case 6: //revelation
                            if (localPlayer.getLastSelectedField() == null) { //first click
                                if (clickedPiece != null && clickedPieceType == ChessPieceType.KNIGHT) {
                                    afterFirstClickAfterCardSelection(clickedField, cards[id].revelation(1, clickedPiece, null));
                                } else
                                    GameActivity.unselectAfterCardActivation();
                            }
                            else {//second click
                                if (localPlayer.getLegalMovesSelected().contains(clickedField)) {
                                    Field temp=localPlayer.getLastSelectedField();
                                    cards[id].revelation(2, localPlayer.getLastSelectedField().getCurrentPiece(), clickedPiece);
                                    clickedPiece.setCurrentPosition(temp);
                                    afterCardActivation(id);
                                } else {
                                    localPlayer.setLastSelectedField(null);
                                    resetLegalMoves();
                                    GameActivity.unselectAfterCardActivation();
                                }
                            }
                            break;

                        case 7: //longJump
                            if (localPlayer.getLastSelectedField() == null) { //first click
                                if (clickedPiece != null && clickedPieceType == ChessPieceType.KNIGHT && clickedPieceColor == localPlayerColor) {
                                    afterFirstClickAfterCardSelection(clickedField, cards[id].longJump(1, clickedPiece, null));
                                } else
                                    GameActivity.unselectAfterCardActivation();
                            }
                            else {//second click
                                if (localPlayer.getLegalMovesSelected().contains(clickedField)) {
                                    cards[id].longJump(2, localPlayer.getLastSelectedField().getCurrentPiece(), clickedField);
                                    afterCardActivation(id);
                                } else {
                                    localPlayer.setLastSelectedField(null);
                                    resetLegalMoves();
                                    GameActivity.unselectAfterCardActivation();
                                }
                            }
                            break;

                        case 8: //lostCastle
                            if (localPlayer.getLastSelectedField() == null) { //first click
                                if (clickedPiece != null && clickedPieceType == ChessPieceType.ROOK) {
                                    afterFirstClickAfterCardSelection(clickedField, cards[id].lostCastle(1, clickedPiece, null));
                                } else
                                    GameActivity.unselectAfterCardActivation();
                            }
                            else {//second click
                                if (localPlayer.getLegalMovesSelected().contains(clickedField)) {
                                    cards[id].lostCastle(2, localPlayer.getLastSelectedField().getCurrentPiece(), clickedPiece);
                                    afterCardActivation(id);
                                } else {
                                    localPlayer.setLastSelectedField(null);
                                    resetLegalMoves();
                                    GameActivity.unselectAfterCardActivation();
                                }
                            }
                            break;

                        case 9://mysticShield
                            if (clickedPiece != null && clickedPieceColor == localPlayerColor) {
                                cards[id].mysticShield(clickedField);
                                afterCardActivation(id);
                            }
                            else
                                GameActivity.unselectAfterCardActivation();
                            break;

                        case 10://forbiddenCity
                            if (!clickedField.hasPiece()) {
                                cards[id].forbiddenCity(clickedField);
                                afterCardActivation(id);
                            }
                            else
                                GameActivity.unselectAfterCardActivation();
                            break;

                        case 11://holyQuest
                            if (localPlayer.getLastSelectedField() == null) { //first click
                                if (clickedPiece != null && clickedPieceType == ChessPieceType.BISHOP&&clickedPieceColor==localPlayerColor) {
                                    afterFirstClickAfterCardSelection(clickedField, cards[id].holyQuest(1, clickedPiece, null));
                                } else
                                    GameActivity.unselectAfterCardActivation();
                            }
                            else {//second click
                                if (localPlayer.getLegalMovesSelected().contains(clickedField)) {
                                    cards[id].holyQuest(2, localPlayer.getLastSelectedField().getCurrentPiece(), clickedPiece);
                                    afterCardActivation(id);
                                } else {
                                    localPlayer.setLastSelectedField(null);
                                    resetLegalMoves();
                                    GameActivity.unselectAfterCardActivation();
                                }
                            }
                            break;

                        case 12://vulture
                            cards[id].vulture(id,localPlayer,deck);

                            localPlayer.setLastSelectedField(null);
                            resetLegalMoves();

                            break;

                        case 13://handOfFate
                            cards[id].handOfFate(localPlayer,enemyPlayer);
                            break;

                        case 14: //crusade
                            if (localPlayer.getLastSelectedField() == null) { //first click
                                if (clickedPiece != null && clickedPieceType == ChessPieceType.BISHOP && clickedPieceColor == localPlayerColor) {
                                    afterFirstClickAfterCardSelection(clickedField, clickedPiece.getLegalFields());
                                } else
                                    GameActivity.unselectAfterCardActivation();
                            }
                            else {//second click
                                if (localPlayer.getLegalMovesSelected().contains(clickedField)) {
                                    cards[id].crusade(localPlayer.getLastSelectedField().getCurrentPiece(), clickedField);
                                    afterCardActivation(id);
                                } else {
                                    localPlayer.setLastSelectedField(null);
                                    resetLegalMoves();
                                    GameActivity.unselectAfterCardActivation();
                                }
                            }
                            break;
                    }
                }
            }
            clickedPiece = null;
            clickedPieceColor = null;
            clickedPieceType = null;
        }
    }

    public void afterFirstClickAfterCardSelection(Field clickedField, ArrayList<Field> legalMoves) {
        localPlayer.setLastSelectedField(clickedField);
        localPlayer.setLegalMovesSelected(legalMoves);

        if (!legalMoves.isEmpty()) {
            drawLegalMoves(legalMoves);
        }
    }

    public void afterCardActivation(int id) {
        localPlayer.setLastSelectedField(null);
        resetLegalMoves();

        deck.setLastCardPlayed(localPlayer.getCurrentCards()[id]);
        localPlayer.getCurrentCards()[id].setOwned(false); //set card free
        localPlayer.getCurrentCards()[id] = deck.drawCard(); //replace card
        GameActivity.unselectAfterCardActivation(); //mark card "unselected"
    }

    //receive Card from Network
    public void receiveCardAction(int cardId, Field field1, Field field2){
        deck.setLastCardPlayed(deck.getSpecificCard(cardId));
        switch (cardId){
            case 0: //Cawardice
                field1.getCurrentPiece().move(field2);
                break;

            case 1: //DarkMirror
                field1.getCurrentPiece().move(field2);
                break;

            case 2: //DeathDance
                swap(field1.getCurrentPiece(),field2.getCurrentPiece());
                break;

            case 3: //Disintegration
                field1.getCurrentPiece().capture();
                break;

            case 4: //Champion
                field1.getCurrentPiece().setChampion();
                field1.getCurrentPiece().getPosition().markChampion();
                field1.getCurrentPiece().getPosition().invalidate();
                break;

            case 5: //Rebirth
                field1.getCurrentPiece().move(field2);
                break;

            case 6: //Revelation
                swap(field1.getCurrentPiece(),field2.getCurrentPiece());
                break;

            case 7: //LongJump
                field1.getCurrentPiece().move(field2);
                break;

            case 8: //LostCastle
                swap(field1.getCurrentPiece(),field2.getCurrentPiece());
                break;

            case 9: //MysticShield
                field1.getCurrentPiece().setProtected(true);
                field1.setPlayingPieceShield();
                field1.invalidate();
                break;

            case 10: //ForbiddenCity
                field1.setBlocked();
                field1.invalidate();
                break;

            case 11: //HolyQuest
                swap(field1.getCurrentPiece(),field2.getCurrentPiece());
                break;

            case 12: //Vulture - nothing to do
                break;

            case 13: //HandOfFate
                break;


        }
    }

    /**
     * Getwas move legal boolean.
     *
     * @return the boolean
     */
    public boolean getwasMoveLegal() {
        return moveWasLegal;
    }

    /**
     * Gets start possition.
     *
     * @return the start possition
     */
    public Field getStartPossition() {
        return startPosition;
    }

    /**
     * Gets end possition.
     *
     * @return the end possition
     */
    public Field getEndPossition() {
        return endPosition;
    }

    public void resetLegalMoves() {
        for (Field f : localPlayer.getLegalMovesSelected()) {
            f.setRectangleDefaultColor();
            f.setAsIllegal();
            f.setUpdate(true);
        }
    }

    /**
     * Sets all fields in ArrayList fieldsToMove as legal to move to. Then calls redraw of view to mark the fields as legal.
     * When a new fieldsToMove Array is passed, the old Fields will be reset to their original color.
     *
     * @param fieldsToMove ArrayList of Fields that are legal for the currently selected ChessPiece to move to
     */
    private void drawLegalMoves(ArrayList<Field> fieldsToMove) {
        resetLegalMoves();

        if (!fieldsToMove.isEmpty()) {
            for (Field f : fieldsToMove) {
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
        int rectSize = (int) canvasWidth / this.boardSize - (int) offset;
        return rectSize;
    }

    /**
     * @param type         the type of ChessPiece to init. ex. ChessPieceType.Pawn.
     * @param row          the row in which the ChessPieces should be placed.
     * @param offset       the startPoint in the 2D Array boardFields at which the placement of the ChessPieces should begin.
     *                     the row param is the the row, the offset param is the column.
     * @param length       specifies the amount of ChessPieces of the param @type that should be placed next to each other. (ex. pawns 1-8).
     * @param piecesPlayer the array that contains the players pieces.
     * @sprite the bitmap of this ChessPiece.
     */
    private void initPieces(ChessPieceType type, int row, int offset, int length, ArrayList<ChessPiece> piecesPlayer, Bitmap sprite, ChessPieceColour colour) {
        for (int j = offset; j < offset + length; j++) {
            Field field = boardFields[row][j];
            ChessPiece piece = null;

            switch (type) {
                case PAWN:
                    piece = new Pawn(field, sprite, view.getContext(), null, colour);
                    break;
                case ROOK:
                    piece = new Rook(field, sprite, view.getContext(), null, colour);
                    break;
                case BISHOP:
                    piece = new Bishop(field, sprite, view.getContext(), null, colour);
                    break;
                case KNIGHT:
                    piece = new Knight(field, sprite, view.getContext(), null, colour);
                    break;
                case QUEEN:
                    piece = new Queen(field, sprite, view.getContext(), null, colour);
                    break;
                case KING:
                    piece = new King(field, sprite, view.getContext(), null, colour);
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
            piecesPlayer.add(piece);
            field.setCurrentPiece(piece);
            view.addView((View) piece);
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


    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        if (view != null) {
            GameActivity a = (GameActivity) view.getContext();
            a.setGameStateView(gameState);
        }
        this.gameState = gameState;
    }

    public Deck getDeck() {
        return this.deck;
    }

    public Card[] getCardsPlayer(){
        return localPlayer.getCurrentCards();
    }

    public boolean isCardActivated(){return this.isCardActivated;}

    public void setCardActivated(boolean cardActivated) {
        isCardActivated = cardActivated;
    }

    public void swap(ChessPiece playingPiece1, ChessPiece playingPiece2){
        playingPiece1.setProtected(true);
        playingPiece2.setProtected(true);

        Field field1=playingPiece1.getPosition();
        Field field2=playingPiece2.getPosition();

        playingPiece1.setSwapPiece(playingPiece2);
        playingPiece2.setSwapPiece(playingPiece1);

        playingPiece1.move(playingPiece2.getPosition());
        playingPiece2.move(playingPiece1.getPosition());

        field1.setCurrentPiece(playingPiece2);
        playingPiece2.setCurrentPosition(field1);

        field2.setCurrentPiece(playingPiece1);
        playingPiece1.setCurrentPosition(field2);
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public boolean isSoundOn() {
        return soundOn;
    }

    public void setSoundOn(boolean soundOn) {
        this.soundOn = soundOn;
    }

    public ChessPiece getMovedPiece() {
        return movedPiece;
    }

    public void setMovedPiece(ChessPiece movedPiece) {
        this.movedPiece = movedPiece;
    }
}
package com.game.chessmate.GameFiles;

import com.game.chessmate.GameFiles.PlayingPieces.ChessPiece;
import com.game.chessmate.GameFiles.PlayingPieces.ChessPieceColour;

import java.util.ArrayList;

/**
 * The type Player.
 */
public class Player {

    private ArrayList<ChessPiece> chessPiecesAlive;
    private ArrayList<ChessPiece> chessPiecesCaptured;
    private ArrayList<Field> legalMovesForCheat;
    private ArrayList<Field> legalMovesSelected;
    private Field lastSelectedField;
    private ChessPieceColour color;

    /**
     * Instantiates a new Player.
     *
     * @param color the color of the Player's ChessPieces
     */
    public Player(ChessPieceColour color) {
        this.chessPiecesAlive = new ArrayList<>();
        this.chessPiecesCaptured = new ArrayList<>();
        this.legalMovesSelected = new ArrayList<>();
        this.lastSelectedField = null;
        this.color = color;
    }

    /**
     * Add chess pieces alive.
     *
     * @param piece the piece
     */
    public void addChessPiecesAlive(ChessPiece piece) {
        this.chessPiecesAlive.add(piece);
    }

    /**
     * Remove chess pieces alive.
     *
     * @param piece the piece
     */
    public void removeChessPiecesAlive(ChessPiece piece) {
        this.chessPiecesAlive.remove(piece);
    }

    /**
     * Add chess pieces captured.
     *
     * @param piece the piece
     */
    public void addChessPiecesCaptured(ChessPiece piece) {
        this.chessPiecesCaptured.add(piece);
    }

    /**
     * Remove chess pieces captured.
     *
     * @param piece the piece
     */
    public void removeChessPiecesCaptured(ChessPiece piece) {
        this.chessPiecesCaptured.remove(piece);
    }

    /**
     * Gets chess pieces alive.
     *
     * @return the chess pieces alive
     */
    public ArrayList<ChessPiece> getChessPiecesAlive() {
        return chessPiecesAlive;
    }

    /**
     * Gets chess pieces captured.
     *
     * @return the chess pieces captured
     */
    public ArrayList<ChessPiece> getChessPiecesCaptured() {
        return chessPiecesCaptured;
    }

    /**
     * Gets legal moves selected.
     *
     * @return the legal moves selected
     */
    public ArrayList<Field> getLegalMovesSelected() {
        return legalMovesSelected;
    }

    /**
     * Gets last selected field.
     *
     * @return the last selected field
     */
    public Field getLastSelectedField() {
        return lastSelectedField;
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    public ChessPieceColour getColor() {
        return color;
    }

    /**
     * Sets legal moves selected.
     *
     * @param legalMovesSelected the legal moves selected
     */
    public void setLegalMovesSelected(ArrayList<Field> legalMovesSelected) {
        this.legalMovesSelected = legalMovesSelected;
    }

    /**
     * Sets last selected field.
     *
     * @param lastSelectedField the last selected field
     */
    public void setLastSelectedField(Field lastSelectedField) {
        this.lastSelectedField = lastSelectedField;
    }

    public ArrayList<Field> getLegalMovesForCheat() {
        return legalMovesForCheat;
    }

    public void setLegalMovesForCheat(ArrayList<Field> legalMovesForCheat) {
        this.legalMovesForCheat = legalMovesForCheat;
    }
}

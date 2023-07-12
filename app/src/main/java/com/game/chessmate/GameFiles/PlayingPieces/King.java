package com.game.chessmate.GameFiles.PlayingPieces;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.game.chessmate.GameFiles.ChessBoard;
import com.game.chessmate.GameFiles.Field;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * class implementing the King playing piece
 */
public class King extends ChessPiece {

    /**
     * Instantiates a new King.
     *
     * @param position the position
     */
    public King(Field position, Bitmap sprite, Context context, @Nullable AttributeSet attrs, ChessPieceColour color) {
        super(context, attrs, position, sprite, color);
    }

    /**
     * Scales the bitmap of this PlayingPiece to the size of the rectangle container.
     */
    public ChessPieceType getPlayingPieceType() {
        return ChessPieceType.KING;
    }

    /**
     * Method determines all legal fields, that type of chess piece is allowed to move to and returns them as an ArrayList.
     *
     * @return ArrayList of fields that are legal for the chess Piece to move to.
     */
    public ArrayList<Field> getLegalFields() {
        Field[][] currentFields = ChessBoard.getInstance().getBoardFields();
        ArrayList<Field> legalFields = new ArrayList<>();

        for (int i = currentPosition.getFieldX() - 1; i <= currentPosition.getFieldX() + 1; i++) {
            for (int j = currentPosition.getFieldY() - 1; j <= currentPosition.getFieldY() + 1; j++) {
                if (i >= 0 && i <= 7 && j >= 0 && j <= 7) {
                    if (!(i == currentPosition.getFieldX() && j == currentPosition.getFieldY())) {
                        if (currentFields[i][j].getCurrentPiece() == null) {
                            if(!wouldbeChecked(currentFields, currentFields[i][j])){//piece can only move to legal field if it does not cause the king to be in check
                                legalFields.add(currentFields[i][j]);
                            }
                        } else if (currentFields[i][j].getCurrentPiece().getColour() != this.colour) {
                            if(!wouldbeChecked(currentFields, currentFields[i][j])){//piece can only move to legal field if it does not cause the king to be in check
                                legalFields.add(currentFields[i][j]);
                            }
                        }
                    }
                }
            }
        }
        return legalFields;
    }

    //checks whether king / this piece would still be in check
    //then also sets isChecking
    public boolean isChecked(Field[][] fields) {
        boolean result = false;
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields.length; j++) {
                if (fields[i][j].getCurrentPiece() != null) {//must be in seperate if - else possible null pointer exception
                    if (fields[i][j].getCurrentPiece().getColour() != this.getColour()) {
                        for (Field f : fields[i][j].getCurrentPiece().getLegalFields()) {
                            if (f.equals(currentPosition)) {
                                isChecking.add(f);
                                result = true;
                            }
                        }
                    }
                }
            }
        }
        return result;
    }


    //checks whether king / this piece would still be in check if he moved to this field - if yes this field can not be moved to !!
    @Override
    public boolean wouldbeChecked(Field [][] currentFields, Field f) {
        Field realPosition = currentPosition;
        currentPosition = f; //what would happen if king had this field as position
        boolean result = isChecked(currentFields); //would this piece / king still be in check?
        currentPosition = realPosition; //resetting to real position
        return result;
    }
}

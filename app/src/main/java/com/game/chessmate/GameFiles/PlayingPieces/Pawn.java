package com.game.chessmate.GameFiles.PlayingPieces;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.game.chessmate.GameFiles.ChessBoard;
import com.game.chessmate.GameFiles.Field;

import java.util.ArrayList;

/** class implementing the Pawn playing piece */
public class Pawn extends ChessPiece {

    /**
     * Instantiates a new Pawn.
     *
     * @param position     the position
     */
    public Pawn(Field position, Bitmap sprite, Context context, @Nullable AttributeSet attrs, ChessPieceColour color){
        super(context, attrs, position, sprite, color);
    }

    public ChessPieceType getPlayingPieceType() {
        return ChessPieceType.PAWN;
    }

    public ArrayList<Field> getLegalFields() {
        Field[][] currentFields = ChessBoard.getInstance().getBoardFields();
        ArrayList<Field> legalFields = new ArrayList<>();
        int i = currentPosition.getFieldX();
        int j = currentPosition.getFieldY();

        if(i-1 >= 0) {
            if (currentFields[i - 1][j].getCurrentPiece() == null) {
                legalFields.add(currentFields[i - 1][j]);
            } else if (currentFields[i - 1][j].getCurrentPiece().getColour() != this.colour) {
                legalFields.add(currentFields[i - 1][j]);
                opponentEncountered = true;
            } else {
                return legalFields;
            }
            if (this.getFirstMove() && i - 2 < 8 && !opponentEncountered) {
                if (currentFields[i - 2][j].getCurrentPiece() == null) {
                    legalFields.add(currentFields[i - 2][j]);
                } else if (currentFields[i - 2][j].getCurrentPiece().getColour() != this.colour) {
                    legalFields.add(currentFields[i - 2][j]);
                } else {
                    return legalFields;
                }
            }
        }
        return legalFields;
    }



}

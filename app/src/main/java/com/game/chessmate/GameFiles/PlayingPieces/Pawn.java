package com.game.chessmate.GameFiles.PlayingPieces;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;

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

    /**
     * Method determines all legal fields, that type of chess piece is allowed to move to and returns them as an ArrayList.
     * @return ArrayList of fields that are legal for the chess Piece to move to.
     */
    public ArrayList<Field> getLegalFields() {
        Field[][] currentFields = ChessBoard.getInstance().getBoardFields();
        ArrayList<Field> legalFields = new ArrayList<>();
        int i = currentPosition.getFieldX();
        int j = currentPosition.getFieldY();

        //moving to empty fields is allowed
        if(i-1 >= 0) {
            if (currentFields[i - 1][j].getCurrentPiece() == null) {
                if(!currentFields[i-1][j].isBlocked())
                    if(!wouldbeChecked(currentFields, currentFields[i-1][j])){//piece can only move to legal field if it does not cause the king to be in check
                        legalFields.add(currentFields[i-1][j]);
                    }
            } else if (currentFields[i - 1][j].getCurrentPiece().getColour() != this.colour) {
                opponentEncountered = true;
            } else {
                return legalFields;
            }
            if (this.getFirstMove() && i - 2 > 0 && !opponentEncountered) {
                if (currentFields[i - 2][j].getCurrentPiece() == null) {
                    if(!currentFields[i-2][j].isBlocked())
                        if(!wouldbeChecked(currentFields, currentFields[i-2][j])){//piece can only move to legal field if it does not cause the king to be in check
                            legalFields.add(currentFields[i-2][j]);
                        }
                } else if (currentFields[i - 2][j].getCurrentPiece().getColour() != this.colour) {
                    if(!wouldbeChecked(currentFields, currentFields[i-2][j])){//piece can only move to legal field if it does not cause the king to be in check
                        legalFields.add(currentFields[i-2][j]);
                    }
                } else {
                    return legalFields;
                }
            }
        }

        //attacking fields is allowed diagonally
        if(i-1>=0 && j-1>=0){
            if (currentFields[i-1][j-1].getCurrentPiece() != null) {
                if(currentFields[i-1][j-1].getCurrentPiece().getColour() != this.colour&&!currentFields[i-1][j-1].isProtected()){
                    if(!wouldbeChecked(currentFields, currentFields[i-1][j-1])){//piece can only move to legal field if it does not cause the king to be in check
                        legalFields.add(currentFields[i-1][j-1]);
                    }
                }
            }
        }
        if(i-1>=0 && j+1<8){
            if (currentFields[i-1][j+1].getCurrentPiece() != null) {
                if(currentFields[i-1][j+1].getCurrentPiece().getColour() != this.colour&&!currentFields[i-1][j+1].isProtected()){
                    if(!wouldbeChecked(currentFields, currentFields[i-1][j+1])){//piece can only move to legal field if it does not cause the king to be in check
                        legalFields.add(currentFields[i-1][j+1]);
                    }
                }
            }
        }
        return legalFields;
    }



}
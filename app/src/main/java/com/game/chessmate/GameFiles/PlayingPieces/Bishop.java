package com.game.chessmate.GameFiles.PlayingPieces;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.game.chessmate.GameFiles.ChessBoard;
import com.game.chessmate.GameFiles.Field;

import java.util.ArrayList;

/** class implementing the Bishop playing piece */
public class Bishop extends ChessPiece {

    /**
     * Instantiates a new Bishop.
     *
     * @param position     the position
     */
    public Bishop(Field position, Bitmap sprite, Context context, @Nullable AttributeSet attrs, ChessPieceColour color){
        super(context, attrs, position, sprite, color);

    }

    public ChessPieceType getPlayingPieceType() {
        return ChessPieceType.BISHOP;
    }

    /**
     * Method determines all legal fields, that type of chess piece is allowed to move to and returns them as an ArrayList.
     * @return ArrayList of fields that are legal for the chess Piece to move to.
     */
    public ArrayList<Field> getLegalFields() {
        Field[][] currentFields = ChessBoard.getInstance().getBoardFields();
        ArrayList<Field> legalFields = new ArrayList<>();

        int i;
        int j;
        for(int loops = 0; loops <4; loops++){
            i = currentPosition.getFieldX();
            j = currentPosition.getFieldY();
            opponentEncountered = false;

            while(i<8 && i>=0 && j<8 && j>=0){
                if(!(i == currentPosition.getFieldX() && j == currentPosition.getFieldY()) && !opponentEncountered){
                    if (currentFields[i][j].getCurrentPiece() == null) {
                        legalFields.add(currentFields[i][j]);
                    } else if (currentFields[i][j].getCurrentPiece().getColour() != this.colour) {
                        legalFields.add(currentFields[i][j]);
                        opponentEncountered = true;
                    }else{
                        break; //breaks out of while so next loop is started
                    }
                }
                switch(loops){
                    case 0:
                        i++;
                        j++;
                        break;
                    case 1:
                        i--;
                        j--;
                        break;
                    case 2:
                        i++;
                        j--;
                        break;
                    case 3:
                        i--;
                        j++;
                        break;
                }
            }
        }
        return legalFields;
    }


}


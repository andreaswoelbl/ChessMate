package com.game.chessmate.GameFiles.PlayingPieces;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.game.chessmate.GameFiles.ChessBoard;
import com.game.chessmate.GameFiles.Field;

import java.util.ArrayList;

/** class implementing the Queen playing piece */
public class Queen extends ChessPiece {

    /**
     * Instantiates a new Queen.
     *
     * @param position     the position
     */
    public Queen(Field position, Bitmap sprite, Context context, @Nullable AttributeSet attrs, ChessPieceColour color){
        super(context, attrs, position, sprite, color);
    }

    public ChessPieceType getPlayingPieceType() {
        return ChessPieceType.QUEEN;
    }

    public ArrayList<Field> getLegalFields() {
        Field[][] currentFields = ChessBoard.getInstance().getBoardFields();
        ArrayList<Field> legalFields = new ArrayList<>();

        for(int loops = 0; loops <8; loops++){
            int i = currentPosition.getFieldX();
            int j = currentPosition.getFieldY();
            opponentEncountered = false;
            while(i<8 && i>=0 && j<8 && j>=0){
                if(!(i == currentPosition.getFieldX() && j == currentPosition.getFieldY()) && !opponentEncountered){
                    if (currentFields[i][j].getCurrentPiece() == null) {
                        legalFields.add(currentFields[i][j]);
                    } else if (currentFields[i][j].getCurrentPiece().getColour() != this.colour) {
                        legalFields.add(currentFields[i][j]);
                        opponentEncountered = true;
                    }else{
                        break;
                    }
                }
                switch(loops){
                    case 0:
                        i++;
                        break;
                    case 1:
                        i--;
                        break;
                    case 2:
                        j--;
                        break;
                    case 3:
                        j++;
                        break;
                    case 4:
                        i--;
                        j--;
                        break;
                    case 5:
                        i++;
                        j++;
                        break;
                    case 6:
                        i--;
                        j++;
                        break;
                    case 7:
                        i++;
                        j--;
                        break;

                }
            }
        }
        return legalFields;
    }




}

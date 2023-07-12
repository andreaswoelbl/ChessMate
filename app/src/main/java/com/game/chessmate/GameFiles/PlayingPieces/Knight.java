package com.game.chessmate.GameFiles.PlayingPieces;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.game.chessmate.GameFiles.ChessBoard;
import com.game.chessmate.GameFiles.Field;

import java.util.ArrayList;

/** class implementing the Knight playing piece */
public class Knight extends ChessPiece {

    boolean isBlocked;
    /**
     * Instantiates a new Knight.
     *
     * @param position     the position
     */

    public Knight(Field position, Bitmap sprite, Context context, @Nullable AttributeSet attrs, ChessPieceColour color){
        super(context, attrs, position, sprite, color);
    }

    public ChessPieceType getPlayingPieceType() {
        return ChessPieceType.KNIGHT;
    }


    /**
     * Method determines all legal fields, that type of chess piece is allowed to move to and returns them as an ArrayList.
     * @return ArrayList of fields that are legal for the chess Piece to move to.
     */
    public ArrayList<Field> getLegalFields() {
        Field[][] currentFields = ChessBoard.getInstance().getBoardFields();
        ArrayList<Field> legalFields = new ArrayList<>();

        if (this.isChampion()) {
            legalFields=getLegalFieldsChampion(currentFields);
        }
        else {
            int i = currentPosition.getFieldX();
            int j = currentPosition.getFieldY();

            for (int loops = 0; loops < 9; loops++) {
                if (i < 8 && i >= 0 && j < 8 && j >= 0 && !(i == currentPosition.getFieldX() && j == currentPosition.getFieldY())) {
                    if (!currentFields[i][j].isBlocked()&&!isBlocked) {
                        if (currentFields[i][j].getCurrentPiece() == null) {
                            if(!wouldbeChecked(currentFields, currentFields[i][j])){//piece can only move to legal field if it does not cause the king to be in check
                                legalFields.add(currentFields[i][j]);
                            }
                        } else if (currentFields[i][j].getCurrentPiece().getColour() != this.colour&&!currentFields[i][j].isProtected()) {
                            if(!wouldbeChecked(currentFields, currentFields[i][j])){//piece can only move to legal field if it does not cause the king to be in check
                                legalFields.add(currentFields[i][j]);
                            }
                        }
                    }
                    isBlocked=false;
                }
                i = currentPosition.getFieldX();
                j = currentPosition.getFieldY();
                switch (loops) {
                    case 0:
                        isBlocked=testJumpedFields(i,j,2,true,1,false,currentFields);
                        i -= 2;
                        j++;
                        break;
                    case 1:
                        isBlocked=testJumpedFields(i,j,2,true,1,true,currentFields);
                        i -= 2;
                        j--;
                        break;
                    case 2:
                        isBlocked=testJumpedFields(i,j,2,false,1,true,currentFields);
                        i += 2;
                        j--;
                        break;
                    case 3:
                        isBlocked=testJumpedFields(i,j,2,false,1,false,currentFields);
                        i += 2;
                        j++;
                        break;
                    case 4:
                        isBlocked=testJumpedFields(i,j,1,true,2,false,currentFields);
                        i--;
                        j += 2;
                        break;
                    case 5:
                        isBlocked=testJumpedFields(i,j,1,false,2,false,currentFields);
                        i++;
                        j += 2;
                        break;
                    case 6:
                        isBlocked=testJumpedFields(i,j,1,true,2,true,currentFields);
                        i--;
                        j -= 2;
                        break;
                    case 7:
                        isBlocked=testJumpedFields(i,j,1,false,2,true,currentFields);
                        i++;
                        j -= 2;
                        break;

                }
            }
        }

        return legalFields;
    }

    public boolean isFieldNull(int i,int j){
        if (i>=0&&i<8&&j>=0&&j<8)
            return false;
        return true;
    }

    public boolean testJumpedFields(int x,int y,int i,boolean iMinus,int j,boolean jMinus,Field[][] currentFields){
        if (iMinus&&jMinus){
            for (int start=i;start>0;start--){
                if(!isFieldNull(x-start,y)&&currentFields[x-start][y].isBlocked())
                    return true;
            }
            for (int start=j;start>0;start--){
                if(!isFieldNull(x-i,y-start)&&currentFields[x-i][y-start].isBlocked())
                    return true;
            }
        }

        if (iMinus&&!jMinus){
            for (int start=i;start>0;start--){
                if(!isFieldNull(x-start,y)&&currentFields[x-start][y].isBlocked())
                    return true;
            }
            for (int start=0;start<j;start++){
                if(!isFieldNull(x-i,y+start)&&currentFields[x-i][y+start].isBlocked())
                    return true;
            }
        }

        if (!iMinus&&jMinus){
            for (int start=0;start<i;start++){
                if(!isFieldNull(x+start,y)&&currentFields[x+start][y].isBlocked())
                    return true;
            }
            for (int start=j;start>0;start--){
                if(!isFieldNull(x+i,y-start)&&currentFields[x+i][y-start].isBlocked())
                    return true;
            }
        }

        if (!iMinus&&!jMinus){
            for (int start=0;start<i;start++){
                if(!isFieldNull(x+start,y)&&currentFields[x+start][y].isBlocked())
                    return true;
            }
            for (int start=0;start<j;start++){
                if(!isFieldNull(x+i,y+start)&&currentFields[x+i][y+start].isBlocked())
                    return true;
            }
        }
        return false;
    }

    public ArrayList<Field> getLegalFieldsChampion(Field [][] currentFields){
        ArrayList<Field> legalFields=new ArrayList<>();

        int i = currentPosition.getFieldX();
        int j = currentPosition.getFieldY();

        for (int loops = 0; loops < 9; loops++) {
            if (i < 8 && i >= 0 && j < 8 && j >= 0 && !(i == currentPosition.getFieldX() && j == currentPosition.getFieldY())) {
                if (!currentFields[i][j].isBlocked()&&!isBlocked) {
                    if (currentFields[i][j].getCurrentPiece() == null) {
                        legalFields.add(currentFields[i][j]);
                    } else if (currentFields[i][j].getCurrentPiece().getColour() != this.colour&&!currentFields[i][j].isProtected()) {
                        legalFields.add(currentFields[i][j]);
                    }
                }
                isBlocked=false;
            }
            i = currentPosition.getFieldX();
            j = currentPosition.getFieldY();
            switch (loops) {
                case 0:
                    isBlocked=testJumpedFields(i,j,3,true,4,false,currentFields);
                    i-=3;
                    j+=4;
                    break;
                case 1:
                    isBlocked=testJumpedFields(i,j,3,true,4,true,currentFields);
                    i-=3;
                    j-=4;
                    break;
                case 2:
                    isBlocked=testJumpedFields(i,j,3,false,4,true,currentFields);
                    i+=3;
                    j-=4;
                    break;
                case 3:
                    isBlocked=testJumpedFields(i,j,3,false,4,false,currentFields);
                    i+=3;
                    j+=4;
                    break;
                case 4:
                    isBlocked=testJumpedFields(i,j,4,true,3,false,currentFields);
                    i-=4;
                    j+=3;
                    break;
                case 5:
                    isBlocked=testJumpedFields(i,j,4,false,3,false,currentFields);
                    i+=4;
                    j+=3;
                    break;
                case 6:
                    isBlocked=testJumpedFields(i,j,4,true,3,true,currentFields);
                    i-=4;
                    j-=3;
                    break;
                case 7:
                    isBlocked=testJumpedFields(i,j,4,false,3,true,currentFields);
                    i+=4;
                    j-=3;
                    break;

            }
        }

        return legalFields;
    }






}
package com.game.chessmate.GameFiles.PlayingPieces;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.game.chessmate.GameFiles.ChessBoard;
import com.game.chessmate.GameFiles.Field;

import java.util.ArrayList;

/**
 * class implementing the King playing piece
 */
public class King extends ChessPiece {

    private ChessPiece checksKing=null;
    private ArrayList<Field>possibleMovesinCheck;
    private boolean gameOver=false;

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

      /*  if(isChecked(currentFields,this.currentPosition,null)){
            if(isInCheckMate(currentFields)){
                legalFields=possibleMovesinCheck;
            }
            else{
                gameOver=true;
            }
        }

        else {*/
            for (int i = currentPosition.getFieldX() - 1; i <= currentPosition.getFieldX() + 1; i++) {
                for (int j = currentPosition.getFieldY() - 1; j <= currentPosition.getFieldY() + 1; j++) {
                    if (i >= 0 && i <= 7 && j >= 0 && j <= 7) {
                        if (!(i == currentPosition.getFieldX() && j == currentPosition.getFieldY())) {
                            if (currentFields[i][j].getCurrentPiece() == null) {
                                legalFields.add(currentFields[i][j]);
                            } else if (currentFields[i][j].getCurrentPiece().getColour() != this.colour) {
                                legalFields.add(currentFields[i][j]);
                            }
                        }
                    }
                }
            }
        /*}*/
        return legalFields;
    }

    public boolean isChecked(Field [][] fields, Field kingPosition, ChessPiece possibleCapture){
        ChessPieceColour oponentColour;
        ArrayList<Field>legalFields;

        if(this.getColour()==ChessPieceColour.BLACK){
            oponentColour=ChessPieceColour.WHITE;
        }
        else{
            oponentColour=ChessPieceColour.BLACK;
        }

        for (int i=0;i<fields.length;i++){
            for (int j=0;j<fields.length;j++){
                if (fields[i][j].getCurrentPiece()!=null&&fields[i][j].getCurrentPiece().getColour()==oponentColour&&fields[i][j].getCurrentPiece()!=possibleCapture){
                    legalFields=fields[i][j].getCurrentPiece().getLegalFields();
                    for (Field field :legalFields){
                        if (field.equals(kingPosition)){
                            checksKing=fields[i][j].getCurrentPiece();
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isInCheckMate(Field[][]fields){
        ArrayList<Field>legalFields;
        ChessPiece temp=checksKing;

        for (int i=0;i<fields.length;i++) {
            for (int j = 0; j < fields.length; j++) {
                if (fields[i][j].getCurrentPiece() != null && fields[i][j].getCurrentPiece().getColour() == this.getColour()) {
                    legalFields = fields[i][j].getCurrentPiece().getLegalFields();
                    for (Field field : legalFields) {
                        if (field.equals(checksKing.getPosition())) {
                            if (!isChecked(fields, this.currentPosition, checksKing)) {
                                checksKing.capture();
                                checksKing = null;
                                return false;
                            }
                            checksKing=temp;
                        }
                    }
                }
            }
        }

        legalFields=this.getLegalFields();
        possibleMovesinCheck=new ArrayList<Field>();

        for (Field field :legalFields){
            if (!isChecked(fields,field,null)) {
                possibleMovesinCheck.add(field);
                return false;
            }
        }

        if (!possibleMovesinCheck.isEmpty()){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean isGameOver(){return this.gameOver;}

}


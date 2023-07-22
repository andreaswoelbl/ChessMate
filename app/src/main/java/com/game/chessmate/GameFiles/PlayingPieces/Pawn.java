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

    boolean normal = true; //normal moves are allowed
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
        if(i-1 >= 0 && this.normal) {
            if (currentFields[i - 1][j].getCurrentPiece() == null) {
                if(!currentFields[i-1][j].isBlocked())
                    legalFields.add(currentFields[i-1][j]);
            } else if (currentFields[i - 1][j].getCurrentPiece().getColour() != this.colour) {
                opponentEncountered = true;
            } else {
                return legalFields;
            }
            if (this.getFirstMove() && i - 2 > 0 && !opponentEncountered) {
                if (currentFields[i - 2][j].getCurrentPiece() == null) {
                    if(!currentFields[i-2][j].isBlocked())
                        legalFields.add(currentFields[i-2][j]);
                } else if (currentFields[i - 2][j].getCurrentPiece().getColour() != this.colour) {
                    legalFields.add(currentFields[i-2][j]);
                } else {
                    return legalFields;
                }
            }
        }

        //attacking fields is allowed diagonally - and not restricted by boolean normal
        if(i-1>=0 && j-1>=0){
            if (currentFields[i-1][j-1].getCurrentPiece() != null) {
                if(currentFields[i-1][j-1].getCurrentPiece().getColour() != this.colour&&!currentFields[i-1][j-1].isProtected()){
                    legalFields.add(currentFields[i-1][j-1]);
                }
            }
        }
        if(i-1>=0 && j+1<8){
            if (currentFields[i-1][j+1].getCurrentPiece() != null) {
                if(currentFields[i-1][j+1].getCurrentPiece().getColour() != this.colour&&!currentFields[i-1][j+1].isProtected()){
                    legalFields.add(currentFields[i-1][j+1]);
                }
            }
        }
        return legalFields;
    }


    /**
     * Calculated legalFields that can be moved to when king is in check. Normal legalFields are taken, but only attacking ones in pawn.
     * Only fields that free the king out of check are copied into legalMovesInCheck Array.
     *
     * @return ArrayList of fields that can be moved to by piece (that frees king out of check), if king is in check
     */
    @Override
    public ArrayList<Field> getLegalMovesInCheck(){
        Field[][] currentFields = ChessBoard.getInstance().getBoardFields();
        this.normal = false; //normal moves are not allowed
        ArrayList<Field> legalFields = this.getLegalFields();
        this.normal = true; //resetting for normal legal moves call
        ChessPiece localKing = ChessBoard.getInstance().getLocalKing();
        ArrayList<Field> legalMovesInCheck = new ArrayList<Field>();
        localKing.isChecked(currentFields); //to set isChecking
        ArrayList<Field> isChecking = localKing.getIsChecking();

        for (Field f : legalFields) {
            if (!wouldbeChecked(currentFields, f)) {//checks whether king would be in check if currentpieces position were field - same for king
                legalMovesInCheck.add(f);
            }
            if(isChecking.contains(f)){//if piece can kill threatening piece
                legalMovesInCheck.add(f);
            }
        }
        return legalMovesInCheck;
    }


}
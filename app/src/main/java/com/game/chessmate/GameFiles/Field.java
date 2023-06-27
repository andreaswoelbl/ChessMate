package com.game.chessmate.GameFiles;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.game.chessmate.GameFiles.PlayingPieces.ChessPiece;

/** The Field class is an Object that represents a PlayingField on the chessboard. */

public class Field extends View {

    /**
     * @param x x coordinate of the Field on the Chessboard Array
     * @param y y coordinate of the Field on the Chessboard Array
     * @param color color of the Rectangle that is the Field
     * @param rectangle is used to display the Field on the Chessboard for now.
     * @param playingPiece the Chess piece on this Field
     */
    private int x;
    private int y;
    public Paint color;
    public Paint colorLegal;
    private Rect rectangle;
    private ChessPiece currentPiece;
    private boolean update;
    private boolean blocked=false;
    private boolean legal = false;

    /**
     * Constructor of Field. Set coordinates, compute color of the Rectangle with x and y, and construct the rectangle
     */
    public Field (int x, int y, Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
        this.x = x;
        this.y = y;

        this.color = new Paint();
        this.colorLegal = new Paint();
        this.colorLegal.setColor(Color.parseColor("#74ff52"));
        setRectangleDefaultColor();
        rectangle = new Rect();
        setupRectangle(rectangle);
    }

    /**
     * onDraw inherited from View. call invalidate on the field to redraw the rectangle.
     */
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(rectangle, color);
        if(legal){
            Log.d("debug", "draw circle");
            int fieldSize = ChessBoard.getInstance().getFieldSize();
            canvas.drawCircle(fieldSize*this.y+fieldSize/2,fieldSize*this.x+fieldSize/2, fieldSize/4, colorLegal);
        }
    }

    /**
     * Returns true if the field has a playing piece on it
     * @return boolean
     * */
    public boolean hasPiece(){
        if (currentPiece == null) {
            return false;
        }
        else return true;
    }

    /**
     * Gets the current Piece that is on the Playing Field.
     * @return PlayingPiece
     * */
    public ChessPiece getCurrentPiece(){
        return this.currentPiece;
    }

    /**
     * Init the Rectangle by setting the coordinates for left, top, right, bottom.
     */
    public void setupRectangle(Rect rectangle) {
        int fieldSize = ChessBoard.getInstance().getFieldSize();
        this.rectangle = rectangle;
        this.rectangle.left = fieldSize * this.y;
        this.rectangle.top = fieldSize * this.x;
        this.rectangle.right = this.rectangle.left + fieldSize;
        this.rectangle.bottom = this.rectangle.top + fieldSize;
    }

    public Rect getRectangle() {
        return rectangle;
    }

    /**
     * Use the helper class to translate the coordinates in the 2D Array to Chess coordinates.
     * @return String containing the Chess coordinates of the Field.
     */
    public String getChessCoordinates() {
        return ArrayToChessCoordinatesTranslator.translateCoordinates(this.x, this.y);
    }

    /**
     * Sets the rectangle to its default color based on the chessboard layout.
     */
    public void setRectangleDefaultColor(){
        color.setColor((this.x + this.y) % 2 == 0 ? Color.parseColor("#838381") : Color.parseColor("#d5d8db"));
    }

    public void setCurrentPiece(ChessPiece currentPiece) {
        this.currentPiece = currentPiece;
    }

    public int getFieldX(){
        return this.x;
    }

    public int getFieldY(){
        return this.y;
    }

    public void setAsLegal(){
        this.legal = true;
    }

    public void setAsIllegal(){
        this.legal = false;
    }

    public boolean getUpdate() {
        return this.update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public  void setPlayingPieceShield(){color.setColor(Color.BLUE);}

    public void setBlocked(){
        this.blocked=true;
        color.setColor(Color.RED);}

    public boolean isBlocked(){return this.blocked;}
}

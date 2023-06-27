package com.game.chessmate.GameFiles;

/**
 * Helper Class to transform coordinates of a 2D Array into coordinates of a chessboard.
 */
public class ArrayToChessCoordinatesTranslator {

    /**
     * @param row from 2D Array
     * @param col from 2D Array
     * @return the String that represents the coordinates on the chessboard. for ex. A1, C4, ...
     */
    public static String translateCoordinates(int row, int col) {
        return columnToString(col) + rowToString(row);
    }

    private static String columnToString(int col) {
        return Character.toString((char)(col+65));
    }

    private static String rowToString(int row) {
        return String.valueOf(row+1);
    }
}
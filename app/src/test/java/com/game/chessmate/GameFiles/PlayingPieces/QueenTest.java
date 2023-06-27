package com.game.chessmate.GameFiles.PlayingPieces;

import android.content.Context;
import android.graphics.Bitmap;

import com.game.chessmate.GameFiles.BoardView;
import com.game.chessmate.GameFiles.ChessBoard;
import com.game.chessmate.GameFiles.Field;
import com.game.chessmate.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QueenTest {
    ChessPieceColour colour;
    int drawableId;

    @Mock
    private Bitmap sprite;

    @Mock
    private Context context;

    @Mock
    private BoardView view;

    @Mock
    private Field field;

    Queen queen;
    //for legal moves tests
    ArrayList<String> expected;
    ChessBoard cb;
    Field[][] currentFields;


    @Before
    public void init(){
        colour= ChessPieceColour.WHITE;
        context= Mockito.mock(Context.class);
        field= Mockito.mock(Field.class);
        sprite=Mockito.mock(Bitmap.class);

        field= Mockito.mock(Field.class);
        view = Mockito.mock(BoardView.class);
        drawableId= R.drawable.bishop_player1;

        queen=new Queen(field, sprite,context,null,colour);
        queen.setColor(colour);
        //for legal Moves tests
        expected = new ArrayList<>();
        cb = ChessBoard.getInstance();
        when(view.getContext()).thenReturn(null);
        cb.initChessBoard(view, 10);
        currentFields = cb.getBoardFields();
        queen.setCurrentPosition(field);

    }


    @Test
    public void returnPlayingPieceTypeTest() {
        assertEquals(ChessPieceType.QUEEN,queen.getPlayingPieceType());
    }

    @Test
    public void getPositionTest(){
        assertEquals(field,queen.getPosition());
    }

    @Test
    public void getDrawableTest(){
        assertEquals(sprite,queen.getDrawable());
    }

    @Test
    public void getColour(){
        assertEquals(colour,queen.getColour());

        colour= ChessPieceColour.BLACK;
        queen.setColor(colour);

        assertEquals(colour,queen.getColour());
    }

    //NOTE - in the testcase environment, the position of the black and white pieces is different than in the app. The position of the pieces (but not the chessboard) is changed as if the chessboard were rotated agianst the clock once - so black pieces are on the left and white pieces on the right.
    /*
       testcases - one average testcase when piece is in the middle of the chessboard - legal moves should be restricted by pieces of same colour (later also by opponent),
       one testcase per chessboard border (4) - legal moves should be restricted by pieces of same colour and border (later also by opponent),
       one testcase per chessboard corner (4) - legal moves should be restricted by border twice and pieces
    */

    @Test
    public void getLegalFieldsAverageCaseTest(){
        when(field.getFieldX()).thenReturn(3);
        when(field.getFieldY()).thenReturn(3);

        expected.add(currentFields[4][3].getFieldX() + ":" + currentFields[4][3].getFieldY());
        expected.add(currentFields[5][3].getFieldX() + ":" + currentFields[5][3].getFieldY());
        expected.add(currentFields[2][3].getFieldX() + ":" + currentFields[2][3].getFieldY());
        expected.add(currentFields[1][3].getFieldX() + ":" + currentFields[1][3].getFieldY());
        //expected.add(currentFields[0][3].getFieldX() + ":" + currentFields[0][3].getFieldY());//must be removed once opponent interaction exists
        expected.add(currentFields[3][2].getFieldX() + ":" + currentFields[3][2].getFieldY());
        expected.add(currentFields[3][1].getFieldX() + ":" + currentFields[3][1].getFieldY());
        expected.add(currentFields[3][0].getFieldX() + ":" + currentFields[3][0].getFieldY());
        expected.add(currentFields[3][4].getFieldX() + ":" + currentFields[3][4].getFieldY());
        expected.add(currentFields[3][5].getFieldX() + ":" + currentFields[3][5].getFieldY());
        expected.add(currentFields[3][6].getFieldX() + ":" + currentFields[3][6].getFieldY());
        expected.add(currentFields[3][7].getFieldX() + ":" + currentFields[3][7].getFieldY());
        expected.add(currentFields[2][2].getFieldX() + ":" + currentFields[2][2].getFieldY());
        expected.add(currentFields[1][1].getFieldX() + ":" + currentFields[1][1].getFieldY());
        //expected.add(currentFields[0][0].getFieldX() + ":" + currentFields[0][0].getFieldY());//must be removed once opponent interaction exists
        expected.add(currentFields[4][4].getFieldX() + ":" + currentFields[4][4].getFieldY());
        expected.add(currentFields[5][5].getFieldX() + ":" + currentFields[5][5].getFieldY());
        expected.add(currentFields[2][4].getFieldX() + ":" + currentFields[2][4].getFieldY());
        expected.add(currentFields[1][5].getFieldX() + ":" + currentFields[1][5].getFieldY());
        //expected.add(currentFields[0][6].getFieldX() + ":" + currentFields[0][6].getFieldY());//must be removed once opponent interaction exists
        expected.add(currentFields[4][2].getFieldX() + ":" + currentFields[4][2].getFieldY());
        expected.add(currentFields[5][1].getFieldX() + ":" + currentFields[5][1].getFieldY());

        ArrayList<Field> temp = queen.getLegalFields();
        ArrayList<String> actual = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            actual.add(temp.get(i).getFieldX() + ":" + temp.get(i).getFieldY());
        }
        assertEquals(expected, actual);//-- to see mistake of this test
        assertTrue(expected.size() == actual.size() && expected.containsAll(actual) && actual.containsAll(expected));//better solution - order does not matter
    }



    @Test
    public void getLegalFieldsUpperBorderTest(){
        when(field.getFieldX()).thenReturn(3);
        when(field.getFieldY()).thenReturn(0);

        expected.add(currentFields[4][0].getFieldX() + ":" + currentFields[4][0].getFieldY());
        expected.add(currentFields[5][0].getFieldX() + ":" + currentFields[5][0].getFieldY());
        expected.add(currentFields[2][0].getFieldX() + ":" + currentFields[2][0].getFieldY());
        expected.add(currentFields[1][0].getFieldX() + ":" + currentFields[1][0].getFieldY());
        //expected.add(currentFields[0][0].getFieldX() + ":" + currentFields[0][0].getFieldY());//must be removed once opponent interaction exists
        expected.add(currentFields[3][1].getFieldX() + ":" + currentFields[3][1].getFieldY());
        expected.add(currentFields[3][2].getFieldX() + ":" + currentFields[3][2].getFieldY());
        expected.add(currentFields[3][3].getFieldX() + ":" + currentFields[3][3].getFieldY());
        expected.add(currentFields[3][4].getFieldX() + ":" + currentFields[3][4].getFieldY());
        expected.add(currentFields[3][5].getFieldX() + ":" + currentFields[3][5].getFieldY());
        expected.add(currentFields[3][6].getFieldX() + ":" + currentFields[3][6].getFieldY());
        expected.add(currentFields[3][7].getFieldX() + ":" + currentFields[3][7].getFieldY());
        expected.add(currentFields[4][1].getFieldX() + ":" + currentFields[4][1].getFieldY());
        expected.add(currentFields[5][2].getFieldX() + ":" + currentFields[5][2].getFieldY());
        expected.add(currentFields[2][1].getFieldX() + ":" + currentFields[2][1].getFieldY());
        expected.add(currentFields[1][2].getFieldX() + ":" + currentFields[1][2].getFieldY());
        //expected.add(currentFields[0][3].getFieldX() + ":" + currentFields[0][3].getFieldY());//must be removed once opponent interaction exists

        ArrayList<Field> temp = queen.getLegalFields();
        ArrayList<String> actual = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            actual.add(temp.get(i).getFieldX() + ":" + temp.get(i).getFieldY());
        }
        assertEquals(expected, actual);//-- to see mistake of this test
        assertTrue(expected.size() == actual.size() && expected.containsAll(actual) && actual.containsAll(expected));//better solution - order does not matter
    }


    @Test
    public void getLegalFieldsLeftBorderTest(){
        when(field.getFieldX()).thenReturn(0);
        when(field.getFieldY()).thenReturn(3);

        expected.add(currentFields[1][3].getFieldX() + ":" + currentFields[1][3].getFieldY());
        //expected.add(currentFields[2][3].getFieldX() + ":" + currentFields[2][3].getFieldY());//must be removed once opponent interaction exists
        //expected.add(currentFields[3][3].getFieldX() + ":" + currentFields[3][3].getFieldY());//must be removed once opponent interaction exists
        //expected.add(currentFields[4][3].getFieldX() + ":" + currentFields[4][3].getFieldY());//must be removed once opponent interaction exists
        //expected.add(currentFields[5][3].getFieldX() + ":" + currentFields[5][3].getFieldY());//must be removed once opponent interaction exists
        expected.add(currentFields[0][2].getFieldX() + ":" + currentFields[0][2].getFieldY());
        //expected.add(currentFields[0][1].getFieldX() + ":" + currentFields[0][1].getFieldY());//must be removed once opponent interaction exists
        //expected.add(currentFields[0][0].getFieldX() + ":" + currentFields[0][0].getFieldY());//must be removed once opponent interaction exists
        expected.add(currentFields[0][4].getFieldX() + ":" + currentFields[0][4].getFieldY());
        //expected.add(currentFields[0][5].getFieldX() + ":" + currentFields[0][5].getFieldY());//must be removed once opponent interaction exists
        //expected.add(currentFields[0][6].getFieldX() + ":" + currentFields[0][6].getFieldY());//must be removed once opponent interaction exists
        //expected.add(currentFields[0][7].getFieldX() + ":" + currentFields[0][7].getFieldY());//must be removed once opponent interaction exists
        expected.add(currentFields[1][4].getFieldX() + ":" + currentFields[1][4].getFieldY());
        //expected.add(currentFields[2][5].getFieldX() + ":" + currentFields[2][5].getFieldY());//must be removed once opponent interaction exists
        //expected.add(currentFields[3][6].getFieldX() + ":" + currentFields[3][6].getFieldY());//must be removed once opponent interaction exists
        //expected.add(currentFields[4][7].getFieldX() + ":" + currentFields[4][7].getFieldY());//must be removed once opponent interaction exists
        expected.add(currentFields[1][2].getFieldX() + ":" + currentFields[1][2].getFieldY());
        //expected.add(currentFields[2][1].getFieldX() + ":" + currentFields[2][1].getFieldY());//must be removed once opponent interaction exists
        //expected.add(currentFields[3][0].getFieldX() + ":" + currentFields[3][0].getFieldY());//must be removed once opponent interaction exists

        ArrayList<Field> temp = queen.getLegalFields();
        ArrayList<String> actual = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            actual.add(temp.get(i).getFieldX() + ":" + temp.get(i).getFieldY());
        }
        assertEquals(expected, actual);//-- to see mistake of this test
        assertTrue(expected.size() == actual.size() && expected.containsAll(actual) && actual.containsAll(expected));//better solution - order does not matter
    }

    @Test
    public void getLegalFieldsRightBorderTest(){
        when(field.getFieldX()).thenReturn(7);
        when(field.getFieldY()).thenReturn(3);


        ArrayList<Field> temp = queen.getLegalFields();
        ArrayList<String> actual = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            actual.add(temp.get(i).getFieldX() + ":" + temp.get(i).getFieldY());
        }
        assertEquals(expected, actual);//-- to see mistake of this test
        assertTrue(expected.size() == actual.size() && expected.containsAll(actual) && actual.containsAll(expected));//better solution - order does not matter
    }

    @Test
    public void getLegalFieldsLowerBorderTest(){
        when(field.getFieldX()).thenReturn(3);
        when(field.getFieldY()).thenReturn(7);

        expected.add(currentFields[4][7].getFieldX() + ":" + currentFields[4][7].getFieldY());
        expected.add(currentFields[5][7].getFieldX() + ":" + currentFields[5][7].getFieldY());
        expected.add(currentFields[2][7].getFieldX() + ":" + currentFields[2][7].getFieldY());
        expected.add(currentFields[1][7].getFieldX() + ":" + currentFields[1][7].getFieldY());
        //expected.add(currentFields[0][7].getFieldX() + ":" + currentFields[0][7].getFieldY());//must be removed once opponent interaction exists
        expected.add(currentFields[3][6].getFieldX() + ":" + currentFields[3][6].getFieldY());
        expected.add(currentFields[3][5].getFieldX() + ":" + currentFields[3][5].getFieldY());
        expected.add(currentFields[3][4].getFieldX() + ":" + currentFields[3][4].getFieldY());
        expected.add(currentFields[3][3].getFieldX() + ":" + currentFields[3][3].getFieldY());
        expected.add(currentFields[3][2].getFieldX() + ":" + currentFields[3][2].getFieldY());
        expected.add(currentFields[3][1].getFieldX() + ":" + currentFields[3][1].getFieldY());
        expected.add(currentFields[3][0].getFieldX() + ":" + currentFields[3][0].getFieldY());
        expected.add(currentFields[2][6].getFieldX() + ":" + currentFields[2][6].getFieldY());
        expected.add(currentFields[1][5].getFieldX() + ":" + currentFields[1][5].getFieldY());
        //expected.add(currentFields[0][4].getFieldX() + ":" + currentFields[0][4].getFieldY());//must be removed once opponent interaction exists
        expected.add(currentFields[4][6].getFieldX() + ":" + currentFields[4][6].getFieldY());
        expected.add(currentFields[5][5].getFieldX() + ":" + currentFields[5][5].getFieldY());

        ArrayList<Field> temp = queen.getLegalFields();
        ArrayList<String> actual = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            actual.add(temp.get(i).getFieldX() + ":" + temp.get(i).getFieldY());
        }
        assertEquals(expected, actual);//-- to see mistake of this test
        assertTrue(expected.size() == actual.size() && expected.containsAll(actual) && actual.containsAll(expected));//better solution - order does not matter
    }

    @Test
    public void getLegalFieldsLeftLowerCornerTest(){
        when(field.getFieldX()).thenReturn(0);
        when(field.getFieldY()).thenReturn(7);

        expected.add(currentFields[1][7].getFieldX() + ":" + currentFields[1][7].getFieldY());
        //expected.add(currentFields[2][7].getFieldX() + ":" + currentFields[2][7].getFieldY());//must be removed once opponent interaction exists
        //expected.add(currentFields[3][7].getFieldX() + ":" + currentFields[3][7].getFieldY());//must be removed once opponent interaction exists
        //expected.add(currentFields[4][7].getFieldX() + ":" + currentFields[4][7].getFieldY());//must be removed once opponent interaction exists
        //expected.add(currentFields[5][7].getFieldX() + ":" + currentFields[5][7].getFieldY());//must be removed once opponent interaction exists
        expected.add(currentFields[0][6].getFieldX() + ":" + currentFields[0][6].getFieldY());
        //expected.add(currentFields[0][5].getFieldX() + ":" + currentFields[0][5].getFieldY());//must be removed once opponent interaction exists
        //expected.add(currentFields[0][4].getFieldX() + ":" + currentFields[0][4].getFieldY());//must be removed once opponent interaction exists
        //expected.add(currentFields[0][3].getFieldX() + ":" + currentFields[0][3].getFieldY());//must be removed once opponent interaction exists
        //expected.add(currentFields[0][2].getFieldX() + ":" + currentFields[0][2].getFieldY());//must be removed once opponent interaction exists
        //expected.add(currentFields[0][1].getFieldX() + ":" + currentFields[0][1].getFieldY());//must be removed once opponent interaction exists
        //expected.add(currentFields[0][0].getFieldX() + ":" + currentFields[0][0].getFieldY());//must be removed once opponent interaction exists
        expected.add(currentFields[1][6].getFieldX() + ":" + currentFields[1][6].getFieldY());
        //expected.add(currentFields[2][5].getFieldX() + ":" + currentFields[2][5].getFieldY());//must be removed once opponent interaction exists
        //expected.add(currentFields[3][4].getFieldX() + ":" + currentFields[3][4].getFieldY());//must be removed once opponent interaction exists
        //expected.add(currentFields[4][3].getFieldX() + ":" + currentFields[4][3].getFieldY());//must be removed once opponent interaction exists
        //expected.add(currentFields[5][2].getFieldX() + ":" + currentFields[5][2].getFieldY());//must be removed once opponent interaction exists

        ArrayList<Field> temp = queen.getLegalFields();
        ArrayList<String> actual = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            actual.add(temp.get(i).getFieldX() + ":" + temp.get(i).getFieldY());
        }
        assertEquals(expected, actual);//-- to see mistake of this test
        assertTrue(expected.size() == actual.size() && expected.containsAll(actual) && actual.containsAll(expected));//better solution - order does not matter
    }

    @Test
    public void getLegalFieldsRightLowerCornerTest(){
        when(field.getFieldX()).thenReturn(7);
        when(field.getFieldY()).thenReturn(7);

        ArrayList<Field> temp = queen.getLegalFields();
        ArrayList<String> actual = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            actual.add(temp.get(i).getFieldX() + ":" + temp.get(i).getFieldY());
        }
        assertEquals(expected, actual);//-- to see mistake of this test
        assertTrue(expected.size() == actual.size() && expected.containsAll(actual) && actual.containsAll(expected));//better solution - order does not matter
    }

    @Test
    public void getLegalFieldsLeftUpperCornerTest(){
        when(field.getFieldX()).thenReturn(0);
        when(field.getFieldY()).thenReturn(0);

        expected.add(currentFields[1][0].getFieldX() + ":" + currentFields[1][0].getFieldY());
        //expected.add(currentFields[2][0].getFieldX() + ":" + currentFields[2][0].getFieldY());//must be removed once opponent interaction exists
        //expected.add(currentFields[3][0].getFieldX() + ":" + currentFields[3][0].getFieldY());//must be removed once opponent interaction exists
        //expected.add(currentFields[4][0].getFieldX() + ":" + currentFields[4][0].getFieldY());//must be removed once opponent interaction exists
        //expected.add(currentFields[5][0].getFieldX() + ":" + currentFields[5][0].getFieldY());//must be removed once opponent interaction exists
        expected.add(currentFields[0][1].getFieldX() + ":" + currentFields[0][1].getFieldY());
        //expected.add(currentFields[0][2].getFieldX() + ":" + currentFields[0][2].getFieldY());//must be removed once opponent interaction exists
        //expected.add(currentFields[0][3].getFieldX() + ":" + currentFields[0][3].getFieldY());//must be removed once opponent interaction exists
        //expected.add(currentFields[0][4].getFieldX() + ":" + currentFields[0][4].getFieldY());//must be removed once opponent interaction exists
        //expected.add(currentFields[0][5].getFieldX() + ":" + currentFields[0][5].getFieldY());//must be removed once opponent interaction exists
        // expected.add(currentFields[0][6].getFieldX() + ":" + currentFields[0][6].getFieldY());//must be removed once opponent interaction exists
        //expected.add(currentFields[0][7].getFieldX() + ":" + currentFields[0][7].getFieldY());//must be removed once opponent interaction exists
        expected.add(currentFields[1][1].getFieldX() + ":" + currentFields[1][1].getFieldY());
        //expected.add(currentFields[2][2].getFieldX() + ":" + currentFields[2][2].getFieldY());//must be removed once opponent interaction exists
        //expected.add(currentFields[3][3].getFieldX() + ":" + currentFields[3][3].getFieldY());//must be removed once opponent interaction exists
        //expected.add(currentFields[4][4].getFieldX() + ":" + currentFields[4][4].getFieldY());//must be removed once opponent interaction exists
        //expected.add(currentFields[5][5].getFieldX() + ":" + currentFields[5][5].getFieldY());//must be removed once opponent interaction exists


        ArrayList<Field> temp = queen.getLegalFields();
        ArrayList<String> actual = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            actual.add(temp.get(i).getFieldX() + ":" + temp.get(i).getFieldY());
        }
        assertEquals(expected, actual);//-- to see mistake of this test
        assertTrue(expected.size() == actual.size() && expected.containsAll(actual) && actual.containsAll(expected));//better solution - order does not matter
    }

    @Test
    public void getLegalFieldsRightUpperCornerTest(){
        when(field.getFieldX()).thenReturn(7);
        when(field.getFieldY()).thenReturn(0);

        ArrayList<Field> temp = queen.getLegalFields();
        ArrayList<String> actual = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            actual.add(temp.get(i).getFieldX() + ":" + temp.get(i).getFieldY());
        }
        assertEquals(expected, actual);//-- to see mistake of this test
        assertTrue(expected.size() == actual.size() && expected.containsAll(actual) && actual.containsAll(expected));//better solution - order does not matter
    }



}

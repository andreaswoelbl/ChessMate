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
public class PawnTest {
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

    Pawn pawn;
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

        pawn=new Pawn(field, sprite,context,null,colour);
        pawn.setColor(colour);
        //for legal Moves tests
        expected = new ArrayList<>();
        cb = ChessBoard.getInstance();
        when(view.getContext()).thenReturn(null);
        cb.initChessBoard(view, 10);
        currentFields = cb.getBoardFields();
        pawn.setCurrentPosition(field);

    }


    @Test
    public void returnPlayingPieceTypeTest() {
        assertEquals(ChessPieceType.PAWN,pawn.getPlayingPieceType());
    }

    @Test
    public void getPositionTest(){
        assertEquals(field,pawn.getPosition());
    }

    @Test
    public void getDrawableTest(){
        assertEquals(sprite,pawn.getDrawable());
    }

    @Test
    public void getColour(){
        assertEquals(colour,pawn.getColour());

        colour= ChessPieceColour.BLACK;
        pawn.setColor(colour);

        assertEquals(colour,pawn.getColour());
    }

    //NOTE - in the testcase environment, the position of the black and white pieces is different than in the app. The position of the pieces (but not the chessboard) is changed as if the chessboard were rotated agianst the clock once - so black pieces are on the left and white pieces on the right.
    /*
       testcases - one average testcase when piece is in the middle of the chessboard - legal moves should be restricted by pieces of same colour (later also by opponent),
       one testcase per chessboard border (4) - legal moves should be restricted by pieces of same colour and border (later also by opponent),
       one testcase per chessboard corner (4) - legal moves should be restricted by border twice and pieces
       some extra testcases with name secondMove test special functionality of pawn which is 2 legal moves in pawns first move only, so in second move only 1 legal move
    */

    @Test
    public void getLegalFieldsAverageCaseFirstMoveTest(){
        when(field.getFieldX()).thenReturn(3);
        when(field.getFieldY()).thenReturn(3);

        expected.add(currentFields[2][3].getFieldX() + ":" + currentFields[2][3].getFieldY());
        expected.add(currentFields[1][3].getFieldX() + ":" + currentFields[1][3].getFieldY());

        ArrayList<Field> temp = pawn.getLegalFields();
        ArrayList<String> actual = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            actual.add(temp.get(i).getFieldX() + ":" + temp.get(i).getFieldY());
        }
        assertEquals(expected, actual);//-- to see mistake of this test
        assertTrue(expected.size() == actual.size() && expected.containsAll(actual) && actual.containsAll(expected));//better solution - order does not matter
    }

    @Test
    public void getLegalFieldsAverageCaseSecondMoveTest(){
        when(field.getFieldX()).thenReturn(3);
        when(field.getFieldY()).thenReturn(3);

        pawn.setFirstMove(false);
        expected = new ArrayList<>();
        expected.add(currentFields[2][3].getFieldX() + ":" + currentFields[2][3].getFieldY());
        ArrayList<Field> temp = pawn.getLegalFields();
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

        expected.add(currentFields[2][0].getFieldX() + ":" + currentFields[2][0].getFieldY());
        expected.add(currentFields[1][0].getFieldX() + ":" + currentFields[1][0].getFieldY());

        ArrayList<Field> temp = pawn.getLegalFields();
        ArrayList<String> actual = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            actual.add(temp.get(i).getFieldX() + ":" + temp.get(i).getFieldY());
        }
        assertEquals(expected, actual);//-- to see mistake of this test
        assertTrue(expected.size() == actual.size() && expected.containsAll(actual) && actual.containsAll(expected));//better solution - order does not matter
    }


    @Test
    public void getLegalFieldsLeftBorderFirstMoveTest(){
        when(field.getFieldX()).thenReturn(0);
        when(field.getFieldY()).thenReturn(3);

        ArrayList<Field> temp = pawn.getLegalFields();
        ArrayList<String> actual = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            actual.add(temp.get(i).getFieldX() + ":" + temp.get(i).getFieldY());
        }
        assertEquals(expected, actual);//-- to see mistake of this test
        assertTrue(expected.size() == actual.size() && expected.containsAll(actual) && actual.containsAll(expected));//better solution - order does not matter
    }

    @Test
    public void getLegalFieldsLeftBorderSecondMoveTest(){
        when(field.getFieldX()).thenReturn(0);
        when(field.getFieldY()).thenReturn(3);

        pawn.setFirstMove(false);
        ArrayList<Field> temp = pawn.getLegalFields();
        ArrayList<String> actual = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            actual.add(temp.get(i).getFieldX() + ":" + temp.get(i).getFieldY());
        }
        assertEquals(expected, actual);//-- to see mistake of this test
        assertTrue(expected.size() == actual.size() && expected.containsAll(actual) && actual.containsAll(expected));//better solution - order does not matter
    }

    @Test
    public void getLegalFieldsRightBorderFirstMoveTest(){
        when(field.getFieldX()).thenReturn(7);
        when(field.getFieldY()).thenReturn(3);

        ArrayList<Field> temp = pawn.getLegalFields();
        ArrayList<String> actual = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            actual.add(temp.get(i).getFieldX() + ":" + temp.get(i).getFieldY());
        }
        assertEquals(expected, actual);//-- to see mistake of this test
        assertTrue(expected.size() == actual.size() && expected.containsAll(actual) && actual.containsAll(expected));//better solution - order does not matter
    }

    @Test
    public void getLegalFieldsRightBorderSecondMoveTest(){
        when(field.getFieldX()).thenReturn(7);
        when(field.getFieldY()).thenReturn(3);
        pawn.setFirstMove(false);

        ArrayList<Field> temp = pawn.getLegalFields();
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

        expected.add(currentFields[2][7].getFieldX() + ":" + currentFields[2][7].getFieldY());
        expected.add(currentFields[1][7].getFieldX() + ":" + currentFields[1][7].getFieldY());

        ArrayList<Field> temp = pawn.getLegalFields();
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


        ArrayList<Field> temp = pawn.getLegalFields();
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

        ArrayList<Field> temp = pawn.getLegalFields();
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

        ArrayList<Field> temp = pawn.getLegalFields();
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

        ArrayList<Field> temp = pawn.getLegalFields();
        ArrayList<String> actual = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            actual.add(temp.get(i).getFieldX() + ":" + temp.get(i).getFieldY());
        }
        assertEquals(expected, actual);//-- to see mistake of this test
        assertTrue(expected.size() == actual.size() && expected.containsAll(actual) && actual.containsAll(expected));//better solution - order does not matter
    }

    @Test
    public void getLegalFieldsOpponentEncounteredTest(){
        when(field.getFieldX()).thenReturn(2);
        when(field.getFieldY()).thenReturn(0);

        expected.add(currentFields[1][0].getFieldX() + ":" + currentFields[1][0].getFieldY());
        ArrayList<Field> temp = pawn.getLegalFields();
        ArrayList<String> actual = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            actual.add(temp.get(i).getFieldX() + ":" + temp.get(i).getFieldY());
        }
        assertEquals(expected, actual);//-- to see mistake of this test
        assertTrue(expected.size() == actual.size() && expected.containsAll(actual) && actual.containsAll(expected));//better solution - order does not matter
    }


}

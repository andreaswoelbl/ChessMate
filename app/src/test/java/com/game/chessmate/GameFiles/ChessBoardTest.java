package com.game.chessmate.GameFiles;

import android.content.res.Resources;

import com.game.chessmate.GameFiles.PlayingPieces.ChessPiece;
import com.game.chessmate.GameFiles.PlayingPieces.ChessPieceType;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(MockitoJUnitRunner.class)
public class ChessBoardTest {

    private ChessBoard chessBoard;

    @Mock
    private static BoardView view;
    @Mock
    private static Resources resources;

    @BeforeClass
    public static void init() {
        view = Mockito.mock(BoardView.class);
        resources = Mockito.mock(Resources.class);
    }

    @Before
    public void setup() {
        chessBoard = ChessBoard.getInstance();
        chessBoard.initChessBoard(view, 8);
    }

    @Test
    public void ifChessBoardInit_ThenChessBoardFieldSizeIsValid() {
        assertEquals("Size of Fields is not computed correctly", 1, chessBoard.getFieldSize());
    }

    @Test
    public void ifChessBoardInit_ThenChessBoardIsStandardSize() {
        Field[][] boardFields = chessBoard.getBoardFields();
        assertEquals("ChessBoard is not standard size", 8, boardFields.length);
        assertEquals("ChessBoard is not standard size", 8, boardFields[0].length);
    }

    @Test
    public void ifChessBoardInit_ThenChessBoardFieldsAreNotNull() {
        Field[][] boardFields = chessBoard.getBoardFields();
        for (Field[] row : boardFields) {
            for (Field f : row) {
                assertNotNull("Some Fields in Chessboard appear to be null", f);
            }
        }
    }

    /*@Test
    public void ifChessBoardInit_ThenViewHasViewHasCorrectChildCount() {
        try {
            Mockito.verify(view, Mockito.times(64)).addView(any());
        } catch (MockitoAssertionError e) {
            throw new MockitoAssertionError("Was expecting addView to be called 64 times for standard chessboard size");
        }
    }*/

    @Test
    public void ifChessBoardInit_ThenPlayerChessPiecesNotNull() {
        for(ChessPiece p : chessBoard.getPiecesPlayer1()){
            assertNotNull("Some Chess pieces of player 1 are null.", p);
        }
        for(ChessPiece p : chessBoard.getPiecesPlayer2()){
            assertNotNull("Some Chess pieces of player 2 are null.", p);
        }
    }

   /* @Test
    public void ifChessBoardInit_ThenEachPlayerHasTheirRespectiveChessPieces() {
        assertEquals("Player 1 has less or more pieces than expected.", 0, computeChessPiecesPerPlayerCount(chessBoard.getPiecesPlayer1()));
        assertEquals("Player 2 has less or more pieces than expected.", 0, computeChessPiecesPerPlayerCount(chessBoard.getPiecesPlayer2()));
    }*/

    private int computeChessPiecesPerPlayerCount(ArrayList<ChessPiece> piecesPlayer) {
        int pawnCount = 8;
        int rookCount = 2;
        int knightCount = 2;
        int bishopCount = 2;
        int queenCount = 1;
        int kingCount = 1;

        for (ChessPiece p : piecesPlayer) {
            ChessPieceType type = p.getPlayingPieceType();
            switch (type) {
                case PAWN: pawnCount--; break;
                case ROOK: rookCount--; break;
                case KNIGHT: knightCount--; break;
                case BISHOP: bishopCount--; break;
                case QUEEN: queenCount--; break;
                case KING: kingCount--; break;
                default: throw new UnsupportedOperationException("Player1Pieces contains an unsupported type.");
            }
        }

        return pawnCount + rookCount + knightCount + bishopCount + queenCount + kingCount;
    }
}

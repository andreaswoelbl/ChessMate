package com.game.chessmate.GameFiles;

import com.game.chessmate.GameFiles.PlayingPieces.ChessPiece;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

public class RenderThreadTest {

    private Thread thread;

    private RenderThread runnable;

    private Field[][] fieldArray;

    @Mock
    private Field field;

    @Mock
    private ChessPiece piece;

    @Mock
    private ChessBoard board;

    @Before
    public void init() {
        runnable = new RenderThread();
        thread = new Thread(runnable);
        field = Mockito.mock(Field.class);
        piece = Mockito.mock(ChessPiece.class);
        board = Mockito.mock(ChessBoard.class);
        fieldArray = new Field[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                fieldArray[i][j] = field;
            }
        }
    }

    @Test
    public void ifRenderThread_ThenDoNotThrow() {
        when(board.getBoardFields()).thenReturn(fieldArray);
        when(field.getCurrentPiece()).thenReturn(piece);
        when(field.getUpdate()).thenReturn(true);
        try{
            runnable.setRunning(true);
            thread.start();
        } catch(AssertionError e) {
            fail("RenderThread should not throw while sleeping.");
        }
        runnable.setRunning(false);
    }
}

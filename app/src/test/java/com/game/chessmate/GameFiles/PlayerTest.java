package com.game.chessmate.GameFiles;

import com.game.chessmate.GameFiles.PlayingPieces.ChessPiece;
import com.game.chessmate.GameFiles.PlayingPieces.ChessPieceColour;
import com.game.chessmate.GameFiles.PlayingPieces.Pawn;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class PlayerTest {

    private Player player;

    @Mock
    private ChessPiece piece;

    @Before
    public void init() {
        piece = Mockito.mock(Pawn.class);
        player = new Player(ChessPieceColour.WHITE);
    }

    @Test
    public void ifNewPlayer_ThenPlayerVariablesNotNull() {
        assertNotNull(player.getChessPiecesAlive());
        assertNotNull(player.getChessPiecesCaptured());
        assertNotNull(player.getLegalMovesSelected());
        assertNull(player.getLastSelectedField());
        assertEquals(ChessPieceColour.WHITE, player.getColor());
    }

    @Test
    public void ifAddOrRemovePiecesAlive_ThenPieceGetsAddedOrRemoved() {
        player.addChessPiecesAlive(piece);
        assertTrue(player.getChessPiecesAlive().contains(piece));
        player.removeChessPiecesAlive(piece);
        assertFalse(player.getChessPiecesAlive().contains(piece));
    }

    @Test
    public void ifAddOrRemovePiecesCaptured_ThenPieceGetsAddedOrRemoved() {
        player.addChessPiecesCaptured(piece);
        assertTrue(player.getChessPiecesCaptured().contains(piece));
        player.removeChessPiecesCaptured(piece);
        assertFalse(player.getChessPiecesCaptured().contains(piece));
    }
}

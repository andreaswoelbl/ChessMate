package com.game.chessmate.GameFiles;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArrayToChessCoordinatesTranslatorTest {

    @Test
    public void translateCoordinatesTest() {
        assertEquals("A1",ArrayToChessCoordinatesTranslator.translateCoordinates(0,0));
    }
}

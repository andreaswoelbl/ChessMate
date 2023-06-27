package com.game.chessmate.GameFiles.PlayingPieces;

import com.game.chessmate.GameFiles.Vector;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class VectorTest {

    private Vector vector1;
    private Vector vector2;

    @Test
    public void ifNewVector_ThenCreateNewVector() {
        vector1 = new Vector(1,1);
        assertNotNull(vector1);
        assertEquals(1, vector1.getX(), 0.0001);
    }

    @Test
    public void ifVectorAddPositiveAndNegative_ThenReturnCorrectAddition() {
        vector1 = new Vector(1,1);
        vector2 = new Vector(-1,-1);
        vector1 = vector1.add(vector2);
        assertEquals(0, vector1.getX(), 0.0001);
        assertEquals(0, vector1.getY(), 0.0001);
    }

    @Test
    public void ifVectorAddNegative_ThenReturnCorrectAddition() {
        vector1 = new Vector(-5,-5);
        vector2 = new Vector(-10,-5);
        vector1 = vector1.add(vector2);
        assertEquals(-15, vector1.getX(), 0.0001);
        assertEquals(-10, vector1.getY(), 0.0001);
    }

    @Test
    public void ifVectorSubPositiveAndNegative_ThenReturnCorrectSubtraction() {
        vector1 = new Vector(1,1);
        vector2 = new Vector(-1,1);
        vector1 = vector1.sub(vector2);
        assertEquals(2, vector1.getX(), 0.0001);
        assertEquals(0, vector1.getY(), 0.0001);
    }

    @Test
    public void ifVectorSubNegative_ThenReturnCorrectSubtraction() {
        vector1 = new Vector(-5,-5);
        vector2 = new Vector(-10,-5);
        vector1 = vector1.sub(vector2);
        assertEquals(5, vector1.getX(), 0.0001);
        assertEquals(0, vector1.getY(), 0.0001);
    }

    @Test
    public void ifVectorDivPositiveAndNegative_ThenReturnCorrectDivision() {
        vector1 = new Vector(1,1);
        vector1 = vector1.div(5);
        assertEquals(0.2, vector1.getX(), 0.0001);
        assertEquals(0.2, vector1.getY(), 0.0001);
    }

    @Test
    public void ifVectorDivNegative_ThenReturnCorrectDivision() {
        vector1 = new Vector(-5,-10);
        vector1 = vector1.div(-5);
        assertEquals(1, vector1.getX(), 0.0001);
        assertEquals(2, vector1.getY(), 0.0001);
    }
}

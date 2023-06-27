package com.game.chessmate.GameFiles;

/**
 * The type Vector.
 */
public class Vector {

    private double x;
    private double y;

    /**
     * Instantiates a new Vector.
     *
     * @param x the x
     * @param y the y
     */
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Addition of two vectors
     *
     * @param vector the vector
     * @return the vector
     */
    public Vector add(Vector vector) {
        return new Vector(this.x + vector.x, this.y + vector.y);
    }

    /**
     * Subtraction of two vectors
     *
     * @param vector the vector
     * @return the vector
     */
    public Vector sub(Vector vector) {
        return new Vector(this.x - vector.x, this.y - vector.y);
    }

    /**
     * Division of two vectors
     *
     * @param divisor the divisor
     * @return the vector
     */
    public Vector div(int divisor) {
        return new Vector(this.x / divisor, this.y / divisor);
    }


    /**
     * Gets x.
     *
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public double getY() {
        return y;
    }
}

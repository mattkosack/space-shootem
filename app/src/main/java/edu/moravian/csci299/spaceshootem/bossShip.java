package edu.moravian.csci299.spaceshootem;

import android.graphics.PointF;

public class bossShip extends Ship {
    /** Tracking for hits taken */
    private static final int NUM_HITS_ALLOWED = 20;
    private final int numHitsTaken = 0;

    /** Dimensions of layout, used to check bounds */
    private final int height;

    /**
     * @param initial
     * @param shipSize
     * @param width
     */
    public bossShip(PointF initial, float shipSize, int width, int height) {
        super(initial, shipSize, width);
        this.height = height;
    }


    /**
     * Set the positions away from the borders if they would be at (or beyond) them.
     * @param distanceForward the speed times dp distance to move the piece forward.
     */
    public void move(double distanceForward) {
        setPositionY(getPosition().y += distanceForward/4);
    }
}

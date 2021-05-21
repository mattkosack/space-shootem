package edu.moravian.csci299.spaceshootem;

import android.graphics.PointF;

import java.util.List;
import java.util.Random;

import static edu.moravian.csci299.spaceshootem.Util.withinRange;


public class EnemyShip extends Ship {

    /** Distances and direction the ships move left/right */
    private static final int MAX_DISTANCE = 10;
    private static final int MIN_DISTANCE = -10;

    /** Dimensions of layout, used to check bounds */
    private final int height;

    /**
     *
     * @param initial
     * @param shipSize
     * @param width
     * @param height
     */
    public EnemyShip(PointF initial, float shipSize, int width, int height) {
        super(initial, shipSize, width);
        this.height = height;
    }

    /**
     * Set the positions away from the borders if they would be at (or beyond) them.
     * @param distanceForward the speed times dp distance to move the piece forward.
     */
    public void move(double distanceForward) {
        double distance = (Math.random() * (MAX_DISTANCE - MIN_DISTANCE) + MIN_DISTANCE);
        if (super.getPosition().x + distance >= super.getWidth()) {
            super.setPositionX(getPosition().x - 1.0f);
        } else if (super.getPosition().x <= 0) {
            super.setPositionX(getPosition().x + 1.0f);
        } else {
            super.setPositionX(getPosition().x += distance);
        }
        setPositionY(getPosition().y += distanceForward/4);
    }

    /**
     * Checks if this ship is out of bounds
     * @return true if the ship is out of bounds
     */
    public boolean outOfBoundsY() { return (getPosition().y > height); }
}
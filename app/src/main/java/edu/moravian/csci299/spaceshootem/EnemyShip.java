package edu.moravian.csci299.spaceshootem;

import android.graphics.PointF;

import java.util.List;
import java.util.Random;

import static edu.moravian.csci299.spaceshootem.Util.withinRange;

/**
 * It's the same as a PlayerShip, but eventually there will be differences between how enemies move
 * or they can shoot or other abilities, so this is separated for future use, even though it
 * is not different yet.
 */
public class EnemyShip extends Ship {

    /** Direction to move ship */
    private final double[] directions = { -2.0, -1.0, 1.0, 2.0 };

    /** Random generator used for direction */
    Random rnd = new Random();

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


    public void move(double distance) {
        if (super.getPosition().x + distance >= super.getWidth() || super.getPosition().x <= 0) {
            super.setPositionX(getPosition().x);
        } else {
            super.setPositionX(getPosition().x += distance * directions[rnd.nextInt(directions.length)]);
        }
        setPositionY(getPosition().y += distance/4);
    }

    /**
     * Checks if this ship is out of bounds
     * @return true if the ship is out of bounds
     */
    public boolean outOfBoundsY() { return (getPosition().y > height); }


}
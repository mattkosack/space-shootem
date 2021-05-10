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
public class EnemyShip {

    /** Starting position of the player ship */
    private final PointF position;

    /** Direction to move ship */
    private final double[] directions = { -2.0, -1.0, 1.0, 2.0 };

    /** Random generator used for direction */
    Random rnd = new Random();

    /** The distance to be travelled. */
    private final double distXToTravel = 0.0;

    /** Dimensions of layout, used to check bounds */
    private final int width, height;

    /** Radius of each body piece in dp */
    private final float shipSize;

    /**
     * Create the ship with the given initial position.
     * @param initial the initial position
     */
    public EnemyShip(PointF initial, float shipSize, int width, int height) {
        this.position = initial;
        this.width = width;
        this.height = height;
        this.shipSize = shipSize;
    }

    /**
     * @return position of this ship
     */
    public PointF getPosition() {
        return position;
    }

    /**
     * Moves the player left or right.
     * @param distance how far to move the ship
     */
    public void move(double distance) {
        if (position.x + distance >= width || position.x <= 0) {
            position.x += 0;
        } else {
            position.x += distance * directions[rnd.nextInt(directions.length)];
        }
        position.y += distance / 4;
    }

    /**
     * Checks if this ship is out of bounds
     * @return true if the ship is out of bounds
     */
    public boolean enemyOutOfBoundsY() { return (getPosition().y > height); }

    /**
     * Checks if the enemy ship head intersects any of the given circular items.
     * @param locations the locations of the bullets
     * @param radius the radius of the bullet, in px
     * @return true if the ship intersections any of the given circular items
     */
    public boolean enemyIntersectsPlayerBullet(List<PointF> locations, float radius) {
        return anyWithinRange(locations, getPosition(), shipSize * SpaceGame.dpToPxFactor + radius);
    }

    /**
     * Checks if any point in the list is within range of a point.
     * @return true if withinRange(a, b, range) is true for any of the points in the list
     */
    private static boolean anyWithinRange(List<PointF> pts, PointF point, double range) {
        return pts.stream().anyMatch(pt -> withinRange(pt, point, range));
    }

}
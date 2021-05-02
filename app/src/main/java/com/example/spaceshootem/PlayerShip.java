package com.example.spaceshootem;

import android.graphics.PointF;
import android.util.Log;

import java.util.List;

import static com.example.spaceshootem.Util.*;

public class PlayerShip {

    /** Starting position of the player ship */
    private final PointF position;

    /** Dimensions of layout, used to check bounds */
    private final int width;

    /** Radius of each body piece in dp */
    private float shipSize;

    /**
     * Create the ship with the given initial position.
     * @param initial the initial position
     */
    public PlayerShip(PointF initial, float shipSize, int width) {
        this.position = initial;
        this.width = width;
        this.shipSize = shipSize;
    }

    /**
     * @return positions of the point of the ship
     */
    public PointF getPosition() {
        return position;
    }

    /**
     * Moves the player left or right.
     * @param isDirectionLeft the direction of movement, in radians
     */
    public void move(boolean isDirectionLeft, double distance) {
        if ((position.x + distance > width && isDirectionLeft) || (position.x - distance < 0 && !isDirectionLeft)) {
            position.x += 0;
        } else {
            position.x += distance * (isDirectionLeft ? 1 : -1);
        }
    }

    public boolean checkShipIntersectsEnemy(List<PointF> locations, float radius) {
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

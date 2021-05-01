package com.example.spaceshootem;


import android.graphics.PointF;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.spaceshootem.Util.*;

// TODO: I copy pasted from gravity snake, deleted some things, but didn't really change what I left.
public class PlayerShip {
    /** Radius of each body piece in dp */
    public final static float BODY_PIECE_SIZE_DP = 15f;

    /** Distance that is moved each actual movement, in dp */
    public final static float STEP_DISTANCE_DP = 2.5f;


    /**
     * The distance to be travelled.
     */
    private double distXToTravel = 0.0, distYToTravel = 0.0;


    /**
     * Converts dp to px
     */
    private final float dpToPxFactor;

    /**
     * Create the snake with the given initial position.
     * @param initial the initial position
     * @param dpToPxFactor the factor to convert dp to px
     */
    public PlayerShip(PointF initial, float dpToPxFactor) {
        body.add(initial);
        this.dpToPxFactor = dpToPxFactor;
    }

    /**
     * Moves the snake forward.
     * @param direction the direction of movement, in radians
     * @param distance the distance of the movement, in pixels
     */
    public void move(double direction, double distance) {
        // Update the distance to be travelled
        distXToTravel += Math.cos(direction) * distance;
        distYToTravel += Math.sin(direction) * distance;

        // Move the snake as much of the distance as possible
        final double stepDist = STEP_DISTANCE_DP * dpToPxFactor; // distance of each step
        double distTotal = Math.hypot(distYToTravel, distXToTravel); // total distance to travel
        if (distTotal >= stepDist) {
            double angle = Math.atan2(distYToTravel, distXToTravel); // angle to travel at
            double stepXDist = stepDist * Math.cos(angle); // step distance in X direction
            double stepYDist = stepDist * Math.sin(angle); // step distance in Y direction

            // Update the remaining distance
            distXToTravel = distTotal * Math.cos(angle);
            distYToTravel = distTotal * Math.cos(angle);
        }
    }




    // TODO: Not sure if the below functions will be useful. Could be for checking bullets
    /**
     * Checks if the snake head intersects any of the given circular items.
     * @param locations the locations of the items, in px
     * @param radius the radius of the items, in px
     * @return true if the snake intersections any of the given circular items
     */
    public boolean headIntersectsAnyItem(List<PointF> locations, float radius) {
        return anyWithinRange(locations, body.get(0), BODY_PIECE_SIZE_DP * dpToPxFactor + radius);
    }

    /**
     * Checks if any point in the list is within range of a point.
     * @return true if withinRange(a, b, range) is true for any of the points in the list
     */
    private static boolean anyWithinRange(List<PointF> pts, PointF point, double range) {
        return pts.stream().anyMatch(pt -> withinRange(pt, point, range));
        // Same as:
        //for (PointF pt : pts) { if (withinRange(pt, point, range)) { return true; } }
        //return false;
    }

    /**
     * Checks if any point in the list (after the first `skip` elements) is within range of a point.
     * @return true if withinRange(a, b, range) is true for any of the points in the list
     *         (after skipping).
     */
    private static boolean anyWithinRange(List<PointF> pts, PointF point, double range, int skip) {
        return pts.stream().skip(skip).anyMatch(pt -> withinRange(pt, point, range));
        // Same as:
        //for (int i = skip; i < pts.size(); i++) { if (withinRange(pts.get(i), point, range)) { return true; } }
        //return false;
    }

    public PointF getPosition() {
        return;
    }
}


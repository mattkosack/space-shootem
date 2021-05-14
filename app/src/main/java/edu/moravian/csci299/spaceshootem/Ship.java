package edu.moravian.csci299.spaceshootem;

import android.graphics.PointF;

import java.util.List;

public class Ship {
    /** Starting position of the player ship */
    private final PointF position;

    /** Dimensions of layout, used to check bounds */
    private final int width;

    /** Radius of each body piece in dp */
    private final float shipSize;

    /**
     *
     * @param initial
     * @param shipSize
     * @param width
     */
    public Ship(PointF initial, float shipSize, int width) {
        this.position = initial;
        this.width = width;
        this.shipSize = shipSize;
    }

    public PointF getPosition() {
        return position;
    }

    public int getWidth() { return width; }

    public void setPosition(float newPosX, float newPosY) {
        this.position.x = newPosX;
        this.position.y = newPosY;
    }

    public void setPositionX(float newPosX) { this.position.x = newPosX; }

    public void setPositionY(float newPosY) { this.position.y = newPosY; }

    public boolean checkShipIntersectsEnemy(List<PointF> locations, float radius) {
        return anyWithinRange(locations, getPosition(), shipSize * SpaceGame.dpToPxFactor + radius);
    }


    /**
     * Checks if any point in the list is within range of a point.
     * @return true if withinRange(a, b, range) is true for any of the points in the list
     */
    private static boolean anyWithinRange(List<PointF> pts, PointF point, double range) {
        return pts.stream().anyMatch(pt -> Util.withinRange(pt, point, range));
    }
}

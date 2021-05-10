package edu.moravian.csci299.spaceshootem;

import android.graphics.PointF;

import java.util.List;

import static edu.moravian.csci299.spaceshootem.Util.withinRange;

/**
 * All bullets will be the same size for now.
 * Eventually there may be different types of bullets.
 */
public class Bullet {
    private final PointF position;
    private boolean didHit;
    private float size;


    public Bullet(PointF position, float size) {
        this.position = position;
        this.didHit = false;
        this.size = size;
    }

    public void setDidHit() {
        this.didHit = true;
    }

    public boolean getDidHit() {
        return didHit;
    }

    public void setSize(float size1) {
        this.size = size1;
    }

    public void setPosition(float x, float y) {
        position.x = x;
        position.y = y;
    }

    public PointF getPosition() {
        return position;
    }

    public boolean checkOutOfBounds(int height) { return position.y > height; }

    public boolean checkShipIntersectsEnemy(List<PointF> locations, float radius) {
        return anyWithinRange(locations, getPosition(), size * SpaceGame.dpToPxFactor + radius);
    }

    /**
     * Checks if any point in the list is within range of a point.
     * @return true if withinRange(a, b, range) is true for any of the points in the list
     */
    private static boolean anyWithinRange(List<PointF> pts, PointF point, double range) {
        return pts.stream().anyMatch(pt -> withinRange(pt, point, range));
    }
}

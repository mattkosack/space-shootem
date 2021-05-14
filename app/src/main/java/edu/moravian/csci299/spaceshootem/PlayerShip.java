package edu.moravian.csci299.spaceshootem;

import android.graphics.PointF;

import java.util.List;

public class PlayerShip extends Ship {

    /**
     * Create the ship with the given initial position.
     * @param initial the initial position
     */
    public PlayerShip(PointF initial, float shipSize, int width) {
        super(initial, shipSize, width);
    }

    /**
     * Moves the player left or right.
     * @param isDirectionLeft the direction of movement
     */
    public void move(boolean isDirectionLeft, double distance) {
        if ((super.getPosition().x + distance > super.getWidth() && isDirectionLeft) || (super.getPosition().x - distance < 0 && !isDirectionLeft)) {
            super.setPositionX(getPosition().x);
        } else {
            super.setPositionX((getPosition().x += distance * (isDirectionLeft ? 1 : -1)));
        }
    }
}

package edu.moravian.csci299.spaceshootem;

import android.graphics.PointF;

import java.util.List;

public class PlayerShip extends Ship {


    /**
     *
     * @param initial
     * @param shipSize
     * @param width
     */
    public PlayerShip(PointF initial, float shipSize, int width) {
        super(initial, shipSize, width);
    }

    /**
     * Move the player piece based on direction and distance
     * @param isDirectionLeft whether the player should move left or right
     * @param distance the amount to move the player
     */
    public void move(boolean isDirectionLeft, double distance) {
        if ((super.getPosition().x + distance > super.getWidth() && isDirectionLeft) || (super.getPosition().x - distance < 0 && !isDirectionLeft)) {
            super.setPositionX(getPosition().x);
        } else {
            super.setPositionX((getPosition().x += distance * (isDirectionLeft ? 1 : -1)));
        }
    }
}

package com.example.spaceshootem;


import android.graphics.PointF;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.example.spaceshootem.Util.withinRange;

public class SpaceGame {
    /** The one and only random number generator for any game */
    private final static Random random = new Random();

    /** Touch "radius" in dp */
    public final static float TOUCH_SIZE_DP = 5;

    /** The width and height of the game, in px */
    private int width, height;

    /** If the game is over */
    private boolean gameOver = true;

    /** The player moving around the game */
    private PlayerShip player;

    /** The direction the player is moving  */
    private double direction; //TODO: should only be left or right, so may not need to be a double

    /** Initial speed of the snake, in dp/frame */
    private double initialSpeed = 2.5;

    /** Speed of the snake, in dp/frame */
    private double speed = 2.5;

    /** Number of enemies killed (i.e. the score) */
    private int score = 0;

    /** Locations of all of the enemy ships, each in px */
    private final List<PointF> enemies = new ArrayList<>();

    /** Converts dp to px */
    private float dpToPxFactor = 1f;

    /**
     * @return true if the game has not yet been started ever
     */
    public boolean hasNotStarted() { return player == null; }

    /**
     * @return the current score (number of foods eaten)
     */
    public int getScore() { return score; }

    /**
     * Set the factor for converting dp measurements to px. This is the size of
     * 1 dp in pixels.
     * @param dpToPxFactor the conversion factor to go from dp to px
     */
    public void setDpToPxFactor(float dpToPxFactor) {
        this.dpToPxFactor = dpToPxFactor;
    }

    /**
     * Start the game. Can also be used to start a new game if one has already begun.
     * @param width the width of the playing area in px
     * @param height the height of the playing area in px
     */
    public void startGame(int width, int height) {
        this.width = width;
        this.height = height;
//        player = new PlayerShip(); // TODO:
        score = 0;
        enemies.clear();
        gameOver = false;
    }

    /**
     * Get the status of the game. The game is over if no game has ever been
     * started or if the player has died and a new game has not yet started.
     * @return true if the game is over, false otherwise
     */
    public boolean isGameOver() { return gameOver; }

    /**
     * Sets the direction that the snake will move in the future.
     * @param angle the new direction of the snake, in radians, 0 is straight
     *              right along the positive X axis, pi/2 is straight down along
     *              the positive Y axis
     */
    public void setMovementDirection(double angle) { direction = angle; } //TODO: only needs to be left or right


    public boolean update() {
        if (gameOver) { return false; }

        // Move player ship
        player.move(direction, speed * dpToPxFactor);

        // Check if player was hit by enemy bullet
//         TODO:
//        if () {
//            gameOver = true;
//            return false;
//        }

        // Check if player shot an enemy ship
//         TODO:
//        if () {
//            score++;
//        }

        // if all the enemies are gone, spawn in new ones?
        // Not sure how we want to handle levels or something
//        TODO

        return true;
    }

    /**
     * If the screen is touched, the playership will fire
     * @return true if the game is still going, false if the game is now over
     */
    public boolean touched() {
        if (gameOver) { return false; }

//        TODO
        // Shoot bullet from player

        return true;
    }


    public PointF getPlayerLocation() {
        return player.getPosition();
    }

    public PointF[] getEnemyLocations() {
    }
}

package com.example.spaceshootem;


import android.graphics.PointF;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static com.example.spaceshootem.Util.withinRange;

public class SpaceGame {
    /** The one and only random number generator for any game */
    private final static Random random = new Random();

    /** Radius of each body piece in dp */
    public final static float BULLET_PIECE_SIZE_DP = 10f;

    /** Enemy Ship radius */
    private static final float ENEMY_SIZE_DP = 20f;

    /** Player Ship radius*/
    public static final float PLAYER_SIZE_DP = 20f;

    /** The width and height of the game, in px */
    private int width, height;

    /** If the game is over */
    private boolean gameOver = true;

    /** The player moving around the game */
    private PlayerShip player;

    /** Locations of enemy bullets, in px */
    private List<Bullet> playerBulletLocations = new ArrayList<>();

    /** The direction the player is moving  */
    private boolean isDirectionLeft;

    /** Speed of the player ship, in dp/frame */
    private double playerSpeed = 1.0;

    /** Number of enemies killed (i.e. the score) */
    private int score = 0;

    /** Round number (new round starts when all enemies are gone) */
    private int round = 0;

    /** Number of enemies to spawn initially */
    private int startingNumberOfEnemies;

    /** Locations of all of the enemy ships, each in px */
    private final List<EnemyShip> enemies = new ArrayList<>();

    /** Speed of the enemy ship, in dp/frame */
    private double enemySpeed = 1.5;

    /** Converts dp to px */
    public static float dpToPxFactor = 1f;

    /**
     * @return true if the game has not yet been started ever
     */
    public boolean hasNotStarted() { return player == null; }

    /**
     * @return the current score (number of foods eaten)
     */
    public int getScore() { return score; }

    /**
     * @return bullet size in dp
     */
    public float getBulletSizeDp() { return BULLET_PIECE_SIZE_DP; }

    /**
     * @return enemy ship size in dp
     */
    public float getEnemySizeDp() { return ENEMY_SIZE_DP; }

    /**
     * @return player ship size in dp
     */
    public float getPlayerSizeDp() { return PLAYER_SIZE_DP; }

    /**
     * Set the factor for converting dp measurements to px. This is the size of
     * 1 dp in pixels.
     * @param dpToPxFactor the conversion factor to go from dp to px
     */
    public void setDpToPxFactor(float dpToPxFactor) {
        SpaceGame.dpToPxFactor = dpToPxFactor;
    }

    /**
     * Start the game. Can also be used to start a new game if one has already begun.
     * @param width the width of the playing area in px
     * @param height the height of the playing area in px
     */
    public void startGame(int width, int height) {
        this.width = width;
        this.height = height;
        player = new PlayerShip(new PointF(width / 1.25f, height / 1.25f), PLAYER_SIZE_DP, width);
        score = 0;
        enemies.clear();
        spawnEnemies(startingNumberOfEnemies + 5 * round);
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
     * @param isLeft if the direction to move is left
     */
    public void setMovementDirection(boolean isLeft) {
        isDirectionLeft = isLeft;
    }

    /**
     * Update the piece statuses
     * @return true if the game isn't over
     */
    public boolean update() {
        if (gameOver || player.checkShipIntersectsEnemy(getEnemyLocations(), ENEMY_SIZE_DP)) { return false; }

        // Move player ship
        player.move(isDirectionLeft, playerSpeed * dpToPxFactor);

        // Move enemy ships
        for (EnemyShip enemy : enemies) {
            enemy.move(enemySpeed * dpToPxFactor);
        }

        // Move the bullets
        for (Bullet bullet : playerBulletLocations) {
            bullet.setPosition(bullet.getPosition().x, bullet.getPosition().y - 4);
        }

        // Set the bullet to true if it hit an enemy
        for (Bullet bullet : playerBulletLocations) {
            if (bullet.checkShipIntersectsEnemy(getEnemyLocations(), ENEMY_SIZE_DP * dpToPxFactor)) {
                bullet.setDidHit();
            }
        }

        // Remove hit enemies
        Iterator<EnemyShip> enemyShipIterator = enemies.iterator();
        while (enemyShipIterator.hasNext()) {
            EnemyShip enemy = enemyShipIterator.next();
            if (enemy.enemyIntersectsPlayerBullet(getPlayerBulletLocations(), BULLET_PIECE_SIZE_DP * dpToPxFactor)) {
                enemyShipIterator.remove();
                score++;
            }
            if (enemy.enemyOutOfBoundsY()) {
                enemyShipIterator.remove();
                score--;
            }
        }

        // Remove the bullets
        Iterator<Bullet> bulletIterator = playerBulletLocations.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            if (bullet.getDidHit() || bullet.checkOutOfBounds(height)) {
                bulletIterator.remove();
            }
        }

        // if all the enemies are gone, spawn in new ones
        if (enemies.size() == 0) {
            round++;
            spawnEnemies(startingNumberOfEnemies + 5 * round);
        }
        return true;
    }

    /**
     * If the screen is touched, the player ship will fire
     * @return true if the game is still going, false if the game is now over
     */
    public boolean touched() {
        if (gameOver) { return false; }
        playerBulletLocations.add(new Bullet(new PointF(player.getPosition().x + 0.1f, getPlayerLocation().y + 0.1f), BULLET_PIECE_SIZE_DP));
        return true;
    }

    /**
     * get the player point
     * @return PointF of the player location
     */
    public PointF getPlayerLocation() {
        return player.getPosition();
    }

    /**
     * Get all enemy locations as an array of points
     * @return array of pointf
     */
    public List<PointF> getEnemyLocations() {
        List<PointF> enemyLocations = new ArrayList<>();
        for (EnemyShip enemy : enemies) {
            enemyLocations.add(new PointF(enemy.getPosition().x, enemy.getPosition().y));
        }
        return enemyLocations;
    }

    /**
     * Get all player bullet locations as an array of points
     * @return array of pointf
     */
    public List<PointF> getPlayerBulletLocations() {
        List<PointF> bulletLocations = new ArrayList<>();
        for (Bullet bullet : playerBulletLocations) {
            bulletLocations.add(new PointF(bullet.getPosition().x, bullet.getPosition().y));
        }
        return bulletLocations;
    }

    /**
     * Sets the number of enemies to initially spawn
     * @param numEnemies the number of enemies to spawn
     */
    public void setStartingNumberOfEnemies(int numEnemies) { startingNumberOfEnemies = numEnemies; }


    /**
     * Adds the enemies to the list
     * @param numEnemies the number of enemies to add
     */
    public void spawnEnemies(int numEnemies) {
        for (int i=0; i < numEnemies; i++) {
            float randX = 1F + random.nextFloat() * (width - 1F);
            float randY = 1F + random.nextFloat() * (height/2F - 1F);
            enemies.add(new EnemyShip(new PointF(randX, randY), ENEMY_SIZE_DP, width, height));
        }
    }
}

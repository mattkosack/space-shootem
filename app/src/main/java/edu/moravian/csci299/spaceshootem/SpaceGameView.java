package edu.moravian.csci299.spaceshootem;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class SpaceGameView extends View implements SensorEventListener {
    /** The paints and drawables used for the different parts of the game */
    private final Paint scorePaint = new Paint();
    private final Paint playerPaint = new Paint();
    private final Paint playerBulletPaint = new Paint();
    private final Paint enemyPaint = new Paint();

    // The game's difficulty; default to easy
    private String difficulty = "Easy";

    // Rectangle for calculating width and height of text
    private final Rect bounds = new Rect();

    /** The metrics about the display to convert from dp and sp to px */
    private final DisplayMetrics displayMetrics;

    /** The snake game for the logic behind this view */
    private final SpaceGame spaceGame;

    /** The difficulty settings. Each index corresponds to difficulty (i.e. index 0 is easy) */
    private final int[] STARTING_ENEMIES = { 10, 20, 30, 40, 50 };

    // Required constructors for making your own view that can be placed in a layout
    public SpaceGameView(Context context) { this(context, null);  }
    public SpaceGameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // Get the metrics for the display so we can later convert between dp, sp, and px
        displayMetrics = context.getResources().getDisplayMetrics();

        // Make the game
        spaceGame = new SpaceGame();

        // This color is automatically painted as the background
        setBackgroundColor(0xFF333333);

        // Setup all of the paints and drawables used for drawing later
        scorePaint.setColor(Color.WHITE);
        scorePaint.setAntiAlias(true);
        scorePaint.setTextAlign(Paint.Align.CENTER);
        scorePaint.setTextSize(spToPx(24)); // use sp for text
        scorePaint.setFakeBoldText(true);
    }

    /**
     * @return the space game for this view
     */
    public SpaceGame getSpaceGame() { return spaceGame; }

    /**
     * Utility function to convert dp units to px units. All Canvas and Paint
     * function use numbers in px units but dp units are better for
     * inter-device support.
     * @param dp the size in dp (device-independent-pixels)
     * @return the size in px (pixels)
     */
    public float dpToPx(float dp) { return dp * displayMetrics.density; }

    /**
     * Utility function to convert sp units to px units. All Canvas and Paint
     * function use numbers in px units but sp units are better for
     * inter-device support, especially for text.
     * @param sp the size in sp (scalable-pixels)
     * @return the size in px (pixels)
     */
    public float spToPx(float sp) { return sp * displayMetrics.scaledDensity; }

    /**
     * @param difficulty the index of the difficulty
     * @param difficultyName the string name of the index
     */
    public void setDifficulty(int difficulty, String difficultyName) {
        spaceGame.setStartingNumberOfEnemies(STARTING_ENEMIES[difficulty]);
        this.difficulty = difficultyName;
    }

    /**
     * Sets the player's ship color
     * @param color the color to change to
     */
    public void setPlayerPaint(String color) { playerPaint.setColor(Color.parseColor(color)); }

    /**
     * Sets the enemy ship color
     * @param color the color to change to
     */
    public void setEnemyPaint(String color) { enemyPaint.setColor(Color.parseColor(color)); }

    /**
     * Sets the bullet color
     * @param color the color to change to
     */
    public void setBulletPaint(String color) { playerBulletPaint.setColor(Color.parseColor(color)); }


    /**
     * Once the view is laid out, we know the dimensions of it and can start
     * the game with the snake in the middle (if the game hasn't already
     * started). We also take this time to set the dp to px factor of the
     * snake.
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (spaceGame.hasNotStarted()) {
            spaceGame.startGame(right - left, bottom - top);
            spaceGame.setDpToPxFactor(displayMetrics.density);
        }
        invalidate();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (spaceGame.isGameOver()) {
            if (getContext() instanceof Activity) {
                ((Activity) getContext()).finish();
            }
        }
        spaceGame.touched();
        invalidate();
        return spaceGame.touched();
    }

    /**
     * Draws all of the pieces
     * @param canvas the canvas to draw on
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        postInvalidateOnAnimation(); // automatically invalidate every frame so we get continuous playback

        spaceGame.update();

        // Draw the Player
        PointF player = spaceGame.getPlayerLocation();
        canvas.drawCircle(player.x, player.y, spaceGame.getPlayerSizeDp(), playerPaint);

        // Draw the enemies
        for (PointF enemyShip : spaceGame.getEnemyLocations()) {
            canvas.drawCircle(enemyShip.x, enemyShip.y, spaceGame.getEnemySizeDp(), enemyPaint);
        }

        // Draw the player bullets
        for (PointF bullet : spaceGame.getPlayerBulletLocations()) {
            canvas.drawCircle(bullet.x, bullet.y, SpaceGame.BULLET_PIECE_SIZE_DP, playerBulletPaint);
        }

        // Draw score
        String text = Integer.toString(spaceGame.getScore());
        scorePaint.getTextBounds(text, 0, text.length(), bounds);
        float x = (getWidth() - scorePaint.measureText(text) + bounds.width()) / 2f;
        canvas.drawText(text, x, 2*bounds.height(), scorePaint);
    }

    /**
     * Change movement direction based on sensor
     * @param event the sensor event
     */
    @Override
    public void onSensorChanged(SensorEvent event) { spaceGame.setMovementDirection(event.values[0] < 0.0); }


    /** Does nothing but must be provided. */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}


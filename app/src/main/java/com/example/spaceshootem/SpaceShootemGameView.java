package com.example.spaceshootem;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;


// TODO: THIS IS JUST THE SNAKEGAMEVIEW FROM PROJECT 2
/**
 * The custom View for the Snake Game. This handles the user interaction and
 * sensor information for the snake game but has none of the game logic. That
 * is all within SnakeGame and Snake.
 *
 * NOTE: This class is where most of the work is required. You must document
 * *all* methods besides the constructors (this includes methods already
 * declared that don't have documentation). You will also need to add at least
 * a few methods to this class.
 */
public class SpaceShootemGameView extends View implements SensorEventListener {
    /** The paints and drawables used for the different parts of the game */
    private final Paint scorePaint = new Paint();
    private final Paint snakePaint = new Paint();
    private final Paint wallPaint = new Paint();
    private final Paint foodPaint = new Paint();

    // The game's difficulty; default to easy
    private String difficulty = "Easy";
    // Rectangle for calculating width and height of text
    private final Rect bounds = new Rect();
    // Whether the highscore was saved or not
    private boolean isHighscoreSaved = false;

    /** The metrics about the display to convert from dp and sp to px */
    private final DisplayMetrics displayMetrics;

    /** The snake game for the logic behind this view */
    private final SpaceShootemGame snakeGame;

    /** The difficulty settings. Each index corresponds to difficulty (i.e. index 0 is easy) */
    private final int[] LENGTH_INCREASES_PER_FOOD = { 8, 10, 13, 15, 20 };
    private final int[] STARTING_LENGTHS = { 25, 35, 45, 55, 65 };
    private final double[] INITIAL_SPEED = { 1.0, 1.2, 1.4, 1.6, 2 };
    private final double[] SPEED_INCREASE_PER_FOOD = { 0.1, 0.1, 0.125, 0.15, 0.4 };
    private final double[] WALL_PLACEMENT_PROBABILITIES = { 0.005, 0.02, 0.04, 0.06, 0.1 };

    // Required constructors for making your own view that can be placed in a layout
    public SnakeGameView(Context context) { this(context, null);  }
    public SnakeGameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // Get the metrics for the display so we can later convert between dp, sp, and px
        displayMetrics = context.getResources().getDisplayMetrics();

        // Make the game
        snakeGame = new SpaceShootemGame();

        // This color is automatically painted as the background
        setBackgroundColor(0xFF333333);

        // Setup all of the paints and drawables used for drawing later
        scorePaint.setColor(Color.WHITE);
        scorePaint.setAntiAlias(true);
        scorePaint.setTextAlign(Paint.Align.CENTER);
        scorePaint.setTextSize(spToPx(24)); // use sp for text
        scorePaint.setFakeBoldText(true);

        snakePaint.setColor(Color.GREEN);
        wallPaint.setColor(Color.BLACK);
        foodPaint.setColor(Color.RED);
    }

    /**
     * @return the snake game for this view
     */
    public SpaceShootemGame getSnakeGame() { return snakeGame; }

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
     * @param difficulty the new difficulty for the game
     */
    public void setDifficulty(int difficulty, String difficultyName) {
        snakeGame.setLengthIncreasePerFood(LENGTH_INCREASES_PER_FOOD[difficulty]);
        snakeGame.setStartingLength(STARTING_LENGTHS[difficulty]);
        snakeGame.setInitialSpeed(INITIAL_SPEED[difficulty]);
        snakeGame.setSpeedIncreasePerFood(SPEED_INCREASE_PER_FOOD[difficulty]);
        snakeGame.setWallPlacementProbability(WALL_PLACEMENT_PROBABILITIES[difficulty]);

        this.difficulty = difficultyName;
    }

    /**
     * Once the view is laid out, we know the dimensions of it and can start
     * the game with the snake in the middle (if the game hasn't already
     * started). We also take this time to set the dp to px factor of the
     * snake.
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        // NOTE: this function is done for you
        super.onLayout(changed, left, top, right, bottom);
        if (snakeGame.hasNotStarted()) {
            snakeGame.startGame(right - left, bottom - top);
            snakeGame.setDpToPxFactor(displayMetrics.density);
        }
        invalidate();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (snakeGame.isGameOver()) {
            if (getContext() instanceof Activity) {
                ((Activity) getContext()).finish();
            }
        }
        PointF pf = new PointF(event.getX(), event.getY());
        snakeGame.touched(pf);
        invalidate();
        return snakeGame.touched(pf);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        postInvalidateOnAnimation(); // automatically invalidate every frame so we get continuous playback

        if (snakeGame.isGameOver()) {
            if (!isHighscoreSaved) {
                isHighscoreSaved = true;
                Context context = getContext();
                String sharedPrefFile = context.getString(R.string.preference_file);
                SharedPreferences preferences = context.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
                int previous_highscore = preferences.getInt(difficulty, 0);
                if (snakeGame.getScore() > previous_highscore) {
                    SharedPreferences.Editor preferencesEditor = preferences.edit();
                    preferencesEditor.putInt(difficulty, snakeGame.getScore());
                    preferencesEditor.apply();
                }
            }
            String text = getContext().getString(R.string.game_over);
            scorePaint.getTextBounds(text, 0, text.length(), bounds);
            float x = (getWidth() - scorePaint.measureText(text) + bounds.width()) / 2f;
            canvas.drawText(text, x, getHeight() / 2f, scorePaint);
            return;
        }

        snakeGame.update();

        // Draw the snake
        for (PointF pt : snakeGame.getSnakeBodyLocations()) { canvas.drawCircle(pt.x, pt.y, Snake.BODY_PIECE_SIZE_DP, snakePaint); }

        // Draw the walls
        for (PointF pt : snakeGame.getWallLocations()) { canvas.drawCircle(pt.x, pt.y, Snake.BODY_PIECE_SIZE_DP, wallPaint); }

        // Draw the food
        canvas.drawCircle(snakeGame.getFoodLocation().x, snakeGame.getFoodLocation().y, Snake.BODY_PIECE_SIZE_DP, foodPaint);

        // Draw score
        String text = Integer.toString(snakeGame.getScore());
        scorePaint.getTextBounds(text, 0, text.length(), bounds);
        float x = (getWidth() - scorePaint.measureText(text) + bounds.width()) / 2f;
        canvas.drawText(text, x, 2*bounds.height(), scorePaint);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] acceleration = event.values;
        snakeGame.setMovementDirection(Math.atan2(acceleration[1], -acceleration[0]));
    }

    /** Does nothing but must be provided. */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

}


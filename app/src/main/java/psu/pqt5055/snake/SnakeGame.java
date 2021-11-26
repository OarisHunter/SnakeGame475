package psu.pqt5055.snake;

import android.content.Context;

import android.util.Log;
import android.widget.GridLayout;

public class SnakeGame implements Runnable {
    MainActivity mainActivity;
    GridLayout mGameBoard;

    // reference vars
    private final String TAG = "SnakeGame";
    private Thread thread = null;

    // game vars
    private int grid_size;
    private long nextFrameTime;
    private final long FPS = 10;
    private final long MILLIS_PER_SECOND = 1000;

    // snake vars
    public enum Heading {UP, RIGHT, DOWN, LEFT}
    private Heading heading = Heading.UP;
    private int[] snakeX;
    private int[] snakeY;
    private int snakeLength;

    // fruit vars
    private int fruitX;
    private int fruitY;

    // player vars
    private int score;
    private boolean isPlaying;

    public SnakeGame(MainActivity mainActivity, int grid_size) {
        this.mainActivity = mainActivity;
        snakeX = new int[grid_size * grid_size];
        snakeY = new int[grid_size * grid_size];
        this.grid_size = grid_size;
    }

    public void newGame(int headX, int headY, int snakeLength) {
        this.snakeLength = snakeLength;

        for (int i = 0; i < snakeLength; i++) {
            snakeX[i] = headX;
            snakeY[i] = headY + i;
        }

        score = 0;

        // TODO: spawn fruit

        nextFrameTime = System.currentTimeMillis();
    }

    private void updateSnake() {
        // start at tail and work towards head
        for (int i = snakeLength; i > 0; i--) {
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }

        switch (heading) {
            case UP:
                snakeY[0]--;
                break;
            case DOWN:
                snakeY[0]++;
                break;
            case LEFT:
                snakeX[0]--;
                break;
            case RIGHT:
                snakeX[0]++;
                break;
        }
    }

    public void update() {
        // TODO: check for fruit collision

        updateSnake();

        // TODO: Check for death
    }

    public boolean updateRequired() {
        if (nextFrameTime <= System.currentTimeMillis()) {
            nextFrameTime = System.currentTimeMillis() + MILLIS_PER_SECOND / FPS;

            return true;
        }
        return false;
    }

    @Override
    public void run() {
        while (true) {
            if (updateRequired()) {
                update();
                mainActivity.updateBoard();
            }
        }
    }

    public void pause() {
        isPlaying = false;
        try {
            thread.join();
        }
        catch (InterruptedException e) {
            Log.e(TAG, "Error in pause()");
        }
    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public Heading getHeading() {
        return heading;
    }

    public void setHeading(Heading heading) {
        this.heading = heading;
    }

    public int[] getSnakeX() {
        return snakeX;
    }

    public void setSnakeX(int[] snakeX) {
        this.snakeX = snakeX;
    }

    public int[] getSnakeY() {
        return snakeY;
    }

    public void setSnakeY(int[] snakeY) {
        this.snakeY = snakeY;
    }

    public int getSnakeLength() {
        return snakeLength;
    }

    public void setSnakeLength(int snakeLength) {
        this.snakeLength = snakeLength;
    }

    public int getFruitX() {
        return fruitX;
    }

    public void setFruitX(int fruitX) {
        this.fruitX = fruitX;
    }

    public int getFruitY() {
        return fruitY;
    }

    public void setFruitY(int fruitY) {
        this.fruitY = fruitY;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
}
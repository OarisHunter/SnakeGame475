package psu.pqt5055.snake;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import java.util.Random;

public class SnakeGame implements Runnable {
    // reference vars
    private final String TAG = "SnakeGame";
    private Thread thread = null;
    private final GameFragment parentActivity;
    private final Random rand = new Random();

    // game vars
    private final int grid_size;
    private long nextFrameTime;
    private final long game_speed = 5;
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

    public SnakeGame(GameFragment parentActivity, int grid_size) {
        this.parentActivity = parentActivity;
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
        spawnFruit();

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

    public boolean checkFatalCollision() {
        boolean isDead = false;

        int headX = snakeX[0];
        int headY = snakeY[0];
        // Check bounds collision
        if (headX <= 1) {
            isDead = true;
        }
        if (headX >= grid_size) {
            isDead = true;
        }
        if (headY <= 1) {
            isDead = true;
        }
        if (headY >= grid_size) {
            isDead = true;
        }

        // Check snake collision
        for (int i = snakeLength; i > 0; i--) {
            if ((i > 4) && (snakeX[i] == headX && snakeY[i] == headY)) {
                isDead = true;
                break;
            }
        }

        if (isDead) Log.i(TAG, "Snake Died");
        return isDead;
    }
    
    public void spawnFruit() {
        // Generate new fruit coordinates
        fruitX = rand.nextInt((grid_size - 3) + 1) + 2;
        fruitY = rand.nextInt((grid_size - 3) + 1) + 2;

        boolean inSnake = false;
        for (int i = snakeLength; i >= 0; i--) {
            if (snakeX[i] == fruitX && snakeY[i] == fruitY) {
                inSnake = true;
                break;
            }
        }
        if (inSnake) {
            spawnFruit();
        }
    }

    public void checkFruitCollision() {
        int headX = snakeX[0];
        int headY = snakeY[0];

        if (headX == fruitX && headY == fruitY) {
            snakeLength++;
            score++;
            spawnFruit();
        }
    }

    public void update() {
        // TODO: check for fruit collision
        checkFruitCollision();

        updateSnake();

        // TODO: Check for death
        if (checkFatalCollision()) {
            isPlaying = false;
        }
    }

    public boolean updateRequired() {
        if (nextFrameTime <= System.currentTimeMillis()) {
            nextFrameTime = System.currentTimeMillis() + MILLIS_PER_SECOND / game_speed;

            return true;
        }
        return false;
    }

    @Override
    public void run() {
        while (isPlaying) {
            if (updateRequired()) {
                update();
                parentActivity.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        parentActivity.updateBoard();
                    }
                });
            }
        }
        parentActivity.endGame();
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
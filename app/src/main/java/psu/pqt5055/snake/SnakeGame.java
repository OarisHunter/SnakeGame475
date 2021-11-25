package psu.pqt5055.snake;

public class SnakeGame {

    private final int GRID_SIZE;

    private final int[][] mGameGrid;

    private int headX;
    private int headY;
    private int tailX;
    private int tailY;

    private int headDirection;
    private int tailDirection;
    private int snakeLength;

    private int score;

    public SnakeGame(int grid_size) {
        GRID_SIZE = grid_size;
        mGameGrid = new int[grid_size][grid_size];
    }

    public void newGame(int startPosX, int startPosY, int endPosX, int endPosY, int startLen) {
        // Initialize Game Board
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                mGameGrid[row][col] = 0;
            }
        }
        // Create boarder
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                mGameGrid[0][j] = 1;
                mGameGrid[GRID_SIZE - 1][j] = 1;
            }
            mGameGrid[i][0] = 1;
            mGameGrid[i][GRID_SIZE - 1] = 1;

        }
        // Initialize Values
        headX = startPosX;
        headY = startPosY;
        tailX = endPosX;
        tailY = endPosY;
        headDirection = 0;
        tailDirection = 0;
        snakeLength = startLen;
        score = 0;
        // Initialize snake position
        mGameGrid[headX][headY] = 2;
        for (int row = 1; row < startLen; row++) {
            mGameGrid[row][headY] = 3;
        }
    }
}

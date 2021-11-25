package psu.pqt5055.snake;

public class SnakeGame {

    private final int GRID_SIZE;

    private final int[][] mGameGrid;

    public SnakeGame(int grid_size) {
        GRID_SIZE = grid_size;
        mGameGrid = new int[grid_size][grid_size];
    }

    public void newGame(int startPosX, int startPosY, int endPosX, int endPosY) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                mGameGrid[row][col] = 0;
            }
        }
    }
}

package psu.pqt5055.snake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridLayout;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private final int board_start_id = 1000;
    private final int game_board_size = 21;

    private final int startPosX = game_board_size / 2 + 1;
    private final int startPosY = game_board_size / 2 + 1;
    private final int startLen = game_board_size / 4;

    private int mBoardColor;
    private int mBorderColor;
    private int mSnakeColor;
    private int mAppleColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBoardColor = ContextCompat.getColor(this, R.color.black);
        mBorderColor = ContextCompat.getColor(this, R.color.white);
        mSnakeColor = ContextCompat.getColor(this, R.color.green_m);
        mAppleColor = ContextCompat.getColor(this, R.color.red_2);

        createBoard();
        createBorder();

        SnakeGame game = new SnakeGame(game_board_size);

        drawStartSnake();

        game.newGame(startPosX, startPosY, startPosX+startLen-1, startPosY+startLen-1, startLen);
    }

    protected void createBoard() {
        int height = (int) this.getResources().getDimension(R.dimen.board_pixel_height);
        int width = (int) this.getResources().getDimension(R.dimen.board_pixel_width);

        GridLayout layout = findViewById(R.id.game_grid);
        layout.setRowCount(game_board_size);
        layout.setColumnCount(game_board_size);;

        for (int col = 1; col <= game_board_size; col++) {
            for (int row = 1; row <= game_board_size; row++) {
                View pixel = new View(this);
                pixel.setId(convertCoords(row, col));
                pixel.setLayoutParams(new LayoutParams(width, height));
                pixel.setBackgroundColor(mBoardColor);

                layout.addView(pixel);
            }
        }
    }

    protected void createBorder() {
        for (int i = 1; i <= game_board_size; i++) {
            for (int j = 1; j <= game_board_size; j++) {
                drawPixel(1, j, mBorderColor);
                drawPixel(game_board_size, j, mBorderColor);
            }
            drawPixel(i, 1, mBorderColor);
            drawPixel(i, game_board_size, mBorderColor);

        }
    }

    public void onControlButtonClick(View view) {

    }

    protected void drawStartSnake() {
        for (int i = 0; i < startLen; i++) {
            drawPixel(startPosX, startPosY + i, mSnakeColor);
        }
    }

    public void updateSnakeHead(int x, int y) {
        drawPixel(x, y, mSnakeColor);
    }

    public void updateSnakeTail(int x, int y) {
        drawPixel(x, y, mBoardColor);
    }

    protected void drawPixel(int x, int y, int color) {
        View pixel = findViewById(convertCoords(x, y));
        pixel.setBackgroundColor(color);
    }

    protected int convertCoords(int x, int y) {
        return (board_start_id + x) * y;
    }
}
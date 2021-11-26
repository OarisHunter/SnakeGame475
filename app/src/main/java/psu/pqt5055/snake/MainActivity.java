package psu.pqt5055.snake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.nfc.Tag;
import android.os.Bundle;
import android.os.HandlerThread;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridLayout;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private Thread mGameThread;
    private MainActivity instance;

    private SnakeGame mGame;
    private GridLayout mGameBoard;
    private GridLayout mGameButtons;

    private final int board_start_id = 1000;
    private final int game_board_size = 21;
    private final int startPosX = 11;
    private final int startPosY = 14;
    private final int startLen = 3;

    private int mBoardColor;
    private int mBorderColor;
    private int mSnakeColor;
    private int mAppleColor;

    private boolean mGameRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;

        mBoardColor = ContextCompat.getColor(this, R.color.black);
        mBorderColor = ContextCompat.getColor(this, R.color.white);
        mSnakeColor = ContextCompat.getColor(this, R.color.green_m);
        mAppleColor = ContextCompat.getColor(this, R.color.red_2);

        initializeGame();
    }

    protected void initializeGame() {
        mGameBoard = findViewById(R.id.game_grid);
        mGameButtons = findViewById(R.id.game_controls);
        mGame = new SnakeGame(getInstance(), game_board_size);
        createBoard();
        createBorder();
        mGameRunning = false;
    }

    protected void startGame() {
        mGame.newGame(startPosX, startPosY, startLen);
        mGameThread = new Thread(mGame);

        mGameThread.start();
    }

    public void updateBoard() {
        updateSnake();
    }

    protected void createBoard() {
        int height = (int) this.getResources().getDimension(R.dimen.board_pixel_height);
        int width = (int) this.getResources().getDimension(R.dimen.board_pixel_width);

        mGameBoard.setRowCount(game_board_size);
        mGameBoard.setColumnCount(game_board_size);;

        for (int col = 1; col <= game_board_size; col++) {
            for (int row = 1; row <= game_board_size; row++) {
                View pixel = new View(this);
                pixel.setId(convertCoords(row, col));
                pixel.setLayoutParams(new LayoutParams(width, height));
                pixel.setBackgroundColor(mBoardColor);

                mGameBoard.addView(pixel);
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
        int buttonIndex = mGameButtons.indexOfChild(view);
        int direction = buttonIndex / 2;

        if (direction == 0) {
            mGame.setHeading(SnakeGame.Heading.UP);
        }
        else if (direction == 1) {
            mGame.setHeading(SnakeGame.Heading.LEFT);
        }
        else if (direction == 2) {
            mGame.setHeading(SnakeGame.Heading.RIGHT);
        }
        else if (direction == 3) {
            mGame.setHeading(SnakeGame.Heading.DOWN);
        }
    }

    public void onStartButtonClick(View view) {
        view.setVisibility(View.INVISIBLE);
        findViewById(R.id.game_controls).setVisibility(View.VISIBLE);
        findViewById(R.id.resetgame_button).setVisibility(View.VISIBLE);

        drawStartSnake();

        startGame();
    }

    public void onResetButtonClick(View view) {
        view.setVisibility(View.INVISIBLE);
        findViewById(R.id.game_controls).setVisibility(View.INVISIBLE);
        findViewById(R.id.startgame_button).setVisibility(View.VISIBLE);

        mGameRunning = false;

        mGameBoard.removeAllViews();
        initializeGame();
    }

    protected void drawStartSnake() {
        for (int i = 0; i < startLen; i++) {
            drawPixel(startPosX, startPosY + i, mSnakeColor);
        }
    }

    public void updateSnake() {
        int[] snakeX = mGame.getSnakeX();
        int[] snakeY = mGame.getSnakeY();

        drawPixel(snakeX[0], snakeY[0], mSnakeColor);
    }

    protected void drawPixel(int x, int y, int color) {
        View pixel = findViewById(convertCoords(x, y));
        pixel.setBackgroundColor(color);
    }

    protected int convertCoords(int x, int y) {
        return (board_start_id + x) * y;
    }

    public MainActivity getInstance() {
        return instance;
    }
}
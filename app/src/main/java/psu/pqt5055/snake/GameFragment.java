package psu.pqt5055.snake;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameFragment extends Fragment {

    private final String TAG = "GameFragment";
    private Thread mGameThread;
    private GameFragment gameFragment;
    private Context context;
    private View parentView;

    private SnakeGame mGame;
    private GridLayout mGameBoard;
    private GridLayout mGameButtons;
    private TextView mScoreDisplay;

    private final int board_start_id = 1000;
    private final int game_board_size = 31;
    private final int startPosX = 16;
    private final int startPosY = 21;
    private final int startLen = 3;

    private int mBoardColor;
    private int mBorderColor;
    private int mSnakeColor;
    private int mFruitColor;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameFragment newInstance(String param1, String param2) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.fragment_game, container, false);

        gameFragment = this;
        context = getActivity();

        setButtonHandlers();

        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        mBorderColor = typedValue.data;
        mBoardColor = ContextCompat.getColor(context, R.color.black);
        mSnakeColor = ContextCompat.getColor(context, R.color.green_m);
        mFruitColor = ContextCompat.getColor(context, R.color.apple_red);

        initializeGame();

        return parentView;
    }

    protected void initializeGame() {
        mGameBoard = parentView.findViewById(R.id.game_grid);
        mGameButtons = parentView.findViewById(R.id.game_controls);
        mScoreDisplay = parentView.findViewById(R.id.game_score);
        mGame = new SnakeGame(getInstance(), game_board_size);
        createBoard();
        createBorder();
        mScoreDisplay.setText(getString(R.string.appbar_newgame));
        mGame.setPlaying(false);
    }

    protected void startGame() {
        mGame.newGame(startPosX, startPosY, startLen);
        mGameThread = new Thread(mGame);
        mGame.setPlaying(true);

        mGameThread.start();
    }

    public void endGame() {

        // TODO: Handle creating new game
        // TODO: Handle storing score
        // TODO: Handle end game message
    }

    public void updateBoard() {
        updateFruit();
        updateSnake();
        updateScore();
    }

    protected void createBoard() {
        int height = (int) this.getResources().getDimension(R.dimen.board_pixel_height);
        int width = (int) this.getResources().getDimension(R.dimen.board_pixel_width);

        mGameBoard.setRowCount(game_board_size);
        mGameBoard.setColumnCount(game_board_size);;

        for (int col = 1; col <= game_board_size; col++) {
            for (int row = 1; row <= game_board_size; row++) {
                View pixel = new View(context);
                pixel.setId(convertCoords(row, col));
                pixel.setLayoutParams(new ViewGroup.LayoutParams(width, height));
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

        // Validate input and set new direction
        if (direction == 0 && mGame.getHeading() != SnakeGame.Heading.DOWN) {
            mGame.setHeading(SnakeGame.Heading.UP);
        }
        else if (direction == 1 && mGame.getHeading() != SnakeGame.Heading.RIGHT) {
            mGame.setHeading(SnakeGame.Heading.LEFT);
        }
        else if (direction == 2 && mGame.getHeading() != SnakeGame.Heading.LEFT) {
            mGame.setHeading(SnakeGame.Heading.RIGHT);
        }
        else if (direction == 3 && mGame.getHeading() != SnakeGame.Heading.UP) {
            mGame.setHeading(SnakeGame.Heading.DOWN);
        }
    }

    public void onStartButtonClick(View view) {
        view.setVisibility(View.INVISIBLE);
        parentView.findViewById(R.id.game_controls).setVisibility(View.VISIBLE);
        parentView.findViewById(R.id.resetgame_button).setVisibility(View.VISIBLE);

        drawStartSnake();

        startGame();
    }

    public void onResetButtonClick(View view) {
        view.setVisibility(View.INVISIBLE);
        parentView.findViewById(R.id.game_controls).setVisibility(View.INVISIBLE);
        parentView.findViewById(R.id.startgame_button).setVisibility(View.VISIBLE);

        mGame.setPlaying(false);

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
        int snakeLength = mGame.getSnakeLength();

        // Draw Head
        drawPixel(snakeX[0], snakeY[0], mSnakeColor);

        // Erase Behind Tail
        drawPixel(snakeX[snakeLength], snakeY[snakeLength], mBoardColor);
    }

    public void updateFruit() {
        drawPixel(mGame.getFruitX(), mGame.getFruitY(), mFruitColor);
    }

    public void updateScore() {
        String score = getString(R.string.appbar_score) + Integer.toString(mGame.getScore());
        mScoreDisplay.setText(score);
    }

    protected void drawPixel(int x, int y, int color) {
        View pixel = parentView.findViewById(convertCoords(x, y));
        pixel.setBackgroundColor(color);
    }

    protected int convertCoords(int x, int y) {
        return (board_start_id + x) * y;
    }

    public GameFragment getInstance() {
        return gameFragment;
    }

    public void setButtonHandlers() {
        GridLayout gameButtons = parentView.findViewById(R.id.game_controls);
        for (int i = 0; i < gameButtons.getChildCount(); i++) {
            if (gameButtons.getChildAt(i) instanceof Button) {
                Button gameButton = (Button) gameButtons.getChildAt(i);
                gameButton.setOnClickListener(this::onControlButtonClick);
            }
        }
        parentView.findViewById(R.id.startgame_button).setOnClickListener(this::onStartButtonClick);
        parentView.findViewById(R.id.resetgame_button).setOnClickListener(this::onResetButtonClick);

    }

    @Override
    public void onPause() {
        super.onPause();
        mGame.pause();
    }
}
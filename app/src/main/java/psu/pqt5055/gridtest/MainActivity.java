package psu.pqt5055.gridtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridLayout;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    int board_start_id = 1000;
    int game_board_width = 21;
    int game_board_height = 21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createBoard();
        createBorder();
    }

    protected void createBoard() {
        int height = (int) this.getResources().getDimension(R.dimen.board_pixel_height);
        int width = (int) this.getResources().getDimension(R.dimen.board_pixel_width);

        GridLayout layout = findViewById(R.id.game_grid);
        layout.setRowCount(game_board_height);
        layout.setColumnCount(game_board_width);;

        for (int col = 1; col <= game_board_height; col++) {
            for (int row = 1; row <= game_board_width; row++) {
                View pixel = new View(this);
                pixel.setId(convertCoords(row, col));
                pixel.setLayoutParams(new LayoutParams(width, height));
                pixel.setBackgroundColor(ContextCompat.getColor(this, R.color.gray));

                layout.addView(pixel);

            }
        }
    }

    protected void createBorder() {
        for (int i = 1; i <= game_board_width; i++) {
            for (int j = 1; j <= game_board_height; j++) {
                View leftPixel = findViewById(convertCoords(1, j));
                View rightPixel = findViewById(convertCoords(game_board_width, j));
                leftPixel.setBackgroundColor(ContextCompat.getColor(this, R.color.black));
                rightPixel.setBackgroundColor(ContextCompat.getColor(this, R.color.black));
            }
            View topPixel = findViewById(convertCoords(i, 1));
            View bottomPixel = findViewById(convertCoords(i, game_board_height));
            topPixel.setBackgroundColor(ContextCompat.getColor(this, R.color.black));
            bottomPixel.setBackgroundColor(ContextCompat.getColor(this, R.color.black));

        }
    }

    protected int convertCoords(int x, int y) {
        return (board_start_id + x) * y;
    }
}
package psu.pqt5055.gridtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridLayout;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    int board_start_id = 1000;
    int game_board_width = 20;
    int game_board_height = 20;

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
            View pixel1 = findViewById(convertCoords(i, 1));
            View pixel2 = findViewById(convertCoords(1, i));
            View pixel3 = findViewById(convertCoords(i, 20));
            View pixel4 = findViewById(convertCoords(20, i));
            pixel1.setBackgroundColor(ContextCompat.getColor(this, R.color.black));
            pixel2.setBackgroundColor(ContextCompat.getColor(this, R.color.black));
            pixel3.setBackgroundColor(ContextCompat.getColor(this, R.color.black));
            pixel4.setBackgroundColor(ContextCompat.getColor(this, R.color.black));
        }
    }

    protected int convertCoords(int x, int y) {
        return (board_start_id + x) * y;
    }
}
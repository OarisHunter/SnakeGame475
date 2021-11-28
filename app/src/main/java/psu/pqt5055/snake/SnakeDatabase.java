package psu.pqt5055.snake;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Score.class}, version=1)
public abstract class SnakeDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "snake.db";

    private static SnakeDatabase mSnakeDatabase;

    // Singleton
    public static SnakeDatabase getInstance(Context context) {
        if (mSnakeDatabase == null) {
            mSnakeDatabase = Room.databaseBuilder(context, SnakeDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
            mSnakeDatabase.addStarterData();
        }

        return mSnakeDatabase;
    }

    public abstract UserDAO userDAO();
    public abstract ScoreDAO scoreDAO();

    private void addStarterData() {
        if (userDAO().getUsers().size() == 0) {
            runInTransaction(() -> {
                User user = new User("Pierce", "password");
                long userId = userDAO().insertUser(user);

                Score score = new Score(21);
                score.setMUserId(userId);
                scoreDAO().insertScore(score);

                score = new Score(50);
                score.setMUserId(userId);
                scoreDAO().insertScore(score);

                score = new Score(14);
                score.setMUserId(userId);
                scoreDAO().insertScore(score);
            });
        }
    }

}

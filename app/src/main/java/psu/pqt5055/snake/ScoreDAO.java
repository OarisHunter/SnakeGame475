package psu.pqt5055.snake;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ScoreDAO {
    @Query("SELECT * FROM Score WHERE id = :id")
    public Score getScore(long id);

    @Query("SELECT * FROM Score WHERE user_id = :id")
    public Score getScoreByUserId(long id);

    @Query("SELECT * FROM Score ORDER BY score DESC")
    public List<Score> getScores();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insertScore(Score score);

    @Delete
    public void deleteScore(Score score);
}

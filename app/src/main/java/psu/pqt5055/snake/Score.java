package psu.pqt5055.snake;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "id", childColumns = "user_id"))
public class Score {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long mId;

    @ColumnInfo(name = "Score")
    private int mScore;

    @ColumnInfo(name = "user_id")
    private long mUserId;

    public Score(@NonNull int score) {
        mScore = score;
    }

    public long getMId() {
        return mId;
    }

    public void setMId(long mId) {
        this.mId = mId;
    }

    public void setMUserId(long userId) {
        mUserId = userId;
    }

    public int getMScore() {
        return mScore;
    }

    public void setMScore(int mScore) {
        this.mScore = mScore;
    }

    public long getMUserId() {
        return mUserId;
    }
}

package psu.pqt5055.snake;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    private long mId;

    @NonNull
    @ColumnInfo(name="Name")
    private String mName;

    @NonNull
    @ColumnInfo(name="Password")
    private String mPassword;

    public User(@NonNull String name, @NonNull String password) {
        mName = name;
        mPassword = password;
    }

    public void setMId(long mId) {
        this.mId = mId;
    }

    public long getMId() {
        return mId;
    }

    @NonNull
    public String getMName() {
        return mName;
    }

    public void setMName(@NonNull String mName) {
        this.mName = mName;
    }

    @NonNull
    public String getMPassword() {
        return mPassword;
    }

    public void setMPassword(@NonNull String mPassword) {
        this.mPassword = mPassword;
    }
}

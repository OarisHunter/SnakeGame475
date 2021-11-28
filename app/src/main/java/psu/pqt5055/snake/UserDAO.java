package psu.pqt5055.snake;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM User WHERE id = :id")
    public User getUser(long id);

    @Query("SELECT * FROM User WHERE name = :name")
    public User getUserByName(String name);

    @Query("SELECT id FROM User WHERE name = :name")
    public long getUserIdByName(String name);

    @Query("SELECT * FROM User ORDER BY name COLLATE NOCASE")
    public List<User> getUsers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insertUser(User user);

    @Delete
    public void deleteUser(User user);
}

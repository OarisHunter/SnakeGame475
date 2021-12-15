package psu.pqt5055.snake;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {

    private SnakeDatabase mSnakeDb;

    EditText username, password;
    Button login, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        mSnakeDb = SnakeDatabase.getInstance(this);

        username = (EditText)findViewById(R.id.username_field);
        password = (EditText)findViewById(R.id.password_field);

        login = (Button)findViewById(R.id.login_button);
        register = (Button)findViewById(R.id.register_button);
    }

    public void onLoginClick(View view) {
        User user = mSnakeDb.userDAO().getUserByName(username.getText().toString());
        if (user != null) {
            if (password.getText().toString().equals(user.getMPassword())) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();

                // Start game activity
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(this, "Password Incorrect", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
        }
    }

    public void onRegisterClick(View view) {
        if (username.getText().toString().isEmpty()) {
            Toast.makeText(this, "Username empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        User user = mSnakeDb.userDAO().getUserByName(username.getText().toString());
        if (user != null) {
            Toast.makeText(this, "User already exists!", Toast.LENGTH_SHORT).show();
        }
        else {
            if (password.getText().toString().isEmpty()) {
                Toast.makeText(this, "Password not entered", Toast.LENGTH_SHORT).show();
            }
            else {
                User new_user = new User(username.getText().toString(), password.getText().toString());
                mSnakeDb.userDAO().insertUser(new_user);
                Toast.makeText(this, "User registered", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

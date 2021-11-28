package psu.pqt5055.snake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Apply selected theme
        preferences = getPreferences(Context.MODE_PRIVATE);
        int themeSelectedId = preferences.getInt("theme", R.id.theme_standard);
        if (themeSelectedId == R.id.theme_classic) {
            getTheme().applyStyle(R.style.OverlayThemeClassic, true);
        }
        else if (themeSelectedId == R.id.theme_rgb) {
            getTheme().applyStyle(R.style.OverlayThemeRGB, true);
        }
        else if (themeSelectedId == R.id.theme_noir) {
            getTheme().applyStyle(R.style.OverlayThemeNoir, true);
        }
        else {
            getTheme().applyStyle(R.style.OverlayThemeStandard, true);
        }
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();

            AppBarConfiguration appBarConfig = new AppBarConfiguration.Builder(
                    R.id.nav_gameFragment, R.id.nav_scoresFragment, R.id.nav_settingsFragment)
                    .build();

            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig);
            NavigationUI.setupWithNavController(navView, navController);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Determine which menu option was selected
        if (item.getItemId() == R.id.pause_button) {
            // Pause selected
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
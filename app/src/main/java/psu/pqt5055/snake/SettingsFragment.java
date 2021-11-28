package psu.pqt5055.snake;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavGraph;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    private View parentView;
    private SharedPreferences preferences;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
        parentView = inflater.inflate(R.layout.fragment_settings, container, false);

        preferences = requireActivity().getPreferences(Context.MODE_PRIVATE);

        initDifficulty();
        initTheme();

        return parentView;
    }

    private void initDifficulty() {
        int difficultyId = preferences.getInt("difficulty", R.id.difficulty_easy);

        int radioId = R.id.difficulty_easy;
        if (difficultyId == R.id.difficulty_medium) {
            radioId = R.id.difficulty_medium;
        }
        else if (difficultyId == R.id.difficulty_hard) {
            radioId = R.id.difficulty_hard;
        }

        RadioButton radio = parentView.findViewById(radioId);
        radio.setChecked(true);

        // Add callbacks
        RadioGroup radioGroup = parentView.findViewById(R.id.difficulty_buttons);
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radio = (RadioButton) radioGroup.getChildAt(i);
            radio.setOnClickListener(this::onDifficultySelected);
        }
    }
    
    private void initTheme() {
        int themeId = preferences.getInt("theme", R.id.theme_standard);

        int radioId = R.id.theme_standard;
        if (themeId == R.id.theme_classic) {
            radioId = R.id.theme_classic;
        }
        else if (themeId == R.id.theme_rgb) {
            radioId = R.id.theme_rgb;
        }
        else if (themeId == R.id.theme_noir) {
            radioId = R.id.theme_noir;
        }

        RadioButton radio = parentView.findViewById(radioId);
        radio.setChecked(true);

        // Add callbacks
        GridLayout radioGroup = parentView.findViewById(R.id.theme_buttons);
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radio = (RadioButton) radioGroup.getChildAt(i);
            radio.setOnClickListener(this::onThemeSelected);
        }
    }

    private void onDifficultySelected(View view) {
        int diffId = R.id.difficulty_easy;
        if (view.getId() == R.id.difficulty_medium) {
            diffId = R.id.difficulty_medium;
        }
        else if (view.getId() == R.id.difficulty_hard) {
            diffId = R.id.difficulty_hard;
        }

        SharedPreferences sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("difficulty", diffId);
        editor.apply();
    }

    private void onThemeSelected(View view) {
        int themeId = R.id.theme_standard;
        if (view.getId() == R.id.theme_classic) {
            themeId = R.id.theme_classic;
        }
        else if (view.getId() == R.id.theme_rgb) {
            themeId = R.id.theme_rgb;
        }
        else if (view.getId() == R.id.theme_noir) {
            themeId = R.id.theme_noir;
        }

        // Clear Checks
        GridLayout radioGroup = parentView.findViewById(R.id.theme_buttons);
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            RadioButton radio = (RadioButton) radioGroup.getChildAt(i);
            if (radio.getId() != view.getId()) {
                radio.setChecked(false);
            }
        }

        SharedPreferences sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("theme", themeId);
        editor.apply();

        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}
package com.example.spaceshootem;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {
    private static final String PREFS = "SpaceShootEmPrefs" ;
    private SharedPreferences sharedPrefs;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Create a new settings fragment
     */
    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    /**
     * Upon creation... TODO:
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        sharedPrefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        // Load shared preferences
//        reMap<String, ?> prefs = sharedPrefs.getAll();
        // TODO: Load the values
        // music, difficulty, color of ship, background (space, sky, black hole, etc), Name
        // val = prefs.get("STRING");

        // TODO
    }
}

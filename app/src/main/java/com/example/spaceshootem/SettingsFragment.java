package com.example.spaceshootem;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment implements View.OnClickListener {
    private View layout;
    private SwitchCompat musicSwitch;
    private Spinner playerColors;
    private Spinner enemyColors;
    private Spinner bulletColors;
    private Spinner difficulty;
    private Button confirm;
    private SharedPreferences settings;
    private boolean musicOn;


    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Create a new settings fragment
     */
    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_settings, container, false);

        settings = PreferenceManager.getDefaultSharedPreferences(getContext());

        musicOn = settings.getBoolean("musicKey", true);
        musicSwitch = layout.findViewById(R.id.musicSwitch);
        musicSwitch.setChecked(musicOn);

        musicSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            musicOn = musicSwitch.isChecked();
            if (musicOn) {
                PlayMusic.playAudio(getContext());
            } else {
                PlayMusic.stopAudio();
            }
        });


        // Setup the spinner values
        playerColors = layout.findViewById(R.id.playerColorSpinner);
        ArrayAdapter<CharSequence> playerSpinner = ArrayAdapter.createFromResource(
                getContext(), R.array.player_colors_list, android.R.layout.simple_spinner_item);
        playerSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        playerColors.setAdapter(playerSpinner);

        enemyColors = layout.findViewById(R.id.enemyColorSpinner);
        ArrayAdapter<CharSequence> enemySpinner = ArrayAdapter.createFromResource(
                getContext(), R.array.enemy_colors_list, android.R.layout.simple_spinner_item);
        enemySpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        enemyColors.setAdapter(enemySpinner);

        bulletColors = layout.findViewById(R.id.bulletColorSpinner);
        ArrayAdapter<CharSequence> bulletSpinner = ArrayAdapter.createFromResource(
                getContext(), R.array.bullet_colors_list, android.R.layout.simple_spinner_item);
        bulletSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bulletColors.setAdapter(bulletSpinner);

        difficulty = layout.findViewById(R.id.difficultySpinner);
        ArrayAdapter<CharSequence> difficultySpinner = ArrayAdapter.createFromResource(
                getContext(), R.array.difficulties, android.R.layout.simple_spinner_item);
        difficultySpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficulty.setAdapter(difficultySpinner);

        confirm = layout.findViewById(R.id.okay);
        confirm.setOnClickListener(this);

        return layout;
    }

    /**
     * Saves the changes made when the okay button is clicked
     * @param v the view clicked
     */
    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("difficulty", (String) difficulty.getSelectedItem());
        editor.putString("playerColor", (String) playerColors.getSelectedItem());
        editor.putString("enemyColor", (String) enemyColors.getSelectedItem());
        editor.putString("bulletColor", (String) bulletColors.getSelectedItem());
        editor.putBoolean("musicKey", musicOn);
        editor.apply();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, IntroFragment.newInstance())
                .commit();
    }
}

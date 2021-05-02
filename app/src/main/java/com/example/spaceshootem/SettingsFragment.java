package com.example.spaceshootem;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

        musicSwitch = layout.findViewById(R.id.musicSwitch);

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

    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("difficulty", (String) difficulty.getSelectedItem());
        editor.putString("playerColor", (String) playerColors.getSelectedItem());
        editor.putString("enemyColor", (String) enemyColors.getSelectedItem());
        editor.putString("bulletColor", (String) bulletColors.getSelectedItem());
        editor.apply();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, IntroFragment.newInstance())
                .commit();
    }
}

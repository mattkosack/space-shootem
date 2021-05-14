package edu.moravian.csci299.spaceshootem;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

public class GameFragment extends Fragment {
    private SensorManager sensorManager;
    private Sensor sensor;
    private SpaceGameView spaceGameView;
    private View layout;
    
    public GameFragment() {
        // Required empty public constructor
    }

    /** Create a new game fragment */
    public static GameFragment newInstance() { return new GameFragment(); }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        assert container != null;
//        container.removeAllViews();

        layout = inflater.inflate(R.layout.fragment_game, container, false);
        sensorManager = (SensorManager) this.getActivity().getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        spaceGameView = layout.findViewById(R.id.SpaceGameView);
        
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        List<String> difficulties = Arrays.asList(getResources().getStringArray(R.array.difficulties));
        String difficulty = settings.getString("difficulty", "");
        String enemyColor = settings.getString("enemyColor", "");
        String playerColor = settings.getString("playerColor", "");
        String bulletColor = settings.getString("bulletColor", "");

        spaceGameView.setDifficulty(difficulties.indexOf(difficulty), difficulty);
        spaceGameView.setEnemyPaint(enemyColor);
        spaceGameView.setPlayerPaint(playerColor);
        spaceGameView.setBulletPaint(bulletColor);

        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(spaceGameView, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(spaceGameView);
    }
}

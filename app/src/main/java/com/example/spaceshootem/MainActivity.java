package com.example.spaceshootem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences settings;
    private boolean musicOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settings = PreferenceManager.getDefaultSharedPreferences(this);
        musicOn = settings.getBoolean("musicKey", musicOn);

        if (musicOn) {
            PlayMusic.playAudio(getApplicationContext());
        } else {
            PlayMusic.stopAudio();
        }


        // Get the currently displayed fragment (if there is one)
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (currentFragment == null) {
            // If no fragment is being currently displayed add one via a transaction
            IntroFragment fragment = IntroFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
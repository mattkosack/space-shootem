package com.example.spaceshootem;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;


public class IntroFragment extends Fragment implements View.OnClickListener {
    private View layout;
    private TextView titleView;
    private ImageView logoView;
    private Button startView;
    private Button highscoreView;
    private Button settingsView;
    private Button websiteView;

    public IntroFragment() {
        // Required empty public constructor
    }

    /**
     * Create a new intro fragment
     */
    public static IntroFragment newInstance() {
        return new IntroFragment();
    }

    /**
     * Create the view of the fragment and fill in its contents.
     *
     * @return the root View of the fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_intro, container, false);

        // Set the contents of the fragment
        titleView = layout.findViewById(R.id.title);

        logoView = layout.findViewById(R.id.logo);

        startView = layout.findViewById(R.id.start_game);
        startView.setOnClickListener(this);

        highscoreView = layout.findViewById(R.id.highscore);
        highscoreView.setOnClickListener(this);

        settingsView = layout.findViewById(R.id.settings);
        settingsView.setOnClickListener(this);

        websiteView = layout.findViewById(R.id.website);
        websiteView.setOnClickListener(this);

        // Return the root view
        return layout;
    }


    /**
     * Replace the current fragment with the corresponding clicked button name.
     * @param v the view that was clicked
     */
    @Override
    public void onClick(View v) {
        Fragment frag = null;
        if (v == layout.findViewById(R.id.start_game)) {
            frag = GameFragment.newInstance();
        } else if (v == layout.findViewById(R.id.highscore)) {
            //TODO: new fragment? show the high scores...
        } else if (v == layout.findViewById(R.id.settings)){
            frag = SettingsFragment.newInstance();
        } else if (v == layout.findViewById(R.id.website)) {
            frag = WebsiteFragment.newInstance();
        }
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, frag)
                .commit();
    }
}
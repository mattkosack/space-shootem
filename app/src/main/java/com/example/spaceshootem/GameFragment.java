package com.example.spaceshootem;

import androidx.fragment.app.Fragment;
import android.graphics.RectF;

public class GameFragment extends Fragment {
    //Assuming this is going to have everything game wise in it?
    //Be that the bullets, playerships, and aliens
    
    public GameFragment() {
        // Required empty public constructor
    }

    /**
     * Create a new game fragment
     */
    public static GameFragment newInstance() {
        return new GameFragment();
    }
}

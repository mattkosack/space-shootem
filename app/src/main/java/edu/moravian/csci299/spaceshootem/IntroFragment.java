package edu.moravian.csci299.spaceshootem;


import android.animation.ObjectAnimator;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;


public class IntroFragment extends Fragment implements View.OnClickListener {
    private View layout;
    private TextView titleView;
    private ImageView logoView;
    private Button startView;
    private Button settingsView;
    private Button websiteView;
    private SharedPreferences settings;


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
     * @return the root View of the fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        assert container != null;
//        container.removeAllViews();
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_intro, container, false);

        
        // Set the contents of the fragment
        titleView = layout.findViewById(R.id.title);
        // Set the title to flicker
        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(1000);
        animation.setStartOffset(20);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(Animation.INFINITE);
        titleView.startAnimation(animation);

        logoView = layout.findViewById(R.id.logo);
        ObjectAnimator animator = ObjectAnimator.ofFloat(logoView, "translationY", -400f, 400f);
        animator.setDuration(3000);
        animator.setRepeatMode(ObjectAnimator.REVERSE);
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.start();

        startView = layout.findViewById(R.id.start_game);
        startView.setOnClickListener(this);

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
        } else if (v == layout.findViewById(R.id.settings)){
            frag = SettingsFragment.newInstance();
        } else if (v == layout.findViewById(R.id.website)) {
            frag = WebsiteFragment.newInstance();
        }
        assert frag != null;
        assert getFragmentManager() != null;
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, frag)
                .addToBackStack(null)
                .commit();
    }
}
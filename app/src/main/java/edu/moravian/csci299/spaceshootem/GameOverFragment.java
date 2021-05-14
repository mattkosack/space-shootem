package edu.moravian.csci299.spaceshootem;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class GameOverFragment extends Fragment implements View.OnClickListener {
    private View layout;
    private TextView gameOverText;
    private ImageView gameOverImage;
    private Button tryAgainView;
    private Button settingsView;
    private Button mainMenuView;

    public GameOverFragment() {
        // required empty public constructor
    }

    public static GameOverFragment newInstance() {
        return new GameOverFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        assert container != null;
//        container.removeAllViews();

        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_game_over, container, false);

        // Set the contents of the fragment
        gameOverText = layout.findViewById(R.id.game_over_text);
        // Set the text to flicker
        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(500);
        animation.setStartOffset(20);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(Animation.INFINITE);
        gameOverText.startAnimation(animation);

        gameOverImage = layout.findViewById(R.id.game_over_image);

        tryAgainView = layout.findViewById(R.id.try_again);
        tryAgainView.setOnClickListener(this);

        settingsView = layout.findViewById(R.id.settings);
        settingsView.setOnClickListener(this);

        mainMenuView = layout.findViewById(R.id.main_menu);
        mainMenuView.setOnClickListener(this);

        return layout;
    }

    @Override
    public void onClick(View v) {
        Fragment frag = null;
        if (v == layout.findViewById(R.id.try_again)) {
            frag = GameFragment.newInstance();
        } else if (v == layout.findViewById(R.id.settings)) {
            frag = SettingsFragment.newInstance();
        } else if (v == layout.findViewById(R.id.main_menu)) {
            Log.e("hm", "it does get here");
            frag = IntroFragment.newInstance();
        }
        assert getFragmentManager() != null;
        assert frag != null;
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, frag)
                .addToBackStack(null)
                .commit();
    }
}

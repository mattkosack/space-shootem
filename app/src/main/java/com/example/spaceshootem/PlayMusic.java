package com.example.spaceshootem;

import android.content.Context;
import android.media.MediaPlayer;

// This is courtesy of Mark Morykan and Jonah Beers
// We all agree it's not great, but it works...
public class PlayMusic {

    public static MediaPlayer mediaPlayer;

    public static void playAudio(Context c) {
        mediaPlayer = MediaPlayer.create(c, R.raw.idontknow);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    public static void stopAudio() {
        if (mediaPlayer != null) mediaPlayer.stop();
    }
}

package com.example.studio08.verysimplepodcast;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

/**
 * Created by Ronen on 16/4/16.
 */
public class MiniPlayerFragment extends Fragment {

    View miniplayer = null;

    private ImageView playButton;
    private boolean isPlayButton = false;
    private MediaPlayer mediaPlayer = null;
    private int fileDuration;
    private SeekBar progressBar = null;
    private Handler progressBarHandler = new Handler();


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        startViews();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        miniplayer = inflater.inflate(R.layout.miniplayer_fragment, container, false);
        return miniplayer;
    }

    public void startPlayer(String itemUrl) {
        Log.d("miniplayer", itemUrl);
    }

    public void changePlayButtonState(){
        isPlayButton = !isPlayButton;
        if(isPlayButton) {
            if (mediaPlayer != null) {
                playButton.setImageResource(R.drawable.ic_pause_24dp);
                mediaPlayer.start();
            }
        } else {
            if (mediaPlayer != null) {
                playButton.setImageResource(R.drawable.ic_play_arrow_24dp);
                mediaPlayer.pause();
            }
        }
    }

    private void startViews() {

        /** TODO: release and set to null MediaPlayer */
        playButton = (ImageView) miniplayer.findViewById(R.id.play_imageView);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePlayButtonState();
            }
        });
    }

}

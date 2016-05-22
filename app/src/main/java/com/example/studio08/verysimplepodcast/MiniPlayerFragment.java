package com.example.studio08.verysimplepodcast;

import android.content.Intent;
import android.media.AudioManager;
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

import java.io.IOException;


/**
 * Created by Ronen on 16/4/16.
 */
public class MiniPlayerFragment extends Fragment implements MediaPlayer.OnPreparedListener {

    View miniplayer = null;

    private ImageView playButton;
    private boolean isPlayButton = true;
    private MediaPlayer mediaPlayer = null;
    private int fileDuration;
    private SeekBar progressBar = null;
    private Handler progressBarHandler = new Handler();


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        miniplayer = inflater.inflate(R.layout.miniplayer_fragment, container, false);
        startViews();
        return miniplayer;
    }

    public void startPlayer(String mediaUrl) {
        Log.d("miniplayer", mediaUrl);
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaPlayer.setDataSource(mediaUrl);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(this);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void play() {
        mediaPlayer.start();
        changeToPause();
    }

    private void pause() {
        mediaPlayer.pause();
        changeToPlay();
    }

    private void changeToPause() {
        isPlayButton = false;
        playButton.setImageResource(R.drawable.ic_pause_24dp);
    }

    private void changeToPlay() {
        isPlayButton = true;
        playButton.setImageResource(R.drawable.ic_play_arrow_24dp);
    }

    private void startViews() {

        /** TODO: release and set to null MediaPlayer */
        playButton = (ImageView) miniplayer.findViewById(R.id.play_imageView);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlayButton)
                    changeToPause();
                else
                    changeToPlay();
            }
        });
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        play();
    }
}

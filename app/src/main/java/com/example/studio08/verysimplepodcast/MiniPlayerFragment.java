package com.example.studio08.verysimplepodcast;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;

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


    private void startViews() {

        /** TODO: release and set to null MediaPlayer */
        playButton = (ImageView) miniplayer.findViewById(R.id.play_imageView);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPlayButton = !isPlayButton;
                if(isPlayButton) {
                    playButton.setImageResource(R.drawable.ic_pause_24dp);
                    // sample stream
                    String url = "http://feeds.wnyc.org/~r/radiolab/~5/KYQG_JtkTYM/radiolab_podcast16cellmates.mp3";
                    if (mediaPlayer == null) {
                        mediaPlayer = MediaPlayer.create(getContext(), Uri.parse(url));
                        fileDuration = mediaPlayer.getDuration();
                        mediaPlayer.start();
                    } else {
                        mediaPlayer.start();
                    }

                    new Runnable() {
                        // he's responsible: http://stackoverflow.com/questions/17168215/seekbar-and-media-player-in-android
                        @Override
                        public void run() {
                            if(mediaPlayer != null){
                                int currentPosition = mediaPlayer.getCurrentPosition() / 1000;
                                progressBar.setProgress(currentPosition);
                            }
                            progressBarHandler.postDelayed(this, 1000);
                        }
                    };
                } else {
                    playButton.setImageResource(R.drawable.ic_play_arrow_24dp);
                    mediaPlayer.pause();
                }
            }
        });

        // SeekBar

        progressBar = (SeekBar) miniplayer.findViewById(R.id.progressBar);
        progressBar.setMax(fileDuration);
        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
                if(mediaPlayer != null && fromUser){
                    mediaPlayer.seekTo(progress * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

}

package com.ronen.studio08.verysimplepodcast;

import android.media.AudioManager;
import android.media.MediaPlayer;
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
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;


/**
 * Created by Ronen on 16/4/16.
 */
public class MiniPlayerFragment extends Fragment implements MediaPlayer.OnPreparedListener {

    private ImageView playButton, replayButton, forwardButton;
    private boolean isPlayButton = true;
    private MediaPlayer mediaPlayer;
    private int fileDuration;
    private SeekBar seekBar;
    private TextView counter;
    private Handler progressBarHandler;
    private Utilities utilities;
    public final static int SKIP30 = 30;
    public final static int SKIP10 = 10;
    public final static int SKIP5 = 5;
    private int skipmode = SKIP30;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.miniplayer_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        playButton = (ImageView) view.findViewById(R.id.play_imageView);
        replayButton = (ImageView) view.findViewById(R.id.replay_iw);
        forwardButton = (ImageView) view.findViewById(R.id.forward_iw);

        switch (skipmode) {
            case SKIP30:
                break;
            case SKIP10:
                replayButton.setImageResource(R.drawable.ic_replay_10_black_24dp);
                forwardButton.setImageResource(R.drawable.ic_forward_10_black_24dp);
                break;
            case SKIP5:
                replayButton.setImageResource(R.drawable.ic_replay_5_black_24dp);
                forwardButton.setImageResource(R.drawable.ic_forward_5_black_24dp);
                break;
            default:
        }

        if (mediaPlayer == null) disableButtons();

        seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        counter = (TextView) view.findViewById(R.id.counter_textview);
        utilities = new Utilities();



            playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isPlayButton) play();
                    else pause();
                }
            });

            replayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mediaPlayer != null)
                        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - skipmode * 1000);
                }
            });

            forwardButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mediaPlayer != null)
                        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + skipmode * 1000);
                }
            });

    }

    private void disableButtons() {
        playButton.setEnabled(false);
        playButton.setAlpha(0.5F);
        replayButton.setEnabled(false);
        replayButton.setAlpha(0.5F);
        forwardButton.setEnabled(false);
        forwardButton.setAlpha(0.5F);
    }

    private void enableButtons() {
        playButton.setEnabled(true);
        playButton.setAlpha(1F);
        replayButton.setEnabled(true);
        replayButton.setAlpha(1F);
        forwardButton.setEnabled(true);
        forwardButton.setAlpha(1F);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        playButton = null;
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
        if (mediaPlayer != null) {
            mediaPlayer.start();
            changeToPause();
        } else {
            Toast.makeText(getActivity(), "No episode selected.", Toast.LENGTH_SHORT).show();
        }
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

    @Override
    public void onPrepared(final MediaPlayer mp) {
        enableButtons();
        play();
        seekBar.setMax(mp.getDuration());
        progressBarHandler = new Handler();

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                long totalDuration = mp.getDuration();
                long currentPosition = mp.getCurrentPosition();
                seekBar.setProgress((int) currentPosition);
                progressBarHandler.postDelayed(this, 1000);
                counter.setText(""+utilities.milliSecondsToTimer(currentPosition)
                        +" / "+utilities.milliSecondsToTimer(totalDuration));
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            // https://stackoverflow.com/questions/17168215/seekbar-and-media-player-in-android
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mp != null && fromUser) {
                    mp.seekTo(progress);
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
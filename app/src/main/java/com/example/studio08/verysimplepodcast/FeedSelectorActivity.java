package com.example.studio08.verysimplepodcast;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class FeedSelectorActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ArrayList<PodcastFeed> podcastFeedList;
    private ImageView plusButton, playButton;
    private SeekBar progressBar = null;
    private boolean isPlayButton = false;
    private MediaPlayer mediaPlayer = null;
    private int fileDuration;
    private Handler progressBarHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_selector);
//        startFontManager();
        startAdapter();
        startViews();
    }

//    private void startFontManager() {
//        /** The FontManager from http://code.tutsplus.com/tutorials/how-to-use-fontawesome-in-an-android-app--cms-24167 */
//        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME);
//        FontManager.markAsIconContainer(findViewById(R.id.icons_container), iconFont);
//        FontManager.markAsIconContainer(findViewById(R.id.miniplayer_container), iconFont);
//    }

    private void startAdapter() {
        // sets sample array
        PodcastFeed[] podcastFeedTestArray = {new PodcastFeed(), new PodcastFeed(), new PodcastFeed(), new PodcastFeed(), new PodcastFeed(), new PodcastFeed(), new PodcastFeed(), new PodcastFeed(), new PodcastFeed(), new PodcastFeed(), new PodcastFeed(), new PodcastFeed()};
        podcastFeedList = new ArrayList<>(Arrays.asList(podcastFeedTestArray));

        ListView podcastFeedListView = (ListView) findViewById(R.id.podcasts_listView);
        PodcastFeedAdapter podcastFeedAdapter = new PodcastFeedAdapter(this, podcastFeedList);
        podcastFeedListView.setAdapter(podcastFeedAdapter);

        podcastFeedListView.setOnItemClickListener(this);
    }

    private void startViews() {

        plusButton = (ImageView) findViewById(R.id.plus_imageView);
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FeedSelectorActivity.this, AddPodcastActivity.class));
            }
        });

        /** TODO: release and set to null MediaPlayer */
        playButton = (ImageView) findViewById(R.id.play_imageView);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPlayButton = !isPlayButton;
                if(isPlayButton) {
                    playButton.setImageResource(R.drawable.ic_pause_24dp);
                    // sample stream
                    String url = "http://feeds.wnyc.org/~r/radiolab/~5/KYQG_JtkTYM/radiolab_podcast16cellmates.mp3";
                    if (mediaPlayer == null) {
                        mediaPlayer = MediaPlayer.create(FeedSelectorActivity.this, Uri.parse(url));
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

        progressBar = (SeekBar) findViewById(R.id.progressBar);
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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, PodcastFeedActivity.class);
        intent.putExtra("name", podcastFeedList.get(position).getTitle());
        intent.putExtra("description", podcastFeedList.get(position).getDescription());
        intent.putExtra("thumbnailId", podcastFeedList.get(position).getThumbnailId());
        startActivity(intent);
    }
}

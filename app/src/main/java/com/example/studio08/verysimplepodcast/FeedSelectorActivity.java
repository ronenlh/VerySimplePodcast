package com.example.studio08.verysimplepodcast;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v4.app.ListFragment;
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

public class FeedSelectorActivity extends AppCompatActivity {

    private ImageView plusButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_selector);

        startBar();
    }

    private void startBar() {
        plusButton = (ImageView) findViewById(R.id.plus_imageView);
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FeedSelectorActivity.this, AddPodcastActivity.class));
            }
        });
    }

//    private void startFontManager() {
//        /** The FontManager from http://code.tutsplus.com/tutorials/how-to-use-fontawesome-in-an-android-app--cms-24167 */
//        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME);
//        FontManager.markAsIconContainer(findViewById(R.id.icons_container), iconFont);
//        FontManager.markAsIconContainer(findViewById(R.id.miniplayer_container), iconFont);
//    }






}

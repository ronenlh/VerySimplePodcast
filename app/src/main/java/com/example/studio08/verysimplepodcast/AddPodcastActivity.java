package com.example.studio08.verysimplepodcast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

public class AddPodcastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_podcast);
    }

    public void addFeedURL(View v) {
        EditText editText = (EditText) findViewById(R.id.editText);
        URL feedURL = null;
        try {
            feedURL = new URL(editText.getText().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Toast.makeText(this, "There must be a typo in the URL...", Toast.LENGTH_SHORT).show();
        }
        String feedStringURL = feedURL.toString();
        Intent intent = new Intent(this, ChannelSelectorActivity.class);
        intent.putExtra("feedURL", feedStringURL);
        startActivity(intent);
    }
}

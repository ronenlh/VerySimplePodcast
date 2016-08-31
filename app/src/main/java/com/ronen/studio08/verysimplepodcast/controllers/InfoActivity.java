package com.ronen.studio08.verysimplepodcast.controllers;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ronen.studio08.verysimplepodcast.InfoListBaseAdapter;
import com.ronen.studio08.verysimplepodcast.R;
import com.ronen.studio08.verysimplepodcast.model.FeedSnippet;
import com.ronen.studio08.verysimplepodcast.model.PodcastLab;
import com.ronen.studio08.verysimplepodcast.model.RssModelClass.Channel;
import com.ronen.studio08.verysimplepodcast.model.RssModelClass.RSS;
import com.ronen.studio08.verysimplepodcast.model.itunesSearchModelClass.Result;
import com.ronen.studio08.verysimplepodcast.model.retrofit.ApiService;
import com.ronen.studio08.verysimplepodcast.model.retrofit.ServiceGenerator;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoActivity extends AppCompatActivity {
    private static final String TAG = "InfoActivity";

    ImageView imageView;
    TextView nameTextView, artistTextView, subtitleTextView;
    //    Button addButton;
    ListView mListView;

    FeedSnippet mFeedSnippet;
    RSS mFeed;


    interface onAddSelectedListener {
        void onAddSelected(Result itemUrl);
    }

    interface onInfoSelectedListener {
        void onInfoSelected(Result itemUrl);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        findViews();

        Intent intent = getIntent();
        mFeedSnippet = (FeedSnippet) intent.getSerializableExtra("feed");

        setContent();
    }

    private void findViews() {
        imageView = (ImageView) findViewById(R.id.imageView);
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        artistTextView = (TextView) findViewById(R.id.artistTextView);
        subtitleTextView = (TextView) findViewById(R.id.subtitleTextView);
//        addButton       = (Button) findViewById(R.id.addButton);
        mListView = (ListView) findViewById(R.id.infoListView);
    }

    private void setContent() {
        Picasso.with(this)
                .load(mFeedSnippet.getThumbnail())
                .error(R.drawable.ic_action_logo_flat)
                .into(imageView);
        nameTextView.setText(mFeedSnippet.getTitle());
        artistTextView.setText(mFeedSnippet.getCreator());
        subtitleTextView.setText(mFeedSnippet.getSubtitle());

        retrofitCaller(mFeedSnippet.getFeedUrl());
    }

    private void retrofitCaller(String feedUrl) {

        ApiService service = ServiceGenerator.createService(ApiService.class);
        Call<RSS> call = service.feed(feedUrl);
        call.enqueue(new retrofit2.Callback<RSS>() {
            @Override
            public void onResponse(Call<RSS> call, Response<RSS> response) {

                mFeed = response.body();

                if (mFeed != null) {

//                    Channel.Item[] items = mFeed.getChannel().getItemList().toArray(new Channel.Item[mFeed.getChannel().getItemList().size()]);

                    InfoListBaseAdapter adapter = new InfoListBaseAdapter(InfoActivity.this, mFeed.getChannel().getItemList());

//                    ArrayAdapter adapter = new ArrayAdapter(InfoActivity.this, android.R.layout.simple_list_item_1, items);
                    mListView.setAdapter(adapter);

                } else
                    try {
                        Log.e("mFeed", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }

            @Override
            public void onFailure(Call<RSS> call, Throwable t) {
                Log.w(TAG, t);
            }
        });
    }

    public void addFeed(View view) {
        PodcastLab.get(InfoActivity.this).insertFeed(mFeedSnippet);
        finish();
    }

    public void goToWebsite(View view) {
        String url = mFeedSnippet.getWebsite();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }



}

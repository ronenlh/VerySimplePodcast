package com.ronen.studio08.verysimplepodcast;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ronen.studio08.verysimplepodcast.database.DbHelper;
import com.ronen.studio08.verysimplepodcast.database.FeedsContract;
import com.ronen.studio08.verysimplepodcast.retrofit.ApiService;
import com.ronen.studio08.verysimplepodcast.retrofit.RSS;
import com.ronen.studio08.verysimplepodcast.retrofit.ServiceGenerator;
import com.ronen.studio08.verysimplepodcast.retrofitCloud.CloudService;
import com.ronen.studio08.verysimplepodcast.retrofitCloud.SampleFeed;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by studio08 on 5/10/2016.
 */
public class AddFeedActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    List<FeedSample> sampleFeedList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feed);
        Button button = (Button) findViewById(R.id.add_button1);
        button.setOnClickListener(this);

        sampleFeedRetrofitCaller();
    }

    private void sampleFeedRetrofitCaller() {

        // Retrofit is the class through which your API interfaces are turned into callable objects.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://infinite-citadel-18717.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CloudService service = retrofit.create(CloudService.class);

        Call<List<SampleFeed>> call = service.feed();
        call.enqueue(new Callback<List<SampleFeed>>() {
            @Override
            public void onResponse(Call<List<SampleFeed>> call, Response<List<SampleFeed>> response) {

                Log.d("RetrofitCaller", response.body().toString());

                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);
                recyclerView.setHasFixedSize(true);

                GridLayoutManager gridLayoutManager = new GridLayoutManager(AddFeedActivity.this,3);
                recyclerView.setLayoutManager(gridLayoutManager);

                AddFeedRVAdapter rvAdapter = new AddFeedRVAdapter(AddFeedActivity.this, response.body());
                recyclerView.setAdapter(rvAdapter);
            }

            @Override
            public void onFailure(Call<List<SampleFeed>> call, Throwable t) {
                Log.d("RetrofitCaller", t.toString());
            }
        });
    }

    private void retrofitCaller(final String feedUrl) {

        // database
        DbHelper dbHelper = new DbHelper(this);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        ApiService service = ServiceGenerator.createService(ApiService.class);
        Call<RSS> call = service.feed(feedUrl);
        call.enqueue(new Callback<RSS>() {
            @Override
            public void onResponse(Call<RSS> call, Response<RSS> response) {
            /**  Similar to Episode Selector Fragment, but here we want info about the feed and add it to SQLite database.
                 In the FeedSelectorFragment we pull info from that Database, Feeds.db */

                RSS rss = response.body(); // <-- this is the feed!

                if (rss != null) {
                    Log.d("feed", "feed is not null: \n" + rss.toString());
                    String title = rss.getChannel().getTitle();
                    String creator;
                    if ((creator = rss.getChannel().getAuthor()) == null)
                        creator = rss.getChannel().getItemList().get(0).getAuthorList().get(0);
                    String subtitle = rss.getChannel().getSubtitle();
                    String thumbnail = rss.getChannel().getImage();
                    databaseHelper(db, title, creator, feedUrl, subtitle, thumbnail);

                } else
                    try {
                        Log.e("feed", response.errorBody().string());
                        Toast.makeText(AddFeedActivity.this, R.string.error_feed, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }

            @Override
            public void onFailure(Call<RSS> call, Throwable t) {
                Log.d("failure", t.toString());
            }
        });
    }


    private long databaseHelper(SQLiteDatabase db, String title, String creator, String feedUrl, String subtitle, String thumbnail) {

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedsContract.FeedEntry.COLUMN_NAME_TITLE, title);
        values.put(FeedsContract.FeedEntry.COLUMN_NAME_CREATOR, creator);
        values.put(FeedsContract.FeedEntry.COLUMN_NAME_FEED_URL, feedUrl);
        values.put(FeedsContract.FeedEntry.COLUMN_NAME_SUBTITLE, subtitle);
        values.put(FeedsContract.FeedEntry.COLUMN_NAME_THUMBNAIL, thumbnail);

        // Insert the new row, returning the primary key value of the new row
        Log.d("databaseHelper()", title);
        // insertWithConflict instead of insert.
        long primaryKey = db.insertWithOnConflict(
                FeedsContract.FeedEntry.TABLE_NAME,
                FeedsContract.FeedEntry.COLUMN_NAME_NULLABLE,
                values,SQLiteDatabase.CONFLICT_IGNORE);

        return primaryKey;
    }

    @Override
    public void onClick(View v) {
        EditText editText = (EditText) findViewById(R.id.feed_editText1);
        retrofitCaller(editText.getText().toString());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        retrofitCaller(sampleFeedList.get(position).getFeedUrl());
//        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }
}
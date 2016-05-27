package com.ronen.studio08.verysimplepodcast;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ronen.studio08.verysimplepodcast.database.FeedReaderContract;
import com.ronen.studio08.verysimplepodcast.database.DatabaseHelper;
import com.ronen.studio08.verysimplepodcast.retrofit.ApiService;
import com.ronen.studio08.verysimplepodcast.retrofit.Channel;
import com.ronen.studio08.verysimplepodcast.retrofit.RSS;
import com.ronen.studio08.verysimplepodcast.retrofit.ServiceGenerator;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ronen on 3/5/16.
 */
public class EpisodeSelectorFragment extends ListFragment implements AdapterView.OnItemLongClickListener {

    RSS feed;
    onEpisodeSelectedListener mCallback;

    interface onEpisodeSelectedListener {
        void onEpisodeSelected(int position);
        void onLongEpisodeClick(int position, String url);
    }

    @Override
    public void onAttach(Context context) {
        // To allow a Fragment to communicate up to its Activity, you can define an interface in the Fragment class and implement it within the Activity.
        // The Fragment captures the interface implementation during its onAttach() lifecycle method and can then call the Interface methods in order to communicate with the Activity.
        super.onAttach(context);
        try {
            mCallback = (onEpisodeSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement onEpisodeSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.episode_selector_listfragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String feedUrl = getArguments().getString("feedUrl");
        retrofitCaller(feedUrl);
        
        getListView().setOnItemLongClickListener(this);

    }

    private void retrofitCaller(String feedUrl) {

        // database
        DatabaseHelper mDbHelper = new DatabaseHelper(getActivity());
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ApiService service = ServiceGenerator.createService(ApiService.class);
        Call<RSS> call = service.feed(feedUrl);
        call.enqueue(new Callback<RSS>() {
            @Override
            public void onResponse(Call<RSS> call, Response<RSS> response) {
                (getView().findViewById(R.id.episode_progressbar)).setVisibility(View.INVISIBLE);
                feed = response.body(); // <-- this is the feed!
                if (feed != null) {
                    Log.d("feed", "feed is not null: \n" + feed.toString());
                    EpisodeBaseAdapter adapter = new EpisodeBaseAdapter(getActivity(), (ArrayList) feed.getChannel().itemList);
                    setListAdapter(adapter);

                    Cursor cursor = cursor(db);

                } else
                    try {
                        Log.e("feed", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }

            @Override
            public void onFailure(Call<RSS> call, Throwable t)
            {
                Toast.makeText(getActivity(), R.string.episode_error2, Toast.LENGTH_SHORT).show();
                Log.d("failure", t.toString());
            }
        });
    }


    private long databaseHelper(SQLiteDatabase db, String title, String link, String description, String url) {

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, title);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_LINK, link);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION, description);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_MEDIA_URL, url);

        // Insert the new row, returning the primary key value of the new row
//        Log.d("databaseHelper()", ""+newRowId+" "+title);
        //insertWithConflict instead of insert.
        long primaryKey = db.insertWithOnConflict(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                FeedReaderContract.FeedEntry.COLUMN_NAME_NULLABLE,
                values,SQLiteDatabase.CONFLICT_IGNORE);

        return primaryKey;
    }

    private Cursor cursor(SQLiteDatabase db) {

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                FeedReaderContract.FeedEntry._ID,
                FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
                FeedReaderContract.FeedEntry.COLUMN_NAME_LINK,
                FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION,
                FeedReaderContract.FeedEntry.COLUMN_NAME_MEDIA_URL
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = null; //FeedReaderContract.FeedEntry.TABLE_NAME + " DESC";

        return db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        mCallback.onEpisodeSelected(position);
        Channel.Item item = feed.getChannel().getItemList().get(position);

        DialogFragment dialogFragment = new EpisodeDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", item.getTitle());
        if (item.getAuthorList() != null) {
            String author = item.getAuthorList().get(0);
            args.putString("author", author);
        } else {
            args.putString("author", "");
        }
        args.putString("description",item.getDescription());
        args.putString("itemUrl",item.getLink());
        args.putString("mediaUrl",item.getEnclosure().getUrl());
        args.putString("pubDate",item.getPubDate());
        dialogFragment.setArguments(args);
        dialogFragment.show(getFragmentManager(), "Episode "+position);
//        super.onListItemClick(l, v, position, id);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        String url = "http://google.com"; // get the correct url from the cursor
        mCallback.onLongEpisodeClick(position, url);
        return true;
    }


}

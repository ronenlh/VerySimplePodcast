package com.ronen.studio08.verysimplepodcast.controllers;


import android.content.Context;
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

import com.ronen.studio08.verysimplepodcast.EpisodeBaseAdapter;
import com.ronen.studio08.verysimplepodcast.R;
import com.ronen.studio08.verysimplepodcast.model.FeedSnippet;
import com.ronen.studio08.verysimplepodcast.model.retrofit.ApiService;
import com.ronen.studio08.verysimplepodcast.model.retrofit.Channel;
import com.ronen.studio08.verysimplepodcast.model.retrofit.RSS;
import com.ronen.studio08.verysimplepodcast.model.retrofit.ServiceGenerator;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ronen on 3/5/16.
 */
public class EpisodeSelectorFragment extends ListFragment implements AdapterView.OnItemLongClickListener {

    private RSS mFeed;
    private onEpisodeSelectedListener mCallback;

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

        FeedSnippet feed = (FeedSnippet) getArguments().getSerializable("feed");
        retrofitCaller(feed.getFeedUrl());
        
        getListView().setOnItemLongClickListener(this);

    }

    private void retrofitCaller(String feedUrl) {

        ApiService service = ServiceGenerator.createService(ApiService.class);
        Call<RSS> call = service.feed(feedUrl);
        call.enqueue(new Callback<RSS>() {
            @Override
            public void onResponse(Call<RSS> call, Response<RSS> response) {
                View view = getView();
                if (view != null)
                    view.findViewById(R.id.episode_progressbar).setVisibility(View.INVISIBLE);

                mFeed = response.body(); // <-- this is the mFeed!
                if (mFeed != null) {
                    Log.d("mFeed", "mFeed is not null: \n" + mFeed.toString());
                    EpisodeBaseAdapter adapter = new EpisodeBaseAdapter(getActivity(), (ArrayList) mFeed.getChannel().itemList);
                    setListAdapter(adapter);

                } else
                    try {
                        Log.e("mFeed", response.errorBody().string());
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


    /*private long databaseHelper(SQLiteDatabase db, String title, String link, String description, String url) {

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, title);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_LINK, link);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION, description);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_MEDIA_URL, url);

        // Insert the new row, returning the primary key value of the new row
//        Log.d("databaseHelper()", ""+newRowId+" "+title);
        //insertWithConflict instead of insert.

        return db.insertWithOnConflict(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                FeedReaderContract.FeedEntry.COLUMN_NAME_NULLABLE,
                values,SQLiteDatabase.CONFLICT_IGNORE);
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
    }*/

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        mCallback.onEpisodeSelected(position);

        Channel.Item item = mFeed.getChannel().getItemList().get(position);

        DialogFragment dialogFragment = new EpisodeDialogFragment();
        Bundle args = new Bundle();


        FeedSnippet.Episode mEpisode = new FeedSnippet.Episode();
        mEpisode.setTitle(item.getTitle());
        if (item.getAuthorList() != null)
            mEpisode.setAuthor(item.getAuthorList().get(0));
        mEpisode.setDescription(item.getDescription());
        mEpisode.setItemUrl(item.getLink());
        mEpisode.setItemUrl(item.getEnclosure().getUrl());
        mEpisode.setPubDate(item.getPubDate());

        args.putSerializable("episode", mEpisode);
        dialogFragment.setArguments(args);
        dialogFragment.show(getFragmentManager(), "Episode " + position);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        String url = "http://google.com"; // get the correct url from the cursor
        mCallback.onLongEpisodeClick(position, url);
        return true;
    }


}

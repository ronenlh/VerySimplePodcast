package com.example.studio08.verysimplepodcast;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * Created by Ronen on 16/4/16.
 */
public class FeedSelectorFragment extends ListFragment {

    onFeedSelectedListener mCallback;

    interface onFeedSelectedListener {
        void onFeedSelected(int position, String feedUrl);
    }

    @Override
    public void onAttach(Context context) {
        // To allow a Fragment to communicate up to its Activity, you can define an interface in the Fragment class and implement it within the Activity.
        // The Fragment captures the interface implementation during its onAttach() lifecycle method and can then call the Interface methods in order to communicate with the Activity.
        super.onAttach(context);
        try {
            mCallback = (onFeedSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement onEpisodeSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.feed_selector_listfragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // sets sample array
        ArrayList<PodcastFeed> podcastFeedList = new ArrayList<>();
//        for (int i = 1; i < 20; i++) {
//            podcastFeedList.add(new PodcastFeed("Sample Podcast " + i));
//        }
        podcastFeedList.add(new PodcastFeed("Serial"));

        PodcastFeedBaseAdapter adapter = new PodcastFeedBaseAdapter(getContext(), podcastFeedList);

//        ArrayAdapter<PodcastFeed> adapter = new ArrayAdapter<PodcastFeed>(getContext(),android.R.layout.simple_list_item_1,podcastFeedList);
        setListAdapter(adapter);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // testing feedUrl, should come from list
        String[] feedUrl = {"http://feeds.serialpodcast.org/serialpodcast",
            "https://feeds.feedburner.com/99pi"};
        mCallback.onFeedSelected(position, feedUrl[0]);
        super.onListItemClick(l, v, position, id);
    }
}

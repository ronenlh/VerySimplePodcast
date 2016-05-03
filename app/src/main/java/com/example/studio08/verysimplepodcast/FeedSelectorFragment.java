package com.example.studio08.verysimplepodcast;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;




/**
 * Created by Ronen on 16/4/16.
 */
public class FeedSelectorFragment extends ListFragment {

    onChannelSelectedListener mCallback;

    interface onChannelSelectedListener {
        void onChannelSelected(int position);
    }

    @Override
    public void onAttach(Context context) {
        // To allow a Fragment to communicate up to its Activity, you can define an interface in the Fragment class and implement it within the Activity.
        // The Fragment captures the interface implementation during its onAttach() lifecycle method and can then call the Interface methods in order to communicate with the Activity.
        super.onAttach(context);
        try {
            mCallback = (onChannelSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement onChannelSelectedListener");
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
        for (int i = 1; i < 20; i++) {
            podcastFeedList.add(new PodcastFeed("Sample Podcast " + i));
        }

//        PodcastFeedAdapter adapter = new PodcastFeedAdapter(getContext(), podcastFeedList);

        ArrayAdapter<PodcastFeed> adapter = new ArrayAdapter<PodcastFeed>(getContext(),android.R.layout.simple_list_item_1,podcastFeedList);
        setListAdapter(adapter);

////        getListView().setOnItemClickListener(this);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        mCallback.onChannelSelected(position);
//        super.onListItemClick(l, v, position, id);
    }
}

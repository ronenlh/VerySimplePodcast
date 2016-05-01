package com.example.studio08.verysimplepodcast;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        super.onAttach(context);
        try {
            mCallback = (onChannelSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
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
//        PodcastFeed[] podcastFeedTestArray = {new PodcastFeed(), new PodcastFeed(), new PodcastFeed(), new PodcastFeed(), new PodcastFeed(), new PodcastFeed(), new PodcastFeed(), new PodcastFeed(), new PodcastFeed(), new PodcastFeed(), new PodcastFeed(), new PodcastFeed()};
//        ArrayList<PodcastFeed> podcastFeedList = new ArrayList<>(Arrays.asList(podcastFeedTestArray));
//
//        PodcastFeedAdapter podcastFeedAdapter = new PodcastFeedAdapter(getContext(), podcastFeedList);
//        setListAdapter(podcastFeedAdapter);
////        getListView().setOnItemClickListener(this);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();
        mCallback.onChannelSelected(position);
        super.onListItemClick(l, v, position, id);
    }
}

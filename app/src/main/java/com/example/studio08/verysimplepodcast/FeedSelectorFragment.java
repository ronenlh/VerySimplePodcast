package com.example.studio08.verysimplepodcast;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;




/**
 * Created by Ronen on 16/4/16.
 */
public class FeedSelectorFragment extends ListFragment {
    private ArrayList<PodcastFeed> podcastFeedList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.feed_selector_listfragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // sets sample array
        PodcastFeed[] podcastFeedTestArray = {new PodcastFeed(), new PodcastFeed(), new PodcastFeed(), new PodcastFeed(), new PodcastFeed(), new PodcastFeed(), new PodcastFeed(), new PodcastFeed(), new PodcastFeed(), new PodcastFeed(), new PodcastFeed(), new PodcastFeed()};
        podcastFeedList = new ArrayList<>(Arrays.asList(podcastFeedTestArray));

        PodcastFeedAdapter podcastFeedAdapter = new PodcastFeedAdapter(getContext(), podcastFeedList);
        setListAdapter(podcastFeedAdapter);
//        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getActivity(), PodcastFeedActivity.class);
        intent.putExtra("name", podcastFeedList.get(position).getTitle());
        intent.putExtra("description", podcastFeedList.get(position).getDescription());
        intent.putExtra("thumbnailId", podcastFeedList.get(position).getThumbnailId());
        startActivity(intent);
    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//    }
}

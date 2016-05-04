package com.example.studio08.verysimplepodcast;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by studio08 on 4/10/2016.
 */
public class PodcastFeedBaseAdapter extends BaseAdapter {

    Context context;
    ArrayList<PodcastFeed> podcastFeeds;

    public PodcastFeedBaseAdapter(Context context, ArrayList<PodcastFeed> podcastFeeds) {
        this.context = context;
        this.podcastFeeds = podcastFeeds;
    }

    @Override
    public int getCount() {
        return podcastFeeds.size();
    }

    @Override
    public PodcastFeed getItem(int position) {
        return podcastFeeds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View row = null;

        if (convertView == null) {
            holder = new Holder();
            row = layoutInflater.inflate(R.layout.main_row, null);
            holder.title = (TextView) row.findViewById(R.id.title_textView);
            holder.description = (TextView) row.findViewById(R.id.description_textView);
            holder.thumbnail = (ImageView) row.findViewById(R.id.thumbnail_imageView);
            row.setTag(holder);
        } else {
            row = convertView;
            holder = (Holder) row.getTag();
        }
        holder.title.setText(getItem(position).getTitle());
        holder.description.setText(getItem(position).getDescription());
        holder.thumbnail.setImageResource(getItem(position).getThumbnailId());

        return row;
    }

    class Holder {
        TextView title, description;
        ImageView thumbnail;
    }
}

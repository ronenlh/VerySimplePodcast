package com.ronen.studio08.verysimplepodcast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by studio08 on 4/10/2016.
 */
public class FeedBaseAdapter extends BaseAdapter {

    Context context;
    ArrayList<FeedSample> podcastFeeds;

    public FeedBaseAdapter(Context context, ArrayList<FeedSample> podcastFeeds) {
        this.context = context;
        this.podcastFeeds = podcastFeeds;
    }

    @Override
    public int getCount() {
        return podcastFeeds.size();
    }

    @Override
    public FeedSample getItem(int position) {
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
            convertView = layoutInflater.inflate(R.layout.main_row, null);
            holder.title = (TextView) convertView.findViewById(R.id.title_textView);
            holder.creator = (TextView) convertView.findViewById(R.id.creator_textView);
            holder.thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail_imageView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.title.setText(getItem(position).getTitle());
        holder.creator.setText(getItem(position).getDescription());
        holder.thumbnail.setImageResource(getItem(position).getThumbnailId());

        return convertView;
    }

    class Holder {
        TextView title, creator;
        ImageView thumbnail;
    }
}

package com.ronen.studio08.verysimplepodcast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ronen.studio08.verysimplepodcast.model.retrofit.Channel;

import java.util.ArrayList;

/**
 * Created by studio08 on 4/10/2016.
 */
public class EpisodeBaseAdapter extends BaseAdapter {

    private final Context context;
    private final ArrayList<Channel.Item> items;

    public EpisodeBaseAdapter(Context context, ArrayList<Channel.Item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Channel.Item getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        if (convertView == null) {
            holder = new Holder();
            convertView = layoutInflater.inflate(R.layout.episode_row, null);
            holder.title = (TextView) convertView.findViewById(R.id.episode_title);
            holder.description = (TextView) convertView.findViewById(R.id.episode_description);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.title.setText(getItem(position).getTitle());
        holder.description.setText(getItem(position).getDescription());

        return convertView;
    }

    class Holder {
        TextView title, description;
    }
}

package com.ronen.studio08.verysimplepodcast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ronen.studio08.verysimplepodcast.model.RssModelClass.Channel;

import java.util.List;

/**
 * Created by Ronen on 31/8/16.
 */

public class InfoListBaseAdapter extends BaseAdapter {
    Context mContext;
    List<Channel.Item> items;

    public InfoListBaseAdapter(Context context, List<Channel.Item> items) {
        mContext = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Channel.Item getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        InfoListBaseAdapter.Holder holder;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        if (convertView == null) {
            holder = new InfoListBaseAdapter.Holder();
            convertView = layoutInflater.inflate(R.layout.info_row, null);

            holder.mTitle = (TextView) convertView.findViewById(R.id.titleRowTextView);
            holder.mDescription = (TextView) convertView.findViewById(R.id.descriptionRowTextView);

            convertView.setTag(holder);
        } else {
            holder = (InfoListBaseAdapter.Holder) convertView.getTag();
        }

        holder.mTitle.setText(getItem(position).getTitle());
        holder.mDescription.setText(getItem(position).getDescription());

        return convertView;
    }

    class Holder {
        TextView mTitle, mDescription;
    }
}

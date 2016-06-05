package com.ronen.studio08.verysimplepodcast;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ronen.studio08.verysimplepodcast.itunes.Search;
import com.ronen.studio08.verysimplepodcast.itunestop.Entry;
import com.ronen.studio08.verysimplepodcast.itunestop.Top;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;


/**
 * Created by Ronen on 5/6/16.
 */
public class ItunesTopRVAdapter extends RecyclerView.Adapter<ItunesTopRVAdapter.feedViewHolder>{
    Context context;
    Top top;
    static View view;

    public ItunesTopRVAdapter(Context context, Top top) {
        this.context = context;
        this.top = top;
    }


    @Override
    public ItunesTopRVAdapter.feedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.itunes_feed_row, parent, false);
        return new feedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(feedViewHolder holder, int position) {
        // onBindViewHolder is called when the SO binds the view with the data -- or, in other words, the data is shown in the UI.
        Entry entry = top.getFeed().getEntry().get(position);
        holder.title.setText(entry.getImName().getLabel());
        holder.artist.setText(entry.getImArtist().getLabel());
        Picasso.
                with(context).
                load(entry.getImImage().get(2).getLabel()).
                // error(R.drawable.ic_broken_image_black_24dp).
                into(holder.thumbnail);

    }


    @Override
    public int getItemCount() {
        return top.getFeed().getEntry().size();
    }

    public static class feedViewHolder extends RecyclerView.ViewHolder {
        protected ImageView thumbnail;
        protected TextView title, artist;

        public feedViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            thumbnail = (ImageView) itemView.findViewById(R.id.add_thumbnail);
            title = (TextView) itemView.findViewById(R.id.podTitle);
            artist = (TextView) itemView.findViewById(R.id.podArtist);
        }
    }
}

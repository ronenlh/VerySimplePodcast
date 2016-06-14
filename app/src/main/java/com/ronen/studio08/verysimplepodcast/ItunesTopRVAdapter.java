package com.ronen.studio08.verysimplepodcast;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ronen.studio08.verysimplepodcast.itunestop.Entry;
import com.ronen.studio08.verysimplepodcast.itunestop.Top;
import com.squareup.picasso.Picasso;


/**
 * Created by Ronen on 5/6/16.
 */
public class ItunesTopRVAdapter extends RecyclerView.Adapter<ItunesTopRVAdapter.topViewHolder>{
    Context context;
    Top top;
    static View view;

    public ItunesTopRVAdapter(Context context, Top top) {
        this.context = context;
        this.top = top;
    }


    @Override
    public topViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.itunes_feed_row, parent, false);
        return new topViewHolder(itemView, top);
    }

    @Override
    public void onBindViewHolder(topViewHolder holder, int position) {
        // onBindViewHolder is called when the SO binds the view with the data -- or, in other words, the data is shown in the UI.
        Entry entry = top.getFeed().getEntry().get(position);
        holder.title.setText(entry.getImName().getLabel());
        holder.artist.setText(entry.getImArtist().getLabel());
        Picasso.with(context)
                .load(entry.getImImage().get(2).getLabel())
                // error(R.drawable.ic_broken_image_black_24dp).
                .into(holder.thumbnail);

    }




    @Override
    public int getItemCount() {
        return top.getFeed().getEntry().size();
    }

    public static class topViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected ImageView thumbnail;
        protected TextView title, artist;
        Top top;

        public topViewHolder(View itemView, Top top) {
            super(itemView);
            itemView.setOnClickListener(this);
            view = itemView;
            this.top = top;
            thumbnail = (ImageView) itemView.findViewById(R.id.add_thumbnail);
            title = (TextView) itemView.findViewById(R.id.podTitle);
            artist = (TextView) itemView.findViewById(R.id.podArtist);
        }


        @Override
        public void onClick(View v) {
            // there is no direct link to the RSS feed in top podcasts API :( need to workaround
            Entry entry = top.getFeed().getEntry().get(getAdapterPosition());
            Log.d("topViewHolder", entry.getImName().getLabel());
            Log.d("topViewHolder", entry.getId().getAttributes().getImId());

            /*java.lang.IllegalStateException: Activity has been destroyed*/
//            ItunesSearchActivity itunesSearchActivity = new ItunesSearchActivity();
//            itunesSearchActivity.search(entry);
        }
    }
}

package com.ronen.studio08.verysimplepodcast;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ronen.studio08.verysimplepodcast.itunes.Result;
import com.ronen.studio08.verysimplepodcast.itunes.Search;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


/**
 * Created by Ronen on 5/6/16.
 */
public class ItunesSeachRVAdapter extends RecyclerView.Adapter<ItunesSeachRVAdapter.searchViewHolder>{
    Context context;
    Search search;
    static View view;

    public ItunesSeachRVAdapter(Context context, Search search) {
        this.context = context;
        this.search = search;
    }


    @Override
    public searchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.itunes_feed_row, parent, false);
        return new searchViewHolder(itemView, search);
    }

    @Override
    public void onBindViewHolder(searchViewHolder holder, int position) {
        // onBindViewHolder is called when the SO binds the view with the data -- or, in other words, the data is shown in the UI.
        Result result = search.getResults().get(position);
        holder.title.setText(result.getCollectionCensoredName());
        holder.artist.setText(result.getArtistName());
        Picasso.with(context).
        load(search.getResults().get(position).getArtworkUrl100()).
                // transform(new CropSquareTransformation()).
                // error(R.drawable.ic_broken_image_black_24dp).
                        into(holder.thumbnail, new Callback() {
                    @Override
                    public void onSuccess() {
//                        view.findViewById(R.id.RVprogressBar).setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError() {

                    }
                });

    }

    @Override
    public int getItemCount() {
        return search.getResultCount();
    }

    public static class searchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected ImageView thumbnail;
        protected TextView title, artist;
        protected Search search;

        public searchViewHolder(View itemView, Search search) {
            super(itemView);
            itemView.setOnClickListener(this);
            view = itemView;
            this.search = search;
            thumbnail = (ImageView) itemView.findViewById(R.id.add_thumbnail);
            title = (TextView) itemView.findViewById(R.id.podTitle);
            artist = (TextView) itemView.findViewById(R.id.podArtist);
        }

        @Override
        public void onClick(View v) {
            Result result = search.getResults().get(getAdapterPosition());
            Log.d("searchViewHolder",result.getArtistName());
        }
    }
}

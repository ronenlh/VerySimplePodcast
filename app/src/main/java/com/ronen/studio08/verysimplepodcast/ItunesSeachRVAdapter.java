package com.ronen.studio08.verysimplepodcast;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ronen.studio08.verysimplepodcast.itunes.Search;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


/**
 * Created by Ronen on 5/6/16.
 */
public class ItunesSeachRVAdapter extends RecyclerView.Adapter<ItunesSeachRVAdapter.feedViewHolder>{
    Context context;
    Search search;
    static View view;

    public ItunesSeachRVAdapter(Context context, Search search) {
        this.context = context;
        this.search = search;
    }


    @Override
    public ItunesSeachRVAdapter.feedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.add_feed_row, parent, false);
        return new feedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(feedViewHolder holder, int position) {
        // onBindViewHolder is called when the SO binds the view with the data -- or, in other words, the data is shown in the UI.
//        holder.thumbnail.setImageResource(R.drawable.thumbnail99pi);  // feedFromServer.getImgHref()
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

    public static class feedViewHolder extends RecyclerView.ViewHolder {
        protected ImageView thumbnail;

        public feedViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            thumbnail = (ImageView) itemView.findViewById(R.id.add_thumbnail);
        }
    }
}

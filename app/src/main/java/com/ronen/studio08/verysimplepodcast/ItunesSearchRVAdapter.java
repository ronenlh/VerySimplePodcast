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
public class ItunesSearchRVAdapter extends RecyclerView.Adapter<ItunesSearchRVAdapter.searchViewHolder>{
    private final Context context;
    private final Search search;
    private static View view;
    private ItunesSearchFragment.OnSearchItemSelectedListener listener;

    public ItunesSearchRVAdapter(Context context, Search search) {
        this.context = context;
        this.search = search;
    }


    @Override
    public searchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.itunes_feed_row, parent, false);
        return new searchViewHolder(itemView, search /*, context */);
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

    public class searchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView thumbnail;
        final TextView title;
        final TextView artist;
        final Search search;
//        protected Context context;

        public searchViewHolder(View itemView, Search search /*, Context context */) {
            super(itemView);
            itemView.setOnClickListener(this);
            view = itemView;
            this.search = search;
//            this.context = context;
            thumbnail = (ImageView) itemView.findViewById(R.id.add_thumbnail);
            title = (TextView) itemView.findViewById(R.id.podTitle);
            artist = (TextView) itemView.findViewById(R.id.podArtist);
        }

        @Override
        public void onClick(View v) {
            Result result = search.getResults().get(getAdapterPosition());
            Log.d("searchViewHolder",result.getCollectionName());
            if (listener != null) {
                listener.onItemSelected(result);
            }

//            ItunesSearchActivity itunesSearchActivity = new ItunesSearchActivity();
//            itunesSearchActivity.retrofitCaller(result);
//            if(context instanceof ItunesSearchActivity){
//                ((ItunesSearchActivity)context).retrofitCaller(result);
//            }


        }
    }

    public void setItemListener(ItunesSearchFragment.OnSearchItemSelectedListener listener) {
        this.listener = listener;
    }
}

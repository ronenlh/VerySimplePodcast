package com.ronen.studio08.verysimplepodcast;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ronen.studio08.verysimplepodcast.model.retrofitCloud.SampleFeed;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

/**
 * Created by studio08 on 5/30/2016.
 */
public class AddFeedRVAdapter extends RecyclerView.Adapter<AddFeedRVAdapter.feedViewHolder> {

    private final Context context;
    private final List<SampleFeed> sampleFeeds;
    private static View view;



    public AddFeedRVAdapter(Context context, List<SampleFeed> sampleFeeds) {
        this.context = context;
        this.sampleFeeds = sampleFeeds;
    }


    @Override
    public feedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // onCreateViewHolder is called whenever a new instance of our ViewHolder class is created


        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.add_feed_row, parent, false);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("setOnClickListener", "works");
            }
        });

        return new feedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(feedViewHolder holder, int position) {
        // onBindViewHolder is called when the SO binds the view with the data -- or, in other words, the data is shown in the UI.
//        holder.thumbnail.setImageResource(R.drawable.thumbnail99pi);  // feedFromServer.getImgHref()
        Log.d("onBindViewHolder",""+ sampleFeeds.get(position).getImgHref());
        Picasso.with(context).
//                load("https://media.simplecast.com/podcast/image/1684/1455140618-artwork.jpg").
                load(sampleFeeds.get(position).getImgHref()).
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
        return sampleFeeds.size();
    }

    public static class feedViewHolder extends RecyclerView.ViewHolder {

        final ImageView thumbnail;

        public feedViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            thumbnail = (ImageView) itemView.findViewById(R.id.add_thumbnail);
        }

        public interface AddFeedRVAdapterClickListener {
            void AddFeedRVonClick(View caller);
        }
    }

    private class CropSquareTransformation implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        @Override
        public String key() { return "square()"; }
    }
}

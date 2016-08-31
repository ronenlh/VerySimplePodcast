package com.ronen.studio08.verysimplepodcast;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ronen.studio08.verysimplepodcast.model.RssModelClass.Channel;

import java.io.IOException;
import java.util.List;

/**
 * Created by Ronen on 31/8/16.
 */

public class InfoListBaseAdapter extends BaseAdapter implements MediaPlayer.OnPreparedListener {
    private final String TAG = "InfoListBaseAdapter";
    Context mContext;
    List<Channel.Item> items;
    MediaPlayer mediaPlayer;
    private boolean isPlaying;

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
            holder.playImageView = (ImageView) convertView.findViewById(R.id.playImageView);
            holder.infoImageView = (ImageView) convertView.findViewById(R.id.infoImageView);

            convertView.setTag(holder);
        } else {
            holder = (InfoListBaseAdapter.Holder) convertView.getTag();
        }

        holder.mTitle.setText(getItem(position).getTitle());
        holder.mDescription.setText(getItem(position).getDescription());

        holder.playImageView.setTag(R.id.intent_data_id, position);
        holder.playImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer position = (Integer) view.getTag(R.id.intent_data_id);
                Log.d(TAG, "onClick: " + position);

                startPlayer(items.get(position).getEnclosure().getUrl());
                changeToPause(view);
                isPlaying = !isPlaying;
            }
        });
        holder.infoImageView.setTag(R.id.intent_data_id, position);
        holder.infoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer position = (Integer) view.getTag(R.id.intent_data_id);
                Log.d(TAG, "onClick: " + position);
            }
        });

        return convertView;
    }

    private void changeToPause(View view) {
        ImageView imageView = (ImageView) view;
        if (!isPlaying) {
            imageView.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
        } else {
            imageView.setImageResource(R.drawable.ic_play_circle_filled_black_24dp);
        }
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }


    class Holder {
        TextView mTitle, mDescription;
        ImageView playImageView, infoImageView;

    }

    public void startPlayer(String mediaUrl) {
        Log.d(TAG, "play: "+mediaUrl);
        if (!isPlaying) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaPlayer.setDataSource(mediaUrl);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(this);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            mediaPlayer.pause();
        }
    }
}

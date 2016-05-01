package com.example.studio08.verysimplepodcast;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by studio08 on 4/19/2016.
 */
public class ChannelListFragment extends ListFragment {

    onEpisodeSelectedListener mCallback;

    interface onEpisodeSelectedListener {
        void onEpisodeSelected(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (onEpisodeSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement onEpisodeSelectedListener");
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
        mCallback.onEpisodeSelected(position);
    }
}

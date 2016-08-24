package com.ronen.studio08.verysimplepodcast;



import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ronen.studio08.verysimplepodcast.model.PodcastLab;


/**
 * Created by Ronen on 16/4/16.
 */
public class FeedSelectorFragment extends ListFragment implements AdapterView.OnItemLongClickListener {

    private OnFeedSelectedListener mCallback;
    private FeedDeletedListener dCallback;
    private FeedCursorAdapter adapter;
    private ActionMode mActionMode;

    interface OnFeedSelectedListener {
        void onFeedSelected(int position, String feedUrl);
    }

    interface FeedDeletedListener {
        void feedDeletedFeedback();
    }

    @Override
    public void onAttach(Context context) {
        // To allow a Fragment to communicate up to its Activity, you can define an interface in the Fragment class and implement it within the Activity.
        // The Fragment captures the interface implementation during its onAttach() lifecycle method and can then call the Interface methods in order to communicate with the Activity.
        super.onAttach(context);

        // Checks if context implements the required interfaces
        try {
            mCallback = (OnFeedSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement onEpisodeSelectedListener");
        }
        try {
            dCallback = (FeedDeletedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FeedDeletedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.feed_selector_listfragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Cursor cursor = PodcastLab.get(getContext()).queryFeeds();
        adapter = FeedCursorAdapter.get(getActivity(), cursor);

        setListAdapter(adapter);

        getListView().setOnItemLongClickListener(this);
    }




    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Cursor cursor = (Cursor) l.getItemAtPosition(position);
        Log.d("onListItemClick", cursor.getString(3));
        mCallback.onFeedSelected(position, cursor.getString(3));
    }


    // Action menu when long clicking in the FeedSample list.
    private final ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.feed_menu, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }
        /** TODO: implement undo button, modularize */
        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.delete:

                    PodcastLab.get(getContext()).deleteFeed(mode.getTag().toString());

                    // UI feedback on UI thread:
                    dCallback.feedDeletedFeedback();

                    adapter.notifyDataSetInvalidated();

                    mode.finish(); // Action picked, so close the CAB
                    return true;
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };



    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor cursor = (Cursor) parent.getItemAtPosition(position);
//        DialogFragment dialogFragment = FeedDialogFragment.newInstance(queryFeeds.getString(1));
//        dialogFragment.show(getFragmentManager(), "dialog");
        long rowId = cursor.getLong(cursor.getColumnIndex("_id"));

        if (mActionMode != null) {
            return false;
        }
        mActionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(mActionModeCallback);
        mActionMode.setTag(String.valueOf(rowId));
        view.setSelected(true);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();

        adapter.notifyDataSetInvalidated();
        setListAdapter(adapter);
    }
}

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

import com.ronen.studio08.verysimplepodcast.database.DbHelper;
import com.ronen.studio08.verysimplepodcast.database.FeedsContract;


/**
 * Created by Ronen on 16/4/16.
 */
public class FeedSelectorFragment extends ListFragment implements AdapterView.OnItemLongClickListener {

//    ArrayList<FeedSample> podcastFeedList;
private onFeedSelectedListener mCallback;
    private feedDeletedListener dCallback;
    private FeedCursorAdapter adapter;

    private ActionMode mActionMode;

    interface onFeedSelectedListener {
        void onFeedSelected(int position, String feedUrl);
    }

    interface feedDeletedListener {
        void feedDeleted();
    }

    @Override
    public void onAttach(Context context) {
        // To allow a Fragment to communicate up to its Activity, you can define an interface in the Fragment class and implement it within the Activity.
        // The Fragment captures the interface implementation during its onAttach() lifecycle method and can then call the Interface methods in order to communicate with the Activity.
        super.onAttach(context);
        try {
            mCallback = (onFeedSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement onEpisodeSelectedListener");
        }
        try {
            dCallback = (feedDeletedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement feedDeletedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.feed_selector_listfragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DbHelper dbHelper = new DbHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = cursor(db);
        adapter = FeedCursorAdapter.FeedCursorAdapterFactory(getActivity(), cursor);

        setListAdapter(adapter);

        getListView().setOnItemLongClickListener(this);
    }

    private Cursor cursor(SQLiteDatabase db) {

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                FeedsContract.FeedEntry._ID, //
                FeedsContract.FeedEntry.COLUMN_NAME_TITLE, // 1
                FeedsContract.FeedEntry.COLUMN_NAME_CREATOR, // 2
                FeedsContract.FeedEntry.COLUMN_NAME_FEED_URL, // 3
                FeedsContract.FeedEntry.COLUMN_NAME_SUBTITLE, // 4
                FeedsContract.FeedEntry.COLUMN_NAME_THUMBNAIL // 5
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = null; //FeedReaderContract.FeedEntry.TABLE_NAME + " DESC";

        return db.query(
                FeedsContract.FeedEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
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
                    DbHelper dbHelper = new DbHelper(getActivity());
//                    (new DbHelper(getContext())).deleteRow((String) mode.getTag());
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    String deleteQuery = "DELETE FROM  "+ FeedsContract.FeedEntry.TABLE_NAME +" where "+FeedsContract.FeedEntry._ID+"='" +  mode.getTag() + "'";
                    Log.d("Query", deleteQuery);
                    db.execSQL(deleteQuery);

                    // UI feedback on UI thread:
                    dCallback.feedDeleted();
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
//        DialogFragment dialogFragment = FeedDialogFragment.newInstance(cursor.getString(1));
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

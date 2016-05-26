package com.ronen.studio08.verysimplepodcast;

import android.app.AlertDialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by studio08 on 5/10/2016.
 */
public class FeedDialogFragment extends DialogFragment {

    public static FeedDialogFragment newInstance(String title) {
        FeedDialogFragment fragment = new FeedDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title", "FeedSample");

        return new AlertDialog.Builder(getActivity())
//                .setIcon(R.drawable.alert_dialog_icon)
                .setTitle(title)
                .setPositiveButton("Open",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        }
                )
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        }
                )
                .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*public boolean deleteTitle(String name)
                        {
                            return db.delete(DATABASE_TABLE, KEY_NAME + "=" + name, null) > 0;
                        }*/
                    }
                })
                .create();
    }
}

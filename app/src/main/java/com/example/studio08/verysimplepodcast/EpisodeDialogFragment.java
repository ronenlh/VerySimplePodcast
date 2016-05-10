package com.example.studio08.verysimplepodcast;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/**
 * Created by studio08 on 5/10/2016.
 */
public class EpisodeDialogFragment extends DialogFragment {

    String title;
    String description;
    String feedUrl;
    String author;
    String pubDate;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        title = getArguments().getString("title");
        description = getArguments().getString("description");
        feedUrl = getArguments().getString("feedUrl");
        author = getArguments().getString("author");
        pubDate = getArguments().getString("pubDate");

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(""+title)
                .setMessage(""+description)
//                .setMessage(""+author)
//                .setMessage(""+pubDate)
                .setPositiveButton(R.string.dialog_play, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton(R.string.dialog_close, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}

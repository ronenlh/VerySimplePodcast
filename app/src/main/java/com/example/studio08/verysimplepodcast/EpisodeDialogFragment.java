package com.example.studio08.verysimplepodcast;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

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

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View content = inflater.inflate(R.layout.fragment_dialog_episode, null);
        builder.setView(content);
        ((TextView) content.findViewById(R.id.dialog_title)).setText(""+title);
        ((TextView) content.findViewById(R.id.dialog_author)).setText(""+author);
        ((TextView) content.findViewById(R.id.dialog_date)).setText(""+pubDate);
        ((TextView) content.findViewById(R.id.dialog_description)).setText(""+description);

        builder.setPositiveButton(R.string.dialog_play, new DialogInterface.OnClickListener() {
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

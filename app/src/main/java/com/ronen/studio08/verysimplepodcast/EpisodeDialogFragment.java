package com.ronen.studio08.verysimplepodcast;

import android.app.Dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by studio08 on 5/10/2016.
 */
public class EpisodeDialogFragment extends DialogFragment {

    String title;
    String description;
    String itemUrl;
    String mediaUrl;
    String author;
    String pubDate;

    onPlaySelectedListener playCallback;
    onInfoSelectedListener infoCallback;

    interface onPlaySelectedListener {
        void onPlaySelected(String itemUrl);
    }

    interface onInfoSelectedListener {
        void onInfoSelected(String itemUrl);
    }

    @Override
    public void onAttach(Context context) {
        // To allow a Fragment to communicate up to its Activity, you can define an interface in the Fragment class and implement it within the Activity.
        // The Fragment captures the interface implementation during its onAttach() lifecycle method and can then call the Interface methods in order to communicate with the Activity.
        super.onAttach(context);
        try {
            playCallback = (onPlaySelectedListener) context;
            infoCallback = (onInfoSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement onPlaySelectedListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        title = getArguments().getString("title");
        description = getArguments().getString("description");
        itemUrl = getArguments().getString("itemUrl");
        mediaUrl = getArguments().getString("mediaUrl");
        author = getArguments().getString("author");
        pubDate = getArguments().getString("pubDate");

        /** TODO: disable webview button if itemURL is "" */

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

        // date parser and formatter
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z ", Locale.ENGLISH);
            SimpleDateFormat newFormat = new SimpleDateFormat("dd.MM.yyyy");

            Date pubDateParsed = dateFormat.parse(pubDate);
            String newFormatDate = newFormat.format(pubDateParsed);

            ((TextView) content.findViewById(R.id.dialog_date)).setText(""+newFormatDate);
        } catch (ParseException e) {
//            e.printStackTrace();
            Log.w("EpisodeDialogFragment", "problem parsing the date, displaying unparsed date.");
            ((TextView) content.findViewById(R.id.dialog_date)).setText(""+pubDate);
        }

        ((TextView) content.findViewById(R.id.dialog_description)).setText(""+description);

        builder.setPositiveButton(R.string.dialog_play, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        playCallback.onPlaySelected(mediaUrl);
                    }
                })
                .setNeutralButton(R.string.dialog_info, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        infoCallback.onInfoSelected(itemUrl);
                    }
                })
                .setNegativeButton(R.string.dialog_close, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}

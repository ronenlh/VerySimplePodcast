package com.ronen.studio08.verysimplepodcast.controllers;

import android.annotation.TargetApi;
import android.app.Dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ronen.studio08.verysimplepodcast.R;
import com.ronen.studio08.verysimplepodcast.model.FeedSnippet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by studio08 on 5/10/2016.
 */
public class EpisodeDialogFragment extends DialogFragment {

    private FeedSnippet.Episode mEpisode;
    private onPlaySelectedListener playCallback;
    private onInfoSelectedListener infoCallback;

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
                    + " must implement onAddSelectedListener");
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mEpisode = (FeedSnippet.Episode) getArguments().getSerializable("episode");

        String mEpisodeTitle = mEpisode.getTitle();
        String mEpisodeDescription = mEpisode.getDescription();
        final String mItemUrl = mEpisode.getItemUrl();
        final String mMediaUrl = mEpisode.getMediaUrl();
        String mEpisodeAuthor = mEpisode.getAuthor();
        String mEpisodePubDate = mEpisode.getPubDate();

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View content = inflater.inflate(R.layout.fragment_dialog_episode, null);
        builder.setView(content);
        ((TextView) content.findViewById(R.id.dialog_title)).setText(""+ mEpisodeTitle);
        ((TextView) content.findViewById(R.id.dialog_author)).setText(""+ mEpisodeAuthor);

        // date parser and formatter
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z ", Locale.ENGLISH);
            SimpleDateFormat newFormat = new SimpleDateFormat("dd.MM.yyyy");

            Date pubDateParsed = dateFormat.parse(mEpisodePubDate);
            String newFormatDate = newFormat.format(pubDateParsed);

            ((TextView) content.findViewById(R.id.dialog_date)).setText(""+newFormatDate);
        } catch (ParseException e) {
//            e.printStackTrace();
            Log.w("EpisodeDialogFragment", "problem parsing the date, displaying unparsed date.");
            ((TextView) content.findViewById(R.id.dialog_date)).setText(""+ mEpisodePubDate);
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            ((TextView) content.findViewById(R.id.dialog_description)).setText(Html.fromHtml(mEpisodeDescription, Html.FROM_HTML_SEPARATOR_LINE_BREAK_HEADING));
//        } else {
            ((TextView) content.findViewById(R.id.dialog_description)).setText(mEpisodeDescription);
//        }

        if (!mItemUrl.equals("")) {
            builder.setNeutralButton(R.string.dialog_info, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    infoCallback.onInfoSelected(mItemUrl);
                }
            });
        }
        builder.setPositiveButton(R.string.dialog_play, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                playCallback.onPlaySelected(mMediaUrl);
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
}

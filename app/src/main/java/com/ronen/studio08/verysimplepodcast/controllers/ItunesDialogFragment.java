package com.ronen.studio08.verysimplepodcast.controllers;

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

import com.ronen.studio08.verysimplepodcast.R;
import com.ronen.studio08.verysimplepodcast.model.Converter;
import com.ronen.studio08.verysimplepodcast.model.itunesSearchModelClass.Result;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by studio08 on 5/10/2016.
 */
public class ItunesDialogFragment extends DialogFragment {

    private onAddSelectedListener playCallback;
    private onInfoSelectedListener infoCallback;
    private Result mResult;

    interface onAddSelectedListener {
        void onAddSelected(Result itemUrl);
    }

    interface onInfoSelectedListener {
        void onInfoSelected(Result itemUrl);
    }

    @Override
    public void onAttach(Context context) {
        // To allow a Fragment to communicate up to its Activity, you can define an interface in the Fragment class and implement it within the Activity.
        // The Fragment captures the interface implementation during its onAttach() lifecycle method and can then call the Interface methods in order to communicate with the Activity.
        super.onAttach(context);
        try {
            playCallback = (onAddSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement onAddSelectedListener");
        }
        try {
            infoCallback = (onInfoSelectedListener) context;
        } catch (ClassCastException e) {
        throw new ClassCastException(context.toString()
                + " must implement onInfoSelectedListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mResult = (Result) getArguments().getSerializable("result");

        String title = mResult.getCollectionName();
        List<String> descriptionList = mResult.getGenres();
        String description = Converter.getGenresList(descriptionList);
        String itemUrl = "";
        String mediaUrl = mResult.getArtworkUrl600();
        String author = mResult.getArtistName();
        String pubDate = mResult.getReleaseDate();

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

            ((TextView) content.findViewById(R.id.dialog_date)).setText(formatDate(pubDate));

        } catch (ParseException e) {
//            e.printStackTrace();
            Log.w("EpisodeDialogFragment", "problem parsing the date, displaying unparsed date.");
            ((TextView) content.findViewById(R.id.dialog_date)).setText(""+pubDate);
        }

        ((TextView) content.findViewById(R.id.dialog_description)).setText("" + description);

        if (!itemUrl.equals(""))
            builder.setNeutralButton(R.string.dialog_info, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    infoCallback.onInfoSelected(mResult);
                }
            });

        builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                playCallback.onAddSelected(mResult);
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

    private String formatDate(String pubDate) throws ParseException {

        DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z ", Locale.ENGLISH);
        Date parsedDate = dateFormat.parse(pubDate);

        DateFormat newFormat = DateFormat.getDateInstance();
        return newFormat.format(parsedDate);
    }
}

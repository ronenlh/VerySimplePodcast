package com.ronen.studio08.verysimplepodcast;

import android.os.Bundle;

import com.takisoft.fix.support.v7.preference.PreferenceFragmentCompat;

/**
 * Created by Ronen on 22/5/16.
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    public static final String KEY_SKIP = "skip";
    public static final String KEY_THEME = "nigth_theme";
    public static final String KEY_EXPLICIT = "explicit";
    public static final String KEY_COUNTRY = "country";
    public static final String KEY_LANG = "language";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onCreatePreferencesFix(Bundle savedInstanceState, String rootKey) {

    }


}

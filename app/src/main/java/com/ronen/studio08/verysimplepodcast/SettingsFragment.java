package com.ronen.studio08.verysimplepodcast;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.Preference;

import com.takisoft.fix.support.v7.preference.PreferenceFragmentCompat;

/**
 * Created by Ronen on 22/5/16.
 */
public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

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


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference connectionPref = findPreference(key);
        switch (key) {
            case KEY_SKIP:
                connectionPref.setSummary(sharedPreferences.getString(key, "")+" seconds");
                break;
            case KEY_COUNTRY:
                connectionPref.setSummary(sharedPreferences.getString(key, "Country Used for Podcast search"));
        }
    }

    @Override
    public void onResume() {
        /* For proper lifecycle management in the activity,
        we recommend that you register and unregister your
        SharedPreferences.OnSharedPreferenceChangeListener during the onResume() and onPause() callbacks, respectively: */
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}

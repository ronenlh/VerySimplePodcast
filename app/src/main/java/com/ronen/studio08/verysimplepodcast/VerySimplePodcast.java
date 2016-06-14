package com.ronen.studio08.verysimplepodcast;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.support.multidex.MultiDex;
import android.util.Log;

import java.util.Locale;

/**
 * Created by studio08 on 5/19/2016.
 * Base class for maintaining global application state.
 * You can provide your own implementation by creating a subclass and specifying the
 * fully-qualified name of this subclass as the "android:name" attribute in your AndroidManifest.xml's <application> tag.
 * The Application class, or your subclass of the Application class,
 * is instantiated before any other class when the process for your application/package is created.
 * Note: There is normally no need to subclass Application.
 * In most situations, static singletons can provide the same functionality in a more modular way.
 * If your singleton needs a global context (for example to register broadcast receivers),
 * include Context.getApplicationContext() as a Context argument when invoking your singleton's getInstance() method.
 */
public class VerySimplePodcast extends Application {

    /*
    * Using an Application (don't forget to define it in your manifest) we get the default locale when the app starts (onCreate())
    * and we update it when the user changes the language in the Android settings (onConfigurationChanged(Configuration)).
    * That's all there is. Whenever you need to know what the system default language was before you used your setDefault call,
    * sDefSystemLanguage will give you the answer.
    * */
    private static String sDefSystemLanguage;

    @Override
    public void onCreate() {
        super.onCreate();
        sDefSystemLanguage = Locale.getDefault().getLanguage();
        Log.d("sDefSystemLanguage", sDefSystemLanguage);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        sDefSystemLanguage = newConfig.locale.getLanguage();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}

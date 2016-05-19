package com.example.studio08.verysimplepodcast;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

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
    @Override
    public void onCreate() {
        super.onCreate();
        // Created this class extending Application and added the android:name in the Manifest so I can add LeakCanary.
        // It is a test/debug compile in Gradle, currently beta.
        // https://github.com/square/leakcanary/wiki/FAQ
        LeakCanary.install(this);
    }
}

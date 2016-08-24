package com.ronen.studio08.verysimplepodcast.model.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Ronen on 27/5/16.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    // A service that extends FirebaseInstanceIdService to handle the creation, rotation, and updating of registration tokens.
    // This is required for sending to specific devices or for creating device groups.

    private static final String TAG = "onTokenRefresh";

    @Override
    public void onTokenRefresh() {
        // The onTokenRefreshcallback fires whenever a new token is generated,
        // so calling getToken in its context ensures that you are accessing a current, available registration token.
        // FirebaseInstanceID.getToken() returns null if the token has not yet been generated.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
        if (refreshedToken != null)
            sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        try {
            URL url = new URL("https://infinite-citadel-18717.herokuapp.com/fcm");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            // Sets the flag indicating whether this {@code URLConnection} allows input.
            conn.setDoInput(true);
            // Sets the flag indicating whether this {@code URLConnection} allows output.
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();

            // Writes text to a character-output stream, buffering characters so as to provide for the efficient writing of single characters, arrays, and strings.
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"), refreshedToken.length());
            writer.write("key=" + refreshedToken);
            writer.close();

            os.close();

            conn.connect();

            if (conn.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                Log.d(TAG,"success");
            } else {
                Log.d(TAG,"failure");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.ronen.studio08.verysimplepodcast.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

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
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {

    }
}

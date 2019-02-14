package com.swein.firebaselyticsdemo.framework.firebaseanalytics;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class EventTracker {

    private static EventTracker instance = new EventTracker();
    private EventTracker() {}

    public static EventTracker getInstance() {
        return instance;
    }

    /**
     * "setCurrentScreen must be called from the main thread"
     * @param activity
     * @param screenName
     */
    public void setScreen(Activity activity, String screenName) {
        FirebaseAnalytics.getInstance(activity).setCurrentScreen(activity, screenName, null /* class override */);
    }

    public void sendEvent(Context context, String eventName, String eventKey, String eventValue) {
        Bundle params = new Bundle();
        params.putString(eventKey, eventValue);
        FirebaseAnalytics.getInstance(context).logEvent(eventName, params);
    }
}

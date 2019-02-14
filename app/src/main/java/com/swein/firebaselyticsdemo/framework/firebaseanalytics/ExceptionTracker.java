package com.swein.firebaselyticsdemo.framework.firebaseanalytics;

import android.content.Context;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class ExceptionTracker {

    private static ExceptionTracker instance = new ExceptionTracker();
    private ExceptionTracker() {}

    public static ExceptionTracker getInstance() {
        return instance;
    }

    public void sendTryCatchException(Context context, Throwable throwable) {
        Fabric.with(context, new Crashlytics());
        Crashlytics.logException(throwable);
    }

    public void sendException(Context context, String tag, String message) {
        Fabric.with(context, new Crashlytics());
        Crashlytics.log(Log.DEBUG, tag, message);
    }

    public void setUserId(Context context, String id) {
        Fabric.with(context, new Crashlytics());
        Crashlytics.setUserIdentifier(id);
    }

}

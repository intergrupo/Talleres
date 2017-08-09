package com.example.utilidades;

import android.util.Log;

import timber.log.Timber;

/**
 * Created by juanfelipegomezvelez on 29/04/16.
 */
public class ReleaseTree extends Timber.Tree {

    private static final int MAXIMA_LONGITUD = 4000;

    @Override
    protected boolean isLoggable(int priority) {
//        if (priority == Log.DEBUG) {
//            return false;
//        }
        return true;
    }

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {

        if (priority == Log.ERROR && t != null) {
            //Crashlytics.log(e);
        }

        if (isLoggable(priority)) {
            if (message.length() < MAXIMA_LONGITUD) {
                if (priority == Log.ASSERT) {
                    Log.wtf(tag, message);
                } else {
                    Log.println(priority, tag, message);
                }
                return;
            }

            for (int i = 0, length = message.length(); i < length; i++) {
                int newline = message.indexOf('\n', i);
                newline = newline != -1 ? newline : length;
                do {
                    int end = Math.min(newline, i + MAXIMA_LONGITUD);
                    String part = message.substring(i, end);
                    if (priority == Log.ASSERT) {
                        Log.wtf(tag, part);
                    } else {
                        Log.println(priority, tag, part);
                    }
                    i = end;
                } while (i < newline);
            }
        }
    }
}

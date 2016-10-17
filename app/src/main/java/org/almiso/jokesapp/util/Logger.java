package org.almiso.jokesapp.util;

import android.util.Log;

public class Logger {

    private static boolean ENABLED = true;
    private static boolean LOG_THREAD = true;

    public static void d(String TAG, String message) {
        if (!ENABLED) {
            return;
        }

        if (LOG_THREAD) {
            Log.d("Joke|" + TAG, Thread.currentThread().getName() + "| " + message);
        } else {
            Log.d("Joke|" + TAG, message);
        }
    }

    public static void e(String TAG, Exception e) {
        if (!ENABLED) {
            return;
        }

        Log.e("Joke|" + TAG, "Exception in class " + TAG, e);
    }


    public static void e(String TAG, String message, Exception e) {
        if (!ENABLED) {
            return;
        }
        Log.e("Joke|" + TAG, message, e);
    }

    public static void e(String TAG, String message, Throwable e) {
        if (!ENABLED) {
            return;
        }
        Log.e("Joke|" + TAG, message, e);
    }

    public static void w(String TAG, String message) {
        if (LOG_THREAD) {
            Log.w("Joke|" + TAG, Thread.currentThread().getName() + "| " + message);
        } else {
            Log.w("Joke|" + TAG, message);
        }
    }
}
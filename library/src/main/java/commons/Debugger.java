package commons;

import android.text.TextUtils;
import android.util.Log;

import es.utils.BuildConfig;


public class Debugger {

    private static boolean isDebug = BuildConfig.DEBUG;
    private static String LOG_TAG = "com.ibo.debug";

    /**
     * Send the given message to the debug console (if enabled)
     *
     * @param message
     */
    public static void debug(String message) {
        debug(LOG_TAG, message);
    }

    public static void debug(String tag, String message) {
        if (isDebug)
            Log.d(tag, message);
    }

    /**
     * Send the given message to the information console
     *
     * @param message
     */

    public static void info(String message) {
        info(LOG_TAG, message);
    }

    public static void info(String tag, String message) {
        if (isDebug)
            Log.i(tag, message);
    }

    /**
     * Send the given message to the warning console
     *
     * @param message
     */
    public static void warn(String message) {
        warn(LOG_TAG, message);
    }

    public static void warn(String tag, String message) {
        if (isDebug)
            Log.w(tag, message);
    }

    public static void error(String message) {
        if (isDebug)
            error(LOG_TAG, message, null);
    }

    /**
     * Send the given message to the error console with the given TAG
     *
     * @param tag
     * @param message
     */
    public static void error(String tag, String message) {
        if (isDebug)
            error(tag, message, null);
    }

    /**
     * Send the given message to the error console with the given TAG
     *
     * @param tag
     * @param message
     * @param error
     */
    public static void error(String tag, String message, Throwable error) {
        if (TextUtils.isEmpty(message)) return;

        if (error != null)
            Log.e(tag, message, error);
        else
            Log.e(tag, message);
    }
}

package aparcandgo.debug;

import android.util.Log;

public class Debugger {
	
	private static boolean isDebug;
	private static String LOG_TAG = "com.aparcandgo.android";
	
	public static void setDebug(boolean isDebug) {
		Debugger.isDebug = isDebug;
	}
	
	public static boolean isDebug() {
		return isDebug;
	}

	/**
	 * Send the given message to the debug console (if enabled)
	 * @param message
	 */
	public static void debug(String message)
	{
		if(isDebug)
			Log.d(LOG_TAG, message);
	}

	/**
	 * Send the given message to the debug console (if enabled) with the given TAG
	 * @param message
	 */
	public static void debug(String tag, String message)
	{
		if(isDebug)
			Log.d(tag, message);
	}

	/**
	 * Send the given message to the information console with the given TAG
	 * @param message
	 */
	public static void info(String tag, String message)
	{
		if(isDebug)
			Log.i(tag, message);
	}
	
	public static void warn(String message)
	{
		if(isDebug)
			Log.w(LOG_TAG, message);
	}
	
	/**
	 * Send the given message to the warning console with the given TAG
	 * @param message
	 */
	public static void warn(String tag, String message)
	{
		if(isDebug)
			Log.w(tag, message);
	}

	/**
	 * Send the given message to the error console with the given TAG
	 * @param tag
	 * @param message
	 */
	public static void error(String tag, String message)
	{
		if(isDebug)
			Debugger.error(tag, message, null);
	}

	/**
	 * Send the given message to the error console with the given TAG
	 * @param tag
	 * @param message
	 * @param error
	 */
	public static void error(String tag, String message, Throwable error)
	{
        if(!isDebug) return;

		if(error != null)
			Log.e(tag, message, error);
		else
			Log.e(tag, message);
	}

    public static void error(String message)
    {
        if(isDebug)
            Debugger.error(LOG_TAG, message, null);
    }
}

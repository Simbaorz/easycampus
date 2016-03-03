package com.handmark.pulltorefresh.library.internal;

import android.util.Log;

/**
 * 工具类，版本过期提示
 */
public class Utils {

	static final String LOG_TAG = "PullToRefresh";

	public static void warnDeprecation(String depreacted, String replacement) {
		Log.w(LOG_TAG, "You're using the deprecated " + depreacted + " attr, please switch over to " + replacement);
	}

}

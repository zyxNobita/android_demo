package com.bai.demo.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
/***
 * DebugUtil：打印数据的
 * @author Administrator
 *
 */
public class DebugUtil {

	public static final String TAG = "HG_视频查验";
	public static final boolean DEBUG = true;
	public static void toast(Context context, String content) {
		Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
	}

	public static void debug(String tag, String msg) {
		if (DEBUG) {
			Log.d(tag, msg);
		}
	}

	public static void debug(String msg) {
		if (DEBUG) {
			Log.d(TAG, msg);
		}
	}

	public static void error(String tag, String error) {
		Log.e(tag, error);
	}

	public static void error(String error) {
		Log.e(TAG, error);
	}

}

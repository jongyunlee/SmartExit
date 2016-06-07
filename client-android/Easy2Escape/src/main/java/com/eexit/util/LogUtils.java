package com.eexit.util;

import android.util.Log;
import com.eexit.Config;

public class LogUtils {
    public static void common(String className, String content) {
	if (Config.DEBUG) {
	    Log.i("COMMON", "[" + className + "] " +content);
	}
    }

    public static void error(String className, String content) {
	if (Config.DEBUG) {
	    Log.e("COMMON", "[" + className + "] " +content);
	}
    }

    public static void highlight(String className, String content) {
	if (Config.DEBUG) {
	    Log.d("COMMON", "[" + className + "] " +content);
	}
    }

    public static void socket(String className, String content) {
	if (Config.DEBUG) {
	    Log.d("SOCKET", "[" + className + "] " +content);
	}
    }
}

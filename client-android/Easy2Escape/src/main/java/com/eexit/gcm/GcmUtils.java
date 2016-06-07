package com.eexit.gcm;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.eexit.util.LogUtils;
import java.io.IOException;

public class GcmUtils {

    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private String GCM_PREF_NAME = "gcmforcom.eexit";

    String SENDER_ID = "210672752209";

    static final String TAG = "GCM";

    private Context mActivity;
    private GoogleCloudMessaging gcm;
    private GcmUtilsListener mListener;

    public void setGcmUtilsListener(GcmUtilsListener listener) {
	mListener = listener;
    }

    public GcmUtils(Context activity) {
	mActivity = activity;
	gcm = GoogleCloudMessaging.getInstance(activity);
    }

    public interface GcmUtilsListener {
	public void onErrorOccured(boolean recoverable);
	public void onRegidExist(boolean exist, String regid);
    }

    public String register() {
	String regid = null;
        if (checkPlayServices()) {
	    LogUtils.highlight("GcmUtils", "register playservice ok");
            regid = getRegistrationId(mActivity.getApplicationContext());
            if (TextUtils.isEmpty(regid)) {
		if (gcm == null) {
		    gcm = GoogleCloudMessaging.getInstance(mActivity.getApplicationContext());
		}
		try {
		    regid = gcm.register(SENDER_ID);
		} catch(IOException e) {
		    LogUtils.highlight("GcmUtils", "ioerror : " + e.getMessage());
		    if (mListener != null) {
			mListener.onErrorOccured(false);
		    }
		    return regid;
		}
		if (mListener != null) {
		    mListener.onRegidExist(false, regid);
		}
            } else {
		if (mListener != null) {
		    mListener.onRegidExist(true, regid);
		}
	    }
	    LogUtils.highlight("GcmUtils", "gcm regid : " + regid);
        } else {
	    if (mListener != null) {
		mListener.onErrorOccured(false);
	    }
	    LogUtils.highlight("SplashActivity", "No valid Google Play Services APK found.");
        }
	return regid;
    }

    public boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(mActivity);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
		if (mListener != null) {
		    mListener.onErrorOccured(true);
		}
            } else {
		if (mListener != null) {
		    mListener.onErrorOccured(false);
		}
            }
            return false;
        }
        return true;
    }

    public String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGcmPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    public void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGcmPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    private SharedPreferences getGcmPreferences(Context context) {
        return context.getSharedPreferences(GCM_PREF_NAME,
					    Context.MODE_PRIVATE);
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }
}

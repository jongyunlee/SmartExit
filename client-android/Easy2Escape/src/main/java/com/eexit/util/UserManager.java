package com.eexit.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.eexit.Constants;
import java.util.StringTokenizer;

public class UserManager {

    public static final String KEY_SESSION_PREFERENCE = "keys2";
    public static final String keys2SessionKey = "keys2SessionKey";
    public static final String keys2UserNickname = "keys2UserNickname";
    public static final String keys2UserId = "keys2UserId";
    public static final String keys2Title = "keys2Title";
    public static final String keys2ProfileImage = "keys2ProfileImage";
    public static final String keys2CoverImage = "keys2CoverImage";
    public static final String keys2Country = "keys2Country";
    public static final String keys2TimeError = "keysTimeError";
    public static final String keys2Flag = "keys2Flag";
    public static final String keys2City = "keys2City";
    public static final String keys2Vibrate = "keys2Vibrate";
    public static final String keys2GameVibrate = "keys2GameVibrate";
    public static final String keys2PushAll = "keys2PushAll";
    public static final String keys2FirstLogin = "keys2FirstLogin";
    public static final String keys2PhoneNumber = "keys2PhoneNumber";

    public static String getSignature(long timeStamp) {
	StringTokenizer values = new StringTokenizer( new String(Constants.APP_KEY), "." );
	String key = values.nextToken() + timeStamp + values.nextToken();
	return SecurityUtils.getMD5String(key);
    }

    public static String getSessionKey(Context context) {
	return getStringValue(context, keys2SessionKey);
    }

    public static String getUserNickname(Context context) {
	return getStringValue(context, keys2UserNickname);
    }

    public static String getUserId(Context context) {
	return getStringValue(context, keys2UserId);
    }

    public static String getTitle(Context context) {
	return getStringValue(context, keys2Title);
    }

    public static String getProfileImage(Context context) {
	return getStringValue(context, keys2ProfileImage);
    }

    public static String getCoverImage(Context context) {
	return getStringValue(context, keys2CoverImage);
    }

    public static String getCountry(Context context) {
	return getStringValue(context, keys2Country);
    }

    public static String getFlag(Context context) {
	return getStringValue(context, keys2Flag);
    }

    public static String getCity(Context context) {
	return getStringValue(context, keys2City);
    }

    public static String getPhoneNumber(Context context) {
	return getStringValue(context, keys2PhoneNumber);
    }

    public static boolean getVibrate(Context context) {
	return getBooleanValue(context, keys2Vibrate);
    }

    public static boolean getGameVibrate(Context context) {
	return getBooleanValue(context, keys2GameVibrate);
    }

    public static boolean getPushAll(Context context) {
	return getBooleanValue(context, keys2PushAll);
    }

    public static boolean getFirstLogin(Context context) {
	return getBooleanValue(context, keys2FirstLogin);
    }

    public static void setSessionKey(Context context, String sessionKey) {
	saveStringValue(context, keys2SessionKey, sessionKey);
    }

    public static void setUserNickname(Context context, String nickname) {
	saveStringValue(context, keys2UserNickname, nickname);
    }

    public static void setUserId(Context context, String name) {
	saveStringValue(context, keys2UserId, name);
    }

    public static void setTitle(Context context, String name) {
	saveStringValue(context, keys2Title, name);
    }

    public static void setProfileImage(Context context, String name) {
	saveStringValue(context, keys2ProfileImage, name);
    }

    public static void setCoverImage(Context context, String name) {
	saveStringValue(context, keys2CoverImage, name);
    }

    public static void setCountry(Context context, String name) {
	saveStringValue(context, keys2Country, name);
    }

    public static void setFlag(Context context, String flag) {
	saveStringValue(context, keys2Flag, flag);
    }

    public static void setCity(Context context, String city) {
	saveStringValue(context, keys2City, city);
    }

    public static void setPhoneNumber(Context context, String phoneNumber) {
	saveStringValue(context, keys2PhoneNumber, phoneNumber);
    }

    public static void setVibrate(Context context, boolean vibrate) {
	saveBooleanValue(context, keys2Vibrate, vibrate);
    }

    public static void setGameVibrate(Context context, boolean gameVibrate) {
	saveBooleanValue(context, keys2GameVibrate, gameVibrate);
    }

    public static void setPushAll(Context context, boolean pushAll) {
	saveBooleanValue(context, keys2PushAll, pushAll);
    }

    public static void setFirstLogin(Context context, boolean firstLogin) {
	saveBooleanValue(context, keys2FirstLogin, firstLogin);
    }

    public static boolean isLoggedIn(Context context) {
	if (!TextUtils.isEmpty(getSessionKey(context))
	    && !TextUtils.isEmpty(getUserId(context))) {
	    return true;
	}
	return false;
    }

    public static void logout(Context context) {
	setUserId(context, "");
	setSessionKey(context, "");
    }

    public static String getAuthorizationHeaderValue(Context context) {
	return "ApiKey " + getUserId(context) + ":" + getSessionKey(context);
    }

    public static SharedPreferences getPrefs(Context context) {

	return context.getSharedPreferences(KEY_SESSION_PREFERENCE, Context.MODE_PRIVATE);
    }

    public static void saveStringValue(Context context, String name, String value) {
	if (value != null) {
	    SharedPreferences.Editor editor = getPrefs(context).edit();
	    editor.putString(name, value);
	    editor.commit();
	}
    }

    public static void saveIntegerValue(Context context, String name, int value) {
	SharedPreferences.Editor editor = getPrefs(context).edit();
	editor.putInt(name, value);
	editor.commit();
    }

    public static void saveBooleanValue(Context context, String name, boolean value) {
	SharedPreferences.Editor editor = getPrefs(context).edit();
	editor.putBoolean(name, value);
	editor.commit();
    }

    public static String getStringValue(Context context, String name) {
	return getPrefs(context).getString(name, "");
    }

    public static int getIntegerValue(Context context, String name) {
	return getPrefs(context).getInt(name, 0);
    }

    public static boolean getBooleanValue(Context context, String name) {
	return getPrefs(context).getBoolean(name, true);
    }
}

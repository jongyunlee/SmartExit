package com.eexit.util;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;
import android.util.Patterns;
import com.eexit.Constants;
import com.eexit.Config;
import java.util.regex.Pattern;

public class PackageUtil {

    public PackageUtil() {

    }

    public static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
		.getPackageInfo(Constants.AUTHORITY, 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    public static String getPhoneNumber(Context context) {
	TelephonyManager tMgr
	    = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
	String phoneNumber = tMgr.getLine1Number();
	// if (Config.DEBUG) {
	//     phoneNumber = "010-7732-4275";
	// }
	return phoneNumber;
    }

    public static String getImei(Context context) {
	TelephonyManager tMgr
	    = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
	String udId = tMgr.getDeviceId();
	// String udId = "asdfasdfasdf";
	return udId;
    }

    public static String getEmail(Context context) {
	Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
	Account[] accounts = AccountManager.get(context).getAccounts();
	for (Account account : accounts) {
	    if (emailPattern.matcher(account.name).matches()) {
		String possibleEmail = account.name;
		return possibleEmail;
	    }
	}
	return null;
    }
}

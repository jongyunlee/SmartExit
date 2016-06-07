package com.eexit.util;

import android.content.Context;
import android.content.SharedPreferences;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    public static final int SEC = 60;
    public static final int MIN = 60;
    public static final int HOUR = 24;
    public static final int DAY = 30;
    public static final int MONTH = 12;
    public static final String DATEFORMAT = "MM월 dd일 a HH:mm";
    public static final String SIMPLE_DATEFORMAT = "yy.MM.dd";

    public static String getDateString(Timestamp timeStamp) {
	SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
	return sdf.format(new Date(timeStamp.getTime()));
    }

    public static String getSimpleDateString(Timestamp timeStamp) {
	SimpleDateFormat sdf = new SimpleDateFormat(SIMPLE_DATEFORMAT);
	return sdf.format(new Date(timeStamp.getTime()));
    }

    public static boolean isDayDifference(Timestamp prev, Timestamp cur) {
        long curTime = cur.getTime();
        long regTime = prev.getTime();
        long diffTime = (curTime - regTime) / 1000;
        if (diffTime < SEC)
	    {
		// sec
		return false;
	    }
        else if ((diffTime /= SEC) < MIN)
	    {
		return false;
	    }
        else if ((diffTime /= MIN) < HOUR)
	    {
		return false;
	    }
        else if ((diffTime /= HOUR) < DAY)
	    {
		return true;
	    }
        else if ((diffTime /= DAY) < MONTH)
	    {
		return true;
	    }
        else
	    {
		return true;
	    }
    }

    public static String calculateTime(Timestamp date)
    {

        long curTime = System.currentTimeMillis();
        long regTime = date.getTime();
        long diffTime = (curTime - regTime) / 1000;

        String msg = null;
        if (diffTime < SEC)
	    {
		// sec
		msg = "방금전";
	    }
        else if ((diffTime /= SEC) < MIN)
	    {
		// min
		System.out.println(diffTime);

		msg = diffTime + "분전";
	    }
        else if ((diffTime /= MIN) < HOUR)
	    {
		// hour
		msg = (diffTime ) + "시간전";
	    }
        else if ((diffTime /= HOUR) < DAY)
	    {
		// day
		msg = (diffTime ) + "일전";
	    }
        else if ((diffTime /= DAY) < MONTH)
	    {
		// day
		msg = (diffTime ) + "달전";
	    }
        else
	    {
		msg = (diffTime) + "년전";
	    }

        return msg;
    }

    public static final String keys2CloseDate = "keys2CloseDate";

    public static void setCloseDate(Context context) {
	saveLongValue(context, keys2CloseDate, System.currentTimeMillis());
    }

    public static boolean isCloseExpired(Context context) {
	long time = getLongValue(context, keys2CloseDate);
	long diff = (System.currentTimeMillis() - time)/1000;
	if (time == 0) {
	    return true;
	} else if (diff >= SEC * MIN * HOUR) {
	    return true;
	}
	return false;
    }

    public static void saveLongValue(Context context, String name, long value) {
	SharedPreferences.Editor editor = UserManager.getPrefs(context).edit();
	editor.putLong(name, value);
	editor.commit();
    }

    public static long getLongValue(Context context, String name) {
	return UserManager.getPrefs(context).getLong(name, 0);
    }
}

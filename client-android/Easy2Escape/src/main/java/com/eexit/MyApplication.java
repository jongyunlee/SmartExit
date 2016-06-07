package com.eexit;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import com.kakao.Session;
import com.kakao.helper.SystemInfo;
import com.facebook.FacebookSdk;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;


public class MyApplication extends MultiDexApplication {

    public static ImageLoaderConfiguration config;

    public static GoogleAnalytics analytics;
    public static Tracker tracker;

    @Override
    public void onCreate() {
	initAnalytics();
	super.onCreate();
	initImageLoader(getApplicationContext());

	// kakaotalk
	Session.initialize(this);
        SystemInfo.initialize();

	// facebook
	FacebookSdk.sdkInitialize(getApplicationContext());
    }

    public static void initImageLoader(Context context) {
	config = new ImageLoaderConfiguration.Builder(context)
	    .threadPriority(Thread.NORM_PRIORITY - 2)
	    .denyCacheImageMultipleSizesInMemory()
	    .discCacheFileNameGenerator(new Md5FileNameGenerator())
	    .tasksProcessingOrder(QueueProcessingType.LIFO)
	    .writeDebugLogs()
	    .build();
	ImageLoader.getInstance().init(config);
    }

    @Override
    public void attachBaseContext(Context base) {
        MultiDex.install(base);
        super.attachBaseContext(base);
    }

    public void initAnalytics() {
	analytics = GoogleAnalytics.getInstance(this);
	analytics.setLocalDispatchPeriod(1800);

	tracker = analytics.newTracker("UA-65380075-1");
	tracker.enableAutoActivityTracking(false);
    }
}

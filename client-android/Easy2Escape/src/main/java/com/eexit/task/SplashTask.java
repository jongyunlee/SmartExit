package com.eexit.task;

import android.content.Context;
import android.text.TextUtils;
import com.eexit.ApiPaths;
import com.eexit.gcm.GcmUtils;
import com.eexit.model.SplashParams;
import com.eexit.model.SplashResult;
import com.eexit.util.LogUtils;
import com.eexit.util.PackageUtil;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.http.Body;
import retrofit.http.POST;
import com.eexit.util.LogUtils;

public class SplashTask extends BaseAsyncTask<SplashResult> {

    private Context mContext;
    private String regid;
    private GcmUtils gcmUtils;

    private SplashTaskListener mListener;

    public interface SplashTaskListener {
	public void onSuccess(SplashResult result);
	public void onFailure(SplashResult result);
    }

    public void setSplashTaskListener(SplashTaskListener listener) {
	mListener = listener;
    }

    public SplashTask(Context context) {
	super(context);
	mContext = context;
	gcmUtils = new GcmUtils(context);
    }

    public interface SplashResultTaskApi {
	@POST(ApiPaths.SPLASH_PATH)
	SplashResult create(@Body SplashParams params);
    }

    @Override
    protected SplashResult doInBackground(Void... args) {
	RestAdapter restAdapter = getRestAdapter();
	SplashResultTaskApi register = restAdapter.create(SplashResultTaskApi.class);
	SplashResult result = null;
	int versionCode = PackageUtil.getAppVersion(mContext);
	String regid = null;
	boolean new_token = true;
	regid = gcmUtils.getRegistrationId(mContext);
	if (TextUtils.isEmpty(regid)) {
	    regid = gcmUtils.register();
	    if (TextUtils.isEmpty(regid)) {
		new_token = false;
	    } else {
		new_token = true;
		gcmUtils.storeRegistrationId(mContext, regid);
	    }
	} else {
	    // new_token = false;
	}

	LogUtils.highlight("SplashTask", "regid : " + regid);

	SplashParams p = new SplashParams(versionCode, regid, new_token);
	try {
	    result = register.create(p);
	} catch (RetrofitError error) {
	}
	return result;
    }

    @Override
    public void loadFinished(SplashResult result) {
	if (mListener != null && result != null) {
	    mListener.onSuccess(result);
	} else {
	    mListener.onFailure(result);
	}
    }

    @Override
    public String getLoadingId() {
	return "loading";
    }

    @Override
    public String getLoadingMessage() {
	return "로딩중...";
    }
}

package com.eexit.task;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.ClassCastException;
import java.lang.Void;
import java.sql.Timestamp;
import com.eexit.ui.ProgressDialogFragment;
import com.eexit.ui.BaseActivity;
import com.eexit.rest.RestAdapterFactory;
import com.eexit.util.TimestampDeserializer;
import retrofit.RestAdapter;

public abstract class BaseAsyncTask<D> extends AsyncTask<Void, Integer, D> {

    public abstract String getLoadingMessage();
    public abstract String getLoadingId();
    public abstract void loadFinished(D data);
    private Context mContext;
    private Gson gson;
    private Fragment dialogFragment;
    private RestAdapter restAdapter;
    private boolean enableDialog = true;
    private D mResult;

    public BaseAsyncTask(Context context) {
	super();
	mContext = context;
	restAdapter = RestAdapterFactory.getRestAdapter(context);
    }

    public RestAdapter getRestAdapter() {
	return restAdapter;
    }

    @Override
    protected void onPreExecute() {
	super.onPreExecute();
	if (enableDialog) {
	    try {
		dialogFragment =
		    ProgressDialogFragment.showLoadingProgress(((BaseActivity)mContext)
							       .getSupportFragmentManager(),
							       getLoadingMessage(),
							       getLoadingId());
	    } catch(ClassCastException e) {
	    }
	}
    }

    public void onPrepareGson() {
	if (gson == null) {
	    GsonBuilder builder = new GsonBuilder();
	    builder.setDateFormat("yyyyMMdd");
	    builder.registerTypeAdapter(Timestamp.class, new TimestampDeserializer());
	    gson = builder.create();
	}
    }

    @Override
    protected void onPostExecute(D result) {
	onPrepareGson();
	if (dialogFragment != null) {
	    try {
		ProgressDialogFragment.dismissLoadingProgress(((BaseActivity)mContext)
							      .getSupportFragmentManager(),
							      dialogFragment);
	    } catch(ClassCastException e) {
	    }
	}
	mResult = result;
	mHandler.sendEmptyMessage(0);
    }

    public Gson getGson() {
	return gson;
    }

    public void setEnableDialog(boolean enable) {
	this.enableDialog = enable;
    }

    private Handler mHandler = new Handler() {
	    @Override
	    public void handleMessage(Message msg){
		loadFinished(mResult);
	    };
	};
}

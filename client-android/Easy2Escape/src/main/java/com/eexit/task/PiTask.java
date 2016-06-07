package com.eexit.task;

import android.content.Context;
import com.eexit.ApiPaths;
import com.eexit.model.Pi;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.http.Path;

public class PiTask extends BaseAsyncTask<Pi> {

    private Context mContext;

    private PiTaskListener mListener;
    private String id;

    public interface PiTaskListener {
	public void onSuccess(Pi result);
	public void onFailure(String result);
    }

    public void setPiTaskListener(PiTaskListener listener) {
	mListener = listener;
    }

    public PiTask(Context context, String id) {
	super(context);
	mContext = context;
	this.id = id;
    }

    public interface PiTaskApi {
	@GET(ApiPaths.PI_DETAIL_PATH)
	Pi create(@Path(ApiPaths.PI_ID) String id);
    }

    @Override
	protected Pi doInBackground(Void... args) {
	RestAdapter restAdapter = getRestAdapter();
	PiTaskApi register = restAdapter.create(PiTaskApi.class);
	Pi result = null;
	try {
	    result = register.create(id);
	} catch (RetrofitError error) {
	}
	return result;
    }

    @Override
	public void loadFinished(Pi result) {
	if (mListener != null && result != null) {
	    mListener.onSuccess(result);
	} else {
	    mListener.onFailure("ERROR");
	}
    }

    @Override
	public String getLoadingMessage() {
	return "로딩중...";
    }

    @Override
	public String getLoadingId() {
	return "loading";
    }
}

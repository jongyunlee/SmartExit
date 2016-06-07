package com.eexit.task;

import android.content.Context;
import com.eexit.ApiPaths;
import com.eexit.model.Pi;
import com.eexit.model.ChangeStateParams;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Query;
import retrofit.http.Body;
import retrofit.http.Path;

public class ChangeStateTask extends BaseAsyncTask<Pi> {

    private Context mContext;
    private String state;
    private String id;

    private ChangeStateTaskListener mListener;

    public interface ChangeStateTaskListener {
	public void onSuccess(Pi result);
	public void onFailure(String result);
    }

    public void setChangeStateTaskListener(ChangeStateTaskListener listener) {
	mListener = listener;
    }

    public ChangeStateTask(Context context, String id, String state) {
	super(context);
	mContext = context;
	this.id = id;
	this.state = state;
    }

    public interface PiTaskApi {
	@PUT(ApiPaths.CHANGE_STATE_PATH)
	Pi create(@Path(ApiPaths.PI_ID) String id, @Body ChangeStateParams params);
    }

    @Override
	protected Pi doInBackground(Void... args) {
	RestAdapter restAdapter = getRestAdapter();
	PiTaskApi register = restAdapter.create(PiTaskApi.class);
	Pi result = null;
	try {
	    result = register.create(id, new ChangeStateParams(state));
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

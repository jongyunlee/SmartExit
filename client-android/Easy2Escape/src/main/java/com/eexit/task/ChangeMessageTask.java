package com.eexit.task;

import android.content.Context;
import com.eexit.ApiPaths;
import com.eexit.model.Pi;
import com.eexit.model.ChangeMessageParams;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Query;
import retrofit.http.Body;
import retrofit.http.Path;

public class ChangeMessageTask extends BaseAsyncTask<Pi> {

    private Context mContext;
    private String message;
    private String id;

    private ChangeMessageTaskListener mListener;

    public interface ChangeMessageTaskListener {
	public void onSuccess(Pi result);
	public void onFailure(String result);
    }

    public void setChangeMessageTaskListener(ChangeMessageTaskListener listener) {
	mListener = listener;
    }

    public ChangeMessageTask(Context context, String id, String message) {
	super(context);
	mContext = context;
	this.id = id;
	this.message = message;
    }

    public interface PiTaskApi {
	@PUT(ApiPaths.CHANGE_MESSAGE_PATH)
	Pi create(@Path(ApiPaths.PI_ID) String id, @Body ChangeMessageParams params);
    }

    @Override
	protected Pi doInBackground(Void... args) {
	RestAdapter restAdapter = getRestAdapter();
	PiTaskApi register = restAdapter.create(PiTaskApi.class);
	Pi result = null;
	try {
	    result = register.create(id, new ChangeMessageParams(message));
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

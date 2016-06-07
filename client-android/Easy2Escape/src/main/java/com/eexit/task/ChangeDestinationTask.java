package com.eexit.task;

import android.content.Context;
import com.eexit.ApiPaths;
import com.eexit.model.Pi;
import com.eexit.model.ChangeDestinationParams;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Query;
import retrofit.http.Body;
import retrofit.http.Path;

public class ChangeDestinationTask extends BaseAsyncTask<Pi> {

    private Context mContext;
    private String destination;
    private String id;

    private ChangeDestinationTaskListener mListener;

    public interface ChangeDestinationTaskListener {
	public void onSuccess(Pi result);
	public void onFailure(String result);
    }

    public void setChangeDestinationTaskListener(ChangeDestinationTaskListener listener) {
	mListener = listener;
    }

    public ChangeDestinationTask(Context context, String id, String destination) {
	super(context);
	mContext = context;
	this.id = id;
	this.destination = destination;
    }

    public interface PiTaskApi {
	@PUT(ApiPaths.CHANGE_DESTINATION_PATH)
	Pi create(@Path(ApiPaths.PI_ID) String id, @Body ChangeDestinationParams params);
    }

    @Override
	protected Pi doInBackground(Void... args) {
	RestAdapter restAdapter = getRestAdapter();
	PiTaskApi register = restAdapter.create(PiTaskApi.class);
	Pi result = null;
	try {
	    result = register.create(id, new ChangeDestinationParams(destination));
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

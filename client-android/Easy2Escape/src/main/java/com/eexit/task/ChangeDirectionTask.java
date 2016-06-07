package com.eexit.task;

import android.content.Context;
import com.eexit.ApiPaths;
import com.eexit.model.Pi;
import com.eexit.model.ChangeDirectionParams;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Query;
import retrofit.http.Body;
import retrofit.http.Path;

public class ChangeDirectionTask extends BaseAsyncTask<Pi> {

    private Context mContext;
    private String direction;
    private String id;

    private ChangeDirectionTaskListener mListener;

    public interface ChangeDirectionTaskListener {
	public void onSuccess(Pi result);
	public void onFailure(String result);
    }

    public void setChangeDirectionTaskListener(ChangeDirectionTaskListener listener) {
	mListener = listener;
    }

    public ChangeDirectionTask(Context context, String id, String direction) {
	super(context);
	mContext = context;
	this.id = id;
	this.direction = direction;
    }

    public interface PiTaskApi {
	@PUT(ApiPaths.CHANGE_DIRECTION_PATH)
	Pi create(@Path(ApiPaths.PI_ID) String id, @Body ChangeDirectionParams params);
    }

    @Override
	protected Pi doInBackground(Void... args) {
	RestAdapter restAdapter = getRestAdapter();
	PiTaskApi register = restAdapter.create(PiTaskApi.class);
	Pi result = null;
	try {
	    result = register.create(id, new ChangeDirectionParams(direction));
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

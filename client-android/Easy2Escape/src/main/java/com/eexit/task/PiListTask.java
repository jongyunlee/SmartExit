package com.eexit.task;

import android.content.Context;
import com.eexit.ApiPaths;
import com.eexit.model.Pi;
import java.util.List;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Query;

public class PiListTask extends BaseAsyncTask<List<Pi>> {

    private Context mContext;

    private PiListTaskListener mListener;

    public interface PiListTaskListener {
	public void onSuccess(List<Pi> result);
	public void onFailure(String result);
    }

    public void setPiListTaskListener(PiListTaskListener listener) {
	mListener = listener;
    }

    public PiListTask(Context context) {
	super(context);
	mContext = context;
    }

    public interface PiTaskApi {
	@GET(ApiPaths.PIS_PATH)
	List<Pi> create();
    }

    @Override
	protected List<Pi> doInBackground(Void... args) {
	RestAdapter restAdapter = getRestAdapter();
	PiTaskApi register = restAdapter.create(PiTaskApi.class);
	List<Pi> result = null;
	try {
	    result = register.create();
	} catch (RetrofitError error) {
	}
	return result;
    }

    @Override
	public void loadFinished(List<Pi> result) {
	if (mListener != null && result != null) {
	    mListener.onSuccess(result);
	} else {
	    mListener.onFailure("ERROR");
	}
    }

    @Override
	public String getLoadingMessage() {
	return "Loading...";
    }

    @Override
	public String getLoadingId() {
	return "loading";
    }
}

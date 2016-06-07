package com.eexit.task;

import android.content.Context;
import com.eexit.ApiPaths;
import com.eexit.model.LoginEmailObject;
import com.eexit.model.LoginEmailParams;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit.http.Body;

public class LoginEmailTask extends BaseAsyncTask<LoginEmailObject> {

    private Context mContext;

    private LoginEmailTaskListener mListener;
    private String email;
    private String password;

    public interface LoginEmailTaskListener {
	public void onSuccess(LoginEmailObject result);
	public void onFailure(LoginEmailObject result);
    }

    public void setLoginEmailTaskListener(LoginEmailTaskListener listener) {
	mListener = listener;
    }

    public LoginEmailTask(Context context, String email, String password) {
	super(context);
	mContext = context;
	this.email = email;
	this.password = password;
    }

    public interface LoginEmailObjectTaskApi {
	@POST(ApiPaths.LOGIN_EMAIL_PATH)
	LoginEmailObject create(@Body LoginEmailParams params);
    }

    @Override
    protected LoginEmailObject doInBackground(Void... args) {
	RestAdapter restAdapter = getRestAdapter();
	LoginEmailObjectTaskApi register = restAdapter.create(LoginEmailObjectTaskApi.class);
	LoginEmailObject result = null;
	try {
	    result = register.create(new LoginEmailParams(email, password));
	} catch (RetrofitError error) {
	}
	return result;
    }

    @Override
    public void loadFinished(LoginEmailObject result) {
	if (mListener != null && result != null && result.getSuccess()) {
	    mListener.onSuccess(result);
	} else {
	    mListener.onFailure(result);
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

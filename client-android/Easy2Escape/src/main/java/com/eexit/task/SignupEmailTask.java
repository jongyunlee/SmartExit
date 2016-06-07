package com.eexit.task;

import android.content.Context;
import com.eexit.ApiPaths;
import com.eexit.model.SignupEmailObject;
import com.eexit.model.SignupEmailParams;
import com.google.gson.annotations.SerializedName;
import java.sql.Timestamp;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.POST;
import retrofit.http.Body;
import retrofit.http.Query;

public class SignupEmailTask extends BaseAsyncTask<SignupEmailObject> {

    private Context mContext;

    private SignupEmailTaskListener mListener;
    private String name;
    private String email;
    private String password;
    private String passwordOk;
    private String recommender;

    public interface SignupEmailTaskListener {
	public void onSuccess(SignupEmailObject result);
	public void onFailure(SignupEmailObject result);
    }

    public void setSignupEmailTaskListener(SignupEmailTaskListener listener) {
	mListener = listener;
    }

    public SignupEmailTask(Context context,
			   String name,
			   String email,
			   String password,
			   String recommender) {
	super(context);
	mContext = context;
	this.name = name;
	this.email = email;
	this.password = password;
	this.recommender = recommender;
    }

    public interface SignupEmailObjectTaskApi {
	@POST(ApiPaths.SIGNUP_EMAIL_PATH)
	SignupEmailObject create(@Body SignupEmailParams params);
    }

    @Override
    protected SignupEmailObject doInBackground(Void... args) {
	RestAdapter restAdapter = getRestAdapter();
	SignupEmailObjectTaskApi register = restAdapter.create(SignupEmailObjectTaskApi.class);
	SignupEmailObject result = null;
	try {
	    result = register.create(new SignupEmailParams(name, email, password, recommender));
	} catch (RetrofitError error) {
	}
	return result;
    }

    @Override
    public void loadFinished(SignupEmailObject result) {
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

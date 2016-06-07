package com.eexit.task;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.eexit.ApiPaths;
import com.eexit.model.SendAccessTokenObject;
import com.eexit.model.SendAccessTokenParams;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.POST;
import retrofit.http.Body;
import retrofit.http.Query;

public class SendAccessTokenTask extends BaseAsyncTask<SendAccessTokenObject> {

    private Context mContext;
    private String[] scopes;
    private String accountName;
    private String token;
    private String loginType;

    private SendAccessTokenTaskListener mListener;

    public interface SendAccessTokenTaskListener {
	public void onSuccess(SendAccessTokenObject result);
	public void onFailure(SendAccessTokenObject result);
    }

    public void setSendAccessTokenTaskListener(SendAccessTokenTaskListener listener) {
	mListener = listener;
    }

    public SendAccessTokenTask(Context context, String accessToken, String loginType) {
	super(context);
	mContext = context;
	this.token = accessToken;
	this.loginType = loginType;
    }

    public SendAccessTokenTask(Context context, String[] scopes, String accountName) {
	super(context);
	mContext = context;
	this.scopes = scopes;
	this.accountName = accountName;
    }

    public interface SendAccessTokenObjectTaskApi {
	@POST(ApiPaths.SEND_ACCESS_TOKEN_PATH)
	SendAccessTokenObject create(@Body SendAccessTokenParams params);
    }

    public interface KakaoSendAccessTokenObjectTaskApi {
	@POST(ApiPaths.KAKAO_SEND_ACCESS_TOKEN_PATH)
	SendAccessTokenObject create(@Body SendAccessTokenParams params);
    }

    public interface FbSendAccessTokenObjectTaskApi {
	@POST(ApiPaths.FACEBOOK_SEND_ACCESS_TOKEN_PATH)
	SendAccessTokenObject create(@Body SendAccessTokenParams params);
    }

    @Override
    protected SendAccessTokenObject doInBackground(Void... args) {
	RestAdapter restAdapter = getRestAdapter();
	SendAccessTokenObject result = null;
	String locale = mContext.getResources().getConfiguration().locale.getCountry();
	if (TextUtils.isEmpty(token)) {
	    SendAccessTokenObjectTaskApi register = restAdapter.create(SendAccessTokenObjectTaskApi.class);

	    String accessToken = null;
	    try {
		accessToken = GoogleAuthUtil.getToken(mContext, accountName, "oauth2:" + TextUtils.join(" ", scopes));
		try {
		    result = register.create(new SendAccessTokenParams(accessToken, locale));
		} catch (RetrofitError error) {
		}
	    } catch (UserRecoverableAuthException e) {
		accessToken = null;
	    } catch (GoogleAuthException authEx) {
		return result;
	    } catch (Exception e) {
		throw new RuntimeException(e);
	    }
	} else {
	    if (loginType.equals("kakao")) {
		KakaoSendAccessTokenObjectTaskApi register = restAdapter.create(KakaoSendAccessTokenObjectTaskApi.class);
		try {
		    result = register.create(new SendAccessTokenParams(token, locale));
		} catch (RetrofitError error) {
		}
	    } else if (loginType.equals("facebook")) {
		FbSendAccessTokenObjectTaskApi register = restAdapter.create(FbSendAccessTokenObjectTaskApi.class);
		try {
		    result = register.create(new SendAccessTokenParams(token, locale));
		} catch (RetrofitError error) {
		}
	    }
	}
	return result;
    }

    @Override
    public void loadFinished(SendAccessTokenObject result) {
	if (mListener != null && result != null && result.getSuccess()) {
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

package com.eexit.rest;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;
import com.eexit.Config;
import com.eexit.Constants;
import com.eexit.ui.BaseActivity;
import com.eexit.ui.BaseDialogFragment;
import com.eexit.ui.BaseDialogFragment.DialogClickListener;
import com.eexit.util.UserManager;
import com.eexit.util.LogUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.StringBuffer;
import retrofit.client.ApacheClient;
import retrofit.client.Client;
import retrofit.client.Request;
import retrofit.client.Response;

public class ApiClient implements Client {

    Client delegate;
    private Context mContext;

    public ApiClient(Context context) {
	mContext = context;
	delegate = new ApacheClient();
    }

    @Override
    public Response execute(Request request) throws IOException {
	String requestBody = null;
	try {
		ByteArrayOutputStream s = new ByteArrayOutputStream();
		request.getBody().writeTo(s);
		requestBody = new String( s.toByteArray(), "UTF-8");
	} catch (Exception e) {
	}
	if (Config.DEBUG) {
	    if (requestBody != null)
		Log.i("SERVER", "{\nrequest body : " + requestBody);
	    else
		Log.i("SERVER", "{\nrequest body : " + request.getBody());
	}
	if (Config.DEBUG) {
	    Log.i("SERVER", "request header : " + request.getHeaders());
	    Log.i("SERVER", "request method : " + request.getMethod());
	    Log.i("SERVER", "request url : " + request.getUrl() + "\n}");
	}
	Response response = delegate.execute(request);
	if (response.getStatus() >= 400){
	    onErrorOccured(response.getStatus());
	}
	if (Config.DEBUG) {
	    if (response.getBody() != null)
		Log.i("SERVER", " body : " +  makeString((ByteArrayInputStream)response.getBody().in()));
	    if (response.getHeaders() != null)
		Log.i("SERVER", " headers : " + response.getHeaders());
	    if (response.getReason() != null)
		Log.i("SERVER", " reason : " + response.getReason());
	    if (response.getStatus() != 0)
		Log.i("SERVER", " status : " + response.getStatus());
	}
	return response;
    }

    public void onErrorOccured(int statusCode) {
    	switch(statusCode) {
    	case Constants.SESSION_EXPIRED_ERROR:
	    LogUtils.common("ApiClient", "session expired");
    	    UserManager.logout(mContext);
	    try {
		FragmentTransaction ft = ((BaseActivity)mContext).getSupportFragmentManager().beginTransaction();
		Fragment prev = ((BaseActivity)mContext).getSupportFragmentManager().findFragmentByTag("dialog");
		if (prev != null) {
		    ft.remove(prev);
		}
		ft.addToBackStack(null);
		BaseDialogFragment newFragment
		    = BaseDialogFragment.newInstance("",
						     "세션이 만료되었습니다. 다시 로그인해 주세요",
						     false);
		newFragment.setDialogClickListener(new DialogClickListener() {
			@Override
			public void onCancel() {
			}
			@Override
			public void onOk() {
			    ((BaseActivity)mContext).finish();
			}
		    });
		newFragment.show(ft, "dialog");
	    } catch(ClassCastException e) {
	    }
    	    break;
    	case Constants.PRECONDITION_FAILED_ERROR:
	    if (Config.DEBUG)
		Log.e("SERVER", "precondition_failed_error");
    	    break;
    	case Constants.NOT_FOUND_ERROR:
	    if (Config.DEBUG)
		Log.e("SERVER", "not found error");
    	    break;
    	case Constants.BAD_REQUEST_ERROR:
	    if (Config.DEBUG)
		Log.e("SERVER", "bad request error");
    	    break;
    	case Constants.INTERNAL_SERVER_ERROR:
	    Toast.makeText(mContext, "SERVER ERROR", Toast.LENGTH_SHORT).show();
	    if (Config.DEBUG)
		Log.e("SERVER", "internal server error");
    	    break;
    	case Constants.METHOD_NOT_ALLOWED_ERROR:
	    if (Config.DEBUG)
		Log.e("SERVER", "method not allowd error");
    	    break;
    	}
    }

    public String makeString(ByteArrayInputStream inputStream) {
	StringBuffer out = new StringBuffer();
	int byteCount = 5;
	byte[] buffer = new byte[byteCount];
	int readSize;

	try {
	    while ( (readSize = inputStream.read(buffer)) != -1) {
		out.append(new String(buffer, 0, byteCount));
	    }
	} catch(IOException e) {
	    return "";
	}

	String result = out.toString();
	return result;
    }
}

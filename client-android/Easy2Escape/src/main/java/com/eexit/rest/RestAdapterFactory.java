package com.eexit.rest;

import java.lang.reflect.Type;
import android.content.Context;
import com.google.gson.Gson;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedString;
import com.eexit.ApiPaths;
import com.eexit.util.UserManager;
import com.eexit.util.GsonUtils;

public class RestAdapterFactory {

    public static class NoContentConverter implements Converter {
	public Object fromBody(TypedInput body, Type type) {
	    return new Object();
	}
	public TypedString toBody(Object object) {
	    return new TypedString("");
	}
    }


    public static RestAdapter getRestAdapter(Context context) {
	return getRestAdapter(context, true);
    }

    public static RestAdapter getRestAdapter(Context context, boolean hasContent) {
	Converter converter = null;
	if (hasContent) {
	    Gson gson = GsonUtils.getGsonObject();
	    converter = new GsonConverter(gson);
	} else {
	    converter = new NoContentConverter();
	}
	return getRestAdapter(context, converter);
    }

    public static RestAdapter getRestAdapter(Context context, Converter converter) {
	return new RestAdapter.Builder()
	    .setEndpoint(ApiPaths.SERVER)
	    .setClient(new ApiClient(context))
	    .setRequestInterceptor(new CustomRequestInterceptor(context))
	    .setConverter(converter)
	    .build();
    }

    public static class CustomRequestInterceptor implements RequestInterceptor {
	private Context mContext;
	public CustomRequestInterceptor(Context context) {
	    mContext = context.getApplicationContext();
	}

	@Override
	public void intercept(RequestInterceptor.RequestFacade request) {
	    long timeStamp = System.currentTimeMillis() / 1000L;
	    request.addHeader("Authorization", UserManager.getAuthorizationHeaderValue(mContext));
	    request.addHeader("x-app-signature", UserManager.getSignature(timeStamp));
	    request.addHeader("x-app-timestamp", ""+timeStamp);
	}
    }
}

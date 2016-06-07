package com.eexit.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.eexit.R;
import com.eexit.ApiPaths;
import com.eexit.ui.BaseActivity;
import com.eexit.ui.BaseFragment;
import com.eexit.util.LogUtils;

public class AgreeFragment extends BaseFragment {

    private BaseActivity mActivity;

    private int type;

    public int getType() {
	return type;
    }

    public void setType(int type) {
	this.type = type;
    }

    public static AgreeFragment newInstance(int type) {
	AgreeFragment f = new AgreeFragment();
	f.setType(type);
	return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	mActivity = (BaseActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	WebView webview = new WebView(mActivity);
	webview.getSettings().setJavaScriptEnabled(true);
	webview.addJavascriptInterface(new WebAppInterface(mActivity), "Android");
	webview.setWebChromeClient(new WebChromeClient() {
		public void onProgressChanged(WebView view, int progress) {
		    mActivity.setProgress(progress * 1000);
		}
	    });
	webview.setWebViewClient(new WebViewClient() {
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
		    Toast.makeText(mActivity, "Network Error " + description, Toast.LENGTH_SHORT).show();
		}
	    });
	if (type == 1) {
	    webview.loadUrl(ApiPaths.SERVER + "/utilization/");
	    mActivity.setActionBarTitle("이용약관");
	} else {
	    webview.loadUrl(ApiPaths.SERVER + "/personal/");
	    mActivity.setActionBarTitle("개인정보 취급방침");
	}
	return webview;
    }

    public class WebAppInterface {
	Context mContext;
	WebAppInterface(Context context) {
	    mContext = context;
	}
	@JavascriptInterface
	public void ok() {
	    // Toast.makeText(mActivity, "ok", Toast.LENGTH_SHORT).show();
	    // ((BaseFragmentActivity)mActivity).pop();
	    mActivity.finish();
	}
    }
}

package com.eexit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.facebook.*;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.DefaultAudience;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.eexit.R;
import com.eexit.ui.BaseActivity;
import com.eexit.ui.BaseFragment;
import com.eexit.ui.BaseFragmentActivity;
import com.eexit.util.LogUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import com.eexit.task.SendAccessTokenTask;
import com.eexit.task.SendAccessTokenTask.SendAccessTokenTaskListener;
import com.eexit.model.SendAccessTokenObject;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.eexit.ui.BaseDialogFragment.DialogClickListener;
import com.eexit.ui.BaseDialogFragment;
import com.eexit.util.UserManager;

public class SignupFacebookActivity extends BaseActivity {

    private CallbackManager callbackManager;
    private LoginManager mLoginManager;

    @Override
    protected int getLayoutResources() {
	return R.layout.activity_splash;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	FacebookSdk.sdkInitialize(this.getApplicationContext());
	callbackManager = CallbackManager.Factory.create();
	mLoginManager = LoginManager.getInstance();
	mLoginManager.logOut();
	mLoginManager.registerCallback(callbackManager,
				       new FacebookCallback<LoginResult>() {
					   @Override
					       public void onSuccess(LoginResult loginResult) {
					       AccessToken accessToken = AccessToken.getCurrentAccessToken();
					       loginWithFacebook(accessToken);
					   }
					   @Override
					       public void onCancel() {
					       LogUtils.common("SignupFacebookActivity", "cancel");
					       Toast.makeText(SignupFacebookActivity.this, "취소되었습니다", Toast.LENGTH_SHORT).show();
					       finish();
					   }
					   @Override
					       public void onError(FacebookException e) {
					       LogUtils.common("SignupFacebookActivity", "cancel");
					       Toast.makeText(SignupFacebookActivity.this, "에러", Toast.LENGTH_SHORT).show();
					       finish();
					   }
				       });
	login();
    }

    private static final String PERMISSION = "publish_actions";
    private DefaultAudience defaultAudience = DefaultAudience.FRIENDS;
    private LoginBehavior loginBehavior = LoginBehavior.SSO_WITH_FALLBACK;
    private List<String> permissions = Collections.<String>emptyList();

    public void login() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
	if (accessToken == null) {
	    mLoginManager.setDefaultAudience(defaultAudience);
	    mLoginManager.setLoginBehavior(loginBehavior);
	    mLoginManager.logInWithReadPermissions(SignupFacebookActivity.this,
						   Arrays.asList("user_friends", "user_location", "public_profile"));
	} else {
	    loginWithFacebook(accessToken);
	}
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    // @Override
    // public BaseFragment getFragment() {
    // 	return FacebookWebViewFragment.newInstance();
    // }



    private SendAccessTokenTask mSendAccessTokenTask;

    public void loginWithFacebook(AccessToken accessToken) {
	String token = accessToken.getToken();
	LogUtils.common("SignupFacebookActivity", "loginWithFacebook : " + token);
	mSendAccessTokenTask = new SendAccessTokenTask(SignupFacebookActivity.this,
						       token,
						       "facebook");
	mSendAccessTokenTask.setSendAccessTokenTaskListener(new SendAccessTokenTaskListener() {
		@Override
		public void onSuccess(SendAccessTokenObject result) {
		    UserManager.setSessionKey(SignupFacebookActivity.this, result.getApiKey());
		    UserManager.setUserId(SignupFacebookActivity.this, result.getUserId());
		    // Intent intent = new Intent(SignupGPlusActivity.this, SplashActivity.class);
		    // startActivity(intent);
		    setResult(RESULT_OK);
		    finish();
		}

		@Override
		public void onFailure(SendAccessTokenObject result) {
		    if (result != null) {
			showDialogMessage(result.getMessage());
		    } else {
			showDialogMessage("네트워크 에러");
		    }
		}
	    });
	mSendAccessTokenTask.execute();
    }

    @Override
    public void onPause() {
	if (mSendAccessTokenTask != null) {
	    mSendAccessTokenTask.cancel(true);
	}
	super.onPause();
    }

    public void showDialogMessage(String message) {
	FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
	Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
	if (prev != null) {
	    ft.remove(prev);
	}
	ft.addToBackStack(null);
	BaseDialogFragment newFragment
	    = BaseDialogFragment.newInstance("",
					     message,
					     false);
	newFragment.setDialogClickListener(new DialogClickListener() {
		@Override
		public void onOk() {
		    setResult(RESULT_OK);
		    finish();
		}
		@Override
		public void onCancel() {
		    setResult(RESULT_OK);
		    finish();
		}
	    });
	newFragment.show(ft, "dialog");
    }
}

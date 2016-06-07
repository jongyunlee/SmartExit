package com.eexit.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.widget.Toast;
import butterknife.ButterKnife;
import com.eexit.R;
import com.eexit.model.AlertMessage;
import com.eexit.model.SplashResult;
import com.eexit.task.SplashTask.SplashTaskListener;
import com.eexit.task.SplashTask;
import com.eexit.ui.BaseActivity;
import com.eexit.ui.BaseDialogFragment.DialogClickListener;
import com.eexit.ui.BaseDialogFragment;
import com.eexit.ui.HomeActivity;
import com.eexit.util.LogUtils;
import com.eexit.util.UserManager;

public class SplashActivity extends BaseActivity {

    private SplashTask mSplashTask;
    private boolean mSticky = false;
    private String mAction = "";
    private String mSessionKey = "";
    private String mUserId = "";
    private boolean mSessionCheckSuccess = false;
    private Context context;
    private String mNick = "";
    private String mPhoneNumber = "";
    private SplashResult result;
    private boolean isResumed = false;

    @Override
    protected int getLayoutResources() {
    	return R.layout.activity_splash;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
	loadInitialData();
    }

    public void loadInitialData() {
	isResumed = true;
	if (UserManager.isLoggedIn(SplashActivity.this)) {
	    mSplashTask = new SplashTask(SplashActivity.this);
	    mSplashTask.setEnableDialog(false);
	    mSplashTask.setSplashTaskListener(new SplashTaskListener() {
		    @Override
		    public void onSuccess(SplashResult result) {
			AlertMessage msg = result.getAlertMessage();
			if (msg != null) {
			    mSticky = msg.getSticky();
			    mAction = msg.getAction();
			}
			mSessionKey = result.getSessionKey();
			mUserId = result.getUserId();
			mSessionCheckSuccess = result.getSessionCheckSuccess();
			mNick = result.getNickname();
			mPhoneNumber= result.getPhoneNumber();
			SplashActivity.this.result = result;
			if (result.getHasMessage()) {
			    if (msg != null)
				showDialogMessage(msg.getTitle(),
						  msg.getMessage(),
						  msg.getCancelable());
			} else {
			    if (mSessionCheckSuccess) {
				gotoHomeActivity();
			    } else {
				showSessionExpired();
			    }
			}
		    }

		    @Override
		    public void onFailure(SplashResult result) {
			if (result != null) {
			    showErrorMessage(result.getMessage());
			} else {
			    showErrorMessage("서버에러. 관리자에게 문의하세요");
			}
		    }
		});
	    mSplashTask.execute();
	} else {
	    gotoHomeActivity();
	}
    }

    @Override
    public void onPause() {
	if (mSplashTask != null) {
	    mSplashTask.cancel(true);
	}
	super.onPause();
    }

    public void showDialogMessage(String title, String message, boolean cancelable) {
	FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
	Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
	if (prev != null) {
	    ft.remove(prev);
	}
	ft.addToBackStack(null);
	BaseDialogFragment newFragment
	    = BaseDialogFragment.newInstance(title,
					     message,
					     cancelable);
	newFragment.setDialogClickListener(new DialogClickListener() {
		@Override
		public void onCancel() {
		    if (mSticky)
			finish();
		    else
			gotoHomeActivity();
		}

		@Override
		public void onOk() {
		    if (TextUtils.isEmpty(mAction))
			gotoHomeActivity();
		    else
			gotoWebPage();
		}
	    });
	newFragment.show(ft, "dialog");
    }

    public void showSessionExpired() {
	FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
	Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
	if (prev != null) {
	    ft.remove(prev);
	}
	ft.addToBackStack(null);
	String message = "";
	if (UserManager.isLoggedIn(SplashActivity.this)) {
	    message = "세션이 만료되었습니다.";
	    UserManager.logout(SplashActivity.this);
	} else {
	    gotoHomeActivity();
	    return;
	}
	BaseDialogFragment newFragment
	    = BaseDialogFragment.newInstance("",
					     message,
					     false);
	newFragment.setDialogClickListener(new DialogClickListener() {
		@Override
		public void onCancel() {
		    finish();
		}

		@Override
		public void onOk() {
		    gotoHomeActivity();
		}
	    });
	newFragment.show(ft, "dialog");
    }

    public void gotoHomeActivity() {
	if (mSessionCheckSuccess) {
	    UserManager.setSessionKey(SplashActivity.this, mSessionKey);
	    UserManager.setUserId(SplashActivity.this, mUserId);
	    UserManager.setUserNickname(SplashActivity.this, mNick);
	    UserManager.setPhoneNumber(SplashActivity.this, mPhoneNumber);

	    UserManager.setCountry(SplashActivity.this, result.getCountry());
	    UserManager.setTitle(SplashActivity.this, result.getTitle());
	    UserManager.setCoverImage(SplashActivity.this, result.getCoverImage());
	    UserManager.setProfileImage(SplashActivity.this, result.getProfileImage());
	    UserManager.setFlag(SplashActivity.this, result.getFlag());
	    UserManager.setCity(SplashActivity.this, result.getCity());

	    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
	    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    startActivity(intent);
	    finish();
	} else {
	    UserManager.setSessionKey(SplashActivity.this, "");
	    UserManager.setUserId(SplashActivity.this, "");
	    UserManager.setUserNickname(SplashActivity.this, "");
	    UserManager.setPhoneNumber(SplashActivity.this, "");

	    UserManager.setCountry(SplashActivity.this, "");
	    UserManager.setTitle(SplashActivity.this, "");
	    UserManager.setCoverImage(SplashActivity.this, "");
	    UserManager.setProfileImage(SplashActivity.this, "");
	    UserManager.setFlag(SplashActivity.this, "");
	    UserManager.setCity(SplashActivity.this, "");
	    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
	    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    startActivity(intent);
	    finish();
	}
    }

    public void gotoWebPage() {
	Intent i = new Intent(Intent.ACTION_VIEW);
	i.setData(Uri.parse(mAction));
	startActivity(i);
	finish();
    }

    public void gotoSignupPage() {
	Intent intent = new Intent(SplashActivity.this, SignupActivity.class);
	startActivity(intent);
	finish();
    }

    public void showErrorMessage(String message) {
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
		public void onCancel() {
		    finish();
		}

		@Override
		public void onOk() {
		    finish();
		}
	    });
	newFragment.show(ft, "dialog");
    }

    @Override
    public void onResume() {
	super.onResume();
	if (isResumed) {
	    loadInitialData();
	}
    }
}

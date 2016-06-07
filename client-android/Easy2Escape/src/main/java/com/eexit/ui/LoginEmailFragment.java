package com.eexit.ui;

import android.app.Activity;
import android.app.Dialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.eexit.R;
import com.eexit.model.LoginEmailObject;
import com.eexit.task.LoginEmailTask.LoginEmailTaskListener;
import com.eexit.task.LoginEmailTask;
import com.eexit.ui.BaseActivity;
import com.eexit.ui.BaseDialogFragment;
import com.eexit.ui.BaseFragment;
import com.eexit.util.UserManager;

public class LoginEmailFragment extends DialogFragment {

    @InjectView(R.id.edit_email) EditText mEmailEdit;
    @InjectView(R.id.edit_password) EditText mPasswordEdit;
    @InjectView(R.id.btn_next) Button mNextButton;

    private BaseActivity mActivity;
    private Dialog dialog;

    public static LoginEmailFragment newInstance() {
	LoginEmailFragment f = new LoginEmailFragment();
	return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	mActivity = (BaseActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	View v = inflater.inflate(R.layout.fragment_login_email, null);
	ButterKnife.inject(this, v);
	mNextButton.setOnClickListener(onButtonsClick);
	return v;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
	dialog = new Dialog(mActivity, android.R.style.Theme_Translucent_NoTitleBar);
    	// dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
    	// 			    WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        return dialog;
    }

    OnClickListener onButtonsClick = new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btn_next:
		    String email = mEmailEdit.getText().toString();
		    String password = mPasswordEdit.getText().toString();
		    if (TextUtils.isEmpty(email)) {
			Toast.makeText(mActivity, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
			return;
		    }
		    if (TextUtils.isEmpty(password)) {
			Toast.makeText(mActivity, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
			return;
		    }
		    login(email, password);
		    break;
		}
	    }
	};

    private LoginEmailTask mLoginEmailTask;

    public void login(String email, String password) {
	mLoginEmailTask = new LoginEmailTask(mActivity, email, password);
	mLoginEmailTask.setLoginEmailTaskListener(new LoginEmailTaskListener() {
		@Override
		public void onSuccess(LoginEmailObject result) {
		    Toast.makeText(mActivity, "로그인 되었습니다", Toast.LENGTH_SHORT).show();
		    UserManager.setSessionKey(mActivity, result.getApiKey());
		    UserManager.setUserNickname(mActivity, result.getUsername());
		    UserManager.setUserId(mActivity, result.getUserId());
		    UserManager.setProfileImage(mActivity, result.getProfileImage());
		    Intent intent = new Intent();
		    mActivity.setResult(Activity.RESULT_OK, intent);
		    mActivity.finish();
		}

		@Override
		public void onFailure(LoginEmailObject result) {
		    if (result != null) {
			showDialogMessage(result.getMessage());
		    } else {
			showDialogMessage("Network Error");
		    }
		}
	    });
	mLoginEmailTask.execute();
    }


    public void showDialogMessage(String message) {
	FragmentTransaction ft = getFragmentManager().beginTransaction();
	Fragment prev = getFragmentManager().findFragmentByTag("dialog");
	if (prev != null) {
	    ft.remove(prev);
	}
	ft.addToBackStack(null);
	BaseDialogFragment newFragment
	    = BaseDialogFragment.newInstance("",
					     message,
					     false);
	newFragment.show(ft, "dialog");
    }

    @Override
    public void onPause() {
	if (mLoginEmailTask != null) {
	    mLoginEmailTask.cancel(true);
	}
	super.onPause();
    }
}

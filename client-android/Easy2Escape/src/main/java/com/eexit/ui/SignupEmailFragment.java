package com.eexit.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.eexit.R;
import com.eexit.model.SignupEmailObject;
import com.eexit.task.SignupEmailTask.SignupEmailTaskListener;
import com.eexit.task.SignupEmailTask;
import com.eexit.ui.BaseActivity;
import com.eexit.ui.BaseDialogFragment;
import com.eexit.ui.BaseFragment;
import com.eexit.util.UserManager;

public class SignupEmailFragment extends DialogFragment {

    @InjectView(R.id.edit_name) EditText mNameEdit;
    @InjectView(R.id.edit_email) EditText mEmailEdit;
    @InjectView(R.id.edit_password) EditText mPasswordEdit;
    @InjectView(R.id.edit_password_ok) EditText mPasswordOkEdit;
    @InjectView(R.id.btn_next) Button mNextButton;
    @InjectView(R.id.txt_agree1) TextView mAgree1View;
    @InjectView(R.id.txt_agree2) TextView mAgree2View;
    @InjectView(R.id.edit_recommender) EditText mRecommenderEdit;

    private BaseActivity mActivity;
    private SignupEmailTask mSignupEmailTask;
    private Dialog dialog;

    public static SignupEmailFragment newInstance() {
	SignupEmailFragment f = new SignupEmailFragment();
	return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	mActivity = (BaseActivity) getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
	dialog = new Dialog(mActivity, android.R.style.Theme_Translucent_NoTitleBar);
    	// dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
    	// 			    WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	View v = inflater.inflate(R.layout.fragment_signup_email, null);
	ButterKnife.inject(this, v);
	mNextButton.setOnClickListener(onButtonsClick);
	mAgree1View.setOnClickListener(onButtonsClick);
	mAgree2View.setOnClickListener(onButtonsClick);
	mAgree1View.setText(Html.fromHtml("회원가입을 함으로써 바로꽃방의 <font color=\"#00f\"><u>이용약관</u></fonrt>,"));
	mAgree2View.setText(Html.fromHtml("<font color=\"#00f\"><u>개인정보 취급방침</u></font> 및 운영원칙에 동의하시게 됩니다"));
	return v;
    }

    OnClickListener onButtonsClick = new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		Intent intent;
		switch(v.getId()) {
		case R.id.btn_next:
		    String name = mNameEdit.getText().toString();
		    String email = mEmailEdit.getText().toString();
		    String password = mPasswordEdit.getText().toString();
		    String passwordOk = mPasswordOkEdit.getText().toString();
		    String recommender = mRecommenderEdit.getText().toString();
		    if (TextUtils.isEmpty(name)) {
			Toast.makeText(mActivity, "이름을 입력해주세요", Toast.LENGTH_SHORT).show();
			return;
		    }
		    if (TextUtils.isEmpty(email)) {
			Toast.makeText(mActivity, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
			return;
		    }
		    if (TextUtils.isEmpty(password)) {
			Toast.makeText(mActivity, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
			return;
		    }
		    if (!password.equals(passwordOk)) {
			Toast.makeText(mActivity, "재입력 비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
			return;
		    }
		    signup(name, email, password, recommender);
		    break;
		case R.id.txt_agree1:
		    // ((BaseFragmentActivity)mActivity).changeFragmentSlideFromBottom(AgreeFragment.newInstance(1), "agree1");
		    intent = new Intent(mActivity, AgreeActivity.class);
		    intent.putExtra("TYPE", 1);
		    startActivity(intent);
		    break;
		case R.id.txt_agree2:
		    intent = new Intent(mActivity, AgreeActivity.class);
		    intent.putExtra("TYPE", 2);
		    startActivity(intent);
		    // ((BaseFragmentActivity)mActivity).changeFragmentSlideFromBottom(AgreeFragment.newInstance(2), "agree2");
		    break;
		}
	    }
	};

    public void signup(String name, String email, String password, String recommender) {
	mSignupEmailTask = new SignupEmailTask(mActivity, name, email, password, recommender);
	mSignupEmailTask.setSignupEmailTaskListener(new SignupEmailTaskListener() {
		@Override
		public void onSuccess(SignupEmailObject result) {
		    Toast.makeText(mActivity, "회원가입되었습니다", Toast.LENGTH_SHORT).show();
		    UserManager.setSessionKey(mActivity, result.getApiKey());
		    UserManager.setUserNickname(mActivity, result.getUsername());
		    UserManager.setUserId(mActivity, result.getUserId());
		    UserManager.setProfileImage(mActivity, result.getProfileImage());
		    Intent intent = new Intent();
		    mActivity.setResult(Activity.RESULT_OK, intent);
		    mActivity.finish();
		}

		@Override
		public void onFailure(SignupEmailObject result) {
		    if (result != null) {
			showDialogMessage(result.getMessage());
		    } else {
			showDialogMessage("Network Error");
		    }
		}
	    });
	mSignupEmailTask.execute();
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
	if (mSignupEmailTask != null) {
	    mSignupEmailTask.cancel(true);
	}
	super.onPause();
    }
}

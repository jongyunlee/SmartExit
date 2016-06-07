package com.eexit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.eexit.R;
import com.eexit.ui.BaseActivity;
import com.eexit.ui.BaseFragment;
import com.eexit.ui.BaseFragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.eexit.ui.BaseDialogFragment;
import com.eexit.ui.BaseDialogFragment.DialogClickListener;

public class LoginMainFragment extends BaseFragment {

    @InjectView(R.id.btn_email) TextView mEmailLoginButton;
    @InjectView(R.id.btn_facebook) Button mFbButton;
    @InjectView(R.id.btn_googleplus) Button mGplusButton;
    @InjectView(R.id.btn_kakao) Button mKakaoButton;
    @InjectView(R.id.btn_email_signup) Button mEmailButton;

    private BaseActivity mActivity;

    public static LoginMainFragment newInstance() {
	LoginMainFragment f = new LoginMainFragment();
	return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	mActivity = (BaseActivity) getActivity();
	mActivity.setActionBarTitle("");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	View v = inflater.inflate(R.layout.fragment_login_main, null);
	ButterKnife.inject(this, v);
	mEmailLoginButton.setOnClickListener(onButtonsClick);
	mFbButton.setOnClickListener(onButtonsClick);
	mGplusButton.setOnClickListener(onButtonsClick);
	mGplusButton.setVisibility(View.GONE);
	mKakaoButton.setOnClickListener(onButtonsClick);
	mEmailButton.setOnClickListener(onButtonsClick);
	mEmailLoginButton.setText(Html.fromHtml("<u>이메일로 바로시작</u>"));
	return v;
    }

    OnClickListener onButtonsClick = new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btn_email:
		    showLoginEmailDialog();
		    break;
		case R.id.btn_facebook:
		    Intent intent = new Intent(mActivity, SignupFacebookActivity.class);
		    mActivity.startActivityForResult(intent, SignupActivity.SIGNUP_SNS);
		    break;
		case R.id.btn_googleplus:

		    break;
		case R.id.btn_kakao:
		    intent = new Intent(mActivity, SignupKakaoActivity.class);
		    mActivity.startActivityForResult(intent, SignupActivity.SIGNUP_SNS);
		    break;
		case R.id.btn_email_signup:
		    showSignupEmailDialog();
		    break;
		}
	    }
	};

    public void showLoginEmailDialog() {
    	FragmentTransaction ft = getFragmentManager().beginTransaction();
    	Fragment prev = getFragmentManager().findFragmentByTag("dialog");
    	if (prev != null) {
    	    ft.remove(prev);
    	}
    	ft.addToBackStack(null);
    	LoginEmailFragment newFragment
    	    = LoginEmailFragment.newInstance();
    	newFragment.show(ft, "dialog");
    }

    public void showSignupEmailDialog() {
    	FragmentTransaction ft = getFragmentManager().beginTransaction();
    	Fragment prev = getFragmentManager().findFragmentByTag("dialog");
    	if (prev != null) {
    	    ft.remove(prev);
    	}
    	ft.addToBackStack(null);
    	SignupEmailFragment newFragment
    	    = SignupEmailFragment.newInstance();
    	newFragment.show(ft, "dialog");
    }
}

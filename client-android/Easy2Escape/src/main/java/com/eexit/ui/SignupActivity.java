package com.eexit.ui;

import android.content.Intent;
import com.eexit.R;
import com.eexit.ui.BaseFragment;
import com.eexit.ui.BaseFragmentActivity;

public class SignupActivity extends BaseFragmentActivity {

    public static final int SIGNUP_SNS = 2;

    @Override
    protected int getLayoutResources() {
	return R.layout.activity_base_fragment;
    }

    @Override
    public BaseFragment getFragment() {
	return LoginMainFragment.newInstance();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
	super.onActivityResult(requestCode, resultCode, intent);
	switch(requestCode){
	case SignupActivity.SIGNUP_SNS:
	    if(resultCode == RESULT_OK){
		Intent i = new Intent(SignupActivity.this, SplashActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
	    }
	    break;
	}
    }
}

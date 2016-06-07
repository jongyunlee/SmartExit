package com.eexit.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout.LayoutParams;
import android.widget.FrameLayout;
import com.eexit.R;

public abstract class BaseFragmentActivity extends BaseActivity {

    public abstract BaseFragment getFragment();

    @Override
    protected int getLayoutResources() {
	return R.layout.activity_base_fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
	if (savedInstanceState == null) {
	    setInitialFragment();
	}
    }
    private void setInitialFragment() {
	FragmentManager fragmentManager = getSupportFragmentManager();
	FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
	fragmentTransaction.add(R.id.content_frame, getFragment()).commit();
    }

    public void changeFragment(BaseFragment newFragment) {
	FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
	// transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
	transaction.replace(R.id.content_frame, newFragment);
	transaction.setCustomAnimations(R.anim.slide_in_right,
					R.anim.nothing,
					R.anim.nothing,
					R.anim.slide_out_right);
	// transaction.addToBackStack(null);
	transaction.commit();
    }

    public void changeFragment(BaseFragment newFragment, String id) {
	FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
	transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
	transaction.replace(R.id.content_frame, newFragment);
	// transaction.addToBackStack(id);
	transaction.commit();
    }

    public void changeFragmentToBackStack(BaseFragment fragment, String title) {
	FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
	ft.replace(R.id.content_frame, fragment, title);
	ft.addToBackStack(title);
	ft.commit();
    }
}

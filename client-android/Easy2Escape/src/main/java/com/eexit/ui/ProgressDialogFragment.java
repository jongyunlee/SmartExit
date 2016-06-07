package com.eexit.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.eexit.R;

public class ProgressDialogFragment extends DialogFragment {

    private String mMessage;
    private boolean mCancelable;

    public ProgressDialogFragment(String message) {
	mMessage = message;
	mCancelable = true;
    }

    public ProgressDialogFragment(String message, boolean cancelable) {
	mMessage = message;
	mCancelable = cancelable;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
	final ProgressDialog dialog = new ProgressDialog(getActivity());
	if (mMessage != null && !"".equals(mMessage))
	    dialog.setMessage(mMessage);
	dialog.setCancelable(false);
	return dialog;
    }

    public static Fragment showLoadingProgress(FragmentManager fm, String message, String tag) {
	FragmentTransaction ft = fm.beginTransaction();
	Fragment fragment = fm.findFragmentByTag(tag);
	if (fragment == null) {
	    ProgressDialogFragment loading = new ProgressDialogFragment(message);
	    loading.show(fm, tag);
	    return loading;
	}
	return fragment;
    }

    public static Fragment showLoadingProgress(FragmentManager fm, String message, String tag, boolean cancelable) {
	FragmentTransaction ft = fm.beginTransaction();
	Fragment fragment = fm.findFragmentByTag(tag);
	if (fragment == null) {
	    ProgressDialogFragment loading = new ProgressDialogFragment(message, cancelable);
	    loading.show(fm, tag);
	    return loading;
	}
	return fragment;
    }

    public static void dismissLoadingProgress(FragmentManager fm, String tag) {
	FragmentTransaction ft = fm.beginTransaction();
	Fragment fragment = fm.findFragmentByTag(tag);
	if (fragment != null) {
	    ft.remove(fragment);
	}
	ft.commitAllowingStateLoss();
    }

    public static void dismissLoadingProgress(FragmentManager fm, Fragment fragment) {
	FragmentTransaction ft = fm.beginTransaction();
	if (fragment != null) {
	    ft.remove(fragment);
	}
	ft.commitAllowingStateLoss();
    }
}

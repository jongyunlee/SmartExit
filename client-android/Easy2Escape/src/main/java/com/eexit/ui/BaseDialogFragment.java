package com.eexit.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.eexit.R;
import com.eexit.ui.BaseActivity;

public class BaseDialogFragment extends DialogFragment {

    private BaseActivity mActivity;
    private TextView mBodyText;
    private String mBody;
    private String mTitle;
    private boolean mCancelable;

    public DialogClickListener mListener;
    LayoutInflater inflater;

    public static BaseDialogFragment newInstance(String title, String body, boolean cancelable) {
	BaseDialogFragment f = new BaseDialogFragment();
	f.setTitle(title);
	f.setBody(body);
	f.setCancelable(cancelable);
	return f;
    }

    public interface DialogClickListener {
	public void onCancel();
  	public void onOk();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	mActivity = (BaseActivity) getActivity();
	inflater = LayoutInflater.from(mActivity);
    }

    public int getLayout() {
	return R.layout.fragment_checkversion_dialog;
    }

    public View getCustomView() {
	View customView = inflater.inflate(getLayout(), null);
	mBodyText = (TextView) customView.findViewById(R.id.body);
	mBodyText.setText(mBody);
	return customView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
	View customView = getCustomView();
        AlertDialog dialog;
	AlertDialog.Builder builder = new AlertDialog.Builder(mActivity).setTitle(mTitle);
	if (mCancelable)
	    builder.setNegativeButton("취소",
				     new DialogInterface.OnClickListener() {
					 public void onClick(DialogInterface dialog, int whichButton) {
					     if (mListener != null)
						 mListener.onCancel();
					     dismiss();
					 }
				     });
	builder.setPositiveButton("확인",
				 new DialogInterface.OnClickListener() {
				     public void onClick(DialogInterface dialog, int whichButton) {
					 if (mListener != null)
					     mListener.onOk();
					 dismiss();
				     }
				 })
	    .setView(customView);
	dialog = builder.create();
	return dialog;
    }

    public void setDialogClickListener(DialogClickListener listener) {
	mListener = listener;
    }

    public void setTitle(String title) {
	mTitle = title;
    }

    public void setBody(String body) {
	mBody = body;
    }

    public void setCancelable(boolean cancelable) {
	mCancelable = cancelable;
    }
}

package com.eexit.ui;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.eexit.R;
import com.eexit.widget.MatrialProgressWheel;
import java.util.ArrayList;

public class ProgressDialog extends Dialog {

    private TextView mMessageView;
    private String mMessage;
    private MatrialProgressWheel progressWheel;

    public String getMessage() {
	return mMessage;
    }

    public void setMessage(String message) {
	this.mMessage = mMessage;
    }

    public ProgressDialog(Context context) {
        super(context , android.R.style.Theme_Translucent_NoTitleBar);
    }

    public ProgressDialog(Context context, String title, String content, boolean cancelable) {
	this(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.6f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.progress_dialog);
	mMessageView = (TextView) findViewById(R.id.message);
	progressWheel = (MatrialProgressWheel)findViewById(R.id.progress_wheel);
	progressWheel.setSpinSpeed((float)0.333);
	if (!TextUtils.isEmpty(mMessage)) {
	    mMessageView.setText(mMessage);
	} else {
	    mMessageView.setText("로딩중");
	}
    }
}

package com.eexit.ui;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.eexit.R;
import com.eexit.model.Pi;
import com.eexit.task.PiListTask.PiListTaskListener;
import com.eexit.task.PiListTask;
import com.eexit.util.LogUtils;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {

    @InjectView(R.id.recycler_view) RecyclerView mRecyclerView;
    @InjectView(R.id.btn_refresh) Button mRefreshButton;
    @InjectView(R.id.btn_message) Button mMessageButton;
    @InjectView(R.id.btn_fire) Button mFireButton;
    @InjectView(R.id.btn_flooding) Button mFloodingButton;
    @InjectView(R.id.btn_quake) Button mQuakeButton;



    private BaseActivity mActivity;
    private PiListAdapter mAdapter;
    private PiListTask mPiListTask;
    private ArrayList<String> phoneNumbers;

    public static HomeFragment newInstance() {
	HomeFragment f = new HomeFragment();
	return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	mActivity = (BaseActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	View v = inflater.inflate(R.layout.fragment_home, null);
	ButterKnife.inject(this, v);

	CustomLayoutManager layoutManager = new CustomLayoutManager(mActivity);
	layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
	// layoutManager.scrollToPosition(currPos);
	mRecyclerView.setLayoutManager(layoutManager);
	mAdapter = new PiListAdapter(mActivity);
	mAdapter.setHasStableIds(true);
 	mAdapter.setPiListListener(new PiListAdapter.PiListListener() {
		@Override
		public void onItemClick(Pi pi) {
		    ((BaseFragmentActivity)mActivity).changeFragmentToBackStack(PiDetailFragment.newInstance(pi.getId()), "detail");
		}
	    });

	mRecyclerView.setAdapter(mAdapter);
	mRefreshButton.setOnClickListener(onButtonsClick);
	mMessageButton.setOnClickListener(onButtonsClick);
	mFireButton.setOnClickListener(onButtonsClick);
	mFloodingButton.setOnClickListener(onButtonsClick);
	mQuakeButton.setOnClickListener(onButtonsClick);
	load();
	initPhoneNumbers();

	return v;
    }

    public void initPhoneNumbers() {
	phoneNumbers = new ArrayList<String>();
	phoneNumbers.add("010-5173-7532");
    }

    public void load() {
	mPiListTask = new PiListTask(mActivity);
	mPiListTask.setPiListTaskListener(new PiListTaskListener() {
		@Override
		public void onSuccess(List<Pi> result) {
		    mAdapter.swapData(result);
		}

		@Override
		public void onFailure(String result) {

		}
	    });
	mPiListTask.execute();
    }

    @Override
    public void onPause() {
	if (mPiListTask != null) {
	    mPiListTask.cancel(true);
	}
	super.onPause();
    }

    OnClickListener onButtonsClick = new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btn_refresh:
		    load();
		    break;
		case R.id.btn_message:
		    for (String number : phoneNumbers) {
			sendSMS(number, "Emergency Situation. Please go to designated position");
		    }
		    break;
		case R.id.btn_fire:

		    break;
		case R.id.btn_flooding:

		    break;
		case R.id.btn_quake:

		    break;

		}
	    }
	};

    public void sendSMS(String phoneNumber,String message) {
        SmsManager smsManager = SmsManager.getDefault();


	String SENT = "SMS_SENT";
	String DELIVERED = "SMS_DELIVERED";

	SmsManager sms = SmsManager.getDefault();
	ArrayList<String> parts = sms.divideMessage(message);
	int messageCount = parts.size();

	LogUtils.common("HomeFragment", "Message Count: " + messageCount);

	ArrayList<PendingIntent> deliveryIntents = new ArrayList<PendingIntent>();
	ArrayList<PendingIntent> sentIntents = new ArrayList<PendingIntent>();

	PendingIntent sentPI = PendingIntent.getBroadcast(mActivity, 0, new Intent(SENT), 0);
	PendingIntent deliveredPI = PendingIntent.getBroadcast(mActivity, 0, new Intent(DELIVERED), 0);

	for (int j = 0; j < messageCount; j++) {
	    sentIntents.add(sentPI);
	    deliveryIntents.add(deliveredPI);
	}

	// ---when the SMS has been sent---
	mActivity.registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context arg0, Intent arg1) {
                    switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(mActivity.getBaseContext(), "SMS sent",
				       Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(mActivity.getBaseContext(), "Generic failure",
				       Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(mActivity.getBaseContext(), "No service",
				       Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(mActivity.getBaseContext(), "Null PDU",
				       Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(mActivity.getBaseContext(), "Radio off",
				       Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }, new IntentFilter(SENT));

	// ---when the SMS has been delivered---
	mActivity.registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context arg0, Intent arg1) {
                    switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(mActivity.getBaseContext(), "SMS delivered",
				       Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(mActivity.getBaseContext(), "SMS not delivered",
				       Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }, new IntentFilter(DELIVERED));
	smsManager.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
	/* sms.sendMultipartTextMessage(phoneNumber, null, parts, sentIntents, deliveryIntents); */
    }
}

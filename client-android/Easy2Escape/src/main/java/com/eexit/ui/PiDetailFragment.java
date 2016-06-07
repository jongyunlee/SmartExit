package com.eexit.ui;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.eexit.ApiPaths;
import com.eexit.R;
import com.eexit.model.Pi;
import com.eexit.model.Pi;
import com.eexit.model.Pi;
import com.eexit.task.ChangeDestinationTask.ChangeDestinationTaskListener;
import com.eexit.task.ChangeDestinationTask;
import com.eexit.task.ChangeDirectionTask.ChangeDirectionTaskListener;
import com.eexit.task.ChangeDirectionTask;
import com.eexit.task.ChangeMessageTask.ChangeMessageTaskListener;
import com.eexit.task.ChangeMessageTask;
import com.eexit.task.ChangeStateTask.ChangeStateTaskListener;
import com.eexit.task.ChangeStateTask;
import com.eexit.task.PiTask.PiTaskListener;
import com.eexit.task.PiTask;
import com.eexit.widget.ProgressImageView;

public class PiDetailFragment extends BaseFragment {

    @InjectView(R.id.image) ProgressImageView mImageView;
    @InjectView(R.id.txt_id) TextView mIdView;
    @InjectView(R.id.txt_temp) TextView mTempView;
    @InjectView(R.id.txt_humidity) TextView mHumidityView;
    @InjectView(R.id.txt_direction) TextView mDirectionView;
    @InjectView(R.id.txt_location) TextView mLocationView;
    @InjectView(R.id.txt_state) TextView mStateView;
    @InjectView(R.id.txt_destination) TextView mDestinationView;
    @InjectView(R.id.txt_message) TextView mMessageView;

    @InjectView(R.id.exit1) ImageView mExit1Button;
    @InjectView(R.id.exit2) ImageView mExit2Button;
    @InjectView(R.id.exit3) ImageView mExit3Button;
    @InjectView(R.id.exit4) ImageView mExit4Button;
    @InjectView(R.id.exit5) ImageView mExit5Button;
    @InjectView(R.id.exit6) ImageView mExit6Button;
    @InjectView(R.id.exit7) ImageView mExit7Button;
    @InjectView(R.id.exit8) ImageView mExit8Button;
    @InjectView(R.id.btn_stop) Button mStopButton;

    @InjectView(R.id.edit_state) EditText mStateEdit;
    @InjectView(R.id.btn_state) Button mStateButton;

    @InjectView(R.id.edit_dest) EditText mDestEdit;
    @InjectView(R.id.btn_dest) Button mDestButton;

    @InjectView(R.id.edit_message) EditText mMessageEdit;
    @InjectView(R.id.btn_message) Button mMessageButton;

    @InjectView(R.id.btn_refresh) Button mRefreshButton;
    @InjectView(R.id.btn_alarm) Button mAlarmButton;
    @InjectView(R.id.btn_alarm_stop) Button mStopAlarmButton;


    private BaseActivity mActivity;
    private String id;
    private PiTask mPiTask;
    private ChangeDirectionTask mChangeDirectionTask;
    private ChangeDestinationTask mChangeDestinationTask;
    private ChangeStateTask mChangeStateTask;
    private ChangeMessageTask mChangeMessageTask;
    private MediaPlayer mediaPlayer;

    public String getPiId() {
	return id;
    }

    public void setPiId(String id) {
	this.id = id;
    }


    public static PiDetailFragment newInstance(String id) {
	PiDetailFragment f = new PiDetailFragment();
	f.setPiId(id);
	return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	mActivity = (BaseActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	View v = inflater.inflate(R.layout.fragment_pi_detail, null);
	ButterKnife.inject(this, v);

	mExit1Button.setOnClickListener(onButtonsClick);
	mExit2Button.setOnClickListener(onButtonsClick);
	mExit3Button.setOnClickListener(onButtonsClick);
	mExit4Button.setOnClickListener(onButtonsClick);
	mExit5Button.setOnClickListener(onButtonsClick);
	mExit6Button.setOnClickListener(onButtonsClick);
	mExit7Button.setOnClickListener(onButtonsClick);
	mExit8Button.setOnClickListener(onButtonsClick);
	mStopButton.setOnClickListener(onButtonsClick);
	mAlarmButton.setOnClickListener(onButtonsClick);
	mStopAlarmButton.setOnClickListener(onButtonsClick);

	mStateButton.setOnClickListener(onButtonsClick);
	mDestButton.setOnClickListener(onButtonsClick);
	mMessageButton.setOnClickListener(onButtonsClick);

	mRefreshButton.setOnClickListener(onButtonsClick);

	load();
	return v;
    }

    public void load() {
	mPiTask = new PiTask(mActivity, id);
	mPiTask.setPiTaskListener(new PiTaskListener() {
		@Override
		public void onSuccess(Pi pi) {
		    refreshPiInfo(pi);
		}
		@Override
		public void onFailure(String result) {

		}
	    });
	mPiTask.execute();
    }

    public void refreshPiInfo(Pi pi) {
	mActivity.imageLoader.clearMemoryCache();
	mActivity.imageLoader.clearDiscCache();
	mImageView.displayImage(ApiPaths.SERVER + "/images/" + pi.getId() + ".jpg");
	mIdView.setText("ID : " + pi.getId());
	mTempView.setText("TEMP : " + pi.getTemp());
	mHumidityView.setText("HUM : " + pi.getHumidity());
	mDirectionView.setText("DIR : " + pi.getDirection());
	mLocationView.setText("LOC : " + pi.getLocation());
	mStateView.setText("STATE : " + pi.getState());
	mDestinationView.setText("DEST : " + pi.getDestination());
	mMessageView.setText("MESSAGE : " + pi.getMessage());
    }

    @Override
    public void onPause() {
	if (mPiTask != null) {
	    mPiTask.cancel(true);
	}
	if (mChangeDirectionTask != null) {
	    mChangeDirectionTask.cancel(true);
	}
	if (mChangeDestinationTask != null) {
	    mChangeDestinationTask.cancel(true);
	}
	if (mChangeStateTask != null) {
	    mChangeStateTask.cancel(true);
	}
	if (mChangeMessageTask != null) {
	    mChangeMessageTask.cancel(true);
	}
	super.onPause();
    }

    OnClickListener onButtonsClick = new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		switch(v.getId()) {
		case R.id.exit1:
		    changeDirection("right");
		    break;
		case R.id.exit2:
		    changeDirection("left");
		    break;
		case R.id.exit3:
		    changeDirection("leftdown");
		    break;
		case R.id.exit4:
		    changeDirection("rightdown");
		    break;
		case R.id.exit5:
		    changeDirection("rightup");
		    break;
		case R.id.exit6:
		    changeDirection("leftup");
		    break;
		case R.id.exit7:
		    changeDirection("up");
		    break;
		case R.id.exit8:
		    changeDirection("down");
		    break;
		case R.id.btn_stop:
		    changeDirection("stop");
		    break;
		case R.id.btn_dest:
		    changeDestination(mDestEdit.getText().toString());
		    break;
		case R.id.btn_state:
		    changeState(mStateEdit.getText().toString());
		    break;
		case R.id.btn_message:
		    changeMessage(mMessageEdit.getText().toString());
		    break;
		case R.id.btn_refresh:
		    load();
		    break;
		case R.id.btn_alarm:
		    mediaPlayer = MediaPlayer.create(mActivity, R.raw.alarm);
		    mediaPlayer.setLooping(true);
		    mediaPlayer.start();
		    break;
		case R.id.btn_alarm_stop:
		    if (mediaPlayer != null) {
			mediaPlayer.setLooping(false);
			mediaPlayer.stop();
		    }
		    break;
		}

	    }
	};

    public void changeDirection(String direction) {
	mChangeDirectionTask = new ChangeDirectionTask(mActivity, getPiId(), direction);
	mChangeDirectionTask.setChangeDirectionTaskListener(new ChangeDirectionTaskListener() {
		@Override
		public void onSuccess(Pi result) {
		    Toast.makeText(mActivity, "direction changed", Toast.LENGTH_SHORT).show();
		    refreshPiInfo(result);
		}

		@Override
		public void onFailure(String result) {

		}
	    });
	mChangeDirectionTask.execute();
    }

    public void changeDestination(String destination) {
	mChangeDestinationTask = new ChangeDestinationTask(mActivity, getPiId(), destination);
	mChangeDestinationTask.setChangeDestinationTaskListener(new ChangeDestinationTaskListener() {
		@Override
		public void onSuccess(Pi result) {
		    Toast.makeText(mActivity, "destination changed", Toast.LENGTH_SHORT).show();
		    mDestEdit.setText("");
		    refreshPiInfo(result);
		}

		@Override
		public void onFailure(String result) {

		}
	    });
	mChangeDestinationTask.execute();
    }

    public void changeState(String state) {
	mChangeStateTask = new ChangeStateTask(mActivity, getPiId(), state);
	mChangeStateTask.setChangeStateTaskListener(new ChangeStateTaskListener() {
		@Override
		public void onSuccess(Pi result) {
		    Toast.makeText(mActivity, "state changed", Toast.LENGTH_SHORT).show();
		    mStateEdit.setText("");
		    refreshPiInfo(result);
		}

		@Override
		public void onFailure(String result) {

		}
	    });
	mChangeStateTask.execute();
    }

    public void changeMessage(String message) {
	mChangeMessageTask = new ChangeMessageTask(mActivity, getPiId(), message);
	mChangeMessageTask.setChangeMessageTaskListener(new ChangeMessageTaskListener() {
		@Override
		public void onSuccess(Pi result) {
		    Toast.makeText(mActivity, "message changed", Toast.LENGTH_SHORT).show();
		    mMessageEdit.setText("");
		    refreshPiInfo(result);
		}

		@Override
		public void onFailure(String result) {

		}
	    });
	mChangeMessageTask.execute();
    }
}

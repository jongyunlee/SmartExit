package com.eexit.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.kakao.AuthType;
import com.kakao.Session;
import com.kakao.SessionCallback;
import com.kakao.exception.KakaoException;
import com.kakao.helper.StoryProtocol;
import com.kakao.helper.TalkProtocol;
import com.eexit.R;
import com.eexit.ui.BaseActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.eexit.util.UserManager;
import com.eexit.task.SendAccessTokenTask;
import com.eexit.task.SendAccessTokenTask.SendAccessTokenTaskListener;
import com.eexit.model.SendAccessTokenObject;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.eexit.ui.BaseDialogFragment;
import com.eexit.ui.BaseDialogFragment.DialogClickListener;

public class SignupKakaoActivity extends BaseActivity {

    private Session session;
    private final SessionCallback sessionCallback = new SessionStatusCallback();
    private SendAccessTokenTask mSendAccessTokenTask;

    @Override
    protected int getLayoutResources() {
	return R.layout.activity_splash;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

	session = Session.getCurrentSession();
	session.close();
	session.addCallback(sessionCallback);
	startAuthorization();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        session.removeCallback(sessionCallback);
    }

    protected void onResume() {
        super.onResume();

        if (session.isClosed()){
            // loginButton.setVisibility(View.VISIBLE);
        } else {
            // loginButton.setVisibility(View.GONE);
            session.implicitOpen();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private class SessionStatusCallback implements SessionCallback {
	@Override
	public void onSessionOpened() {
	    // Log.i("COMMON", "accessToken : " + session.getAccessToken());
	    String accessToken = session.getAccessToken();
	    // Toast.makeText(SignupKakaoActivity.this, "accessToken : " + accessToken, Toast.LENGTH_SHORT).show();
	    send(accessToken);
	}

        @Override
        public void onSessionClosed(final KakaoException exception) {
	    Toast.makeText(SignupKakaoActivity.this, "네트워크 오류", Toast.LENGTH_SHORT).show();
	    finish();
        }

        @Override
        public void onSessionOpening() {

        }
    }

    public void startAuthorization() {
	final List<AuthType> authTypes = getAuthTypes();
	if(authTypes.size() == 1){
	    Session.getCurrentSession().open(authTypes.get(0), SignupKakaoActivity.this);
	} else {
	    onClickLoginButton(authTypes);
	}
    }

    private List<AuthType> getAuthTypes() {
        final List<AuthType> availableAuthTypes = new ArrayList<AuthType>();
        if(TalkProtocol.existCapriLoginActivityInTalk(SignupKakaoActivity.this, Session.getCurrentSession().isProjectLogin())){
            availableAuthTypes.add(AuthType.KAKAO_TALK);
        }
        if(StoryProtocol.existCapriLoginActivityInStory(SignupKakaoActivity.this, Session.getCurrentSession().isProjectLogin())){
            availableAuthTypes.add(AuthType.KAKAO_STORY);
        }
        availableAuthTypes.add(AuthType.KAKAO_ACCOUNT);

        final AuthType[] selectedAuthTypes = Session.getCurrentSession().getAuthTypes();
        availableAuthTypes.retainAll(Arrays.asList(selectedAuthTypes));

        // 개발자가 설정한 것과 available 한 타입이 없다면 직접계정 입력이 뜨도록 한다.
        if(availableAuthTypes.size() == 0){
            availableAuthTypes.add(AuthType.KAKAO_ACCOUNT);
        }
        return availableAuthTypes;
    }

    private void onClickLoginButton(final List<AuthType> authTypes){
        final List<Item> itemList = new ArrayList<Item>();
        if(authTypes.contains(AuthType.KAKAO_TALK)) {
            itemList.add(new Item(R.string.com_kakao_kakaotalk_account, R.drawable.kakaotalk_icon, AuthType.KAKAO_TALK));
        }
        if(authTypes.contains(AuthType.KAKAO_STORY)) {
            itemList.add(new Item(R.string.com_kakao_kakaostory_account, R.drawable.kakaostory_icon, AuthType.KAKAO_STORY));
        }
        if(authTypes.contains(AuthType.KAKAO_ACCOUNT)){
            itemList.add(new Item(R.string.com_kakao_other_kakaoaccount, R.drawable.kakaoaccount_icon, AuthType.KAKAO_ACCOUNT));
        }
        itemList.add(new Item(R.string.com_kakao_account_cancel, 0, null)); //no icon for this one

        final Item[] items = itemList.toArray(new Item[itemList.size()]);

        final ListAdapter adapter = new ArrayAdapter<Item>(
 	   SignupKakaoActivity.this,
            android.R.layout.select_dialog_item,
            android.R.id.text1, items){
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                TextView tv = (TextView)v.findViewById(android.R.id.text1);

                tv.setText(items[position].textId);
                tv.setTextSize(15);
                tv.setGravity(Gravity.CENTER);
                if(position == itemList.size() -1) {
                    tv.setBackgroundResource(R.drawable.kakao_cancel_button_background);
                } else {
                    tv.setBackgroundResource(R.drawable.kakao_account_button_background);
                }
                tv.setCompoundDrawablesWithIntrinsicBounds(items[position].icon, 0, 0, 0);

                int dp5 = (int) (5 * getResources().getDisplayMetrics().density + 0.5f);
                tv.setCompoundDrawablePadding(dp5);

                return v;
            }
        };


        new AlertDialog.Builder(SignupKakaoActivity.this)
            .setAdapter(adapter, new DialogInterface.OnClickListener() {
                public void onClick(final DialogInterface dialog, final int position) {
                    dialog.dismiss();

                    final AuthType authType = items[position].authType;
                    if (authType != null) {
                        Session.getCurrentSession().open(authType, SignupKakaoActivity.this);
                    }
                }
            }).create().show();

    }

    private static class Item {
        public final int textId;
        public final int icon;
        public final AuthType authType;
        public Item(final int textId, final Integer icon, final AuthType authType) {
            this.textId = textId;
            this.icon = icon;
            this.authType = authType;
        }
    }

    public void send(String accessToken) {
	mSendAccessTokenTask = new SendAccessTokenTask(SignupKakaoActivity.this,
						       accessToken,
						       "kakao");
	mSendAccessTokenTask.setSendAccessTokenTaskListener(new SendAccessTokenTaskListener() {
		@Override
		public void onSuccess(SendAccessTokenObject result) {
		    UserManager.setSessionKey(SignupKakaoActivity.this, result.getApiKey());
		    UserManager.setUserId(SignupKakaoActivity.this, result.getUserId());
		    setResult(RESULT_OK);
		    finish();
		}

		@Override
		public void onFailure(SendAccessTokenObject result) {
		    if (result != null) {
			showDialogMessage(result.getMessage());
		    } else {
			showDialogMessage("네트워크 에러");
		    }
		}
	    });
	mSendAccessTokenTask.execute();
    }

    @Override
    public void onPause() {
	if (mSendAccessTokenTask != null) {
	    mSendAccessTokenTask.cancel(true);
	}
	super.onPause();
    }

    public void showDialogMessage(String message) {
	FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
	Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
	if (prev != null) {
	    ft.remove(prev);
	}
	ft.addToBackStack(null);
	BaseDialogFragment newFragment
	    = BaseDialogFragment.newInstance("",
					     message,
					     false);
	newFragment.setDialogClickListener(new DialogClickListener() {
		@Override
		public void onOk() {
		    setResult(RESULT_OK);
		    finish();
		}
		@Override
		public void onCancel() {
		    setResult(RESULT_OK);
		    finish();
		}
	    });
	newFragment.show(ft, "dialog");
    }
}

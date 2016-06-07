package com.eexit.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.eexit.R;

public class StringFragment extends BaseFragment {

    @InjectView(R.id.txt_title) TextView mTitleView;

    private BaseActivity mActivity;
    private String title;

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public static StringFragment newInstance(String title) {
	StringFragment f = new StringFragment();
	f.setTitle(title);
	return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	mActivity = (BaseActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	View v = inflater.inflate(R.layout.fragment_string, null);
	ButterKnife.inject(this, v);
	mTitleView.setText(title);
	return v;
    }
}

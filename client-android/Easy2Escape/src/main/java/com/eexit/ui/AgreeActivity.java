package com.eexit.ui;

import com.eexit.R;

public class AgreeActivity extends BaseFragmentActivity {
    @Override
    public BaseFragment getFragment() {
	int type = getIntent().getIntExtra("TYPE", 1);
	return AgreeFragment.newInstance(type);
    }
}

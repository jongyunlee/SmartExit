package com.eexit.ui;

public class HomeActivity extends BaseFragmentActivity {

    @Override
    public BaseFragment getFragment() {
        return HomeFragment.newInstance();
    }
}

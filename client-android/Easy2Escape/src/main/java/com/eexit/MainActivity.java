package com.eexit;

import android.app.Activity;
import android.os.Bundle;
import com.eexit.ui.BaseActivity;

public class MainActivity extends BaseActivity
{
    @Override
    protected int getLayoutResources() {
        return R.layout.main;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
}

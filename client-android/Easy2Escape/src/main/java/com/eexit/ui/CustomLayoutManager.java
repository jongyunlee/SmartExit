package com.eexit.ui;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

public class CustomLayoutManager extends LinearLayoutManager {
    public CustomLayoutManager(Context context) {
	super(context);
    }
    @Override
    public boolean supportsPredictiveItemAnimations() {
	return true;
    }
}

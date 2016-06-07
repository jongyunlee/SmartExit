package com.eexit.ui;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.eexit.R;
import com.eexit.util.LogUtils;

public abstract class BaseActivity extends ActionBarActivity {

    public ImageLoader imageLoader = ImageLoader.getInstance();

    public Toolbar toolbar;
    public Drawable mActionBarBackgroundDrawable;
    private boolean visibility = true;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResources());
	toolbar = (Toolbar) findViewById(R.id.toolbar);
	if (toolbar != null) {
	    setSupportActionBar(toolbar);
	    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	    // init actionbar alpha
	    // toolbar.refreshDrawableState();
	    mActionBarBackgroundDrawable = toolbar.getBackground();
	    mActionBarBackgroundDrawable.setAlpha(255);
	    toolbar.setBackground(mActionBarBackgroundDrawable);
	}
    }

    protected abstract int getLayoutResources();

    protected void setActionBarIcon(int iconRes) {
	toolbar.setNavigationIcon(iconRes);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	LogUtils.common("BaseActivity", "onOptionsItemSelected");
        switch (item.getItemId()) {
	case android.R.id.home:
	    // if (this instanceof HomeDrawerActivity) {
	    // 	return false;
	    // }
	    onBackPressed();
	    return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected int getActionBarSize() {
        TypedValue typedValue = new TypedValue();
        int[] textSizeAttr = new int[]{R.attr.actionBarSize};
        int indexOfAttrTextSize = 0;
        TypedArray a = obtainStyledAttributes(typedValue.data, textSizeAttr);
        int actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
        a.recycle();
        return actionBarSize;
    }

    protected int getScreenHeight() {
        return findViewById(android.R.id.content).getHeight();
    }

    public void setActionBarTitle(String title) {
	toolbar.setTitle(title);
    }

    public void hideToolbar(boolean hide) {
	if (hide) {
	    toolbar.setVisibility(View.GONE);
	} else {
	    toolbar.setVisibility(View.VISIBLE);
	}
    }
}

package com.eexit.ui;

import android.widget.BaseAdapter;

public abstract class BaseListAdapter<D> extends BaseAdapter {
    public abstract void swapData(D data);
}

package com.eexit.ui;

import android.content.Context;
import java.sql.Timestamp;
import java.util.List;

public abstract class LoadDataAdapter<D, T> extends BaseListAdapter<D> {

    public abstract List<T> getObjectsFromData(D data);
    private Context mContext;
    public List<T> objects;
    public D mData;

    private EmptyListener mListener;

    private int popularTabPosition = 0;

    public interface EmptyListener {
	public void onEmpty(boolean empty);
    }

    public void setEmptyListener(EmptyListener listener) {
	mListener = listener;
    }

    public LoadDataAdapter(Context context) {
	mContext = context;
    }

    @Override
    public int getCount() {
	if (objects != null) {
	    return objects.size();
	}
	return 0;
    }

    @Override
    public T getItem(int position) {
	if (position >= objects.size())
	    return null;
	return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
	return position;
    }

    @Override
    public void swapData(D data) {
	mData = data;
	swapObjects(getObjectsFromData(data));
    }

    public void swapObjects(List<T> objects) {
	this.objects = objects;
	if ((objects == null || objects.size() == 0)) {
	    if (mListener != null)
		mListener.onEmpty(true);
	} else {
	    if (mListener != null)
		mListener.onEmpty(false);
	}
	notifyDataSetChanged();
    }

    public String convertTimeToString(Timestamp t) {
	return ("" + t.getTime());
    }
}

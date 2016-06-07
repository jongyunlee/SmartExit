package com.eexit.util;

import android.support.v4.widget.SwipeRefreshLayout;

public class SwipeUtils {
    public static void init(SwipeRefreshLayout layout) {
	layout.setColorSchemeResources(android.R.color.holo_green_light,
				       android.R.color.holo_orange_light,
				       android.R.color.holo_red_light);
    }
}

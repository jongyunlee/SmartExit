package com.eexit.util.imageloader;

import android.graphics.Bitmap;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.eexit.R;

public class CommonDisplayOptions {

    public static DisplayImageOptions getDefaultRoundOption() {
	DisplayImageOptions options = new DisplayImageOptions.Builder()
	    // .showStubImage(R.drawable.ic_stub)
	    // .showImageForEmptyUri(R.drawable.ic_empty)
	    // .showImageOnFail(R.drawable.ic_error)
	    .cacheInMemory(true)
	    .cacheOnDisc(true)
	    // .displayer(new RoundedBitmapDisplayer(0))
	    .build();
	return options;
    }

    public static DisplayImageOptions getResultImageOption() {
        DisplayImageOptions options
	    = new DisplayImageOptions.Builder()
	    .resetViewBeforeLoading(true)
	    .cacheInMemory(false)
	    .cacheOnDisc(false)
	    .bitmapConfig(Bitmap.Config.RGB_565)
	    .imageScaleType(ImageScaleType.EXACTLY)
	    .resetViewBeforeLoading(true)
	    .build();
	return options;
    }

    public static DisplayImageOptions getProfessorProfileOption() {
	DisplayImageOptions options = new DisplayImageOptions.Builder()
	    // .showStubImage(R.drawable.ic_user_default)
	    // .showImageForEmptyUri(R.drawable.ic_user_default)
	    // .showImageOnFail(R.drawable.ic_user_default)
	    .cacheInMemory(true)
	    .cacheOnDisc(true)
	    // .displayer(new RoundedBitmapDisplayer(0))
	    .build();
	return options;
    }

}

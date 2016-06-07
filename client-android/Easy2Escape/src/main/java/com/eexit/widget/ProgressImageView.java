package com.eexit.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.eexit.R;
import com.eexit.ui.BaseActivity;
import com.eexit.util.imageloader.AnimationFirstDisplayListener;
import com.eexit.util.imageloader.CommonDisplayOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;
import com.makeramen.roundedimageview.RoundedImageView;

public class ProgressImageView extends RelativeLayout {

    @InjectView(R.id.content_image) ImageView mImageView;
    @InjectView(R.id.content_progress) MatrialProgressWheel mProgressBar;

    Context context;
    LayoutInflater mInflater;
    private boolean round = false;
    private float cornerRadius = 0;
    private int source = 0;
    // private ImageLoadingListener animateFirstListener = new AnimationFirstDisplayListener();

    public static DisplayImageOptions options = new DisplayImageOptions.Builder()
    	// .showImageOnLoading(R.drawable.ic_auil_stub)
    	// .showImageForEmptyUri(R.drawable.ic_auil_empty)
    	// .showImageOnFail(R.drawable.ic_auil_error)
    	.cacheInMemory(true)
    	.cacheOnDisk(true)
    	.considerExifParams(true)
    	.build();
    private float scale = 0.3f;

    public ProgressImageView(Context context) {
	super(context);
	this.context = context;
	initView();
    }

    public ProgressImageView(Context context, AttributeSet attrs) {
	super(context, attrs);
	this.context = context;
	parseAttrs(context, attrs);
	initView();
    }

    public void parseAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressImageView);
        if (typedArray == null) return;
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int attr = typedArray.getIndex(i);
            if (attr == R.styleable.ProgressImageView_round) {
                round = typedArray.getBoolean(attr, false);
            } else if (attr == R.styleable.ProgressImageView_cornerRadius) {
                cornerRadius = typedArray.getDimensionPixelSize(attr, 0);
            } else if (attr == R.styleable.ProgressImageView_source) {
		source = typedArray.getResourceId(attr, 0);
	    }
        }
        typedArray.recycle();
    }

    public void initView() {
	mInflater = LayoutInflater.from(context);
	if (round) {
	    mInflater.inflate(R.layout.view_progress_image_round, this, true);
	} else {
	    mInflater.inflate(R.layout.view_progress_image, this, true);
	}
	ButterKnife.inject(this);
	if (source != 0) {
	    mImageView.setImageResource(source);
	}
	if (cornerRadius != 0) {
	    ((RoundedImageView)mImageView).setOval(false);
	    ((RoundedImageView)mImageView).setCornerRadius(cornerRadius);
	}
	// options = new DisplayImageOptions.Builder()
	//     .showImageOnLoading(R.drawable.ic_auil_stub)
	//     .showImageForEmptyUri(R.drawable.ic_auil_empty)
	//     .showImageOnFail(R.drawable.ic_auil_error)
	//     .cacheInMemory(true)
	//     .cacheOnDisk(true)
	//     .considerExifParams(true)
	// .decodingOptions(resizeOptions)
	// .build();
    }

    public void displayImage(String url, float scale) {
	this.scale = scale;
	displayImage(url);
    }

    public void displayImage(String url, int width, int height) {
	mProgressBar.setVisibility(View.GONE);
	displayImage(url);
    }

    public void displayImage(String url) {
	mProgressBar.setVisibility(View.GONE);
	((BaseActivity)context).imageLoader
	    .displayImage(url, mImageView, options,
			  new SimpleImageLoadingListener() {
			      @Override
			      public void onLoadingStarted(String imageUri, View view) {
			  	  // mProgressBar.setProgress(0);
			  	  mProgressBar.setVisibility(View.VISIBLE);
			      }

			      @Override
			      public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
			  	  mProgressBar.setVisibility(View.GONE);
			      }

			      @Override
			      public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			  	  mProgressBar.setVisibility(View.GONE);
			      }
			  },
			  new ImageLoadingProgressListener() {
			      @Override
			      public void onProgressUpdate(String imageUri, View view, int current, int total) {
			  	  // mProgressBar.setProgress(Math.round(100.0f * current / total));
			      }
			  });
    }

    public void displayImage(BaseActivity activity, String url) {
	mProgressBar.setVisibility(View.GONE);
	activity.imageLoader
	    .displayImage(url, mImageView, options,
			  new SimpleImageLoadingListener() {
			      @Override
			      public void onLoadingStarted(String imageUri, View view) {
			  	  // mProgressBar.setProgress(0);
			  	  mProgressBar.setVisibility(View.VISIBLE);
			      }

			      @Override
			      public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
			  	  mProgressBar.setVisibility(View.GONE);
			      }

			      @Override
			      public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			  	  mProgressBar.setVisibility(View.GONE);
			      }
			  },
			  new ImageLoadingProgressListener() {
			      @Override
			      public void onProgressUpdate(String imageUri, View view, int current, int total) {
			  	  // mProgressBar.setProgress(Math.round(100.0f * current / total));
			      }
			  });
    }

    public void setScaleType(ImageView.ScaleType scaleType) {
	mImageView.setScaleType(scaleType);
    }

    public ImageView getImageView() {
	return mImageView;
    }

    public void setImageResource(int resource) {
	mProgressBar.setVisibility(View.GONE);
	mImageView.setImageResource(resource);
    }
}

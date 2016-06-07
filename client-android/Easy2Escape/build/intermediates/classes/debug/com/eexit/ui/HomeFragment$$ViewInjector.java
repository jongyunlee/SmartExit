// Generated code from Butter Knife. Do not modify!
package com.eexit.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class HomeFragment$$ViewInjector<T extends com.eexit.ui.HomeFragment> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689603, "field 'mRecyclerView'");
    target.mRecyclerView = finder.castView(view, 2131689603, "field 'mRecyclerView'");
    view = finder.findRequiredView(source, 2131689604, "field 'mRefreshButton'");
    target.mRefreshButton = finder.castView(view, 2131689604, "field 'mRefreshButton'");
    view = finder.findRequiredView(source, 2131689605, "field 'mMessageButton'");
    target.mMessageButton = finder.castView(view, 2131689605, "field 'mMessageButton'");
    view = finder.findRequiredView(source, 2131689606, "field 'mFireButton'");
    target.mFireButton = finder.castView(view, 2131689606, "field 'mFireButton'");
    view = finder.findRequiredView(source, 2131689607, "field 'mFloodingButton'");
    target.mFloodingButton = finder.castView(view, 2131689607, "field 'mFloodingButton'");
    view = finder.findRequiredView(source, 2131689608, "field 'mQuakeButton'");
    target.mQuakeButton = finder.castView(view, 2131689608, "field 'mQuakeButton'");
  }

  @Override public void reset(T target) {
    target.mRecyclerView = null;
    target.mRefreshButton = null;
    target.mMessageButton = null;
    target.mFireButton = null;
    target.mFloodingButton = null;
    target.mQuakeButton = null;
  }
}

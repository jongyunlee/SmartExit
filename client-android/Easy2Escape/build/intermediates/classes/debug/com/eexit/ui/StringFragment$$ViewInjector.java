// Generated code from Butter Knife. Do not modify!
package com.eexit.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class StringFragment$$ViewInjector<T extends com.eexit.ui.StringFragment> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689609, "field 'mTitleView'");
    target.mTitleView = finder.castView(view, 2131689609, "field 'mTitleView'");
  }

  @Override public void reset(T target) {
    target.mTitleView = null;
  }
}

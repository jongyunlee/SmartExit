// Generated code from Butter Knife. Do not modify!
package com.eexit.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class LoginEmailFragment$$ViewInjector<T extends com.eexit.ui.LoginEmailFragment> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689610, "field 'mEmailEdit'");
    target.mEmailEdit = finder.castView(view, 2131689610, "field 'mEmailEdit'");
    view = finder.findRequiredView(source, 2131689611, "field 'mPasswordEdit'");
    target.mPasswordEdit = finder.castView(view, 2131689611, "field 'mPasswordEdit'");
    view = finder.findRequiredView(source, 2131689612, "field 'mNextButton'");
    target.mNextButton = finder.castView(view, 2131689612, "field 'mNextButton'");
  }

  @Override public void reset(T target) {
    target.mEmailEdit = null;
    target.mPasswordEdit = null;
    target.mNextButton = null;
  }
}

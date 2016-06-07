// Generated code from Butter Knife. Do not modify!
package com.eexit.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class LoginMainFragment$$ViewInjector<T extends com.eexit.ui.LoginMainFragment> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689618, "field 'mEmailLoginButton'");
    target.mEmailLoginButton = finder.castView(view, 2131689618, "field 'mEmailLoginButton'");
    view = finder.findRequiredView(source, 2131689614, "field 'mFbButton'");
    target.mFbButton = finder.castView(view, 2131689614, "field 'mFbButton'");
    view = finder.findRequiredView(source, 2131689615, "field 'mGplusButton'");
    target.mGplusButton = finder.castView(view, 2131689615, "field 'mGplusButton'");
    view = finder.findRequiredView(source, 2131689616, "field 'mKakaoButton'");
    target.mKakaoButton = finder.castView(view, 2131689616, "field 'mKakaoButton'");
    view = finder.findRequiredView(source, 2131689617, "field 'mEmailButton'");
    target.mEmailButton = finder.castView(view, 2131689617, "field 'mEmailButton'");
  }

  @Override public void reset(T target) {
    target.mEmailLoginButton = null;
    target.mFbButton = null;
    target.mGplusButton = null;
    target.mKakaoButton = null;
    target.mEmailButton = null;
  }
}

// Generated code from Butter Knife. Do not modify!
package com.eexit.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class SignupEmailFragment$$ViewInjector<T extends com.eexit.ui.SignupEmailFragment> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131689643, "field 'mNameEdit'");
    target.mNameEdit = finder.castView(view, 2131689643, "field 'mNameEdit'");
    view = finder.findRequiredView(source, 2131689610, "field 'mEmailEdit'");
    target.mEmailEdit = finder.castView(view, 2131689610, "field 'mEmailEdit'");
    view = finder.findRequiredView(source, 2131689611, "field 'mPasswordEdit'");
    target.mPasswordEdit = finder.castView(view, 2131689611, "field 'mPasswordEdit'");
    view = finder.findRequiredView(source, 2131689644, "field 'mPasswordOkEdit'");
    target.mPasswordOkEdit = finder.castView(view, 2131689644, "field 'mPasswordOkEdit'");
    view = finder.findRequiredView(source, 2131689612, "field 'mNextButton'");
    target.mNextButton = finder.castView(view, 2131689612, "field 'mNextButton'");
    view = finder.findRequiredView(source, 2131689646, "field 'mAgree1View'");
    target.mAgree1View = finder.castView(view, 2131689646, "field 'mAgree1View'");
    view = finder.findRequiredView(source, 2131689647, "field 'mAgree2View'");
    target.mAgree2View = finder.castView(view, 2131689647, "field 'mAgree2View'");
    view = finder.findRequiredView(source, 2131689645, "field 'mRecommenderEdit'");
    target.mRecommenderEdit = finder.castView(view, 2131689645, "field 'mRecommenderEdit'");
  }

  @Override public void reset(T target) {
    target.mNameEdit = null;
    target.mEmailEdit = null;
    target.mPasswordEdit = null;
    target.mPasswordOkEdit = null;
    target.mNextButton = null;
    target.mAgree1View = null;
    target.mAgree2View = null;
    target.mRecommenderEdit = null;
  }
}

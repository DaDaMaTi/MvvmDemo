package com.csmar.mvvmdemo;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;

import androidx.annotation.NonNull;

import com.csmar.lib.base.BaseActivity;
import com.csmar.lib.base.util.MyClickableSpan;
import com.csmar.mvvmdemo.databinding.ActivityPlatformLoginBinding;
import com.csmar.mvvmdemo.modeview.PlatformViewMode;

/**
 * 平台登录, 采用 mmvm 模式开发，数据绑定
 */
public class PlatformLoginActivity extends BaseActivity<ActivityPlatformLoginBinding> {

    private PlatformViewMode viewMode;

    private String privacy;
    private String term;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_platform_login;
    }

    @Override
    protected void initViewModel() {
        viewMode = getActivityViewModel(BR.mode, PlatformViewMode.class);
        mBinding.setVariable(BR.base, this); // 设置标题
    }

    @Override
    protected void initData() {
       mTitle.set("平台登录");
       viewMode.name.set(mBinding.loginView.accountEdit.getText().toString());
       SpannableStringBuilder builder = new SpannableStringBuilder();
       MyClickableSpan clickableSpan1 = new MyClickableSpan(this, Color.parseColor("#0A90EE"), 0);
       MyClickableSpan clickableSpan2 = new MyClickableSpan(this, Color.parseColor("#0A90EE"), 1);
       privacy = getString(R.string.permissions_app);
       term = getString(R.string.aggrement_app);
       String mAll = String.format(getString(R.string.privacy_format), privacy, term);
       builder.append(mAll);
       builder.setSpan(clickableSpan1, mAll.indexOf(privacy), mAll.indexOf(privacy)
               + privacy.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//设置clickableSpan
       builder.setSpan(clickableSpan2, mAll.indexOf(term), mAll.indexOf(term)
               + term.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//设置clickableSpan
       mBinding.privacyTxt.setMovementMethod(LinkMovementMethod.getInstance());
       viewMode.notice.set(builder);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mBinding.privacyTxt.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
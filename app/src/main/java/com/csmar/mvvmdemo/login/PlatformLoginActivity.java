package com.csmar.mvvmdemo.login;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;

import com.csmar.lib.base.BaseChangeActivity;
import com.csmar.lib.base.ToolBarViewMode;
import com.csmar.lib.base.util.Constants;
import com.csmar.lib.base.util.MyClickableSpan;
import com.csmar.lib.base.util.SpUtils;
import com.csmar.mvvmdemo.BR;
import com.csmar.mvvmdemo.R;
import com.csmar.mvvmdemo.bean.PlatformResponse;
import com.csmar.mvvmdemo.databinding.ActivityPlatformLoginBinding;
import com.csmar.mvvmdemo.login.modeview.PlatformViewMode;

/**
 * 平台登录, 采用 mmvm 模式开发，数据绑定
 */
public class PlatformLoginActivity extends BaseChangeActivity<ActivityPlatformLoginBinding> {
    private String TAG = this.getClass().getName();
    private PlatformViewMode mViewMode;

    private String privacy;
    private String term;

    private SpUtils mSp;
    private String platformUserId;

    @Override
    protected int getLayout() {
        return R.layout.activity_platform_login;
    }

    @Override
    protected void initViewModel() {
        mViewMode = getActivityViewModel(BR.mode, PlatformViewMode.class);
        mToolBarViewMode = getActivityViewModel(BR.toolbar, ToolBarViewMode.class);
    }

    @Override
    protected void initData() {
        // livedata 监听接口回调情况
        mViewMode.getLiveData().observe(this, (Observer<PlatformResponse.PlatformUser>) platformUser -> {
            mSp.writeString(Constants.PLATFORM_USER_ID, platformUser.id);
            mSp.writeString(Constants.PLATFORM_USER_NAME, platformUser.userName);
            Intent intent = new Intent(this, InnerLoginActivity.class);
            startActivity(intent);
            finish();
        });
        mToolBarViewMode.mTitle.set("平台登录");
        if (mSp == null) {
           mSp = SpUtils.getInstance(Constants.PLATFORM_USER);
        }
        platformUserId = mSp.readString(Constants.PLATFORM_USER_ID, "");
       if (!TextUtils.isEmpty(platformUserId)) {
           Intent intent = new Intent(this, InnerLoginActivity.class);
           startActivity(intent);
           finish();
       }
        mViewMode.name.set(mBinding.loginView.accountEdit.getText().toString());
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
        mViewMode.notice.set(builder);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mBinding.privacyTxt.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
package com.csmar.mvvmdemo.login;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;

import com.csmar.lib.base.BaseActivity;
import com.csmar.lib.base.util.Constants;
import com.csmar.lib.base.util.MyClickableSpan;
import com.csmar.lib.base.util.SharedPreferencesUtil;
import com.csmar.mvvmdemo.BR;
import com.csmar.mvvmdemo.R;
import com.csmar.mvvmdemo.bean.PlatformResponse;
import com.csmar.mvvmdemo.databinding.ActivityPlatformLoginBinding;
import com.csmar.mvvmdemo.login.modeview.PlatformViewMode;

/**
 * 平台登录, 采用 mmvm 模式开发，数据绑定
 */
public class PlatformLoginActivity extends BaseActivity<ActivityPlatformLoginBinding> {
    private String TAG = this.getClass().getName();
    private PlatformViewMode viewMode;

    private String privacy;
    private String term;

    private SharedPreferencesUtil mSp;
    private String platformUserId;

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
        // livedata 监听接口回调情况
        viewMode.getLiveData().observe(this, (Observer<PlatformResponse.PlatformUser>) platformUser -> {
            mSp.writeString(Constants.PLATFORM_USER_ID, platformUser.id);
            Intent intent = new Intent(this, InnerLoginActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void initData() {
       mTitle.set("平台登录");
       if (mSp == null) {
           mSp = new SharedPreferencesUtil(this, Constants.PLATFORM_USER);
       }
       platformUserId = mSp.readString(Constants.PLATFORM_USER_ID, "");
       if (!TextUtils.isEmpty(platformUserId)) {
           Intent intent = new Intent(this, InnerLoginActivity.class);
           startActivity(intent);
       }
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
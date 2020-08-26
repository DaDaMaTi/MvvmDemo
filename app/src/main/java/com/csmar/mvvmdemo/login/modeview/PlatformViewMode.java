package com.csmar.mvvmdemo.login.modeview;

import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.csmar.lib.base.BaseViewModel;
import com.csmar.lib.base.util.GsonUtils;
import com.csmar.lib.base.util.LogUtil;
import com.csmar.lib.base.util.SimpleTextWatcher;
import com.csmar.lib.base.util.ToastUtil;
import com.csmar.lib.base.util.Utils;
import com.csmar.lib.net.ApiException;
import com.csmar.lib.net.NetworkManager;
import com.csmar.lib.net.ResponseException;
import com.csmar.mvvmdemo.R;
import com.csmar.mvvmdemo.api.UserApi;
import com.csmar.mvvmdemo.app.BaseApp;
import com.csmar.mvvmdemo.bean.PlatformResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 平台登录 mode
 * mmvm 中 viewmode 不 与 view 产生联系
 * 可使用 livedata 进行数据监听，登录成功进行跳转
 */
public class PlatformViewMode extends BaseViewModel<PlatformResponse.PlatformUser> {

    public final ObservableField<String> name = new ObservableField<>();

    public final ObservableField<String> password = new ObservableField<>();

    public final ObservableField<SpannableStringBuilder> notice = new ObservableField<>();

    public final ObservableBoolean loadingVisible = new ObservableBoolean();

    /**
     * 账号文本变化监听器
     */
    public TextWatcher textWatcher = new SimpleTextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            LogUtil.e("wsd---textWatcher--", s.toString());
            name.set(s.toString());
        }
    };

    /**
     * 密码文本变化监听器
     */
    public TextWatcher textWatcher2 = new SimpleTextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            LogUtil.e("wsd---textWatcher2--", s.toString());
            password.set(s.toString());
        }
    };

    public void setText(View view, boolean hasFocus) {
        String msg = ((EditText)view).getText().toString();
        switch (view.getId()) {
            case R.id.accountEdit :
                LogUtil.e("wsd---setTextacc--", msg + "--" + hasFocus);
                break;
            case R.id.pwdEdit :
                LogUtil.e("wsd---setTextpwd--", msg + "--" + hasFocus);
                break;
        }
    }


    public void login(String name, String pwd) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {
            ToastUtil.showToast("账号或密码为空！");
            return;
        }
        String path = "yyyf-server-app/";
        loadingVisible.set(true);
        getCompositeDisposable().add(NetworkManager.getInstance().create(UserApi.class)
        .platformLogin(path, name, pwd)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(platformResponse -> {
            loadingVisible.set(false);
            if (platformResponse != null) {
                if (platformResponse.isSuccess()) {
                    PlatformResponse.PlatformUser platformUser = GsonUtils.parseJSON(platformResponse.data, PlatformResponse.PlatformUser.class);
                    getLiveData().setValue(platformUser);
                } else {
                    ToastUtil.showToast(platformResponse.explain);
                }
            }
            LogUtil.e("wsd---", platformResponse.toString());
        }, new ResponseException(Utils.getApp()) {
            @Override
            public void onError(ApiException e) {
                loadingVisible.set(false);
                ToastUtil.showToast(e.getErrMessage());
                LogUtil.e("wsd---", e.getErrMessage());
            }
        }));

    }
}

package com.csmar.lib.base.viewmode;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.csmar.lib.base.util.LogUtil;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseViewModel<T> extends ViewModel {

    private CompositeDisposable mDisposable;
    /**
     * 这边只做简单的登录是否成功操作，泛型指定布尔值
     */
    private MutableLiveData<T> mLiveData;

    public MutableLiveData getLiveData() {
        if (mLiveData == null) {
            mLiveData = new MutableLiveData();
        }
        return mLiveData;
    }


    protected CompositeDisposable getCompositeDisposable() {
        if (mDisposable == null) {
            mDisposable = new CompositeDisposable();
        }
        return mDisposable;
    }

    @Override
    protected void onCleared() {
        if (mDisposable != null) {
            mDisposable.clear();
            mDisposable = null;
        }
        super.onCleared();
        LogUtil.e("wsd---", "---onCleared()");
    }
}

package com.csmar.lib.base;

import androidx.lifecycle.ViewModel;

import com.csmar.lib.base.util.LogUtil;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseViewModel extends ViewModel {

    private CompositeDisposable mDisposable;

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

package com.csmar.lib.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

class BaseKtViewMode : ViewModel() {

    private var mDisposable: CompositeDisposable? = null

    protected fun getCompositeDisposable(): CompositeDisposable? {
        if (mDisposable == null) {
            mDisposable = CompositeDisposable()
        }
        return mDisposable
    }

    override fun onCleared() {
        mDisposable!!.clear()
        mDisposable = null
        super.onCleared()
    }
}
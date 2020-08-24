package com.csmar.lib.base;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.csmar.lib.base.util.LogUtil;
import com.csmar.lib.base.util.ToastUtil;

public class ApplicationObserver implements LifecycleObserver {
    private String TAG = this.getClass().getName();

    public ApplicationObserver() {

    }

    /**
     * ON_CREATE 在应用程序的整个生命周期中只会被调用一次
     * */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        ToastUtil.clearToast();
//        ToastUtil.init(BaseApplication.getContext());
        LogUtil.d(TAG, "Lifecycle.Event.ON_CREATE");
    }

    /**
     * 应用程序出现到前台时调用
     * */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        LogUtil.d(TAG, "Lifecycle.Event.ON_START");
    }

    /**
     * 应用程序出现到前台时调用
     * */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        LogUtil.d(TAG, "Lifecycle.Event.ON_RESUME");
    }

    /**
     * 应用程序退出到后台时调用
     * */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        LogUtil.d(TAG, "Lifecycle.Event.ON_PAUSE");
    }

    /**
     * 应用程序退出到后台时调用
     * */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        LogUtil.d(TAG, "Lifecycle.Event.ON_STOP");
    }

    /**
     * 永远不会被调用到，系统不会分发调用ON_DESTROY事件
     * */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        LogUtil.d(TAG, "Lifecycle.Event.ON_DESTROY");
    }
}

package com.csmar.mvvmdemo.app;

import androidx.lifecycle.ProcessLifecycleOwner;

import com.csmar.lib.base.ApplicationObserver;
import com.csmar.lib.base.util.BaseApplication;
import com.csmar.lib.base.util.ToastUtil;
import com.csmar.lib.base.util.Utils;
import com.csmar.lib.net.NetworkManager;
import com.squareup.leakcanary.LeakCanary;

public class BaseApp extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        // 监听应用程序的生命周期
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new ApplicationObserver());

        Utils.init(this);

        // 初始化网络
        NetworkManager.getInstance().init(this);

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }

        LeakCanary.install(this);
    }
}

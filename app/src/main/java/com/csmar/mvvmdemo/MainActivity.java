package com.csmar.mvvmdemo;

import com.csmar.lib.base.BaseActivity;
import com.csmar.mvvmdemo.databinding.ActivityMainBinding;

/**
 * 通过 ViewPage2BindingAdapter 中的 initMainViewPage2 绑定 viewpage2 (xml)
 */
public class MainActivity extends BaseActivity<ActivityMainBinding>{

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewModel() {
//        getActivityViewModel(BR.mode, MainViewMode.class);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
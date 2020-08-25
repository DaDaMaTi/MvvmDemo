package com.csmar.lib.base;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.csmar.lib.base.util.FastClickUtil;

/**
 * 标题栏统一 view mode
 */
public class ToolBarViewMode extends ViewModel {
    /**
     * 标题
     */
    public final ObservableField<String> mTitle = new ObservableField<>();

    /**
     * 这边只做简单返回事件
     */
    private MutableLiveData mLiveData;

    public MutableLiveData getLiveData() {
        if (mLiveData == null) {
            mLiveData = new MutableLiveData();
        }
        return mLiveData;
    }

    /**
     * 导航栏返回
     */
    public void onBack() {
        if (FastClickUtil.isFastClick()) {
            return;
        }
        if (mLiveData != null) {
            mLiveData.setValue("");
        }
    }
}

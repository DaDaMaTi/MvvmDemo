package com.csmar.mvvmdemo.modeview;

import androidx.databinding.ObservableField;

import com.csmar.lib.base.BaseViewModel;

public class NoticesViewMode extends BaseViewModel {
    public final ObservableField<String> mContent = new ObservableField<>();
}

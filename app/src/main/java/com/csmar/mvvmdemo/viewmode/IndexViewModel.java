package com.csmar.mvvmdemo.viewmode;

import android.util.SparseArray;

import androidx.databinding.ObservableField;

import com.csmar.lib.base.viewmode.BaseViewModel;

public class IndexViewModel extends BaseViewModel {
    public final ObservableField<SparseArray<String>> mData = new ObservableField<>();
    private SparseArray<String> sparseArray = new SparseArray(4);

}

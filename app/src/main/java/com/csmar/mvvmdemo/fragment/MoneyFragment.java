package com.csmar.mvvmdemo.fragment;


import android.content.Context;

import androidx.annotation.NonNull;

import com.csmar.lib.base.BaseFragment;
import com.csmar.lib.base.ToolBarViewMode;
import com.csmar.mvvmdemo.BR;
import com.csmar.mvvmdemo.R;
import com.csmar.mvvmdemo.databinding.FragmentIndexBinding;

public class MoneyFragment extends BaseFragment<FragmentIndexBinding> {

    @Override
    public void onAttach(@NonNull Context context) {
        TAG = TAG + "MoneyFragment--";
        super.onAttach(context);
    }

    @Override
    protected void initViewModel() {
        mToolBarViewMode = getFragmentViewModel(BR.toolbar, ToolBarViewMode.class);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_index;
    }

    @Override
    protected void initData() {
        mToolBarViewMode.isShowBack.set(false);
        mToolBarViewMode.mTitle.set("理财");
    }
}

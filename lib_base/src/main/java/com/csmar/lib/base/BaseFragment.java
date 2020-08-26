package com.csmar.lib.base;

import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.csmar.lib.base.util.LogUtil;
import com.csmar.lib.base.util.ScreenUtils;

/**
 * 抽取 fragment 基类
 */
public abstract class BaseFragment<T extends ViewDataBinding> extends Fragment {
    protected String TAG = "BaseFragment_";
    protected T mBinding;
    protected AppCompatActivity mActivity;
    protected ToolBarViewMode mToolBarViewMode;
    private ViewModelProvider mFragmentProvider;
    private ViewModelProvider mActivityProvider;
    protected SparseArray<ViewModel> mSparseArray = new SparseArray<>(2);

    /**
     * 初始化mode
     */
    protected abstract void initViewModel();

    /**
     * 获取布局id
     *
     * @return 布局id
     */
    protected abstract int getLayout();

    /**
     * 初始化数据
     *
     * @return
     */
    protected abstract void initData();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (AppCompatActivity)context;
        LogUtil.e(TAG, "onAttach()");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        if (mBinding == null) {
            mBinding = DataBindingUtil.inflate(inflater, getLayout(), parent, false);
        }
        mBinding.setLifecycleOwner(this);
        initViewModel();
        for (int i = 0, length = mSparseArray.size(); i < length; i++) {
            mBinding.setVariable(mSparseArray.keyAt(i), mSparseArray.valueAt(i));
        }
        LogUtil.e(TAG, "onAttach()");
        return mBinding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e(TAG, "onCreate()");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtil.e(TAG, "onViewCreated()");
        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e(TAG, "onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.e(TAG, "onDetach()");
    }

    protected <T extends ViewModel> T getFragmentViewModel(@NonNull Class<T> modelClass) {
        if (mFragmentProvider == null) {
            mFragmentProvider = new ViewModelProvider(this);
        }
        return mFragmentProvider.get(modelClass);
    }

    protected <T extends ViewModel> T getActivityViewModel(@NonNull Class<T> modelClass) {
        if (mActivityProvider == null) {
            mActivityProvider = new ViewModelProvider(mActivity);
        }
        return mActivityProvider.get(modelClass);
    }
}

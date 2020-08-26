package com.csmar.lib.base;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.csmar.lib.base.util.AdaptScreenUtils;
import com.csmar.lib.base.util.LogUtil;
import com.csmar.lib.base.util.ScreenUtils;

/**
 * activity 数据绑定基类
 *
 * @param <T> 数据绑定类，自动生成的类
 * @author wsd
 * @since 2020-08-19
 */
public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity {

    protected T mBinding;
    protected ToolBarViewMode mToolBarViewMode;
    private ViewModelProvider mActivityProvider;
    protected SparseArray<ViewModel> mSparseArray = new SparseArray<>(2);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        BarUtils.setStatusBarColor(this, Color.TRANSPARENT);
//        BarUtils.setStatusBarLightMode(this, true);
//        BarUtils.setStatusBarVisibility(getWindow(), true);
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, getLayout());
        mBinding.setLifecycleOwner(this);
        initViewModel();
        for (int i = 0, length = mSparseArray.size(); i < length; i++) {
            mBinding.setVariable(mSparseArray.keyAt(i), mSparseArray.valueAt(i));
        }
        if (!ScreenUtils.isTablet()) {
            ScreenUtils.setPortrait(this);
        }
        initData();
        if (mToolBarViewMode != null) {
            mToolBarViewMode.getLiveData().observe(this, new Observer() {
                @Override
                public void onChanged(Object o) {
                    finish();
                }
            });
        }
    }

    /**
     * 获取布局id
     *
     * @return 布局id
     */
    protected abstract int getLayout();

    /**
     * 初始化mode
     */
    protected abstract void initViewModel();

    /**
     * 初始化数据
     *
     * @return
     */
    protected abstract void initData();

    protected <T extends ViewModel> T getActivityViewModel(int variableId, @NonNull Class<T> modelClass) {
        if (mActivityProvider == null) {
            mActivityProvider = new ViewModelProvider(this);
        }
        mSparseArray.put(variableId, mActivityProvider.get(modelClass));
        return mActivityProvider.get(modelClass);
    }

    @Override
    public Resources getResources() {
        if (ScreenUtils.isPortrait()) {
            return AdaptScreenUtils.adaptWidth(super.getResources(), 360);
        } else {
            return AdaptScreenUtils.adaptHeight(super.getResources(), 640);
        }
    }

    protected void toggleSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

   /* public final ObservableField<String> mTitle = new ObservableField<>();
    public void onBack(View view) {
        finish();
    }*/

    protected Activity getMyActivity() {
        return this;
    }

    //------------------------------隐藏键盘----------------------------
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (this == null) {
                LogUtil.e("dispatchTouchEvent", "context == null");
                return false;
            }
            // 自动隐藏键盘
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) this.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            // 获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            float x = event.getX();
            float y = event.getY();
            return !(x > left && x < right && y > top && y < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }
}

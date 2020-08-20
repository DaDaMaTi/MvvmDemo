package com.csmar.lib.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.csmar.lib.base.util.AdaptScreenUtils;
import com.csmar.lib.base.util.BarUtils;
import com.csmar.lib.base.util.LogUtil;
import com.csmar.lib.base.util.ScreenUtils;
import com.kunminx.architecture.BaseApplication;

import java.lang.reflect.Field;

/**
 * 针对布局横竖屏有变化可以集成这个类，本类已在屏幕变化生命周期进行了适配，后续遇到问题再具体完善
 *
 * @param <T> 数据绑定类，自动生成的类
 * @author wsd
 * @since 2020-08-19
 */
public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity {

    protected T mBinding;
    private ViewModelProvider mActivityProvider;
    private ViewModelProvider.Factory mFactory;
    protected SparseArray<ViewModel> mSparseArray = new SparseArray<>(2);

    public ObservableField<String> mTitle = new ObservableField<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        BarUtils.setStatusBarColor(this, Color.TRANSPARENT);
//        BarUtils.setStatusBarLightMode(this, true);
        BarUtils.setStatusBarVisibility(getWindow(), true);
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, getLayout());
        initViewModel();
        for (int i = 0, length = mSparseArray.size(); i < length; i++) {
            mBinding.setVariable(mSparseArray.keyAt(i), mSparseArray.valueAt(i));
        }
        initData();
        if (!ScreenUtils.isTablet()) {
            ScreenUtils.setPortrait(this);
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

    protected ViewModelProvider getAppViewModelProvider() {
        return new ViewModelProvider((BaseApplication) this.getApplicationContext(),
                getAppFactory(this));
    }

    private ViewModelProvider.Factory getAppFactory(Activity activity) {
        Application application = checkApplication(activity);
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application);
        }
        return mFactory;
    }

    private Application checkApplication(Activity activity) {
        Application application = activity.getApplication();
        if (application == null) {
            throw new IllegalStateException("Your activity/fragment is not yet attached to "
                    + "Application. You can't request ViewModel before onCreate call.");
        }
        return application;
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

    public void onBack(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        fixInputMethodManagerLeak(this);
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        int id = 0;
        if (this.getCurrentFocus() != null) {
            id = this.getCurrentFocus().getId();
        }
        EditText editText = findViewById(id);
        String s ="";
        if (editText != null) {
            editText.requestFocus();
            s = editText.getText().toString();
            LogUtil.e("wsd---edit--", s);
        }
        mBinding = DataBindingUtil.setContentView(this, getLayout());
        mBinding.getRoot().requestApplyInsets(); // 解决横竖屏 fitsSystemWindows 属性失效问题
        EditText editText2 = findViewById(id);
        if (editText2 != null) {
            editText2.requestFocus();
            editText2.setText(s);
            editText2.setSelection(s.length());
        }
        for (int i = 0, length = mSparseArray.size(); i < length; i++) {
            mBinding.setVariable(mSparseArray.keyAt(i), mSparseArray.valueAt(i));
        }
        mSparseArray.clear();
        initViewModel();
        super.onConfigurationChanged(newConfig);
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
            if (x > left && x < right && y > top && y < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 修复软键盘及InputMethodManager导致的内存泄露
     * @param destContext
     */
    private void fixInputMethodManagerLeak(Context destContext) {
        if (destContext == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) destContext.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        String [] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field f;
        Object obj_get;
        for (int i = 0;i < arr.length;i ++) {
            String param = arr[i];
            try{
                f = imm.getClass().getDeclaredField(param);
                if (f.isAccessible() == false) {
                    f.setAccessible(true);
                }
                obj_get = f.get(imm);
                if (obj_get != null && obj_get instanceof View) {
                    View v_get = (View) obj_get;
                    if (v_get.getContext() == destContext) {
                        f.set(imm, null);
                    } else {
                        break;
                    }
                }
            }catch(Throwable t){
                t.printStackTrace();
            }
        }
    }
}

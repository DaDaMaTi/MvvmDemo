package com.csmar.lib.base;

import android.content.res.Configuration;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.csmar.lib.base.util.LogUtil;

/**
 * 针对布局横竖屏有变化可以集成这个类，本类已在屏幕变化生命周期进行了适配，后续遇到问题再具体完善
 *
 * @param <T> 数据绑定类，自动生成的类
 * @author wsd
 * @since 2020-08-19
 */
public abstract class BaseChangeActivity<T extends ViewDataBinding> extends BaseActivity<T> {

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
        super.onConfigurationChanged(newConfig);
    }
}

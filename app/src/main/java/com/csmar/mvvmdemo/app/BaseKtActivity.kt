package com.csmar.mvvmdemo.app

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.SparseArray
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.csmar.lib.base.util.LogUtil
import com.csmar.lib.base.util.ScreenUtils
import java.lang.reflect.Field

/**
 * kotlin activity base class
 */
abstract class BaseKtActivity<T : ViewDataBinding> : AppCompatActivity() {

    var mBinding:T? = null
    private var mActivityProvider: ViewModelProvider? = null
    private var mSparseArray = SparseArray<ViewModel>(2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, getLayout())
        initViewModel()
        for(i in 0 until mSparseArray.size()) {
            mBinding!!.setVariable(mSparseArray.keyAt(i), mSparseArray.valueAt(i))
        }
        initData()
        /**
         * 手机只支持竖屏
         */
        if (!ScreenUtils.isTablet()) {
            ScreenUtils.setPortrait(this)
        }
    }

    /**
     * 获取布局id
     *
     * @return 布局id
     */
    protected abstract fun getLayout(): Int

    /**
     * 初始化数据
     */
    protected abstract fun initData()

    /**
     * 初始化mode
     */
    protected abstract fun initViewModel()

    protected open fun <T : ViewModel?> getActivityViewModel(variableId: Int, modelClass: Class<T>): T {
        if (mActivityProvider == null) {
            mActivityProvider = ViewModelProvider(this)
        }
        mSparseArray.put(variableId, mActivityProvider!![modelClass])
        return mActivityProvider!![modelClass]
    }

    open fun onBack(view: View?) {
        finish()
    }

//    /**
//     * 适配横竖屏有变化的
//     */
//    override fun onConfigurationChanged(newConfig: Configuration) {
//        var id = 0
//        if (this.currentFocus != null) {
//            id = this.currentFocus!!.id
//        }
//        val editText = findViewById<EditText>(id)
//        var s = ""
//        if (editText != null) {
//            editText.requestFocus()
//            s = editText.text.toString()
//            LogUtil.e("wsd---edit--", s)
//        }
//        mBinding = DataBindingUtil.setContentView(this, getLayout())
//        mBinding!!.root!!.requestApplyInsets() // 解决横竖屏 fitsSystemWindows 属性失效问题
//        val editText2 = findViewById<EditText>(id)
//        if (editText2 != null) {
//            editText2.requestFocus()
//            editText2.setText(s)
//            editText2.setSelection(s.length)
//        }
//        var i = 0
//        val length = mSparseArray.size()
//        while (i < length) {
//            mBinding!!.setVariable(mSparseArray.keyAt(i), mSparseArray.valueAt(i))
//            i++
//        }
////        initViewModel()
//        super.onConfigurationChanged(newConfig)
//    }

    //------------------------------隐藏键盘----------------------------
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            if (this == null) {
                LogUtil.e("dispatchTouchEvent", "context == null")
                return false
            }
            // 自动隐藏键盘
            val v = currentFocus
            if (isShouldHideInput(v, ev)) {
                var imm = this.applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm!!.hideSoftInputFromWindow(v!!.windowToken, 0)
            }
            return super.dispatchTouchEvent(ev)
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        return if (window.superDispatchTouchEvent(ev)) {
            true
        } else onTouchEvent(ev)
    }

    private fun isShouldHideInput(v: View?, event: MotionEvent): Boolean {
        if (v != null && v is EditText) {
            val leftTop = intArrayOf(0, 0)
            // 获取输入框当前的location位置
            v.getLocationInWindow(leftTop)
            val left = leftTop[0]
            val top = leftTop[1]
            val bottom = top + v.getHeight()
            val right = left + v.getWidth()
            val x = event.x
            val y = event.y
            return !(x > left && x < right && y > top && y < bottom)
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false
    }
}
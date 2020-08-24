package com.csmar.mvvmdemo.login.modeview

import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.TextWatcher
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.csmar.lib.base.BaseViewModel
import com.csmar.lib.base.util.LogUtil
import com.csmar.lib.base.util.SimpleTextWatcher
import com.csmar.lib.base.util.ToastUtil

class LoginViewMode : BaseViewModel<String>(){
    val name = ObservableField<String>()

    val password = ObservableField<String>()

    val notice = ObservableField<SpannableStringBuilder>()

    val loadingVisible = ObservableBoolean()

    /**
     * 账号文本变化监听器
     */
    var textWatcher: TextWatcher = object : SimpleTextWatcher() {
        override fun afterTextChanged(s: Editable) {
            LogUtil.e("wsd---textWatcher--", s.toString())
            name.set(s.toString())
        }
    }

    /**
     * 密码文本变化监听器
     */
    var textWatcher2: TextWatcher = object : SimpleTextWatcher() {
        override fun afterTextChanged(s: Editable) {
            LogUtil.e("wsd---textWatcher2--", s.toString())
            password.set(s.toString())
        }
    }
//
    fun login(userName : String, pwd : String) {
//        if (TextUtils.isEmpty(userName)) {
//            getView().showError(mContext.getResources().getString(R.string.please_input_email))
//            return
//        }
//
//        if (!RegexUtils.isEmail(userName)) {
//            getView().showError(mContext.getResources().getString(R.string.email_form_wrong))
//            return
//        }
//
//        if (TextUtils.isEmpty(pwd)) {
//            getView().showError(mContext.getResources().getString(R.string.please_input_pwd))
//            return
//        }
//
//        if (!RegexUtils.isPassword(pwd)) {
//            ToastUtil.showToast(R.string.error_pwd)
//            return
//        }
//        loadingVisible.setV
    }


}
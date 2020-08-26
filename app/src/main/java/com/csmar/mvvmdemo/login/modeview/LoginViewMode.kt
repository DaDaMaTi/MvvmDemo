package com.csmar.mvvmdemo.login.modeview

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.csmar.lib.base.BaseViewModel
import com.csmar.lib.base.util.*
import com.csmar.lib.net.ApiException
import com.csmar.lib.net.NetworkManager
import com.csmar.lib.net.Response
import com.csmar.lib.net.ResponseException
import com.csmar.mvvmdemo.R
import com.csmar.mvvmdemo.api.UserApi
import com.csmar.mvvmdemo.app.BaseApp
import com.csmar.mvvmdemo.bean.UserBean
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class LoginViewMode : BaseViewModel<String>(){

    val name = ObservableField<String>()

    val password = ObservableField<String>()

    val platformId = ObservableField<String>()

    private val loadingVisible = ObservableBoolean()

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

    /**
     * 内部登录
     */
    fun login(userName : String?, pwd : String?) {
        if (TextUtils.isEmpty(userName)) {
            ToastUtil.showToast(R.string.please_input_email)
            return
        }

        if (!RegexUtils.isEmail(userName!!.trim())) {
            ToastUtil.showToast(R.string.email_form_wrong)
            return
        }

        if (TextUtils.isEmpty(pwd)) {
            ToastUtil.showToast(R.string.please_input_pwd)
            return
        }

        if (!RegexUtils.isPassword(pwd!!.trim())) {
            ToastUtil.showToast(R.string.error_pwd)
            return
        }
        loadingVisible.set(true)

        compositeDisposable.add(NetworkManager.getInstance()
                .create(UserApi::class.java)
                .Innerlogin(userName!!.trim(), pwd!!.trim(), platformId.get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer<Response<UserBean>> { userBeanResponse ->
                    loadingVisible.set(false)
                    if (userBeanResponse != null) {
                        if (userBeanResponse.isSuccess) {
                            val userBean: UserBean = userBeanResponse.data
                            if (userBean != null) {
                                if (userBean.isComplete === 0) {
                                    liveData.value = userBean
                                } else {
                                    ToastUtil.showToast("信息未完善，暂不提供完善页面")
                                }
                            }
                        } else {
                            ToastUtil.showToast(userBeanResponse.msg)
                        }
                    }
                }, object : ResponseException(Utils.getApp()) {
                    override fun onError(e: ApiException) {
                        loadingVisible.set(false)
                        ToastUtil.showToast(e.showMessage)
                    }
                }))
        }
}
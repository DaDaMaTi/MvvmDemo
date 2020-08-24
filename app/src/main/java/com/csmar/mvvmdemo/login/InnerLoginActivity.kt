package com.csmar.mvvmdemo.login

import com.csmar.lib.base.util.Constants
import com.csmar.lib.base.util.SharedPreferencesUtil
import com.csmar.mvvmdemo.R
import com.csmar.mvvmdemo.app.BaseKtActivity
import com.csmar.mvvmdemo.databinding.ActivityInnerLoginBinding
import com.csmar.mvvmdemo.login.modeview.LoginViewMode as LoginViewMode1

/**
 * kotlin 语言写内部登录
 */
class InnerLoginActivity : BaseKtActivity<ActivityInnerLoginBinding>() {
    var viewMode : LoginViewMode1? = null
    var mSp : SharedPreferencesUtil? = null
    var platformUserId : String? = null

    override fun getLayout(): Int {
        return R.layout.activity_inner_login
    }

    override fun initViewModel() {
        viewMode = getActivityViewModel(1, LoginViewMode1::class.java)
        mBinding!!.setVariable(2, this) // 设置标题
        // livedata 监听接口回调情况
//        viewMode!!.liveData.observe(this, (Observer(fun(platformUser: PlatformUser) {
//        }) as Observer<PlatformUser>)!!)
    }

    override fun initData() {
        mTitle.set("系统登录")
        mSp = SharedPreferencesUtil(this, Constants.PLATFORM_USER)
        platformUserId = mSp!!.readString(Constants.PLATFORM_USER_ID, "")

    }

}
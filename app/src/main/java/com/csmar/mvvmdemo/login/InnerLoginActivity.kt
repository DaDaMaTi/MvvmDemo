package com.csmar.mvvmdemo.login

import android.text.TextUtils
import androidx.lifecycle.Observer
import com.csmar.lib.base.ToolBarViewMode
import com.csmar.lib.base.util.Constants
import com.csmar.lib.base.util.SpUtils
import com.csmar.mvvmdemo.MainActivity
import com.csmar.mvvmdemo.R
import com.csmar.mvvmdemo.app.BaseKtActivity
import com.csmar.mvvmdemo.bean.UserBean
import com.csmar.mvvmdemo.databinding.ActivityInnerLoginBinding
import com.csmar.mvvmdemo.login.modeview.LoginViewMode

/**
 * kotlin 语言写内部登录
 */
class InnerLoginActivity : BaseKtActivity<ActivityInnerLoginBinding>() {
    var barMode : ToolBarViewMode? = null
    var viewMode : LoginViewMode? = null
    var mSp : SpUtils? = null
    var platformUserId : String? = null
    var userId : String? = null

    override fun getLayout(): Int {
        return R.layout.activity_inner_login
    }

    override fun initViewModel() {
        viewMode = getActivityViewModel(1, LoginViewMode::class.java)
        barMode = getActivityViewModel(2, ToolBarViewMode::class.java)
    }

    override fun initData() {
        barMode!!.mTitle.set("系统登录")
        mSp = SpUtils.getInstance(Constants.PLATFORM_USER)
        platformUserId = mSp!!.readString(Constants.PLATFORM_USER_ID, "")
        userId = mSp!!.readString(Constants.USER_ID, "")
        if (!TextUtils.isEmpty(userId)) {
            intent.setClass(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        viewMode!!.platformId.set(platformUserId)
        barMode!!.liveData.observe(this, Observer<String?> {
            finish()
        })
        viewMode!!.liveData.observe(this, Observer<UserBean>(){
            // 登录成功回调
            mSp!!.writeString(Constants.USER_ID, it.id)
            mSp!!.writeString(Constants.USER_PHONE, it.phone)
            mSp!!.writeString(Constants.USER_NAME, it.userName)
            intent.setClass(this, MainActivity::class.java)
            startActivity(intent)
        })
    }

}
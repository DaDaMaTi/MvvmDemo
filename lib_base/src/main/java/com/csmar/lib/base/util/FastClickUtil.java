package com.csmar.lib.base.util;

import android.view.View;


/**
 * 连续点击检查
 */
public abstract class FastClickUtil implements View.OnClickListener {
    // 两次点击间隔不能少于1000ms
    private static final long FAST_CLICK_DELAY_TIME = 600;
    private static long lastClickTime;

    /**
     * 是否是快速点击
     *
     * @return true 为快速点击
     */
    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        long intervalTime = currentClickTime - lastClickTime;
        LogUtil.e("FastClickUtil", String.valueOf(intervalTime));
        if ((currentClickTime - lastClickTime) >= FAST_CLICK_DELAY_TIME ) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }
}

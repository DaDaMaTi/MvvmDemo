package com.csmar.lib.net;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络工具管理类
 */
public class NetworkUtil {
    private NetworkUtil() {
    }

    /**
     * 网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkReachable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // 网络连接
            return true;
        }
        return false;
    }

    /**
     * 网络状态
     *
     * @param context
     * @return
     */
    public static int getNetworkType(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            /**
             * 有网
             * ConnectivityManager.TYPE_WIFI WiFi网络
             * ConnectivityManager.TYPE_ETHERNET 有线网络
             * ConnectivityManager.TYPE_MOBILE 3G网络
             */
            return networkInfo.getType();
        } else {
            // 网络断开
            return -1;
        }
    }

    /**
     * 网络状态是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
        } else {
            //如果仅仅是用来判断网络连接
            //则可以使用 cm.getActiveNetworkInfo().isAvailable();
            @SuppressLint("MissingPermission") NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

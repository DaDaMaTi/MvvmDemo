package com.csmar.lib.base.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ToastUtil {

    private volatile static ToastUtil mToast;

    public static void clearToast() {
        mToast = null;
    }
    private static Object iNotificationManagerObj;

    /**
     * 消息通知是否开启, 通知关闭是不弹吐司的
     *
     * @return true 为开启了通知， false 是关了通知，默认情况下手机是开启通知的
     */
    private static boolean isNotificationEnabled() {
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(Utils.getApp());
        boolean areNotificationsEnabled = notificationManagerCompat.areNotificationsEnabled();
        LogUtil.e("wsd---", "是否开启了通知--" + areNotificationsEnabled);
        return areNotificationsEnabled;
    }

    private static Handler handler = new Handler(Looper.getMainLooper());

    private volatile static Toast toast = null;
    private volatile static boolean isOpenNotify; // 是否开启了通知

    /**
     * 根据设置的文本显示
     *
     * @param msg
     */
    public static void showToast(String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }

    /**
     * 根据设置的资源文件显示
     *
     * @param resId
     */
    public static void showToast(final int resId) {
        showToast(resId, Toast.LENGTH_SHORT);
    }

    /**
     * 显示一个文本并且设置时长
     *
     * @param msg
     * @param len
     */
    public static void showToast(final CharSequence msg, final int len) {
        if (TextUtils.isEmpty(msg)) return;
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (toast == null) {
                    initToast(len);
                } else {
                    if (isOpenNotify != isNotificationEnabled()) {
                        toast = null;
                        initToast(len);
                    }
                }
                LogUtil.e("wsd---", Thread.currentThread().getName() + "  " + toast);
                toast.setText(msg);
                toast.setDuration(len);
                toast.show();
            }
        });
    }

    /**
     * 资源文件方式显示文本
     *
     * @param resId
     * @param len
     */
    public static void showToast(final int resId, final int len) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (toast == null) {
                    initToast(len);
                } else {
                    if (isOpenNotify != isNotificationEnabled()) {
                        toast = null;
                        initToast(len);
                    }
                }
                LogUtil.e("wsd---", Thread.currentThread().getName() + "  " + toast);
                toast.setText(resId);
                toast.setDuration(len);
                toast.show();
            }
        });
    }


    private static void initToast(final int len) {
        synchronized (ToastUtil.class) { //加上同步是为了每个toast只要有机会显示出来
            if (toast == null) {
                isOpenNotify = isNotificationEnabled();
                toast = Toast.makeText(Utils.getApp(), null, len);
                if (!isOpenNotify) {
                    showSystemToast(toast);
                }
            }
        }
    }

    /**
     * 显示系统Toast
     */
    private static void showSystemToast(Toast toast) {
        try {
            @SuppressLint("SoonBlockedPrivateApi") Method getServiceMethod = Toast.class.getDeclaredMethod("getService");
            getServiceMethod.setAccessible(true);
            //hook INotificationManager
            if (iNotificationManagerObj == null) {
                iNotificationManagerObj = getServiceMethod.invoke(null);

                Class iNotificationManagerCls = Class.forName("android.app.INotificationManager");
                Object iNotificationManagerProxy = Proxy.newProxyInstance(toast.getClass().getClassLoader(),
                        new Class[]{iNotificationManagerCls}, new InvocationHandler() {
                            @Override
                            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                //强制使用系统Toast
                                if ("enqueueToast".equals(method.getName())
                                        || "enqueueToastEx".equals(method.getName())) {  //华为p20 pro上为enqueueToastEx
                                    args[0] = "android";
                                }
                                return method.invoke(iNotificationManagerObj, args);
                            }
                        });
                Field sServiceFiled = Toast.class.getDeclaredField("sService");
                sServiceFiled.setAccessible(true);
                sServiceFiled.set(null, iNotificationManagerProxy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

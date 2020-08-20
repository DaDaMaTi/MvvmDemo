package com.csmar.lib.net;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.util.Log;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.HttpRetryException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.functions.Consumer;
import retrofit2.HttpException;

public abstract class ResponseException implements Consumer<Throwable> {
    /**
     * 未知错误
     */
    public static final int UNKNOWN = 1000;

    /**
     * 解析错误
     */
    public static final int PARSE_ERROR = 1001;

    /**
     * 网络错误
     */
    public static final int NETWORK_ERROR = 1002;

    /**
     * 协议错误
     */
    public static final int HTTP_ERROR = 1003;

    private Context mContext;

    public ResponseException(Context context) {
        if (context != null) {
            this.mContext = context.getApplicationContext();
        }
    }

    @Override
    public void accept(Throwable throwable) throws Exception {
        ApiException ex = handleException(throwable);
        onError(ex);
    }

    private ApiException handleException(Throwable e) {
        ApiException ex;
        if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            //解析错误
            ex = new ApiException(PARSE_ERROR, e.getMessage(), "解析错误！");
            return ex;
        } else if (e instanceof ConnectException) {
            //网络错误
            ex = new ApiException(NETWORK_ERROR, e.getMessage(), "网络错误！");
            return ex;
        } else if (e instanceof UnknownHostException) {
            //连接错误
            ex = new ApiException(NETWORK_ERROR, e.getMessage(), "网络错误！");
            return ex;
        }else if (e instanceof SocketTimeoutException) {
            //连接错误
            ex = new ApiException(NETWORK_ERROR, e.getMessage(), "网络连接超时！");
            return ex;
        } else if (e instanceof HttpException
                || e instanceof HttpRetryException
                || e instanceof retrofit2.adapter.rxjava2.HttpException) {
            ex = new ApiException(NETWORK_ERROR, e.getMessage(), "网络或服务器异常！");
            return ex;
        } else {
            //未知错误
            ex = new ApiException(UNKNOWN, e.getMessage(), "错误！");
            if (mContext != null) {
                if (!isNetworkReachable(mContext)) {
                    ex = new ApiException(NETWORK_ERROR, e.getMessage(), "网络未连接！");
                } else {
                    if (isNetworkAvailable(mContext)) {
                        ex = new ApiException(NETWORK_ERROR, e.getMessage(), "网络连接不可用！");
                    }
                }
            }
            return ex;
        }
    }

    public abstract void onError(ApiException e);

    /**
     * 网络是否连接
     *
     * @param context
     * @return
     */
    private boolean isNetworkReachable(Context context) {
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
    private boolean isNetworkAvailable(Context context) {
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

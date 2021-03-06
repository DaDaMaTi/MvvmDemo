package com.csmar.lib.net;

import android.content.Context;
import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.google.gson.stream.MalformedJsonException;

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

    /**
     * 证书错误
     */
    public static final int SSL_ERROR = 1004;

    /**
     * 主机未知
     */
    public static final int HOST_ERROR = 1005;

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
                || e instanceof ParseException
                || e instanceof MalformedJsonException) {
            //解析错误
            ex = new ApiException(PARSE_ERROR, e.getMessage(), "解析错误！");
            return ex;
        } else if (e instanceof javax.net.ssl.SSLException) {
            ex = new ApiException(SSL_ERROR, e.getMessage(), "证书验证失败");
            return ex;
        } else if (e instanceof ConnectException) {
            //网络错误
            ex = new ApiException(NETWORK_ERROR, e.getMessage(), "网络错误！");
            return ex;
        } else if (e instanceof UnknownHostException) {
            //连接错误
            ex = new ApiException(HOST_ERROR, e.getMessage(), "主机地址未知！");
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
                if (!NetworkUtil.isNetworkReachable(mContext)) {
                    ex = new ApiException(NETWORK_ERROR, e.getMessage(), "网络未连接！");
                } else {
                    if (NetworkUtil.isNetworkAvailable(mContext)) {
                        ex = new ApiException(NETWORK_ERROR, e.getMessage(), "网络连接不可用！");
                    }
                }
            }
            return ex;
        }
    }

    public abstract void onError(ApiException e);
}

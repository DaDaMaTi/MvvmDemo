package com.csmar.lib.net;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.X509TrustManager;
import javax.security.cert.CertificateException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络管理类
 *
 * @autor wsd
 * @since 2020-07-06
 */
public class NetworkManager {

    private static NetworkManager mInstance;
    private static Retrofit retrofit;
    private static volatile RequestApi request = null;

    public static NetworkManager getInstance() {
        if (mInstance == null) {
            synchronized (NetworkManager.class) {
                if (mInstance == null) {
                    mInstance = new NetworkManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化必要对象和参数
     */
    public void init(final Context context) {
        //声明缓存地址和大小
        Cache cache = new Cache(context.getCacheDir(),2 * 1024 * 1024);
        // 初始化okhttp
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(logging);

        builder.cache(cache)
                .retryOnConnectionFailure(true) // //默认重试一次，若需要重试N次，则要实现拦截器
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        // 有网络的情况下，缓存时间是：20秒。也就是在20秒内的请求都是获取本地的缓存。当网络断开后，会设置一个离线的缓存，为上面设置的时间
                        // maxAge ：设置最大失效时间，失效则不使用
                        // maxStale ：设置最大失效时间，失效则不使用
                        // max-stale在请求头设置有效，在响应头设置无效。
                        // max-stale和max-age同时设置的时候，缓存失效的时间按最长的算
                        Request request = chain.request();
                        if (!isNetworkReachable(context)) {
                            int maxStale = 7 * 24 * 60 * 60; // 离线时缓存保存1周,单位:秒
                            CacheControl tempCacheControl = new CacheControl.Builder()
                                    .onlyIfCached()
                                    .maxStale(maxStale, TimeUnit.SECONDS)
                                    .build();
                            request = request.newBuilder()
                                    .cacheControl(tempCacheControl)
                                    .build();
                        }
                        return chain.proceed(request);
                    }
                })
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Response originalResponse = chain.proceed(request);
                        int maxAge = 20;    // 在线缓存,单位:秒
                        return originalResponse.newBuilder()
                                .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                                .removeHeader("Cache-Control")
                                .header("Cache-Control", "public, max-age=" + maxAge)
                                .build();
                    }
                })
                .sslSocketFactory(new MySSLSocketFactory(trustAllCert), trustAllCert)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS);

        OkHttpClient client = builder.build();

        // 初始化Retrofit
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BuildConfig.URL) // 设置基础地址 url
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 设置返回类型适配
                .addConverterFactory(GsonConverterFactory.create()) // 设置数据解析器
                .build();
    }

    public static RequestApi getRequest() {
        if (request == null) {
            synchronized (RequestApi.class) {
                request = retrofit.create(RequestApi.class);
            }
        }
        return request;
    }

    /**
     * create you ApiService
     * Create an implementation of the API endpoints defined by the {@code service} interface.
     */
    public <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(service);
    }

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

    //定义一个信任所有证书的TrustManager
    final X509TrustManager trustAllCert = new X509TrustManager() {
        @Override
        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
        }

        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
        }
    };
}

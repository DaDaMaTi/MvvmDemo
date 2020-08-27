package com.csmar.lib.net;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
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

    //缓存时间
    int CACHE_TIMEOUT = 10 * 1024 * 1024;

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
        //缓存存放的文件
        File httpCacheDirectory = new File(context.getCacheDir(), "college_cache");
        //声明缓存地址和大小  缓存拦截器，当没有网络连接的时候自动读取缓存中的数据，缓存存放时间默认为3天。
        Cache cache = new Cache(httpCacheDirectory, CACHE_TIMEOUT);
        // 初始化okhttp
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(logging);

        builder.cache(cache)
                .addInterceptor(new CacheInterceptor(context)) // 无网络读取缓存
                .retryOnConnectionFailure(true) // //默认重试一次，若需要重试N次，则要实现拦截器
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

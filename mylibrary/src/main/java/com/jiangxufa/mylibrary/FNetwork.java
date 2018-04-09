package com.jiangxufa.mylibrary;


import android.arch.lifecycle.BuildConfig;
import android.content.Context;

import com.jiangxufa.mylibrary.Utils.AppContextHolder;
import com.jiangxufa.mylibrary.converter.FastJsonConverterFactory;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/*
 * 创建时间：2018/1/6
 * 编写人：lenovo
 * 功能描述：
 */

public class FNetwork {

    private static HashMap<Class, Object> apiMap = new HashMap<>();
    private static Converter.Factory gsonConverterFactory = FastJsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create();
    private static OkHttpClient okHttpClient;
    private static OkHttpClient.Builder builder;
    private final static int CONNECT_TIMEOUT =60;
    private final static int READ_TIMEOUT=100;
    private final static int WRITE_TIMEOUT=60;

    public static void init(Interceptor... interceptors) {
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            builder = new OkHttpClient.Builder()
                    .readTimeout(READ_TIMEOUT,TimeUnit.SECONDS)//设置读取超时时间
                    .writeTimeout(WRITE_TIMEOUT,TimeUnit.SECONDS)//设置写的超时时间
                    .connectTimeout(CONNECT_TIMEOUT,TimeUnit.SECONDS);//设置连接超时时间
            if (interceptors.length > 0) {
                for (Interceptor interceptor : interceptors) {
                    builder.addInterceptor(interceptor);
                }
            }
            builder.addInterceptor(httpLoggingInterceptor);
            okHttpClient = builder.build();
        } else {
            builder = new OkHttpClient.Builder();
            if (interceptors.length > 0) {
                for (Interceptor interceptor : interceptors) {
                    builder.addInterceptor(interceptor);
                }
            }
            okHttpClient = builder
                    .readTimeout(READ_TIMEOUT,TimeUnit.SECONDS)//设置读取超时时间
                    .writeTimeout(WRITE_TIMEOUT,TimeUnit.SECONDS)//设置写的超时时间
                    .connectTimeout(CONNECT_TIMEOUT,TimeUnit.SECONDS)//设置连接超时时间
                    .build();
        }

    }

    /**
     * 获取服务请求的对象
     * @param clazz Service接口类型
     * @param <T> 接口对象类型
     * @return 返回接口对象
     */
    public static <T> T getService(Class<T> clazz) {
        if (apiMap.containsKey(clazz)) {
            return (T) apiMap.get(clazz);
        } else {
            throw new RuntimeException("请先初始化相应的api service");
        }
    }

    /**
     * 初始化retrofit客户端
     *
     * @param baseUrl baseUrl
     * @param clazz   apiService
     * @param <T>     apiService
     * @return apiService
     */
    public static <T> T initService(String baseUrl, Class<T> clazz) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .client(okHttpClient)
                .build();
        T t = retrofit.create(clazz);
        apiMap.put(clazz, t);
        return t;
    }

    public static void init(Context context) {
        AppContextHolder.initContext(context);
    }

}

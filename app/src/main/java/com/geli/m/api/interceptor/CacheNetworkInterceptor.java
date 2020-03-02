package com.geli.m.api.interceptor;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Response;

class CacheNetworkInterceptor implements Interceptor {
    public Response intercept(Interceptor.Chain chain) throws IOException {
        //无缓存,进行缓存
        return chain.proceed(chain.request()).newBuilder()
                .removeHeader("Pragma")
                .addHeader("Cache-Control", "max-age=60")   // 对请求进行最大60秒的缓存
                .build();
    }
}
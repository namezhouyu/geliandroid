package com.geli.m.api.interceptor;

import com.geli.m.app.GlobalData;
import com.geli.m.utils.LogUtils;
import com.geli.m.utils.NetWorkUtils;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

import static okhttp3.internal.Util.UTF_8;

public class MyCacheInterceptor implements Interceptor {

    public Response intercept(Interceptor.Chain chain) throws IOException {
        Response response;
        Request request;

        if (NetWorkUtils.isNetworkConnected(GlobalData.mContext)) {
            request = chain.request()
                    .newBuilder()
                    .cacheControl(new CacheControl
                            .Builder()
                            .maxAge(10, TimeUnit.SECONDS)     // 有网络,检查10秒内的缓存
                            .build())
                    .build();
        } else {
            request = chain.request()
                    .newBuilder()
                    .cacheControl(new CacheControl.Builder()
                            .onlyIfCached()
                            .maxStale(30, TimeUnit.DAYS)     // 无网络,检查30天内的缓存,即使是过期的缓存
                            .build())
                    .build();
        }

        printRequestMessage(request);
        response = chain.proceed(request);
        printResponseMessage(response);
        return response.newBuilder().build();
    }


    /**
     * 打印请求消息
     * @param request 请求的对象
     */
    private void printRequestMessage(Request request) {
        if (request == null) {
            return;
        }
        LogUtils.i("=");
        LogUtils.i("==================================请求信息 start ===================================");
        LogUtils.i("UrlSet : " + request.url().url().toString());
        LogUtils.i("Method: " + request.method());
        LogUtils.i("Heads : " + request.headers());
        RequestBody requestBody = request.body();
        if (requestBody == null) {
            LogUtils.i( "=================请求信息  end (没有请求体 requestBody == null)  ====================");
            return;
        }
        try {
            Buffer bufferedSink = new Buffer();
            requestBody.writeTo(bufferedSink);
            MediaType mediaType = requestBody.contentType();

            if(mediaType != null) {
                Charset charset = mediaType.charset();
                charset = charset == null ? Charset.forName("utf-8") : charset;
                LogUtils.i("Params: " + bufferedSink.readString(charset));
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.e("intercept -- printRequestMessage -- IOException e:" , e);
        } catch (NullPointerException e){
            LogUtils.e("intercept -- printRequestMessage -- NullPointerException e:" , e);
        }finally {
            LogUtils.i( "");
            LogUtils.i("==================================请求信息  end  ===================================");
        }

    }
    /**
     *
     * 打印返回消息
     * @param response 返回的对象
     */
    private void printResponseMessage(Response response) {
        if (response == null || !response.isSuccessful()) {
            return;
        }
        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        BufferedSource source = responseBody.source();
        try {
            source.request(Long.MAX_VALUE); // Buffer the entire body.
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.e("intercept -- printResponseMessage -- IOException e:" , e);
        }
        Buffer buffer = source.buffer();
        Charset charset = UTF_8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset();
        }
        if (charset != null && contentLength != 0) {
            String result = buffer.clone().readString(charset);
            LogUtils.i("=");
            LogUtils.i("==================================返回消息 start ===================================");
            LogUtils.i("Response: " + result);
            LogUtils.printJsonAll(result);
            LogUtils.i("==================================返回消息  end  ===================================");
        }
    }
}
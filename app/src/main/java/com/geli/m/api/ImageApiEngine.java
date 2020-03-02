package com.geli.m.api;

import com.geli.m.BuildConfig;
import com.geli.m.app.GlobalData;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.autonavi.ae.gmap.utils.GLMapUtil.getAppVersionName;
import static com.geli.m.BuildConfig.GL_IMAGE_URL;


/**
 *
 */
public class ImageApiEngine {
    private static ImageApiEngine apiEngine;
    private Retrofit retrofit;

    private ImageApiEngine() {
        /* 日志拦截器 */
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request build = chain.request().newBuilder().addHeader("User-Agent", getAgent(true)).build();
                return chain.proceed(build);
            }
        };

        /* 缓存 */
        CookieJar cookieJar = new CookieJar() {
            private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

            // Tip：這裡key必須是String
            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                cookieStore.put(url.host(), cookies);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url.host());
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        };

        //缓存
        //        int size = 1024 * 1024 * 100;
        //        File cacheFile = new File(App.mContext.getCacheDir(), "OkHttpCache");
        //        Cache cache = new Cache(cacheFile, size);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(12, TimeUnit.SECONDS)
                .writeTimeout(12, TimeUnit.SECONDS)
                .writeTimeout(12, TimeUnit.SECONDS)
                .cookieJar(cookieJar)
                //.addInterceptor(interceptor);
                .addInterceptor(new LoggerInterceptor());
                // .cache(cache)

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }

        OkHttpClient client = builder.build();
        retrofit = new Retrofit.Builder()
                .baseUrl(GL_IMAGE_URL)
                //.baseUrl("https://image.baidu.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    public static ImageApiEngine getInstance() {
        if (apiEngine == null) {
            synchronized (ImageApiEngine.class) {
                if (apiEngine == null) {
                    apiEngine = new ImageApiEngine();
                }
            }
        }
        return apiEngine;
    }

    public Api getApiService() {
        return retrofit.create(Api.class);
    }

    public String getAgent(boolean needHost) {
        String res = "&okhttp&&android&" + android.os.Build.VERSION.RELEASE + "&V" + getAppVersionName(GlobalData.getInstance());
        if (needHost) {
            res = "l.gelifood.com" + res;
        }
        return res;
    }
}

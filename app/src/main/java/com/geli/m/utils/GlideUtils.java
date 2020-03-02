package com.geli.m.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.geli.m.R;
import com.geli.m.api.UrlSet;

import static android.widget.ImageView.ScaleType.FIT_CENTER;
import static com.geli.m.BuildConfig.GL_IMAGE_URL;

/**
 * Created by Steam_l on 2018/1/9.
 */

public class GlideUtils {
    public static void loadImg(Context context, String url, ImageView imageView) {
        loadImg(context, url, imageView, false);
    }

    public static void loadImgRect(Context context, String url, ImageView imageView) {
        loadImgRect(context, url, imageView, false);
    }


    public static void loadImg(Context context, String url, ImageView imageView, boolean isLocation) {
        if (!isLocation && StringUtils.isNotEmpty(url)) {
            url = url.startsWith(UrlSet.HTTP)|| url.startsWith(UrlSet.HTTPS) ? url : GL_IMAGE_URL + url;
        }

        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.img_loading)
                .error(R.drawable.img_jiazaishibei)
                .centerCrop();
        try {
            Glide.with(context).load(url).apply(options).into(imageView);
        }catch (IllegalArgumentException e){
            // context就有问题 会出现这个 -- 如当初没有设置 禁止 HomeActivity 横竖屏切换 重新生命周期
            // LogUtils.i("loadImg error:" , e);
        }

    }

    public static void loadImgRect(Context context, String url, ImageView imageView,
                                   boolean isLocation) {
        if (!isLocation && StringUtils.isNotEmpty(url)) {
            url = url.startsWith(UrlSet.HTTP)|| url.startsWith(UrlSet.HTTPS) ? url : GL_IMAGE_URL + url;
        }

        imageView.setScaleType(FIT_CENTER);
        //imageView.setScaleType(FIT_XY);
        //imageView.setScaleType(CENTER_INSIDE);
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.ALL)            //缓存所有版本的图像
                .placeholder(R.drawable.img_loading)
                .error(R.drawable.img_jiazaishibei);

        Glide.with(context).load(url).apply(options).into(imageView);
    }


    public static void loadAvatar(Context context, String url, ImageView imageView) {
        loadAvatar(context, url, imageView, false);
    }

    public static void loadAvatar(Context context, String url, ImageView imageView, boolean isLocation) {
        if (!isLocation) {
            url = url.startsWith(GL_IMAGE_URL) ? url : GL_IMAGE_URL + url;
        }
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.img_jiazaishibei)
                .centerCrop();
        Glide.with(context).load(url).apply(options).into(imageView);
    }


    SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
        @Override
        public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
            // mTvOther.setImageDrawable(resource);
            // mTvOther.setCompoundDrawables(null, resource, null, null);
        }
    };

    public static void loadDrawable(Context context, String url, SimpleTarget<Drawable> simpleTarget) {
        url = url.startsWith(GL_IMAGE_URL) ? url : GL_IMAGE_URL + url;

        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.img_jiazaishibei)
                .centerCrop();
        Glide.with(context).load(url).apply(options).into(simpleTarget);
    }
}

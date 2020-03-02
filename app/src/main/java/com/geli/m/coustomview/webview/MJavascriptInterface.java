package com.geli.m.coustomview.webview;

import android.content.Context;
import android.content.Intent;
import com.geli.m.ui.activity.ShowImageActivity;
import java.util.ArrayList;

/**
 * Created by pks on 2017/7/14.
 */

public class MJavascriptInterface {
    private Context context;
//    private ArrayList<String> imageUrls;

    public MJavascriptInterface(Context context) {
        this.context = context;
//        imageUrls = new ArrayList<>();
    }

//    public MJavascriptInterface(Context context, ArrayList<String> imageUrls) {
//        this.context = context;
//        if (imageUrls == null) {
//            imageUrls = new ArrayList<>();
//        }
//        this.imageUrls = imageUrls;
//    }

    @android.webkit.JavascriptInterface
    public void openImage(String img) {//点击webview中的图片
        ArrayList<String> imgList = new ArrayList<>();
        imgList.add(img);
//        int position = getPosition(img);
        Intent intent = new Intent(context, ShowImageActivity.class);
        intent.putExtra(ShowImageActivity.EXTRA_IMAGE_URLS, imgList);
        intent.putExtra(ShowImageActivity.EXTRA_IMAGE_INDEX, 0);
        context.startActivity(intent);
    }

//    //
//    //根据图片url获取在整个路径集合中的位置
//    private int getPosition(String imgUrl) {
//        if (TextUtils.isEmpty(imgUrl)) return 0;
//        int len = imageUrls.size();
//        String u = null;
//        int n = 0;
//        for (int i = 0; i < len; i++) {
////            u = imageUrls.get(i);
//            if (imgUrl.equals(imageUrls.get(i))) {
//                n = i;
//            }
//        }
//        return n;
//    }

}

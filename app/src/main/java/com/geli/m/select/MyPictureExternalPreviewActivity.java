package com.geli.m.select;

import android.os.Bundle;

import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.luck.picture.lib.PictureExternalPreviewActivity;

/**
 * Created by Administrator on 2018/1/26.
 */

public class MyPictureExternalPreviewActivity extends PictureExternalPreviewActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this)
                .hideBar(BarHide.FLAG_HIDE_STATUS_BAR).init();
    }
}

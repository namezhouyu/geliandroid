package com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.aftersolddetails_activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.AfterSoldDetailsBean;
import com.geli.m.select.SelectPhotoFragment;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.luck.picture.lib.entity.LocalMedia;
import java.util.ArrayList;
import java.util.List;

import static com.geli.m.BuildConfig.GL_IMAGE_URL;

/**
 * author:  shen
 * date:    2018/5/24
 *
 * "售后跟踪详情"的头布局
 *
 */
public class AfterSoldDetailsHeaderVH extends LinearLayout implements RecyclerArrayAdapter.ItemView {

    FragmentManager mFragmentManager;
    Context mContext;
    /** 售后反馈的文本内容 */
    TextView mTv_content;
    /** 包裹 "售后反馈的图片" 的布局 */
    FrameLayout mFLayout_content;

    public AfterSoldDetailsHeaderVH(Context context, FragmentManager supportFragmentManager) {
        super(context);
        mContext = context;
        mFragmentManager = supportFragmentManager;
    }

    @Override
    public View onCreateView(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        return layoutInflater.inflate(R.layout.itemview_after_sold_details_header, null);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindView(View headerView) {
        mTv_content = (TextView) headerView.findViewById(R.id.tv_after_sold_details_header_content);
        mFLayout_content = (FrameLayout) headerView.findViewById(R.id.fl_after_sold_details_header_img);
        mTv_content.setMovementMethod(ScrollingMovementMethod.getInstance());

        // 此"触动监听"主要是获得"事件"(不让父控件拦截)，进而是指可以滚动
        mTv_content.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true); // 父控件不拦截事件
                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);    // 可以拦截了
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 设置"售后反馈的内容"
     * @param bean
     */
    public void setDate(AfterSoldDetailsBean bean){
        mTv_content.setText(bean.getData().getContent());
    }

    /**
     * 设置"售后反馈的图片"
     * @param bean
     */
    public void setImg(AfterSoldDetailsBean bean){

        List<String> imgList = bean.getData().getImg();

        if (imgList != null && imgList.size() != 0) {
            List<LocalMedia> urlList = new ArrayList<>();
            for (String url : imgList) {
                LocalMedia media = new LocalMedia();
                if (!url.startsWith(GL_IMAGE_URL)) {
                    url = GL_IMAGE_URL + url;
                }
                media.setPath(url);
                urlList.add(media);
            }

            // 评论上的 "fragment" 显示图片的 只是显示图片 !!!
            SelectPhotoFragment fragment = SelectPhotoFragment.newInstance(3, 3, urlList, 10);
            fragment.setMode(SelectPhotoFragment.MODE_CHECK);
            mFragmentManager.beginTransaction().replace(R.id.fl_after_sold_details_header_img, fragment).commit();

        }else {     /* 必须设置这里 -- 否则如果没有图片，界面显示会出问题的 */
            List<LocalMedia> urlList = new ArrayList<>();
            SelectPhotoFragment fragment = SelectPhotoFragment.newInstance(3, 3, urlList, 10);
            fragment.setMode(SelectPhotoFragment.MODE_CHECK);
            mFragmentManager.beginTransaction().replace(R.id.fl_after_sold_details_header_img, fragment).commit();
        }

    }

}

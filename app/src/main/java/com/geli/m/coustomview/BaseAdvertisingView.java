package com.geli.m.coustomview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.geli.m.bean.AtsBean;
import com.geli.m.mvp.home.index_fragment.main.IndexFragment;
import com.geli.m.utils.GlideUtils;
import java.util.List;

import static com.geli.m.app.GlobalData.mContext;

/**
 * Created by l on 2018/4/14.
 *
 * Base广告布局
 */
public abstract class BaseAdvertisingView extends RelativeLayout {
    protected int mWinWidth;
    public static final String TAG_IMG = "tag_img";

    public BaseAdvertisingView(Context context) {
        this(context, null);
    }

    public BaseAdvertisingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseAdvertisingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        for (int i = 0; i < imgNumber(); i++) {                        // 添加"图片控件"
            ImageView imageView = new ImageView(context);
            // imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            addView(imageView);                                        // 将"控件"添加到"布局中"
        }

        // 获取"手机屏宽"
        WindowManager manager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        mWinWidth = manager.getDefaultDisplay().getWidth();
    }

    /**
     * 图片的数量
     * @return
     */
    abstract int imgNumber();

    public void setData(List<AtsBean> data) {
        if (data != null && data.size() > 0) {
            for (int i = 0; i < getChildCount(); i++) {
                ImageView iv_adv = (ImageView) getChildAt(i);
                final AtsBean advBean = data.get(i);
                GlideUtils.loadImg(getContext(), advBean.getAts_pic(), iv_adv); // 拿到"图片控件"，添加图片

                iv_adv.setOnClickListener(new OnClickListener() {               // 设置图片的点击事件
                    @Override
                    public void onClick(View v) {
                        IndexFragment.bannerOnClicker(mContext, advBean);
                    }
                });
            }
        }
    }
}

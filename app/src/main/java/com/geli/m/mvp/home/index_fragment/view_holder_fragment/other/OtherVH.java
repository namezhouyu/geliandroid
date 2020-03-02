package com.geli.m.mvp.home.index_fragment.view_holder_fragment.other;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.geli.m.R;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.home.index_fragment.main.IndexFragment;
import com.geli.m.mvp.home.index_fragment.overseas_activity.OverseasActivity;
import com.geli.m.mvp.home.index_fragment.restaurantlist_activity.main.RestaurantListActivity;
import com.geli.m.mvp.home.index_fragment.retailcenter_activity.RetailCenterActivity;
import com.geli.m.mvp.home.other.WebViewActivity;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import static com.geli.m.config.Constant.INTENT_LINKS;
import static com.geli.m.config.Constant.INTENT_LOCATION;
import static com.geli.m.config.Constant.INTENT_TYPE;
import static com.geli.m.config.Constant.Other_ChangJia;
import static com.geli.m.config.Constant.Other_JinRong;
import static com.geli.m.config.Constant.Other_Logistics;
import static com.geli.m.config.Constant.Other_Overseas;
import static com.geli.m.config.Constant.Other_Piling;
import static com.geli.m.config.Constant.Other_Restaurant;

/**
 * Created by l on 2018/4/9.
 *
 * 其他的
 */
public class OtherVH extends BaseViewHolder<Integer> {
    Context mContext;
    /** 食品馆描述 */
    @BindView(R.id.tv_other_HomeOtherVH)
    TextView mTvOther;


    public OtherVH(ViewGroup parent, Context context) {
        super(parent, R.layout.vh_home_other);
        mContext = context;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(final Integer integer) {
        if (integer != null) {
            setView(integer.intValue());

            mTvOther.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickL(integer.intValue());
                }
            });

        }
    }



    private void setView(int value) {
        switch (value){
            case Other_Piling:                  /* 批零列表 */
                setImageAndText(R.drawable.middle_btn_pingling, R.string.piling_center);
                break;

            case Other_ChangJia:                /* 厂家直供 */
                setImageAndText(R.drawable.middle_btn_changjia, R.string.changjia_zhigong);
                break;

            case Other_Restaurant:              /* 食品馆 */
                setImageAndText(R.drawable.middle_btn_restaurant, R.string.restaurant);
                break;

            case Other_Overseas:                /* 海外 */
                setImageAndText(R.drawable.middle_btn_haiwai, R.string.haiwai_zhicai);
                break;

            case Other_Logistics:               /* 物流 -- 冷运 */
                setImageAndText(R.drawable.middle_btn_lenyun, R.string.geli_lengyun);
                break;

            case Other_JinRong:                 /* 金融 */
                setImageAndText(R.drawable.middle_btn_jinrong, R.string.geli_jinrong);
                break;
        }
    }

    private void setImageAndText(int drawableResId, int strResId){
        Resources resources = mContext.getResources();
        Drawable drawable = resources.getDrawable(drawableResId);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        mTvOther.setCompoundDrawables(null, drawable, null, null);
        mTvOther.setText(mContext.getString(strResId));
    }


    private void clickL(int value){
        Intent intent = null;
        switch (value){
            case Other_Piling:                  /* 批零列表 */
                intent = new Intent().putExtra(INTENT_TYPE, RetailCenterActivity.TYPE_PILING);
                ((BaseActivity) mContext).startActivity(RetailCenterActivity.class, intent);
                break;

            case Other_ChangJia:                /* 厂家直供 */
                intent = new Intent().putExtra(INTENT_TYPE, RetailCenterActivity.TYPE_CHANGJIA);
                ((BaseActivity) mContext).startActivity(RetailCenterActivity.class, intent);
                break;

            case Other_Restaurant:              /* 食品馆 */
                intent = new Intent().putExtra(INTENT_LOCATION, IndexFragment.mAreaBean);
                ((BaseActivity) mContext).startActivity(RestaurantListActivity.class, intent);
                break;

            case Other_Overseas:                /* 海外 */
                mContext.startActivity(new Intent(mContext, OverseasActivity.class));
                break;

            case Other_Logistics:               /* 物流 -- 冷运 */
                logistics();
                break;

            case Other_JinRong:                 /* 金融 */
                //intent = new Intent().putExtra(INTENT_LINKS, "http://m.gelifood.com/finance");
                intent = new Intent().putExtra(INTENT_LINKS, "http://m.gelistore.com/finance");
                ((BaseActivity) mContext).startActivity(WebViewActivity.class, intent);
                break;
        }
    }

    /**
     * 物流
     */
    private void logistics() {
        if (Utils.isAvilible(mContext, Utils.getString(R.string.gl_lengyun_packgename))) { // 如果安装了这个包名的APP,返回true
            try {
                // 这里的packname就是从上面得到的目标apk的包名
                Intent resolveIntent = mContext.getPackageManager().getLaunchIntentForPackage(Utils.getString(R.string.gl_lengyun_packgename));
                mContext.startActivity(resolveIntent); // 启动目标应用
            } catch (Exception e) {
                ToastUtils.showToast(Utils.getString(R.string.message_unknown_mistake));
            }
        } else {                                // 使用手机浏览器打开这个网址 -- "http://app.l.gelifood.com/mobile/"
            Intent intentTemp = new Intent();
            intentTemp.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse("http://app.l.gelistore.com/mobile/");
            intentTemp.setData(content_url);
            mContext.startActivity(intentTemp);
        }
    }
}

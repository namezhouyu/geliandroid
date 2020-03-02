package com.geli.m.mvp.home.index_fragment.view_holder_fragment.nav;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.geli.m.R;
import com.geli.m.bean.NvaBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main.LocalRestaurantListActivity;
import com.geli.m.mvp.home.index_fragment.main.IndexFragment;
import com.geli.m.mvp.home.index_fragment.overseas_activity.OverseasActivity;
import com.geli.m.mvp.home.index_fragment.restaurantlist_activity.main.RestaurantListActivity;
import com.geli.m.mvp.home.index_fragment.retailcenter_activity.RetailCenterActivity;
import com.geli.m.mvp.home.other.WebViewActivity;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.StringUtils;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import static com.geli.m.config.Constant.INTENT_LINKS;
import static com.geli.m.config.Constant.INTENT_LOCATION;
import static com.geli.m.config.Constant.INTENT_TITLE;
import static com.geli.m.config.Constant.INTENT_TYPE;

/**
 * Created by l on 2018/4/9.
 *
 * 导航子view
 *新批发-厂家直供-海外直采-格利金融系统
 *
 */
public class NavVH extends BaseViewHolder<NvaBean> {
    Context mContext;

    @BindView(R.id.cLayout_NavItemView)
    ConstraintLayout mCLayout;

    /**  */
    @BindView(R.id.iv_NavItemView)
    ImageView mIv;
    /**  */
    @BindView(R.id.tv_NavItemView)
    TextView mTv;


    public NavVH(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_nav);
        mContext = context;
        ButterKnife.bind(this, itemView);

        int width = ((Activity) mContext).getWindowManager().getDefaultDisplay().getWidth();
        ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
        layoutParams.width = (int) (width * 0.2);
        itemView.setLayoutParams(layoutParams);

    }

    @Override
    public void setData(final NvaBean data) {

        GlideUtils.loadAvatar(mContext, data.getNav_img(), mIv);
        mTv.setText(data.getNav_title());

        mCLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.getTarget_type() == 1){ //原来的类型链接
                    clickL(data);
                }else if(data.getTarget_type() == 2){   //根据线上的类型进入网页（动态的原因是因为金融这个分类审核不通过）
                    if(StringUtils.isNotEmpty(data.getTarget())){
                        Intent intent = new Intent().putExtra(INTENT_LINKS, data.getTarget());
                        ((BaseActivity) mContext).startActivity(WebViewActivity.class, intent);
                    }
                }
            }
        });
    }

    private void clickL(NvaBean data){
        Intent intent = null;
        switch (data.getNav_key()){

            case Constant.Navkey.sell:                  /* 批零列表 */
                intent = new Intent().putExtra(INTENT_TYPE, RetailCenterActivity.TYPE_PILING);
                ((BaseActivity) mContext).startActivity(RetailCenterActivity.class, intent);
                break;

            case Constant.Navkey.market:                /* 新批发 */
                intent = new Intent().putExtra(INTENT_LOCATION, IndexFragment.mAreaBean);
                ((BaseActivity) mContext).startActivity(RestaurantListActivity.class, intent);
                break;

            case Constant.Navkey.localFood:             /* 本地食品 */
                intent = new Intent().putExtra(INTENT_LOCATION, IndexFragment.mAreaBean);
                ((BaseActivity) mContext).startActivity(LocalRestaurantListActivity.class, intent);
                break;

            case Constant.Navkey.factory:                /* 厂家直供 */
                intent = new Intent().putExtra(INTENT_TYPE, RetailCenterActivity.TYPE_CHANGJIA);
                ((BaseActivity) mContext).startActivity(RetailCenterActivity.class, intent);
                break;

            case Constant.Navkey.overseas:                /* 海外直采 */
                mContext.startActivity(new Intent(mContext, OverseasActivity.class));
                break;

            case Constant.Navkey.logistics:               /* 物流 -- 冷运 */
                logistics();
                break;

            case Constant.Navkey.pay:                     /* 金融 */
                //intent = new Intent().putExtra(INTENT_LINKS, "http://m.gelifood.com/finance");
                intent = new Intent().putExtra(INTENT_LINKS, "http://m.gelistore.com/finance");
                intent.putExtra(INTENT_TITLE, data.getNav_title());
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

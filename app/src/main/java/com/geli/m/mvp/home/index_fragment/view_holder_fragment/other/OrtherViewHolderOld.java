package com.geli.m.mvp.home.index_fragment.view_holder_fragment.other;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import com.geli.m.R;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.home.index_fragment.overseas_activity.OverseasActivity;
import com.geli.m.mvp.home.index_fragment.retailcenter_activity.RetailCenterActivity;
import com.geli.m.mvp.home.other.WebViewActivity;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import static com.geli.m.config.Constant.INTENT_LINKS;
import static com.geli.m.config.Constant.INTENT_TYPE;

/**
 * Created by l on 2018/4/9.
 *
 * 其他的
 */

public class OrtherViewHolderOld extends BaseViewHolder {
    Context mContext;

    public OrtherViewHolderOld(ViewGroup parent, Context context) {
        super(parent, R.layout.include_home_orther);
        mContext = context;

        View.OnClickListener onClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = null;
                switch (v.getId()) {

                    case R.id.bt_index_orther_piling:                   /* 批零列表 */
                        intent = new Intent().putExtra(INTENT_TYPE, RetailCenterActivity.TYPE_PILING);
                        ((BaseActivity) mContext).startActivity(RetailCenterActivity.class, intent);
                        break;

                    case R.id.bt_index_orther_changjia:                 /* 厂家直供 */
                        intent = new Intent().putExtra(INTENT_TYPE, RetailCenterActivity.TYPE_CHANGJIA);
                        ((BaseActivity) mContext).startActivity(RetailCenterActivity.class, intent);
                        break;

                    case R.id.bt_index_orther_overseas:                 /* 海外 */
                        mContext.startActivity(new Intent(mContext, OverseasActivity.class));
                        break;

                    case R.id.bt_index_orther_logistics:                /* 物流 -- 冷运 */
                        logistics();
                        break;

                    case R.id.bt_index_orther_jinrong:                  /* 金融 */
                        // ((BaseActivity) mContext).startActivity(UploadCertificateActivity.class, new Intent().putExtra(RetailCenterActivity.ARGS_TYPE, RetailCenterActivity.TYPE_CHANGJIA));
                        // ((BaseActivity) mContext).startActivity(ShopDetailsActivity.class, new Intent().putExtra(ShopDetailsActivity.INTENT_SHOPID,"1"));
                        //intent = new Intent().putExtra(INTENT_LINKS, "http://m.gelifood.com/finance");
                        intent = new Intent().putExtra(INTENT_LINKS, "http://m.gelistore.com/finance");
                        ((BaseActivity) mContext).startActivity(WebViewActivity.class, intent);
                        break;


                    default:
                        break;
                }
            }
        };

        $(R.id.bt_index_orther_jinrong).setOnClickListener(onClickListener);
        $(R.id.bt_index_orther_logistics).setOnClickListener(onClickListener);
        $(R.id.bt_index_orther_piling).setOnClickListener(onClickListener);
        $(R.id.bt_index_orther_overseas).setOnClickListener(onClickListener);
        $(R.id.bt_index_orther_changjia).setOnClickListener(onClickListener);
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

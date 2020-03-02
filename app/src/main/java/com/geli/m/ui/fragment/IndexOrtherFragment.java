package com.geli.m.ui.fragment;

import android.content.Intent;
import android.view.View;

import com.geli.m.R;
import com.geli.m.mvp.home.index_fragment.overseas_activity.OverseasActivity;
import com.geli.m.mvp.home.index_fragment.retailcenter_activity.RetailCenterActivity;
import com.geli.m.mvp.home.find_fragment.findlist_fragment.details.VideoDetailsActivity;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.base.BaseFragment;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.Utils;

import butterknife.OnClick;

import static com.geli.m.config.Constant.INTENT_TYPE;

/**
 * Created by Steam_l on 2017/12/30.
 */

public class IndexOrtherFragment extends BaseFragment implements View.OnClickListener {
    @Override
    public int getResId() {
        return R.layout.include_home_orther;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.bt_index_orther_jinrong, R.id.bt_index_orther_logistics,
            R.id.bt_index_orther_piling, R.id.bt_index_orther_overseas, R.id.bt_index_orther_changjia})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_index_orther_piling:       // 批零列表
                ((BaseActivity) mContext).startActivity(RetailCenterActivity.class, new Intent().putExtra(INTENT_TYPE, RetailCenterActivity.TYPE_PILING));
                break;

            case R.id.bt_index_orther_overseas:     // 海外
                mContext.startActivity(new Intent(mContext, OverseasActivity.class));
                break;

            case R.id.bt_index_orther_changjia:     // 厂家直供
                ((BaseActivity) mContext).startActivity(RetailCenterActivity.class, new Intent().putExtra(INTENT_TYPE, RetailCenterActivity.TYPE_CHANGJIA));
                break;

            case R.id.bt_index_orther_logistics:    // 物流
                if (Utils.isAvilible(mContext, Utils.getString(R.string.gl_lengyun_packgename))) {
                    try {
                        Intent resolveIntent = mContext.getPackageManager().getLaunchIntentForPackage(Utils.getString(R.string.gl_lengyun_packgename));// 这里的packname就是从上面得到的目标apk的包名
                        // 启动目标应用
                        mContext.startActivity(resolveIntent);
                    } catch (Exception e) {
                        ShowSingleToast.showToast(mContext, Utils.getString(R.string.message_unknown_mistake));
                    }
                } else {

                }
                break;

            case R.id.bt_index_orther_jinrong://jinr
                //                ((BaseActivity) mContext).startActivity(UploadCertificateActivity.class, new Intent().putExtra(RetailCenterActivity.ARGS_TYPE, RetailCenterActivity.TYPE_CHANGJIA));
                ((BaseActivity) mContext).startActivity(VideoDetailsActivity.class, new Intent());
                break;

            default:
                break;
        }
    }
}

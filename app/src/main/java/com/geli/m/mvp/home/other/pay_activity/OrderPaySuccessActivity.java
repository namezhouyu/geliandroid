package com.geli.m.mvp.home.other.pay_activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.home.main.HomeActivity;
import com.geli.m.mvp.home.mine_fragment.myorder_activity.MyOrderActivity;
import com.geli.m.utils.Utils;

import static com.geli.m.config.Constant.INTENT_ITEM;
import static com.geli.m.config.Constant.IndexFragment;

/**
 * Created by Steam_l on 2018/3/12.
 */

public class OrderPaySuccessActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_title_name)
    TextView tv_title;

    @Override
    protected int getResId() {
        return R.layout.activity_orderpaysuccess;
    }

    @Override
    protected void initData() {
        tv_title.setText(Utils.getString(R.string.title_submitorder));
    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.tv_orderpay_success_phone, R.id.iv_title_back,
            R.id.bt_orderpay_success_index, R.id.bt_orderpay_success_myorder})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;

            case R.id.tv_orderpay_success_phone:
                Utils.callPhone(mContext, Utils.getString(R.string.geli_phone));
                break;

            case R.id.bt_orderpay_success_myorder:
                startActivity(MyOrderActivity.class, new Intent());
                break;

            case R.id.bt_orderpay_success_index:
                startActivity(HomeActivity.class, new Intent().putExtra(INTENT_ITEM, IndexFragment));
                break;

            default:
                break;
        }
    }
}

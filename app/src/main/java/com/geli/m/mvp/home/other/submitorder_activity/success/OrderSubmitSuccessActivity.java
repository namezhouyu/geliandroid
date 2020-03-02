package com.geli.m.mvp.home.other.submitorder_activity.success;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
 * Created by Steam_l on 2018/1/5.
 */

public class OrderSubmitSuccessActivity extends BaseActivity {
    @BindView(R.id.iv_title_back)
    ImageView mIvTitleBack;
    @BindView(R.id.tv_title_name)
    TextView mTvTitleName;
    @BindView(R.id.tv_title_right)
    TextView mTvTitleRight;
    @BindView(R.id.rl_title_rootlayout)
    RelativeLayout mRlTitleRootlayout;


    @BindView(R.id.btn_my_order_ordersubmitsuccess)
    Button mBtnMyOrder;
    @BindView(R.id.btn_go_home_ordersubmitsuccess)
    Button mBtnGoHome;

    @Override
    protected int getResId() {
        return R.layout.activity_ordersubmitsuccess;
    }

    @Override
    protected void initData() {
        mTvTitleName.setText(Utils.getString(R.string.title_submitorder));
    }

    @Override
    protected void initEvent() {

    }


    @OnClick({R.id.iv_title_back,
            R.id.btn_my_order_ordersubmitsuccess,
            R.id.btn_go_home_ordersubmitsuccess,
            R.id.tv_phone_ordersubmitsuccess})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.iv_title_back:
                finish();
                break;

            case R.id.tv_phone_ordersubmitsuccess:
                Utils.callPhone(mContext, Utils.getString(R.string.geli_phone));
                break;

            case R.id.btn_my_order_ordersubmitsuccess:
                startActivity(MyOrderActivity.class, new Intent());
                break;

            case R.id.btn_go_home_ordersubmitsuccess:
                startActivity(HomeActivity.class, new Intent().putExtra(INTENT_ITEM, IndexFragment));
                break;
        }
    }
}

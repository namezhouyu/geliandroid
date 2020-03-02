package com.geli.m.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.geli.m.R;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Steam_l on 2018/1/18.
 */

public class ConsumptionDetailsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_title_name)
    TextView tv_title;

    @Override
    protected int getResId() {
        return R.layout.activity_consumption;
    }

    @Override
    protected void initData() {
        tv_title.setText(Utils.getString(R.string.title_consumption_details));
    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.iv_title_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;

            default:
                break;
        }
    }
}

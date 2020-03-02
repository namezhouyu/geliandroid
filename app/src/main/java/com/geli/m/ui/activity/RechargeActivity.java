package com.geli.m.ui.activity;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.geli.m.R;
import com.geli.m.dialog.InputPassDialog;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Steam_l on 2018/1/18.
 * 充值
 */

public class RechargeActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_title_name)
    TextView tv_title;
    @BindView(R.id.cb_recharge_weixin)
    CheckBox cb_weixin;
    @BindView(R.id.cb_recharge_zhifubao)
    CheckBox cb_zhifubao;
    private int weixin_type = 1 << 0;
    private int zhifubao_type = 1 << 1;
    private int curr_paytype = weixin_type;
    private List<CheckBox> mCheckBoxes;

    @Override
    protected int getResId() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void initData() {
        tv_title.setText(Utils.getString(R.string.recharge));
        mCheckBoxes = new ArrayList<>();
        mCheckBoxes.add(cb_weixin);
        mCheckBoxes.add(cb_zhifubao);
        changeSelect(R.id.cb_recharge_weixin);
    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.bt_recharge, R.id.cb_recharge_weixin, R.id.cb_recharge_zhifubao})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cb_recharge_weixin:
            case R.id.cb_recharge_zhifubao:
                changeSelect(v.getId());
                break;
            case R.id.bt_recharge:
                showPopup();
                break;

            default:
                break;
        }
    }

    private void showPopup() {
        InputPassDialog inputPassDialog = new InputPassDialog();
        inputPassDialog.show(getSupportFragmentManager(), "");
    }

    public void changeSelect(int id) {
        for (CheckBox child : mCheckBoxes) {
            child.setChecked(child.getId() == id);
            child.setEnabled(!(child.getId() == id));
            if (child.isChecked()) {
                if (id == R.id.cb_recharge_weixin) {
                    curr_paytype = weixin_type;
                } else {
                    curr_paytype = zhifubao_type;
                }
            }
        }
    }
}

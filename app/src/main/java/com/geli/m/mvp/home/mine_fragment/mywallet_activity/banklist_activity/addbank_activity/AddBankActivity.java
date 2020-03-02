package com.geli.m.mvp.home.mine_fragment.mywallet_activity.banklist_activity.addbank_activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.home.mine_fragment.mywallet_activity.banklist_activity.addbank_activity.bind.BindBankActivity;
import com.geli.m.utils.LoginInformtaionSpUtils;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.Utils;

/**
 * Created by Steam_l on 2018/1/18.
 */

public class AddBankActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_title_name)
    TextView tv_title;
    @BindView(R.id.ll_addbank_info)
    LinearLayout ll_bankinfo;
    @BindView(R.id.cb_addbank_userinfo)
    CheckBox cb_userinfo;
    @BindView(R.id.et_addbank_idnumber)
    EditText et_idnumber;
    @BindView(R.id.et_addbank_name)
    EditText et_name;

    @Override
    protected int getResId() {
        return R.layout.activity_addbank;
    }

    @Override
    protected void initData() {
        tv_title.setText(Utils.getString(R.string.title_addbank));
        String user_info = LoginInformtaionSpUtils.getLoginInfString(mContext, LoginInformtaionSpUtils.LOGIN_USERINFO);
        if (TextUtils.isEmpty(user_info)) {
            ll_bankinfo.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.bt_addbank_next, R.id.iv_title_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.bt_addbank_next:
                String idnumber = et_idnumber.getText().toString().trim();
                String name = et_name.getText().toString().trim();
                if (TextUtils.isEmpty(idnumber) || TextUtils.isEmpty(name)) {
                    ShowSingleToast.showToast(mContext, Utils.getString(R.string.message_pelasefillinfo));
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra(BindBankActivity.INTENT_IDNUMBER, idnumber);
                intent.putExtra(BindBankActivity.INTENT_NAME, name);
                startActivity(BindBankActivity.class, intent);
                break;

            default:
                break;
        }
    }
}

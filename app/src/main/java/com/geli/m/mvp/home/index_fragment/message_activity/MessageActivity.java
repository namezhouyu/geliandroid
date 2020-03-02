package com.geli.m.mvp.home.index_fragment.message_activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.utils.Utils;

import static com.geli.m.config.Constant.MessageType;
import static com.geli.m.config.Constant.Message_Title;

/**
 * Created by Steam_l on 2017/12/22.
 */

public class MessageActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_title_name)
    TextView tv_title;

    @Override
    protected int getResId() {
        return R.layout.activity_message;
    }

    @Override
    protected void initData() {
        tv_title.setText(Utils.getString(R.string.message_centre));
    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.iv_title_back, R.id.bt_logistics_message, R.id.bt_notify_message, R.id.bt_system_message})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_title_back:
                finish();
                break;

            case R.id.bt_system_message:                         /* 1:系统公告 */
                startActivity(MessActivity.class, new Intent().putExtra(Message_Title, MessageType.system_notify));
                break;

            case R.id.bt_notify_message:                        /* 2:通知中心 */
                startActivity(MessActivity.class, new Intent().putExtra(Message_Title, MessageType.notify_center));
                break;

            case R.id.bt_logistics_message:                     /* 3:交易物流 */
                startActivity(MessActivity.class, new Intent().putExtra(Message_Title, MessageType.logistics_transactions));
                break;

            default:
                break;
        }
    }
}

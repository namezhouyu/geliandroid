//package com.geli.m.ui.activity;
//
//import android.view.KeyEvent;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.geli.m.R;
//import com.geli.m.mvp.base.BaseActivity;
//
//import io.rong.imkit.RongIM.UserInfoProvider;
//import io.rong.imkit.fragment.ConversationFragment;
//import io.rong.imlib.model.UserInfo;
//
//
///**
// * Created by Labor on 2017/6/16.
// * 融云通讯
// */
//
//public class conversation extends BaseActivity implements UserInfoProvider {
//    private ImageView backIv;//返回按钮
//    private TextView titleTv;//标题
//
//    @Override
//    protected int getResId() {
//        return R.layout.conversion;
//    }
//
//    @Override
//    protected void initData() {
//    }
//
//    @Override
//    protected void initEvent() {
//
//    }
//
//    @Override
//    public UserInfo getUserInfo(String s) {
//        return null;
//    }
//
//
//    @Override
//    public void onBackPressed() {
//        ConversationFragment fragment = (ConversationFragment) getSupportFragmentManager().findFragmentById(R.id.conversation);
//        if (!fragment.onBackPressed()) {
//            finish();
//        }
//        super.onBackPressed();
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        return super.onKeyDown(keyCode, event);
//    }
//}

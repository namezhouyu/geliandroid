package com.geli.m.mvp.home.mine_fragment.mywallet_activity.recharge_activity;

import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.mvp.base.BaseView;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.select.SelectPhotoFragment;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.Utils;
import com.luck.picture.lib.tools.PictureFileUtils;

import static com.geli.m.config.Constant.INTENT_SHOP_ID;

/**
 * Created by Steam_l on 2017/12/22.
 *
 * 充值
 */

public class ReportShopActivity extends MVPActivity<ReportShopPresentImpl> implements BaseView, View.OnClickListener {
    @BindView(R.id.rg_reportshop_group)
    RadioGroup rg_group;
    @BindView(R.id.et_reportshop_content)
    EditText et_content;
    @BindView(R.id.tv_reportshop_limt)
    TextView tv_limt;
    @BindView(R.id.tv_title_name)
    TextView tv_title;
    int report_type = 1;
    private SelectPhotoFragment mFragment;
    private String shop_id;

    @Override
    protected int getResId() {
        return R.layout.activity_reportshop;
    }

    @Override
    protected void init() {
        super.init();
        shop_id = getIntent().getStringExtra(INTENT_SHOP_ID);
    }

    @Override
    protected void initData() {
        tv_title.setText(Utils.getString(R.string.shopdetails_reprotcenter));
        rg_group.check(R.id.rb_reportshop_1);
        mFragment = SelectPhotoFragment.newInstance(3, 3, 15);
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.fl_reportshop_content, mFragment).commit();
    }

    @Override
    protected void initEvent() {
        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = et_content.getText().toString().length();
                if (length > 200) {
                    ShowSingleToast.showToast(mContext, Utils.getString(R.string.limit_exceeded));
                    int editStart = et_content.getSelectionStart();
                    int editEnd = et_content.getSelectionEnd();
                    s.delete(editStart - 1, editEnd);
                    et_content.setText(s);
                    et_content.setSelection(s.length());
                    return;
                }
                tv_limt.setText(length + "/200");
            }
        });
        rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                //1涉嫌刷单或好评，2商品实际与描述不符，3商家资质问题，4其他
                if (checkedId == R.id.rb_reportshop_1) {
                    report_type = 1;
                } else if (checkedId == R.id.rb_reportshop_2) {
                    report_type = 2;
                } else if (checkedId == R.id.rb_reportshop_3) {
                    report_type = 3;
                } else {
                    report_type = 4;
                }
            }
        });
    }

    @Override
    protected ReportShopPresentImpl createPresenter() {
        return new ReportShopPresentImpl(this);
    }

    @Override
    public void onSuccess(String msg) {
        ShowSingleToast.showToast(mContext, msg);
        PictureFileUtils.deleteCacheDirFile(mContext);
        finish();
    }

    @Override
    public void onError(String msg) {
        ShowSingleToast.showToast(mContext, msg);
    }

    @Override
    public void showLoading() {
        Utils.uProgressDialog(mContext);
    }

    @Override
    public void hideLoading() {
        Utils.dismissProgress();
    }

    @OnClick({R.id.bt_repoetshop_send, R.id.iv_title_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.bt_repoetshop_send:
                if (TextUtils.isEmpty(et_content.getText().toString().trim())) {
                    ShowSingleToast.showToast(mContext, Utils.getString(R.string.message_pelasefillinfo));
                    return;
                }
                mPresenter.reportShop(mFragment.getPhotoModelList(), GlobalData.getUser_id(), shop_id, et_content.getText().toString().trim(), report_type + "");
                break;

            default:
                break;
        }
    }
}

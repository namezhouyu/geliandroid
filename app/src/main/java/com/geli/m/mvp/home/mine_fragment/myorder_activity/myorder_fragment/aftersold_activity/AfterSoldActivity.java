package com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.aftersold_activity;

import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.OrderContactBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.select.SelectPhotoFragment;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;
import com.luck.picture.lib.tools.PictureFileUtils;


/**
 * author:  shen
 * date:    2018/5/22
 * <p>
 * 申请售后
 */

public class AfterSoldActivity extends MVPActivity<AfterSoldPresenterImpl>
        implements AfterSoldView, View.OnClickListener {

    /**
     * 标题名称
     */
    @BindView(R.id.tv_title_name)
    TextView mTvTitle;
    /**
     * 售后"单选框"组
     */
    @BindView(R.id.rg_aftermarket_group)
    RadioGroup mRgProblem;
    /**
     * 售后文本编辑框
     */
    @BindView(R.id.et_aftermarket_content)
    EditText mEtContent;
    /**
     * 售后文本字数(已输入字数/可输入总字数)
     */
    @BindView(R.id.tv_aftermarket_limit)
    TextView mTvLimit;

    /**
     * 联系人
     */
    @BindView(R.id.et_aftermarket_contact)
    EditText mEtContact;
    /**
     * 联系电话
     */
    @BindView(R.id.et_contact_aftermarket_phone)
    EditText mEtContactPhone;
    /**
     * 提交按钮
     */
    @BindView(R.id.bt_aftermarket_submit)
    Button mBtnSubmit;
    /**
     * 订单id
     */
    String mOrderId = "";

    int type = 1;
    private SelectPhotoFragment mFragment;

    @Override
    protected AfterSoldPresenterImpl createPresenter() {
        return new AfterSoldPresenterImpl(this);
    }

    @Override
    protected int getResId() {
        return R.layout.activity_after_sold;
    }

    @Override
    protected void initData() {
        mOrderId = getIntent().getStringExtra(Constant.INTENT_ORDER_ID);
        mTvTitle.setText(Utils.getString(R.string.aftermarket));
        mPresenter.getContact(GlobalData.getUser_id(), mOrderId);

        mFragment = SelectPhotoFragment.newInstance(3, 3, 15);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_aftermarket_content, mFragment).commit();
    }

    @Override
    protected void initEvent() {
        initRgListener();
        initEtContent();
    }

    /**
     * 设置"问题类型"选择框的监听事件
     */
    private void initRgListener() {
        mRgProblem.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                //1商品 2物流 3商家
                if (checkedId == R.id.rb_aftermarket_goods) {                   /* 商品 */
                    type = Constant.AfterSold_goods;
                } else if (checkedId == R.id.rb_aftermarket_logistics) {        /* 物流 */
                    type = Constant.AfterSold_logistics;
                } else if (checkedId == R.id.rb_aftermarket_shop) {             /* 商家 */
                    type = Constant.AfterSold_shop;
                }
            }
        });
    }

    /**
     * 设置"编辑框"字数监听
     */
    private void initEtContent() {
        mEtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = s.toString();
                if (content.length() > 200) {
                    s.delete(200, 201);
                }
                mTvLimit.setText(s.toString().length() + "/200");
            }
        });
    }


    @OnClick({R.id.bt_aftermarket_submit, R.id.iv_title_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;

            case R.id.bt_aftermarket_submit:                       /** 提交按钮 */

                String contact = mEtContact.getText().toString().trim();
                String contactPhone = mEtContactPhone.getText().toString().trim();
                String content = mEtContent.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    ToastUtils.showToast(Utils.getString(R.string.message_pelasefillinfo));
                    return;
                }

                mPresenter.aftermarket(GlobalData.getUser_id(),
                        type + "",
                        mOrderId, content, contact, contactPhone,
                        mFragment.getPhotoModelList(),
                        mFragment.getPhotoModelList().size() + "");
                break;

            default:
                break;
        }
    }


    @Override
    public void onSuccess(String msg) {
        ToastUtils.showToast(msg);
        PictureFileUtils.deleteCacheDirFile(mContext);
        finish();
    }

    @Override
    public void onError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void showLoading() {
        Utils.uProgressDialog(mContext);
    }

    @Override
    public void hideLoading() {
        Utils.dismissProgress();
    }

    @Override
    public void getContactSuccess(OrderContactBean bean) {
        mEtContact.setText(bean.getData().getConsignee().trim());
        mEtContactPhone.setText(bean.getData().getMobile().trim());
    }
}

package com.geli.m.mvp.home.mine_fragment.mywallet_activity.banklist_activity.bankdetails_activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.BankDetailsBean;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.Utils;

/**
 * Created by Steam_l on 2018/1/18.
 */

public class BankDetailsActivity extends MVPActivity<BankDetailsPresentImpl> implements View.OnClickListener, BankDetailsView {
    @BindView(R.id.tv_title_name)
    TextView tv_title;
    @BindView(R.id.tv_bankdetails_bankinfo)
    TextView tv_bankinfo;
    @BindView(R.id.tv_bankdetails_bankname)
    TextView tv_bankname;
    @BindView(R.id.tv_bankdetails_name)
    TextView tv_name;
    @BindView(R.id.tv_bankdetails_phone)
    TextView tv_phone;
    @BindView(R.id.iv_bankdetails_img)
    ImageView iv_img;
    public static final String INTENT_BANKID = "intent_bankid";
    private String bank_id;

    @Override
    protected int getResId() {
        return R.layout.activity_bankdetails;
    }

    @Override
    protected void init() {
        super.init();
        bank_id = getIntent().getStringExtra(INTENT_BANKID);
    }

    @Override
    protected void initData() {
        tv_title.setText(Utils.getString(R.string.title_bankdetails));
        mPresenter.getDetails(GlobalData.getUser_id(), bank_id);
    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.bt_bankdetails_unbin, R.id.iv_title_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.bt_bankdetails_unbin:
                unBind();
                break;

            default:
                break;
        }
    }

    private void unBind() {
        //        startActivity(UnBinBankActivity.class, new Intent().putExtra(INTENT_BANKID, bank_id));
        mPresenter.unBin(GlobalData.getUser_id(), bank_id, "");
    }

    @Override
    protected BankDetailsPresentImpl createPresenter() {
        return new BankDetailsPresentImpl(this);
    }

    @Override
    public void onSuccess(String msg) {
        ShowSingleToast.showToast(mContext, msg);
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

    @Override
    public void showDetails(BankDetailsBean.DataEntity dataEntity) {
        String union_pay_number = dataEntity.getUnion_pay_number();
        union_pay_number = union_pay_number.substring(union_pay_number.length() - 4, union_pay_number.length());
        tv_bankinfo.setText(Utils.getString(R.string.tail_number, union_pay_number) + " " + dataEntity.getCard_type());
        tv_bankname.setText(dataEntity.getBank_category_number());
        tv_name.setText(dataEntity.getName());
        String phone = dataEntity.getBank_phone();
        phone = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        tv_phone.setText(phone);
    }
}

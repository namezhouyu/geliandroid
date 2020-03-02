package com.geli.m.mvp.home.mine_fragment.invoice_activity.invoicetype_activity.addoreditinvoice_activity.personal_fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.api.UrlSet;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.InvoiceBean;
import com.geli.m.mvp.base.BaseView;
import com.geli.m.mvp.base.MVPFragment;
import com.geli.m.mvp.home.mine_fragment.invoice_activity.invoicetype_activity.addoreditinvoice_activity.AddOrEditInvoicePresenterImpl;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;

import static com.geli.m.config.Constant.INTENT_INVOICE_DATA;
import static com.geli.m.config.Constant.INVOICE_ELECTRONIC;
import static com.geli.m.config.Constant.INVOICE_PAGER;
import static com.geli.m.config.Constant.INVOICE_PERSONAL;

/**
 * Created by Steam_l on 2018/1/8.
 */

public class PersonalFragment extends MVPFragment<AddOrEditInvoicePresenterImpl> implements View.OnClickListener, BaseView {

    /** 包裹电子邮件的布局 */
    @BindView(R.id.ll_invoice_personal_electronic_email_layout)
    LinearLayout mLayoutEmailLayout;
    /** 联系电话 */
    @BindView(R.id.ll_invoice_personal_pager_phone_layout)
    LinearLayout mLayoutPhoneLayout;
    /** 发票载体类型 -- 电子、纸质 */
    @BindView(R.id.rg_invoice_personal_type_layout)
    RadioGroup mRgTypeLayout;
    /** 名字 */
    @BindView(R.id.et_personalinvoice_name)
    EditText mEtName;
    /** 电话 */
    @BindView(R.id.et_personalinvoice_phone)
    EditText mEtPhone;
    /** 邮件 */
    @BindView(R.id.et_personalinvoice_email)
    EditText mEtEmail;

    private InvoiceBean.DataEntity mEntity;
    /** 当前发票载体类型 1:电子; 2:纸质*/
    private int mCurrInvoiceType;


    @Override
    protected AddOrEditInvoicePresenterImpl createPresent() {
        return new AddOrEditInvoicePresenterImpl(this);
    }

    public static PersonalFragment newInstance(Bundle args) {
        PersonalFragment personalFragment = new PersonalFragment();
        personalFragment.setArguments(args);
        return personalFragment;
    }

    @Override
    public int getResId() {
        return R.layout.fragment_addoredit_invoice_personal;
    }

    @Override
    protected void init() {
        super.init();
        Bundle arguments = getArguments();
        if(arguments != null){
            mEntity = arguments.getParcelable(INTENT_INVOICE_DATA);
        }
    }

    @Override
    protected void initData() {
        setView();
    }

    @Override
    protected void initEvent() {
        changeTypeView(R.id.rb_invoice_personal_pagerinvoice);      // 默认选择纸质发票
        setRgType();
    }

    private void setView() {
        if (mEntity != null && mEntity.getBelong() == INVOICE_PERSONAL) {    /* 个人发票 */
            mEtName.setText(mEntity.getName());
            mEtName.setSelection(mEtName.getText().toString().length());

            if (mEntity.getInvoice_type() == INVOICE_ELECTRONIC) {          /* 电子发票 */
                mEtEmail.setText(mEntity.getEmail());
                mEtEmail.setSelection(mEtEmail.getText().toString().length());
            } else {                                                        /* 纸质发票 */
                mEtPhone.setText(mEntity.getTel());
                mEtPhone.setSelection(mEtPhone.getText().toString().length());
            }

        }
    }

    /**
     * 纸质发票 和 电子发票
     * @param id
     */
    private void changeTypeView(int id) {
        if (id == R.id.rb_invoice_personal_pagerinvoice) {      /* 纸质 */
            mCurrInvoiceType = INVOICE_PAGER;
            mLayoutEmailLayout.setVisibility(View.GONE);
            mLayoutPhoneLayout.setVisibility(View.VISIBLE);

        } else {                                                /* 电子 */
            mCurrInvoiceType = INVOICE_ELECTRONIC;
            mLayoutEmailLayout.setVisibility(View.VISIBLE);
            mLayoutPhoneLayout.setVisibility(View.GONE);
        }
    }

    private void setRgType() {
        mRgTypeLayout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                changeTypeView(checkedId);
            }
        });
        if (mEntity != null && mEntity.getInvoice_type() == INVOICE_ELECTRONIC) {
            mRgTypeLayout.check(R.id.rb_invoice_personal_electronic);
        }
    }

    @OnClick({R.id.bt_personalinvoice_save})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_personalinvoice_save:
                requestNetword();
                break;

            default:
                break;
        }
    }

    private void requestNetword() {
        if (checkNull())                // 判断"信息"有没有输入完整
            return;

        setData();
    }

    /**
     * 判断"信息"有没有输入完整
     * @return
     */
    private boolean checkNull() {
        if (Utils.etIsNull(mEtName)) {
            ToastUtils.showToast(Utils.getString(R.string.info_no_null));
            return true;
        }

        if (mCurrInvoiceType == INVOICE_ELECTRONIC) {   /* 电子 */
            if (Utils.etIsNull(mEtEmail)) {
                ToastUtils.showToast(Utils.getString(R.string.info_no_null));
                return true;
            }
        } else {                                        /* 纸质 */
            if (Utils.etIsNull(mEtPhone)) {
                ToastUtils.showToast(Utils.getString(R.string.info_no_null));
                return true;
            }
        }
        return false;
    }

    private void setData() {
        int invoice_id = -1;
        if (mEntity != null) {
            invoice_id = mEntity.getInvoice_id();
        }
        mEntity = new InvoiceBean.DataEntity();
        if (invoice_id != -1) {
            mEntity.setInvoice_id(invoice_id);
        }

        mEntity.setUser_id(GlobalData.getUser_id());
        mEntity.setBelong(INVOICE_PERSONAL);
        mEntity.setInvoice_type(mCurrInvoiceType);
        mEntity.setName(mEtName.getText().toString().trim());
        if (mCurrInvoiceType == INVOICE_ELECTRONIC) {
            mEntity.setEmail(mEtEmail.getText().toString().trim());
        } else {
            mEntity.setTel(mEtPhone.getText().toString().trim());
        }
        String url;
        if (invoice_id == -1) {             /* 添加 */

            url = UrlSet.addInvoice;
        } else {                            /* 修改 */
            mEntity.isEditInvoice = true;
            url = UrlSet.editInvoice;
        }

        mPresenter.addOrEditInvoice(url, mEntity, null);
    }




    @Override
    public void onSuccess(String msg) {
        ToastUtils.showToast(msg);
        finish();
    }


    @Override
    public void showLoading() {
        Utils.uProgressDialog(mContext);
    }

    @Override
    public void hideLoading() {
        Utils.dismissProgress();
    }
}

package com.geli.m.mvp.home.mine_fragment.invoice_activity.invoicetype_activity.addoreditinvoice_activity.unit_fragment;

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
import com.geli.m.select.SelectPhotoFragment;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import java.util.ArrayList;
import java.util.List;

import static com.geli.m.BuildConfig.GL_IMAGE_URL;
import static com.geli.m.config.Constant.INTENT_INVOICE_DATA;
import static com.geli.m.config.Constant.INVOICE_ELECTRONIC;
import static com.geli.m.config.Constant.INVOICE_PAGER;
import static com.geli.m.config.Constant.INVOICE_UNIT;
import static com.geli.m.config.Constant.INVOICE_UNIT_COMMON;
import static com.geli.m.config.Constant.INVOICE_UNIT_SPECIAL;

/**
 * Created by Steam_l on 2018/1/8.
 *
 * 单位发票
 */
public class UnitFragment extends MVPFragment<AddOrEditInvoicePresenterImpl> implements BaseView, View.OnClickListener {

    /** 专用发票 -- 布局 */
    @BindView(R.id.ll_unitinvoice_vatordinary_email_layout)
    LinearLayout mLayoutEmail;
    /** 普通发票 -- 布局 */
    @BindView(R.id.ll_unitinvoice_vatdedicated_orther_layout)
    LinearLayout mLayoutOrtherLayout;

    /** 专用/普通 发票 */
    @BindView(R.id.rg_unitinvoice_type_layout)
    RadioGroup mRgTypeLayout;
    /** 发票载体类型 -- 电子/纸质 */
    @BindView(R.id.rg_unitinvoice_vatordinary_layout)
    RadioGroup mRgInvoiceTypeLayout;

    /** 单位固话 */
    @BindView(R.id.et_unitinvoice_unitphone)
    EditText mEtUnitPhone;
    /** 单位名称/个人名称 */
    @BindView(R.id.et_unitinvoice_name)
    EditText mEtName;
    /** 单位地址/个人地址 */
    @BindView(R.id.et_unitinvoice_unitaddress)
    EditText mEtAddress;
    /** 单位开户行名称 */
    @BindView(R.id.et_unitinvoice_unitbankname)
    EditText mEtUnitBankName;
    /** 单位开户行账号 */
    @BindView(R.id.et_unitinvoice_unitbanknumber)
    EditText mEtUnitBankNumber;
    /** 单位税号 */
    @BindView(R.id.et_unitinvoice_unitnumber)
    EditText mEtUnitNumber;
    /** 邮政编码 */
    @BindView(R.id.et_unitinvoice_zipcode)
    EditText mEtZipCode;
    /** 电子邮箱 */
    @BindView(R.id.et_unitinvoice_email)
    EditText mEtEmail;

    /** 当前发票类型 1:专用; 2:普通*/
    private int mCurrType = 1;
    /** 当前发票载体类型 1:电子; 2:纸质*/
    private int mCurrInvoiceType = 2;

    private InvoiceBean.DataEntity mEntity;
    private SelectPhotoFragment mSelectImgFragment;


    public static UnitFragment newInstance(Bundle args) {
        UnitFragment unitFragment = new UnitFragment();
        unitFragment.setArguments(args);
        return unitFragment;
    }

    @Override
    public int getResId() {
        return R.layout.fragment_addoredit_invoice_unit;
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
        changeVatView(R.id.rb_unitinvoice_vatdedicated);
    }

    @Override
    protected void initEvent() {
        setRgTypeListener();                // 设置: 专用发票/普通发票  的选择监听
        setRgInvoiceTypeListener();         // 设置: 发票载体类型--电子发票/纸质发票  的选择监听
        initSelect();                       // 初始选择
    }

    private void setView() {
        List<LocalMedia> list = new ArrayList();
        if (mEntity != null) {
            if (mEntity.getBelong() == INVOICE_UNIT) {
                setUnitTypeView(list);
            }
        }

        mSelectImgFragment = SelectPhotoFragment.newInstance(2, 3, list, 10);
        getChildFragmentManager().beginTransaction().replace(R.id.fl_unitinvoice_selectimg, mSelectImgFragment).commit();
    }

    /**
     * 设置"单位发票"的值
     * @param list
     */
    private void setUnitTypeView(List<LocalMedia> list) {
        setUnitView();                           // 单位发票通用的 -- 电子、纸质

        if (mEntity.getType() == INVOICE_UNIT_SPECIAL) {             /* 单位专用 */
            setUnitZYView();
        } else {                                                /* 单位普通 */
            setUnitPTView();
        }

        /* 图片链接 */
        String[] split = mEntity.getLicense().split(";");
        for (String url : split) {
            LocalMedia media = new LocalMedia();
            if (!url.startsWith(GL_IMAGE_URL)) {
                media.setPath(GL_IMAGE_URL + url);
            }
            list.add(media);
        }
    }

    /**
     * 单位发票通用的 -- 电子、纸质
     */
    private void setUnitView() {
        mEtName.setText(mEntity.getName());
        mEtName.setSelection(mEtName.getText().toString().length());
        mEtUnitNumber.setText(mEntity.getDuty_paragraph());
        mEtUnitNumber.setSelection(mEtUnitNumber.getText().toString().length());
        mEtAddress.setText(mEntity.getAddress());
        mEtAddress.setSelection(mEtAddress.getText().toString().length());
    }


    /**
     * 单位专用发票 -- 需要设置数据的控件
     */
    private void setUnitZYView() {
        mEtUnitBankName.setText(mEntity.getAccount_name());
        mEtUnitBankName.setSelection(mEtUnitBankName.getText().toString().length());
        mEtUnitPhone.setText(mEntity.getTel());
        mEtUnitPhone.setSelection(mEtUnitPhone.getText().toString().length());
        mEtZipCode.setText(mEntity.getCode());
        mEtZipCode.setSelection(mEtZipCode.getText().toString().length());
        mEtUnitBankNumber.setText(mEntity.getAccount());
        mEtUnitBankNumber.setSelection(mEtUnitBankNumber.getText().toString().length());
    }

    /**
     * 单位普通发票 -- 需要设置数据的控件
     */
    private void setUnitPTView() {
        if (mEntity.getInvoice_type() == INVOICE_ELECTRONIC) {              /* 电子 */
            mEtEmail.setText(mEntity.getEmail());
            mEtEmail.setSelection(mEtEmail.getText().toString().length());
        }
    }

    /**
     * 设置: 专用发票/普通发票  的选择监听
     */
    private void setRgTypeListener() {
        mRgTypeLayout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                changeVatView(checkedId);
            }
        });
    }

    /**
     * 设置: 发票载体类型--电子发票/纸质发票  的选择监听
     */
    private void setRgInvoiceTypeListener() {
        mRgInvoiceTypeLayout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rb_unitinvoice_pagerinvoice) {        /* 纸质 */
                    mCurrInvoiceType = INVOICE_PAGER;
                    mLayoutEmail.setVisibility(View.GONE);
                } else {                                                    /* 电子 */
                    mLayoutEmail.setVisibility(View.VISIBLE);
                    mCurrInvoiceType = INVOICE_ELECTRONIC;
                }
            }
        });
    }

    /**
     * 初始化选择
     */
    private void initSelect() {
        if (mEntity != null && mEntity.getType() == INVOICE_UNIT_COMMON) {     /* 发票类型 1:专用; 2:普通*/
            mRgTypeLayout.check(R.id.rb_unitinvoice_vatordinary);
            if (mEntity.getInvoice_type() == INVOICE_ELECTRONIC) {         /* 发票载体类型 1:电子; 2:纸质*/
                mRgInvoiceTypeLayout.check(R.id.rb_unitinvoice_electronic);
            }
        }
    }

    /**
     * 专用发票/普通发票  的选择修改后的处理
     * @param id
     */
    private void changeVatView(int id) {
        if (id == R.id.rb_unitinvoice_vatdedicated) {       /* 专用 */
            mCurrType = INVOICE_UNIT_SPECIAL;
            mRgInvoiceTypeLayout.check(R.id.rb_unitinvoice_pagerinvoice);
            mLayoutEmail.setVisibility(View.GONE);
            mRgInvoiceTypeLayout.setVisibility(View.GONE);
            mLayoutOrtherLayout.setVisibility(View.VISIBLE);

        } else {                                            /* 普通 */
            mCurrType = INVOICE_UNIT_COMMON;
            mLayoutEmail.setVisibility(View.GONE);
            mRgInvoiceTypeLayout.setVisibility(View.VISIBLE);
            mLayoutOrtherLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected AddOrEditInvoicePresenterImpl createPresent() {
        return new AddOrEditInvoicePresenterImpl(this);
    }

    @Override
    public void onSuccess(String msg) {
        ToastUtils.showToast(msg);
        PictureFileUtils.deleteCacheDirFile(mContext);
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

    @OnClick({R.id.bt_unitinvoice_save})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_unitinvoice_save:      /* 保存 */
                requestNetwork();
                break;

            default:
                break;
        }
    }


    /**
     * 保存数据
     */
    private void requestNetwork() {

        List<LocalMedia> photoModelList = mSelectImgFragment.getPhotoModelList();
        if (checkNull(photoModelList))                // 判断"信息"有没有输入完整
            return;
        saveData(photoModelList);
    }

    /**
     * 判断"信息"有没有输入完整
     * @return
     * @param photoModelList
     */
    private boolean checkNull(List<LocalMedia> photoModelList) {
        if (Utils.etIsNull(mEtUnitNumber) || Utils.etIsNull(mEtAddress) || Utils.etIsNull(mEtName)) {
            ToastUtils.showToast(Utils.getString(R.string.info_no_null));
            return true;
        }

        if (mCurrType == INVOICE_UNIT_SPECIAL) {           /* 单位专用 */
            if (Utils.etIsNull(mEtZipCode) || Utils.etIsNull(mEtUnitBankNumber) ||
                    Utils.etIsNull(mEtUnitBankName) || Utils.etIsNull(mEtUnitPhone)) {
                ShowSingleToast.showToast(mContext, Utils.getString(R.string.info_no_null));
                return true;
            }
        } else {                                       /* 单位普通 */
            if (mCurrInvoiceType == INVOICE_ELECTRONIC && Utils.etIsNull(mEtEmail)) {       // 电子发票
                ShowSingleToast.showToast(mContext, Utils.getString(R.string.info_no_null));
                return true;
            }
        }

        if (photoModelList.size() == 0) {
            ShowSingleToast.showToast(mContext, Utils.getString(R.string.please_select_img));
            return true;
        }
        return false;
    }


    /**
     * 保存数据
     * @param photoModelList
     */
    private void saveData(List<LocalMedia> photoModelList) {
        int invoice_id = -1;
        if (mEntity != null) {
            invoice_id = mEntity.getInvoice_id();
        }

        mEntity = new InvoiceBean.DataEntity();
        if (invoice_id != -1) {
            mEntity.setInvoice_id(invoice_id);
        }

        mEntity.setUser_id(GlobalData.getUser_id());
        mEntity.setBelong(INVOICE_UNIT);                     // 1：单位 2：个人
        mEntity.setType(mCurrType);
        mEntity.setInvoice_type(mCurrInvoiceType);
        mEntity.setName(mEtName.getText().toString().trim());
        mEntity.setDuty_paragraph(mEtUnitNumber.getText().toString().trim());
        mEntity.setAddress(mEtAddress.getText().toString().trim());

        if (mCurrType == INVOICE_UNIT_SPECIAL) {                 // 专用发票
            mEntity.setAccount_name(mEtUnitBankName.getText().toString().trim());
            mEntity.setAccount(mEtUnitBankNumber.getText().toString().trim());
            mEntity.setCode(mEtZipCode.getText().toString().trim());
            mEntity.setTel(mEtUnitPhone.getText().toString().trim());
        } else {
            if (mCurrInvoiceType == INVOICE_ELECTRONIC) {   // 电子发票
                mEntity.setEmail(mEtEmail.getText().toString().trim());
            }
        }

        String url;
        if (invoice_id == -1) {                     /* 添加 */
            url = UrlSet.addInvoice;
        } else {                                    /* 修改 */
            mEntity.isEditInvoice = true;
            url = UrlSet.editInvoice;
        }

        mPresenter.addOrEditInvoice(url, mEntity, photoModelList);
    }
}

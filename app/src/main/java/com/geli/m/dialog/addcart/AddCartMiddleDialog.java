package com.geli.m.dialog.addcart;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.bean.CartBean;
import com.geli.m.bean.ShopInfoBean;
import com.geli.m.bean.SkuAttrBean;
import com.geli.m.bean.SpecifiBean;
import com.geli.m.dialog.addcart.view.OnSkuListener;
import com.geli.m.dialog.addcart.view.SkuSelectScrollView;
import com.geli.m.dialog.base.BaseDialogFragment;
import com.geli.m.utils.KeyBoardUtils;
import com.geli.m.utils.StringUtils;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steam_l on 2018/1/5.
 * 添加购物车弹窗
 */

@SuppressLint("ValidFragment")
public class AddCartMiddleDialog extends BaseDialogFragment implements View.OnClickListener, OnSkuListener {


    public static String ARG_SPECIFI = "arg_specifi";
    public static String ARG_BEAN = "arg_bean";

    @BindView(R.id.dialog_rootview)
    RelativeLayout mDialogRootview;
    @BindView(R.id.dialog_contentview)
    LinearLayout mDialogContentview;


    @BindView(R.id.tv_goodsName_AddCartMiddleDialog)
    TextView mTvGoodsName;
    @BindView(R.id.iv_close_AddCartMiddleDialog)
    ImageView mIvClose;
    @BindView(R.id.sku_specification_AddCartMiddleDialog)
    SkuSelectScrollView mSkuSpecification;
    @BindView(R.id.iv_add_AddCartMiddleDialog)
    ImageView mIvAdd;
    @BindView(R.id.et_number_AddCartMiddleDialog)
    EditText mEtNumber;
    @BindView(R.id.iv_cut_AddCartMiddleDialog)
    ImageView mIvCut;
    @BindView(R.id.tv_salesVolume_AddCartMiddleDialog)
    TextView mTvSalesVolume;
    @BindView(R.id.tv_stock_AddCartMiddleDialog)
    TextView mTvStock;
    @BindView(R.id.tv_toast_AddCartMiddleDialog)
    TextView mTvToast;
    @BindView(R.id.tv_price_AddCartMiddleDialog)
    TextView mTvPrice;
    @BindView(R.id.tv_addCart_AddCartMiddleDialog)
    TextView mTvAddCart;

    AddCartMiddleListener mAddCartListener;
    private SpecifiBean.DataEntity mCurrSpecifi;
    private LayoutInflater mInflater;
    private ShopInfoBean.DataEntity.GoodsResEntity mCurrBean;
    private String mCurrPrice;
    private int maxNumber;
    private List<SpecifiBean.DataEntity.GoodsSkuEntity.TieredPri> mTieredPri;

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            Window window = dialog.getWindow();
            window.setLayout((int) (dm.widthPixels * 0.85), ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    @SuppressLint("ValidFragment")
    public AddCartMiddleDialog(AddCartMiddleListener listener) {
        mAddCartListener = listener;
    }

    public static AddCartMiddleDialog newInstance(SpecifiBean.DataEntity data,
                                                  ShopInfoBean.DataEntity.GoodsResEntity goodsResEntity,
                                                  AddCartMiddleListener listener) {
        AddCartMiddleDialog addCartDialog = new AddCartMiddleDialog(listener);
        Bundle args = new Bundle();
        args.putParcelable(ARG_SPECIFI, data);
        args.putParcelable(ARG_BEAN, goodsResEntity);
        addCartDialog.setArguments(args);
        return addCartDialog;
    }

    @Override
    protected int getResId() {
        return R.layout.dialog_add_cart_middle;
    }

    @Override
    protected void init() {
        super.init();
        Bundle arguments = getArguments();
        mCurrSpecifi = arguments.getParcelable(ARG_SPECIFI);
        mCurrBean = arguments.getParcelable(ARG_BEAN);
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    protected void initData() {
        mTvPrice.setVisibility(View.INVISIBLE);
        mTvStock.setVisibility(View.INVISIBLE);
        mTvToast.setVisibility(View.INVISIBLE);
        mTvGoodsName.setText(mCurrBean.getGoods_name());

        mEtNumber.setText(mCurrBean.getOrigin_number() + "");
        mEtNumber.setSelection(mEtNumber.getText().length());

        mSkuSpecification.setListener(this);
        mSkuSpecification.setSkuList(mCurrSpecifi);

        KeyBoardUtils.disableCopyAndPaste(getEt());

        setEditListener();
        setNumber(mCurrBean.getOrigin_number() + "");
    }

    @Override
    protected void initEvent() {
    }

    @Override
    protected EditText getEt() {
        return mEtNumber;
    }


    @OnClick({R.id.iv_close_AddCartMiddleDialog,
            R.id.iv_add_AddCartMiddleDialog,
            R.id.iv_cut_AddCartMiddleDialog,
            R.id.tv_addCart_AddCartMiddleDialog})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close_AddCartMiddleDialog:                 /* 取消 */
                dismiss();
                break;

            case R.id.iv_add_AddCartMiddleDialog:                   /* 数量 + */
                addNumber();
                break;

            case R.id.iv_cut_AddCartMiddleDialog:                   /* 数量 - */
                cutNumber();
                break;

            case R.id.tv_addCart_AddCartMiddleDialog:               /* 加入购物车 */
                addCart();
                break;
        }
    }



    /**
     * 数量 +
     */
    private void addNumber() {
        Integer integer = Integer.valueOf(mEtNumber.getText().toString().trim());
        String number = ++integer + "";
        setNumber(number);
    }

    /**
     * 数量 -
     */
    private void cutNumber() {
        Integer integer = Integer.valueOf(mEtNumber.getText().toString().trim());
        String number = (--integer <= 0 ? 1 : integer) + "";
        setNumber(number);
    }


    /**
     * 设置 数量编辑框 和 当前价格
     * @param number
     */
    private void setNumber(String number){
        if(StringUtils.isEmpty(number)){
            return;
        }
        String price = AddCartPriceUtils.setCurrPrice(mTieredPri, number);
        if (StringUtils.isNotEmpty(price)) {
            mCurrPrice = price;
        }
        mEtNumber.setText(number);
        mEtNumber.setSelection(mEtNumber.getText().length());
    }


    /**
     * 编辑框的监听事件
     */
    private void setEditListener(){
        mEtNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (StringUtils.isEmpty(s.toString().trim()) || StringUtils.isEmpty(mCurrPrice)) {
                    return;
                }

                // 最大购买量
                if (Integer.valueOf(s.toString()) > maxNumber) {
                    if(maxNumber == 0){
                        ToastUtils.showToast(mContext, Utils.getString(R.string.beyond_zero));
                    }else {
                        ToastUtils.showToast(mContext, Utils.getString(R.string.beyond_maxnumber));
                    }
                    mEtNumber.setText(maxNumber + "");
                }

                // 设置价格
                String price = AddCartPriceUtils.setCurrPrice(mTieredPri, mEtNumber.getText().toString().trim());
                if (!TextUtils.isEmpty(price)) {
                    mCurrPrice = price;
                }
                mEtNumber.setSelection(mEtNumber.getText().length());
                mTvPrice.setText(Utils.getString(R.string.overseas_list_money,
                        Utils.getFormatDoubleTwoDecimalPlaces(
                                Utils.mul(Double.valueOf(mEtNumber.getText().toString()),
                                        Double.valueOf(mCurrPrice)), 2)));

            }
        });
    }


    private void addCart() {
        String number = mEtNumber.getText().toString().trim();

        if (StringUtils.isEmpty(number) || Integer.valueOf(number) < mCurrBean.getOrigin_number()) {
            ToastUtils.showToast(mContext, Utils.getString(R.string.setfrom_pieces, mCurrBean.getOrigin_number() + "", mCurrBean.getGoods_unit()));
            mEtNumber.setText(mCurrBean.getOrigin_number() + "");
            return;
        }

        SpecifiBean.DataEntity.GoodsSkuEntity selectSku = mSkuSpecification.getSelectedSku();
        if(selectSku == null){
            String unSelectName = mSkuSpecification.getFirstUnelectedAttributeName();
            ToastUtils.showToast(mContext, Utils.getString(R.string.please_select, unSelectName));

        }else {
            if(maxNumber == 0){
                ToastUtils.showToast(mContext, Utils.getString(R.string.beyond_zero));
                return;
            }

            List<CartBean.DataEntity.CartListEntity.SpecificationEntity> list = new ArrayList<>();
            for(SkuAttrBean skuAttrBean : selectSku.getSkuAttrBeans()){
                list.add(new CartBean.DataEntity.CartListEntity.SpecificationEntity(
                        skuAttrBean.getSpecName(),
                        skuAttrBean.getAttributeName()));
            }

            mAddCartListener.addCart(new Gson().toJson(list), selectSku.getSku_id() + "", number);
            dismiss();
        }
    }

    public interface AddCartMiddleListener {
        void addCart(String json, String sku, String sku_id);
    }



    /*------------------------------ OnSkuListener ---------------------------------------*/
    @Override
    public void onUnselected(SkuAttrBean unselectedAttribute) {

    }

    @Override
    public void onSelect(SkuAttrBean selectAttribute) {

    }

    // 选中的商品
    @Override
    public void onSkuSelected(SpecifiBean.DataEntity.GoodsSkuEntity sku) {
        if(sku != null){
            setView(sku);
        }
    }

    private void setView(SpecifiBean.DataEntity.GoodsSkuEntity sku) {
        if (mTvPrice.getVisibility() == View.INVISIBLE) {
            mTvPrice.setVisibility(View.VISIBLE);
        }
        if (mTvStock.getVisibility() == View.INVISIBLE) {
            mTvStock.setVisibility(View.VISIBLE);
        }
        if (mTvToast.getVisibility() == View.INVISIBLE) {
            mTvToast.setVisibility(View.VISIBLE);
        }


        // 设置价格 -- 设置数量
        mCurrPrice = sku.getPrice();
        mTieredPri = sku.getTieredPri();
        maxNumber = sku.getInventory();
        String number = mEtNumber.getText().toString().trim();
        if (TextUtils.isEmpty(number) || number.equals("0")) {
            number = "1";
            mEtNumber.setText("1");
        }

        if(maxNumber == 0){
            number = "0";
            mEtNumber.setText("0");
        }

        String price = AddCartPriceUtils.setCurrPrice(mTieredPri, number);
        if (!TextUtils.isEmpty(price) && maxNumber != 0) {
            mCurrPrice = price;
        }

        // 设置提示
        String tiered_content = sku.getTiered_content();
        if (!TextUtils.isEmpty(tiered_content)) {
            mTvToast.setText(tiered_content);
        } else {
            mTvToast.setText("");
        }

        // 设置库存 -- 设置价格
        String unit =  StringUtils.isEmpty(mCurrBean.getGoods_unit()) ? "" : mCurrBean.getGoods_unit();
        mTvStock.setText(Utils.getString(R.string.popup_stock, maxNumber + "", unit));
        mTvPrice.setText(Utils.getString(R.string.overseas_list_money,
                Utils.getFormatDoubleTwoDecimalPlaces(
                        Utils.mul(Double.valueOf(mCurrPrice),
                                Double.valueOf(mEtNumber.getText().toString())), 2)));
    }
}
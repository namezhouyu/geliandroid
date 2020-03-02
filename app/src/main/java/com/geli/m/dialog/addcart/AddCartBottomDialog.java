package com.geli.m.dialog.addcart;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.bean.CartBean;
import com.geli.m.bean.SkuAttrBean;
import com.geli.m.bean.SpecifiBean;
import com.geli.m.dialog.addcart.view.OnSkuListener;
import com.geli.m.dialog.addcart.view.SkuSelectScrollView;
import com.geli.m.dialog.base.BaseDialogFragment;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.KeyBoardUtils;
import com.geli.m.utils.StringUtils;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Steam_l on 2018/1/6.
 */
@SuppressLint("ValidFragment")
public class AddCartBottomDialog extends BaseDialogFragment implements View.OnClickListener, OnSkuListener {

    @BindView(R.id.dialog_contentview)
    LinearLayout mDialogContentview;
    @BindView(R.id.dialog_rootview)
    RelativeLayout mDialogRootview;

    /** 商品数据 */
    public static String DIALOG_BEAN = "dialog_bean";
    /** 规格数据 */
    public static String DIALOG_SPECIFIC = "dialog_specific";
    /** 是否广播 */
    public static String DIALOG_IS_RADIO = "dialog_is_radio";

    @BindView(R.id.iv_img_AddCartBottomDialog)
    ImageView mIvImg;
    @BindView(R.id.iv_close_AddCartBottomDialog)
    ImageView mIvClose;
    @BindView(R.id.tv_stock_AddCartBottomDialog)
    TextView mTvStock;
    @BindView(R.id.tv_specifi_AddCartBottomDialog)
    TextView mTvSpecifi;
    @BindView(R.id.tv_price_AddCartBottomDialog)
    TextView mTvPrice;
    @BindView(R.id.sku_specification_AddCartBottomDialog)
    SkuSelectScrollView mSkuSpecification;
    @BindView(R.id.iv_add_AddCartBottomDialog)
    ImageView mIvAdd;
    @BindView(R.id.et_number_AddCartBottomDialog)
    EditText mEtNumber;
    @BindView(R.id.iv_cut_AddCartBottomDialog)
    ImageView mIvCut;
    @BindView(R.id.tv_toast_AddCartBottomDialog)
    TextView mTvToast;
    @BindView(R.id.bt_determine_AddCartBottomDialog)
    Button mBtDetermine;


    /** 商品数据 */
    private CartBean.DataEntity.CartListEntity mCurr_bean;
    /** 规格数据 -- 选中的 */
    private SpecifiBean.DataEntity mCurrSpecifi;
    /** 是否广播 */
    private boolean mIsRadio;

    /** 当前规格值 */
    private String mCurrSpecifiValue;
    /** 当前价格 */
    private String mCurrPrice;

    /** 放规格的 */
    Map<String, String> SkuMap = new HashMap();

    private List<SpecifiBean.DataEntity.GoodsSkuEntity.TieredPri> mTieredPri;


    /** 用于"选择规格"之后，调用者回调 */
    private ResListener mListener;
    private LayoutInflater mInflater;

    private int maxNumber = 1;
    boolean isSelect = false;
    boolean isFirst = true;

    private String mButtonString = "";

    @Override
    protected int getResId() {
        return R.layout.dialog_addcart_bottom;
    }

    public static AddCartBottomDialog newInstance(SpecifiBean.DataEntity bean,
                                                  CartBean.DataEntity.CartListEntity editBean,
                                                  ResListener listener) {
        return newInstance(bean, editBean, listener, true);
    }

    public static AddCartBottomDialog newInstance(SpecifiBean.DataEntity bean,
                                                  CartBean.DataEntity.CartListEntity editBean,
                                                  ResListener listener,
                                                  boolean isRadio) {

        AddCartBottomDialog goodsDetailsAddcartDialog = new AddCartBottomDialog(listener);
        Bundle args = new Bundle();
        args.putParcelable(DIALOG_BEAN, editBean);
        args.putParcelable(DIALOG_SPECIFIC, bean);
        args.putBoolean(DIALOG_IS_RADIO, isRadio);
        goodsDetailsAddcartDialog.setArguments(args);
        return goodsDetailsAddcartDialog;
    }

    public AddCartBottomDialog(ResListener listener) {
        mListener = listener;
    }

    @Override
    protected void init() {
        super.init();
        Bundle arguments = getArguments();
        mCurr_bean = arguments.getParcelable(DIALOG_BEAN);
        mCurrSpecifi = arguments.getParcelable(DIALOG_SPECIFIC);
        mIsRadio = arguments.getBoolean(DIALOG_IS_RADIO);
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    protected void initData() {
        GlideUtils.loadImg(mContext, mCurr_bean.getGoods_thumb(), mIvImg);  // 图片

        mSkuSpecification.setListener(this);
        mSkuSpecification.setSkuList(mCurrSpecifi);
        setView();                                      //  根据购物车商品的信息设置控件

    }

    @Override
    protected void initEvent() {
        setScrollView();                   // 限定窗口只能 小于"屏幕的 0.4"
        setDialogDismissListener();        // 窗口隐藏的时候 -- 关闭键盘
    }

    /**
     * 根据购物车商品的信息设置控件
     */
    private void setView() {
        String value = "";                              // 规格内容
        List<CartBean.DataEntity.CartListEntity.SpecificationEntity> specification =
                mCurr_bean.getSpecification();

        if (specification != null) {                    /* 选择了规格 -- 如从购物车进来的 */
            for (int i = 0; i < specification.size(); i++) {
                value += specification.get(i).getValue() + ";";
            }
            if (StringUtils.isNotEmpty(value)) {           // 不为空
                value = value.substring(0, value.length() - 1);
            }
            mTvSpecifi.setText(value);

            mTvStock.setVisibility(View.VISIBLE);
            mSkuSpecification.setSelectedSkuAndClick(mCurr_bean.getSpecification());

        } else {                                        /* 还没选择规格，将要添加到购物车的 */

            if(mSkuSpecification.isOnlyAttribute()){

            }else {
                value = Utils.getString(R.string.please_select_specifi);
                mTvSpecifi.setText(value);
                mTvPrice.setText(Utils.getString(R.string.overseas_list_money, 0));
                mTvStock.setVisibility(View.INVISIBLE);
            }
        }

        setEditListener();
        mEtNumber.setText(mCurr_bean.getCart_number() + "");

        if (StringUtils.isNotEmpty(mButtonString)) {
            mBtDetermine.setText(mButtonString);
        }
    }

    public void setButtonString(String buttonString) {
        mButtonString = buttonString;
    }

    /**
     * 限定窗口只能 小于"屏幕的 0.4"
     */
    private void setScrollView() {
        mSkuSpecification.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                mSkuSpecification.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int height = mSkuSpecification.getHeight();
                        if (height > mContext.getWindowManager().getDefaultDisplay().getHeight() * 0.4) {
                            LinearLayout.LayoutParams layoutParams =
                                    (LinearLayout.LayoutParams) mSkuSpecification.getLayoutParams();
                            layoutParams.height =
                                    (int) (mContext.getWindowManager().getDefaultDisplay().getHeight() * 0.4);
                            mSkuSpecification.setLayoutParams(layoutParams);
                        }
                    }
                });
            }
        });
    }


    /**
     * 窗口隐藏的时候 -- 关闭键盘
     */
    private void setDialogDismissListener() {
        getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                KeyBoardUtils.closeKeybord(mEtNumber, mContext);
            }
        });
    }


    @Override
    protected EditText getEt() {
        return mEtNumber;
    }

    @OnClick({R.id.iv_close_AddCartBottomDialog, R.id.iv_add_AddCartBottomDialog,
            R.id.iv_cut_AddCartBottomDialog, R.id.bt_determine_AddCartBottomDialog})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close_AddCartBottomDialog:                 /* 关闭按钮 */
                dismiss();
                break;

            case R.id.iv_add_AddCartBottomDialog:                   /* 点击数量加号 */
                clickAdd();
                break;

            case R.id.iv_cut_AddCartBottomDialog:                   /* 点击数量减号 */
                clickCut();
                break;

            case R.id.bt_determine_AddCartBottomDialog:             /* 确定 -- 提交选中的规格、数量 */
                returnRes();
                break;
        }
    }

    /**
     * 确定 -- 提交选中的规格、数量
     */
    public void returnRes() {

        String number = mEtNumber.getText().toString().trim();
        /* 没有数量 || 小于最小购买量 */
        if (StringUtils.isEmpty(number) || Integer.valueOf(number) < mCurr_bean.getOrigin_number()) {
            ToastUtils.showToast(Utils.getString(R.string.setfrom_pieces, mCurr_bean.getOrigin_number() + "", mCurr_bean.getGoods_unit()));
            mEtNumber.setText(mCurr_bean.getOrigin_number() + "");
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

            mCurr_bean.setSku_id(selectSku.getSku_id());
            mCurr_bean.setSpecification(list);
            // 设置数量
            mCurr_bean.setCart_number(Integer.valueOf(mEtNumber.getText().toString().trim()));

            mListener.returnRes(new Gson().toJson(list), mCurr_bean);
            dismiss();
        }
    }


    /**
     * 点击"数量的加号"
     */
    private void clickAdd() {
        Integer integer = Integer.valueOf(mEtNumber.getText().toString().trim());
        String number = ++integer + "";

        setNumber(number);
    }

    /**
     * 点击"数量的减号"
     */
    private void clickCut() {
        Integer integer = Integer.valueOf(mEtNumber.getText().toString().trim());
        String number = (--integer <= 0 ? 1 : integer) + "";

        setNumber(number);
    }

    private void setNumber(String number) {
        if(StringUtils.isEmpty(number)){
            return;
        }
        String tempPrice = AddCartPriceUtils.setCurrPrice(mTieredPri, number + "");
        if (!TextUtils.isEmpty(tempPrice)) {
            mCurrPrice = tempPrice;
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
    /*----------------------------- 被外部调用 ---------------------------------*/

    /**
     * 当前选择的规格值
     *
     * @return
     */
    public String getValue() {
        return mCurrSpecifiValue;
    }


    /**
     * 当前的 价格
     *
     * @return
     */
    public String getPrice() {
        return Utils.getString(R.string.overseas_list_money,
                Utils.getFormatDoubleTwoDecimalPlaces(Utils.mul(Double.valueOf(mCurrPrice),
                        Double.valueOf(mCurr_bean.getCart_number())), 2));
    }

    /*-------------------------------- 接口 --------------------------------*/

    /**
     * 点击"确定"后获取数据
     */
    public interface ResListener {
        /**
         * @param json      选中的规格的json -- 多个属性
         * @param curr_bean
         */
        void returnRes(String json, CartBean.DataEntity.CartListEntity curr_bean);
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
        if (mTvToast.getVisibility() == View.GONE) {
            mTvToast.setVisibility(View.VISIBLE);
        }

        //LogUtils.i("sku:" + sku.toString());

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
        if (!TextUtils.isEmpty(price)) {
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
        String unit =  StringUtils.isEmpty(mCurr_bean.getGoods_unit()) ? "" : mCurr_bean.getGoods_unit();
        mTvStock.setText(Utils.getString(R.string.popup_stock, maxNumber + "", unit));
        mTvPrice.setText(Utils.getString(R.string.overseas_list_money,
                Utils.getFormatDoubleTwoDecimalPlaces(
                        Utils.mul(Double.valueOf(mCurrPrice),
                                Double.valueOf(mEtNumber.getText().toString())), 2)));

        // 设置选中的规格
        String value = "";
        for (int i = 0; i < sku.getSkuAttrBeans().size(); i++) {
            value += sku.getSkuAttrBeans().get(i).getAttributeName() + ";";
        }

        mCurrSpecifiValue = value;
        if (StringUtils.isNotEmpty(mCurrSpecifiValue)) {           // 不为空
            mCurrSpecifiValue = mCurrSpecifiValue.substring(0, mCurrSpecifiValue.length() - 1);
        }

        mTvSpecifi.setText(mCurrSpecifiValue);
    }
}

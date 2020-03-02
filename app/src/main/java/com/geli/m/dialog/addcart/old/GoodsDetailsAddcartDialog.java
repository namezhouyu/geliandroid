package com.geli.m.dialog.addcart.old;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
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
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.bean.CartBean;
import com.geli.m.bean.SpecifiBean;
import com.geli.m.coustomview.SpecificLayout;
import com.geli.m.dialog.base.BaseDialogFragment;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.KeyBoardUtils;
import com.geli.m.utils.LogUtils;
import com.geli.m.utils.ShowSingleToast;
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
 *
 */
@SuppressLint("ValidFragment")
public class GoodsDetailsAddcartDialog extends BaseDialogFragment implements View.OnClickListener {

    /** 包裹"mLlSpecifi"的滑动布局 */
    @BindView(R.id.nsv_dialog_goodsdetails_specifi)
    NestedScrollView mScrollView;
    /** 包裹"具体规格"的布局 */
    @BindView(R.id.ll_dialog_goodsdetails_addcart_specifi)
    LinearLayout mLlSpecifi;

    /** 确定按钮 */
    @BindView(R.id.bt_dialog_goodsdetails_addcart_determine)
    Button mBtDetemine;
    /** 规格上图片 */
    @BindView(R.id.iv_dialog_goodsdetails_addcart_img)
    ImageView mIvImg;
    /** 剩余数量 */
    @BindView(R.id.tv_dialog_goodsdetails_addcart_info_stock)
    TextView mTvInfoStock;
    /** 规格 */
    @BindView(R.id.tv_dialog_goodsdetails_addcart_info_specifi)
    TextView mTvInfoSpecifi;

    /** "数量"减号 */
    @BindView(R.id.iv_goodsdetails_addcart_cut)
    ImageView mIvCut;
    /** "数量"加号 */
    @BindView(R.id.iv_goodsdetails_addcart_add)
    ImageView mIvAdd;
    /** 价格 */
    @BindView(R.id.tv_dialog_goodsdetails_addcart_info_price)
    TextView mTvInfoPrice;
    /** 优惠政策/条件 -- 满xxx */
    @BindView(R.id.tv_dialog_goodsdetails_addcart_info_toast)
    TextView mTvToast;
    /** 要添加到购物车的数量 */
    @BindView(R.id.et_goodsdetails_addcart_number)
    EditText mEtNumber;

    /** 商品数据 */
    public static String DIALOG_BEAN = "dialog_bean";
    /** 规格数据 */
    public static String DIALOG_SPECIFIC = "dialog_specific";
    /** 是否广播 */
    public static String DIALOG_IS_RADIO = "dialog_is_radio";

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

    private List<SpecifiBean.DataEntity.GoodsSkuEntity.TieredPri> mCurrTieredPri;


    /** 用于"选择规格"之后，调用者回调 */
    private ResListener mListener;
    private LayoutInflater mInflater;

    private int maxNumber = 1;
    boolean isSelect = false;
    boolean isFirst = true;

    private String mButtonString = "";

    @Override
    protected int getResId() {
        return R.layout.dialog_goodsdetails_addcart;
    }

    public static GoodsDetailsAddcartDialog newInstance(SpecifiBean.DataEntity bean,
                                                        CartBean.DataEntity.CartListEntity editBean,
                                                        ResListener listener) {
        return newInstance(bean, editBean, listener, true);
    }

    public static GoodsDetailsAddcartDialog newInstance(SpecifiBean.DataEntity bean,
                                                        CartBean.DataEntity.CartListEntity editBean,
                                                        ResListener listener,
                                                        boolean isRadio) {

        GoodsDetailsAddcartDialog goodsDetailsAddcartDialog = new GoodsDetailsAddcartDialog(listener);
        Bundle args = new Bundle();
        args.putParcelable(DIALOG_BEAN, editBean);
        args.putParcelable(DIALOG_SPECIFIC, bean);
        args.putBoolean(DIALOG_IS_RADIO, isRadio);
        goodsDetailsAddcartDialog.setArguments(args);
        return goodsDetailsAddcartDialog;
    }

    public GoodsDetailsAddcartDialog(ResListener listener) {
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
        List<SpecifiBean.DataEntity.GoodsSkuEntity> goods_sku = mCurrSpecifi.getGoods_sku();
        List<SpecifiBean.DataEntity.SpecAttrEntity> spec_attr = mCurrSpecifi.getSpec_attr();
        // LogUtils.printJson(new Gson().toJson(goods_sku), "GoodsSkuEntity");
        // LogUtils.printJson(new Gson().toJson(spec_attr), "SpecAttrEntity");

        setSpecificLayout(goods_sku, spec_attr);        // 设置 -- 属性布局 -- 里面的每一个项
        GlideUtils.loadImg(mContext, mCurr_bean.getGoods_thumb(), mIvImg);  // 图片
        setView();                                      //  根据购物车商品的信息设置控件
    }

    @Override
    protected void initEvent() {
        setScrollView();                   // 限定窗口只能 小于"屏幕的 0.4"
        setDialogDismissListener();        // 窗口隐藏的时候 -- 关闭键盘
    }

    /**
     * 设置 -- 属性布局 -- 里面的每一个项
     *
     * @param goods_sku     所有型号的商品
     * @param spec_attr     所有的属性  -- 可有多个分类
     */
    private void setSpecificLayout(List<SpecifiBean.DataEntity.GoodsSkuEntity> goods_sku, List<SpecifiBean.DataEntity.SpecAttrEntity> spec_attr) {
        mLlSpecifi.removeAllViews();
        for (int i = 0; i < spec_attr.size(); i++) {
            View inflate = mInflater.inflate(R.layout.include_addcart_container, new LinearLayout(mContext), false);
            mLlSpecifi.addView(inflate);
            // 根布局
            SpecificLayout layout = (SpecificLayout) inflate.findViewById(R.id.ll_addcart_container_layout);
            layout.setFlIsRadio(mIsRadio);

            /* 每点击了 */
            layout.setOnChangeListener(new SpecificLayout.OnChangeListener() {
                @Override
                public void change(String res) {
                    changeSpecific(res);
                }
            });
            layout.setData(spec_attr.get(i), mCurr_bean.getSku_id(), goods_sku);
        }
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
                value += specification.get(i).getValue() + "  ";
            }
            mTvInfoSpecifi.setText(value);
            mTvInfoStock.setVisibility(View.VISIBLE);

        } else {                                        /* 还没选择规格，将要添加到购物车的 */
            value = Utils.getString(R.string.please_select_specifi);
            mTvInfoSpecifi.setText(value);
            mTvInfoPrice.setText(Utils.getString(R.string.overseas_list_money, value));
            mTvInfoStock.setVisibility(View.INVISIBLE);
        }
        mEtNumber.setText(mCurr_bean.getCart_number() + "");

        if(StringUtils.isNotEmpty(mButtonString)) {
            mBtDetemine.setText(mButtonString);
        }
    }

    public void setButtonString(String buttonString) {
        mButtonString = buttonString;
    }

    /**
     * 限定窗口只能 小于"屏幕的 0.4"
     */
    private void setScrollView() {
        mScrollView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                mScrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int height = mScrollView.getHeight();
                        if (height > mContext.getWindowManager().getDefaultDisplay().getHeight() * 0.4) {
                            LinearLayout.LayoutParams layoutParams =
                                    (LinearLayout.LayoutParams) mScrollView.getLayoutParams();
                            layoutParams.height =
                                    (int) (mContext.getWindowManager().getDefaultDisplay().getHeight() * 0.4);
                            mScrollView.setLayoutParams(layoutParams);
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

    @OnClick({R.id.iv_dialog_goodsdetails_addcart_close,
            R.id.bt_dialog_goodsdetails_addcart_determine,
            R.id.iv_goodsdetails_addcart_cut,
            R.id.iv_goodsdetails_addcart_add})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_dialog_goodsdetails_addcart_close:             /* 关闭按钮 */
                dismiss();
                break;

            case R.id.bt_dialog_goodsdetails_addcart_determine:         /* 确定 -- 提交选中的规格、数量 */
                returnRes();
                break;

            case R.id.iv_goodsdetails_addcart_cut:                      /* 点击数量减号 */
                clickCut();
                break;

            case R.id.iv_goodsdetails_addcart_add:                      /* 点击数量加号 */
                clickAdd();
                break;

            default:
                break;
        }
    }

    /**
     * 确定 -- 提交选中的规格、数量
     */
    public void returnRes() {

        /* 没有数量 || 小于最小购买量 */
        if (TextUtils.isEmpty(mEtNumber.getText().toString().trim()) ||
                Integer.valueOf(mEtNumber.getText().toString().trim()) < mCurr_bean.getOrigin_number()) {

            ToastUtils.showToast(Utils.getString(R.string.setfrom_pieces, mCurr_bean.getOrigin_number() + "", mCurr_bean.getGoods_unit()));
            mEtNumber.setText(mCurr_bean.getOrigin_number() + "");
            return;
        }

        returnSpecificationEntities();      // 拿到所有的规格属性
    }

    /**
     * 拿到所有的规格属性 -- 使用接口 将数据提交子类
     *
     * @return
     */
    @Nullable
    private void returnSpecificationEntities() {
        String speId = "";                              // 当前选中的规格id
        mCurrSpecifiValue = "";
        List<CartBean.DataEntity.CartListEntity.SpecificationEntity> list = new ArrayList<>();

        // 所有的分类
        for (int i = 0; i < mLlSpecifi.getChildCount(); i++) {
            SpecificLayout layout = (SpecificLayout) mLlSpecifi.getChildAt(i);
            CartBean.DataEntity.CartListEntity.SpecificationEntity entity =
                    new CartBean.DataEntity.CartListEntity.SpecificationEntity();

            if (TextUtils.isEmpty(layout.getValue())) {      // 没有选择分类中某个属性
                ToastUtils.showToast(Utils.getString(R.string.please_select, layout.getName()));
                return ;
            }
            entity.setValue(layout.getValue());

            if (!TextUtils.isEmpty(layout.getValue())) {
                entity.setKey(layout.getName());
                mCurrSpecifiValue += layout.getValue() + "、";
                list.add(entity);
                speId += layout.getResId() + ",";
            }
        }


         if (!TextUtils.isEmpty(mCurrSpecifiValue)) {           // 不为空
            mCurrSpecifiValue = mCurrSpecifiValue.substring(0, mCurrSpecifiValue.length() - 1);
         }

        if (!TextUtils.isEmpty(speId)) {                        // 不为空
            speId = speId.substring(0, speId.length() - 1);
            List<SpecifiBean.DataEntity.GoodsSkuEntity> goods_sku = mCurrSpecifi.getGoods_sku();

            // 循环所有的型号 -- 找到对应的 -- 那个型号
            for (int i = 0; i < goods_sku.size(); i++) {
                SpecifiBean.DataEntity.GoodsSkuEntity goodsSkuEntity = goods_sku.get(i);
                if (goodsSkuEntity.getSku_attr().contains(speId)) {
                    mCurr_bean.setSku_id(goodsSkuEntity.getSku_id());
                }
            }
        } else {
            mCurr_bean.setSku_id(0);
        }

        // 没有就不设置
        if (mCurr_bean.getSku_id() != 0) {
            mCurr_bean.setSpecification(list);
        } else {
            mCurr_bean.setSpecification(null);
        }

        // 设置数量
        mCurr_bean.setCart_number(Integer.valueOf(mEtNumber.getText().toString().trim()));

        // 使用回调
        if (mListener != null) {
            mListener.returnRes(new Gson().toJson(list), mCurr_bean);
        }

        dismiss();
    }


    /**
     * 点击"数量的加号"
     */
    private void clickAdd() {
        Integer integer = Integer.valueOf(mEtNumber.getText().toString().trim());
        String number = ++integer + "";

        String tempPrice = setCurrPrice(mCurrTieredPri, number + "");
        if (!TextUtils.isEmpty(tempPrice)) {
            mCurrPrice = tempPrice;
        }
        mEtNumber.setText(number);
        mEtNumber.setSelection(mEtNumber.getText().length());
    }

    /**
     * 点击"数量的减号"
     */
    private void clickCut() {
        Integer integer = Integer.valueOf(mEtNumber.getText().toString().trim());
        String number = (--integer <= 0 ? 1 : integer) + "";

        String tempPrice = setCurrPrice(mCurrTieredPri, number + "");
        if (!TextUtils.isEmpty(tempPrice)) {
            mCurrPrice = tempPrice;
        }

        mEtNumber.setText(number);
        mEtNumber.setSelection(mEtNumber.getText().length());
    }


    /**
     * 根据数量 -- 设置价格 -- 有些商品达到一定的量是有优惠的
     *
     *      * mTieredPri": [
     * ║             {"key": 10,"value": 90},
     * ║             {"key": 5,"value": 95},
     * ║             {"key": 1,"value": 100}
     * ║         ]
     *
     * @param currTieredPri
     * @param number
     * @return                      返回的值 -- 就是价钱
     */
    public static String setCurrPrice(List<SpecifiBean.DataEntity.GoodsSkuEntity.TieredPri> currTieredPri, String number) {
        String price = "";
        if (currTieredPri != null && currTieredPri.size() > 0) {        //  是否有优惠
            for (SpecifiBean.DataEntity.GoodsSkuEntity.TieredPri tieredPri : currTieredPri) {
                if (Integer.valueOf(number) >= tieredPri.getKey()) {
                    return tieredPri.getValue() + "";
                }
            }
        }
        return price;
    }




    /**
     * 如果一进来就有选择,会优先initData执行
     *
     * @param str
     */
    //@Override
    public void changeSpecific(String str) {
        /** 1:xxx,2:xxx,3:xxx  */
        String res = getSpecific();     // 获取选中的规格
        if (res == null)                // 没有选中的就不用往下走了
            return;

        /*==================规格==================*/
        List<SpecifiBean.DataEntity.GoodsSkuEntity> goods_sku = mCurrSpecifi.getGoods_sku();
        SkuMap.clear();
        setSkuMap(res, goods_sku);


        /*==================规格==================*/
        for (int i = 0; i < goods_sku.size(); i++) {
            SpecifiBean.DataEntity.GoodsSkuEntity goodsSkuEntity = goods_sku.get(i);
            if (goodsSkuEntity.getSku_attr().equals("{" + res + "}")) {
                if (mTvInfoStock.getVisibility() == View.INVISIBLE) {
                    mTvInfoStock.setVisibility(View.VISIBLE);
                }
                mTvInfoStock.setText(Utils.getString(R.string.remaining_pieces, goodsSkuEntity.getInventory() + "", mCurr_bean.getGoods_unit()));
                String number = mEtNumber.getText().toString().trim();
                if (TextUtils.isEmpty(number)) {
                    number = "1";
                    mEtNumber.setText("1");
                }
                int curr_number = Integer.valueOf(number);
                mCurrPrice = goodsSkuEntity.getPrice();
                mCurrTieredPri = goodsSkuEntity.getTieredPri();
                String price = setCurrPrice(mCurrTieredPri, curr_number + "");
                if (!TextUtils.isEmpty(price)) {
                    mCurrPrice = price;
                }
                mTvInfoPrice.setText(Utils.getString(R.string.overseas_list_money, Utils.getFormatDoubleTwoDecimalPlaces(Utils.mul(Double.valueOf(mCurrPrice), Double.valueOf(mEtNumber.getText().toString())), 2)));
                maxNumber = goodsSkuEntity.getInventory();
                String tiered_content = goodsSkuEntity.getTiered_content();
                mTvToast.setText(tiered_content);
                if (!isFirst) {
                    mEtNumber.setText(mEtNumber.getText().toString());//第二次的时候触发,避免数量超出剩余
                }
            }
        }

        if (isFirst) {
            mEtNumber.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (TextUtils.isEmpty(s)) {
                        return;
                    }
                    if (isSelect) {
                        if (!TextUtils.isEmpty(mCurrPrice)) {
                            //                            if (Integer.valueOf(s.toString()) < mCurr_bean.getOrigin_number()) {
                            //                                ShowSingleToast.showToast(mContext, Utils.getString(R.string.setfrom_pieces, mCurr_bean.getOrigin_number() + "", mCurr_bean.getGoods_unit()));
                            //                                mEtNumber.setText(mCurr_bean.getOrigin_number() + "");
                            //                            }

                            if (Integer.valueOf(s.toString()) > maxNumber) {
                                ShowSingleToast.showToast(mContext, Utils.getString(R.string.beyond_maxnumber));
                                mEtNumber.setText(maxNumber + "");
                            }
                            String price = setCurrPrice(mCurrTieredPri, mEtNumber.getText().toString().trim());
                            if (!TextUtils.isEmpty(price)) {
                                mCurrPrice = price;
                            }
                            mEtNumber.setSelection(mEtNumber.getText().length());
                            mTvInfoPrice.setText(Utils.getString(R.string.overseas_list_money, Utils.getFormatDoubleTwoDecimalPlaces(Double.valueOf(mCurrPrice), 2)));
                        }
                    }
                }
            });
            mEtNumber.setText(mCurr_bean.getCart_number() + "");
            //            mEtNumber.setSelection(mEtNumber.getText().length());
            isFirst = !isFirst;
        }
    }

    /**
     *
     * @param res           规格 -- 1:xxx,2xxx,3xxx
     * @param goods_sku     商品的所有型号
     */
    private void setSkuMap(String res, List<SpecifiBean.DataEntity.GoodsSkuEntity> goods_sku) {

        List<SpecifiBean.DataEntity.SpecAttrEntity> spec_attr = mCurrSpecifi.getSpec_attr();    // 所有属性

        for (int i = 0; i < spec_attr.size(); i++) {

            /* 某一分类的属性 */
            SpecifiBean.DataEntity.SpecAttrEntity specAttrEntity = spec_attr.get(i);
            for (int j = 0; j < goods_sku.size(); j++) {
                boolean filter = false;
                SpecifiBean.DataEntity.GoodsSkuEntity goodsSkuEntity = goods_sku.get(j);

                // "sku_attr": "{1:30274,2:30273}" -- 所以要去头尾 --  1:30274,2:30273
                String substring = goodsSkuEntity.getSku_attr()
                        .substring(1, goodsSkuEntity.getSku_attr().length() - 1);

                String[] split = substring.trim().split(",");       //
                String[] selectSpe = res.split(",");                // 选中的规格 -- 1:xxx,2:xxx,3:xxx

                spe:
                for (int k = 0; k < selectSpe.length; k++) {
                    String[] spe = selectSpe[k].split(":");
                    if (spe[0].equals(specAttrEntity.getSpec_id() + "")) {      // 同一大分类属性 -- 跳出不处理
                        LogUtils.i("1、continue -- spe[0]:" + spe[0]);
                        continue;
                    }

                    for (int z = 0; z < split.length; z++) {
                        if (split[z].startsWith(spe[0])) {                // 同一分类的
                            LogUtils.i("2、split[z].startsWith(spe[0]: " +
                                    "-- split[z]:" + split[z] + " -- spe[0]:" + spe[0]);
                            if (!split[z].equals(selectSpe[k])) {         // 不是同一种属性 -- 过滤掉，重新循环

                                LogUtils.i("3、!split[z].equals(selectSpe[k]): " +
                                        "-- split[z]:" + split[z] + " -- selectSpe[k]:" + selectSpe[k]);
                                filter = true;
                                break spe;
                            }
                        }
                    }
                }

                /* 不过滤 */
                if (!filter) {
                    for (int n = 0; n < specAttrEntity.getRes().size(); n++) {  // 所有属性
                        for (int z = 0; z < split.length; z++) {
                            String spec_id = specAttrEntity.getSpec_id() + "";
                            String values = specAttrEntity.getRes().get(n).getAttr_id() + "";
                            if (split[z].equals(spec_id + ":" + values)) {

                                LogUtils.i("4、split[z].equals(spec_id + \":\" + values): " +
                                        "-- split[z]:" + split[z] + " -- spec_id+ \":\" + values:" + spec_id + ":" + values);

                                String speValue = SkuMap.get(spec_id);
                                if (TextUtils.isEmpty(speValue)) {
                                    LogUtils.i("5、TextUtils.isEmpty(speValue)");
                                    SkuMap.put(spec_id + "", values);
                                } else {
                                    LogUtils.i("6、!TextUtils.isEmpty(speValue)");
                                    if (!speValue.contains(values)) {
                                        LogUtils.i("7、!speValue.contains(values): speValue:" + speValue +
                                        "values" + values);
                                        SkuMap.put(spec_id + "", speValue + "," + values);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        for(Map.Entry<String, String> set : SkuMap.entrySet()){
            LogUtils.i("SkuMap -- key:" + set.getKey() +
                    " -- value:" + set.getValue());
        }


        // 设置每个分类 数据
        for (int j = 0; j < mLlSpecifi.getChildCount(); j++) {
            SpecificLayout specifi = (SpecificLayout) mLlSpecifi.getChildAt(j);
            specifi.updateSpeState(SkuMap);
        }
    }

    /**
     * 获取属性
     * @return
     */
    @Nullable
    private String getSpecific() {
        /** 1:xxx,2:xxx,3:xxx  */
        String res = "";
        /** 属性： "1kg/包"或"甜" */
        String value = "";
        for (int i = 0; i < mLlSpecifi.getChildCount(); i++) {
            SpecificLayout specific = (SpecificLayout) mLlSpecifi.getChildAt(i);
            res += TextUtils.isEmpty(specific.getResId()) ? "" : specific.getResId() + ",";
            value += specific.getValue() + "  ";
        }
        if (!TextUtils.isEmpty(res)) {
            res = res.substring(0, res.length() - 1);
        }

        if (TextUtils.isEmpty(value.trim())) {          // 判断是否有选择,只有再不是单选情况下会执行
            value = Utils.getString(R.string.please_select_specifi);
            mTvInfoStock.setVisibility(View.INVISIBLE);
            mTvInfoPrice.setText(Utils.getString(R.string.overseas_list_money, value));
            mTvInfoSpecifi.setText(value);
            isSelect = false;
            return null;

        } else {                                        // 有选中属性
            isSelect = true;
            mTvInfoSpecifi.setText(value);
        }
        return res;
    }


    /*----------------------------- 被外部调用 ---------------------------------*/

    /**
     * 当前选择的规格值
     * @return
     */
    public String getValue() {
        return mCurrSpecifiValue;
    }


    /**
     * 当前的 价格
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
         *
         * @param json              选中的规格的json -- 多个属性
         * @param curr_bean
         */
        void returnRes(String json, CartBean.DataEntity.CartListEntity curr_bean);
    }

}

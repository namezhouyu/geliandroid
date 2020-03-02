package com.geli.m.dialog.addcart.old;

import android.app.Dialog;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.bean.CartBean;
import com.geli.m.bean.ShopInfoBean;
import com.geli.m.bean.SpecifiBean;
import com.geli.m.coustomview.SpecificLayout;
import com.geli.m.dialog.base.BaseDialogFragment;
import com.geli.m.utils.KeyBoardUtils;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.Utils;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Steam_l on 2018/1/5.
 * 添加购物车弹窗
 */

public class AddCartDialog extends BaseDialogFragment implements View.OnClickListener, SpecificLayout.OnChangeListener {
    @BindView(R.id.ll_popup_buy_layout)
    LinearLayout ll_specifi;
    @BindView(R.id.et_popup_buy_number)
    EditText et_number;
    @BindView(R.id.tv_popup_buy_name)
    TextView tv_name;
    @BindView(R.id.tv_popup_buy_stock)
    TextView tv_stock;
    @BindView(R.id.tv_popup_buy_price)
    TextView tv_price;
    @BindView(R.id.tv_popup_buy_toast)
    TextView tv_toast;


    public static String ARG_SPECIFI = "arg_specifi";
    public static String ARG_BEAN = "arg_bean";
    private SpecifiBean.DataEntity mCurrSpecifi;
    private LayoutInflater mInflater;
    private ShopInfoBean.DataEntity.GoodsResEntity mCurrBean;
    private String mCurrPrice;
    private int maxNumber;
    private IBinder mWindowToken;
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

    @Override
    protected int getResId() {
        return R.layout.dialog_buy;
    }

    public interface AddCartListener {
        void addCart(String json, String sku, String sku_id);
    }

    AddCartListener mAddCartListener;

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
        tv_price.setVisibility(View.INVISIBLE);
        tv_stock.setVisibility(View.INVISIBLE);
        tv_toast.setVisibility(View.GONE);
        tv_name.setText(mCurrBean.getGoods_name());
        et_number.setText(mCurrBean.getOrigin_number() + "");
        et_number.setSelection(et_number.getText().length());
        mWindowToken = et_number.getWindowToken();
        List<SpecifiBean.DataEntity.GoodsSkuEntity> goods_sku = mCurrSpecifi.getGoods_sku();
        List<SpecifiBean.DataEntity.SpecAttrEntity> spec_attr = mCurrSpecifi.getSpec_attr();
        for (int i = 0; i < spec_attr.size(); i++) {
            View inflate = mInflater.inflate(R.layout.include_addcart_container, new LinearLayout(mContext), false);
            SpecificLayout layout = (SpecificLayout) inflate.findViewById(R.id.ll_addcart_container_layout);
            layout.setOnChangeListener(this);
            layout.setData(spec_attr.get(i), -1, goods_sku);
            ll_specifi.addView(inflate);
        }
        KeyBoardUtils.disableCopyAndPaste(getEt());
    }

    @Override
    protected void initEvent() {
    }

    @Override
    protected EditText getEt() {
        return et_number;
    }


    @OnClick({R.id.iv_popup_buy_cut, R.id.iv_popup_buy_close, R.id.tv_popup_buy_addcart, R.id.iv_popup_buy_add})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_popup_buy_close:
                dismiss();
                break;
            case R.id.tv_popup_buy_addcart:
                addCart();
                break;
            case R.id.iv_popup_buy_add: {
                Integer integer = Integer.valueOf(et_number.getText().toString().trim());
                String number = ++integer + "";
                String price = GoodsDetailsAddcartDialog.setCurrPrice(mTieredPri, number);
                if (!TextUtils.isEmpty(price)) {
                    mCurrPrice = price;
                }
                et_number.setText(number);
                et_number.setSelection(et_number.getText().length());
                break;
            }
            case R.id.iv_popup_buy_cut: {
                Integer integer = Integer.valueOf(et_number.getText().toString().trim());
                String number = (--integer <= 0 ? 1 : integer) + "";
                String price = GoodsDetailsAddcartDialog.setCurrPrice(mTieredPri, number);
                if (!TextUtils.isEmpty(price)) {
                    mCurrPrice = price;
                }
                et_number.setText(number);
                et_number.setSelection(et_number.getText().length());
                break;
            }
            default:
                break;
        }
    }

    private void addCart() {
        if (TextUtils.isEmpty(et_number.getText().toString().trim())
                ||
                Integer.valueOf(et_number.getText().toString().trim()) < mCurrBean.getOrigin_number()) {
            ShowSingleToast.showToast(mContext, Utils.getString(R.string.setfrom_pieces, mCurrBean.getOrigin_number() + "", mCurrBean.getGoods_unit()));
            et_number.setText(mCurrBean.getOrigin_number() + "");
            return;
        }
        String speId = "";
        List<CartBean.DataEntity.CartListEntity.SpecificationEntity> list = new ArrayList<>();
        for (int i = 0; i < ll_specifi.getChildCount(); i++) {
            SpecificLayout layout = (SpecificLayout) ll_specifi.getChildAt(i);
            CartBean.DataEntity.CartListEntity.SpecificationEntity entity = new CartBean.DataEntity.CartListEntity.SpecificationEntity();
            entity.setValue(layout.getValue());
            if (TextUtils.isEmpty(layout.getValue())) {
                ShowSingleToast.showToast(mContext, Utils.getString(R.string.please_select, layout.getName()));
                return;
            }
            entity.setKey(layout.getName());
            list.add(entity);
            speId += layout.getResId() + ",";
        }
        speId = speId.substring(0, speId.length() - 1);

        List<SpecifiBean.DataEntity.GoodsSkuEntity> goods_sku = mCurrSpecifi.getGoods_sku();
        int sku_id = 0;
        for (int i = 0; i < goods_sku.size(); i++) {
            SpecifiBean.DataEntity.GoodsSkuEntity goodsSkuEntity = goods_sku.get(i);
            if (goodsSkuEntity.getSku_attr().contains(speId)) {
                sku_id = goodsSkuEntity.getSku_id();
            }
        }
        mAddCartListener.addCart(new Gson().toJson(list), sku_id + "",
                et_number.getText().toString());
        dismiss();
    }

    public AddCartDialog(AddCartListener listener) {
        mAddCartListener = listener;
    }

    public static AddCartDialog newInstance(SpecifiBean.DataEntity data,
                                            ShopInfoBean.DataEntity.GoodsResEntity goodsResEntity,
                                            AddCartListener listener) {
        AddCartDialog addCartDialog = new AddCartDialog(listener);
        Bundle args = new Bundle();
        args.putParcelable(ARG_SPECIFI, data);
        args.putParcelable(ARG_BEAN, goodsResEntity);
        addCartDialog.setArguments(args);
        return addCartDialog;
    }

    private boolean isFirst = true;
    Map<String, String> SkuMap = new HashMap();

    @Override
    public void change(String str) {
        String res = "";
        for (int i = 0; i < ll_specifi.getChildCount(); i++) {
            SpecificLayout specifi = (SpecificLayout) ll_specifi.getChildAt(i);
            res += TextUtils.isEmpty(specifi.getResId()) ? "" : specifi.getResId() + ",";
        }
        if (!TextUtils.isEmpty(res)) {
            res = res.substring(0, res.length() - 1);
        }
        //处理规格可否选择
        List<SpecifiBean.DataEntity.GoodsSkuEntity> goods_sku = mCurrSpecifi.getGoods_sku();
        SkuMap.clear();
        /*==================规格==================*/
        List<SpecifiBean.DataEntity.SpecAttrEntity> spec_attr = mCurrSpecifi.getSpec_attr();
        for (int i = 0; i < spec_attr.size(); i++) {
            SpecifiBean.DataEntity.SpecAttrEntity specAttrEntity = spec_attr.get(i);
            for (int j = 0; j < goods_sku.size(); j++) {
                boolean filter = false;
                SpecifiBean.DataEntity.GoodsSkuEntity goodsSkuEntity = goods_sku.get(j);
                String substring = goodsSkuEntity.getSku_attr().substring(1, goodsSkuEntity.getSku_attr().length() - 1);
                //1:xxx,2xxx,3xxx
                String[] split = substring.trim().split(",");
                String[] selectSpe = res.split(",");
                spe:
                for (int k = 0; k < selectSpe.length; k++) {
                    String[] spe = selectSpe[k].split(":");
                    if (spe[0].equals(specAttrEntity.getSpec_id() + "")) {
                        continue;
                    }
                    for (int z = 0; z < split.length; z++) {
                        if (split[z].startsWith(spe[0])) {
                            if (!split[z].equals(selectSpe[k])) {
                                filter = true;
                                break spe;
                            }
                        }
                    }
                }
                if (!filter) {
                    for (int n = 0; n < specAttrEntity.getRes().size(); n++) {
                        for (int z = 0; z < split.length; z++) {
                            String spec_id = specAttrEntity.getSpec_id() + "";
                            String values = specAttrEntity.getRes().get(n).getAttr_id() + "";
                            if (split[z].equals(spec_id + ":" + values)) {
                                //                                System.out.println("=============" + specAttrEntity.getSpec_id() + ":" + specAttrEntity.getRes().get(n).getAttr_id() + "===============");
                                String speValue = SkuMap.get(spec_id);
                                if (TextUtils.isEmpty(speValue)) {
                                    SkuMap.put(spec_id + "", values);
                                } else {
                                    if (!speValue.contains(values)) {
                                        SkuMap.put(spec_id + "", speValue + "," + values);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        for (int j = 0; j < ll_specifi.getChildCount(); j++) {
            SpecificLayout specifi = (SpecificLayout) ll_specifi.getChildAt(j);
            specifi.updateSpeState(SkuMap);
        }
        for (int i = 0; i < goods_sku.size(); i++) {
            SpecifiBean.DataEntity.GoodsSkuEntity goodsSkuEntity = goods_sku.get(i);
            if (goodsSkuEntity.getSku_attr().equals("{" + res + "}")) {
                if (tv_price.getVisibility() == View.INVISIBLE) {
                    tv_price.setVisibility(View.VISIBLE);
                }
                if (tv_stock.getVisibility() == View.INVISIBLE) {
                    tv_stock.setVisibility(View.VISIBLE);
                }
                if (tv_toast.getVisibility() == View.GONE) {
                    tv_toast.setVisibility(View.VISIBLE);
                }
                mCurrPrice = goodsSkuEntity.getPrice();
                mTieredPri = goodsSkuEntity.getTieredPri();
                String number = et_number.getText().toString().trim();
                if (TextUtils.isEmpty(number)) {
                    number = "1";
                    et_number.setText("1");
                }
                String price = GoodsDetailsAddcartDialog.setCurrPrice(mTieredPri, number);
                if (!TextUtils.isEmpty(price)) {
                    mCurrPrice = price;
                }
                String tiered_content = goodsSkuEntity.getTiered_content();
                if (!TextUtils.isEmpty(tiered_content)) {
                    tv_toast.setText(tiered_content);
                } else {
                    tv_toast.setText("");
                }
                maxNumber = goodsSkuEntity.getInventory();
                tv_stock.setText(Utils.getString(R.string.popup_stock, maxNumber + "", mCurrBean.getGoods_unit()));
                tv_price.setText(Utils.getString(R.string.overseas_list_money, Utils.getFormatDoubleTwoDecimalPlaces(Utils.mul(Double.valueOf(mCurrPrice), Double.valueOf(et_number.getText().toString())), 2)));
                if (!isFirst) {
                    et_number.setText(et_number.getText().toString());//第二次的时候触发,避免数量超出剩余
                }
            }
        }
        if (isFirst) {
            et_number.addTextChangedListener(new TextWatcher() {
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
                    if (!TextUtils.isEmpty(mCurrPrice)) {
                        //                        if (Integer.valueOf(s.toString()) < mCurrBean.getOrigin_number()) {
                        //                            ShowSingleToast.showToast(mContext, Utils.getString(R.string.setfrom_pieces, mCurrBean.getOrigin_number() + "", mCurrBean.getGoods_unit()));
                        //                            mEtNumber.setText(mCurrBean.getOrigin_number() + "");
                        //                        }
                        if (Integer.valueOf(s.toString()) > maxNumber) {
                            ShowSingleToast.showToast(mContext, Utils.getString(R.string.beyond_maxnumber));
                            et_number.setText(maxNumber + "");
                        }
                        String price = GoodsDetailsAddcartDialog.setCurrPrice(mTieredPri, et_number.getText().toString().trim());
                        if (!TextUtils.isEmpty(price)) {
                            mCurrPrice = price;
                        }
                        et_number.setSelection(et_number.getText().length());
                        tv_price.setText(Utils.getString(R.string.overseas_list_money, Utils.getFormatDoubleTwoDecimalPlaces(Utils.mul(Double.valueOf(et_number.getText().toString()), Double.valueOf(mCurrPrice)), 2)));
                    }
                }
            });
            isFirst = !isFirst;
        }
    }
}
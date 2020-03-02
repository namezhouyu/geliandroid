package com.geli.m.mvp.home.cart_fragment.main;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.CartBean;
import com.geli.m.coustomview.SwipeDeleteItem;
import com.geli.m.dialog.addcart.EditGoodsNumDialog;
import com.geli.m.orther.SwipeDeleteManager;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.ResourceUtil;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Steam_l on 2018/1/4.
 */

public class CartGoodsViewHolder extends BaseViewHolder<CartBean.DataEntity.CartListEntity> {

    Context mContext;
    // CartFragment mFragment;

    /** 整个根目录 */
    private final SwipeDeleteItem mSdi_layout;



    /** 选择按钮 */
    private final LinearLayout mLayout_goodscheck;
    /** 选择按钮 */
    private final CheckBox mCb_goodscheck;
    /** 商品图片 */
    private final ImageView mIv_img;

    /** 编包裹"编辑的规格、数量、价格"的布局 */
    private final RelativeLayout mRl_edit;
    /** 编辑时 -- 加号 */
    private final ImageView mIv_add;
    /** 编辑时 -- 减号 */
    private final ImageView mIv_cut;
    /** 编辑时 -- 完成按钮 */
    private final Button mBt_complete;
    /** 编辑时 -- 数量 */
    public TextView mTv_editNumber;
    /** 编辑时 -- 规格 */
    private final TextView mTv_editspecifi;

    /** 编包裹"非编辑的规格、数量、价格"的布局 */
    private final RelativeLayout mRl_normal;
    /** 编辑图片 -- 画笔图片 */
    private final ImageView mIv_edit;
    /** 商品数量 */
    private final TextView mTv_number;
    /** 商品价格 */
    private final TextView mTv_price;
    /** 商品名字 */
    private final TextView mTv_name;
    /** 商品规格 */
    private final TextView mTv_specifi;

    /** 删除按钮 */
    private final TextView mTv_delete;

    /** "账期"文本 */
    private final TextView mTv_sh;

    /** 暂存数量 */
    private int mTempNumber = 0;
//    public MyTextWatcher mWatcher;
    private String mGoodsNumber;


    private GoodsClickListener mListener;

    public CartGoodsViewHolder(ViewGroup parent, Context context, GoodsClickListener listener) {
        super(parent, R.layout.itemview_cart_goods);
        mContext = context;
        //mFragment = fragment;
        mListener = listener;

        mSdi_layout = $(R.id.sdi_itemview_cart_goods_rootlayout);
        mLayout_goodscheck = $(R.id.layout_itemview_cart_goods_goodscheck);
        mCb_goodscheck = $(R.id.cb_itemview_cart_goods_goodscheck);
        mIv_img = $(R.id.iv_itemview_cart_goods_img);

        mRl_edit = $(R.id.rl_itemview_cart_goods_edit);
        mIv_cut = $(R.id.iv_itemview_cart_goods_edit_numbercut);
        mIv_add = $(R.id.iv_itemview_cart_goods_edit_numberadd);
        mBt_complete = $(R.id.bt_itemview_cart_goods_editcomplete);
        mTv_editNumber = $(R.id.tv_itemview_cart_goods_edit_goodsnumber);
        mTv_editspecifi = $(R.id.tv_itemview_cart_goods_edit_specifi);

        mRl_normal = $(R.id.rl_itemview_cart_goods_normal);
        mIv_edit = $(R.id.iv_itemview_cart_goods_edit);
        mTv_number = $(R.id.tv_itemview_cart_goods_goodsnumber);
        mTv_name = $(R.id.tv_itemview_cart_goods_name);
        mTv_price = $(R.id.tv_itemview_cart_goods_price);
        mTv_specifi = $(R.id.tv_itemview_cart_goods_specifi);

        mTv_sh = $(R.id.tv_itemview_cart_goods_sh);

        mTv_delete = $(R.id.tv_itemview_smallcart_delete);


    }


    @Override
    public void setData(final CartBean.DataEntity.CartListEntity data) {
        super.setData(data);

        if (mListener == null) {
            return;
        }

        setEditLayout(data);                        // 设置"编辑布局"的数据
        setNormalLayout(data);                      // 设置"非编辑布局"的数据
        setViewByStatus(data);                      // 根据是否失效设置控件

        if(data.isShowEdit){
            SwipeDeleteManager.getInstance().setCanScroll(false);
            mRl_normal.setVisibility(View.GONE);
            mRl_edit.setVisibility(View.VISIBLE);
        }else {
            SwipeDeleteManager.getInstance().setCanScroll(true);
            mRl_normal.setVisibility(View.VISIBLE);
            mRl_edit.setVisibility(View.GONE);
        }

        mTv_sh.setVisibility(data.getIs_sh() == 1 ? View.VISIBLE : View.GONE);

        //  侧滑的删除按钮
        mTv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mFragment.deleteGoods(data.getCart_id() + "");
                mListener.deleteGoodsVH(data.getCart_id() + "");
            }
        });


        ((SwipeDeleteItem) itemView).setItemOnclickListener(new SwipeDeleteItem.ItemOnclickListener() {
            @Override
            public void onclick() {
                // mFragment.mOnItemClickListener.onGoodsItemClick(getDataPosition());

                if(mListener != null){
                    mListener.onGoodsItemClick(getDataPosition(), data.getGoods_id() + "");
                }
            }
        });
    }


    /**
     * 是否显示编辑
     * @param data
     */
    private void setIsShowEdit(CartBean.DataEntity.CartListEntity data) {
        if (data.isShowEdit) {
            SwipeDeleteManager.getInstance().setCanScroll(false);
            mRl_normal.setVisibility(View.GONE);
            mRl_edit.setVisibility(View.VISIBLE);
        } else {
            SwipeDeleteManager.getInstance().setCanScroll(true);
            mRl_normal.setVisibility(View.VISIBLE);
            mRl_edit.setVisibility(View.GONE);
        }
    }

    /**
     * 设置食品选择框是否选择，监听事件
     * @param data
     */
    private void setGoodsIsCheckAndListener(final CartBean.DataEntity.CartListEntity data) {
        mCb_goodscheck.setChecked(data.isCheck);
        mCb_goodscheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.isCheck = mCb_goodscheck.isChecked();
                mListener.cbGoodsCheckVH(data);
            }
        });
    }

    /**
     * 设置"非编辑布局"的数据
     * @param data
     */
    private void setNormalLayout(final CartBean.DataEntity.CartListEntity data) {
        GlideUtils.loadImg(mContext, data.getGoods_thumb(), mIv_img);
        mTv_number.setText(Utils.getString(R.string.buy_goods_number, data.getCart_number()));
        if (data.isDialog) {            /* 弹窗修改 */
            data.isDialog = false;
            // 逆差多少
            int i = data.getCart_number() - Integer.valueOf(mTv_editNumber.getText().toString().trim());
            mListener.updateShopMoqVH(data, i, true);
        } else {
            mTempNumber = data.getCart_number();
        }
        mTv_price.setText(Utils.getString(R.string.overseas_list_money, data.getCart_price()));
        mTv_name.setText(data.getGoods_name());
    }

    /**
     * 设置"编辑布局"的数据
     * @param data
     */
    private void setEditLayout(CartBean.DataEntity.CartListEntity data) {
        mTv_editNumber.setTag(data);                    /* 注意这里 */

//        mWatcher = new MyTextWatcher(mTv_editNumber);
//        mTv_editNumber.addTextChangedListener(mWatcher);
        mTv_editNumber.setText(data.getCart_number() + "");
//        mTv_editNumber.setSelection(mEt_number.getText().length());

        if (data.getSpecification() != null) {
            if (data.tempSpecifi == null) {
                data.tempSpecifi = data.getSpecification();
            }
            String str = "";
            String editStr = "";

            for (CartBean.DataEntity.CartListEntity.SpecificationEntity specifi : data.getSpecification()) {
                str += specifi.getKey() + ":" + specifi.getValue() + "\n";      // 规格 + 值
                editStr += specifi.getValue() + ";";                            // 规格
            }
            mTv_specifi.setText(str.trim());
            mTv_editspecifi.setText(editStr.substring(0, editStr.length() - 1));
        }
    }


    /**
     * 设置"编辑布局"的控件的"点击事件"
     * @param data
     */
    private void setEditLayoutViewListener(final CartBean.DataEntity.CartListEntity data) {
        mTv_editspecifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取最新的bean
                // mFragment.selectSpecific((CartBean.DataEntity.CartListEntity) mEt_number.getTag());
                mListener.selectSpecificVH((CartBean.DataEntity.CartListEntity) mTv_editNumber.getTag());
            }
        });

        mBt_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.isShowEdit = false;
                SwipeDeleteManager.getInstance().setCanScroll(true);
                mRl_normal.setVisibility(View.VISIBLE);
                mRl_edit.setVisibility(View.GONE);
                Map<String, String> map = new HashMap<>();
                map.put("user_id", GlobalData.getUser_id());
                map.put("goods_id", data.getGoods_id() + "");
                map.put("cart_number", mTv_editNumber.getText().toString().trim());
                map.put("sku_id", data.getSku_id() + "");
                map.put("cart_type", 2 + "");                           // 2 直接修改数量
                map.put("cart_id", data.getCart_id() + "");
                map.put("is_sh", data.getIs_sh() + "");
                map.put("specification", data.json + "");
                mListener.editCartVH(map, mTempNumber, data.shop_id);
            }
        });

        mIv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoodsNumber = mTv_editNumber.getText().toString().trim();
                Integer integer = Integer.valueOf(mGoodsNumber);
                mGoodsNumber = (++integer) + "";
                data.setCart_number(integer);
                mTv_editNumber.setText(mGoodsNumber);
//                mEt_number.setSelection(mGoodsNumber.length());
                mTv_number.setText(Utils.getString(R.string.buy_goods_number, mGoodsNumber));
                mListener.updateShopMoqVH(data, 1, false);
            }
        });

        mIv_cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoodsNumber = mTv_editNumber.getText().toString().trim();
                Integer integer = Integer.valueOf(mGoodsNumber);
                mGoodsNumber = ((--integer) <= 0 ? 1 : (integer)) + "";
                data.setCart_number(integer);
                mTv_editNumber.setText(mGoodsNumber);
//                mEt_number.setSelection(mGoodsNumber.length());
                mTv_number.setText(Utils.getString(R.string.buy_goods_number, mGoodsNumber));
                mListener.updateShopMoqVH(data, -1, false);
            }
        });

        mTv_editNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditGoodsNumDialog editGoodsNumDialog = EditGoodsNumDialog.newInstance(mTv_editNumber.getText().toString().trim(),
                        new EditGoodsNumDialog.EditGoodsNumListener() {
                            @Override
                            public void editNum(String number) {
                                mTv_editNumber.setText(number);
                            }
                        });

                if(mListener != null){
                    editGoodsNumDialog.show(mListener.getFragmentM(),  "");
                }

            }
        });
    }

    public void setIvEdit(final CartBean.DataEntity.CartListEntity data) {
        mIv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.showEditVH(data);
                }
            }
        });
    }


//    public class MyTextWatcher implements TextWatcher {
//        EditText mEditText;
//
//        public MyTextWatcher(EditText et_number) {
//            mEditText = et_number;
//        }
//
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//            CartBean.DataEntity.CartListEntity entity = (CartBean.DataEntity.CartListEntity) mEditText.getTag();
//            if (TextUtils.isEmpty(s.toString().trim())) {
//                mEt_number.setText("1");
//
//            }else if (entity.isShowEdit && Integer.valueOf(s.toString().trim()) < entity.getOrigin_number()) {
//                // 编辑状态才判断 -- 起订数量
//                ToastUtils.showToast(Utils.getString(R.string.setfrom_pieces,
//                        entity.getOrigin_number() + "", entity.getGoods_unit()));
//                mEt_number.setText(entity.getOrigin_number() + "");
//            }
//
//            entity.setCart_number(Integer.valueOf(mEt_number.getText().toString().trim()));
//        }
//    }


    /**
     * 根据是否失效设置控件
     * @param data
     */
    private void setViewByStatus(CartBean.DataEntity.CartListEntity data) {

//        if(mListener.getCurrMode() == CART_MODE_EDIT){          // 编辑模式(删除)
//            setViewByNormal(data);
//
//        }else {
        if (data.getStatus() == 1) {
            setViewByNormal(data);

        } else if (data.getStatus() == 2) {
            setViewByNoSelect();
        }
//        }
    }


    /**
     * 正常
     * @param data
     */
    private void setViewByNormal(CartBean.DataEntity.CartListEntity data) {
        ColorMatrix matrix = new ColorMatrix();

        Resources resources = mContext.getResources();
        Drawable drawable = resources.getDrawable(R.drawable.cb_xiaoyuan_selector);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        mCb_goodscheck.setCompoundDrawables(drawable, null, null, null);
        mCb_goodscheck.setPadding(Utils.dip2px(mContext, 5),
                Utils.dip2px(mContext, 5),
                Utils.dip2px(mContext, 5),
                Utils.dip2px(mContext, 5));
        mCb_goodscheck.setBackgroundResource(ResourceUtil.getDrawableResIDByName(mContext, "shape_transparent"));
        mCb_goodscheck.setText("");

        // ColorMatrix类有一个内置的方法可用于改变饱和度。
        // 传入一个大于1的数字将增加饱和度，而传入一个0～1之间的数字会减少饱和度。0值将产生一幅灰度图像。
        matrix.setSaturation(1);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        mIv_img.setColorFilter(filter);

        mIv_edit.setVisibility(View.VISIBLE);
        mTv_sh.setEnabled(true);

        /* 设置控件 */
        setIsShowEdit(data);                        // 是否显示编辑
        setGoodsIsCheckAndListener(data);           // 设置食品选择框是否选择，监听事件
        setIvEdit(data);                            // 编辑图片
        setEditLayoutViewListener(data);            // 设置"编辑布局"的控件的"点击事件"
    }

    /**
     * 商品失效了--不能选择
     */
    private void setViewByNoSelect() {
        ColorMatrix matrix = new ColorMatrix();
        mCb_goodscheck.setCompoundDrawables(null, null, null, null);
        mCb_goodscheck.setBackgroundResource(ResourceUtil.getDrawableResIDByName(mContext, "shape_bg_gray"));
        mCb_goodscheck.setText("失效");
        mCb_goodscheck.setPadding(Utils.dip2px(mContext, 4),
                Utils.dip2px(mContext, 2),
                Utils.dip2px(mContext, 4),
                Utils.dip2px(mContext, 2));

        // 添加灰度
        matrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        mIv_img.setColorFilter(filter);


        mIv_edit.setVisibility(View.INVISIBLE);
        mTv_sh.setEnabled(false);
    }


    public interface GoodsClickListener {
        /**
         * 项点击事件
         * @param position
         * @param goodsId
         */
        void onGoodsItemClick(int position, String goodsId);

        /**
         * 商店选择框是否选择
         * @param data
         */
        void cbGoodsCheckVH(CartBean.DataEntity.CartListEntity data);

        /**
         * 更新 选择的规格? 数量
         * @param data
         * @param number
         * @param isDelay
         */
        void updateShopMoqVH(CartBean.DataEntity.CartListEntity data, int number, boolean isDelay);

        /**
         * 选择规格 -- 网络请求 -- 再显示规格窗口
         * @param entity
         */
        void selectSpecificVH(CartBean.DataEntity.CartListEntity entity);

        /**
         * 点击完成按钮 -- 编辑购物车
         * @param data
         * @param number
         * @param shopId
         */
        void editCartVH(Map data, int number, int shopId);

        /**
         * 侧滑删除
         * @param cart_id
         */
        void deleteGoodsVH(String cart_id);


        /**
         * 获取当前的模式 -- 编辑（删除）/结算
         * @return
         */
        int getCurrMode();


        void showEditVH(CartBean.DataEntity.CartListEntity data);

        FragmentManager getFragmentM();
    }

}

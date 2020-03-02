package com.geli.m.mvp.home.other.submitorder_activity.main.vh;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.CartBean;
import com.geli.m.bean.SubmitOrderBean;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.PriceUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import static com.geli.m.config.Constant.AP_False;
import static com.geli.m.config.Constant.AP_True;

/**
 * Created by Steam_l on 2018/1/22.
 *
 *
 * 提交订单 -- 商店列表中 -- 每个商店里面的"商品项"   --   好像其他地方也复用了
 */
public class SubmitOrderGoodsViewHolder extends BaseViewHolder<SubmitOrderBean.DataEntity.ShopListEntity.GoodsListEntity> {

    Context mContext;
    /** 商品数量 */
    private final TextView mTv_number;
    /** 商品价格(单价) */
    private final TextView mTv_price;
    /** 商品规格 */
    private final TextView mTv_specifi;
    /** 商品名称 */
    private final TextView mTv_name;
    /** 商品图片 */
    private final ImageView mIv_img;
    /** 账期 */
    private final TextView mTv_sh;

    /* 是不是账期 */
    private int mIsSh = AP_False;



    public SubmitOrderGoodsViewHolder(ViewGroup parent, Context context, int is_sh) {
        super(parent, R.layout.itemview_submitorder_goods);

        mContext = context;
        mIsSh = is_sh;

        mIv_img = $(R.id.iv_itemview_submitorder_img);
        mTv_name = $(R.id.tv_itemview_submitorder_goodsname);
        mTv_specifi = $(R.id.tv_itemview_submitorder_goodsspecifi);
        mTv_price = $(R.id.tv_itemview_submitorder_price);
        mTv_number = $(R.id.tv_itemview_submitorder_number);

        mTv_sh = $(R.id.tv_itemview_submitorder_sh);
    }

    @Override
    public void setData(SubmitOrderBean.DataEntity.ShopListEntity.GoodsListEntity data) {
        super.setData(data);
        GlideUtils.loadImg(mContext, data.getGoods_thumb(), mIv_img);
        mTv_name.setText(data.getGoods_name());
        mTv_number.setText(Utils.getString(R.string.buy_goods_number, data.getCart_number()));
        mTv_price.setText(PriceUtils.getPrice(data.getCart_price()));

        if (data.getSpecification() != null) {
            String specifi = "";
            for (CartBean.DataEntity.CartListEntity.SpecificationEntity entity : data.getSpecification()) {
                specifi += entity.getKey() + ":" + entity.getValue() + "\n";
            }
            mTv_specifi.setText(specifi);
        }

        if(mIsSh == AP_True){
            mTv_sh.setVisibility(View.VISIBLE);
        }else {
            mTv_sh.setVisibility(View.GONE);
        }
    }
}

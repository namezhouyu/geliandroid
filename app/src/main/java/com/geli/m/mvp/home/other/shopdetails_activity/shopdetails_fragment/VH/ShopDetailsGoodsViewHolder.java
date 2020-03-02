package com.geli.m.mvp.home.other.shopdetails_activity.shopdetails_fragment.VH;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.CartBean;
import com.geli.m.bean.ShopInfoBean;
import com.geli.m.mvp.home.other.shopdetails_activity.shopdetails_fragment.ShopDetailsFragment;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.PriceUtils;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import java.util.List;

import static com.geli.m.config.Constant.Gs_Id_resale;

/**
 * Created by Steam_l on 2017/12/29.
 */

public class ShopDetailsGoodsViewHolder extends BaseViewHolder<ShopInfoBean.DataEntity.GoodsResEntity> {
    Context mContext;

    /** 分类? */
    private final TextView mTv_title;

    /** 选规格 按钮 */
    private final Button mBt_selectspecifi;
    /** 商品图片 */
    private final ImageView mIv_img;
    /** 商品价格 */
    private final TextView mTv_price;
    /** 商品名称 */
    private final TextView mTv_name;
    /** 商品规格 */
    private final TextView mTv_specifi;


    /** 可零售 */
    private final TextView mTv_retail;

    ShopDetailsFragment mFragment;

    public ShopDetailsGoodsViewHolder(ViewGroup parent, Context context, ShopDetailsFragment fragment) {
        super(parent, R.layout.itemview_shopdetails_goods);
        mContext = context;
        mFragment = fragment;
        mBt_selectspecifi = $(R.id.bt_itemview_shopdetails_selectspecifi);
        mIv_img = $(R.id.iv_itemview_shopdetails_img);
        mTv_name = $(R.id.tv_itemview_shopdetails_name);
        mTv_specifi = $(R.id.tv_itemview_shopdetails_spectifi);
        mTv_price = $(R.id.tv_itemview_shopdetails_price);
        mTv_title = $(R.id.tv_itemview_goodstitle_title);

        mTv_retail = $(R.id.tv_retail_ShopDetailsGoodsVH);
    }

    @Override
    public void setData(final ShopInfoBean.DataEntity.GoodsResEntity data) {
        super.setData(data);
        mTv_title.setVisibility(View.GONE);

        if(data.getGs_id() == Gs_Id_resale){
            mTv_retail.setVisibility(View.VISIBLE);
        }else {
            mTv_retail.setVisibility(View.GONE);
        }

        if (data.dataNUll) {
            mTv_name.setText("");
            mTv_specifi.setText("");
            mTv_name.setBackgroundResource(R.color.text_f2f2f2);
            mTv_specifi.setBackgroundResource(R.color.text_f2f2f2);
            mIv_img.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.img_loading));
            return;
        } else {
            mTv_name.setBackgroundResource(R.color.transparent);
            mTv_specifi.setBackgroundResource(R.color.transparent);
        }

        if(data.getSku_count() == 1){
            mBt_selectspecifi.setText(Utils.getString(R.string.buy));
            mBt_selectspecifi.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (data.dataNUll) {
                        return;
                    }
                    if (!(Double.valueOf(data.getShop_price()) <= 0)) {
                        mFragment.addCartOnly(data);
                    } else {
                        ShowSingleToast.showToast(mContext, Utils.getString(R.string.please_contact_customer_service_inquiry));
                    }
                }
            });
        }else {
            mBt_selectspecifi.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (data.dataNUll) {
                        return;
                    }
                    if (!(Double.valueOf(data.getShop_price()) <= 0)) {
                        mFragment.showSelectPrice(data);
                    } else {
                        ShowSingleToast.showToast(mContext, Utils.getString(R.string.please_contact_customer_service_inquiry));
                    }
                }
            });
        }
        if (Double.valueOf(data.getShop_price()) == 0) {
            mTv_price.setText(PriceUtils.getPrice(Double.valueOf(data.getShop_price())));
        } else {
            mTv_price.setText(Utils.getString(R.string.overseas_list_money, Utils.getFormatDoubleTwoDecimalPlaces(Double.valueOf(data.getShop_price()), 2)));
        }
        mTv_name.setText(data.getGoods_name());
        GlideUtils.loadImg(mContext, data.getGoods_thumb(), mIv_img);
        List<CartBean.DataEntity.CartListEntity.SpecificationEntity> specifications = data.getSpecifications();
        if (specifications != null) {
            String specifi = "";
            for (CartBean.DataEntity.CartListEntity.SpecificationEntity entity : specifications) {
                String str = entity.getKey() + ":" + entity.getValue();
                if (str.length() > 15) {                    // 超过15个字的,超过部分使用省略号
                    str = str.substring(0, 15) + "...";
                }
                specifi += str + "\n";
            }
            mTv_specifi.setText(specifi.trim());
        } else {
            mTv_specifi.setText("");
        }
    }
}

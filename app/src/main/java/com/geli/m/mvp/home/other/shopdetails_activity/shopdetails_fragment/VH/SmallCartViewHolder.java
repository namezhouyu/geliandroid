package com.geli.m.mvp.home.other.shopdetails_activity.shopdetails_fragment.VH;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.CartBean;
import com.geli.m.coustomview.SmallCartLayout;
import com.geli.m.coustomview.SwipeDeleteItem;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.PriceUtils;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import static com.geli.m.R.id.tv_itemview_smallcart_delete;

/**
 * Created by Steam_l on 2018/1/18.
 */

public class SmallCartViewHolder extends BaseViewHolder<CartBean.DataEntity.CartListEntity> {

    Context mContext;
    /** 价格 */
    private final TextView mTv_price;
    /** 右边，需要拖出来的"删除"按钮 */
    private final TextView mTv_delete;
    /** 商品规格 */
    private final TextView mTv_specifi;
    /** 商品名称 */
    private final TextView mTv_name;
    /** 数量 */
    private final TextView mTv_number;
    /** 加号 */
    private final ImageView mIv_add;
    /** 减号 */
    private final ImageView mIv_cut;
    /** 商品图片 */
    private final ImageView mIv_img;
    private int number = 0;

    SmallCartLayout.SmallCartEditListener mCartEditListener;

    public SmallCartViewHolder(ViewGroup parent, Context context, SmallCartLayout.SmallCartEditListener listener) {
        super(parent, R.layout.itemview_smallcart_goods);

        mContext = context;
        mCartEditListener = listener;
        mIv_img = $(R.id.iv_itemview_smallcart_img);
        mIv_cut = $(R.id.iv_itemview_smallcart_cut);
        mIv_add = $(R.id.iv_itemview_smallcart_add);
        mTv_number = $(R.id.tv_itemview_smallcart_number);
        mTv_name = $(R.id.tv_itemview_smallcart_name);
        mTv_specifi = $(R.id.tv_itemview_smallcart_specifi);
        mTv_delete = $(tv_itemview_smallcart_delete);
        mTv_price = $(R.id.tv_itemview_smallcart_price);
    }

    @Override
    public void setData(final CartBean.DataEntity.CartListEntity data) {
        super.setData(data);

        GlideUtils.loadImg(mContext, data.getGoods_thumb(), mIv_img);
        mTv_name.setText(data.getGoods_name());
        mTv_price.setText(PriceUtils.getPrice(data.getCart_price()));
        number = data.getCart_number();
        String specifi = "";
        if (data.getSpecification() != null) {
            for (CartBean.DataEntity.CartListEntity.SpecificationEntity entity : data.getSpecification()) {
                specifi += entity.getValue() + ";";
            }
            if (!TextUtils.isEmpty(specifi)) {
                specifi = specifi.substring(0, specifi.length() - 1);
            }
        }

        mTv_number.setText(data.getCart_number() + "");
        mTv_specifi.setText(specifi);

        /* "加号"点击事件 */
        mIv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number++;
                mCartEditListener.editCart(data, number, true);
            }
        });


        /* "减号"点击事件 */
        mIv_cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number--;
                if (number <= 0) {
                    ToastUtils.showToast( Utils.getString(R.string.message_delete_plase_leftscroll));
                    number = 1;
                    return;
                }
                if (number < data.getOrigin_number()) {         // 判断下 -- 商品起订量
                    number++;
                    ToastUtils.showToast(Utils.getString(R.string.setfrom_pieces_temp,
                            data.getOrigin_number()));
                    return;
                }
                mCartEditListener.editCart(data, number, false);
            }
        });


        /* 删除按钮 */
        mTv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SwipeDeleteItem) itemView).close();
                mCartEditListener.deleteGoods(data);
            }
        });
    }

}

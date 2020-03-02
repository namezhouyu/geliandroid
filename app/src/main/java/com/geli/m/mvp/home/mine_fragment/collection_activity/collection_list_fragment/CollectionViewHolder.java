package com.geli.m.mvp.home.mine_fragment.collection_activity.collection_list_fragment;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.geli.m.R;
import com.geli.m.bean.CollectionBean;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.PriceUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import static com.geli.m.config.Constant.TYPE_GOODS;
import static com.geli.m.config.Constant.TYPE_SHOP;

/**
 * Created by Steam_l on 2018/4/2.
 */

public class CollectionViewHolder extends BaseViewHolder<CollectionBean.DataEntity> {

    Context mContext;

    BCListener mListener;
    int mType;

    /** 可以是"价格"，可以是"描述" */
    private final TextView mTv_setfrom;
    /** 商品名称 */
    private final TextView mTv_name;
    /** 商品图片 */
    private final ImageView mIv_img;
    /** "取消收藏"按钮 */
    private final Button mBt_sotoshop;

    public CollectionViewHolder(ViewGroup parent, Context context, int type, BCListener listener) {
        super(parent, R.layout.itemview_collection);
        mContext = context;
        mListener = listener;
        mType = type;

        mIv_img = $(R.id.iv_itemview_coll_shopimg);
        mTv_name = $(R.id.tv_itemview_coll_shopname);
        mTv_setfrom = $(R.id.tv_itemview_coll_setfrom);
        mBt_sotoshop = $(R.id.bt_itemview_coll_entershop);
        NestedScrollView nestedScrollView = $(R.id.nsv_itemview_coll);

        ViewCompat.setNestedScrollingEnabled(nestedScrollView, false);
    }

    @Override
    public void setData(final CollectionBean.DataEntity data) {
        super.setData(data);
        String name;
        String imgUrl;
        String setfrom;
        mBt_sotoshop.setText(Utils.getString(R.string.cancel_coll));

        switch (mType){
            case TYPE_SHOP:                 /* 商店收藏 */
                name = data.getShop_name();
                setfrom = data.getShop_intro();
                imgUrl = data.getShop_img_thumb();
                mTv_setfrom.setTextColor(Utils.getColor(R.color.text_color));
                GlideUtils.loadImg(mContext, imgUrl, mIv_img);
                break;

            case TYPE_GOODS:                 /* 商品收藏 */
                name = data.getGoods_name();
                setfrom = PriceUtils.getPrice(data.getShop_price());
                imgUrl = data.getGoods_thumb();
                mTv_setfrom.setTextColor(Utils.getColor(R.color.zhusediao));
                GlideUtils.loadImg(mContext, imgUrl, mIv_img);
                break;

            default:
                name = data.getArticle_title();
                setfrom = data.getArticle_content();
                imgUrl = data.getCover_url();
                mTv_setfrom.setTextColor(Utils.getColor(R.color.text_color));
                RequestOptions options = new RequestOptions();
                options.diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.img_loading)
                        .error(R.drawable.img_jiazaishibei)
                        .centerCrop();
                Glide.with(mContext).load(imgUrl).apply(options).into(mIv_img);
                break;
        }

        mTv_name.setText(name);
        mTv_setfrom.setText(setfrom);

        mBt_sotoshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id;
                if (mType == TYPE_GOODS) {
                    id = data.getGoods_id();
                } else if (mType == TYPE_SHOP) {
                    id = data.getShop_id();
                } else {
                    id = data.getFind_id();
                }
                if(mListener != null){
                    mListener.cancelColl(id, getAdapterPosition());
                }

            }
        });
    }

    interface BCListener {
        void cancelColl(int id, int position);
    }
}

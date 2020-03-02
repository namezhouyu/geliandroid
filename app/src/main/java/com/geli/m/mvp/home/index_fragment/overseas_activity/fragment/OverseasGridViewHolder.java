package com.geli.m.mvp.home.index_fragment.overseas_activity.fragment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.CartBean;
import com.geli.m.bean.OverseasGoodsOuterBean;
import com.geli.m.coustomview.CustomProgressBar;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.PriceUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import java.util.List;

/**
 * Created by Steam_l on 2017/12/29.
 */

public class OverseasGridViewHolder extends BaseViewHolder<OverseasGoodsOuterBean.OverseasGoodsBean> {
    Context mContext;
    private final TextView mTv_price;
    private final TextView mTv_type;
    private final TextView mTv_specifi;
    private final TextView mTv_name;
    private final ImageView mIv_img;
    private final TextView mTv_pronumber;
    private final TextView mTv_remaining;
    private final TextView mTv_buynumber;
    private final CustomProgressBar mCpb_progress;
    private final TextView mTv_origin;
    private final RelativeLayout mRl_progress;

    public OverseasGridViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_search_grid_overseasgoods);
        mContext = context;
        mIv_img = $(R.id.iv_itemview_goodssearch_grid_overseas_img);
        mTv_name = $(R.id.tv_itemview_goodssearch_grid_overseas_name);
        mTv_specifi = $(R.id.tv_itemview_goodssearch_grid_overseas_spectifi);
        mTv_type = $(R.id.tv_itemview_goodssearch_grid_overseas_type);
        mTv_price = $(R.id.tv_itemview_goodssearch_grid_overseas_price);
        mTv_buynumber = $(R.id.tv_include_delegation_personnumber);
        mTv_remaining = $(R.id.tv_include_delegation_remaining);
        mTv_pronumber = $(R.id.tv_include_delegation_progress_number);
        mCpb_progress = $(R.id.cpb_include_delegation_progress);
        mTv_origin = $(R.id.tv_itemview_goodssearch_grid_overseas_from);
        mRl_progress = $(R.id.rl_itemview_goodssearch_grid_overseas_layout);
    }

    @Override
    public void setData(final OverseasGoodsOuterBean.OverseasGoodsBean data) {
        super.setData(data);
        GlideUtils.loadImg(mContext, data.getGoods_thumb(), mIv_img);
        mTv_name.setText(data.getGoods_name());
        if (data.getGoods_type() == 4) {
            mTv_type.setText(Utils.getString(R.string.group_buy));
            mTv_buynumber.setText(Utils.getString(R.string.delegation_personnumber, data.getSold_number() + ""));
            mTv_remaining.setText(Utils.getString(R.string.remaining_ton, data.getSurplus(), data.getGoods_unit()));
            double number = Double.valueOf(data.getSold_number()) / Double.valueOf(data.getTarget_number());
            mTv_pronumber.setText(Utils.getFormatDoubleTwoDecimalPlaces(number * 100, 1) + "%");
            mCpb_progress.setCurProgress((int) (number * 100));

        } else if (data.getGoods_type() == 3) {
            mRl_progress.setVisibility(View.GONE);
            mTv_type.setText(Utils.getString(R.string.futures));

        } else if (data.getGoods_type() == 2) {
            mRl_progress.setVisibility(View.GONE);
            mTv_type.setText(Utils.getString(R.string.spot));
        }

        mTv_origin.setText(Utils.getString(R.string.origin, data.getCoun_name()));
        mTv_price.setText(PriceUtils.getStartPrice(data.getShop_price()));

        List<CartBean.DataEntity.CartListEntity.SpecificationEntity> specifications = data.getSpecifications();
        if (specifications != null) {
            String specifi = "";
            for (CartBean.DataEntity.CartListEntity.SpecificationEntity child : specifications) {
                String childSpe = child.getKey() + ":" + child.getValue();
                if (childSpe.length() > 20) {
                    childSpe = childSpe.substring(0, 20) + "...";
                }
                specifi += childSpe + "\n";
            }
            mTv_specifi.setText(specifi.trim());
        }
    }
}

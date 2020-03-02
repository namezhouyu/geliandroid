package com.geli.m.mvp.home.index_fragment.overseas_activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.CartBean;
import com.geli.m.bean.OverseasGoodsOuterBean;
import com.geli.m.coustomview.CustomProgressBar;
import com.geli.m.mvp.home.index_fragment.overseas_activity.OverseasActivity;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.PriceUtils;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import java.util.List;

/**
 * Created by Steam_l on 2017/12/29.
 */

public class OverseasListViewHolder extends BaseViewHolder<OverseasGoodsOuterBean.OverseasGoodsBean> {
    Context mContext;
    private final Button mBt_qualifications;
    private final Button mBt_selectspecifi;
    private final TextView mTv_selfrom;
    private final TextView mTv_price;
    private final TextView mTv_type;
    private final TextView mTv_specifi;
    private final TextView mTv_name;
    private final TextView mTv_origin;
    private final ImageView mIv_img;
    private final FrameLayout mFl_progress;
    private final TextView mTv_pronumber;
    private final TextView mTv_remaining;
    private final TextView mTv_buynumber;
    private final CustomProgressBar mCpb_progress;
    private boolean mIsSearch;

    public OverseasListViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_overseas_list);
        mContext = context;
        mIv_img = $(R.id.iv_itemview_overseas_list_img);
        mTv_origin = $(R.id.tv_itemview_overseas_list_origin);
        mTv_name = $(R.id.tv_itemview_overseas_list_name);
        mTv_specifi = $(R.id.tv_itemview_overseas_list_spectifi);
        mTv_type = $(R.id.tv_itemview_overseas_list_type);
        mTv_price = $(R.id.tv_itemview_overseas_list_price);
        mTv_selfrom = $(R.id.tv_itemview_overseas_list_selfrom);
        mBt_selectspecifi = $(R.id.bt_itemview_overseas_list_selectspecifi);
        mBt_qualifications = $(R.id.bt_itemview_overseas_list_qualifications);
        mFl_progress = $(R.id.fl_itemview_overseas_list_progress);
        mTv_buynumber = $(R.id.tv_include_delegation_personnumber);
        mTv_remaining = $(R.id.tv_include_delegation_remaining);
        mTv_pronumber = $(R.id.tv_include_delegation_progress_number);
        mCpb_progress = $(R.id.cpb_include_delegation_progress);
    }

    @Override
    public void setData(final OverseasGoodsOuterBean.OverseasGoodsBean data) {
        super.setData(data);
        GlideUtils.loadImg(mContext, data.getGoods_thumb(), mIv_img);
        mTv_name.setText(data.getGoods_name());
        if (data.getGoods_type() == 4) {
            mFl_progress.setVisibility(View.VISIBLE);
            mTv_type.setText(Utils.getString(R.string.group_buy));
            mTv_buynumber.setText(Utils.getString(R.string.delegation_personnumber, data.getParticipants() + ""));
            mTv_remaining.setText(Utils.getString(R.string.remaining_ton, data.getSurplus(), data.getGoods_unit()));
            double number = Double.valueOf(data.getSold_number()) / Double.valueOf(data.getTarget_number());
            mTv_pronumber.setText(Utils.getFormatDoubleTwoDecimalPlaces(number * 100, 1) + "%");
            mCpb_progress.setCurProgress((int) (number * 100));

        } else if (data.getGoods_type() == 3) {
            mTv_selfrom.setVisibility(View.VISIBLE);
            mTv_selfrom.setText(Utils.getString(R.string.setfrom_pieces, data.getOrigin_number() + "", data.getGoods_unit()));
            mTv_type.setText(Utils.getString(R.string.futures));

        } else if (data.getGoods_type() == 2) {
            mTv_selfrom.setVisibility(View.VISIBLE);
            mTv_selfrom.setText(Utils.getString(R.string.setfrom_pieces, data.getOrigin_number() + "", data.getGoods_unit()));
            mBt_selectspecifi.setVisibility(View.VISIBLE);
            mBt_selectspecifi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Double.valueOf(data.getShop_price()) == 0) {
                        ShowSingleToast.showToast(mContext, Utils.getString(R.string.please_contact_customer_service_inquiry));
                        return;
                    }
                    Intent intent = new Intent(OverseasActivity.BROADCAST_ADDCART);
                    intent.putExtra(OverseasActivity.BROADCAST_GOODSID, data.getGoods_id() + "");
                    intent.putExtra(OverseasActivity.BROADCAST_DATA, data);
                    mContext.sendBroadcast(intent);
                }
            });
            mTv_type.setText(Utils.getString(R.string.spot));
        }

        mBt_qualifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OverseasActivity.BROADCAST_SHOPAPTITUDE);
                intent.putExtra(OverseasActivity.BROADCAST_SHOPID, data.getShop_id() + "");
                mContext.sendBroadcast(intent);
            }
        });
        if (mIsSearch) {
            mBt_selectspecifi.setVisibility(View.GONE);
            mBt_qualifications.setVisibility(View.GONE);
            mTv_selfrom.setVisibility(View.GONE);
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
        } else {
            mTv_specifi.setText("");
        }
    }

    public void setIsSearch(boolean isSearch) {
        mIsSearch = isSearch;
    }
}

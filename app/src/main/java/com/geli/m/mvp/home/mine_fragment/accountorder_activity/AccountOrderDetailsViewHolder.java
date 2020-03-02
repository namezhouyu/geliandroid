package com.geli.m.mvp.home.mine_fragment.accountorder_activity;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.geli.m.R;
import com.geli.m.bean.AccountOrderBean;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * author:  shen
 * date:    2018/11/15
 */
public class AccountOrderDetailsViewHolder extends BaseViewHolder<AccountOrderBean.DataBean.GoodsResBean> {
    Context mContext;
    @BindView(R.id.iv_img_ivaorderdetail)
    ImageView mIvImg;
    @BindView(R.id.tv_goodsname_ivaorderdetail)
    TextView mTvGoodsName;
    @BindView(R.id.tv_goodsspecific_ivaorderdetail)
    TextView mTvGoodsSpecific;
    @BindView(R.id.tv_price_ivaorderdetail)
    TextView mTvPrice;
    @BindView(R.id.tv_number_ivaorderdetail)
    TextView mTvNumber;

    public AccountOrderDetailsViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_accountorderdetails);
        mContext = context;
        ButterKnife.bind(this, itemView);
    }


    @Override
    public void setData(AccountOrderBean.DataBean.GoodsResBean data) {
        GlideUtils.loadImg(mContext, data.getGoods_thumb() == null ? "" : data.getGoods_thumb(), mIvImg);
        mTvGoodsName.setText(data.getGoods_name());
        mTvPrice.setText(Utils.getString(R.string.overseas_list_money, data.getGoods_price()));
        mTvNumber.setText(Utils.getString(R.string.buy_goods_number, data.getCart_number()));

        setSpecifi(data);
    }

    /**
     * 设置规格
     *
     * @param data
     */
    private void setSpecifi(AccountOrderBean.DataBean.GoodsResBean data) {
        String specifi = "";
        if (data.getGoods_attr() != null) {
            for (AccountOrderBean.DataBean.GoodsResBean.GoodsAttrBean specificationEntity : data.getGoods_attr()) {
                specifi += specificationEntity.getKey() + ":" + specificationEntity.getValue() + "\n";
            }
        }
        mTvGoodsSpecific.setText(specifi.trim());
    }
}

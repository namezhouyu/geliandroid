package com.geli.m.mvp.home.mine_fragment.invoice_activity.invoicerecord_activity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.geli.m.R;
import com.geli.m.bean.InvoiceRecordBean;
import com.geli.m.utils.TimeUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import static com.geli.m.config.Constant.INVOICE_ELECTRONIC;
import static com.geli.m.config.Constant.INVOICE_PERSONAL;
import static com.geli.m.config.Constant.INVOICE_UNIT_SPECIAL;
import static com.geli.m.utils.TimeUtils.format12;

/**
 * Created by Steam_l on 2018/1/11.
 * <p>
 * 发票管理 中 发票类型(增值税普通发票， 增值税专用发票) 个人发票
 */

public class InvoiceRecordViewHolder extends BaseViewHolder<InvoiceRecordBean.DataBean> {
    Context mContext;

    /**
     * 商店名字
     */
    @BindView(R.id.tv_shop_name_vhInvoiceRecord)
    TextView mTvShopName;
    /**
     * 标志 -- 是否是合并的发票
     */
    @BindView(R.id.tv_flag_vhInvoiceRecord)
    TextView mTvFlag;
    /**
     * 状态 -- 申请中（合并的时候） / 是否开具了
     */
    @BindView(R.id.tv_state_vhInvoiceRecord)
    TextView mTvState;
    /**
     * 时间
     */
    @BindView(R.id.tv_time_vhInvoiceRecord)
    TextView mTvTime;
    /**
     * 发票类型
     */
    @BindView(R.id.tv_type_vhInvoiceRecord)
    TextView mTvType;
    /**
     * 发票金额
     */
    @BindView(R.id.tv_price_vhInvoiceRecord)
    TextView mTvPrice;


    public InvoiceRecordViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.vh_invoice_record);
        mContext = context;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setData(InvoiceRecordBean.DataBean data) {
        super.setData(data);

        mTvShopName.setText(data.getShop_name());
        mTvTime.setText(TimeUtils.transForDate(data.getAdd_time(), format12));
        mTvType.setText(Utils.getString(R.string.invoice_type_1, setInvoiceType(data)));
        mTvPrice.setText(Utils.getString(R.string.invoice_price_1, data.getSum_amount()));

        mTvFlag.setVisibility(data.getIs_merge() == 0 ? View.GONE : View.VISIBLE);

        // 开票状态(1等待开票2开票成功)
        mTvState.setText(data.getStatus() == 1 ?
                Utils.getString(R.string.application) :
                Utils.getString(R.string.opening));
    }


    /**
     * 发票类型
     *
     * @param data
     */
    private String setInvoiceType(InvoiceRecordBean.DataBean data) {

        String res = "";
        if (data.getBelong() == INVOICE_PERSONAL) {                     /* 个人发票 -- 都是普通发票 */
            res += Utils.getString(R.string.personal_invoice);

        } else {                                                        /* 单位发票 */
            if (data.getType() == INVOICE_UNIT_SPECIAL) {       // 增值专用
                res += Utils.getString(R.string.invoice_VATdedicatedinvoice);
            } else {                                           // 普通发票
                res += Utils.getString(R.string.invoice_VATordinaryinvoice);
            }
        }

        res += "-";
        if (data.getInvoice_type() == INVOICE_ELECTRONIC) {             /* 电子发票 */
            res += Utils.getString(R.string.invoice_electronicinvoice);
        } else {                                                        /* 纸质发票 */
            res += Utils.getString(R.string.invoice_pagerinvoice);

        }
        return res;
    }
}

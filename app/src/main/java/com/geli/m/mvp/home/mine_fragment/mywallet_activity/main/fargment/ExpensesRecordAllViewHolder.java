package com.geli.m.mvp.home.mine_fragment.mywallet_activity.main.fargment;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.ExpensesRecordBean;
import com.geli.m.utils.DateFormatUtil;
import com.geli.m.utils.StringUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by Steam_l on 2018/3/27.
 *
 * 消费记录
 */

public class ExpensesRecordAllViewHolder extends BaseViewHolder<ExpensesRecordBean.DataEntity> {

    Context mContext;

    /** 订单/交易 id */
    private final TextView mTv_order_id;
    /** 类型 */
    private final TextView mTv_type;
    /** 时间--2018-05-28 18:45 */
    private final TextView mTv_time;
    /** 金额 */
    private final TextView mTv_money;

    public ExpensesRecordAllViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_expensesrecord_all);
        mContext = context;

        mTv_order_id = $(R.id.tv_itemview_expenses_cord_all_order_id);
        mTv_type = $(R.id.tv_itemview_expenses_cord_all_type);
        mTv_time = $(R.id.tv_itemview_expenses_cord_all_time);
        mTv_money = $(R.id.tv_itemview_expenses_cord_all_money);

    }

    @Override
    public void setData(ExpensesRecordBean.DataEntity data) {
        super.setData(data);

        mTv_order_id.setText(Utils.getString(R.string.transaction_number, data.getPtn_srl()));
        mTv_type.setText(data.getRemark());
        mTv_time.setText(DateFormatUtil.transForDate(data.getAdd_time()));

        try {
            String money = data.getTrade_money();
            if(StringUtils.isEmpty(money)){
                mTv_money.setText("");

            }else {
                // float moneyF = Float.valueOf(money);
                int trade_type = data.getTrade_type();

                if (trade_type == 1) {                                      // 支出
                    mTv_money.setText("- " + money);
                    mTv_money.setTextColor(Utils.getColor(R.color.zhusediao));
                }else if(trade_type == 2){                                                    // 转入
                    mTv_money.setText("+ " + money);
                    mTv_money.setTextColor(Utils.getColor(R.color.text_49b950));

                }else if(trade_type == 3){                                                    // 开户(开卡)的，不要金额
                    mTv_money.setText("");
                }

            }
        }catch (Exception e){
            mTv_money.setText("");
        }
    }
}

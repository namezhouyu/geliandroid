package com.geli.m.mvp.home.mine_fragment.mywallet_activity.expensesrecord_activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.bean.ExpensesBean;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.utils.DateFormatUtil;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.PriceUtils;
import com.geli.m.utils.Utils;
import java.util.List;

/**
 * Created by Steam_l on 2018/3/27.
 *
 * 消费记录详情
 */
public class ExpensesDetailsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_expensesdetails_img)
    ImageView iv_img;
    @BindView(R.id.tv_expensesdetails_time)
    TextView tv_time;
    @BindView(R.id.tv_expensesdetails_shipping)
    TextView tv_shipping;
    @BindView(R.id.tv_expensesdetails_price)
    TextView tv_price;
    @BindView(R.id.tv_expensesdetails_paymethod)
    TextView tv_paymthod;
    @BindView(R.id.tv_expensesdetails_ordernumber)
    TextView tv_ordernumber;
    @BindView(R.id.tv_expensesdetails_name)
    TextView tv_shopname;
    @BindView(R.id.tv_expensesdetails_mess)
    TextView tv_mess;
    @BindView(R.id.tv_expensesdetails_goodsdes)
    TextView tv_goodsdes;
    @BindView(R.id.tv_title_name)
    TextView tv_title;
    public static final String INTENT_DATA = "intent_data";
    private ExpensesBean.DataEntity.ConsumptionEntity mEntity;

    @Override
    protected int getResId() {
        return R.layout.activity_expensesdetails;
    }

    @Override
    protected void init() {
        super.init();
        mEntity = getIntent().getParcelableExtra(INTENT_DATA);
    }

    @Override
    protected void initData() {
        tv_title.setText(Utils.getString(R.string.expenses_details));
        GlideUtils.loadImg(mContext, mEntity.getShop_thumb(), iv_img);
        tv_ordernumber.setText(mEntity.getOut_trade_no());
        tv_shopname.setText(mEntity.getShop_name());
        tv_shipping.setText(mEntity.getAddr());
        tv_price.setText(PriceUtils.getPrice(mEntity.getTotal_amount()));
        tv_time.setText(DateFormatUtil.transForDate((int) mEntity.getAdd_time(), "yyyy.MM.dd HH:mm"));
        String goodsdes = "";
        List<ExpensesBean.DataEntity.ConsumptionEntity.GoodsEntity> goods = mEntity.getGoods();
        for (ExpensesBean.DataEntity.ConsumptionEntity.GoodsEntity entity : goods) {
            goodsdes += entity.getGoods_name() + "X" + entity.getGoods_number() + ";";
        }
        if (goodsdes.length() > 0) {
            tv_goodsdes.setText(goodsdes.substring(0, goodsdes.length() - 1));
        }
        int use_type = mEntity.getUse_type();
        String paymthod;
        if (use_type == 1) {
            paymthod = Utils.getString(R.string.wechat_pay);
        } else if (use_type == 2) {
            paymthod = Utils.getString(R.string.ali_pay1);
        } else if (use_type == 3) {
            paymthod = Utils.getString(R.string.linedown_pay);
        } else {
            paymthod = Utils.getString(R.string.wallet_pay);
        }
        tv_paymthod.setText(paymthod);
    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.iv_title_back})
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_title_back) {
            finish();
        }
    }
}

package com.geli.m.dialog.apprice;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.bean.ExpensesRecordBean;
import com.geli.m.bean.ShPriceBean;
import com.geli.m.dialog.base.BaseDialogFragment;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import java.util.ArrayList;
import java.util.List;

import static com.geli.m.config.Constant.DIALOG_BEAN;

/**
 * Created by Steam_l on 2018/1/6.
 *
 *
 * 账期（天数）对应的 "价格（加利息）"
 */
@SuppressLint("ValidFragment")
public class ApPriceDialog extends BaseDialogFragment implements View.OnClickListener {

    @BindView(R.id.dialog_rootview)
    RelativeLayout mDialogRootview;

    @BindView(R.id.dialog_contentview)
    LinearLayout mDialogContentview;
    /** 标题 */
    @BindView(R.id.tv_title_appricedialog)
    TextView mTvTitle;
    /** 账期价格列表 */
    @BindView(R.id.erv_price_appricedialog)
    EasyRecyclerView mErvPrice;
    /** 顶部取消按钮 */
    @BindView(R.id.btn_top_cancel_appricedialog)
    Button mBtnCancel;


    private LayoutInflater mInflater;

    ShPriceBean.DataBean mBean;

    @Override
    protected int getResId() {
        return R.layout.dialog_ap_price;
    }

    public static ApPriceDialog newInstance(ShPriceBean.DataBean bean) {
        ApPriceDialog apPriceDialog = new ApPriceDialog();
        Bundle args = new Bundle();
        args.putParcelable(DIALOG_BEAN, bean);
        apPriceDialog.setArguments(args);
        return apPriceDialog;
    }

    public ApPriceDialog() {

    }

    @Override
    protected void init() {
        super.init();
        Bundle arguments = getArguments();
        mBean = arguments.getParcelable(DIALOG_BEAN);
        mInflater = LayoutInflater.from(mContext);

    }

    @Override
    protected void initData() {
        if (mBean == null && mBean.getPrice() == null){
            dismiss();
            return;
        }

        setTitle();
        initErv();
    }

    @Override
    protected void initEvent() {

    }

    private void setTitle() {
        if(mBean.getDay() == 0){
            mTvTitle.setText(getString(R.string.not_open_accounts));
        }else {
            mTvTitle.setText(Utils.getString(R.string.open_accounts_day, mBean.getDay()));
        }
    }

    private List<ShPriceBean.DataBean.PriceBean> getData(){
        List<ShPriceBean.DataBean.PriceBean> list = new ArrayList<>();
        for(ShPriceBean.DataBean.PriceBean bean : mBean.getPrice()){
            if(bean.getDay() == mBean.getDay()){
                bean.setDefault(true);
            }
            list.add(bean);
        }
        return list;
    }

    private void initErv(){

        RecyclerArrayAdapter adapter = new RecyclerArrayAdapter<ExpensesRecordBean.DataEntity>(mContext) {

            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new ApPriceViewHolder(parent, mContext);
            }
        };
        adapter.addAll(getData());

        mErvPrice.setLayoutManager(new LinearLayoutManager(mContext));
        mErvPrice.addItemDecoration(new DividerDecoration(Utils.getColor(R.color.transparent),
                Utils.dip2px(mContext, 0.5f),
                Utils.dip2px(mContext, 15), 0));

        mErvPrice.setAdapterWithProgress(adapter);
    }


    @Override
    protected EditText getEt() {
        return null;
    }

    @OnClick({R.id.btn_top_cancel_appricedialog})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_top_cancel_appricedialog:                    /* 确认按钮 */
                dismiss();
                break;


            default:
                break;
        }
    }

}

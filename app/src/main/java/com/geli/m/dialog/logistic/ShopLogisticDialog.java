package com.geli.m.dialog.logistic;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.bean.ShopLogisticBean;
import com.geli.m.dialog.base.BaseDialogFragment;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import static com.geli.m.dialog.logistic.DividerItemDecoration.VERTICAL_LIST;

/**
 * author:  shen
 * date:    2019/3/23
 * <p>
 * 批发商的物流信息
 */
@SuppressLint("ValidFragment")
public class ShopLogisticDialog extends BaseDialogFragment {


    @BindView(R.id.erv_ShopLogisticDialog)
    EasyRecyclerView mErv;
    @BindView(R.id.iv_close_ShopLogisticDialog)
    ImageView mIvClose;

    RecyclerArrayAdapter mAdapter;


    static final String ARG_BEAN = "arg_bean";
    ShopLogisticBean mBean;

//    public static ShopLogisticDialog newInstance(ShopLogisticBean bean) {
//        ShopLogisticDialog dialog = new ShopLogisticDialog();
//        Bundle args = new Bundle();
//        args.putParcelable(ARG_BEAN, bean);
//        return dialog;
//    }
//
//    @Override
//    protected void init() {
//        super.init();
//        Bundle arguments = getArguments();
//        mBean = arguments.getParcelable(ARG_BEAN);
//    }

    public ShopLogisticDialog(ShopLogisticBean bean){
        mBean = bean;
    }

    @Override
    protected int getResId() {
        return R.layout.dialog_shop_logistic;
    }

    @Override
    protected void initData() {
        setCancelable(true);
    }

    @Override
    protected void initEvent() {
        setErv();
        setData();
    }
    private void setErv(){

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mErv.addItemDecoration(new DividerItemDecoration(mContext, VERTICAL_LIST,
                Utils.dip2px(mContext, 1f), R.color.text_color, true));
        mErv.setLayoutManager(layoutManager);
        mErv.setAdapter(initAdapter());
    }

    private RecyclerArrayAdapter initAdapter(){
        mAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new ShopLogisticVH(parent, mContext);
            }
        };
        return mAdapter;
    }

    private void setData() {
        if(mBean != null && mBean.getData() != null && mBean.getData().size() > 0){
            mAdapter.clear();
            mAdapter.addAll(mBean.getData());
        }
    }


    @Override
    protected EditText getEt() {
        return null;
    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            Window window = dialog.getWindow();
            window.setLayout((int) (dm.widthPixels * 0.85), (int) (dm.heightPixels * 0.7));
        }
    }

    @OnClick(R.id.iv_close_ShopLogisticDialog)
    public void onViewClicked() {
        dismiss();
    }
}

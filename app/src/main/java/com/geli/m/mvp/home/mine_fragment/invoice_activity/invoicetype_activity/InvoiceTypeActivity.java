package com.geli.m.mvp.home.mine_fragment.invoice_activity.invoicetype_activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.InvoiceBean;
import com.geli.m.bean.InvoiceTitleBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.mvp.home.mine_fragment.invoice_activity.invoicetype_activity.addoreditinvoice_activity.AddOrEditInvoiceActivity;
import com.geli.m.utils.EasyRecyclerViewUtils;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by Steam_l on 2018/1/2.
 * <p>
 * 发票类型
 */
public class InvoiceTypeActivity extends MVPActivity<InvoiceTypePresentImpl> implements View.OnClickListener, InvoiceTypeView {

    /**
     * 标题名称
     */
    @BindView(R.id.tv_title_name)
    TextView mTvTitle;
    /**
     * 标题的右边按钮 -- 管理 -- 删除
     */
    @BindView(R.id.tv_title_right)
    TextView mTvRight;


    @BindView(R.id.erv_invoice_list)
    EasyRecyclerView mErvList;

    /**
     * 包裹"添加发票"的布局
     */
    @BindView(R.id.rl_invoice_bottom)
    RelativeLayout mRlayoutBottom;
    /**
     * 添加发票的按钮
     */
    @BindView(R.id.tv_invoice_bottom)
    TextView mTvBottom;

    /**
     * 点击将不使用发票
     */
    @BindView(R.id.tv_invoice_clicknouser)
    TextView mTvNouser;

    /**
     * 管理
     */
    public static final int INVOICEMODE_MANAGER = 1 << 1;
    /**
     * 选择
     */
    public static final int INVOICEMODE_SELECT = 1 << 2;
    /**
     * 当前类型
     */
    int mCurrentMode = INVOICEMODE_MANAGER;
    /**
     * true:正在选择删除要删除的发票
     */
    boolean isShowSelect = false;
    private RecyclerArrayAdapter mAdapter;
    private List mInvoiceList;

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getInvoiceList(GlobalData.getUser_id());
    }

    @Override
    protected int getResId() {
        return R.layout.activity_invoice_type;
    }

    @Override
    protected void init() {
        super.init();
        Intent intent = getIntent();
        if (intent != null) {
            mCurrentMode = intent.getIntExtra(Constant.INTENT_MODE, INVOICEMODE_MANAGER);
        }
    }

    @Override
    protected void initData() {
        initErv();
        EasyRecyclerViewUtils.initEasyRecyclerView(mErvList);
        setView(mCurrentMode);
    }

    @Override
    protected void initEvent() {

    }

    private void initErv() {
        mInvoiceList = new ArrayList();
        mErvList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mErvList.addItemDecoration(new DividerDecoration(Utils.getColor(R.color.line_color), Utils.dip2px(mContext, 10)));
        mErvList.setAdapterWithProgress(initAdapter());
    }

    private RecyclerArrayAdapter initAdapter() {
        return mAdapter = new RecyclerArrayAdapter(mContext) {
            final int title = 1 << 0;
            final int content = 1 << 1;

            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                if (viewType == title) {
                    return new InvoiceTitleViewHolder(parent, mContext);            // 发票类型 -- 普通发票/专用发票/个人发票
                } else {
                    return new InvoiceContentViewHolder(parent, mContext);          // 发票内容
                }
            }

            @Override
            public int getViewType(int position) {
                if (mInvoiceList.get(position) instanceof InvoiceTitleBean) {
                    return title;
                }
                return content;
            }
        };
    }

    private void setView(int mode) {
        if (mode == INVOICEMODE_SELECT) {                                    /* 选择 */
            mTvTitle.setText(Utils.getString(R.string.title_invoiceselect));
            mTvRight.setText(Utils.getString(R.string.manager));
            mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Object item = mAdapter.getItem(position);
                    if (item instanceof InvoiceBean.DataEntity) {
                        Intent intent = new Intent(Constant.BROADCAST_INVOICE);
                        intent.putExtra(Constant.BROADCAST_DATA, (InvoiceBean.DataEntity) item);
                        mContext.sendBroadcast(intent);
                        finish();
                    }
                }
            });
            mTvNouser.setVisibility(View.VISIBLE);

        } else {                                                            /* 管理 -- 发票类型 */
            mTvTitle.setText(Utils.getString(R.string.title_invoicemanager));
            mTvNouser.setVisibility(View.GONE);

            // 标题右边设置"垃圾桶"图片
            Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.nav_btn_lajitong_nor);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            mTvRight.setCompoundDrawables(drawable, null, null, null);
        }
    }

    public int getCurrMode() {
        return mCurrentMode;
    }

    public boolean isShowSelect() {
        return isShowSelect;
    }


    @OnClick({R.id.iv_title_back, R.id.tv_invoice_clicknouser, R.id.tv_title_right, R.id.rl_invoice_bottom})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;

            case R.id.tv_title_right:                               /* 选择模式：管理 -- 管理模式：删除/完成/ */
                clickTitleRightBtn();
                break;

            case R.id.rl_invoice_bottom:
                clickBottomBtn();
                break;

            case R.id.tv_invoice_clicknouser:                       /* 点击将不适用发票 */
                Intent intent = new Intent(Constant.BROADCAST_INVOICE);
                mContext.sendBroadcast(intent);
                finish();
                break;

            default:
                break;
        }
    }

    /**
     * 点击底部按钮
     */
    private void clickBottomBtn() {
        if (isShowSelect) {                     /* 正在选择删除要删除的发票 */
            deleteInvoice();
        } else {                                /* 添加发票 */
            startActivity(AddOrEditInvoiceActivity.class, new Intent());
        }
    }

    /**
     * 点击右边按钮 -- 删除/完成/管理
     */
    private void clickTitleRightBtn() {
        if (mCurrentMode == INVOICEMODE_SELECT) {                       /* 选择 */
            // 去"管理模式"
            Intent intent = new Intent();
            intent.putExtra(Constant.INTENT_MODE, InvoiceTypeActivity.INVOICEMODE_MANAGER);
            startActivity(InvoiceTypeActivity.class, intent);

        } else {                                                        /* 管理 */
            isShowSelect = !isShowSelect;
            // 设置底部的按钮 -- 添加发票 / 删除
            if (isShowSelect) {                           /* --删除-- */
                mRlayoutBottom.setBackgroundColor(Utils.getColor(R.color.zhusediao));
                mTvBottom.setText(Utils.getString(R.string.delete));
                mTvBottom.setCompoundDrawables(null, null, null, null);
                mTvBottom.setTextColor(Utils.getColor(R.color.white));
                mTvRight.setCompoundDrawables(null, null, null, null);
                mTvRight.setText(Utils.getString(R.string.complete));

            } else {                                      /* --普通-- */
                mTvBottom.setText(Utils.getString(R.string.add_invoice));
                mTvBottom.setTextColor(Utils.getColor(R.color.zhusediao));
                Drawable drawableBottom = ContextCompat.getDrawable(mContext, R.drawable.btn_jiahao_bor40);
                drawableBottom.setBounds(0, 0, drawableBottom.getIntrinsicWidth(), drawableBottom.getIntrinsicHeight());
                mTvBottom.setCompoundDrawables(drawableBottom, null, null, null);
                mRlayoutBottom.setBackgroundColor(Utils.getColor(R.color.white));
                Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.nav_btn_lajitong_nor);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                mTvRight.setCompoundDrawables(drawable, null, null, null);
                mTvRight.setText("");
            }

            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 删除发票
     */
    private void deleteInvoice() {
        List allData = mAdapter.getAllData();
        String deleteId = "";
        for (int i = 0; i < allData.size(); i++) {
            if (allData.get(i) instanceof InvoiceBean.DataEntity) {
                InvoiceBean.DataEntity entity = (InvoiceBean.DataEntity) allData.get(i);
                if (entity.isCheck) {
                    deleteId += entity.getInvoice_id() + ",";
                }
            }
        }
        if (deleteId.length() > 1) {
            deleteId = deleteId.substring(0, deleteId.length() - 1);        // 去掉最后的--","
            mPresenter.deleteInvoice(GlobalData.getUser_id(), deleteId);
        } else {
            ToastUtils.showToast(mContext.getString(R.string.please_choose_the_option_to_delete));
        }
    }

    @Override
    protected InvoiceTypePresentImpl createPresenter() {
        return new InvoiceTypePresentImpl(this);
    }

    @Override
    public void onSuccess(String msg) {
        ToastUtils.showToast(msg);
        mPresenter.getInvoiceList(GlobalData.getUser_id());
    }

    @Override
    public void showLoading() {
        Utils.uProgressDialog(mContext);
    }

    @Override
    public void hideLoading() {
        Utils.dismissProgress();
    }

    @Override
    public void showList(List<InvoiceBean.DataEntity> list) {
        mInvoiceList.clear();
        mAdapter.clear();
        if (list == null || list.size() == 0) {
            mErvList.showEmpty();
            return;
        }
        Collections.sort(list);                  // 排序
        setErvData(list);
    }

    /**
     * 设置列表的数据
     *
     * @param list
     */
    private void setErvData(List<InvoiceBean.DataEntity> list) {
        InvoiceTitleBean invoiceTitleBean = null;
        if (invoiceTitleBean == null) {
            invoiceTitleBean = new InvoiceTitleBean();
        }
        for (int i = 0; i < list.size(); i++) {
            InvoiceBean.DataEntity dataEntity = list.get(i);
            int belong = dataEntity.getBelong();
            int type = dataEntity.getType();

            // 插入标题
            invoiceTitleBean = addErvTitle(invoiceTitleBean, belong, type);

            // 插入"内容"
            mInvoiceList.add(dataEntity);
        }
        mAdapter.addAll(mInvoiceList);
    }

    /**
     * 插入标题
     *
     * @param invoiceTitleBean
     * @param belong
     * @param type
     * @return 应为是在循环中，要反复获取数据
     */
    @NonNull
    private InvoiceTitleBean addErvTitle(InvoiceTitleBean invoiceTitleBean, int belong, int type) {
        if (belong == Constant.INVOICE_UNIT) {                             /* 单位 */
            if (invoiceTitleBean.type != type) {       // 单位有两个人
                invoiceTitleBean = new InvoiceTitleBean();
                invoiceTitleBean.type = type;
                if (type == Constant.INVOICE_UNIT_SPECIAL) {  /* --增值专用-- */
                    invoiceTitleBean.title = Utils.getString(R.string.invoice_VATdedicatedinvoice);
                } else {                        /* --增值普通-- */
                    invoiceTitleBean.title = Utils.getString(R.string.invoice_VATordinaryinvoice);
                }
                mInvoiceList.add(invoiceTitleBean);
            }
        } else {                                                /* 个人 */
            if (invoiceTitleBean.belong != belong) {    // 不"单位"就是"个人"的！
                invoiceTitleBean = new InvoiceTitleBean();
                invoiceTitleBean.belong = belong;
                invoiceTitleBean.title = Utils.getString(R.string.personal_invoice);
                mInvoiceList.add(invoiceTitleBean);
            }
        }
        return invoiceTitleBean;
    }
}

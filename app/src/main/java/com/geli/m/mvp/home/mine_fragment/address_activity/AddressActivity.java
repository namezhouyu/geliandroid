package com.geli.m.mvp.home.mine_fragment.address_activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.AddressListBean;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.mvp.home.mine_fragment.address_activity.addaddress_activity.AddEditAddressActivity;
import com.geli.m.utils.EasyRecyclerViewUtils;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import java.util.List;

import static com.geli.m.config.Constant.BROADCAST_ADDRESS;
import static com.geli.m.config.Constant.BROADCAST_DATA;
import static com.geli.m.config.Constant.INTENT_MODE;

/**
 * Created by Steam_l on 2018/1/4.
 *
 * 地址管理
 */
public class AddressActivity extends MVPActivity<AddressListPresentImpl> implements View.OnClickListener, AddressListView, SwipeRefreshLayout.OnRefreshListener {

    /** 标题的右边按钮 */
    @BindView(R.id.tv_title_right)
    TextView mTvRight;
    /** 标题 */
    @BindView(R.id.tv_title_name)
    TextView mTvName;
    /** 地址列表 */
    @BindView(R.id.erv_address_list)
    EasyRecyclerView mErvList;
    /** 包裹"添加地址"的布局 */
    @BindView(R.id.rl_address_bottom)
    RelativeLayout mRLayoutBottom;
    /** 添加地址 -- 文本按钮 */
    @BindView(R.id.tv_address_bottom)
    TextView mTvBottom;

    public static final int ADDRESSMODE_MANAGER = 1 << 0;//管理
    public static final int ADDRESSMODE_SELECT = 1 << 1; //选择

    int mCurrMode = ADDRESSMODE_MANAGER;
    private boolean isShowSelect = false;
    private RecyclerArrayAdapter mAdapter;

    @Override
    protected AddressListPresentImpl createPresenter() {
        return new AddressListPresentImpl(this);
    }


    @Override
    protected int getResId() {
        return R.layout.activity_address;
    }

    @Override
    protected void init() {
        super.init();
        Intent intent = getIntent();
        if (intent != null) {
            mCurrMode = intent.getIntExtra(INTENT_MODE, mCurrMode);
        }
    }

    @Override
    protected void initData() {
        setErv();
        setView();
    }

    @Override
    protected void initEvent() {
        mErvList.setRefreshListener(this);
    }

    private void setErv() {
        DividerDecoration itemDecoration = new DividerDecoration(Utils.getColor(R.color.line_color), Utils.dip2px(mContext, 10));
        itemDecoration.setDrawLastItem(true);
        mErvList.addItemDecoration(itemDecoration);
        mErvList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mErvList.setAdapterWithProgress(mAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new AddressViewHolder(parent, mContext);
            }
        });

        EasyRecyclerViewUtils.initEasyRecyclerView(mErvList, R.layout.layout_empty_address, R.layout.layout_error_data);
    }


    private void setView() {
        if (mCurrMode == ADDRESSMODE_SELECT) {                              /* 选择模式 */
            mTvName.setText(Utils.getString(R.string.title_selectadd));
            mTvRight.setText(Utils.getString(R.string.manager));
            mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    if (!GlobalData.hasNetWork) {
                        ToastUtils.showToast(Utils.getString(R.string.message_unknownhost));
                        return;
                    }

                    // 地址修改后，广播通知下
                    final AddressListBean.DataEntity entity = (AddressListBean.DataEntity) mAdapter.getItem(position);
                    Intent intent = new Intent(BROADCAST_ADDRESS);
                    intent.putExtra(BROADCAST_DATA, entity);
                    mContext.sendBroadcast(intent);
                    finish();
                }
            });

        } else {                                                            /* 管理模式 */
            mTvName.setText(Utils.getString(R.string.title_addmanager));
            Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.nav_btn_lajitong_nor);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            mTvRight.setCompoundDrawables(drawable, null, null, null);
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        onRefresh();
    }

    public int getAddressMode() {
        return mCurrMode;
    }

    @OnClick({R.id.iv_title_back, R.id.tv_title_right, R.id.rl_address_bottom})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_title_right:
                clickRightBtn();
                break;

            case R.id.rl_address_bottom:
                clickBottomBtn();
                break;

            case R.id.iv_title_back:
                finish();
                break;

            default:
                break;
        }
    }

    /**
     * 底部 -- 删除/添加地址 按钮
     */
    private void clickBottomBtn() {
        if (isShowSelect) {                     /* 正在选择删除 */
            deleteAddress();
        } else {
            startActivity(new Intent(mContext, AddEditAddressActivity.class));
        }
    }

    /**
     * 点击标题右边的按钮
     */
    private void clickRightBtn() {
        if (mCurrMode == ADDRESSMODE_SELECT) {                              /* 选择模式 */
            Intent intent = new Intent();
            intent.putExtra(INTENT_MODE, AddressActivity.ADDRESSMODE_MANAGER);
            startActivity(AddressActivity.class, intent);

        } else {                                                            /* 管理模式 -- 删除 */
            isShowSelect = !isShowSelect;
            if (isShowSelect) {                     // 正在勾选要删除的 -- 按钮名称是：完成
                mRLayoutBottom.setBackgroundColor(Utils.getColor(R.color.zhusediao));
                mTvBottom.setText(Utils.getString(R.string.delete));
                mTvBottom.setCompoundDrawables(null, null, null, null);
                mTvBottom.setTextColor(Utils.getColor(R.color.white));
                mTvRight.setCompoundDrawables(null, null, null, null);
                mTvRight.setText(Utils.getString(R.string.complete));

            } else {                                // 没有选择 -- 按钮是：删除图片
                mRLayoutBottom.setBackgroundColor(Utils.getColor(R.color.white));
                mTvBottom.setText(Utils.getString(R.string.add_address));
                Drawable drawableBottom = ContextCompat.getDrawable(mContext, R.drawable.btn_jiahao);
                drawableBottom.setBounds(0, 0, drawableBottom.getIntrinsicWidth(), drawableBottom.getIntrinsicHeight());
                mTvBottom.setCompoundDrawables(drawableBottom, null, null, null);
                mTvBottom.setTextColor(Utils.getColor(R.color.zhusediao));
                Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.nav_btn_lajitong_nor);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                mTvRight.setCompoundDrawables(drawable, null, null, null);
                mTvRight.setText("");
            }

            mAdapter.notifyDataSetChanged();            // viewHolder 里面根据isShowSelect设置项是否打开
        }
    }

    /**
     * 删除地址
     */
    private void deleteAddress() {
        String deleteId = "";
        List<AddressListBean.DataEntity> allData = mAdapter.getAllData();
        for (AddressListBean.DataEntity bean : allData) {
            if (bean.isCheck) {
                deleteId += bean.getAddress_id() + ",";
            }
        }
        if (TextUtils.isEmpty(deleteId)) {
            ToastUtils.showToast(Utils.getString(R.string.message_palease_selectdelete));
            return;
        }
        if(deleteId.length() > 1) {
            String substring = deleteId.substring(0, deleteId.length() - 1);
            mPresenter.deleteAddress(GlobalData.getUser_id(), substring);
        }else {
            ToastUtils.showToast(mContext.getString(R.string.please_choose_the_option_to_delete));
        }
    }

    public boolean getIsShowSelect() {
        return isShowSelect;
    }

    @Override
    public void onError(String msg){
        ToastUtils.showToast( msg);
    }

    @Override
    public void showLoading() {
        if (!mErvList.getSwipeToRefresh().isRefreshing()) {
            mErvList.setRefreshing(true);
        }
    }


    @Override
    public void hideLoading() {
        mErvList.setRefreshing(false);
    }

    @Override
    public void showList(AddressListBean bean) {
        if (bean == null || bean.getData() == null || bean.getData().size() == 0) {
            mErvList.showEmpty();
            return;
        }
        mErvList.showRecycler();
        mAdapter.clear();
        mAdapter.addAll(bean.getData());
    }

    @Override
    public void deleteSuccess(String add_id) {
        List<AddressListBean.DataEntity> allData = mAdapter.getAllData();
        for (int i = allData.size() - 1; i >= 0; i--) {
            if (allData.get(i).isCheck) {
                mAdapter.remove(i);
            }
        }
        if (mAdapter.getAllData().size() == 0) {
            mErvList.showEmpty();
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.getAddressList(GlobalData.getUser_id());
    }
}

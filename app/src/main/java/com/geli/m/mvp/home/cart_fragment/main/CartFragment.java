package com.geli.m.mvp.home.cart_fragment.main;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.CartBean;
import com.geli.m.bean.SpecifiBean;
import com.geli.m.coustomview.ErrorView;
import com.geli.m.dialog.TipDialog;
import com.geli.m.dialog.addcart.AddCartBottomDialog;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.base.MVPFragment;
import com.geli.m.mvp.home.cart_fragment.main.CartGoodsViewHolder.GoodsClickListener;
import com.geli.m.mvp.home.cart_fragment.main.CartShopViewHolder.ShopClickListener;
import com.geli.m.mvp.home.cart_fragment.tempaccountperiod_activity.TempAccountPeriodActivity;
import com.geli.m.mvp.home.other.accountperiod_activity.AccountPeriodActivity;
import com.geli.m.mvp.home.other.goodsdetails_activity.GoodsDetailsActivity;
import com.geli.m.mvp.home.other.login_register_activity.LoginActivity;
import com.geli.m.mvp.home.other.shopdetails_activity.ShopDetailsActivity;
import com.geli.m.mvp.home.other.submitorder_activity.main.SubmitOrderActivity;
import com.geli.m.orther.SwipeDeleteManager;
import com.geli.m.utils.EasyRecyclerViewUtils;
import com.geli.m.utils.KeyBoardUtils;
import com.geli.m.utils.LoginInformtaionSpUtils;
import com.geli.m.utils.RxUtils;
import com.geli.m.utils.StringUtils;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL;
import static com.geli.m.config.Constant.AP_OtherStatus_Cancel;
import static com.geli.m.config.Constant.AP_OtherStatus_NormalUse;
import static com.geli.m.config.Constant.AP_OtherStatus_Revising;
import static com.geli.m.config.Constant.AP_STATUS;
import static com.geli.m.config.Constant.AP_Status_AlreadyOpened;
import static com.geli.m.config.Constant.AP_TempAp_Apply;
import static com.geli.m.config.Constant.AP_TempAp_No;
import static com.geli.m.config.Constant.AP_TempAp_Yes;
import static com.geli.m.config.Constant.AP_UseStatus_BeOverdue;
import static com.geli.m.config.Constant.AP_shop_status_on_open;
import static com.geli.m.config.Constant.AP_shop_status_open;
import static com.geli.m.config.Constant.Goods_State_Invalid;
import static com.geli.m.config.Constant.INTENT_BEAN;
import static com.geli.m.config.Constant.INTENT_CART_ID;
import static com.geli.m.config.Constant.INTENT_GOODS_ID;
import static com.geli.m.config.Constant.INTENT_IS_SH;
import static com.geli.m.config.Constant.INTENT_SHOP_ID;
import static com.geli.m.config.Constant.INTENT_TYPE;

/**
 * Created by Steam_l on 2017/12/22.
 *
 * 购物车
 */
public class CartFragment extends MVPFragment<CartPresentImpl> implements
        View.OnClickListener, CartView, SwipeRefreshLayout.OnRefreshListener {

    /** 包裹标题 */
    @BindView(R.id.toolbar_cart)
    Toolbar mToolbar;
    /** 标题名 */
    @BindView(R.id.tv_title_name)
    TextView mTvTitle;
    /** 标题左边的"返回按钮" */
    @BindView(R.id.iv_title_back)
    ImageView mIvBack;
    /** 标题右边的"删除/完成" */
    @BindView(R.id.tv_title_right)
    TextView mTvRight;


    /** 已超出账期使用额度。 */
    @BindView(R.id.tv_toast_cf)
    TextView mTvToast;

    /** 未登录 布局 */
    @BindView(R.id.rl_cart_nologin)
    RelativeLayout mRlNoLogin;
    /** 已登录 布局 */
    @BindView(R.id.ll_cart_root)
    LinearLayout mLlRoot;

    /** 购物车列表 */
    @BindView(R.id.erv_cart_list)
    EasyRecyclerView mErvList;


    /** 包裹使用账期的布局 */
    @BindView(R.id.layout_cart_ap)
    LinearLayout mLlAP;
    /** 使用账期 */
    @BindView(R.id.cb_cart_use_ap)
    CheckBox mCbUseAp;
    /** 账期 */
    @BindView(R.id.tv_cart_ap)
    TextView mTvAp;


    /** 全选按钮 */
    @BindView(R.id.cb_cart_checkall)
    CheckBox mCbCheckAll;

    /** 包裹"运费"和"合计" */
    @BindView(R.id.ll_cart_bottom_price_layout)
    LinearLayout mLlBottomLayout;
    /** 合计 */
    @BindView(R.id.tv_cart_totalprice)
    TextView mTvTotlaPrice;

    /** 结算/删除 按钮 */
    @BindView(R.id.bt_cart_settlement)
    Button mBtnSettlement;

    public static final int CART_MODE_EDIT = 1 << 0;            // 编辑
    public static final int CART_MODE_SETTLEMENT = 1 << 1;      // 结算
    int CURR_MODE = CART_MODE_EDIT;

    private boolean isShow = false;

    private RecyclerArrayAdapter mAdapter;

    private CartBean.DataEntity.CartListEntity mEditBean;


    /** 当前修改的tempNumber */
    private int mTempNumber = 0;
    /** 编辑前后差值 */
    private int mDifferenceNumber;
    private int mCurrEditCartId = 0;
    private int mCurrEditShopId;

    private int mLastoffset;
    private int mLastposition;

    private long mLastTime;

    private boolean isShowAp = true;

    @Override
    public int getResId() {
        return R.layout.fragment_cart;
    }

    @Override
    protected void init() {
        super.init();
        ImmersionBar.setTitleBar(getActivity(), mToolbar);
    }

    @Override
    protected void initData() {

        mTvTitle.setText(Utils.getString(R.string.title_cart));
        mIvBack.setVisibility(View.GONE);

        changeMode();
        initView();
        setAdapter();
        setErv();
    }


    @Override
    protected void initEvent() {
    }

    /**
     * 改变模式
     */
    private void changeMode() {
        if (SwipeDeleteManager.getInstance().haveOpened()) {        // 是否有item已经打开 -- 显示item右边删除按钮
            SwipeDeleteManager.getInstance().clear();

        } else {
            if (CURR_MODE == CART_MODE_EDIT) {
                CURR_MODE = CART_MODE_SETTLEMENT;            // 结算模式
                Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.nav_btn_lajitong_nor);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                mTvRight.setCompoundDrawables(drawable, null, null, null);
                mTvRight.setText("");
                mLlBottomLayout.setVisibility(View.VISIBLE);
                if(isShowAp)
                    mLlAP.setVisibility(View.VISIBLE);

            } else {
                CURR_MODE = CART_MODE_EDIT;                 // 删除（编辑）模式
                mTvRight.setText(Utils.getString(R.string.complete));
                mTvRight.setCompoundDrawables(null, null, null, null);
                mLlBottomLayout.setVisibility(View.INVISIBLE);
                if(isShowAp)
                    mLlAP.setVisibility(View.GONE);
            }
        }

        changeBtnStr(0);        // 改变"结算/删除"按钮文字
    }

    /**
     * 改变"结算/删除"按钮文字
     * @param number            选定的项
     */
    public void changeBtnStr(int number) {
        if (CURR_MODE == CART_MODE_EDIT) {
            mBtnSettlement.setBackgroundResource(R.drawable.bgred_lefttopbot2r_shape);
            mBtnSettlement.setText(Utils.getString(R.string.delete_cart, number));
            mBtnSettlement.setEnabled(true);
        } else {
            mBtnSettlement.setBackgroundResource(R.drawable.selector_btn_bg_yelllow_gray);
            mBtnSettlement.setText(Utils.getString(R.string.settlement, number));
        }
    }

    /**
     * 取消全部选中，并将总价格设置为0
     */
    public void initView() {
        setTotalPrice(0.0);
        mCbCheckAll.setChecked(false);
    }

    /**
     * 设置总价格
     * @param totalPrice
     */
    public void setTotalPrice(Double totalPrice) {
        mTvTotlaPrice.setText(Utils.getString(R.string.overseas_list_money, Utils.getFormatDoubleTwoDecimalPlaces(totalPrice, 2)));
    }


    private void setAdapter(){
        mAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new CartShopViewHolder(parent, mContext,
                        new ShopClickListener() {
                            @Override
                            public void cbShopClickVH(int position, CartBean.DataEntity data) {
                                cbShopClick(position, data);
                            }

                            /* 临时账期 */
                            @Override
                            public void applyApTempClick(int shopId, CartBean.DataEntity.ShEntity shEntity) {
                                applyApTemp(shopId, shEntity);
                            }

                            @Override
                            public void openApClick(int shopId) {
                                startActivity(AccountPeriodActivity.class, new Intent()
                                        .putExtra(AP_STATUS, AP_Status_AlreadyOpened)
                                        .putExtra(INTENT_SHOP_ID, shopId));
                            }
                        },

                        new GoodsClickListener() {
                            @Override
                            public void onGoodsItemClick(int position, String goodsId) {
                                itemClick(goodsType, goodsId);
                            }

                            @Override
                            public void cbGoodsCheckVH(CartBean.DataEntity.CartListEntity data) {
                                cbGoodsCheck(data);
                            }


                            @Override
                            public void updateShopMoqVH(CartBean.DataEntity.CartListEntity data, int number, boolean isDelay) {
                                updateShopMoq(data, number, isDelay);
                            }

                            @Override
                            public void selectSpecificVH(CartBean.DataEntity.CartListEntity data) {
                                selectSpecific(data);
                            }

                            @Override
                            public void editCartVH(Map data, int number, int shopId) {
                                editCart(data, number, shopId);
                            }

                            @Override
                            public void deleteGoodsVH(String cart_id) {
                                deleteGoods(cart_id);
                            }

                            @Override
                            public int getCurrMode() {
                                return CURR_MODE;
                            }

                            @Override
                            public void showEditVH(CartBean.DataEntity.CartListEntity data) {
                                showEdit(data);
                            }

                            @Override
                            public FragmentManager getFragmentM() {
                                return getFragmentManager();
                            }
                        });
            }
        };

        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                CartBean.DataEntity item = (CartBean.DataEntity) mAdapter.getItem(position);
                itemClick(shopType, item.getShop_id() + "");
            }
        });
    }


    /**
     * 显示 商品编辑的布局(item里的) -- 已经打开的就关闭
     * @param data
     */
    private void showEdit(CartBean.DataEntity.CartListEntity data) {
        int i = 0;
        for (Object shopEntityTemp : mAdapter.getAllData()){
            CartBean.DataEntity shopEntity = (CartBean.DataEntity)shopEntityTemp;
            int j = 0;
            if(shopEntity.getShop_id() == data.shop_id){  // 要显示的
                for(CartBean.DataEntity.CartListEntity goodsEntity : shopEntity.getCart_list()){
                    if(goodsEntity.getCart_id() == data.getCart_id()){
                        shopEntity.getCart_list().get(j).isShowEdit = true;
                        mAdapter.notifyItemChanged(i, shopEntity);
                    }else {
                        if(shopEntity.getCart_list().get(j).isShowEdit){
                            shopEntity.getCart_list().get(j).isShowEdit = false;
                            mAdapter.notifyItemChanged(i, shopEntity);
                        }
                    }
                    j++;
                }
            }else {
                for(CartBean.DataEntity.CartListEntity goodsEntity : shopEntity.getCart_list()){
                    if(shopEntity.getCart_list().get(j).isShowEdit){
                        shopEntity.getCart_list().get(j).isShowEdit = false;
                        mAdapter.notifyItemChanged(i, shopEntity);
                    }
                    j++;
                }
            }
            i++;
        }
    }


    private void setErv() {

        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mErvList.setLayoutManager(manager);
        mErvList.getRecyclerView().setHasFixedSize(false);
        mErvList.setAdapterWithProgress(mAdapter);
        mErvList.setRefreshListener(this);


        // 设置"空数据"
        ErrorView errorId = new ErrorView(mContext);
        errorId.setClickRefreshListener(new ErrorView.ClickRefreshListener() {
            @Override
            public void clickRefresh() {
                requestNetwork();
            }
        });
        EasyRecyclerViewUtils.initEasyRecyclerView(mErvList, R.layout.layout_empty_cart, errorId);

        // 滑动事件
        mErvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == SCROLL_STATE_TOUCH_SCROLL) {
                    KeyBoardUtils.hide_keyboard((BaseActivity) mContext);
                }

                if (recyclerView.getLayoutManager() != null) {
                    getPositionAndOffset(recyclerView.getLayoutManager());
                }
            }
        });


        // mErvList 加载完毕后调用
        mErvList.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                setBtnSettlement();
                setTitlePriceAndNumber();
            }
        });
    }

    String mMessage = "";
    boolean isFirst = true;
    boolean isEnabled = false;
    View.OnClickListener mApClickListener;

    /**
     * 结算
     */
    private void setBtnSettlement() {

        mMessage = "";
        isFirst = true;
        isEnabled = true;
        mApClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };


        for(int i=0; i<mAdapter.getAllData().size(); i++){
            CartBean.DataEntity shopEntity = (CartBean.DataEntity) mAdapter.getAllData().get(i);

            CartBean.DataEntity.ShEntity shEntity = shopEntity.getSh();
            if(shopEntity.isUseAP && getSelectItem(shopEntity)) {
                if(shopEntity.shop_sh_status == AP_shop_status_open) {
                    if (shEntity != null) {
                        switch (shopEntity.getSh().getStatus()) {
                            case AP_OtherStatus_Cancel:                          /* 0取消账期权限 */
                                isEnabled &= false;
                                setMessage(Utils.getString(R.string.unopen_go_open), shopEntity);
                                break;

                            case AP_OtherStatus_NormalUse:                        /* 1开通权限 */
                                setBeOverdue(shopEntity);
                                break;

                            case AP_OtherStatus_Revising:                         /* 2账期修改中 */
                                isEnabled &= false;
                                setMessage(Utils.getString(R.string.adjustment_of_accounts), shopEntity);
                                break;

                            default:
                                break;
                        }
                    } else {
                        if(shopEntity.getSh_apply() == 0) {
                            isEnabled &= false;
                            setMessage(Utils.getString(R.string.unopen_go_open), shopEntity);
                        }else {
                            isEnabled &= false;
                            setMessage(Utils.getString(R.string.apply_ap), shopEntity);
                        }
                    }
                }else if(shopEntity.shop_sh_status == AP_shop_status_on_open){
                    isEnabled &= false;
                    setMessage(Utils.getString(R.string.unopen_ap), shopEntity);
                }

            }else {                                     // 不勾选账期
                isEnabled &= true;
            }
        }

        mBtnSettlement.setEnabled(isEnabled);
        if(!isEnabled && StringUtils.isNotEmpty(mMessage) ){
            mTvAp.setText(mMessage);
            mTvToast.setText(Utils.getString(R.string.unable_to_purchase_accounts));
            mTvToast.setVisibility(View.VISIBLE);
        }else {
            mMessage = "";
            mTvAp.setText("");
            mTvToast.setText("");
            mTvToast.setVisibility(View.GONE);
        }

        mTvAp.setOnClickListener(mApClickListener);
    }

    private boolean getSelectItem(CartBean.DataEntity shopEntity){
        if(shopEntity.isCheck){
            return true;
        }else {
            boolean isSelect = false;
            for(CartBean.DataEntity.CartListEntity goodsEntity : shopEntity.getCart_list()){
                if(goodsEntity.isCheck){
                    isSelect = true;
                    break;
                }
            }
            return isSelect;
        }
    }

    /**
     * 设置逾期
     * @param shopEntity
     */
    private void setBeOverdue(final CartBean.DataEntity shopEntity){
        if(shopEntity.getSh().getSh_status() == AP_UseStatus_BeOverdue){
            setMessage(Utils.getString(R.string.ap_be_overdue), shopEntity);
        }else {
            setViewByExceed(shopEntity);
        }
    }

    /**
     * 设置超额
     * @param shopEntity
     */
    private void setViewByExceed(final CartBean.DataEntity shopEntity) {
        if(shopEntity.isExceed()) {                                     /* 超出 */
            if (shopEntity.getSh().getIs_temp() == AP_TempAp_No) {
                isEnabled &= false;
                setMessage(Utils.getString(R.string.exceed_account_go), shopEntity);
            } else if(shopEntity.getSh().getIs_temp() == AP_TempAp_Yes){
                isEnabled &= false;
                setMessage(Utils.getString(R.string.exceed_account_cannot_buy), shopEntity);
            }else if(shopEntity.getSh().getIs_temp() == AP_TempAp_Apply){         // 未申请临时账期 -- 正在申请临时账期
                isEnabled &= false;
                setMessage(Utils.getString(R.string.apply_temp_ap), shopEntity);
            }
        }else {
            isEnabled &= true;
        }
    }

    /**
     * 设置账期的 不符合的提示
     * @param message
     * @param shopEntity
     */
    private void setMessage(String message, final CartBean.DataEntity shopEntity){
        if(isFirst){
            mMessage = message;
            isFirst = false;
            mApClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartBean.DataEntity.ShEntity shEntity = shopEntity.getSh();
                    if(shopEntity.isUseAP && getSelectItem(shopEntity)) {
                        if(shopEntity.shop_sh_status == AP_shop_status_open) {
                            if (shEntity != null) {
                                switch (shopEntity.getSh().getStatus()) {
                                    case AP_OtherStatus_Cancel:                          /* 0取消账期权限 */

                                        break;

                                    case AP_OtherStatus_NormalUse:                        /* 1开通权限 */
                                        if(shopEntity.isExceed()) {                                 // 超出
                                            if (shopEntity.getSh().getIs_temp() == AP_TempAp_No) {
                                                applyApTemp(shopEntity.getShop_id(), shEntity);
                                            } else if(shopEntity.getSh().getIs_temp() == AP_TempAp_Yes){

                                            }else if(shopEntity.getSh().getIs_temp() == AP_TempAp_Apply){         // 未申请临时账期 -- 正在申请临时账期

                                            }
                                        }
                                        break;

                                    case AP_OtherStatus_Revising:                         /* 2账期修改中 */

                                        break;

                                    default:
                                        break;
                                }
                            } else {
                                if(shopEntity.getSh_apply() == 0) {
                                    startActivity(AccountPeriodActivity.class, new Intent()
                                            .putExtra(AP_STATUS, AP_Status_AlreadyOpened)
                                            .putExtra(INTENT_SHOP_ID, shopEntity.getShop_id()));
                                }else {

                                }
                            }
                        }else if(shopEntity.shop_sh_status == AP_shop_status_on_open){

                        }

                    }else {                                     // 不勾选账期

                    }
                }
            };
        }
    }


    int shopType = 0;
    int goodsType = 1;

    /**
     * 项点击事件
     * @param type
     * @param id
     */
    private void itemClick(int type, String id){
        if (System.currentTimeMillis() - mLastTime < 1000) {
            return;
        }
        mLastTime = System.currentTimeMillis();
        /* 编辑的时候 -- item 点击事件不可用 */
        if (CURR_MODE == CART_MODE_EDIT) {
            return;
        }

        Intent intent = new Intent();
        if (type == shopType) {
            intent.putExtra(INTENT_SHOP_ID, id);
            startActivity(ShopDetailsActivity.class, intent);
        } else if (type == goodsType) {
            intent.putExtra(INTENT_GOODS_ID, id);
            startActivity(GoodsDetailsActivity.class, intent);
        }
    }


    /**
     * "商店项" -- 是否勾选
     * @param position
     * @param data
     */
    private void cbShopClick(int position, CartBean.DataEntity data) {
//        for(int i=0; i<data.getCart_list().size(); i++){
//            data.getCart_list().get(i).isCheck = data.isCheck;
//        }
//        mAdapter.notifyItemChanged(position, data);

        for(int i=0; i<mAdapter.getAllData().size(); i++){
            if(i == position){                                  /* 这个商店 全选/全非选 */
                for(int j=0; j<data.getCart_list().size(); j++){
                    data.getCart_list().get(j).isCheck = data.isCheck;
                }
                mAdapter.notifyItemChanged(position, data);
            }else {                                             /* 其他商店全部全部不选 */
                CartBean.DataEntity temp = (CartBean.DataEntity) mAdapter.getAllData().get(i);
                temp.isCheck = false;
                for(int j=0; j<temp.getCart_list().size(); j++){
                    temp.getCart_list().get(j).isCheck = false;
                }
                mAdapter.notifyItemChanged(i, temp);
            }
        }

        setUserAp(mCbUseAp.isChecked());
    }

    /**
     * 设置总价格 和 选中的数量
     */
    private void setTitlePriceAndNumber() {
        int selectItem = 0;             // 选中的商品数量
        boolean isSelectAll = true;     // 是不是全部选中
        double price = 0;               // 选中的价格
        for(Object object : mAdapter.getAllData()){
            CartBean.DataEntity shopEntity = (CartBean.DataEntity) object;
            if(isSelectAll && !shopEntity.isCheck){         // 有一个未选中就设为 -- 取消全选
                isSelectAll = false;
            }

            for(CartBean.DataEntity.CartListEntity goodsEntity : shopEntity.getCart_list()){
                if(goodsEntity.isCheck && goodsEntity.getStatus() != 2){         // 选中 且 未失效
                    if(shopEntity.isUseAP){
                        if(goodsEntity.getSh_price() != 0) {
                            price += Utils.mul((double) goodsEntity.getCart_number(),
                                    goodsEntity.getSh_price());
                        }else {     // 未开通账期的 但选了使用账期
                            price += Utils.mul((double) goodsEntity.getCart_number(),
                                    Double.valueOf(goodsEntity.getCart_price()));
                        }
                    }else {
                        price += Utils.mul((double) goodsEntity.getCart_number(),
                                Double.valueOf(goodsEntity.getCart_price()));
                    }
                    selectItem ++;
                }
            }
        }

//        if(mAdapter.getAllData().size() < 1){
//            isSelectAll = false;
//        }

        // mCbCheckAll.setChecked(isSelectAll);
        // setAllState(isSelectAll);
        setTotalPrice(price);
        changeBtnStr(selectItem);
    }

    /**
     * 申请 -- 临时账期
     * @param shopId
     * @param shEntity
     */
    private void applyApTemp(int shopId, CartBean.DataEntity.ShEntity shEntity) {
        if(shEntity != null){
            if(shEntity.getIs_temp() == 2){          // 已提交 临时账期额度
                showTigDialog();
            }else {
                Intent intent = new Intent();
                intent.putExtra(INTENT_BEAN, shEntity);
                intent.putExtra(INTENT_SHOP_ID, shopId);
                startActivity(TempAccountPeriodActivity.class, intent);
            }
        }
    }

    /**
     * 提示已提交 -- 临时账期（请等待）
     */
    private void showTigDialog(){
        TipDialog dialog = TipDialog.newInstance(getString(R.string.wait));
        dialog.isShowTitle(false)
                .isShowCancel(false)
                .isShowConfirm(true)
                .setConfirmSrc(getString(R.string.dialog_confirm))
                .setConfirmTextColor(Utils.getColor(R.color.zhusediao))
                .setOnclickListener(new TipDialog.ClickListenerInterface() {
                    @Override
                    public void doConfirm(TipDialog tipDialog) {
                        tipDialog.dismiss();
                    }

                    @Override
                    public void doCancel() {

                    }
                })
                .show(getFragmentManager(), "tempAP");
    }


    /**
     * "商品项" -- 是否勾选
     * @param data
     */
    private void cbGoodsCheck(CartBean.DataEntity.CartListEntity data) {
//        int i = 0;
//        for (Object shopEntityTemp : mAdapter.getAllData()){
//            CartBean.DataEntity shopEntity = (CartBean.DataEntity)shopEntityTemp;
//            if(shopEntity.getShop_id() == data.shop_id){
//                int j = 0;
//                for(CartBean.DataEntity.CartListEntity goodsEntity : shopEntity.getCart_list()){
//                    if(goodsEntity.getGoods_id() == data.getGoods_id()){
//                        shopEntity.getCart_list().get(j).isCheck = data.isCheck;
//                        setShopEntityIsClick(shopEntity);
//                        mAdapter.notifyItemChanged(i, shopEntity);
//
//                        setUserAp(mCbUseAp.isChecked());
//                        break;
//                    }
//                    j++;
//                }
//                break;
//            }
//            i++;
//        }

        int i = 0;
        for (Object shopEntityTemp : mAdapter.getAllData()){
            CartBean.DataEntity shopEntity = (CartBean.DataEntity)shopEntityTemp;
            if(shopEntity.getShop_id() == data.shop_id){
                int j = 0;
                for(CartBean.DataEntity.CartListEntity goodsEntity : shopEntity.getCart_list()){
                    if(goodsEntity.getCart_id() == data.getCart_id()){  // 不要使用 goods_id
                        shopEntity.getCart_list().get(j).isCheck = data.isCheck;
                        setShopEntityIsClick(shopEntity);
                        mAdapter.notifyItemChanged(i, shopEntity);

                        setUserAp(mCbUseAp.isChecked());
                        break;
                    }
                    j++;
                }
            }else {
                CartBean.DataEntity temp = (CartBean.DataEntity) shopEntityTemp;
                if(temp.isCheck){
                    temp.isCheck = false;
                    for(int j=0; j<temp.getCart_list().size(); j++){
                        temp.getCart_list().get(j).isCheck = false;
                    }
                    mAdapter.notifyItemChanged(i, temp);
                }else {
                    boolean isChange = false;
                    for(int j=0; j<temp.getCart_list().size(); j++){
                        if(temp.getCart_list().get(j).isCheck){
                            if(!isChange)
                                isChange = true;
                            temp.getCart_list().get(j).isCheck = false;
                        }
                    }
                    if(isChange){
                        temp.isCheck = false;
                        mAdapter.notifyItemChanged(i, temp);
                    }
                }

            }
            i++;
        }
    }

    /**
     * 设置"商店"是否全选
     * @param shopEntity
     */
    private void setShopEntityIsClick(CartBean.DataEntity shopEntity){
        int count = 0;
        for(CartBean.DataEntity.CartListEntity goodsEntity : shopEntity.getCart_list()){
            if(goodsEntity.isCheck){
                count++;
            }
        }
        shopEntity.isCheck =  shopEntity.getCart_list().size() == count;
    }


    /**
     * 更新 选择的规格?  数量
     * @param data
     * @param number
     * @param isDelay       是否延时
     */
    public void updateShopMoq(final CartBean.DataEntity.CartListEntity data, final int number, boolean isDelay) {
        int delay = 1000;
        if (!isDelay) {
            delay = 0;
        }
        Observable
                .timer(delay, TimeUnit.MILLISECONDS)
                .compose(RxUtils.<Long>rxSchedulerHelper())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        int i = 0;
                        for (Object temp : mAdapter.getAllData()) {
                            CartBean.DataEntity shopEntity = (CartBean.DataEntity) temp;
                            if (shopEntity.getShop_id() == data.shop_id) {
                                shopEntity.size += number;
                                mAdapter.update(shopEntity, i);
                                break;
                            }
                            i++;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 选择规格 -- 网络请求 -- 再显示规格窗口
     * @param entity
     */
    public void selectSpecific(CartBean.DataEntity.CartListEntity entity) {
        mEditBean = entity;
        mPresenter.getGoodsSpecifi(entity.getGoods_id() + "");
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        requestNetwork();
    }

    @Override
    public void onResume() {
        super.onResume();
        isShow = true;
        requestNetwork();
    }

    @Override
    public void onPause() {
        super.onPause();
        isShow = false;
    }


    /**
     * 获取购物车数据 -- 没登录的显示未登录界面
     */
    private void requestNetwork() {
        if (isShow && getUserVisibleHint()) {
            if (!LoginInformtaionSpUtils.getLoginInfString(mContext, LoginInformtaionSpUtils.login_login).equals("1")) {
                mRlNoLogin.setVisibility(View.VISIBLE);
                mLlRoot.setVisibility(View.GONE);
                mTvRight.setVisibility(View.GONE);
            } else {                                        // 已登录的处理
                mRlNoLogin.setVisibility(View.GONE);
                mTvRight.setVisibility(View.VISIBLE);
                mLlRoot.setVisibility(View.VISIBLE);
                onRefresh();
            }
        }
    }

    @Override
    public void onRefresh() {
        Map<String, String> data = new HashMap<>();
        data.put("user_id", GlobalData.getUser_id());
        mPresenter.getCartList(data);
    }


    /*--------------------------  GoodsVH start --------------------------------*/

    /**
     * 点击完成按钮 -- 编辑购物车
     * @param data
     * @param number
     * @param shopId
     */
    public void editCart(Map data, int number, int shopId) {
        // 暂存数据,出错恢复
        mCurrEditCartId = Integer.valueOf(data.get("cart_id") + "");
        mTempNumber = number;
        mCurrEditShopId = shopId;
        mDifferenceNumber = Integer.valueOf(data.get("cart_number") + "") - number;

        mPresenter.editCart(data, false);
    }


    public void deleteGoods(String cart_id) {
        mPresenter.deleteCart(GlobalData.getUser_id(), cart_id);
    }

    /*--------------------------  GoodsVH end --------------------------------*/
    /**
     * 记录RecyclerView当前位置
     *
     * @param layoutManager
     */
    private void getPositionAndOffset(RecyclerView.LayoutManager layoutManager) {
        View topView = layoutManager.getChildAt(0);        // 获取可视的第一个view
        if (topView != null) {
            mLastoffset = topView.getTop();                      // 获取与该view的顶部的偏移量
            mLastposition = layoutManager.getPosition(topView);  // 得到该View的数组位置
        }
    }

    /**
     * 让RecyclerView滚动到指定位置
     */
    private void scrollToPosition() {
        if (mErvList.getRecyclerView().getLayoutManager() != null && mLastposition >= 0) {
            ((LinearLayoutManager) mErvList.getRecyclerView().getLayoutManager()).scrollToPositionWithOffset(mLastposition, mLastoffset);
        }
    }

    @OnClick({R.id.tv_cart_login, R.id.tv_title_right,
            R.id.cb_cart_checkall, R.id.bt_cart_settlement,
            R.id.cb_cart_use_ap, R.id.tv_cart_ap})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_title_right:      /* 删除图片（点击进入编辑删除）/完成（点击完成删除） */
                changeMode();
                setAllState(false);
                mCbUseAp.setChecked(false);
                setUserAp(mCbUseAp.isChecked());
                break;

            case R.id.cb_cart_checkall:                                 /* 全选/取消全选 */
                // setAllState(mCbCheckAll.isChecked());
                break;

            case R.id.bt_cart_settlement:                               /* 删除或者结算 */
                delOrSettlement();
                break;

            case R.id.tv_cart_login:                                    /* 未登录的要先登录 */
                startActivity(LoginActivity.class, new Intent());
                break;

            case R.id.cb_cart_use_ap:                                    /* 使用账期 */
                setUserAp(mCbUseAp.isChecked());
                break;

            case R.id.tv_cart_ap:                                        /* 底下账期状态提示 */

                break;

            default:
                break;
        }
    }

    /**
     * 删除或者结算
     */
    private void delOrSettlement(){
        String cart_id = "";
        List<CartBean.DataEntity> shopList = mAdapter.getAllData();
        Map<Integer, Integer> moq = new LinkedHashMap<>();

        for(CartBean.DataEntity shopEntity : shopList){
            for(CartBean.DataEntity.CartListEntity goodsEntity : shopEntity.getCart_list()){
                if (goodsEntity.isShowEdit && CURR_MODE != CART_MODE_EDIT) {
                    ToastUtils.showToast( Utils.getString(R.string.plase_close_edit));
                    return;
                }

                if (goodsEntity.isCheck && goodsEntity.getStatus() != Goods_State_Invalid ) {
                    cart_id += goodsEntity.getCart_id() + ",";
                    Integer shopId = Integer.valueOf(goodsEntity.shop_id);
                    Integer number = Integer.valueOf(goodsEntity.getCart_number());

                    if (!moq.containsKey(shopId)) {
                        moq.put(shopId, number);
                    } else {
                        Integer integer = moq.get(shopId);
                        integer += number;
                        moq.put(shopId, integer);
                    }
                }
            }
        }

        // 要有选中
        if (TextUtils.isEmpty(cart_id)) {
            ToastUtils.showToast(Utils.getString(R.string.message_plase_select_goods));
            return;
        }
        cart_id = cart_id.substring(0, cart_id.length() - 1);

        if (CURR_MODE == CART_MODE_EDIT) {           /* 删除 */
            deleteGoods(cart_id);

        } else {                                     /* 结算 */
            for (CartBean.DataEntity shopEntity : shopList) {
                // 起定量
                Integer shop_id = Integer.valueOf(shopEntity.getShop_id());
                if (moq.containsKey(shop_id)) {
                    Integer number = moq.get(shop_id);


                    if (number < shopEntity.getMoq()) {
                        ToastUtils.showToast(Utils.getString(R.string.shop_moq,
                                shopEntity.getShop_name(), shopEntity.getMoq() + ""));
                        return;
                    }
                }
            }

            Intent intent = new Intent();
            intent.putExtra(INTENT_CART_ID, cart_id);
            intent.putExtra(INTENT_TYPE, SubmitOrderActivity.TYPE_NORMAL);
            intent.putExtra(INTENT_IS_SH, mCbUseAp.isChecked() ? 1 : 0);
            startActivity(SubmitOrderActivity.class, intent);                        /* 提交订单界面 */
        }
    }

//    /**
//     * 如果是 --
//     * true -- 海外订单 (2:海外现货; 3:海外期货)
//     * false -- 不是海外订单
//     * @param entity
//     * @return
//     */
//    public boolean isOverseas(OrderListBean.DataEntity entity) {
//        return entity.getOrder_type().equals(Goods_Type_Spot + "")
//                || entity.getOrder_type().equals(Goods_Type_Futures + "");
//    }


    /**
     * 删除图片（点击进入编辑删除）/完成（点击完成删除）
     *
     * 全选/取消全选
     *
     * 都用到
     * @param isCheck
     */
    public void setAllState(boolean isCheck) {
        List<CartBean.DataEntity> shopList = mAdapter.getAllData();
        int i = 0;
        for(CartBean.DataEntity shopEntity : shopList){
            shopList.get(i).isCheck = isCheck;
            int j = 0;
            for(CartBean.DataEntity.CartListEntity goodsEntity : shopEntity.getCart_list()){
                shopList.get(i).getCart_list().get(j).isCheck = isCheck;
                j++;
            }
            i++;
        }
        mAdapter.clear();
        mAdapter.addAll(shopList);

    }


    private void setUserAp(boolean isCheck){
        List<CartBean.DataEntity> shopList = mAdapter.getAllData();
        int i = 0;
        for(CartBean.DataEntity shopEntity : shopList){
            shopList.get(i).isUseAP = isCheck;
            shopList.get(i).setExceed(setShExceed(shopEntity));
            i++;
        }
        mAdapter.clear();
        mAdapter.addAll(shopList);
    }



    /**
     * 设置账期是否超出
     */
    private boolean setShExceed(CartBean.DataEntity shopEntity) {
        CartBean.DataEntity.ShEntity shEntity = shopEntity.getSh();
        Double price = 0.00;
        for(CartBean.DataEntity.CartListEntity goodsEntity : shopEntity.getCart_list()){
            if(goodsEntity.isCheck) {
                price += Utils.mul(Double.valueOf(goodsEntity.getCart_number()),
                        Double.valueOf(goodsEntity.getSh_price()));
            }
        }

        // LogUtils.i("price:" + price);

        if(shEntity != null){
            Double tempAmount = Double.valueOf(shEntity.getTemp_amount());      // 临时账期金额
            Double amount = Double.valueOf(shEntity.getAmount()) + tempAmount;  // 可用的账期金额
            // LogUtils.i("amount:" + amount);
            // LogUtils.i("tempAmount:" + tempAmount);
            if (price > amount && amount > 0) {
                return true;
            } else {
                return false;
            }
        }else {
            return false;
        }
    }

    @Override
    protected CartPresentImpl createPresent() {
        return new CartPresentImpl(this);
    }

    @Override
    public void onSuccess(String msg) {
        ToastUtils.showToast(msg);
        if (mPresenter.isDelete) {
            mPresenter.isDelete = false;
            changeMode();
            initView();
        }
        if (!mPresenter.isGetCartList) {
            requestNetwork();
        }
    }

    @Override
    public void onError(String msg) {
        ToastUtils.showToast(msg);
        editCartError();                // 编辑购物车 -- 网络请求失败的处理
        mErvList.getSwipeToRefresh().setRefreshing(false);
    }

    /**
     * 编辑购物车 -- 网络请求失败的处理
     */
    private void editCartError() {
        /* 编辑购物车 -- 网络请求失败的处理 */
        if (mPresenter.isEdit && mAdapter.getAllData().size() > 0) {
            mPresenter.isEdit = !mPresenter.isEdit;
            int i = 0;
            for (Object child : mAdapter.getAllData()) {
                CartBean.DataEntity shopEntity = (CartBean.DataEntity)child;
                if(shopEntity.getShop_id() == mCurrEditShopId ){                    /* 对应的商店 */
                    shopEntity.size -= mDifferenceNumber;

                    for(int j=0; j<shopEntity.getCart_list().size(); j++){
                        CartBean.DataEntity.CartListEntity goodsEntity = shopEntity.getCart_list().get(j);

                        if(goodsEntity.getCart_id() == mCurrEditCartId) {           /* 对应的商品 */
                            shopEntity.getCart_list().get(j).setCart_number(mTempNumber);
                            if (goodsEntity.tempSpecifi != null) {
                                shopEntity.getCart_list().get(j).setSpecification(goodsEntity.tempSpecifi);
                            }
                        }
                    }
                    mAdapter.update(shopEntity, i);
                    return;
                }
                i++;
            }
        }
    }

    @Override
    public void showLoading() {
        mErvList.setRefreshing(false);
        Utils.uProgressDialog(mContext);
    }

    @Override
    public void hideLoading() {
        Utils.dismissProgress();
    }


    @Override
    public void showCartList(List<CartBean.DataEntity> data) {
        mAdapter.clear();
        // mCbCheckAll.setChecked(false);
        // setAllState(false);
        mCbUseAp.setChecked(false);
        mBtnSettlement.setText(Utils.getString(R.string.settlement, "0"));
        setTotalPrice(0.0);

        if (data == null || data.size() == 0) {
            mErvList.showEmpty();
            return;
        }
        setCartList(data);              // 网络请求的"购物车数据" -- 重新整理
        scrollToPosition();
    }



    /**
     * 网络请求的"购物车数据" -- 重新整理
     * @param data
     */
    private void setCartList(List<CartBean.DataEntity> data) {
        int i = 0;
        int count = 0;
        for(CartBean.DataEntity shopEntity : data){
            if(shopEntity.shop_sh_status == 0){
                count++;
            }
            if(shopEntity.getCart_list() != null) {
                int j = 0;
                for (CartBean.DataEntity.CartListEntity goodsEntity : shopEntity.getCart_list()) {
                    if(goodsEntity.getSpecification() != null && goodsEntity.getSpecification().size() > 0){
                        data.get(i).getCart_list().get(j).json = new Gson().toJson(goodsEntity.getSpecification());
                    }
                    j++;
                }
            }
            i++;
        }

        if(count == data.size()){
            isShowAp = false;
            mLlAP.setVisibility(View.GONE);
        }else {
            isShowAp = true;
            mLlAP.setVisibility(View.VISIBLE);
        }

        mAdapter.addAll(data);
    }


    /* 显示 -- 规格窗口 */
    @Override
    public void showGoodSpecifi(SpecifiBean bean) {
//        GoodsDetailsAddcartDialog goodsDetailsAddcartDialog =
//                GoodsDetailsAddcartDialog.newInstance(bean.getData(), mEditBean,
//                        new GoodsDetailsAddcartDialog.ResListener() {
//
//                    /* 规格修改弹窗返回 */
//                    @Override
//                    public void returnRes(String json, CartBean.DataEntity.CartListEntity curr_bean) {
//                        int i = 0;
//                        for (Object child : mAdapter.getAllData()) {
//                            CartBean.DataEntity shopEntity = (CartBean.DataEntity)child;
//                            int j = 0;
//                            for(CartBean.DataEntity.CartListEntity goodsEntity : shopEntity.getCart_list()){
//                                if(goodsEntity.getCart_id() == curr_bean.getCart_id()) {           /* 对应的商品 */
//                                    curr_bean.isDialog = true;
//                                    curr_bean.json = json;
//                                    List<CartBean.DataEntity.CartListEntity> list = shopEntity.getCart_list();
//                                    list.set(j, curr_bean);
//                                    shopEntity.setCart_list(list);
//                                    mAdapter.update(shopEntity, i);
//                                }
//                                j++;
//                            }
//                            i++;
//                        }
//                    }
//                });
//
//        goodsDetailsAddcartDialog.setButtonString(getString(R.string.determine));
//        goodsDetailsAddcartDialog.show(getChildFragmentManager(), "");


        AddCartBottomDialog addCartBottomDialog =
                AddCartBottomDialog.newInstance(bean.getData(), mEditBean,
                        new AddCartBottomDialog.ResListener() {

                            /* 规格修改弹窗返回 */
                            @Override
                            public void returnRes(String json, CartBean.DataEntity.CartListEntity curr_bean) {
                                int i = 0;
                                for (Object child : mAdapter.getAllData()) {
                                    CartBean.DataEntity shopEntity = (CartBean.DataEntity)child;
                                    int j = 0;
                                    for(CartBean.DataEntity.CartListEntity goodsEntity : shopEntity.getCart_list()){
                                        if(goodsEntity.getCart_id() == curr_bean.getCart_id()) {           /* 对应的商品 */
                                            curr_bean.isDialog = true;
                                            curr_bean.json = json;
                                            List<CartBean.DataEntity.CartListEntity> list = shopEntity.getCart_list();
                                            list.set(j, curr_bean);
                                            shopEntity.setCart_list(list);
                                            mAdapter.update(shopEntity, i);
                                        }
                                        j++;
                                    }
                                    i++;
                                }
                            }
                        });

        addCartBottomDialog.setButtonString(getString(R.string.determine));
        addCartBottomDialog.show(getChildFragmentManager(), "");
    }


}

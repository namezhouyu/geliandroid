package com.geli.m.mvp.home.index_fragment.overseas_activity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.CartBean;
import com.geli.m.bean.OverseasBean;
import com.geli.m.bean.OverseasCountrOutrBean;
import com.geli.m.bean.OverseasGoodsOuterBean;
import com.geli.m.bean.OverseasSortBean;
import com.geli.m.bean.ShopInfoBean;
import com.geli.m.bean.SpecifiBean;
import com.geli.m.coustomview.FluidLayout;
import com.geli.m.coustomview.MyTabLayout;
import com.geli.m.coustomview.SmallCartLayout;
import com.geli.m.dialog.PreviewDialog;
import com.geli.m.dialog.addcart.AddCartMiddleDialog;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.mvp.home.index_fragment.overseas_activity.fragment.OverseasListFragment;
import com.geli.m.mvp.home.index_fragment.search_activity.SearchActivity;
import com.geli.m.mvp.home.other.shopdetails_activity.shopdetails_fragment.ShopDetailsFragment;
import com.geli.m.mvp.home.other.shopdetails_activity.shopdetails_fragment.VH.SmallCartTitleViewHolder;
import com.geli.m.mvp.home.other.shopdetails_activity.shopdetails_fragment.VH.SmallCartViewHolder;
import com.geli.m.utils.LoginInformtaionSpUtils;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.geli.m.config.Constant.Goods_Type_Spot;

/**
 * Created by Steam_l on 2017/12/27.
 */

public class OverseasActivity extends MVPActivity<OverseasPresentImpl>
        implements View.OnClickListener,
        OverseasView,
        FluidLayout.CheckBoxClickListener,
        SmallCartLayout.SmallCartEditListener {

    /** 根目录 */
    @BindView(R.id.ll_overseas_rootlayout)
    LinearLayout mLlRootlayout;

    @BindView(R.id.mtl_overseas_layout)
    MyTabLayout mMyTabLayout;
    @BindView(R.id.tv_title_name)
    TextView mTvTitle;
    @BindView(R.id.tv_title_right)
    TextView mTvRight;

    /** 期货 */
    @BindView(R.id.tv_overseas_list_futures)
    TextView mTvFutures;
    /** 团购 */
    @BindView(R.id.tv_overseas_list_groubuy)
    TextView mTvGroubuy;
    /** 现货 */
    @BindView(R.id.tv_overseas_list_spot)
    TextView mTvSpot;

    @BindView(R.id.vp_overseas_content)
    ViewPager mVpContent;

    /** ViewPager 下面的  购物车 上的布局 */
    @BindView(R.id.rl_overseas_select_rootlayout)
    RelativeLayout mRlSelectRootLayout;
    @BindView(R.id.fl_overseas_select_layout)
    FluidLayout mFlLayout;

    @BindView(R.id.rl_overseas_select_country)
    RelativeLayout mRlCountry;
    @BindView(R.id.rl_overseas_select_goodstype)
    RelativeLayout mRlGoodsType;

    /** 二级分类左边那个 */
    @BindView(R.id.tv_overseas_select_country)
    TextView mTvTabCountry;
    /** 二级分类右边那个 */
    @BindView(R.id.tv_overseas_select_goodstype)
    TextView mTvTabGoodsType;

    /** 底部购物车 -- 的布局 */
    @BindView(R.id.scl_smallcart_root)
    SmallCartLayout mSclLayout;
    /** 购物车中商品的种类 */
    @BindView(R.id.tv_itemview_smallcart_cartnumber)
    TextView mTvCartNumber;

    /** 地下的按钮 */
    @BindView(R.id.tv_smallcart_bottom_price)
    TextView mTvBottomPrice;
    /** 结算按钮 */
    @BindView(R.id.bt_smallcart_bootom_settlement)
    Button mBtSettlement;

    private OverseasSortBean mOverseasSortBean;
    private List<String> mCountryList = new ArrayList<>();
    private List<String> mTypeList = new ArrayList<>();
    private OverseasAdapter mOverseasAdapter;
    private List<OverseasCountrOutrBean.OverseasCountrBean> mOverseasCountrBean;
    private int mSelectViewHeight;
    private OverseasGoodsOuterBean.OverseasGoodsBean currEditBean;
    private EasyRecyclerView mErv_smallCart;
    private RecyclerArrayAdapter mSmallCartAdapter;
    private List<Object> mCartList = new ArrayList<>();
    Double mTotalPrice = 0.0;
    private boolean isClearCart;
    private CartBean.DataEntity.CartListEntity mCurrEditBean;//当前编辑的bean
    private int mCurrEditNumber;
    private PreviewDialog mPreviewDialog;

    @Override
    protected int getResId() {
        return R.layout.activity_overseas;
    }

    @Override
    protected void initData() {
        setTitleView();                         // 设置标题

        initVp();
        initSmallCart();
        initListData(2);
        showCart();
        mFlLayout.setCheckBoxClickListener(this);

    }

    @Override
    protected void initEvent() {

    }

    /**
     * 设置标题
     */
    private void setTitleView() {
        Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.nav_btn_sousuo);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        mTvRight.setCompoundDrawables(drawable, null, null, null);
        mTvTitle.setText(Utils.getString(R.string.haiwai_zhicai));
    }

    private void initVp() {
        mOverseasAdapter = new OverseasAdapter(getSupportFragmentManager());
        mVpContent.setAdapter(mOverseasAdapter);
        mVpContent.setOffscreenPageLimit(mOverseasAdapter.getCount());
        mMyTabLayout.setViewPager(mVpContent);

        mVpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mOverseasAdapter.getFragments().get(position).mCurrGoodsType == Goods_Type_Spot) {  // 现货
                    showCart();

                } else {
                    hideCart();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void initSmallCart() {

        mSmallCartAdapter = new RecyclerArrayAdapter(mContext) {
            int type_shop = 1 << 0;
            int type_goods = 1 << 1;

            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                if (viewType == type_shop) {
                    return new SmallCartTitleViewHolder(parent, mContext, null);
                } else {
                    return new SmallCartViewHolder(parent, mContext, OverseasActivity.this);
                }
            }

            @Override
            public int getViewType(int position) {
                Object cartBaseBean = mCartList.get(position);
                if (cartBaseBean instanceof CartBean.DataEntity) {
                    return type_shop;
                }
                return type_goods;
            }
        };

        DividerDecoration itemDecoration = new DividerDecoration(Color.WHITE, Utils.dip2px(mContext, 10));
        itemDecoration.setDrawLastItem(true);
        itemDecoration.setDrawHeaderFooter(true);

        mErv_smallCart = mSclLayout.getErvList(mSmallCartAdapter, mPresenter);
        mErv_smallCart.addItemDecoration(itemDecoration);
        mErv_smallCart.setAdapterWithProgress(mSmallCartAdapter);

        mSclLayout.setIsOverseas(true);                         // 固定是海外的
        mSclLayout.setVisibility(View.GONE);


        mRlSelectRootLayout.setVisibility(View.GONE);
    }

    /**
     * 获取数据
     * @param type 4 团购  3 期货  2 现货
     */
    public void initListData(int type) {
        mPresenter.init(type + "");
        mPresenter.getSortList(getCurrGoodsType() + "", "", false);
    }

    private void showCart(){
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mLlRootlayout.getLayoutParams();
        layoutParams.bottomMargin = (int) mContext.getResources().getDimension(R.dimen.bottom_height);
        mLlRootlayout.setLayoutParams(layoutParams);
        getCartList();
        mSclLayout.setVisibility(View.VISIBLE);     // 显示购物车
    }

    private void hideCart(){
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mLlRootlayout.getLayoutParams();
        layoutParams.bottomMargin = 0;
        mLlRootlayout.setLayoutParams(layoutParams);
        mSclLayout.setVisibility(View.GONE);
    }

    private void getCartList() {
        if (LoginInformtaionSpUtils.getLoginInfString(mContext, LoginInformtaionSpUtils.login_login).equals("1")) {
            Map<String, String> data = new HashMap<>();
            data.put("user_id", GlobalData.getUser_id());
            data.put("shop_id", 1 + "");
            mPresenter.getCartList(data);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(BROADCAST_ADDCART);
        filter.addAction(BROADCAST_SHOPAPTITUDE);
        registerReceiver(mShowDialogBroadcastReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mShowDialogBroadcastReceiver);
    }

    @OnClick({R.id.tv_title_right, R.id.iv_title_back,
            R.id.tv_itemview_smallcart_clearcart, R.id.rl_overseas_select_country,
            R.id.rl_overseas_select_goodstype, R.id.tv_overseas_list_groubuy,
            R.id.tv_overseas_list_futures, R.id.tv_overseas_list_spot})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_title_right:                       /* 右上角的搜索 */
                Intent intent = new Intent().putExtra(SearchActivity.INTENT_SEARCHTYPE, SearchActivity.SEARCH_TYPE_H);
                startActivity(SearchActivity.class, intent);
                break;

            case R.id.iv_title_back:
                finish();
                break;

            case R.id.tv_overseas_list_spot:                /* 现货 */
                mVpContent.setCurrentItem(0);
                break;

            case R.id.tv_overseas_list_groubuy:             /* 团购 */
                mVpContent.setCurrentItem(1);
                break;

            case R.id.tv_overseas_list_futures:             /* 期货 */
                mVpContent.setCurrentItem(2);
                break;



            case R.id.rl_overseas_select_country:
            case R.id.rl_overseas_select_goodstype:
                curr_clickId = v.getId();
                opencloseSelectLayout(v.getId());
                break;

            case R.id.tv_itemview_smallcart_clearcart:
                clearCart();
                break;

            default:
                break;
        }
    }

    private void clearCart() {
        isClearCart = true;
        String delId = "";
        for (Object child : mSmallCartAdapter.getAllData()) {
            if (child instanceof CartBean.DataEntity.CartListEntity) {
                delId += ((CartBean.DataEntity.CartListEntity) child).getCart_id() + ",";
            }
        }
        delId = delId.substring(0, delId.length() - 1);
        mPresenter.deleteCart(GlobalData.getUser_id(), delId);
    }

    private int animaTime = 500;
    private boolean animaIng = false;
    private int coutryNumber = 0;
    private int goodstypeNumber = 0;//点击tag标记
    public boolean isClose;
    public boolean isOpen;

    public void opencloseSelectLayout(int id) {
        if (animaIng) {
            return;
        }


        if (id == R.id.rl_overseas_select_country) {
            coutryNumber++;
            goodstypeNumber = 0;
            if (coutryNumber % 2 == 0) {
                isClose = true;
            } else {
                isClose = false;
            }

        } else if (id == R.id.rl_overseas_select_goodstype) {
            goodstypeNumber++;
            coutryNumber = 0;
            if (goodstypeNumber % 2 == 0) {
                isClose = true;
            } else {
                isClose = false;
            }
        } else {
            coutryNumber = 0;
            goodstypeNumber = 0;
            isClose = !isClose;
        }


        if (!isClose) {
            openSelect(id);
        } else {
            closeSelect();
        }
    }

    private void closeSelect() {
        ValueAnimator closeAnima = ValueAnimator.ofInt(0, -mSelectViewHeight);
        closeAnima.setDuration(animaTime);
        closeAnima.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int height = (int) animation.getAnimatedValue();
                mRlSelectRootLayout.setY(height);
            }
        });
        closeAnima.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                animaIng = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animaIng = false;
                isOpen = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        closeAnima.start();
    }

    public int selectCountryId = -1;
    public int selectSortId = -1;
    boolean isShowCountry = false;
    boolean isShowSort = false;
    private int curr_clickId;

    private void openSelect(int id) {
        List<TextView> view;
        mFlLayout.removeAllViews();
        if (id == R.id.rl_overseas_select_country) {
            view = mFlLayout.getView(mCountryList);
            isShowCountry = true;
            isShowSort = false;
        } else {
            view = mFlLayout.getView(mTypeList);
            isShowSort = true;
            isShowCountry = false;
        }
        int i = 0;
        int tag;
        for (TextView cb : view) {
            CheckBox checkBox = (CheckBox) cb;
            if (i == 0) {
                tag = -1;//全部的tag为-1
                boolean isCheck = (id == R.id.rl_overseas_select_country && selectCountryId == -1)
                        || (id == R.id.rl_overseas_select_goodstype && selectSortId == -1);
                checkBox.setChecked(isCheck);
            } else {
                if (id == R.id.rl_overseas_select_country) {
                    tag = mOverseasCountrBean.get(i - 1).getCoun_id();
                    checkBox.setChecked(tag == selectCountryId);
                } else {
                    tag = mOverseasSortBean.getData().get(i - 1).getCat_id();
                    checkBox.setChecked(tag == selectSortId);
                }
            }
            checkBox.setTag(tag);
            mFlLayout.addView(checkBox);
            i++;
        }
        if (isOpen) {
            return;
        }
        mRlSelectRootLayout.setVisibility(View.VISIBLE);
        mRlSelectRootLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mRlSelectRootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mSelectViewHeight = mRlSelectRootLayout.getMeasuredHeight();
                ValueAnimator openAnima = ValueAnimator.ofInt(-mSelectViewHeight, 0);
                openAnima.setDuration(animaTime);
                openAnima.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int height = (int) animation.getAnimatedValue();
                        mRlSelectRootLayout.setY(height);
                    }
                });
                openAnima.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        animaIng = true;
                        isOpen = true;
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animaIng = false;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                openAnima.start();
            }
        });
    }

    @Override
    public void onCheckBoxClick(View v) {
        CheckBox checkBox = (CheckBox) v;
        //由于内部处理了false,会触发点击事件
        if (checkBox.isChecked() && checkBox.getTag() != null) {
            if (isShowCountry) {
                mTvTabCountry.setText(checkBox.getText());
                // TODO: 2018/4/4  上一次是商品分类，tag不同上一次选中的countryId
                OverseasCountrOutrBean.OverseasCountrBean o = new OverseasCountrOutrBean.OverseasCountrBean();
                o.setCoun_id((Integer) checkBox.getTag());
                if ((int) checkBox.getTag() == -1 || (selectCountryId != (int) checkBox.getTag() && mOverseasCountrBean.contains(o))) {
                    //切换tab全部page为1
                    setFragmentPage(1);
                    //切换tab,商品分类初始化
                    selectSortId = -1;
                    mTvTabGoodsType.setText(Utils.getString(R.string.all) + Utils.getString(R.string.types));
                    selectCountryId = (int) checkBox.getTag();
                    if (selectCountryId == -1) {
                        mPresenter.init(getCurrGoodsType() + "");
                        mPresenter.getSortList(getCurrGoodsType() + "", "", false);
                    } else {
                        mPresenter.getSortList(getCurrGoodsType() + "", selectCountryId + "", true);
                    }
                }
            } else {
                mTvTabGoodsType.setText(checkBox.getText());
                OverseasSortBean.DataEntity dataEntity = new OverseasSortBean.DataEntity();
                dataEntity.setCat_id((Integer) checkBox.getTag());
                if ((int) checkBox.getTag() == -1 || (selectSortId != (int) checkBox.getTag() && mOverseasSortBean.getData().contains(dataEntity))) {//不等于上一次
                    //切换tab全部page为1
                    setFragmentPage(1);
                    selectSortId = (int) checkBox.getTag();
                    getGoodsList();
                }
            }
        }
    }

    public void getGoodsList() {
        getGoodsList(getCurrFragmentPage(), getCurrGoodsType());
    }

    public void getGoodsList(int page, int type) {
        Map data = new HashMap();
        if (getCurrGoodsType() != -1) {
            data.put("goods_type", type + "");
        }
        data.put("page", page + "");
        if (selectCountryId != -1) {
            data.put("countries_id", selectCountryId + "");
        }
        if (selectSortId != -1) {
            data.put("cat_id", selectSortId + "");
        }
        mPresenter.getGoodsList(data);
    }

    public int getCurrGoodsType() {
        for (OverseasListFragment fragment : mOverseasAdapter.getFragments()) {
            if (fragment.getUserVisibleHint() && fragment.mIsShow) {
                return fragment.mCurrGoodsType;
            }
        }
        return -1;
    }

    public int getCurrFragmentPage() {
        for (OverseasListFragment fragment : mOverseasAdapter.getFragments()) {
            if (fragment.getUserVisibleHint() && fragment.mIsShow) {
                return fragment.page;
            }
        }
        return 1;
    }

    public EasyRecyclerView getCurrFragmentView() {
        for (OverseasListFragment fragment : mOverseasAdapter.getFragments()) {
            if (fragment.getUserVisibleHint() && fragment.mIsShow) {
                return fragment.mErvList;
            }
        }
        return null;

    }

    public void setFragmentPage(int page) {
        for (OverseasListFragment fragment : mOverseasAdapter.getFragments()) {
            if (fragment.getUserVisibleHint() && fragment.mIsShow) {
                fragment.page = page;
            }
        }
    }

    @Override
    protected OverseasPresentImpl createPresenter() {
        return new OverseasPresentImpl(this);
    }


    @Override
    public void onSuccess(String msg) {
        ShowSingleToast.showToast(mContext, msg);
        if (mPresenter.isAddCart) {
            mPresenter.isAddCart = !mPresenter.isAddCart;
            getCartList();
            return;
        }
        if (mPresenter.isEdit) {
            mPresenter.isEdit = !mPresenter.isEdit;
            int index = 0;
            for (Object child : mSmallCartAdapter.getAllData()) {
                if (child instanceof CartBean.DataEntity.CartListEntity) {
                    if (((CartBean.DataEntity.CartListEntity) child).getGoods_id() == mCurrEditBean.getGoods_id()) {
                        mTotalPrice += Utils.mul(Double.valueOf(mCurrEditBean.getCart_price()), (double) (mCurrEditNumber - mCurrEditBean.getCart_number()));
                        mTvBottomPrice.setText(Utils.getString(R.string.overseas_list_money, Utils.getFormatDoubleTwoDecimalPlaces(mTotalPrice, 2)));
                        mCurrEditBean.setCart_number(mCurrEditNumber);
                        mSmallCartAdapter.update(mCurrEditBean, index);
                        break;
                    }
                }
                index++;
            }
            return;
        }
        if (isClearCart) {
            isClearCart = !isClearCart;
            mSmallCartAdapter.clear();
            mCartList.clear();
            mSclLayout.closeCart();
            mTvBottomPrice.setText(Utils.getString(R.string.overseas_list_money, "0.00"));
            mBtSettlement.setText(Utils.getString(R.string.settlement, "0"));
            mSclLayout.getTvCartNumber().setText("0");
            return;
        }
        if (mPresenter.isDelete) {
            mPresenter.isDelete = !mPresenter.isDelete;
            int index = 0;
            for (Object child : mSmallCartAdapter.getAllData()) {
                if (child instanceof CartBean.DataEntity.CartListEntity) {
                    if (((CartBean.DataEntity.CartListEntity) child).getGoods_id() == mCurrEditBean.getGoods_id()) {
                        mTotalPrice -= Utils.mul(Double.valueOf(mCurrEditBean.getCart_price()), Double.valueOf(mCurrEditBean.getCart_number()));
                        mTvBottomPrice.setText(Utils.getString(R.string.overseas_list_money, Utils.getFormatDoubleTwoDecimalPlaces(mTotalPrice, 2)));
                        mSmallCartAdapter.remove(index);
                        break;
                    }
                }
                index++;
            }
            index = 0;
            for (Object child : mSmallCartAdapter.getAllData()) {
                if (child instanceof CartBean.DataEntity) {
                    //数组可能越界
                    try {
                        if (mSmallCartAdapter.getItem(index + 1) == null) {
                            mSmallCartAdapter.remove(index);
                            break;
                        }
                    } catch (IndexOutOfBoundsException e) {
                        mSmallCartAdapter.remove(index);
                        break;
                    }
                }
                index++;
            }
            int number = Integer.valueOf(mSclLayout.getTvCartNumber().getText().toString().trim()) - 1;
            mSclLayout.getTvCartNumber().setText(number + "");
            mBtSettlement.setText(Utils.getString(R.string.settlement, mSclLayout.getTvCartNumber().getText()));
            if (number <= 0) {
                mSclLayout.closeCart();
            }
        }
    }

    @Override
    public void onError(String msg) {
        ShowSingleToast.showToast(mContext, msg);
        for (OverseasListFragment fragment : mOverseasAdapter.getFragments()) {
            if (fragment.mIsShow && fragment.getUserVisibleHint()) {
                fragment.onError();
            }
        }
    }

    @Override
    public void showLoading() {
        if (getCurrFragmentPage() == 1 || isShowDialog || mPresenter.isPost()) {
            if (null != getCurrFragmentView()) {
                getCurrFragmentView().setRefreshing(true);
            }
        }
    }

    @Override
    public void hideLoading() {
        if (getCurrFragmentPage() == 1 || isShowDialog || mPresenter.isPost()) {
            if (null != getCurrFragmentView()) {
                getCurrFragmentView().setRefreshing(false);
            }
        }
    }

    @Override
    public void showData(OverseasBean bean) {
        mCountryList.clear();
        mOverseasCountrBean = bean.getOverseasCountrBean();
        mCountryList.add(Utils.getString(R.string.all) + Utils.getString(R.string.country));
        mTvTabCountry.setText(Utils.getString(R.string.all) + Utils.getString(R.string.country));
        mTvTabGoodsType.setText(Utils.getString(R.string.all) + Utils.getString(R.string.types));
        for (OverseasCountrOutrBean.OverseasCountrBean chile : mOverseasCountrBean) {
            mCountryList.add(chile.getCoun_name());
        }
        setGoodsData(bean.getOverseasGoodsBean());
    }

    @Override
    public void showSortData(OverseasBean bean) {
        setSortData(bean.getOverseasSortBean());
        setGoodsData(bean.getOverseasGoodsBean());
    }

    @Override
    public void setSortData(OverseasSortBean bean) {
        mTypeList.clear();
        mTypeList.add(Utils.getString(R.string.all) + Utils.getString(R.string.types));
        mOverseasSortBean = bean;
        List<OverseasSortBean.DataEntity> data = mOverseasSortBean.getData();
        for (OverseasSortBean.DataEntity child : data) {
            mTypeList.add(child.getCat_name());
        }
    }

    @Override
    public void showGoodsData(List<OverseasGoodsOuterBean.OverseasGoodsBean> goodsBeanList) {
        setGoodsData(goodsBeanList);
    }

    @Override
    public void showShopAptitude(List<String> url) {
        if (url == null || url.size() == 0) {
            ShowSingleToast.showToast(mContext, Utils.getString(R.string.noshopaptitude));
            return;
        }
        mPreviewDialog = PreviewDialog.newInstance(url);
        mPreviewDialog.show(getSupportFragmentManager(), "");
    }

    public void setGoodsData(List<OverseasGoodsOuterBean.OverseasGoodsBean> goodsData) {
        for (OverseasListFragment fragment : mOverseasAdapter.getFragments()) {
            if (fragment.mIsShow && fragment.getUserVisibleHint()) {
                fragment.setGoodsData(goodsData);
            }
        }
    }

    @Override
    public void showCartList(List<CartBean.DataEntity> dataEntities) {
        mCartList.clear();
        mSmallCartAdapter.clear();
        mTotalPrice = 0.0;
        int cartNumber = 0;
        if (dataEntities.size() != 0) {
            List list = ShopDetailsFragment.parseCartData(dataEntities, mCartList, mTotalPrice, 2);
            cartNumber = (int) list.get(0);
            mTotalPrice = (Double) list.get(1);
        }
        mTvCartNumber.setText(cartNumber + "");
        mTvBottomPrice.setText(Utils.getString(R.string.overseas_list_money, Utils.getFormatDoubleTwoDecimalPlaces(mTotalPrice, 2)));
        mBtSettlement.setText(Utils.getString(R.string.settlement, cartNumber + ""));
        mSmallCartAdapter.addAll(mCartList);
    }

    @Override
    public void showGoodSpecifi(SpecifiBean bean) {
        ShopInfoBean.DataEntity.GoodsResEntity resEntity = new ShopInfoBean.DataEntity.GoodsResEntity();
        resEntity.setGoods_name(currEditBean.getGoods_name());
        resEntity.setOrigin_number(currEditBean.getOrigin_number());
//        AddCartDialog addCartDialog = AddCartDialog.newInstance(bean.getData(), resEntity,
//                new AddCartDialog.AddCartListener() {
//            @Override
//            public void addCart(String json, String sku_id, String number) {
//                Map data = new HashMap();
//                data.put("user_id", GlobalData.getUser_id());
//                data.put("goods_id", currEditBean.getGoods_id() + "");
//                data.put("cart_number", number);
//                data.put("specification", json);
//                data.put("sku_id", sku_id + "");
//                data.put("cart_type", 1 + "");
//                mPresenter.editCart(data, true);
//            }
//        });

        AddCartMiddleDialog addCartDialog = AddCartMiddleDialog.newInstance(bean.getData(), resEntity,
                new AddCartMiddleDialog.AddCartMiddleListener() {
                    @Override
                    public void addCart(String json, String sku_id, String number) {
                        Map data = new HashMap();
                        data.put("user_id", GlobalData.getUser_id());
                        data.put("goods_id", currEditBean.getGoods_id() + "");
                        data.put("cart_number", number);
                        data.put("specification", json);
                        data.put("sku_id", sku_id + "");
                        data.put("cart_type", 1 + "");
                        mPresenter.editCart(data, true);
                    }
                });
        addCartDialog.show(getSupportFragmentManager(), "");
    }

    public static final String BROADCAST_ADDCART = "broadcast_addcart";
    public static final String BROADCAST_GOODSID = "broadcast_goodsid";
    public static final String BROADCAST_DATA = "broadcast_data";
    public static final String BROADCAST_SHOPAPTITUDE = "broadcast_shopaptitude";
    public static final String BROADCAST_SHOPID = "broadcast_shopid";

    boolean isShowDialog = false;
    public BroadcastReceiver mShowDialogBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(BROADCAST_ADDCART)) {
                currEditBean = intent.getParcelableExtra(BROADCAST_DATA);
                isShowDialog = true;
                mPresenter.getGoodsSpecifi(intent.getStringExtra(BROADCAST_GOODSID));
            } else if (intent.getAction().equals(BROADCAST_SHOPAPTITUDE)) {
                String shop_id = intent.getStringExtra(BROADCAST_SHOPID);
                mPresenter.shopAptitude(mContext,shop_id);
            }
        }
    };


    /* 小购物车回调 */
    @Override
    public void editCart(CartBean.DataEntity.CartListEntity data, int number, boolean isAdd) {
        mCurrEditBean = data;
        mCurrEditNumber = number;
        Map map = new HashMap();
        map.put("user_id", GlobalData.getUser_id());
        map.put("goods_id", data.getGoods_id());
        if (isAdd) {
            map.put("cart_number", "1");//累+
        } else {
            map.put("cart_number", "-1");//累-
        }
        map.put("sku_id", data.getSku_id());
        map.put("cart_type", "1");
        if (data.getSpecification() != null) {
            map.put("specification", data.getSpecification());
        }
        mPresenter.editCart(map, false);
    }

    @Override
    public void deleteGoods(CartBean.DataEntity.CartListEntity data) {
        mCurrEditBean = data;
        mPresenter.deleteCart(GlobalData.getUser_id(), data.getCart_id() + "");
    }
}

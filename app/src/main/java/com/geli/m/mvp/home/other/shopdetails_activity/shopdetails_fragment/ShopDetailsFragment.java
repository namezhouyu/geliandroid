package com.geli.m.mvp.home.other.shopdetails_activity.shopdetails_fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.CartBean;
import com.geli.m.bean.CouponBean;
import com.geli.m.bean.ShopInfoBean;
import com.geli.m.bean.SpecifiBean;
import com.geli.m.config.Constant;
import com.geli.m.coustomview.NetworkingErrorView;
import com.geli.m.coustomview.ShopDetailsLayout;
import com.geli.m.coustomview.SmallCartLayout;
import com.geli.m.coustomview.StickyHeaderAdapter;
import com.geli.m.dialog.ShareDialog;
import com.geli.m.dialog.addcart.AddCartMiddleDialog;
import com.geli.m.dialog.addcart.old.AddCartDialog;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.base.BaseModel;
import com.geli.m.mvp.base.MVPFragment;
import com.geli.m.mvp.home.index_fragment.restaurantlist_activity.main.restaurant_activity.KeyWordRestaurantVH;
import com.geli.m.mvp.home.index_fragment.search_activity.SearchActivity;
import com.geli.m.mvp.home.mine_fragment.coupon_activity.coupon_fragment.CouponFragment;
import com.geli.m.mvp.home.mine_fragment.mywallet_activity.recharge_activity.ReportShopActivity;
import com.geli.m.mvp.home.other.WebViewActivity;
import com.geli.m.mvp.home.other.goodsdetails_activity.GoodsDetailsActivity;
import com.geli.m.mvp.home.other.shopdetails_activity.ShopDetailsActivity;
import com.geli.m.mvp.home.other.shopdetails_activity.shopdetails_fragment.VH.GoodsLabelOrtherViewHolder;
import com.geli.m.mvp.home.other.shopdetails_activity.shopdetails_fragment.VH.GoodsLabelViewHolder;
import com.geli.m.mvp.home.other.shopdetails_activity.shopdetails_fragment.VH.ShopDetailsGoodsViewHolder;
import com.geli.m.mvp.home.other.shopdetails_activity.shopdetails_fragment.VH.SmallCartTitleViewHolder;
import com.geli.m.mvp.home.other.shopdetails_activity.shopdetails_fragment.VH.SmallCartViewHolder;
import com.geli.m.select.SelectPhotoFragment;
import com.geli.m.ui.activity.AllCommentActivity;
import com.geli.m.utils.EasyRecyclerViewUtils;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.LoginInformtaionSpUtils;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.StringUtils;
import com.geli.m.utils.Utils;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.jude.easyrecyclerview.decoration.StickyHeaderDecoration;
import com.luck.picture.lib.entity.LocalMedia;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.geli.m.BuildConfig.GL_IMAGE_URL;
import static com.geli.m.config.Constant.INTENT_CAT_ID;
import static com.geli.m.config.Constant.INTENT_GOODS_ID;
import static com.geli.m.config.Constant.INTENT_IS_OPEN;
import static com.geli.m.config.Constant.INTENT_LINKS;
import static com.geli.m.config.Constant.INTENT_SHOP_ID;
import static com.geli.m.config.Constant.VIEWTYPE_SHOPALL;
import static com.geli.m.config.Constant.resale_true;

/**
 * Created by Steam_l on 2017/12/30.
 *
 * 店铺详情
 */
public class ShopDetailsFragment extends MVPFragment<ShopInfoPresentImpl>
        implements ShopDetailsLayout.LayoutChangeListener,
        SmallCartLayout.SmallCartOpenListener,
        ShopDetailsActivity.OnBackListener,
        ShopInfoView, AddCartDialog.AddCartListener,
        View.OnClickListener, AddCartMiddleDialog.AddCartMiddleListener {//, SmallCartLayout.SmallCartEditListener{

    @BindView(R.id.toolbar_shopdetails)
    Toolbar mToolbar;


    /** 标题 */
    @BindView(R.id.tv_shopdetails_title)
    TextView mTvTitle;

    /** 店铺详情的上层布 -- 右边具体商品 */
    @BindView(R.id.erv_shopdetails_goods_content)
    EasyRecyclerView mErvGoodsContent;
    /** 店铺详情的上层布 -- 左边分类索引 */
    @BindView(R.id.erv_shopdetails_goods_title)
    EasyRecyclerView mErvGoodsTitle;
    /** 店铺详情的上层布中 -- 包裹"左、右"的布局  */
    @BindView(R.id.sdl_shopdetails_goods_layout)
    ShopDetailsLayout mSdlLayout;

    /** 底部一行布局 -- 购物车(包括弹出购物车里面的商品详情) */
    @BindView(R.id.scl_smallcart_root)
    SmallCartLayout mSclLayout;
    /** 底部一行 -- 按钮部分 */
    @BindView(R.id.rl_smallcart_bottom)
    RelativeLayout mRlSmallCartBottom;

    /** 上滑继续购物 */
    @BindView(R.id.tv_shopdetails_bottomlayout)
    TextView mTvBottom;

    /** 网络异常布局 */
    @BindView(R.id.nev_layout_error)
    NetworkingErrorView mNevError;

    /** 底层布局中 -- 商店图标 */
    @BindView(R.id.iv_shopdetails_img)
    ImageView mIvImg;
    /** 底层布局中 -- 商店名称 */
    @BindView(R.id.tv_shopdetails_name)
    TextView mTvShopName;
    /** 底层布局中 -- 拼团标志 */
    @BindView(R.id.tv_Assemble_ShopRestaurantVH)
    TextView mTvAssemble;
    /** 底层布局中 -- 商店说明 */
    @BindView(R.id.tv_shopdetails_introduction)
    TextView mTvIntroduction;
    /** 底层布局中 -- 商店说明下的关键字 */
    @BindView(R.id.erv_keyWord_ShopDetailsDetailsInclude)
    EasyRecyclerView mErvKeyWord;
    /** 底层布局中 -- 收藏 */
    @BindView(R.id.cb_shopdetails_collection)
    CheckBox mCbCollection;
    /** 底层布局中 -- 冰冻等级(评分) */
    @BindView(R.id.tv_shopdetails_chilled_level)
    TextView mTvChilledLevel;
    /** 底层布局中 -- 客服态度(评分) */
    @BindView(R.id.tv_shopdetails_service_attitude)
    TextView mTvServiceAttitude;
    /** 底层布局中 -- 物流速度(评分) */
    @BindView(R.id.tv_shopdetails_logistics_speed)
    TextView mTvLogisticsSpeed;
    /** 底层布局中 -- 订单总数:x单 */
    @BindView(R.id.tv_shopdetails_ordertotalnumber)
    TextView mTvOrderTotalNumber;
    /** 底层布局中 -- 月销单量:x单 */
    @BindView(R.id.tv_shopdetails_monthlyordernumber)
    TextView mTvMonthlyOrderNumber;
    /** 底层布局中 -- 中心地址:xxx */
    @BindView(R.id.tv_shopdetails_centeraddress)
    TextView mTvCenterAddress;
    /** 底层布局中 -- 中心电话:xxx */
    @BindView(R.id.tv_shopdetails_centerphone)
    TextView mTvCenterPhone;
    /** 底层布局中 -- 中心介绍 */
    @BindView(R.id.tv_shopdetails_centerintro)
    TextView mTvCenterintro;
    /** 底层布局中 -- 举报中心 */
    @BindView(R.id.tv_shopdetails_reprotcenter)
    TextView mTvReprotcenter;

    /** 底层布局中 -- 中心优惠劵 -- 布局 */
    @BindView(R.id.ll_shopdetails_coupon_layout)
    LinearLayout mLlCouponLayout;

    /** 底层布局中 -- 中心资质 -- 布局 */
    @BindView(R.id.ll_shopdetails_qualifications_layout)
    LinearLayout mLlQualifiLayout;

    @BindView(R.id.tv_search)
    TextView mTvSearch;

    /** 底层布局中 -- 中心评价 -- 布局 */
    @BindView(R.id.ll_shopdetails_score)
    LinearLayout mLlScore;


    /** 关键字 -- 适配器 */
    private RecyclerArrayAdapter mKeyWordAdapter;

    /** 小购物车中的"商品列表" */
    EasyRecyclerView mErvSmallCart;

    /** 左边"分类" */
    private List mLeftTitle;
    /** 左边"分类" -- 适配器 */
    private RecyclerArrayAdapter mTitleAdapter;
    /** 右边"具体商品" -- 适配器 */
    private RecyclerArrayAdapter mGoodsAdapter;

    private SelectBean mSelectBean;
    private SelectBean mTitleSelectBean;

    /** 当前店铺id */
    private String mCurr_shopId;
    private boolean isOpen = true;
    /** 要跳到的分类id -- 可以为空 */
    private String mCatId = "";

    private ShopInfoBean.DataEntity.GoodsResEntity mAddBean;
    private RecyclerArrayAdapter mSmallCartAdapter;
    private CartBean.DataEntity.CartListEntity mCurrEditBean;//当前编辑的bean
    private int mCurrEditNumber;
    private ShopInfoBean.DataEntity.ShopInfoEntity.GradeEntity mGrade;

    /** 普通分类 */
    private List<ShopInfoBean.DataEntity.CatResEntity.CartEntity> mCart;//普通cat
    /** 其他分类(推荐/爆款/新品...) */
    private List<ShopInfoBean.DataEntity.CatResEntity.OthercartEntity> mOtherCart;//推荐cat
    private String mShareTitle = "";
    private String mShareContent = "";
    private String mShareImageUrl = "";
    //private String mShareUrl = "http://m.gelifood.com/shop/";
    private String mShareUrl = "http://m.gelistore.com/shop/";
    private ShopInfoBean.DataEntity mShopData;


    /** 是否允许左边的滑动 */
    public boolean mLeftIsScroll = true;

    /** 粘性标题 适配器 */
    private StickyHeaderAdapter mStickyHeaderAdapter = null;
    /** 粘性标题 */
    private StickyHeaderDecoration mStickyHeaderDecoration = null;

    /** 左边点击的下标 */
    int leftPosition = 0;

    /**
     * 选中的"商品"在列表位置的信息
     */
    public class SelectBean {
        /** 当前"0级分类"id -- 应该是"目标"分类吧 */
        public int bigId = -1;
        /** 当前"小分类"id */
        public int xiaoId = -1;
        /** 是不是"其他分类" */
        public boolean isOrther;

        /** 是不是点击同一个 */
        public boolean isSame = true;

        @Override
        public String toString() {
            return "SelectBean{" +
                    "bigId=" + bigId +
                    ", xiaoId=" + xiaoId +
                    ", isOrther=" + isOrther +
                    ", isSame=" + isSame +
                    '}';
        }
    }


    public static ShopDetailsFragment newInstance(String shopId) {
        return newInstance(shopId, true);
    }

    public static ShopDetailsFragment newInstance(String shopId, boolean isOpen) {
        ShopDetailsFragment shopDetailsFragment = new ShopDetailsFragment();
        Bundle args = new Bundle();
        args.putString(INTENT_SHOP_ID, shopId);
        args.putBoolean(INTENT_IS_OPEN, isOpen);
        shopDetailsFragment.setArguments(args);
        return shopDetailsFragment;
    }

    public static ShopDetailsFragment newInstance(String shopId, String catId, boolean isOpen) {
        ShopDetailsFragment shopDetailsFragment = new ShopDetailsFragment();
        Bundle args = new Bundle();
        args.putString(INTENT_SHOP_ID, shopId);
        args.putString(INTENT_CAT_ID, catId);
        args.putBoolean(INTENT_IS_OPEN, isOpen);
        shopDetailsFragment.setArguments(args);
        return shopDetailsFragment;
    }

    @Override
    public int getResId() {
        return R.layout.fragment_shopdetails_content;
    }

    @Override
    protected void init() {
        super.init();
        ImmersionBar.setTitleBar((Activity) mContext, mToolbar);
        if (mContext instanceof ShopDetailsActivity) {
            ((ShopDetailsActivity) mContext).setOnBackListener(this);
        }
        mCurr_shopId = getArguments().getString(INTENT_SHOP_ID);
        mCatId = getArguments().getString(INTENT_CAT_ID);
        isOpen = getArguments().getBoolean(INTENT_IS_OPEN, isOpen);

        // mCurr_shopId = "100762";
    }

    @Override
    protected void initData() {
        mSelectBean = new SelectBean();
        mTitleSelectBean = new SelectBean();
        mLeftTitle = new ArrayList();
        mNevError.setVisibility(View.GONE);

        initShare();

        initErvKeyWord();               // 设置"关键字"列表
        initErvSmallCart();             // 设置"购物车"列表
        initErvGoodsTitle();            // 设置"店铺详情的上层布"的"左边列表(商品分类)"
        initErvGoodsContent();          // 设置"店铺详情的上层布"的"右边列表(商品详情)"

        requestData();                  // 请求"商品"的详细信息
    }



    @Override
    protected void initEvent() {

        setTitleListener();                 // 左边 -- 监听事件
        setRightListener();                 // 右边的 -- 监听事件

        mSdlLayout.setLayoutChangeListener(this);
        mSclLayout.setSmallCartOpenListener(this);
        mTvBottom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!mSdlLayout.mGoodsIsOpen) {
                    mSdlLayout.moveInit();
                }
                return false;
            }
        });

        mNevError.setClickRefreshListener(new NetworkingErrorView.ClickRefreshListener() {
            @Override
            public void clickRefresh() {
                requestData();
            }
        });
    }

    /**
     * 设置"分享"
     */
    private void initShare() {
        if (TextUtils.isEmpty(mCurr_shopId)) {                          // 空的--直接就是网址
            mShareUrl = "http://app.gelifood.com/d/mobile/";
        } else {
            mShareUrl += mCurr_shopId;                                  // 有商品编号的就添加在网址后
        }
    }

    /**
     * 为EasyRecyclerView 添加"粘性标题"
     * @param context
     * @param erv
     * @param arrayAdapter
     */
    private void addStickyHeaderDecoration(Context context, EasyRecyclerView erv, RecyclerArrayAdapter arrayAdapter){
        mStickyHeaderAdapter = new StickyHeaderAdapter(context, arrayAdapter);
        mStickyHeaderDecoration = new StickyHeaderDecoration(mStickyHeaderAdapter);
        mStickyHeaderDecoration.setIncludeHeader(false);
        erv.addItemDecoration(mStickyHeaderDecoration);
    }

    /**
     * 设置关键字
     */
    private void initErvKeyWord() {
        mErvKeyWord.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mErvKeyWord.addItemDecoration(new DividerDecoration(Utils.getColor(R.color.transparent),
                Utils.dip2px(mContext, 11f), 0, 0));
        mErvKeyWord.setAdapterWithProgress(initAdapterKeyWord());

    }

    private RecyclerView.Adapter initAdapterKeyWord() {
        mKeyWordAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new KeyWordRestaurantVH(parent, mContext, 1);
            }
        };

        return mKeyWordAdapter;
    }

    /**
     * 设置下"小购物车"
     */
    private void initErvSmallCart(){
        mSmallCartAdapter = new RecyclerArrayAdapter(mContext) {
            int type_shop = 1 << 0;
            int type_goods = 1 << 1;

            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                if (viewType == type_shop) {
                    return new SmallCartTitleViewHolder(parent, mContext, new SmallCartTitleViewHolder.SmallCartTitleI() {
                        @Override
                        public int getCartGoodsNumber() {
                            return getCartGoodsCount();
                        }
                    });
                } else {
                    return new SmallCartViewHolder(parent, mContext, new SmallCartLayout.SmallCartEditListener() {
                        @Override
                        public void editCart(CartBean.DataEntity.CartListEntity data, int number, boolean isAdd) {
                            setEditCart(data, number, isAdd);
                        }

                        @Override
                        public void deleteGoods(CartBean.DataEntity.CartListEntity data) {
                            setDeleteGoods(data);
                        }
                    });
                }
            }

            @Override
            public int getViewType(int position) {
                Object cartBaseBean = mSmallCartAdapter.getItem(position);
                if (cartBaseBean instanceof CartBean.DataEntity) {          /* 商店项 */
                    return type_shop;
                }
                return type_goods;
            }
        };

        // 从小购物车中
        mErvSmallCart = mSclLayout.getErvList(mSmallCartAdapter, mPresenter);
        DividerDecoration itemDecoration = new DividerDecoration(Color.WHITE, Utils.dip2px(mContext, 10));
        itemDecoration.setDrawLastItem(true);
        itemDecoration.setDrawHeaderFooter(true);
        mErvSmallCart.addItemDecoration(itemDecoration);
        mErvSmallCart.setAdapterWithProgress(mSmallCartAdapter);
    }

    /**
     * 设置"店铺详情的上层布"的"左边列表(商品分类)"
     * 设置下"商品分类列表"
     */
    private void initErvGoodsTitle(){
        mErvGoodsTitle.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mErvGoodsTitle.setAdapterWithProgress(mTitleAdapter = new RecyclerArrayAdapter(mContext) {
            int orther = 1 << 0;            // 其他(推荐)
            int normal = 1 << 1;            // 正常的商品分类

            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                if (viewType == orther) {
                    return new GoodsLabelOrtherViewHolder(parent, mContext, new GoodsLabelOrtherViewHolder.SelectInterface() {
                        @Override
                        public SelectBean getSelect() {
                            return getSelectBean();
                        }
                    });
                } else {
                    return new GoodsLabelViewHolder(parent, mContext, ShopDetailsFragment.this);
                }
            }
            @Override
            public int getViewType(int position) {
                if (mLeftTitle.get(position) instanceof ShopInfoBean.DataEntity.CatResEntity.OthercartEntity) {
                    return orther;
                }
                return normal;
            }
        });

        EasyRecyclerViewUtils.initEasyRecyclerView(mErvGoodsTitle);
    }


    /**
     * 设置"店铺详情的上层布"的"右边列表(商品详情)"
     * 设置下"商品列表"
     */
    private void initErvGoodsContent(){
        mErvGoodsContent.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mErvGoodsContent.setAdapterWithProgress(mGoodsAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new ShopDetailsGoodsViewHolder(parent, mContext, ShopDetailsFragment.this);
            }
        });

        EasyRecyclerViewUtils.initEasyRecyclerView(mErvGoodsContent);
        addStickyHeaderDecoration(mContext, mErvGoodsContent, mGoodsAdapter);   // 设置粘性头部
    }




    /**
     *  左边 -- 监听事件
     */
    private void setTitleListener(){

        /* 左边点击时，禁止右边滑动带动着边动 */
        mErvGoodsTitle.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                mLeftIsScroll = false;
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


        /* 左边 -- 点击事件 */
        mTitleAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                leftItemClick(position, -1);
            }
        });
    }

    private void leftItemClick(int position, int xiaoId) {
//        LogUtils.i("position:" + position);
//        LogUtils.i("mTitleAdapter.getCount():" + mTitleAdapter.getCount());

        if(position < 0 || position >= mTitleAdapter.getCount() ){
            return;
        }

        leftPosition = position;    // 如果有新数据加入 就 再次刷新


//        LogUtils.i("leftPosition:" + leftPosition);

        Object item = mTitleAdapter.getItem(position);
        /* 点击的是"哪种分类" */
        if (item instanceof ShopInfoBean.DataEntity.CatResEntity.OthercartEntity) {        /* "其他分类" */

//            LogUtils.i("OthercartEntity:" + ((ShopInfoBean.DataEntity.CatResEntity.OthercartEntity) item).toString());

            mTitleSelectBean.isOrther = true;
            mTitleSelectBean.bigId = ((ShopInfoBean.DataEntity.CatResEntity.OthercartEntity) item).getGs_id();
            mTitleSelectBean.isSame = true;
            int i = 0;
            List<ShopInfoBean.DataEntity.GoodsResEntity> goodsResEntityList = mGoodsAdapter.getAllData();
            for(ShopInfoBean.DataEntity.GoodsResEntity entity : goodsResEntityList){
                if(entity.getGs_id() == mTitleSelectBean.bigId){
                    smoothMoveToPosition(mErvGoodsContent.getRecyclerView(), i);
                    break;
                }
                i++;
            }
        } else {                                                                            /* "普通分类" */
            ShopInfoBean.DataEntity.CatResEntity.CartEntity cartEntity =
                    (ShopInfoBean.DataEntity.CatResEntity.CartEntity) item;

//            LogUtils.i("CartEntity:" + ((ShopInfoBean.DataEntity.CatResEntity.CartEntity) item).toString());

            if(mTitleSelectBean.bigId == cartEntity.getCat_id()){  // "本次点击分类"和上次分类"是否相同"
                if(mTitleSelectBean.isSame){
                    mTitleSelectBean.isSame = false;
                }else {
                    mTitleSelectBean.isSame = true;
                }
            } else {
                mTitleSelectBean.isSame = false;
            }

            mTitleSelectBean.isOrther = false;
            mTitleSelectBean.bigId = cartEntity.getCat_id();            // 点中的"大分类"
            mTitleSelectBean.xiaoId = -1;

            int i = 0;
            List<ShopInfoBean.DataEntity.GoodsResEntity> goodsResEntityList = mGoodsAdapter.getAllData();
            for(ShopInfoBean.DataEntity.GoodsResEntity entity : goodsResEntityList){

                if(entity.getGs_id() != -1){
                    i++;
                    continue;
                }
                if(cartEntity.getSmallCartList().size() > 0) {                      /* 有子分类 */
                    if(entity.getParent_id() == mTitleSelectBean.bigId){      // 有子分类 -- 说明父分类没有对应的食品 -- 只有找到"食物"的"父类分类"
                        if(xiaoId <= 0) {
                            mTitleSelectBean.xiaoId = entity.getCat_id();
                        }else {
                            mTitleSelectBean.xiaoId = xiaoId;
                        }
                        smoothMoveToPosition(mErvGoodsContent.getRecyclerView(), i);
                        break;
                    }
                }else if(entity.getCat_id() == mTitleSelectBean.bigId){                  /* 没有子分类 */
                    smoothMoveToPosition(mErvGoodsContent.getRecyclerView(), i);
                    break;
                }
                i++;
            }
        }

        mTitleAdapter.notifyDataSetChanged();
    }

    /**
     * 再次跳到这个位置 -- 因为"部分商品"是在到了对应的分类才加载的
     * @param position
     */
    private void again(int position){
        SelectBean selectBean = new SelectBean();
        if(position < 0 || position >= mTitleAdapter.getCount() ){
            return;
        }

        Object item = mTitleAdapter.getItem(position);
                /* 点击的是"哪种分类" */
        if (item instanceof ShopInfoBean.DataEntity.CatResEntity.OthercartEntity) {        /* "其他分类" */
            selectBean.isOrther = true;
            selectBean.bigId = ((ShopInfoBean.DataEntity.CatResEntity.OthercartEntity) item).getGs_id();
            int i = 0;
            List<ShopInfoBean.DataEntity.GoodsResEntity> goodsResEntityList = mGoodsAdapter.getAllData();
            for(ShopInfoBean.DataEntity.GoodsResEntity entity : goodsResEntityList){
                if(entity.getGs_id() == selectBean.bigId){
                    smoothMoveToPosition(mErvGoodsContent.getRecyclerView(), i);
                    break;
                }
                i++;
            }
        } else {                                                                            /* "普通分类" */
            ShopInfoBean.DataEntity.CatResEntity.CartEntity cartEntity =
                    (ShopInfoBean.DataEntity.CatResEntity.CartEntity) item;

            selectBean.isOrther = false;
            selectBean.bigId = cartEntity.getCat_id();
            selectBean.xiaoId = -1;

            int i = 0;
            List<ShopInfoBean.DataEntity.GoodsResEntity> goodsResEntityList = mGoodsAdapter.getAllData();
            for(ShopInfoBean.DataEntity.GoodsResEntity entity : goodsResEntityList){

                if(entity.getGs_id() != -1){
                    i++;
                    continue;
                }

                if(cartEntity.getSmallCartList().size() > 0) {                      /* 有子分类 */
                    if(entity.getParent_id() == selectBean.bigId){      // 有子分类 -- 说明父分类没有对应的食品
                        smoothMoveToPosition(mErvGoodsContent.getRecyclerView(), i);
                        break;
                    }
                }else if(entity.getCat_id() == selectBean.bigId){                  /* 没有子分类 */
                    smoothMoveToPosition(mErvGoodsContent.getRecyclerView(), i);
                    break;
                }
                i++;
            }
        }
    }

    /**
     * 右边的 -- 监听事件
     */
    private void setRightListener(){

        /* 右边滑动的时候 */
        mErvGoodsContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(!mLeftIsScroll)
                    mLeftIsScroll = true;
                if(!mTitleSelectBean.isSame)
                    mTitleSelectBean.isSame = false;    // 右边滑动的时候 -- 相同标志设为"false"
                return false;
            }
        });

        /* 右边的点击事件 */
        mGoodsAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Object o = mGoodsAdapter.getAllData().get(position);
                if (o instanceof ShopInfoBean.DataEntity.GoodsResEntity) {
                    if (((ShopInfoBean.DataEntity.GoodsResEntity) o).dataNUll) {
                        return;
                    }
                    Intent intent = new Intent();
                    intent.putExtra(INTENT_GOODS_ID, ((ShopInfoBean.DataEntity.GoodsResEntity) o).getGoods_id() + "");
                    startActivity(GoodsDetailsActivity.class, intent);
                }
            }
        });

        /* 右边 -- 滑动事件 */
        mErvGoodsContent.addOnScrollListener(new RecyclerView.OnScrollListener() {

            int notifiNumber = 3;

            /**
             *
             * dx > 0 时为手指向左滚动,列表滚动显示右面的内容
             * dx < 0 时为手指向右滚动,列表滚动显示左面的内容
             * dy > 0 时为手指向上滚动,列表滚动显示下面的内容
             * dy < 0 时为手指向下滚动,列表滚动显示上面的内容
             *
             * @param recyclerView 当前滚动的view
             * @param dx 水平滚动距离
             * @param dy 垂直滚动距离
             */
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstVisiblePosition = manager.findFirstVisibleItemPosition();         // 第一个可见的项
                int lastVisiblePosition = manager.findLastVisibleItemPosition();         // 最后可见的项
                int lastPosition = mGoodsAdapter.getCount() -1;

                ShopInfoBean.DataEntity.GoodsResEntity baseBean;
                if(firstVisiblePosition != 0 && lastVisiblePosition == lastPosition){
                    baseBean = (ShopInfoBean.DataEntity.GoodsResEntity) mGoodsAdapter.getItem(lastVisiblePosition);
                }else {
                    baseBean = (ShopInfoBean.DataEntity.GoodsResEntity) mGoodsAdapter.getItem(firstVisiblePosition);
                }



                if (mSelectBean.isOrther != (baseBean.getGs_id() != -1)
                        || mSelectBean.bigId != baseBean.getParent_id()
                        || mSelectBean.xiaoId != baseBean.getCat_id()) {

                    if (baseBean.getGs_id() != -1) {                            /* "其他分类" */
                        mSelectBean.bigId = baseBean.getGs_id();

                    } else {                                                    /* "普通分类" */
                        ShopInfoBean.DataEntity.CatResEntity.CartEntity cartEntity = new ShopInfoBean.DataEntity.CatResEntity.CartEntity();
                        cartEntity.setCat_id(baseBean.getCat_id());

                        if (mCart.contains(cartEntity)) {              /* 0级分类 */
                            for (int i = 0; i < mCart.size(); i++) {
                                ShopInfoBean.DataEntity.CatResEntity.CartEntity cat = mCart.get(i);
                                if (cat.getCat_id() == baseBean.getCat_id()) {
                                    mSelectBean.bigId = baseBean.getCat_id();
                                    mSelectBean.xiaoId = -1;
                                }
                            }
                        } else {                                      /* 子分类 */
                            for (int i = 0; i < mCart.size(); i++) {
                                ShopInfoBean.DataEntity.CatResEntity.CartEntity cat = mCart.get(i);
                                List<ShopInfoBean.DataEntity.CatResEntity.CartEntity> smallCartList = cat.getSmallCartList();
                                for (ShopInfoBean.DataEntity.CatResEntity.CartEntity entity : smallCartList) {
                                    if (entity.getCat_id() == baseBean.getCat_id()) {
                                        mSelectBean.bigId = entity.getParent_id();
                                        mSelectBean.xiaoId = entity.getCat_id();
                                        break ;
                                    }
                                }
                            }
                        }
                    }

                    if(mLeftIsScroll){
                        mTitleSelectBean.bigId = mSelectBean.bigId;
                        mTitleSelectBean.xiaoId = mSelectBean.xiaoId;
                    }
                    mSelectBean.isOrther = (baseBean.getGs_id() != -1);
                    int index = 0;
                    List allData = mTitleAdapter.getAllData();
                    for (int i = 0; i < allData.size(); i++) {
                        Object o = allData.get(i);
                        // 区分是 "其他" 还是 "普通" table
                        if (o instanceof ShopInfoBean.DataEntity.CatResEntity.OthercartEntity && mSelectBean.isOrther) {
                            if (((ShopInfoBean.DataEntity.CatResEntity.OthercartEntity) o).getGs_id() == mSelectBean.bigId) {
                                notifiTitleAdapter();
                                break;
                            }
                        } else if (o instanceof ShopInfoBean.DataEntity.CatResEntity.CartEntity && !mSelectBean.isOrther) {
                            if (((ShopInfoBean.DataEntity.CatResEntity.CartEntity) o).getLevel() == 0
                                    && ((ShopInfoBean.DataEntity.CatResEntity.CartEntity) o).getCat_id() == mSelectBean.bigId) {
                                notifiTitleAdapter();
                                break;
                            }
                        }
                        index++;
                    }
                }
            }

            /**
             * 更改"左边"的列表选中位置
             */
            public void notifiTitleAdapter() {
                if(mLeftIsScroll) {
                    mTitleAdapter.notifyDataSetChanged();
                }
            }

            /**
             *
             * 滚动状态发生变化时，系统会回调这个方法。
             * 1、正在滚动 public static final int SCROLL_STATE_IDLE = 0;
             * 2、正在被外部拖拽,一般为用户正在用手指滚动 public static final int SCROLL_STATE_DRAGGING = 1;
             * 3、自动滚动开始 public static final int SCROLL_STATE_SETTLING = 2;
             * @param recyclerView
             * @param newState
             */
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_SETTLING) {       /* 自动滚动开始 */
                    Glide.with(mContext).pauseRequests();
                } else {
                    if (Glide.with(mContext).isPaused()) {
                        Glide.with(mContext).resumeRequests();
                    }
                }

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {           /* 正在滚动 */
                    if (mShouldScroll) {
                        mShouldScroll = false;
                        smoothMoveToPosition(recyclerView, mToPosition);
                    }
                }

                /* 正在被外部拖拽,一般为用户正在用手指滚动  ||  正在滚动 */
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING || newState == RecyclerView.SCROLL_STATE_IDLE) {
                    LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (manager.findFirstVisibleItemPosition() <= 0) {
                        return;
                    }

                    for (int i = manager.findFirstVisibleItemPosition(); i < manager.findLastVisibleItemPosition() + 1; i++) {
                        ShopInfoBean.DataEntity.GoodsResEntity goodsResEntity = (ShopInfoBean.DataEntity.GoodsResEntity) mGoodsAdapter.getItem(i - mGoodsAdapter.getHeaderCount());
                        if (!goodsResEntity.dataNUll) {
                            continue;
                        }
                        if (goodsResEntity.getGs_id() != -1) {
                            mPresenter.getGoodsFromOtherCat(mCurr_shopId, goodsResEntity.getGs_id() + "");
                        } else {
                            mPresenter.getGoodsFromCat(mCurr_shopId, goodsResEntity.getCat_id() + "");
                        }
                    }
                }
            }
        });
    }


    /**
     * 获取 -- 当前购物车中所有商品的数量之和
     * @return
     */
    public int getCartGoodsCount() {
        int size = 0;
        List allData = mSmallCartAdapter.getAllData();
        for (Object childe : allData) {
            if (childe instanceof CartBean.DataEntity.CartListEntity) {
                size += ((CartBean.DataEntity.CartListEntity) childe).getCart_number();
            }
        }
        return size;
    }

    @Override
    public void showGoodSpecifi(SpecifiBean bean) {
        // AddCartDialog.newInstance(bean.getData(), mAddBean, this).show(getChildFragmentManager(), "");
        AddCartMiddleDialog.newInstance(bean.getData(), mAddBean, this).show(getChildFragmentManager(), "");
    }

    @Override
    public void addCart(String json, String sku, String number) {
        Map data = new HashMap();
        data.put("user_id", GlobalData.getUser_id());
        data.put("goods_id", mAddBean.getGoods_id() + "");
        data.put("cart_number", number);
        data.put("specification", json);
        data.put("sku_id", sku + "");
        data.put("cart_type", 1 + "");
        mPresenter.isAddCart = true;
        mPresenter.editCart(data, true);
    }


    /**
     * 只有一个规格的时候
     */
    public void addCartOnly(ShopInfoBean.DataEntity.GoodsResEntity addBean) {
        mAddBean = addBean;
        String json = new Gson().toJson(mAddBean.getSpecifications());

        Map data = new HashMap();
        data.put("user_id", GlobalData.getUser_id());
        data.put("goods_id", mAddBean.getGoods_id() + "");
        data.put("cart_number", 1 + "");
        data.put("specification", json);
        data.put("cart_type", 1 + "");
        mPresenter.isAddCart = true;
        mPresenter.editCart(data, true);
    }

    @Override
    public void showCartList(List<CartBean.DataEntity> data) {
        mSclLayout.setData(data);
    }

    /**
     * 解析数据"购物车数据"   返回--List--[0]数量 [1]总金额
     *
     *
     * @param data
     * @param cartList
     * @param totalPrice
     * @param tag        1:只要格利  2:只要海外  3:都要
     * @return [0]数量 [1]总金额
     */
    public static List parseCartData(List<CartBean.DataEntity> data, List<Object> cartList, Double totalPrice, int tag) {
        int cartNumber = 0;                         // 商品数量
        CartBean.DataEntity overseasShop = null;    // "海外"商店
        List overseasList = null;                   // "海外"列表
        int index = 0;

        for (int i = 0; i < data.size(); i++) {
            CartBean.DataEntity dataEntity = data.get(i);
            List<CartBean.DataEntity.CartListEntity> cart_list = dataEntity.getCart_list();
            Iterator<CartBean.DataEntity.CartListEntity> iterator = cart_list.iterator();
            int j = 0;
            while (iterator.hasNext()) {
                CartBean.DataEntity.CartListEntity next = iterator.next();
                if (TextUtils.isEmpty(dataEntity.unit)) {
                    dataEntity.unit = next.getGoods_unit();
                }

                if (next.getGoods_type() == 3 || next.getGoods_type() == 2) {       /* 2:海外现货; 3:海外期货 */
                    if (overseasShop == null) {
                        overseasShop = new CartBean.DataEntity();
                        overseasShop.setShop_name(Utils.getString(R.string.overseas_shop));
                        overseasShop.setShop_id(Constant.overseasId);
                        overseasList = new ArrayList<>();
                        overseasList.add(overseasShop);
                    }
                    overseasShop.size += 1;
                    next.shop_id = overseasShop.getShop_id();
                    if (tag == 2 || tag == 3) {                 // tag -- 1:只要格利  2:只要海外  3:都要
                        cartNumber++;
                        // 求"积"
                        totalPrice += Utils.mul(Double.valueOf(next.getCart_price()), Double.valueOf(next.getCart_number()));
                    }
                    overseasList.add(next);


                } else {                                                            /* 不是海外 */
                    if (j == 0) {
                        cartList.add(dataEntity);
                    }
                    j++;
                    next.shop_id = dataEntity.getShop_id();
                    dataEntity.size += 1;
                    if (tag == 1 || tag == 3) {                 // tag -- 1:只要格利  2:只要海外  3:都要
                        cartNumber++;
                        totalPrice += Utils.mul(Double.valueOf(next.getCart_price()), Double.valueOf(next.getCart_number()));
                    }
                    cartList.add(next);
                    if (dataEntity.getShop_id() == 1) {
                        index = cartList.size();
                    }
                }
                iterator.remove();
            }
        }

        if (tag != 1) {                         // tag -- 1:只要格利  2:只要海外  3:都要
            if (tag == 2) {
                cartList.clear();
            }
            if (overseasList != null && overseasList.size() > 0) {
                cartList.addAll(cartList.size() == 0 ? 0 : index, overseasList);
            }
        }

        List number = new ArrayList();
        number.add(cartNumber);
        number.add(totalPrice);
        return number;
    }

    @OnClick({R.id.bt_shopdetails_callserver, R.id.tv_shopdetails_centerintro,
            R.id.tv_shopdetails_reprotcenter, R.id.tv_search,
            R.id.bt_shopdetails_checkallcomment, R.id.cb_shopdetails_collection,
            R.id.iv_shopdetails_shear, R.id.iv_shopdetails_back})
    @Override
    public void onClick(View v) {

        Intent intent;

        switch (v.getId()) {
            case R.id.bt_shopdetails_callserver:                                /* 联系客服 */
                if (mShopData != null) {
                    Utils.callPhone(mContext, mShopData.getShop_info().getShop_tel());
                }
                break;

            case R.id.tv_shopdetails_centerintro:                               /* 中心介绍 */
                if (mShopData != null) {
                    intent = new Intent().putExtra(INTENT_LINKS, mShopData.getShop_info().getShop_detail_url());
                    ((BaseActivity) mContext).startActivity(WebViewActivity.class, intent);
                }
                break;

            case R.id.tv_search:                                                /* 查询 */
                ViewCompat.setTransitionName(mTvSearch, SearchActivity.SEARCH_TRANSITIONNAME);
                intent = new Intent(mContext, SearchActivity.class);
                intent.putExtra(SearchActivity.INTENT_SEARCHTYPE, SearchActivity.SEARCH_TYPE_S);
                intent.putExtra(INTENT_SHOP_ID, mCurr_shopId);

                // 设置"共享控件"  -- 场景过渡动画
                ActivityOptionsCompat activityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation((BaseActivity) mContext,
                        Pair.create((View) mTvSearch, ViewCompat.getTransitionName(mTvSearch)));
                ActivityCompat.startActivity(mContext, intent, activityOptionsCompat.toBundle());
                break;

            case R.id.iv_shopdetails_back:                                      /* 返回按钮 */
                ((ShopDetailsActivity) mContext).finish();
                break;

            case R.id.tv_shopdetails_reprotcenter:                              /* 举报中心 */
                ((BaseActivity) mContext).startActivity(ReportShopActivity.class, new Intent().putExtra(INTENT_SHOP_ID, mCurr_shopId));
                break;

            case R.id.iv_shopdetails_shear:                                     /* 分享 */
                ShareDialog.newInstance(mShareTitle, mShareContent, mShareImageUrl, mShareUrl).show(getChildFragmentManager(), "");
                break;

            case R.id.cb_shopdetails_collection:                                /* 收藏按钮 */
                mCbCollection.setChecked(!mCbCollection.isChecked());
                Map<String, String> data = new HashMap();
                data.put("user_id", GlobalData.getUser_id());
                data.put("shop_id", mCurr_shopId);
                data.put("col_type", 2 + "");
                mPresenter.shopCollection(data);
                break;

            case R.id.bt_shopdetails_checkallcomment:                           /* 查看全部评论 */
                intent = new Intent();
                intent.putExtra(AllCommentActivity.INTENT_TYPE, AllCommentActivity.TYPE_SHOP);
                intent.putExtra(AllCommentActivity.INTENT_ID, mCurr_shopId + "");
                intent.putExtra(AllCommentActivity.INTENT_GRADE, mGrade);
                ((BaseActivity) mContext).startActivity(AllCommentActivity.class, intent);
                break;

            default:
                break;
        }
    }

    /**
     * 获得购物车列表
     */
    public void getCartList() {
        if (LoginInformtaionSpUtils.getLoginInfString(mContext, LoginInformtaionSpUtils.login_login).equals("1")) {
            Map carList = new HashMap();
            carList.put("user_id", GlobalData.getUser_id());
            carList.put("shop_id", mCurr_shopId);
            mPresenter.getCartList(carList, false);
        }
    }




    /**
     * 购物车，商品的编辑 -- 增加 或 减少 商品
     *
     * @param data
     * @param number 编辑后的number
     */
    public void setEditCart(CartBean.DataEntity.CartListEntity data, int number, boolean isAdd) {
        Map map = new HashMap();
        map.put("user_id", GlobalData.getUser_id());
        map.put("goods_id", data.getGoods_id());
        if (isAdd) {
            map.put("cart_number", "1");    /* 累+ */
        } else {
            map.put("cart_number", "-1");   /* 累- */
        }
        map.put("sku_id", data.getSku_id());
        map.put("cart_type", "1");
        if (data.getSpecification() != null) {
            map.put("specification", new Gson().toJson(data.getSpecification()));
        }
        mCurrEditBean = data;
        mCurrEditNumber = number;
        mPresenter.editCart(map, false);
    }

    /**
     * 购物车，删除"这件商品"
     * @param data
     */
    public void setDeleteGoods(CartBean.DataEntity.CartListEntity data) {
        mCurrEditBean = data;
        mPresenter.deleteCart(GlobalData.getUser_id(), data.getCart_id() + "");
    }


    /**
     * 请求"商品"的详细信息
     */
    private void requestData() {
        Map data = new HashMap();
        data.put("user_id", GlobalData.getUser_id());
        data.put("shop_id", mCurr_shopId);
        mPresenter.getShopInfo(data);

        getCartList();
    }

    public void showSelectPrice(ShopInfoBean.DataEntity.GoodsResEntity data) {
        mAddBean = data;
        mPresenter.getGoodsSpecifi(data.getGoods_id() + "");
    }


    /** 目标项是否在"最后一个可见项"之后 */
    private boolean mShouldScroll;
    /** 记录目标项位置 */
    private int mToPosition;

    /**
     * 滑动到指定位置
     *
     * @param mRecyclerView
     * @param position
     */
    public void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {

        /** 第一个可见位置 */
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        /** 最后一个可见位置 */
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));

        if (position < firstItem) {                 /* "跳转的位置"在"第一个可见项之前" */
            mRecyclerView.smoothScrollToPosition(position); // 如果要跳转的位置在第一个可见项之前，则smoothScrollToPosition可以直接跳转

        } else if (position <= lastItem) {          /* "跳转的位置"在"第一个可见项之后"，且在"最后一个可见项之前" */
            // smoothScrollToPosition根本不会动，此时调用smoothScrollBy来滑动到指定位置
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                int i = Utils.dip2px(mContext, 25);           // 粘性标题的高度
                if(Math.abs(top) >= i){                       // 粘性标题的问题(顶太上了)
                    top = top - i;
                }
                mRecyclerView.smoothScrollBy(0, top);
            }

        } else {                                    /* "跳转的位置"在"最后可见项之后" */
            // 如果要跳转的位置在最后可见项之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用smoothMoveToPosition，进入上一个控制语句
            mRecyclerView.smoothScrollToPosition(position);
            mShouldScroll = true;
            mToPosition = position;
        }
    }


    @Override
    public void open() {
        mRlSmallCartBottom.setVisibility(View.VISIBLE);
    }

    @Override
    public void close() {
        mRlSmallCartBottom.setVisibility(View.GONE);
    }

    @Override
    public void smallCartOpen() {
        mSdlLayout.moveTop();
    }

    @Override
    public boolean onBack() {
        if (mSclLayout.smallCartIsOpen) {
            mSclLayout.closeCart();
            return true;
        }
        if (mSdlLayout.isTop) {
            mSdlLayout.moveInit();
            return true;
        }
        return false;
    }

    @Override
    protected ShopInfoPresentImpl createPresent() {
        return new ShopInfoPresentImpl(this);
    }

    @Override
    public void onSuccess(String msg) {
        ShowSingleToast.showToast(mContext, msg);
        if (mPresenter.isAddCart) {
            mPresenter.isAddCart = !mPresenter.isAddCart;
            getCartList();
            return;
        }
        mSclLayout.notifyData(mCurrEditBean, mCurrEditNumber);
    }

    @Override
    public void onError(String msg) {
        //        if (!GlobalData.hasNetWork) {
        //            mNevError.setVisibility(View.VISIBLE);
        //        }
        ShowSingleToast.showToast(mContext, msg);
        if (mPresenter.isGetCartList) {
            mPresenter.isGetCartList = !mPresenter.isGetCartList;
            mErvSmallCart.showError();
        }
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
    public void showInfo(ShopInfoBean bean) {
        mNevError.setVisibility(View.GONE);
        mShopData = bean.getData();
        setShopData(mShopData.getShop_info(), (ArrayList<CouponBean>) mShopData.getCoupon());
        setGoodsData(bean);

        setCat();

        if (!isOpen) {
            mSdlLayout.moveBottom();
        }
    }

    private void setCat() {

        if(StringUtils.isNotEmpty(mCatId)){
            mLeftIsScroll = false;
            int position = 0;
            for(Object object : mLeftTitle){
                if(object instanceof ShopInfoBean.DataEntity.CatResEntity.OthercartEntity){     /* 其他分类 */
                    if(mCatId.equals(((ShopInfoBean.DataEntity.CatResEntity.OthercartEntity) object).getGs_id() + "")){
                        leftItemClick(position, -1);
                        mCatId = "";
                        break;
                    }

                }else if(object instanceof ShopInfoBean.DataEntity.CatResEntity.CartEntity){    /* 普通分类 */
                    ShopInfoBean.DataEntity.CatResEntity.CartEntity temp =
                            (ShopInfoBean.DataEntity.CatResEntity.CartEntity) object;
                    if(mCatId.equals(temp.getCat_id() + "")) {         // 找到"对应的分类id"
                        leftItemClick(position, -1);
                        mCatId = "";
                        break;
                    }

                    if(temp.getSmallCartList().size() > 0){ // 如果有"子分类的"，就拿子分类的
                        for(ShopInfoBean.DataEntity.CatResEntity.CartEntity temp1 : temp.getSmallCartList()){
                            if(mCatId.equals(temp1.getCat_id() + "")) {
                                leftItemClick(position, Integer.valueOf(mCatId));
                                mCatId = "";
                                break;
                            }
                        }

                    }
                }
                position++;
            }
        }
    }

    @Override
    public void showGoodsFromCat(List<ShopInfoBean.DataEntity.GoodsResEntity> dataBean) {
        if (dataBean == null || dataBean.size() == 0) {
            return;
        }

        int gsId = dataBean.get(0).getGs_id(); // 其他

        //获取第一个商品的parentId,用于删除占位数据,对应现有数据的cat_id
        int catId = dataBean.get(0).getParent_id(); // 注意:这个getParent_id 就是对应他的"分类id(无论是哪一级的)"
        int parentId = -1;


        List<ShopInfoBean.DataEntity.GoodsResEntity> goodsResEntities = mGoodsAdapter.getAllData();


        for(Object object : mLeftTitle){
            if(object instanceof ShopInfoBean.DataEntity.CatResEntity.OthercartEntity){     /* 其他分类 */
//                if(gsId == ((ShopInfoBean.DataEntity.CatResEntity.OthercartEntity) object).getGs_id()){
//                    break;
//                }
            }else if(object instanceof ShopInfoBean.DataEntity.CatResEntity.CartEntity){    /* 普通分类 */
                ShopInfoBean.DataEntity.CatResEntity.CartEntity temp =
                        (ShopInfoBean.DataEntity.CatResEntity.CartEntity) object;

                if(catId == temp.getCat_id()) {         // 找到"对应的分类id"
                    parentId = temp.getParent_id();     // 拿到这个分类的"父分类id"
                    break;
                }

                if(temp.getSmallCartList().size() > 0){ // 如果有"子分类的"，就拿子分类的
                    for(ShopInfoBean.DataEntity.CatResEntity.CartEntity temp1 : temp.getSmallCartList()){
                        if(catId == temp1.getCat_id()) {
                            parentId = temp1.getParent_id();
                            break;
                        }
                    }

                }
            }
        }
//        LogUtils.i("******************************************************************************");
//        LogUtils.printJson(dataBean.get(0).toString(), "dataBean.get(0)");
        /* 为获取的到数据 "重新设置 子类id 和 父类id" */
        int i = 0;
        for (ShopInfoBean.DataEntity.GoodsResEntity resEntity : goodsResEntities) {
            // LogUtils.printJson(resEntity.toString(), "resEntity--i=" + i);


            if(resEntity.getGs_id() != -1 && resEntity.getGs_id() == gsId && resEntity.dataNUll){
                List<ShopInfoBean.DataEntity.GoodsResEntity> tempBean = new ArrayList<>();
                for(ShopInfoBean.DataEntity.GoodsResEntity entity : dataBean){
                    if(!goodsResEntities.contains(entity))
                        tempBean.add(entity);
                }

                mGoodsAdapter.remove(i);
                mGoodsAdapter.insertAll(tempBean, i);

            }else if (resEntity.getGs_id() == -1 &&
                    resEntity.getCat_id() != -1 && resEntity.getCat_id() == catId && resEntity.dataNUll) {

                List<ShopInfoBean.DataEntity.GoodsResEntity> tempBean = new ArrayList<>();
                for(ShopInfoBean.DataEntity.GoodsResEntity entity : dataBean){
                    entity.setCat_id(catId);
                    entity.setParent_id(parentId);
                    if(!goodsResEntities.contains(entity))
                        tempBean.add(entity);
                }

                mGoodsAdapter.remove(i);
                mGoodsAdapter.insertAll(tempBean, i);
            }
            i++;
        }

        if(!mLeftIsScroll)
            again(leftPosition);

        mStickyHeaderDecoration.clearHeaderCache();
    }


    @Override
    public void showCollectionResult(String mess) {
        ShowSingleToast.showToast(mContext, mess);
        mCbCollection.setChecked(!mCbCollection.isChecked());
    }

    @Override
    public void showGoodsFromCatTemp(List<ShopInfoBean.DataEntity.GoodsResEntity> dataBean) {
        if (dataBean == null || dataBean.size() == 0) {
            return;
        }

        int gsId = dataBean.get(0).getGs_id(); // 其他

        //获取第一个商品的parentId,用于删除占位数据,对应现有数据的cat_id
        int catId = dataBean.get(0).getParent_id(); // 注意:这个getParent_id 就是对应他的"分类id(无论是哪一级的)"
        int parentId = -1;


        List<ShopInfoBean.DataEntity.GoodsResEntity> goodsResEntities = mGoodsAdapter.getAllData();


        for(Object object : mLeftTitle){
            if(object instanceof ShopInfoBean.DataEntity.CatResEntity.OthercartEntity){     /* 其他分类 */
//                if(gsId == ((ShopInfoBean.DataEntity.CatResEntity.OthercartEntity) object).getGs_id()){
//                    break;
//                }
            }else if(object instanceof ShopInfoBean.DataEntity.CatResEntity.CartEntity){    /* 普通分类 */
                ShopInfoBean.DataEntity.CatResEntity.CartEntity temp =
                        (ShopInfoBean.DataEntity.CatResEntity.CartEntity) object;

                if(catId == temp.getCat_id()) {         // 找到"对应的分类id"
                    parentId = temp.getParent_id();     // 拿到这个分类的"父分类id"
                    break;
                }

                if(temp.getSmallCartList().size() > 0){ // 如果有"子分类的"，就拿子分类的
                    for(ShopInfoBean.DataEntity.CatResEntity.CartEntity temp1 : temp.getSmallCartList()){
                        if(catId == temp1.getCat_id()) {
                            parentId = temp1.getParent_id();
                            break;
                        }
                    }

                }
            }
        }
//        LogUtils.i("******************************************************************************");
//        LogUtils.printJson(dataBean.get(0).toString(), "dataBean.get(0)");
        /* 为获取的到数据 "重新设置 子类id 和 父类id" */
        int i = 0;
        for (ShopInfoBean.DataEntity.GoodsResEntity resEntity : goodsResEntities) {
            // LogUtils.printJson(resEntity.toString(), "resEntity--i=" + i);


            if(resEntity.getGs_id() != -1 && resEntity.getGs_id() == gsId && resEntity.dataNUll){
                List<ShopInfoBean.DataEntity.GoodsResEntity> tempBean = new ArrayList<>();
                for(ShopInfoBean.DataEntity.GoodsResEntity entity : dataBean){
                    if(!goodsResEntities.contains(entity))
                        tempBean.add(entity);
                }

//                mGoodsAdapter.remove(i);
//                mGoodsAdapter.insertAll(tempBean, i);

                // 防止列表項不是显示第一项
                List<ShopInfoBean.DataEntity.GoodsResEntity> list = getGoodsList();
                list.remove(i);
                list.addAll(i, tempBean);
                mGoodsAdapter.clear();
                mGoodsAdapter.addAll(list);

            }else if (resEntity.getGs_id() == -1 &&
                    resEntity.getCat_id() != -1 && resEntity.getCat_id() == catId && resEntity.dataNUll) {

                List<ShopInfoBean.DataEntity.GoodsResEntity> tempBean = new ArrayList<>();
                for(ShopInfoBean.DataEntity.GoodsResEntity entity : dataBean){
                    entity.setCat_id(catId);
                    entity.setParent_id(parentId);
                    if(!goodsResEntities.contains(entity))
                        tempBean.add(entity);
                }

//                mGoodsAdapter.remove(i);
//                mGoodsAdapter.insertAll(tempBean, i);

                List<ShopInfoBean.DataEntity.GoodsResEntity> list = getGoodsList();
                list.remove(i);
                list.addAll(i, tempBean);
                mGoodsAdapter.clear();
                mGoodsAdapter.addAll(list);
            }
            i++;
        }

        if(!mLeftIsScroll)
            again(leftPosition);

        mStickyHeaderDecoration.clearHeaderCache();
    }

    /** 分类(所有的包括一二级的，普通分类，特殊分类) k:分类号， v:分类名 */
    Map<Integer, String> mLeftTitleMap = new HashMap<>();
    /** 第一层:分类 k:什么种类分类(其他/普通)， v:分类列表... 第二层:k:分类号， v:分类名 */
    Map<Integer, Map<Integer, String>> mLeftTitleMapMap = new HashMap<>();

    /**
     * 处理下"从后台获取的数据"
     * @param bean
     */
    private void setGoodsData(ShopInfoBean bean) {

        mLeftTitle.clear();         // 清空下左边
        mGoodsAdapter.clear();

        ShopInfoBean.DataEntity data = bean.getData();
        mCart = data.getCatRes().getCart();                     // 普通分类
        mOtherCart = data.getCatRes().getOthercart();           // 其他分类(推荐/爆款/新品...)

        // List<ShopInfoBean.DataEntity.GoodsResEntity> mGoodsRes = data.getGoodsRes();    // 所有的商品
        List<ShopInfoBean.DataEntity.GoodsResEntity> mGoodsRes = new ArrayList<>();

        mCart = mPresenter.setCartData(mCart);
        mGoodsRes = mPresenter.setGoodsResData(mGoodsRes, mCart, mOtherCart);

        mLeftTitle.addAll(mOtherCart);
        mLeftTitle.addAll(mCart);
        mTitleAdapter.addAll(mLeftTitle);
        mGoodsAdapter.addAll(mGoodsRes);



        //mLeftTitleMap = mPresenter.parse(mOtherCart, mCart);
        mLeftTitleMapMap = mPresenter.parse2(mOtherCart, mCart);
        mStickyHeaderDecoration.clearHeaderCache();
        //mStickyHeaderAdapter.setLeftTitleMap(mLeftTitleMap);
        mStickyHeaderAdapter.setLeftTitleMapMap(mLeftTitleMapMap);

        getDateNull();              /** 界面中如果有 预加载的项，就要再从后台获取对应的数据 */

//        for(Object object : mTitleAdapter.getAllData()){
//            if(object instanceof ShopInfoBean.DataEntity.CatResEntity.CartEntity){
//                ShopInfoBean.DataEntity.CatResEntity.CartEntity object1 =
//                        (ShopInfoBean.DataEntity.CatResEntity.CartEntity) object;
//                LogUtils.i("mTitleAdapter -- 普通分类:" + object1.getCat_id());
//                if(object1.getSmallCartList() != null && object1.getSmallCartList().size() > 0){
//                    for(ShopInfoBean.DataEntity.CatResEntity.CartEntity smallCat : object1.getSmallCartList()){
//                        LogUtils.i("mTitleAdapter ---- 小分类:" + smallCat.getCat_id());
//                    }
//                }
//
//            }else if(object instanceof ShopInfoBean.DataEntity.CatResEntity.OthercartEntity){
//                ShopInfoBean.DataEntity.CatResEntity.OthercartEntity object2 =
//                        (ShopInfoBean.DataEntity.CatResEntity.OthercartEntity) object;
//                LogUtils.i("mTitleAdapter -- 其他分类:" + object2.getGs_id());
//            }
//        }
//
//        for(Object object : mGoodsAdapter.getAllData()){
//            ShopInfoBean.DataEntity.GoodsResEntity object1 =
//                    (ShopInfoBean.DataEntity.GoodsResEntity) object;
//            if(object1.getGs_id() == -1){
//                LogUtils.i("mGoodsAdapter -- 普通分类:" + object.toString());
//            }else {
//                LogUtils.i("mGoodsAdapter -- 其他分类:" + object.toString());
//            }
//        }
//
//        for(Map.Entry<Integer, String> entry : mLeftTitleMap.entrySet()){
//            int key = entry.getKey();
//            String value = entry.getValue();
//            LogUtils.i("mLeftTitleMap.entrySet() -- xxx:" +
//            "key:" + key + " -- value:" + value);
//        }
//
//        for(Map.Entry<Integer, Map<Integer, String>> entry : mLeftTitleMapMap.entrySet()) {
//            for (Map.Entry<Integer, String> entry1 : entry.getValue().entrySet()) {
//                int key = entry1.getKey();
//                String value = entry1.getValue();
//                LogUtils.i("mLeftTitleMap.entrySet() -- xxx:" +
//                        "key:" + key + " -- value:" + value);
//            }
//        }
    }

    /**
     * 界面中如果有 预加载的项，就要再从后台获取对应的数据
     */
    private void getDateNull(){

        Observable
                .timer(BaseModel.delayTime200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        if(mErvGoodsContent == null)
                            return;

                        if(mErvGoodsContent.getRecyclerView() == null)
                            return;

                        LinearLayoutManager manager = (LinearLayoutManager) mErvGoodsContent.getRecyclerView().getLayoutManager();

                        if (manager.findFirstVisibleItemPosition() < 0) {
                            return;
                        }

                        for (int i = 0; i < manager.findLastVisibleItemPosition() + 1; i++) {
                            ShopInfoBean.DataEntity.GoodsResEntity goodsResEntity =
                                    (ShopInfoBean.DataEntity.GoodsResEntity) mGoodsAdapter.getItem(i - mGoodsAdapter.getHeaderCount());
                            if (!goodsResEntity.dataNUll) {
                                continue;
                            }
                            if (goodsResEntity.getGs_id() != -1) {              /* 其他分类 */
                                mPresenter.getGoodsFromOtherCatTemp(mCurr_shopId, goodsResEntity.getGs_id() + "");
                            } else {                                            /* 普通分类 */
                                mPresenter.getGoodsFromCatTemp(mCurr_shopId, goodsResEntity.getCat_id() + "");
                            }
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
     * 设置"商店信息"
     * @param shopData
     * @param coupon
     */
    public void setShopData(ShopInfoBean.DataEntity.ShopInfoEntity shopData, ArrayList<CouponBean> coupon) {
        if (!shopData.getShop_img().startsWith(GL_IMAGE_URL)) {
            mShareImageUrl = GL_IMAGE_URL + shopData.getShop_img();
        }
        mShareTitle = shopData.getShop_name();
        mShareContent = shopData.getShop_intro();
        // GlideUtils.loadImg(mContext, shopData.getShop_img(), mIvImg);
        GlideUtils.loadImgRect(mContext, shopData.getShop_img(), mIvImg, false);

        mTvTitle.setText(shopData.getShop_name());
        mTvShopName.setText(shopData.getShop_name());
        mTvIntroduction.setText(shopData.getShop_intro());
        mCbCollection.setChecked(shopData.getShop_att() == 1);
        if(shopData.getIs_resale() == resale_true){
            mTvAssemble.setVisibility(View.VISIBLE);
        }else {
            mTvAssemble.setVisibility(View.INVISIBLE);
        }

        mKeyWordAdapter.clear();
        String[] goodsMains = shopData.getGoods_main().split(",");
        if(goodsMains.length > 0){
            List goodsMainList = Arrays.asList(goodsMains);
            if(goodsMainList != null && goodsMainList.size() > 3){
                mKeyWordAdapter.addAll(goodsMainList.subList(0, 3));
            }else {
                mKeyWordAdapter.addAll(goodsMainList);
            }
        }

        mGrade = shopData.getGrade();
        if (mGrade == null) {
            mLlScore.setVisibility(View.GONE);
        } else {
            mTvChilledLevel.setText(mGrade.getCom_grade() + "");
            mTvLogisticsSpeed.setText(mGrade.getLogistics_grade() + "");
            mTvServiceAttitude.setText(mGrade.getServe_grade() + "");
        }
        mTvOrderTotalNumber.setText(Utils.getString(R.string.shopdetails_ordernumber, shopData.getCountOrder()));
        mTvMonthlyOrderNumber.setText(Utils.getString(R.string.shopdetails_monthlyordernumber, shopData.getCount_month()));
        mTvCenterAddress.setText(Utils.getString(R.string.shopdetails_centeraddress, shopData.getAddress()));
        mTvCenterPhone.setText(Utils.getString(R.string.shopdetails_centerphone, shopData.getShop_tel()));

        if (coupon.size() == 0) {
            mLlCouponLayout.setVisibility(View.GONE);
        } else {
            mLlCouponLayout.setVisibility(View.VISIBLE);
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.fl_shopdetails_coupon_content, CouponFragment.newInstance(VIEWTYPE_SHOPALL, coupon, ""))
                    .commit();
        }

        // 设置资质图片
        List<String> qualification = shopData.getQualification();
        if (qualification == null || qualification.size() == 0) {
            mLlQualifiLayout.setVisibility(View.GONE);
        } else {
            mLlQualifiLayout.setVisibility(View.VISIBLE);
            List<LocalMedia> selMedias = new ArrayList<>();
            for (String url : qualification) {
                LocalMedia media = new LocalMedia();
                if (!url.startsWith(GL_IMAGE_URL)) {
                    url = GL_IMAGE_URL + url;
                }
                media.setPath(url);
                selMedias.add(media);
            }
            SelectPhotoFragment fragment = SelectPhotoFragment.newInstance(3, 3, selMedias, 10);
            fragment.setMode(SelectPhotoFragment.MODE_CHECK);
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.fl_shopdetails_qualifi, fragment)
                    .commit();
        }
    }

    public SelectBean getSelectBean() {
        if(mLeftIsScroll){
            return mSelectBean;
        }else {
            return mTitleSelectBean;
        }

    }

    public void setSelectBean(SelectBean selectBean) {
        if(mLeftIsScroll){
            mSelectBean = selectBean;
        }else {
            mTitleSelectBean = selectBean;
        }
    }

    public List<ShopInfoBean.DataEntity.GoodsResEntity> getGoodsList() {
        return mGoodsAdapter.getAllData();
    }

    public EasyRecyclerView getGoodsErv() {
        return mErvGoodsContent;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mErvGoodsContent != null){
            mErvGoodsContent.removeAllOnScrollListeners();
        }
    }
}

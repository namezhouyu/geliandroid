package com.geli.m.mvp.home.other.goodsdetails_activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.CartBean;
import com.geli.m.bean.GoodsDetailsBean;
import com.geli.m.bean.OverseasSubmitOrderBean;
import com.geli.m.bean.ShPriceBean;
import com.geli.m.bean.SpecifiBean;
import com.geli.m.config.Constant;
import com.geli.m.coustomview.CustomProgressBar;
import com.geli.m.coustomview.webview.MyWebView;
import com.geli.m.dialog.ShareDialog;
import com.geli.m.dialog.addcart.AddCartBottomDialog;
import com.geli.m.dialog.apprice.ApPriceDialog;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.mvp.home.main.HomeActivity;
import com.geli.m.mvp.home.other.WebViewActivity;
import com.geli.m.mvp.home.other.accountperiod_activity.AccountPeriodActivity;
import com.geli.m.mvp.home.other.accountperiod_opened_activity.AccountPeriodOpenedActivity;
import com.geli.m.mvp.home.other.login_register_activity.LoginActivity;
import com.geli.m.mvp.home.other.shopdetails_activity.ShopDetailsActivity;
import com.geli.m.mvp.home.other.submitorder_activity.main.SubmitOrderActivity;
import com.geli.m.orther.GlideImageLoader;
import com.geli.m.select.SelectPhotoFragment;
import com.geli.m.ui.activity.AllCommentActivity;
import com.geli.m.utils.DateFormatUtil;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.LogUtils;
import com.geli.m.utils.LoginInformtaionSpUtils;
import com.geli.m.utils.PriceUtils;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;
import com.google.gson.Gson;
import com.luck.picture.lib.entity.LocalMedia;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.geli.m.BuildConfig.GL_IMAGE_URL;
import static com.geli.m.config.Constant.AP_STATUS;
import static com.geli.m.config.Constant.AP_Status_AllowablePay;
import static com.geli.m.config.Constant.AP_Status_AlreadyOpened;
import static com.geli.m.config.Constant.AP_Status_Apply;
import static com.geli.m.config.Constant.AP_Status_NotPayable;
import static com.geli.m.config.Constant.Goods_Type_Domestic;
import static com.geli.m.config.Constant.Goods_Type_Futures;
import static com.geli.m.config.Constant.Goods_Type_GroupBuy;
import static com.geli.m.config.Constant.INTENT_GOODS_ID;
import static com.geli.m.config.Constant.INTENT_ITEM;
import static com.geli.m.config.Constant.INTENT_LINKS;
import static com.geli.m.config.Constant.INTENT_OVERSEAS_DATA;
import static com.geli.m.config.Constant.INTENT_SHOP_ID;
import static com.geli.m.config.Constant.INTENT_TYPE;
import static com.geli.m.mvp.home.other.accountperiod_activity.AccountPeriodActivity.AP_ResultCode;
import static com.geli.m.mvp.home.other.accountperiod_opened_activity.AccountPeriodOpenedActivity.APO_ResultCode;
import static com.geli.m.utils.ResourceUtil.getDrawableResIDByName;

/**
 * Created by Steam_l on 2018/1/2.
 * <p>
 * 食品详情界面
 */
public class GoodsDetailsActivity extends MVPActivity<GoodsDetailsPresentImpl>
        implements View.OnClickListener,
        GoodsDetailsView, AddCartBottomDialog.ResListener {


    /**
     * 包裹"除了底部--购物车、合计"的其他内容
     */
    @BindView(R.id.nsv_goodsdetails_content)
    NestedScrollView mNsvContent;

    /*标题*/
    /**
     * 包裹"标题"的布局
     */
    @BindView(R.id.ll_goodsdetails_title_layout)
    LinearLayout mLlTitleLayout;


    /**
     * 中文名
     */
    @BindView(R.id.tv_goodsdetails_china_name)
    TextView mTvChinaName;
    /**
     * 可零售 标志
     */
    @BindView(R.id.tv_retail_GoodDetailsActivity)
    TextView mTvRetail;
    /**
     * 英文名
     */
    @BindView(R.id.tv_goodsdetails_english_name)
    TextView mTvEnglishName;
    /**
     * 是否收藏
     */
    @BindView(R.id.cb_goodsdetails_coll)
    CheckBox mCbColl;
    /**
     * 价格
     */
    @BindView(R.id.tv_goodsdetails_price)
    TextView mTvPrice;
    /**
     * 查看帐期价格
     */
    @BindView(R.id.tv_goodsdetails_check_ap_price)
    TextView mTvCheckApPrice;
    /**
     * 销量 -- 已售xxx
     */
    @BindView(R.id.tv_goodsdetails_sold)
    TextView mTvSold;
    /**
     * 起订 -- xx单位起订
     */
    @BindView(R.id.tv_goodsdetails_setfrom)
    TextView mTvSetfrom;
    /**
     * 商家名称
     */
    @BindView(R.id.tv_goodsdetails_shopname)
    TextView mTvShopName;
    /**
     * 规格 -- 选择规格数量
     */
    @BindView(R.id.tv_goodsdetails_specifi)
    TextView mTvSpecific;
    /**
     * 包裹账期的布局
     */
    @BindView(R.id.layout_goodsdetails_accountperiod)
    RelativeLayout mLayoutAccountPeriod;
    /**
     * 账期 -- 类型
     */
    @BindView(R.id.tv_goodsdetails_accountperiod)
    TextView mTvAccountPeriod;
    /**
     * 账期 -- 状态
     */
    @BindView(R.id.tv_goodsdetails_accountperiodstate)
    TextView mTvAccountPeriodState;
    /**
     * 合计价格
     */
    @BindView(R.id.tv_goodsdetails_bottom_price)
    TextView mTvBottomPrice;


    /*评论*/
    /**
     * 包裹"用户评论"的布局
     */
    @BindView(R.id.ll_goodsdetails_commentlayout)
    LinearLayout mLlCommentLayout;
    /**
     * 用户评论(xxx)
     */
    @BindView(R.id.tv_goodsdetails_commentnumber)
    TextView mTvCommentNumber;
    /**
     * 评论用户 -- 头像
     */
    @BindView(R.id.iv_itemview_comment_avatr)
    ImageView mIvCommentAvatar;
    /**
     * 评论用户 -- 昵称
     */
    @BindView(R.id.tv_itemview_comment_name)
    TextView mTvCommentName;
    /**
     * 评论的时间
     */
    @BindView(R.id.tv_itemview_comment_date)
    TextView mTvCommentDate;
    /**
     * 所评论的 -- 产品的规格
     */
    @BindView(R.id.tv_itemview_comment_specifi)
    TextView mTvCommentSpecific;
    /**
     * 所评论的 -- 内容
     */
    @BindView(R.id.tv_itemview_comment_content)
    TextView mTvCommentContent;
    /**
     * 评论点赞单选框 -- 也显示点赞数据量
     */
    @BindView(R.id.cb_itemview_comment_like)
    CheckBox mCbCommentLike;
    /**
     * 对商品的满意度 -- 最多5颗星
     */
    @BindView(R.id.rb_itemview_comment_score)
    RatingBar mRbScore;
    /**
     * 查看全部评论
     */
    @BindView(R.id.bt_goodsdetails_checkallcomment)
    Button mBtCheckAllcomment;

    /* 横幅广告 */
    /**
     * 食品详情图片 -- 轮播控件
     */
    @BindView(R.id.banner_goodsdetails)
    Banner mBanner;

    /*详情*/
    /**
     * 食品详情 -- WebView加载
     */
    @BindView(R.id.wv_goodsdetails_goodsimg)
    MyWebView mWebView;
    /**
     * 数据出错的布局 -- 商品详情
     */
    @BindView(R.id.layout_error_rootlayout)
    com.geli.m.coustomview.ErrorView mErrorView;

    /*底部一行*/
    /**
     * 底部一行 -- "商店"
     */
    @BindView(R.id.tv_goodsdetails_bottom_shop)
    TextView mTvShop;
    /**
     * 底部一行 -- "购物车"
     */
    @BindView(R.id.bt_goodstails_bottom_addcart)
    Button mBtnConfirm;

    /*团购*/
    /**
     * 包裹"团购"的布局
     */
    @BindView(R.id.ll_include_delegation)
    LinearLayout mLlDelegation;//团购
    /**
     * 已参团人数
     */
    @BindView(R.id.tv_include_delegation_personnumber)
    TextView mTvBuynumber;
    /**
     * 剩余参团人数
     */
    @BindView(R.id.tv_include_delegation_remaining)
    TextView mTvRemaining;
    /**
     * 已参团人数 -- 占得百分比
     */
    @BindView(R.id.tv_include_delegation_progress_number)
    TextView mTvPronumber;
    /**
     * 团购百分比控件 -- 进度框
     */
    @BindView(R.id.cpb_include_delegation_progress)
    CustomProgressBar mCpbProgress;


    private GoodsDetailsBean.DataBean.GoodsCommentBean mCommentData;
    private double mBannerHeight;
    private double mTitleHeight;

    private GoodsDetailsBean.DataBean mCurrGoodsData;

    /**
     * 意图 中获取 -- 食品编号
     */
    private String goods_id;
    //    private GoodsDetailsAddcartDialog mGoodsDetailsAddcartDialog;
    private AddCartBottomDialog mGoodsDetailsAddcartDialog;

    /**
     * 当前选择的规格Bean
     */
    private CartBean.DataEntity.CartListEntity mEditBean;//当前选择的规格Bean
    private String mCurr_json;
    /**
     * 横幅广告 -- 图片地址
     */
    private List<String> bannerImgList;
    private String DIALOG_TAG = "dialog_tag";

    /**
     * 类型 -- 如团购之类的
     */
    private int mCurrGoodsType;
    private String mShareTitle = "";
    private String mShareContent = "";
    private String mShareImageUrl = "";
    //private String mShareUrl = "http://m.gelifood.com/goods/";
    private String mShareUrl = "http://m.gelistore.com/goods/";

    private int mShopId = -1;

    private int mAccountPeriodState = -1;

    @Override
    protected int getResId() {
        return R.layout.activity_goodsdetails;
    }

    @Override
    protected void init() {
        super.init();
        mImmersionBar
                .reset()
                .statusBarDarkFont(true)
                .statusBarView(R.id.view_goodsdetails_top)
                .statusBarAlpha(0.0f)
                .init();

        getIntentByGoodId();             // 从意图中获取 商品id
        initShare();                     // 设置"分享"
    }

    @Override
    protected void initData() {

        initBanner();                    // 设置"圆指示器"
        initWeb();                       // 设置web
        setBottomPriceZero();            // 设置底部价格为0
        requestData();                   // 根据商品id 请求数据
    }

    @Override
    protected void initEvent() {
        setTitleLayoutColor();          // 设置标题布局的颜色
    }


    /**
     * 从intent中获取 商品id
     */
    private void getIntentByGoodId() {
        goods_id = getIntent().getStringExtra(INTENT_GOODS_ID);
    }

    /**
     * 设置"分享"
     */
    private void initShare() {
        if (TextUtils.isEmpty(goods_id)) {                          // 空的--直接就是网址
            mShareUrl = "http://app.gelifood.com/d/mobile/";
        } else {
            mShareUrl += goods_id;                                  // 有食品编号的就添加在网址后
        }
    }

    /**
     * 设置"圆指示器"
     */
    private void initBanner() {
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);      // 设置"圆指示器"
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.setImageLoader(new GlideImageLoader());
        bannerImgList = new ArrayList<>();

        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                List<LocalMedia> selectList = new ArrayList();
                for (String url : bannerImgList) {
                    LocalMedia media = new LocalMedia();
                    media.setPath(url);
                    selectList.add(media);
                }

                // 查看图片
                SelectPhotoFragment.picturePreview(mContext, position, selectList);
            }
        });
    }

    /**
     * 设置web
     */
    private void initWeb() {
//        WebSettings webSettings = mWebView.getSettings();
//        webSettings.setUseWideViewPort(true);                       // 设置此属性，可任意比例缩放
//        webSettings.setLoadWithOverviewMode(true);
//        webSettings.setTextZoom(350);
//        // webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
//        if (GlobalData.hasNetWork) {
//            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);     //根据cache-control决定是否从网络上取数据。
//        } else {
//            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
//        }
//
//
////        webSettings.setAllowFileAccess(true);
////        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
////        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);//排版适应屏幕
//
//
//        mWebView.addJavascriptInterface(new MJavascriptInterface(this), "imagelistener");
//        mWebView.setWebViewClient(new MyWebViewClient(mErrorView));
        mWebView.setErrorView(mErrorView);
        mErrorView.setVisibility(View.GONE);
    }

    /**
     * 设置底部价格为0
     */
    private void setBottomPriceZero() {
        mTvBottomPrice.setText(Utils.getString(R.string.overseas_list_money,
                Utils.getFormatDoubleTwoDecimalPlaces(Double.valueOf(0 + ""), 2)));
    }

    /**
     * 根据商品id 请求数据
     */
    private void requestData() {
        mPresenter.getGoodsDetails(GlobalData.getUser_id(), goods_id);
    }


    /**
     * 设置标题布局的颜色
     * 根据"Banner的高度"设置"头部的透明度"
     */
    private void setTitleLayoutColor() {
        // 根据"Banner的高度"设置"头部的透明度"
        mNsvContent.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                double number = 1.0 - Double.valueOf((mBannerHeight - mTitleHeight - Double.valueOf(scrollY)) / (mBannerHeight - mTitleHeight));
                if (number >= 1) {
                    number = 1;
                }
                int alpha = (int) Math.round(number * 255);
                String hex = Integer.toHexString(alpha).toUpperCase();
                if (hex.length() == 1) {
                    hex = "0" + hex;
                }
                hex = "#" + hex + "ffffff";
                mLlTitleLayout.setBackgroundColor(Color.parseColor(hex));

            }
        });


        mBanner.post(new Runnable() {             // 一直获取 Banner 的高度
            @Override
            public void run() {
                mBannerHeight = mBanner.getMeasuredHeight();

            }
        });


        mLlTitleLayout.post(new Runnable() {     // 一直获取 标题 的高度
            @Override
            public void run() {
                mTitleHeight = mLlTitleLayout.getMeasuredHeight();

            }
        });

    }


    @OnClick({R.id.tv_invlude_delegation_rule, R.id.cb_itemview_comment_like,
            R.id.bt_errorview_refresh, R.id.bt_goodsdetails_contact,
            R.id.iv_goodsdetails_index, R.id.cb_goodsdetails_coll,
            R.id.tv_goodsdetails_bottom_shop, R.id.tv_goodsdetails_bottom_cart,
            R.id.tv_goodsdetails_specifi, R.id.bt_goodstails_bottom_addcart,
            R.id.iv_goodsdetails_back, R.id.iv_goodsdetails_share,
            R.id.bt_goodsdetails_checkallcomment, R.id.layout_goodsdetails_accountperiod,
            R.id.tv_goodsdetails_check_ap_price})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_invlude_delegation_rule:               /* 显示拼单规则 */
                startActivity(WebViewActivity.class, new Intent().putExtra(INTENT_LINKS, mCurrGoodsData.getRule_url()));
                break;

            case R.id.cb_itemview_comment_like:                 /* 点赞单选框 -- 也显示点赞数据量 */
                toLike();
                break;

            case R.id.bt_errorview_refresh:                     /* 数据出错的"点击刷新按钮" */
                requestData();
                break;

            case R.id.bt_goodstails_bottom_addcart:             /* 底部一行 -- "加入购物车" */
                clickAddCart();
                break;

            case R.id.iv_goodsdetails_back:                     /* 后退图标 */
                finish();
                break;

            case R.id.bt_goodsdetails_contact:                  /* 联系客服 */
                if (!TextUtils.isEmpty(mCurrGoodsData.getShop_tel())) {
                    Utils.callPhone(mContext, mCurrGoodsData.getShop_tel());
                }
                break;

            case R.id.cb_goodsdetails_coll:                     /* 收藏 */
                collectionGoods();
                break;

            case R.id.tv_goodsdetails_bottom_shop:               /* 底部一行 -- "商店" */
                if (mCurrGoodsData.getShop_id() != 0) {
                    startActivity(ShopDetailsActivity.class, new Intent().putExtra(INTENT_SHOP_ID, mCurrGoodsData.getShop_id() + ""));
                    finish();
                }
                break;

            case R.id.tv_goodsdetails_bottom_cart:              /* 底部一行 -- "购物车"  -- 回到HomeActivity 的购物车Fragment*/
                startActivity(HomeActivity.class, new Intent().putExtra(INTENT_ITEM, 2));
                break;

            case R.id.iv_goodsdetails_index:
                startActivity(HomeActivity.class, new Intent());    /* 标题中的"小屋" -- 回到HomeActivity  */
                break;

            case R.id.iv_goodsdetails_share:                        /* 标题中的"分享" */
                ShareDialog.newInstance(mShareTitle, mShareContent, mShareImageUrl, mShareUrl)
                        .show(getSupportFragmentManager(), "");
                break;

            case R.id.tv_goodsdetails_specifi:                      /* 规格 -- 选择规格数量 */
                showSelectSpecifiDialog();
                break;

            case R.id.layout_goodsdetails_accountperiod:            /* 账期 */
                clickAccountPeriod();
                break;

            case R.id.bt_goodsdetails_checkallcomment:              /* 查看全部评论 */
                checkAllComment();
                break;

            case R.id.tv_goodsdetails_check_ap_price:              /* 查看账期价格 */
                mPresenter.shPrice(GlobalData.getUser_id(), goods_id, mShopId + "");
                break;

            default:
                break;
        }
    }


    /**
     * 点击 -- 账期
     */
    private void clickAccountPeriod() {
        switch (mAccountPeriodState) {
            case AP_Status_AllowablePay:          /* 用户可以账期支付 -- 提升额度 */
                startActivityForResult(AccountPeriodOpenedActivity.class, new Intent()
                        .putExtra(AP_STATUS, AP_Status_AllowablePay)
                        .putExtra(INTENT_SHOP_ID, mShopId), 2);
                break;

            case AP_Status_Apply:                 /* 户已经申请账期支付  -- 正在申请还没通过 */
                break;

            case AP_Status_AlreadyOpened:         /* 店铺已经开通账期(可申请) */
                startActivityForResult(AccountPeriodActivity.class, new Intent()
                        .putExtra(AP_STATUS, AP_Status_AlreadyOpened)
                        .putExtra(INTENT_SHOP_ID, mShopId), 1);
                break;

            case AP_Status_NotPayable:            /* 不可以账期支付(店铺未开通或者商品未开通) */
                break;
        }
    }


    /**
     * 点击 -- 底部一行 -- "购物车"
     */
    private void clickAddCart() {
        // 登录
        if (!LoginInformtaionSpUtils.getLoginInfString(mContext, LoginInformtaionSpUtils.login_login).equals("1")) {
            ToastUtils.showToast(Utils.getString(R.string.please_login));
            startActivity(LoginActivity.class, new Intent());
            return;
        }


        if (PriceUtils.judgmentPrice(mCurrGoodsData.getShop_price())) {          // 判断下价格，做对应处理
//            if (mEditBean == null || mEditBean.getSku_id() <= 0) {          /* 没有选择规格 */
//                ToastUtils.showToast(Utils.getString(R.string.message_plase_select_specifi));
//                showSelectSpecifiDialog();
//            } else {                                                        /* 选择了规格 */
//                if (mCurrGoodsType == Goods_Type_Futures ||         // 海外订单
//                        mCurrGoodsType == Goods_Type_GroupBuy) {
//                    submitOrder();                  /* 直接提交订单 */
//
//                } else {
//                    addCart();
//                }
//            }

            if (mEditBean == null || mEditBean.getSpecification() == null ||
                    mEditBean.getSpecification().size() == 0 ||
                    (mEditBean.getSpecification().size() > 1 && mEditBean.getSku_id() <= 0)) {          /* 没有选择规格 */
                ToastUtils.showToast(Utils.getString(R.string.message_plase_select_specifi));
                showSelectSpecifiDialog();
            } else {                                                        /* 选择了规格 */
                if (mCurrGoodsType == Goods_Type_Futures ||         // 海外订单
                        mCurrGoodsType == Goods_Type_GroupBuy) {
                    submitOrder();                  /* 直接提交订单 */

                } else {
                    addCart();
                }
            }
        }
    }

    /**
     * 评论点赞单选框 -- 也显示点赞数据量  -- 设置"点赞"还是"取消点赞"
     */
    private void toLike() {
        mPresenter.toLike(GlobalData.getUser_id(), mCommentData.getCom_id() + "");
    }

    /**
     * 海外订单
     */
    private void submitOrder() {
        Intent intent = new Intent();
        intent.putExtra(INTENT_TYPE, SubmitOrderActivity.TYPE_OVERSEAS);

        OverseasSubmitOrderBean bean = new OverseasSubmitOrderBean();
        bean.setGoods_id(goods_id);
        bean.setSku_id(mEditBean.getSku_id() + "");
        bean.setGoods_number(mEditBean.getCart_number() + "");

        if (mCurrGoodsType == Goods_Type_GroupBuy) {                // 团购
            bean.setGroupon_id(mCurrGoodsData.getGroupon_id());     // 团购ID
        }
        intent.putExtra(INTENT_OVERSEAS_DATA, bean);
        startActivity(SubmitOrderActivity.class, intent);
    }

    /**
     * 加入购物车
     */
    private void addCart() {
        Map data = new HashMap();
        data.put("user_id", GlobalData.getUser_id());
        data.put("goods_id", mEditBean.getGoods_id());
        data.put("cart_number", mEditBean.getCart_number());
        if (mCurrGoodsData.getSku_count() == 1) {

        } else {
            data.put("sku_id", mEditBean.getSku_id());
        }

        data.put("cart_type", 1 + "");
        data.put("specification", mCurr_json);
        mPresenter.addCart(data);
    }

    /**
     * 改变"收藏" 是否收藏
     */
    private void collectionGoods() {
        mCbColl.setChecked(!mCbColl.isChecked());

        if (mCurrGoodsData.getGoods_id() != 0) {
            Map<String, String> data = new HashMap();
            data.put("user_id", GlobalData.getUser_id());
            data.put("goods_id", mCurrGoodsData.getGoods_id() + "");
            data.put("col_type", 1 + "");
            mPresenter.collectionGood(data);
        }
    }

    /**
     * 显示"规格的窗口" -- 从网络中加载
     */
    private void showSelectSpecifiDialog() {
        if (PriceUtils.judgmentPrice(mCurrGoodsData.getShop_price())) {
            mPresenter.getGoodsSpecifi(mCurrGoodsData.getGoods_id() + "");
        }

    }

    /**
     * 查看全部评论
     */
    private void checkAllComment() {
        if (mCommentData == null || mCommentData.getComment_number() <= 0) {
            ShowSingleToast.showToast(mContext, Utils.getString(R.string.message_nocomment));
        } else {
            Intent intent = new Intent();
            intent.putExtra(AllCommentActivity.INTENT_TYPE, AllCommentActivity.TYPE_GOODS);
            intent.putExtra(AllCommentActivity.INTENT_ID, goods_id + "");
            startActivity(AllCommentActivity.class, intent);
        }
    }


    /**
     * 查看账期价格
     *
     * @param data
     */
    private void showApPriceDialog(ShPriceBean.DataBean data) {
        ApPriceDialog apPriceDialog = ApPriceDialog.newInstance(data);
        apPriceDialog.show(getSupportFragmentManager(), "ApPriceDialog");
    }

    @Override
    protected GoodsDetailsPresentImpl createPresenter() {
        return new GoodsDetailsPresentImpl(this);
    }

    @Override
    public void onSuccess(String msg) {
        ShowSingleToast.showToast(mContext, msg);
        setToLike();
    }

    /**
     * 处理"点赞/取消"的结果
     */
    private void setToLike() {
        if (mPresenter.isToLike) {
            mPresenter.isToLike = false;
            mCommentData.setIs_like(mCommentData.getIs_like() == 0 ? 1 : 0);
            int comment_number = mCommentData.getCom_like();
            if (mCommentData.getIs_like() == 0) {               // 取消
                mCommentData.setCom_like(--comment_number);
            } else {                                            // 点赞
                mCommentData.setCom_like(++comment_number);
            }
            setCommentData(mCommentData);
        }
    }

    @Override
    public void onError(String msg) {
        ShowSingleToast.showToast(mContext, msg);
    }

    @Override
    public void showLoading() {
        Utils.uProgressDialog(mContext);
    }

    @Override
    public void hideLoading() {
        Utils.dismissProgress();
    }

    /**
     * 显示食品 -- 从网络中获取数据
     *
     * @param data
     */
    @Override
    public void showGoodsData(GoodsDetailsBean.DataBean data) {
        mCurrGoodsData = data;
        mShopId = data.getShop_id();

        LogUtils.printJson(new Gson().toJson(data), "GoodsData");

        setShare();
        setBanner(data);
        setOtherView(data);
        setViewByGoodsType();                              // 设置
        setCommentData(data.getGoods_comment());           // 设置评论数据
        setWebViewData(data.getGoods_desc());              // 设置"WebView"数据

        setAccountPeriod(data.getSh_status());             // 设置账期

        setOlnySpecification(data);
    }

    private void setOlnySpecification(GoodsDetailsBean.DataBean data) {
        if (data.getSku_count() == 1) {
            mTvSpecific.setText(Utils.getString(R.string.choose, data.getSpecificationArray().get(0).getValue()));
            mTvBottomPrice.setText(Utils.getString(R.string.overseas_list_money,
                    Utils.getFormatDoubleTwoDecimalPlaces(
                            Double.valueOf(mCurrGoodsData.getShop_price()), 2)));
            mCurr_json = mCurrGoodsData.getSpecifications();


            if (mEditBean == null) {
                mEditBean = new CartBean.DataEntity.CartListEntity();
                mEditBean.setGoods_id(mCurrGoodsData.getGoods_id());
                mEditBean.setCart_number(mCurrGoodsData.getOrigin_number());
                mEditBean.setOrigin_number(mCurrGoodsData.getOrigin_number());
                mEditBean.setGoods_unit(mCurrGoodsData.getGoods_unit());

                // mCurr_bean.setSku_id(selectSku.getSku_id());
                mEditBean.setSpecification(data.getSpecificationArray());
                // 设置数量
                mEditBean.setCart_number(1);
            }
        }
    }


    private void setShare() {
        mShareTitle = mCurrGoodsData.getGoods_name() + " " + PriceUtils.getPrice(mCurrGoodsData.getShop_price());
        mShareContent = mCurrGoodsData.getGoods_intro();
        if (!mCurrGoodsData.getGoods_thumb().startsWith(GL_IMAGE_URL)) {   // 判断图片略缩图的路径是否是"xxx"开始的
            mShareImageUrl = GL_IMAGE_URL + mCurrGoodsData.getGoods_thumb();
        }
    }

    private void setBanner(GoodsDetailsBean.DataBean data) {
        List<GoodsDetailsBean.DataBean.GoodsImgBean> goods_img = data.getGoods_img(); // 原图、略缩图列表
        bannerImgList.clear();
        for (GoodsDetailsBean.DataBean.GoodsImgBean imgBean : goods_img) {
            if (!imgBean.getImg_url().startsWith(GL_IMAGE_URL)) {         // 判断图片路径"前面"
                bannerImgList.add(GL_IMAGE_URL + imgBean.getImg_url());
            }
        }
        mBanner.setImages(bannerImgList);
        mBanner.start();
    }

    private void setOtherView(GoodsDetailsBean.DataBean data) {
        mTvChinaName.setText(data.getGoods_name());
        if (data.getGs_id() == Constant.Gs_Id_resale) {
            mTvRetail.setVisibility(View.VISIBLE);
        } else {
            mTvRetail.setVisibility(View.GONE);
        }

        mCbColl.setChecked(data.getGoods_coll() != 0);
        mTvShopName.setText(data.getShop_name());
        if (data.getSku_count() > 1) {
            mTvPrice.setText(PriceUtils.getStartPrice(data.getShop_price()));
        } else {
            mTvPrice.setText(PriceUtils.getPrice(data.getShop_price()));
        }

        mTvSold.setText(Utils.getString(R.string.sold_pieces, data.getQuantity_sold(), data.getGoods_unit()));
        mTvSetfrom.setText(Utils.getString(R.string.goodsdetails_setfrom_pieces, data.getOrigin_number(), data.getGoods_unit()));
        mTvSpecific.setText(Utils.getString(R.string.please_select_specifi_number));
    }

    private void setViewByGoodsType() {
        mCurrGoodsType = mCurrGoodsData.getGoods_type();

        if (mCurrGoodsType != Goods_Type_Domestic) {       // 海外的
            mTvShop.setVisibility(View.GONE);

            if (mCurrGoodsType == Goods_Type_GroupBuy) {            /* 团购 */
                mBtnConfirm.setText(Utils.getString(R.string.registration_delegation));
                mLlDelegation.setVisibility(View.VISIBLE);
                mTvBuynumber.setText(Utils.getString(R.string.delegation_personnumber, mCurrGoodsData.getParticipants() + ""));
                mTvRemaining.setText(Utils.getString(R.string.remaining_ton, mCurrGoodsData.getSurplus(), mCurrGoodsData.getGoods_unit()));
                double number = Double.valueOf(mCurrGoodsData.getSold_number()) / Double.valueOf(mCurrGoodsData.getTarget_number());
                mTvPronumber.setText(Utils.getFormatDoubleTwoDecimalPlaces(number * 100, 1) + "%");
                mCpbProgress.setCurProgress((int) (number * 100));

            } else if (mCurrGoodsType == Goods_Type_Futures) {      /* 期货 */
                mBtnConfirm.setText(Utils.getString(R.string.submit_order));
            }
        }
    }


    /**
     * 设置"WebView"数据
     *
     * @param webViewData 要加载的路径
     */
    public void setWebViewData(String webViewData) {
        mWebView.loadDataWithBaseURL("", webViewData, "text/html", "utf-8", null);
    }

    /**
     * 设置账期布局
     *
     * @param sh_status
     */
    private void setAccountPeriod(GoodsDetailsBean.DataBean.ShStatusBean sh_status) {
        if (sh_status != null) {
            mAccountPeriodState = sh_status.getStatus();

            switch (sh_status.getStatus()) {
                case AP_Status_AllowablePay:          /* 用户可以账期支付 */
                    setApStatus(true,
                            Utils.getString(R.string.you_have_opened_xxx_days_account_payment, sh_status.getSh_day()),
                            R.color.zhusediao,
                            true,
                            R.string.check_my_account);
                    break;

                case AP_Status_Apply:                 /* 户已经申请账期支付  -- 正在申请还没通过 */
                    setApStatus(true,
                            Utils.getString(R.string.you_have_applied_for_xxx_day_payment_and_account_application_is_under_examination, sh_status.getSh_day()),
                            R.color.specifitext_color,
                            false,
                            R.string.empty);
                    break;

                case AP_Status_AlreadyOpened:         /* 店铺已经开通账期(可申请) */
                    setApStatus(true,
                            Utils.getString(R.string.we_support_payment_of_accounts),
                            R.color.zhusediao,
                            true,
                            R.string.go_to_open);

                    break;

                case AP_Status_NotPayable:            /* 不可以账期支付(店铺未开通或者商品未开通) */
                    setApViewVisibility(false);
                    break;
            }

        } else {
            setApViewVisibility(false);
        }
    }

    /**
     * 设置账期的账本
     *
     * @param isShow         是否显示 提示消息
     * @param message        提示消息
     * @param colorId        提示消息 -- 文字颜色
     * @param isShowDrawable 提示消息文本的右边是否显示右边的箭头
     * @param stateTextId    状态文字id
     */
    private void setApStatus(boolean isShow, String message, int colorId,
                             boolean isShowDrawable, int stateTextId) {

        setApViewVisibility(isShow);

        if (isShow) {
            mLayoutAccountPeriod.setVisibility(View.VISIBLE);
            mTvCheckApPrice.setVisibility(View.VISIBLE);

            mTvAccountPeriod.setText(message);
            mTvAccountPeriod.setTextColor(Utils.getColor(colorId));
            Drawable drawable = null;
            if (isShowDrawable) {
                int resID = getDrawableResIDByName(mContext, "first_btn_jinru");
                drawable = getResources().getDrawable(resID);
            }
            mTvAccountPeriod.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            mTvAccountPeriodState.setText(mContext.getString(stateTextId));
        }
    }

    private void setApViewVisibility(boolean isShow) {
        if (isShow) {
            mLayoutAccountPeriod.setVisibility(View.VISIBLE);
            mTvCheckApPrice.setVisibility(View.VISIBLE);
        } else {
            mLayoutAccountPeriod.setVisibility(View.GONE);
            mTvCheckApPrice.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mWebView.onResume();
        mWebView.resumeTimers();
    }

    @Override
    public void onPause() {
        super.onPause();
        mBanner.stopAutoPlay();
        mWebView.onPause();
        mWebView.pauseTimers(); //暂停整个 WebView 所有布局、解析、JS。
    }


    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

    /**
     * 收藏状态 -- 后台的数据修改成功后才设置"app"
     *
     * @param message
     */
    @Override
    public void showCollectionResult(String message) {
        ShowSingleToast.showToast(mContext, message);
        mCbColl.setChecked(!mCbColl.isChecked());
    }

    /**
     * 设置"评论"数据
     *
     * @param commentData
     */
    public void setCommentData(GoodsDetailsBean.DataBean.GoodsCommentBean commentData) {
        mCommentData = commentData;

        if (commentData == null) {                          // 没有评论就不显示
            mLlCommentLayout.setVisibility(View.GONE);
            mBtCheckAllcomment.setVisibility(View.GONE);

        } else {
            mLlCommentLayout.setVisibility(View.VISIBLE);
            mBtCheckAllcomment.setVisibility(View.VISIBLE);

            mTvCommentNumber.setText(Utils.getString(R.string.person_comment, commentData.getComment_number()));
            mTvCommentContent.setText(commentData.getCom_content());
            mTvCommentDate.setText(DateFormatUtil.transForDate(Integer.valueOf(commentData.getAdd_time())));

            setCommentUserNameAndAvatar(commentData);       // 设置 评论用户名 和 头像
            setCommentSpecific(commentData);                // 设置被评论的 -- 产品规格
            setCommentLike(commentData);                    // 设置点赞
            setCommentPhoto(commentData);                   // 设置评论照片
        }

    }

    /**
     * 设置 评论用户名 和 头像
     *
     * @param commentData
     */
    private void setCommentUserNameAndAvatar(GoodsDetailsBean.DataBean.GoodsCommentBean commentData) {
        /* 评论用户名 */
        if (TextUtils.isEmpty(commentData.getNickname().trim())) {
            mTvCommentName.setText(Utils.getString(R.string.anonymoususer));
        } else {
            mTvCommentName.setText(commentData.getNickname().trim());
        }

        /* 评论用户头像 */
        mRbScore.setRating(commentData.getCom_grade());
        if (TextUtils.isEmpty(commentData.getAvatar())) {       // 头像
            mIvCommentAvatar.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img_touxiang50));
        } else {
            GlideUtils.loadAvatar(mContext, commentData.getAvatar(), mIvCommentAvatar);
        }
    }

    /**
     * 设置被评论的 -- 产品规格
     *
     * @param commentData
     */
    private void setCommentSpecific(GoodsDetailsBean.DataBean.GoodsCommentBean commentData) {
        if (commentData.getGoods_attr() != null) {              // 产品规格
            String specifi = "";
            for (CartBean.DataEntity.CartListEntity.SpecificationEntity entity : commentData.getGoods_attr()) {
                specifi += entity.getValue() + ";";
            }
            if (!TextUtils.isEmpty(specifi)) {
                specifi.substring(0, specifi.length() - 1);
            }
            mTvCommentSpecific.setText(specifi);
        }
    }

    /**
     * 设置点赞
     *
     * @param commentData
     */
    private void setCommentLike(GoodsDetailsBean.DataBean.GoodsCommentBean commentData) {
        mCbCommentLike.setText("(" + commentData.getCom_like() + ")"); // 点赞数
        mCbCommentLike.setChecked(commentData.getIs_like() == 1);      // 是否点赞了
    }

    /**
     * 设置评论照片
     *
     * @param commentData
     */
    private void setCommentPhoto(GoodsDetailsBean.DataBean.GoodsCommentBean commentData) {
        if (commentData.getCom_photo() != null && commentData.getCom_photo().size() != 0) {
            List<LocalMedia> urlList = new ArrayList<>();
            for (String url : commentData.getCom_photo()) {
                LocalMedia media = new LocalMedia();
                if (!url.startsWith(GL_IMAGE_URL)) {
                    url = GL_IMAGE_URL + url;
                }
                media.setPath(url);
                urlList.add(media);
            }

            // 评论上的 "fragment" 显示图片的 只是显示图片 !!!
            SelectPhotoFragment fragment = SelectPhotoFragment.newInstance(3, 3, urlList, 10);
            fragment.setMode(SelectPhotoFragment.MODE_CHECK);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_itemview_comment_content, fragment).commit();
        }
    }


    /**
     * 从网络上获取到"规格"数据后，在这里显示"规格选择窗口"
     *
     * @param bean
     */
    @Override
    public void showGoodSpecifi(SpecifiBean bean) {
        if (mEditBean == null) {
            mEditBean = new CartBean.DataEntity.CartListEntity();
            mEditBean.setGoods_thumb(mCurrGoodsData.getGoods_thumb());
            // mEditBean.setGoods_name("1");
            mEditBean.setGoods_id(mCurrGoodsData.getGoods_id());
            mEditBean.setCart_number(mCurrGoodsData.getOrigin_number());
            mEditBean.setOrigin_number(mCurrGoodsData.getOrigin_number());
            mEditBean.setGoods_unit(mCurrGoodsData.getGoods_unit());
        }

        if (mGoodsDetailsAddcartDialog == null ||
                mGoodsDetailsAddcartDialog.getDialog() == null ||
                !mGoodsDetailsAddcartDialog.getDialog().isShowing()) {

//            mGoodsDetailsAddcartDialog = GoodsDetailsAddcartDialog.newInstance(bean.getData(),
//                    mEditBean, this, true);
//            mGoodsDetailsAddcartDialog.show(getSupportFragmentManager(), DIALOG_TAG);

            mGoodsDetailsAddcartDialog = AddCartBottomDialog.newInstance(bean.getData(),
                    mEditBean, this, true);
            mGoodsDetailsAddcartDialog.show(getSupportFragmentManager(), DIALOG_TAG);
        }
    }

    @Override
    public void showShPrice(ShPriceBean data) {
        if (data != null && data.getData() != null) {
            showApPriceDialog(data.getData());
        }

    }

    // 如果选择了规格 -- 就回到这里处理下数据
    @Override
    public void returnRes(String json, CartBean.DataEntity.CartListEntity curr_bean) {
        if (!TextUtils.isEmpty(mGoodsDetailsAddcartDialog.getValue())) {
            mTvSpecific.setText(Utils.getString(R.string.choose, mGoodsDetailsAddcartDialog.getValue()));
            mTvBottomPrice.setText(mGoodsDetailsAddcartDialog.getPrice());
            mCurr_json = json;
            mEditBean = curr_bean;
            clickAddCart();

        } else {
            mTvSpecific.setText(Utils.getString(R.string.please_select_specifi_number));
            setBottomPriceZero();                               // 设置底部价格为0
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AP_ResultCode || resultCode == APO_ResultCode) {
            requestData();
        }
    }

}

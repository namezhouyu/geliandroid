package com.geli.m.coustomview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.CartBean;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.home.cart_fragment.main.CartPresentImpl;
import com.geli.m.mvp.home.main.HomeActivity;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import java.util.ArrayList;
import java.util.List;

import static com.geli.m.config.Constant.INTENT_ITEM;
import static com.geli.m.mvp.home.other.shopdetails_activity.shopdetails_fragment.ShopDetailsFragment.parseCartData;

/**
 * Created by Steam_l on 2017/12/30.
 *
 * 小购物车 -- 就是商店中的"购物车"那一大块 -- 底下的那一行 + 点击后展开的布局
 */
public class SmallCartLayout extends RelativeLayout implements View.OnClickListener{

    /** 展开的"详情布局" */
    private RelativeLayout mLl_root;
    /** "详情布局"根布局下的 -- "包裹全部控件"的一个线性布局 */
    private LinearLayout mLl_goods_root;
    /** 包裹"简单信息" 和 "清空购物车" 的布局*/
    private LinearLayout mLl_goods_toplayout;
    /** 加入购物车后 -- 显示下"简单信息" -- 如:(已选11件,共5.5kg) */
    private TextView mTv_selectinfo;
    /** "清空购物车"文本 */
    private TextView mTv_clearcart;
    private EasyRecyclerView mErv_list;


    /** 包裹底下一行按钮的布局 -- 就是未展开的所有布局 */
    private RelativeLayout mRl_smallcart_bottom;
    /** 包裹"购物车图片"的布局 */
    private FrameLayout mFl_img;
    /** 购物车图片上方的"商品数量" */
    private TextView mTv_number;
    /** "结算"按钮 */
    private Button mBt_settlement;
    /** 合计中的"金额" */
    private TextView mTv_bottom_price;


    Context mContext;
    /** 是否是"海外" */
    private boolean mIsOverseas;

    /** Erv控件的高度 */
    private int mErvHeight;
    /** 包裹"简单信息" 和 "清空购物车" 的布局 -- 的高度 */
    private int mGoodsTopHeight;
    /** 包裹底下一行按钮的布局 -- 就是未展开的所有布局 -- 的高度 */
    private int mBottomLayout;
    /** 动画持续的时间 */
    private int animaTime = 500;
    /** "购物车详情"，是否展开 */
    public boolean smallCartIsOpen = false;

    /** erv的适配器 */
    RecyclerArrayAdapter mSmallCartAdapter;
    CartPresentImpl mPresenter;
    private Double mTotalPrice;
    private boolean isClearCart;
    private List<Object> mCartList;

    /** 购物车"展开的动画" true:正在执行动画 */
    boolean isOpenIng = false;

    /** 打开"小购物车" -- 接口  */
    SmallCartOpenListener mSmallCartOpenListener;


    public SmallCartLayout(Context context) {
        this(context, null);
    }

    public SmallCartLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmallCartLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_itemview_smallcart_clearcart:          /* "清空购物车"文本 */
                clearCart();
                break;

            case R.id.bt_smallcart_bootom_settlement:           /* 结算 */
                settlementListener();
                break;

            case R.id.fl_smallcart_imglayout:                   /* 包裹"购物车"的布局 */
                imgLayoutListener();
                break;
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        initView();
        initListener();
        initData();
    }



    /**
     * 初始化控件
     */
    private void initView(){
        mTv_clearcart = (TextView) findViewById(R.id.tv_itemview_smallcart_clearcart);
        mTv_bottom_price = (TextView) findViewById(R.id.tv_smallcart_bottom_price);
        mBt_settlement = (Button) findViewById(R.id.bt_smallcart_bootom_settlement);

        mLl_root = (RelativeLayout) findViewById(R.id.ll_smallcart_details_rootlayout);
        mFl_img = (FrameLayout) findViewById(R.id.fl_smallcart_imglayout);
        mTv_number = (TextView) findViewById(R.id.tv_itemview_smallcart_cartnumber);
        mTv_selectinfo = (TextView) findViewById(R.id.tv_smallcart_goods_selectinfo);

        mRl_smallcart_bottom = (RelativeLayout) findViewById(R.id.rl_smallcart_bottom);
        mLl_goods_root = (LinearLayout) findViewById(R.id.ll_smallcart_goods_rootlayout);
        mLl_goods_toplayout = (LinearLayout) findViewById(R.id.ll_smallcart_goods_toplayout);
        mErv_list = (EasyRecyclerView) mLl_root.findViewById(R.id.erv_smallcart_list);

    }

    /**
     * 初始化监听
     */
    private void initListener(){
        mTv_clearcart.setOnClickListener(this);
        mBt_settlement.setOnClickListener(this);
        mFl_img.setOnClickListener(this);

        setErvListener();                     // 将Erv设置为"手机"的一半高度 ?

        mLl_root.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isOpenIng || !smallCartIsOpen) {        // 如果"正在执行动画" 或是 "未打开" -- 不处理
                    return false;                           // 不消费事件
                }
                closeCart();                                // 收起"购物车详情"
                return true;
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mTv_bottom_price.setText(Utils.getString(mContext, R.string.overseas_list_money,
                Utils.getFormatDoubleTwoDecimalPlaces(0d, 2)));
        mBt_settlement.setText(Utils.getString(mContext, R.string.settlement, "0"));
        mErv_list.setLayoutManager(new LinearLayoutManager(mContext));
        mBottomLayout = (int) mContext.getResources().getDimension(R.dimen.bottom_height);
        mLl_root.setBackgroundColor(Color.TRANSPARENT);         // 透明
    }


    /**
     * 将Erv设置为"手机"的一半高度 -- mErvHeight 的高
     *
     * addOnGlobalLayoutListener -- 注册一个回调，当全局布局状态或视图树中的视图的可见性发生变化时调用
     */
    private void setErvListener() {

        mErv_list.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                /* 这里移除 -- 是因为防止"重复调用" */
                //显示这个ViewTreeObserver是否还活着。当观察者没有生命的时候， 任何对方法的调用（除了这个方法）都会抛出异常。
                //如果一个应用程序保留了对这个ViewTreeObserver的长期引用，那么它应该在调用任何其他方法之前，一定要检查这个方法的结果。
                if (mErv_list.getViewTreeObserver().isAlive()) {
                    mErv_list.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }

                // 获取"窗口"的一半高度
                WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
                DisplayMetrics outMetrics = new DisplayMetrics();
                manager.getDefaultDisplay().getMetrics(outMetrics);
                int halfWindowHdight = outMetrics.heightPixels / 2;

                ViewGroup.LayoutParams layoutParams = mErv_list.getLayoutParams();
                layoutParams.height = halfWindowHdight;
                mErv_list.setLayoutParams(layoutParams);
                mErvHeight = layoutParams.height;


                //                        if (mErv_list.getViewTreeObserver().isAlive()) {
                //                            mErv_list.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                //                        }
                //                        int height = mErv_list.getMeasuredHeight();
                //                        ViewGroup.LayoutParams layoutParams = mErv_list.getLayoutParams();
                //                        if (height > Utils.dip2px(mContext, 296)) {
                //                            layoutParams.height = Utils.dip2px(mContext, 296);
                //                            height = layoutParams.height;
                //                        } else {
                //                            layoutParams.height = RecyclerView.LayoutParams.WRAP_CONTENT;
                //                        }
                //                        mErv_list.setLayoutParams(layoutParams);
                //                        mErvHeight = height;
            }
        });

    }


    /**
     * 结算
     */
    private void settlementListener(){
        if (mSmallCartAdapter != null) {
            List allData = mSmallCartAdapter.getAllData();
            String cart_id = "";
            int moq = 0;
            int goodsNumber = 0;
            String unit = "";
            for (Object child : allData) {
                if (child instanceof CartBean.DataEntity.CartListEntity) {
                    cart_id += ((CartBean.DataEntity.CartListEntity) child).getCart_id() + ",";
                    goodsNumber += ((CartBean.DataEntity.CartListEntity) child).getCart_number();
                } else if (child instanceof CartBean.DataEntity) {
                    moq = ((CartBean.DataEntity) child).getMoq();
                    unit = ((CartBean.DataEntity) child).unit;
                }
            }
            if (TextUtils.isEmpty(cart_id)) {
                ShowSingleToast.showToast(mContext, Utils.getString(mContext, R.string.please_add_goods));
                return;
            }
            if (moq - goodsNumber > 0) {
                ShowSingleToast.showToast(mContext, Utils.getString(mContext, R.string.moq, (moq - goodsNumber) + "", unit));
                return;
            }

//            cart_id = cart_id.substring(0, cart_id.length() - 1);
//            Intent intent = new Intent();
//            intent.putExtra(INTENT_CART_ID, cart_id);
//            ((BaseActivity) mContext).startActivity(SubmitOrderActivity.class, intent);

            // 直接去购物车
            ((BaseActivity) mContext).startActivity(HomeActivity.class, new Intent().putExtra(INTENT_ITEM, 2));
        }
    }

    /**
     * 包裹"购物车"的布局
     */
    private void imgLayoutListener(){
        if (isOpenIng) {
            return;
        }

        if (Integer.valueOf(mTv_number.getText().toString().trim()) == 0) {
            return;
        }

        if (smallCartIsOpen) {
            closeCart();
        } else {
            openCart();
        }
    }

    /**
     * 清空"购物车"
     *
     * 找到，小购物车中所有的"商品id",使用","分隔组合成"字符串"
     */
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


    /**
     * 设置数据 -- 设置控件显示数据
     *
     * @param data
     */
    public void setData(List<CartBean.DataEntity> data) {

        mSmallCartAdapter.clear();
        mCartList.clear();
        mTotalPrice = 0.0;
        int cartNumber = 0;

        // 解析数据"购物车数据"   返回--List--[0]数量 [1]总金额
        if (data != null && data.size() != 0) {
            List list = parseCartData(data, mCartList, mTotalPrice, 1);
            cartNumber = (int) list.get(0);
            mTotalPrice = (Double) list.get(1);
        }

        getTvCartNumber().setText(cartNumber + "");
        // getTvSelectInfo().setText(Utils.getString(mContext, R.string.smallcart_selectinfo, cartNumber + "", "5.5"));
        getTvSelectInfo().setText(Utils.getString(mContext, R.string.smallcart_selectinfo1, cartNumber + ""));
        mTv_bottom_price.setText(Utils.getString(mContext, R.string.overseas_list_money, Utils.getFormatDoubleTwoDecimalPlaces(mTotalPrice, 2)));
        mBt_settlement.setText(Utils.getString(mContext, R.string.settlement, cartNumber + ""));
        if (cartNumber != 0) {
            mSmallCartAdapter.addAll(mCartList);
        }
    }


    /**
     * 数据改变后
     * @param mCurrEditBean
     * @param mCurrEditNumber
     */
    public void notifyData(CartBean.DataEntity.CartListEntity mCurrEditBean, int mCurrEditNumber) {

        if (mPresenter.isEdit) {                            /* 添加编辑购物车 */
            mPresenter.isEdit = !mPresenter.isEdit;
            int index = 0;
            CartBean.DataEntity dataEntity = null;
            for (Object child : mSmallCartAdapter.getAllData()) {
                if (child instanceof CartBean.DataEntity.CartListEntity) {
                    if (((CartBean.DataEntity.CartListEntity) child).getGoods_id() == mCurrEditBean.getGoods_id()
                            && ((CartBean.DataEntity.CartListEntity) child).getSku_id() == mCurrEditBean.getSku_id()) {
                        mTotalPrice += Utils.mul(Double.valueOf(mCurrEditBean.getCart_price()), (double) (mCurrEditNumber - mCurrEditBean.getCart_number()));
                        mTv_bottom_price.setText(Utils.getString(mContext, R.string.overseas_list_money, Utils.getFormatDoubleTwoDecimalPlaces(mTotalPrice, 2)));
                        mCurrEditBean.setCart_number(mCurrEditNumber);
                        mSmallCartAdapter.update(mCurrEditBean, index);
                        break;
                    }
                } else if (child instanceof CartBean.DataEntity) {
                    dataEntity = (CartBean.DataEntity) child;
                }
                index++;
            }

            if (dataEntity != null) {
                mSmallCartAdapter.update(dataEntity, 0);
            }
            return;
        }

        if (isClearCart) {                                  /* 清空购物车 */
            isClearCart = !isClearCart;
            mSmallCartAdapter.clear();
            mCartList.clear();
            closeCart();
            mTv_bottom_price.setText(Utils.getString(mContext, R.string.overseas_list_money, "0.00"));
            mBt_settlement.setText(Utils.getString(mContext, R.string.settlement, "0"));
            getTvCartNumber().setText("0");
            return;
        }


        if (mPresenter.isDelete) {                          /* 删除 */
            mPresenter.isDelete = !mPresenter.isDelete;
            int index = 0;
            CartBean.DataEntity dataEntity = null;
            for (Object child : mSmallCartAdapter.getAllData()) {
                if (child instanceof CartBean.DataEntity.CartListEntity) {
                    if (((CartBean.DataEntity.CartListEntity) child).getGoods_id() == mCurrEditBean.getGoods_id()
                            && ((CartBean.DataEntity.CartListEntity) child).getSku_id() == mCurrEditBean.getSku_id()) {
                        mTotalPrice -= Utils.mul(Double.valueOf(mCurrEditBean.getCart_price()), Double.valueOf(mCurrEditBean.getCart_number()));
                        mTv_bottom_price.setText(Utils.getString(mContext, R.string.overseas_list_money, Utils.getFormatDoubleTwoDecimalPlaces(mTotalPrice, 2)));
                        mSmallCartAdapter.remove(index);
                        break;
                    }
                } else if (child instanceof CartBean.DataEntity) {
                    dataEntity = (CartBean.DataEntity) child;
                }
                index++;
            }
            if (dataEntity != null) {
                mSmallCartAdapter.update(dataEntity, 0);
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
            int number = Integer.valueOf(getTvCartNumber().getText().toString().trim()) - 1;
            getTvCartNumber().setText(number + "");
            mBt_settlement.setText(Utils.getString(mContext, R.string.settlement, getTvCartNumber().getText()));
            if (number <= 0) {
                closeCart();
            }
        }
    }

    /**
     * 获取 Erv(购物车详情)
     *
     * @param adapter           erv(购物车详情)的适配器
     * @param presenter
     * @return
     */
    public EasyRecyclerView getErvList(RecyclerArrayAdapter adapter, CartPresentImpl presenter) {
        mSmallCartAdapter = adapter;
        mPresenter = presenter;
        mCartList = new ArrayList<>();
        return mErv_list;
    }

    /**
     * 获取"购物车"商品种类数量
     * @return
     */
    public TextView getTvCartNumber() {
        return mTv_number;
    }

    /**
     * 获取简单信息 -- "加入购物车后 -- 显示下"简单信息" -- 如:(已选11件,共5.5kg)"
     * @return
     */
    public TextView getTvSelectInfo() {
        return mTv_selectinfo;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mLl_goods_root.layout(0, getHeight() - mBottomLayout, getWidth(), getHeight() + mGoodsTopHeight + mErvHeight - mBottomLayout);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mGoodsTopHeight = mLl_goods_toplayout.getMeasuredHeight();
    }


    /**
     * 展开"购物车详情" -- 执行动画
     */
    public void openCart() {
        smallCartIsOpen = !smallCartIsOpen;
        mLl_root.setBackgroundColor(Color.parseColor("#80000000"));
        mRl_smallcart_bottom.setVisibility(VISIBLE);
        if (mSmallCartOpenListener != null) {
            mSmallCartOpenListener.smallCartOpen();
        }
        ValueAnimator openAnima = ValueAnimator.ofInt(getHeight() - mBottomLayout,
                getHeight() - mGoodsTopHeight - mErvHeight - mBottomLayout);
        openAnima.setDuration(animaTime);
        openAnima.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int y = (int) animation.getAnimatedValue();
                mLl_goods_root.setY(y);
            }
        });
        openAnima.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isOpenIng = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isOpenIng = false;
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


    /**
     * 收起"购物车详情" -- 执行动画
     */
    public void closeCart() {
        if (!smallCartIsOpen) {
            return;
        }
        smallCartIsOpen = !smallCartIsOpen;
        ValueAnimator openAnima = ValueAnimator.ofInt(getHeight() - mGoodsTopHeight - mErvHeight - mBottomLayout,
                getHeight() - mBottomLayout);
        openAnima.setDuration(animaTime);
        openAnima.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int y = (int) animation.getAnimatedValue();
                mLl_goods_root.setY(y);
            }
        });
        openAnima.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isOpenIng = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isOpenIng = false;
                mLl_root.setBackgroundColor(Color.TRANSPARENT);
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

    /**
     * 设置"是否是海外"
     * @param isOverseas
     */
    public void setIsOverseas(boolean isOverseas) {
        mIsOverseas = isOverseas;
    }

    /**
     * 打开"小购物车" -- 接口
     */
    public interface SmallCartOpenListener {
        void smallCartOpen();
    }

    public void setSmallCartOpenListener(SmallCartOpenListener listener) {
        mSmallCartOpenListener = listener;
    }


    /**
     * 小购物车 -- 数量编辑 -- 监听事件接口
     */
    public interface SmallCartEditListener {
        /**
         * 购物车，商品的编辑 -- 增加 或 减少 商品
         * @param data      在"购物车"中要处理的商品
         * @param number    这件商品的数量(已经计算好了)
         * @param isAdd     true:增加商品  false:减少商品
         */
        void editCart(CartBean.DataEntity.CartListEntity data, int number, boolean isAdd);


        /**
         * 购物车，删除"这件商品"
         * @param data
         */
        void deleteGoods(CartBean.DataEntity.CartListEntity data);
    }
}

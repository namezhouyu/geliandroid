package com.geli.m.mvp.home.mine_fragment.main;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.BuildConfig;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.PersonInfoBean;
import com.geli.m.config.Constant;
import com.geli.m.coustomview.ShapeImageView;
import com.geli.m.mvp.base.MVPFragment;
import com.geli.m.mvp.home.index_fragment.message_activity.MessActivity;
import com.geli.m.mvp.home.mine_fragment.accountmanagement_activity.AccountManagementActivity;
import com.geli.m.mvp.home.mine_fragment.address_activity.AddressActivity;
import com.geli.m.mvp.home.mine_fragment.browse_activity.BrowseActivity;
import com.geli.m.mvp.home.mine_fragment.collection_activity.CollectionActivity;
import com.geli.m.mvp.home.mine_fragment.coupon_activity.CouponManagerActivity;
import com.geli.m.mvp.home.mine_fragment.feedback_activity.FeedbackActivity;
import com.geli.m.mvp.home.mine_fragment.helpcenter_activity.HelpCenterActivity;
import com.geli.m.mvp.home.mine_fragment.invoice_activity.InvoiceActivity;
import com.geli.m.mvp.home.mine_fragment.invoicemerge_activity.InvoiceMergeActivity;
import com.geli.m.mvp.home.mine_fragment.member_activity.MemberCenterActivity;
import com.geli.m.mvp.home.mine_fragment.myorder_activity.MyOrderActivity;
import com.geli.m.mvp.home.mine_fragment.mywallet_activity.main.MyWalletActivity;
import com.geli.m.mvp.home.mine_fragment.personinfo_activity.PersonInfoModifyActivity;
import com.geli.m.mvp.home.mine_fragment.setting_activity.SettingActivity;
import com.geli.m.mvp.home.other.login_register_activity.LoginActivity;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.LoginInformtaionSpUtils;
import com.geli.m.utils.StringUtils;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;
import com.gyf.barlibrary.ImmersionBar;


/**
 * Created by Steam_l on 2017/12/22.
 * <p>
 * 我的 Fragment
 */
public class MineFragment extends MVPFragment<PersonInfoPresentImpl> implements View.OnClickListener, MineView {

    /**
     * Toolbar
     */
    @BindView(R.id.toolbar_mine)
    Toolbar mToolbar;

    /**
     * 头像图片
     */
    @BindView(R.id.iv_mine_avatar)
    ShapeImageView mIvAvatar;
    /**
     * 用户"昵称"
     */
    @BindView(R.id.tv_mine_nickname)
    TextView mTvNickname;
    /**
     * 用户"等级"
     */
    @BindView(R.id.tv_mine_grade)
    TextView mTvGrade;


    /**
     * 动画图片 -- 点击后会弹出(物流、通知、公告)
     */
    @BindView(R.id.iv_mine_anima)
    ImageView mIvAnima;
    /**
     * 动画图片弹出的 -- 公告
     */
    @BindView(R.id.tv_mine_anima_bulletin)
    TextView mTvBulletin;
    /**
     * 动画图片弹出的 -- 通知
     */
    @BindView(R.id.tv_mine_anima_notifi)
    TextView mTvNotifi;
    /**
     * 动画图片弹出的 -- 物流
     */
    @BindView(R.id.tv_mine_anima_logistics)
    TextView mTvLogistics;


    /**
     * 动画 -- 默认是：关闭的
     */
    boolean isOpenAnima = false;
    private float mStartY;
    private float mStartX;
    private double mY;
    private double mX;
    private ObjectAnimator mLogisticsOpenAnima;
    private ObjectAnimator mBulletinOpenAnima;
    private PropertyValuesHolder mNotifiOpenAnimaY;
    private PropertyValuesHolder mNotifiOpenAnimaX;

    /**
     * "通知控件"的"动画"
     */
    private ObjectAnimator mNorifiOpenAnima;
    /**
     * "公告控件"的"动画"
     */
    private ObjectAnimator mBulletinCloseAnima;
    /**
     * "物流控件"的"动画"
     */
    private ObjectAnimator mLogisticsCloseAnima;

    private PropertyValuesHolder mNotifiCloseAnimaX;
    private PropertyValuesHolder mNotifiCloseAnimaY;
    private ObjectAnimator mNorifiCloseAnima;

    /**
     * 图片打开
     */
    private ObjectAnimator mIvOpenAnima;
    /**
     * 图片关闭
     */
    private ObjectAnimator mIvCloseAnima;

    private int radius = 60;        //dp
    /**
     * 动画执行的时间
     */
    private int animaTime = 300;
    /**
     * 动画执行的时间
     */
    private int delay = 100;

    /**
     * 用户信息
     */
    private PersonInfoBean.DataEntity mDataEntity;


    @Override
    public int getResId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void init() {
        super.init();
        ImmersionBar.setTitleBar(getActivity(), mToolbar);
    }

    @Override
    protected void initData() {
        initUserInfoSet();              // 头像、昵称这几个控件的动画转场
        getNestData();                  // 如果登录了就，网络加载具体的用户信息
        initReceiver();                 // 广播过滤
    }


    @Override
    protected void initEvent() {
        mIvAnima.post(new Runnable() {  // 执行任务(线程)
            @Override
            public void run() {
                OpenOrClose();
            }
        });
    }

    /**
     * 头像、昵称这几个控件的动画转场
     */
    private void initUserInfoSet() {
        // 主要的语句---将当前Activity的View和自己定义的Key绑定起来
//        ViewCompat.setTransitionName(mIvAvatar, PersonInfoModifyActivity.AVATAR_TRANSITIONNAME);
//        ViewCompat.setTransitionName(mTvNickname, PersonInfoModifyActivity.NAME_TRANSITIONNAME);
        mIvAvatar.setImageResource(R.drawable.img_touxiang120);
    }

    /**
     * 广播过滤
     */
    private void initReceiver() {
        IntentFilter filter = new IntentFilter(Constant.ACTION_MODIFY);      // 过滤自定义广播 -- "修改"
        filter.addAction(Constant.ACTION_REQUEST);                           // 过滤自定义广播 -- "请求"
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);  // 过滤广播 -- "网络状态变化"
        mContext.registerReceiver(modifyPersonalInfo, filter);
    }

    /**
     * 如果登录了就，网络加载具体的用户信息<br>
     * 未登录就显示下信息
     */
    private void getNestData() {
        if (LoginInformtaionSpUtils.getLoginInfString(mContext, LoginInformtaionSpUtils.login_login).equals("1") &&
                StringUtils.isNotEmpty(GlobalData.getUser_id())) { // 已登录
            mPresenter.getPersonInfo(GlobalData.getUser_id());  // 网络加载用户信息
        }

        mTvNickname.setText(Utils.getString(R.string.mine_login_regist));
        mTvGrade.setVisibility(View.GONE);
    }


    @OnClick({R.id.tv_mine_collection, R.id.tv_mine_anima_notifi, R.id.tv_mine_anima_bulletin,
            R.id.tv_mine_browse, R.id.tv_mine_pay_state, R.id.tv_mine_delivered_state,
            R.id.tv_mine_received_state, R.id.tv_mine_evaluated_state, R.id.tv_mine_aftermarket_state,
            R.id.tv_mine_anima_logistics, R.id.tv_mine_helpcenter, R.id.tv_mine_feedback,
            R.id.tv_mine_nickname, R.id.tv_mine_membercenter, R.id.tv_mine_coupon,
            R.id.iv_mine_setting, R.id.iv_mine_avatar, R.id.tv_mine_addressmanager,
            R.id.iv_mine_anima, R.id.tv_mine_check_allorder, R.id.tv_mine_mywlaaet,
            R.id.tv_mine_invoicemanager, R.id.tv_mine_am, R.id.tv_mine_invoicemerge})
    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.tv_mine_collection:                           /* 我的收藏 */
                startActivity(CollectionActivity.class, new Intent());
                break;

            case R.id.tv_mine_pay_state:                            /* 待付款 */
                intent = new Intent().putExtra(Constant.INTENT_POSITION, Constant.POSITION_PAY);
                startActivity(MyOrderActivity.class, intent);
                break;

            case R.id.tv_mine_delivered_state:                     /* 待发货 */
                intent = new Intent().putExtra(Constant.INTENT_POSITION, Constant.POSITION_SHIP);
                startActivity(MyOrderActivity.class, intent);
                break;

            case R.id.tv_mine_received_state:                      /* 待收货 */
                intent = new Intent().putExtra(Constant.INTENT_POSITION, Constant.POSITION_RECEIVING);
                startActivity(MyOrderActivity.class, intent);
                break;

            case R.id.tv_mine_evaluated_state:                     /* 待评价 */
                intent = new Intent().putExtra(Constant.INTENT_POSITION, Constant.POSITION_EVALUATION);
                startActivity(MyOrderActivity.class, intent);
                break;

            case R.id.tv_mine_aftermarket_state:                   /* 售后 */
                intent = new Intent().putExtra(Constant.INTENT_POSITION, Constant.POSITION_AFTERMARKET);
                startActivity(MyOrderActivity.class, intent);
                break;

            case R.id.tv_mine_check_allorder:                      /* 查看全部订单 */
                startActivity(MyOrderActivity.class, new Intent());
                break;

            case R.id.tv_mine_anima_bulletin:                      /* 系统公告 */
                startActivity(MessActivity.class, new Intent().putExtra(Constant.Message_Title, Constant.MessageType.system_notify));
                break;

            case R.id.tv_mine_anima_notifi:                        /* 通知中心 */
                startActivity(MessActivity.class, new Intent().putExtra(Constant.Message_Title, Constant.MessageType.notify_center));
                break;

            case R.id.tv_mine_anima_logistics:                     /* 交易物流 */
                startActivity(MessActivity.class, new Intent().putExtra(Constant.Message_Title, Constant.MessageType.logistics_transactions));
                break;

            case R.id.tv_mine_helpcenter:                          /* 帮助中心 */
                startActivity(HelpCenterActivity.class, new Intent());
                break;

            case R.id.tv_mine_feedback:                            /* 意见反馈 */
                startActivity(FeedbackActivity.class, new Intent());
                break;

            case R.id.tv_mine_browse:                              /* 浏览记录 */
                startActivity(BrowseActivity.class, new Intent());
                break;

            case R.id.iv_mine_anima:
                OpenOrClose();
                break;

            case R.id.iv_mine_setting:                             /* 设置按钮 */
                startActivity(SettingActivity.class, new Intent());
                break;

            case R.id.iv_mine_avatar:                              /* 头像 */
            case R.id.tv_mine_nickname:                            /* 昵称 */
                goPersonInfoModifyActivity();
                break;

            case R.id.tv_mine_mywlaaet:                           /* 我的钱包 */
                startActivity(MyWalletActivity.class, new Intent());
                break;

            case R.id.tv_mine_membercenter:                       /* 会员中心 */
                startActivity(MemberCenterActivity.class, new Intent());
                break;

            case R.id.tv_mine_invoicemerge:                       /* 发票申请 */
                startActivity(InvoiceMergeActivity.class, new Intent());
                break;

            case R.id.tv_mine_invoicemanager:                     /* 我的发票 */
                startActivity(InvoiceActivity.class, new Intent());
                break;

            case R.id.tv_mine_addressmanager:                     /* 地址管理 */
                intent = new Intent().putExtra(Constant.INTENT_MODE, AddressActivity.ADDRESSMODE_MANAGER);
                startActivity(AddressActivity.class, intent);
                break;

            case R.id.tv_mine_coupon:                             /* 我的优惠券 */
                intent = new Intent().putExtra(Constant.INTENT_MODE, CouponManagerActivity.COUPONMODE_MANAGER);
                startActivity(CouponManagerActivity.class, intent);
                break;

            case R.id.tv_mine_am:                                 /* 账期管理 */
                startActivity(AccountManagementActivity.class, new Intent());
                break;
            default:
                break;
        }
    }

    /**
     * 先检查网络，再检查是否登录，跳到"个人中心"
     */
    private void goPersonInfoModifyActivity() {
        // 检查网络
        Intent intent;
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
            ToastUtils.showToast(Utils.getString(R.string.message_unknownhost));
            return;
        }

        // 如果登录了 -- 就调到"用户信息"界面
        if (LoginInformtaionSpUtils.getLoginInfString(mContext, LoginInformtaionSpUtils.login_login).equals("1")) {
            intent = new Intent(mContext, PersonInfoModifyActivity.class);
            intent.putExtra(Constant.INTENT_BEAN, mDataEntity);
        } else {
            intent = new Intent(mContext, LoginActivity.class);
        }

//        // 这个是要用到"共享控件"的效果来(切换Activity)
//        ActivityOptionsCompat activityOptionsCompat =
//                ActivityOptionsCompat.makeSceneTransitionAnimation((BaseActivity) mContext,
//                        Pair.create((View) mIvAvatar, ViewCompat.getTransitionName(mIvAvatar)),
//                        Pair.create((View) mTvNickname, ViewCompat.getTransitionName(mTvNickname)));
//        ActivityCompat.startActivity(mContext, intent, activityOptionsCompat.toBundle());
        startActivity(intent);
    }

    /**
     * 开/关 动画
     */
    private void OpenOrClose() {
        if (isOpenAnima) {            //关闭
            closeAnima();
        } else {                      //打开
            openAnima();
        }

        isOpenAnima = !isOpenAnima;
    }


    /**
     * 执行"关闭"动画
     */
    private void closeAnima() {

        if (mIvCloseAnima == null) {
            mIvCloseAnima = ObjectAnimator.ofFloat(mIvAnima, "rotation", -90, 0);   // 旋转
            mIvCloseAnima.setDuration(animaTime + delay * 2);

            mIvCloseAnima.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    mIvAnima.setEnabled(false);         // 执行的时候"不可交互"(防止多次点击)
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    mIvAnima.setEnabled(true);
                    mTvLogistics.setVisibility(View.GONE);      // "关闭动画结束"的时候"隐藏这三个控件"
                    mTvNotifi.setVisibility(View.GONE);
                    mTvBulletin.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }

        if (mBulletinCloseAnima == null) {
            mBulletinCloseAnima = ObjectAnimator.ofFloat(mTvBulletin, "y", mStartY - Utils.dip2px(mContext, radius), mStartY);
            mBulletinCloseAnima.setDuration(animaTime + delay * 2);
        }

        if (mLogisticsCloseAnima == null) {
            mLogisticsCloseAnima = ObjectAnimator.ofFloat(mTvLogistics, "x", mStartX - Utils.dip2px(mContext, radius), mStartX);
            mLogisticsCloseAnima.setDuration(animaTime);
        }

        if (mNorifiCloseAnima == null) {
            mNotifiCloseAnimaX = PropertyValuesHolder.ofFloat("x", (float) (mStartX - mX), mStartX);
            mNotifiCloseAnimaY = PropertyValuesHolder.ofFloat("y", (float) (mStartY - mY), mStartY);
            mNorifiCloseAnima = ObjectAnimator.ofPropertyValuesHolder(mTvNotifi, mNotifiCloseAnimaX, mNotifiCloseAnimaY);
            mNorifiCloseAnima.setDuration(animaTime + delay);
        }

        mIvCloseAnima.start();
        mNorifiCloseAnima.start();
        mLogisticsCloseAnima.start();
        mBulletinCloseAnima.start();
    }


    /**
     * 执行"打开"动画
     */
    private void openAnima() {
        if (mStartX == 0) {
            mX = Math.abs(Math.sin(45 * Math.PI / 180)) * Utils.dip2px(mContext, radius);
            mY = Math.abs(Math.cos(45 * Math.PI / 180)) * Utils.dip2px(mContext, radius);
            mStartX = mIvAnima.getLeft();
            mStartY = mIvAnima.getTop();
        }

        if (mIvOpenAnima == null) {
            mIvOpenAnima = ObjectAnimator.ofFloat(mIvAnima, "rotation", 0, -90);
            mIvOpenAnima.setDuration(animaTime + delay * 2);
            mIvOpenAnima.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    mIvAnima.setEnabled(false);
                    mTvLogistics.setVisibility(View.VISIBLE);
                    mTvNotifi.setVisibility(View.VISIBLE);
                    mTvBulletin.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    mIvAnima.setEnabled(true);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        if (mBulletinOpenAnima == null) {
            mBulletinOpenAnima = ObjectAnimator.ofFloat(mTvBulletin, "y", mStartY, mStartY - Utils.dip2px(mContext, radius));
            mBulletinOpenAnima.setDuration(animaTime);
        }
        if (mLogisticsOpenAnima == null) {
            mLogisticsOpenAnima = ObjectAnimator.ofFloat(mTvLogistics, "x", mStartX, mStartX - Utils.dip2px(mContext, radius));
            mLogisticsOpenAnima.setDuration(animaTime + delay * 2);
        }
        if (mNorifiOpenAnima == null) {
            mNotifiOpenAnimaX = PropertyValuesHolder.ofFloat("x", mStartX, (float) (mStartX - mX));
            mNotifiOpenAnimaY = PropertyValuesHolder.ofFloat("y", mStartY, (float) (mStartY - mY));
            mNorifiOpenAnima = ObjectAnimator.ofPropertyValuesHolder(mTvNotifi, mNotifiOpenAnimaX, mNotifiOpenAnimaY);
            mNorifiOpenAnima.setDuration(animaTime + delay);
        }


        mIvOpenAnima.start();
        mBulletinOpenAnima.start();
        mLogisticsOpenAnima.start();
        mNorifiOpenAnima.start();
    }

    @Override
    protected PersonInfoPresentImpl createPresent() {
        return new PersonInfoPresentImpl(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext.unregisterReceiver(modifyPersonalInfo);
    }

    @Override
    public void onError(String msg) {
        super.onError(msg);
        LoginInformtaionSpUtils.setLoginInfString(mContext, LoginInformtaionSpUtils.login_login, "0");
    }

    @Override
    public void showPersonInfo(PersonInfoBean.DataEntity infoBean) {
        mDataEntity = infoBean;
        if (!infoBean.getAvatar().startsWith(BuildConfig.GL_IMAGE_URL)) {
            infoBean.setAvatar(BuildConfig.GL_IMAGE_URL + infoBean.getAvatar());
        }
        GlideUtils.loadAvatar(mContext, infoBean.getAvatar(), mIvAvatar);
        mTvNickname.setText(infoBean.getNickname());
        mTvGrade.setText(infoBean.getRank_name());
        mTvGrade.setVisibility(View.VISIBLE);
    }

    @Override
    public void getPersonInfoError() {
        LoginInformtaionSpUtils.setLoginInfString(mContext, LoginInformtaionSpUtils.login_login, "0");
    }


    public BroadcastReceiver modifyPersonalInfo = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(Constant.ACTION_MODIFY)) {                         /* 修改 */
                modifyUser(intent);

            } else if (intent.getAction().equals(Constant.ACTION_REQUEST)) {                 /* 请求用户信息 -- 比如：用户登录成功 */
                mPresenter.getPersonInfo(GlobalData.getUser_id());

            } else if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {    /* 网络改变状态了 */
                connectivity(context);


            }
        }


        /**
         * "设置界面"退出 / "个人中心"修改了信息
         * @param intent
         */
        private void modifyUser(Intent intent) {
            mDataEntity = intent.getParcelableExtra(Constant.BROADCAST_DATA);
            if (mDataEntity == null) {                                  /* "设置界面"退出 */
                mIvAvatar.setImageResource(R.drawable.img_touxiang120);
                mTvNickname.setText(Utils.getString(R.string.mine_login_regist));
                mTvGrade.setVisibility(View.GONE);

            } else {                                                    /* "个人中心"修改了信息 */
                GlideUtils.loadAvatar(mContext, mDataEntity.getAvatar(), mIvAvatar, true);
                mTvNickname.setText(mDataEntity.getNickname());
            }
        }

        /**
         *  每当网络连接状态发生了变化，
         *  ConnectivityManager会广播一个CONNECTIVITY_ACTION("android.net.conn.CONNECTIVITY_CHANGE")。
         *
         * 网络改变状态了
         * @param context
         */
        private void connectivity(Context context) {
            // 获得网络连接服务
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // 获取GPRS状态
            NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            // 获取wifi连接状态
            NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            // GPRS 或 wifi 某一个连接了
            if (mobNetInfo.isConnected() || wifiNetInfo.isConnected()) {
                getNestData();
            }
        }

    };
}

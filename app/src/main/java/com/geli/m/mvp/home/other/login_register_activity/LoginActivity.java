package com.geli.m.mvp.home.other.login_register_activity;

import android.animation.ValueAnimator;
import android.os.Parcel;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.home.other.login_register_activity.login_fragment.LoginFragment;
import com.geli.m.mvp.home.other.login_register_activity.regist_fragment.RegistFragment;
import com.geli.m.utils.Utils;
import java.util.ArrayList;
import java.util.List;

import static com.geli.m.mvp.home.mine_fragment.personinfo_activity.PersonInfoModifyActivity.AVATAR_TRANSITIONNAME;

/**
 * Created by Administrator on 2017/12/14.
 *
 * 登录界面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {


    /** 图片 */
    @BindView(R.id.iv_login_img)
    ImageView mIvImg;

    /** 包裹 --  登录、注册 单选框 -- 布局 */
    @BindView(R.id.ll_lrr_bottomtitle_layout)
    LinearLayout mLlBottomTitleLayout;
    /** 登录 -- 单选框 */
    @BindView(R.id.cb_lrr_login)
    CheckBox mCbLogin;
    /** 注册 -- 单选框 */
    @BindView(R.id.cb_lrr_regist)
    CheckBox mCbRegist;



    Fragment[] fragments = new Fragment[2];
    List<CheckBox> mCheckBoxList = new ArrayList<>();

    private int resetStartY = 50;
    private int resetEndY = 0;

    private AlphaAnimation mHindeAnim;
    private AlphaAnimation mShowAnim;

    private int animaTime = 500;

    private ValueAnimator mhindeAnimator;
    private ValueAnimator mShowAnimator;

    /** 重置密码 */
    private ResetPassFragment mResetPassFragment;

    @Override
    protected int getResId() {
        return R.layout.activity_login_regist_reset;
    }

    @Override
    protected void init() {
        super.init();
        mImmersionBar
                .statusBarColor(R.color.backgroundColor)
                .init();
    }

    @Override
    protected void initData() {
        mCheckBoxList.add(mCbLogin);
        mCheckBoxList.add(mCbRegist);

        initFragment();
        setImgAnim();                   // 设置图片控件的"显示/隐藏"的动画
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_lrr_content, fragments[0]).commit();
    }


    @Override
    protected void initEvent() {
    }

    private void initFragment(){
        fragments[0] = LoginFragment.newInstance(new LoginFragment.LoginInterface() {
            @Override
            public void resetPass() {               /* 忘记密码 */
                showResetPass();
            }

            @Override
            public void goHome() {                  /* 去主界面 */
                gotoHome();
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {

            }
        });

        fragments[1] = RegistFragment.newInstance(new RegistFragment.RegisterInterface() {
            @Override
            public void registerSuccessful() {              /* 注册成功后去登录 */
                replaceFragment(R.id.cb_lrr_login);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {

            }
        });

        mResetPassFragment = ResetPassFragment.newInstance(new ResetPassFragment.ResetPassInterface() {
            @Override
            public void closeReset() {
                closeResetPass();
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {

            }
        });
    }

    /**
     * 设置图片控件的"显示/隐藏"的动画
     */
    private void setImgAnim() {
        ViewCompat.setTransitionName(mIvImg, AVATAR_TRANSITIONNAME);   // 和其他界面联动

        mShowAnim = new AlphaAnimation(0, 1);
        mShowAnim.setDuration(animaTime);
        mShowAnim.setFillAfter(true);

        mHindeAnim = new AlphaAnimation(1, 0);
        mHindeAnim.setDuration(animaTime);
        mHindeAnim.setFillAfter(true);

        mShowAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (mCbLogin != null) {
                    mCbLogin.setEnabled(true);
                }
                if (mCbRegist != null) {
                    mCbRegist.setEnabled(true);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mHindeAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (mCbLogin != null) {
                    mCbLogin.setEnabled(false);
                }
                if (mCbRegist != null) {
                    mCbRegist.setEnabled(false);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }



    @OnClick({R.id.cb_lrr_login, R.id.cb_lrr_regist, R.id.iv_lrr_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cb_lrr_login:
            case R.id.cb_lrr_regist:
                replaceFragment(v.getId());
                break;

            case R.id.iv_lrr_back:
                gotoHome();
                break;

            default:
                break;
        }
    }

    /**
     * 登录/注册
     * @param id
     */
    public void replaceFragment(int id) {
        for (CheckBox cb : mCheckBoxList) {
            if (cb.getId() == id) {
                cb.setChecked(true);
            } else {
                cb.setChecked(false);
            }
        }

        if (id == R.id.cb_lrr_login) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_lrr_content, fragments[0]).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_lrr_content, fragments[1]).commit();
        }
    }


    public void gotoHome() {
        ActivityCompat.finishAfterTransition(this);
    }


    /**
     * 忘记密码
     */
    public void showResetPass() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.login_reset_fragment_show, R.anim.login_reset_fragment_hinde);
        fragmentTransaction.replace(R.id.fl_lrr_content, mResetPassFragment).commit();

        if (mShowAnimator == null) {
            mShowAnimator = new ValueAnimator();
            mShowAnimator.setIntValues(Utils.dip2px(mContext, resetStartY), Utils.dip2px(mContext, resetEndY));
            mShowAnimator.setDuration(animaTime);
            mShowAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int y = (int) animation.getAnimatedValue();
                    params.setMargins(0, y, 0, 0);
                    mLlBottomTitleLayout.setLayoutParams(params);
                }
            });
        }
        mLlBottomTitleLayout.startAnimation(mHindeAnim);
        mShowAnimator.start();
    }

    /**
     * 关闭重置密码界面
     */
    public void closeResetPass() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.login_reset_fragment_show, R.anim.login_reset_fragment_hinde);
        fragmentTransaction.replace(R.id.fl_lrr_content, fragments[0]).commit();
        if (mhindeAnimator == null) {
            mhindeAnimator = new ValueAnimator();
            mhindeAnimator.setIntValues(Utils.dip2px(mContext, resetEndY), Utils.dip2px(mContext, resetStartY));
            mhindeAnimator.setDuration(animaTime);
            mhindeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int y = (int) animation.getAnimatedValue();
                    params.setMargins(0, y, 0, 0);
                    mLlBottomTitleLayout.setLayoutParams(params);
                }
            });
        }
        mLlBottomTitleLayout.startAnimation(mShowAnim);
        mhindeAnimator.start();
    }
}

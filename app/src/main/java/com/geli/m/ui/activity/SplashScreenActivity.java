package com.geli.m.ui.activity;

import android.content.Intent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import com.geli.m.R;
import com.geli.m.mvp.home.main.HomeActivity;
import com.geli.m.mvp.base.BaseActivity;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;

/**
 * Created by mrCTang on 2017/6/6.
 */

public class SplashScreenActivity extends BaseActivity {
    private AnimationSet animationSet;
    @BindView(R.id.rl_splash_rootlayout)
    RelativeLayout rl_rootlayout;


    @Override
    protected int getResId() {
        // getWindow().setBackgroundDrawable(null);    // 设置背景为空

        return R.layout.activity_splash;
    }

    @Override
    protected void init() {
        super.init();


        if (!isTaskRoot()) {
            // Android launched another instance of the root activity into an existing task
            //  so just quietly finish and go away, dropping the user back into the activity
            //  at the top of the stack (ie: the last state of this task)
            finish();
            return;
        }

        ImmersionBar.with(this)
                .fitsSystemWindows(false)
                .statusBarDarkFont(true, 0) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .init();
    }

    @Override
    protected void initData() {
        animationSet = new AnimationSet(true);
        animationSet.addAnimation(new AlphaAnimation(1.0f, 0.5f));
        //设置插值器
        animationSet.setInterpolator(new LinearInterpolator());
        //设置动画持续时长
        animationSet.setDuration(1000);
        //设置动画结束之后是否保持动画的目标状态
        animationSet.setFillAfter(true);
        //设置动画结束之后是否保持动画开始时的状态
        //        animationSet.setFillBefore(false);
        //设置重复模式
        //        animationSet.setRepeatMode(AnimationSet.REVERSE);
        //设置重复次数
        //        animationSet.setRepeatCount(AnimationSet.INFINITE);
        //设置动画延时时间
        animationSet.setStartOffset(1000);
        //开始动画
        rl_rootlayout.startAnimation(animationSet);
    }

    @Override
    protected void initEvent() {
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ((BaseActivity) mContext).startActivity(HomeActivity.class, new Intent());
                //                Intent intent = new Intent(mContext, LoginActivity.class);
                //                startActivity(intent);
                //取消动画
                //                animationSet.cancel();
                //释放资源
                //                animationSet.reset();
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }


}

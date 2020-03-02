package com.geli.m.coustomview;

import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.geli.m.R;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.home.find_fragment.findlist_fragment.details.VideoDetailsActivity;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

/**
 * Created by Steam_l on 2018/2/27.
 */
public class MyVideoView extends StandardGSYVideoPlayer {

    private ImageView iv_shear;
    private ProgressBar mDialogBrightnessProgressBar;
    private ImageView mIv_img;
    private CheckBox mCb_coll;
    private ImageView mIv_centerstart;

    /**
     * 1.5.0开始加入，如果需要不同布局区分功能，需要重载
     */
    public MyVideoView(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public MyVideoView(Context context) {
        super(context);
    }

    public MyVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    Context mContext;

    @Override
    protected void init(Context context) {
        super.init(context);
        mContext = context;
        iv_shear = (ImageView) findViewById(R.id.shear);
        mCb_coll = (CheckBox) findViewById(R.id.cb_video_coll);
        iv_shear.setOnClickListener(this);
        mCb_coll.setOnClickListener(this);
        if (context instanceof VideoDetailsActivity) {
            mCb_coll.setChecked(((VideoDetailsActivity) context).mIs_collection == 1);
            setTitleOnclickListener((VideoDetailsActivity)context);
        }
        try {
            mIv_centerstart = (ImageView) findViewById(R.id.center_start);
            mIv_centerstart.setImageResource(R.drawable.middle_btn_bofangjian);
            if (context instanceof VideoDetailsActivity) {
                ((VideoDetailsActivity) context).mIv_centerstart = mIv_centerstart;
            }
            mIv_centerstart.setOnClickListener(this);
            mIv_centerstart.setVisibility(GONE);
        } catch (Exception e) {
        }
    }

    @Override
    protected void showWifiDialog() {
        startButtonLogic();
    }

    public void setIsColl(boolean isColl) {
        mCb_coll.setChecked(isColl);
        ((VideoDetailsActivity) mContext).mIs_collection = isColl ? 1 : 0;
    }

    //这个必须配置最上面的构造才能生效
    @Override
    public int getLayoutId() {
        if (mIfCurrentIsFullscreen) {
            return R.layout.video_full;
        }
        return R.layout.video_normal;
    }

    @Override
    protected int getProgressDialogLayoutId() {
        return R.layout.video_progress_dialog;
    }

    @Override
    protected int getVolumeLayoutId() {
        return R.layout.video_volume_dialog;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (!mIfCurrentIsFullscreen && v.getId() == R.id.back) {
            ((BaseActivity) getContext()).finish();
        } else if (v.getId() == R.id.shear) {
            if (mListener != null) {
                mListener.shear();
            }
        } else if (v.getId() == R.id.cb_video_coll) {
            if (mListener != null) {
                mListener.coll(mCb_coll.isChecked());
            }
        } else if (v.getId() == R.id.center_start) {
            centerImgState(GONE);
            try {
                getGSYVideoManager().getMediaPlayer().start();
            } catch (Exception e) {
                e.printStackTrace();
            }
            setStateAndUi(CURRENT_STATE_PLAYING);
        }
    }

    public interface TitleOnclickListener {
        void shear();

        void coll(boolean isColl);
    }

    TitleOnclickListener mListener;

    public void setTitleOnclickListener(TitleOnclickListener listener) {
        mListener = listener;
    }


    @Override
    protected void showVolumeDialog(float deltaY, int volumePercent) {
        if (mVolumeDialog == null) {
            View localView = LayoutInflater.from(getActivityContext()).inflate(getVolumeLayoutId(), null);
            if (localView.findViewById(getVolumeProgressId()) instanceof ProgressBar) {
                mDialogVolumeProgressBar = ((ProgressBar) localView.findViewById(getVolumeProgressId()));
                if (mVolumeProgressDrawable != null && mDialogVolumeProgressBar != null) {
                    mDialogVolumeProgressBar.setProgressDrawable(mVolumeProgressDrawable);
                }
            }
            mIv_img = (ImageView) localView.findViewById(R.id.video_volume_img);
            mVolumeDialog = new Dialog(getActivityContext(), R.style.video_style_dialog_progress);
            mVolumeDialog.setContentView(localView);
            mVolumeDialog.getWindow().addFlags(8);
            mVolumeDialog.getWindow().addFlags(32);
            mVolumeDialog.getWindow().addFlags(16);
            mVolumeDialog.getWindow().setLayout(-2, -2);
            WindowManager.LayoutParams localLayoutParams = mVolumeDialog.getWindow().getAttributes();
            localLayoutParams.gravity = Gravity.TOP | Gravity.LEFT;
            localLayoutParams.width = getWidth();
            localLayoutParams.height = getHeight();
            int location[] = new int[2];
            getLocationOnScreen(location);
            localLayoutParams.x = location[0];
            localLayoutParams.y = location[1];
            mVolumeDialog.getWindow().setAttributes(localLayoutParams);
        }
        if (!mVolumeDialog.isShowing()) {
            mVolumeDialog.show();
        }
        if (mDialogVolumeProgressBar != null) {
            if (volumePercent > 100) {
                volumePercent = 100;
            } else if (volumePercent < 0) {
                volumePercent = 0;
            }
            mDialogVolumeProgressBar.setProgress(volumePercent);
            if (volumePercent <= 0) {
                mIv_img.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_jingyin));
            } else {
                mIv_img.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_yinliang));
            }
        }
    }

    /**
     * 亮度dialog的layoutId
     * 继承后重写可返回自定义
     * 有自定义的实现逻辑可重载showBrightnessDialog方法
     */
    protected int getBrightnessLayoutId() {
        return R.layout.video_brightness_dialog;
    }

    /**
     * 触摸亮度dialog，如需要自定义继承重写即可，记得重写dismissBrightnessDialog
     */
    @Override
    protected void showBrightnessDialog(float percent) {
        if (mBrightnessDialog == null) {
            View localView = LayoutInflater.from(getActivityContext()).inflate(getBrightnessLayoutId(), null);
            mDialogBrightnessProgressBar = ((ProgressBar) localView.findViewById(R.id.brightness_progressbar));
            mBrightnessDialog = new Dialog(getActivityContext(), R.style.video_style_dialog_progress);
            mBrightnessDialog.setContentView(localView);
            mBrightnessDialog.getWindow().addFlags(8);
            mBrightnessDialog.getWindow().addFlags(32);
            mBrightnessDialog.getWindow().addFlags(16);
            mBrightnessDialog.getWindow().setLayout(-2, -2);
            WindowManager.LayoutParams localLayoutParams = mBrightnessDialog.getWindow().getAttributes();
            localLayoutParams.gravity = Gravity.TOP | Gravity.RIGHT;
            localLayoutParams.width = getWidth();
            localLayoutParams.height = getHeight();
            int location[] = new int[2];
            getLocationOnScreen(location);
            localLayoutParams.x = location[0];
            localLayoutParams.y = location[1];
            mBrightnessDialog.getWindow().setAttributes(localLayoutParams);
        }
        if (!mBrightnessDialog.isShowing()) {
            mBrightnessDialog.show();
        }
        if (mDialogBrightnessProgressBar != null) {
            mDialogBrightnessProgressBar.setProgress((int) (percent * 100));
        }
    }

    @Override
    protected void showProgressDialog(float deltaX, String seekTime, int seekTimePosition, String totalTime, int totalTimeDuration) {
        super.showProgressDialog(deltaX, seekTime, seekTimePosition, totalTime, totalTimeDuration);
        if (deltaX > 0) {
            if (mDialogIcon != null) {
                if (mIfCurrentIsFullscreen) {
                    mDialogIcon.setBackgroundResource(R.drawable.icon_da_kuaijin);
                } else {
                    mDialogIcon.setBackgroundResource(R.drawable.icon_xiao_kuaijin);
                }
            }
        } else {
            if (mDialogIcon != null) {
                if (mIfCurrentIsFullscreen) {
                    mDialogIcon.setBackgroundResource(R.drawable.icon_da_houtui);
                } else {
                    mDialogIcon.setBackgroundResource(R.drawable.icon_xiao_houtui);
                }
            }
        }
    }

    @Override
    protected void updateStartImage() {
        if (mIfCurrentIsFullscreen) {
            if (mStartButton instanceof ImageView) {
                ImageView imageView = (ImageView) mStartButton;
                if (mCurrentState == CURRENT_STATE_PLAYING) {
                    imageView.setImageResource(R.drawable.btn_zantingzhong);
                } else if (mCurrentState == CURRENT_STATE_ERROR) {
                    imageView.setImageResource(R.drawable.btn_bofangzhong);
                } else {
                    imageView.setImageResource(R.drawable.btn_bofangzhong);
                }
            }
        } else {
            if (mStartButton instanceof ImageView) {
                ImageView imageView = (ImageView) mStartButton;
                if (mCurrentState == CURRENT_STATE_PLAYING) {
                    imageView.setImageResource(R.drawable.middle_btn_zantingjian);
                } else if (mCurrentState == CURRENT_STATE_ERROR) {
                    imageView.setImageResource(R.drawable.middle_btn_bofangjian);
                } else {
                    imageView.setImageResource(R.drawable.middle_btn_bofangjian);
                }
            }
        }
    }

    public void centerImgState(int Visibility) {
        if (mIv_centerstart != null) {
            mIv_centerstart.setVisibility(Visibility);
        }
    }


    /**
     * 点击触摸显示和隐藏逻辑
     */
    @Override
    protected void onClickUiToggle() {
        if (mIfCurrentIsFullscreen && mLockCurScreen && mNeedLockFull) {
            setViewShowState(mLockScreen, VISIBLE);
            return;
        }
        if (mCurrentState == CURRENT_STATE_PREPAREING) {
            if (mBottomContainer != null) {
                if (mBottomContainer.getVisibility() == View.VISIBLE) {
                    changeUiToPrepareingClear();
                    hide();
                } else {
                    changeUiToPreparingShow();
                    show();
                }
            }
        } else if (mCurrentState == CURRENT_STATE_PLAYING) {
            if (mBottomContainer != null) {
                if (mBottomContainer.getVisibility() == View.VISIBLE) {
                    changeUiToPlayingClear();
                    hide();
                } else {
                    changeUiToPlayingShow();
                    show();
                }
            }
        } else if (mCurrentState == CURRENT_STATE_PAUSE) {
            if (mBottomContainer != null) {
                if (mBottomContainer.getVisibility() == View.VISIBLE) {
                    changeUiToPauseClear();
                    hide();
                    centerImgState(GONE);
                } else {
                    changeUiToPauseShow();
                    show();
                    centerImgState(VISIBLE);
                }
            }
        } else if (mCurrentState == CURRENT_STATE_AUTO_COMPLETE) {
            if (mBottomContainer != null) {
                if (mBottomContainer.getVisibility() == View.VISIBLE) {
                    changeUiToCompleteClear();
                    hide();
                } else {
                    changeUiToCompleteShow();
                    show();
                }
            }
        } else if (mCurrentState == CURRENT_STATE_PLAYING_BUFFERING_START) {
            if (mBottomContainer != null) {
                if (mBottomContainer.getVisibility() == View.VISIBLE) {
                    changeUiToPlayingBufferingClear();
                    hide();
                } else {
                    changeUiToPlayingBufferingShow();
                    show();
                }
            }
        }
    }

    private void hide() {
        if (mChangeListener != null) {
            mChangeListener.hide();
        }
    }

    private void show() {
        if (mChangeListener != null) {
            mChangeListener.show();
        }
    }

    @Override
    protected void hideAllWidget() {
        super.hideAllWidget();
        hide();
    }

    @Override
    public void onAutoCompletion() {
        super.onAutoCompletion();
        show();
    }

    public interface StateChangeListener {
        void hide();

        void show();
    }

    StateChangeListener mChangeListener;

    public void setStateChangeListener(StateChangeListener listener) {
        mChangeListener = listener;
    }

}

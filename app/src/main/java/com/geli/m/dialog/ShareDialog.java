package com.geli.m.dialog;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import com.geli.m.R;
import com.geli.m.dialog.base.BaseDialogFragment;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.Utils;
import java.util.HashMap;

/**
 * Created by Steam_l on 2018/3/27.
 * <p>
 * 分享窗口
 */
@SuppressLint("ValidFragment")
public class ShareDialog extends BaseDialogFragment implements View.OnClickListener {

    private String mShareTitle;
    private String mShareContent;
    private String mShareImageUrl;
    private String mShareUrl;

    private Platform.ShareParams mSp;
    private Platform mWechat;
    private Platform mWechatMoments;

    @Override
    protected int getResId() {
        return R.layout.dialog_share;
    }

    public ShareDialog(String title, String content, String imgUrl, String url) {
        mShareTitle = title;
        mShareContent = content;
        mShareImageUrl = imgUrl;
        mShareUrl = url;
    }

    public static ShareDialog newInstance(String title, String content, String imgUrl, String url) {
        return new ShareDialog(title, content, imgUrl, url);
    }

    @Override
    protected void initData() {
        ShareSDK.initSDK(mContext);
        mSp = new Platform.ShareParams();
        mSp.setTitle(mShareTitle);
        // sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
        mSp.setShareType(Platform.SHARE_WEBPAGE);//非常重要：一定要设置分享属性
        mSp.setText(mShareContent);
        if (!TextUtils.isEmpty(mShareImageUrl)) {   //设置图标
            mSp.setImageUrl(mShareImageUrl);
        } else {        //如果为空，则默认设置应用图标
            mSp.setImageData(BitmapFactory.decodeResource(getResources(), R.drawable.icon));
        }
        mSp.setUrl(mShareUrl);
        mSp.setSiteUrl(mShareUrl);
        mSp.setTitleUrl(mShareUrl);

        mWechat = ShareSDK.getPlatform(Wechat.NAME);
        mWechatMoments = ShareSDK.getPlatform(WechatMoments.NAME);
        mWechat.setPlatformActionListener(new MyPlatformActionListener());
        mWechatMoments.setPlatformActionListener(new MyPlatformActionListener());
    }

    /**
     * 回调
     */
    private class MyPlatformActionListener implements PlatformActionListener {

        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            mContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ShowSingleToast.showToast(mContext, Utils.getString(R.string.message_sharesuccess));
                }
            });
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            mContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ShowSingleToast.showToast(mContext, Utils.getString(R.string.message_sharefailed));
                }
            });
        }

        @Override
        public void onCancel(Platform platform, int i) {
        }
    }

    @Override
    protected void initEvent() {
        setDialogDismissListener(new DialogDismissListener() {
            @Override
            public void dismiss() {
                ShareSDK.stopSDK(mContext);
            }
        });
    }

    @Override
    protected EditText getEt() {
        return null;
    }

    @OnClick({R.id.tv_share_wechat_moments, R.id.tv_share_wechat_friends, R.id.bt_share_cancel})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.bt_share_cancel:                  /* 取消按钮 */
                dismiss();
                break;

            case R.id.tv_share_wechat_friends:          /* 微信好友 */
                mWechat.share(mSp);
                break;

            case R.id.tv_share_wechat_moments:          /* 朋友圈 */
                mWechatMoments.share(mSp);
                break;

            default:
                break;
        }
    }
}

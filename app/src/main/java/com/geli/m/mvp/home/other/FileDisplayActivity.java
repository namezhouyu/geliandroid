package com.geli.m.mvp.home.other;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.utils.FilesUtils;
import com.geli.m.utils.LogUtils;
import com.geli.m.utils.StringUtils;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;
import com.tencent.smtt.sdk.TbsReaderView;
import java.io.File;

import static com.geli.m.config.Constant.INTENT_LINKS;
import static com.geli.m.config.Constant.INTENT_LOCAL_FILE;

/**
 * 显示文件 -- 使用x5腾讯浏览器
 * author:  shen
 * date:    2018/12/13
 */
public class FileDisplayActivity extends BaseActivity implements TbsReaderView.ReaderCallback {



    @BindView(R.id.iv_title_back)
    ImageView mIvTitleBack;
    @BindView(R.id.tv_title_name)
    TextView mTvTitleName;
    @BindView(R.id.tv_title_right)
    TextView mTvTitleRight;
    @BindView(R.id.rl_title_rootlayout)
    RelativeLayout mRlayoutTitle;

    @BindView(R.id.llayout_FileDisplayActivity)
    LinearLayout mLinearLayout;

    TbsReaderView mTbsReaderView;

    private String mLocalFile = "";
    /** 链接 */
    public String mLinkeUrl = "";





    @Override
    protected int getResId() {
        return R.layout.activity_file_display;
    }

    @Override
    protected void initData() {
        mLocalFile = getIntent().getStringExtra(INTENT_LOCAL_FILE);
        mLinkeUrl = getIntent().getStringExtra(INTENT_LINKS);
        mTvTitleName.setText(getText(R.string.check));
        setTitleRight();
        initView();

    }

    @Override
    protected void initEvent() {
        displayFile();
    }

    @OnClick({R.id.iv_title_back, R.id.tv_title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                finish();
                break;

            case R.id.tv_title_right:
                openFile();
                break;
        }
    }



    private void setTitleRight() {
        mTvTitleRight.setVisibility(View.VISIBLE);
        Resources resources = mContext.getResources();
        Drawable drawable = resources.getDrawable(R.drawable.nav_btn_fenxiang_nor);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        mTvTitleRight.setCompoundDrawables(drawable, null, null, null);
        mTvTitleRight.setPadding(Utils.dip2px(mContext, 5),
                Utils.dip2px(mContext, 5),
                Utils.dip2px(mContext, 5),
                Utils.dip2px(mContext, 5));
    }


    private void initView() {
        mTbsReaderView = new TbsReaderView(this, this);
        mLinearLayout.addView(mTbsReaderView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

    }

    /**
     * 加载显示文件内容
     */
    private void displayFile() {
        if(StringUtils.isEmpty(mLocalFile)){
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putString("filePath", mLocalFile);
        bundle.putString("tempPath", Environment.getExternalStorageDirectory()
                .getPath());
        boolean result = mTbsReaderView.preOpen(FilesUtils.getExtensionName(mLocalFile), false);

        if (result) {
            mTbsReaderView.openFile(bundle);
        } else {
                File file = new File(mLocalFile);
                if (file.exists()) {
                    Intent openintent = new Intent();
                    openintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    String type = FilesUtils.getMIMEType(file);
                    // 设置intent的data和Type属性。
                    openintent.setDataAndType(/* uri */Uri.fromFile(file), type);
                    // 跳转
                    startActivity(openintent);
            }
        }
    }

    /**
     * 打开文件
     */
    private void openFile() {
        if (StringUtils.isNotEmpty(mLocalFile)) {
            File file = new File(mLocalFile);

            if (file.exists()) {
                //Intent intent = new Intent(Intent.ACTION_VIEW);        // 系统调用Action属性
                //intent.addCategory(Intent.CATEGORY_DEFAULT);
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.fromFile(file), "*/*");

//                String type = FilesUtils.getMIMEType(file);
//                intent.setDataAndType(/* uri */Uri.fromFile(file), type);

                try {
                    startActivity(intent);
                } catch (Exception e) {
                    LogUtils.i("查看 error:", e);
                    ToastUtils.showToast("打开文件失败，请重新下载");
                }
            } else {
                ToastUtils.showToast("文件已损坏，请重新下载");
            }
        }
    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mTbsReaderView != null){
            mTbsReaderView.onStop();
            mTbsReaderView = null;
        }
    }
}

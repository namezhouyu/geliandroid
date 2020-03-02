package com.geli.m.dialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.dialog.base.BaseDialogFragment;

/**
 * Created by Labor on 2017/6/13.
 */

public class TipDialog extends BaseDialogFragment implements View.OnClickListener {


    @BindView(R.id.dialog_title)
    TextView tv_title;
    @BindView(R.id.dialog_text)
    TextView tv_content;

    /** 确认按钮 */
    @BindView(R.id.dialog_confirm)
    TextView tv_confirm;

    /** 取消按钮 */
    @BindView(R.id.dialog_cancel)
    TextView tv_cancel;

    /** 两个按钮中间地线 */
    @BindView(R.id.tv_line_tip)
    TextView tv_line;


    private ClickListenerInterface clickListenerInterface;

    private String tipString;

    private boolean isShowTitle = true;
    private boolean isShowConfirm = true;
    private boolean isShowCancel = true;

    private String mTitleSrc = "提示";
    private String mConfirmSrc = "确认";
    private String mCancelSrc = "取消";

    private int mConfirmTextColor = 0XFF2E2E2E;
    private int mCancelTextColor = 0XFF2E2E2E;

    public static final String ARG_CONTENT = "arg_content";

    @Override
    protected int getResId() {
        return R.layout.dialog_tip;
    }

    public static TipDialog newInstance(String content) {
        TipDialog tipDialog = new TipDialog();
        Bundle args = new Bundle();
        args.putString(ARG_CONTENT, content);
        tipDialog.setArguments(args);
        return tipDialog;
    }

    @Override
    protected void init() {
        super.init();
        tipString = getArguments().getString(ARG_CONTENT);
    }

    @Override
    protected void initData() {
        setCancelable(true);
        if (!TextUtils.isEmpty(tipString)) {
            tv_content.setText(tipString);
        }

        setTvTitleVisibility(isShowTitle);
        setTvConfirmVisibility(isShowConfirm);
        setTvCancelVisibility(isShowCancel);

        tv_title.setText(mTitleSrc);
        tv_confirm.setText(mConfirmSrc);
        tv_cancel.setText(mCancelSrc);

        tv_confirm.setTextColor(mConfirmTextColor);
        tv_cancel.setTextColor(mCancelTextColor);

        setWindow();
    }



    private void setWindow() {
        Window dialogWindow = getDialog().getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8);
        dialogWindow.setAttributes(lp);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected EditText getEt() {
        return null;
    }


    public TipDialog isShowTitle(boolean isShow){
        if(tv_title != null){
            setTvTitleVisibility(isShow);
        }
        isShowTitle = isShow;

        return this;
    }

    /* 按钮是否显示 */
    public TipDialog isShowConfirm(boolean isShow){
        if(tv_confirm != null){
            setTvConfirmVisibility(isShow);
        }
        isShowConfirm = isShow;
        return this;
    }

    public TipDialog isShowCancel(boolean isShow){
        if(tv_cancel != null){
            setTvCancelVisibility(isShow);
        }
        isShowCancel = isShow;
        return this;
    }

    /* 标题 */
    public TipDialog setTitleSrc(String src){
        if(tv_title != null){
            tv_title.setText(src);
        }
        mTitleSrc = src;
        return this;
    }

    /* 按钮文字 */
    public TipDialog setConfirmSrc(String src){
        if(tv_confirm != null){
            tv_confirm.setText(src);
        }
        mConfirmSrc = src;
        return this;
    }

    public TipDialog setCancelSrc(String src){
        if(tv_cancel != null){
            tv_cancel.setText(src);
        }
        mCancelSrc = src;
        return this;
    }

    /* 按钮显示的颜色 */
    public TipDialog setConfirmTextColor(int color){
        if(tv_confirm != null){
            tv_confirm.setTextColor(color);
        }
        mConfirmTextColor = color;
        return this;
    }

    public TipDialog setCancelTextColor(int color){
        if(tv_cancel != null){
            tv_cancel.setTextColor(color);
        }
        mCancelTextColor = color;
        return this;
    }

    /*---------------------------------------------------------------------------------*/
    private void setTvTitleVisibility(boolean isShow) {
        if(isShow){
            tv_title.setVisibility(View.VISIBLE);
        }else {
            tv_title.setVisibility(View.GONE);
        }
    }

    private void setTvConfirmVisibility(boolean isShow) {
        if(isShow){
            tv_confirm.setVisibility(View.VISIBLE);
        }else {
            tv_confirm.setVisibility(View.GONE);
            tv_line.setVisibility(View.GONE);
        }
    }

    private void setTvCancelVisibility(boolean isShow) {
        if(isShow){
            tv_cancel.setVisibility(View.VISIBLE);
        }else {
            tv_cancel.setVisibility(View.GONE);
            tv_line.setVisibility(View.GONE);
        }
    }

    public interface ClickListenerInterface {

        void doConfirm(TipDialog tipDialog);

        void doCancel();
    }

    public TipDialog setOnclickListener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
        return this;
    }

    @OnClick({R.id.dialog_confirm, R.id.dialog_cancel})
    public void onClick(View v) {super.onClick(v);
        switch (v.getId()) {
            case R.id.dialog_cancel:
                if (clickListenerInterface != null) {
                    clickListenerInterface.doCancel();
                }
                dismiss();
                break;

            case R.id.dialog_confirm:
                if (clickListenerInterface != null) {
                    clickListenerInterface.doConfirm(this);
                }
                break;
        }
    }
}

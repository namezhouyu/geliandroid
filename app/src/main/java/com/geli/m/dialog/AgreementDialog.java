package com.geli.m.dialog;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.dialog.base.BaseDialogFragment;
import com.geli.m.utils.StringUtils;

/**
 * shen
 */

public class AgreementDialog extends BaseDialogFragment implements View.OnClickListener {

    @BindView(R.id.wv_agreement)
    WebView mWvAgreement;

    private ClickListenerInterface clickListenerInterface;

    private String mUrl;


    public static final String AGREEMENT_URL = "agreement_url";

    @Override
    protected int getResId() {
        return R.layout.dialog_agreement;
    }

    public static AgreementDialog newInstance(String url) {
        AgreementDialog dialog = new AgreementDialog();
        Bundle args = new Bundle();
        args.putString(AGREEMENT_URL, url);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    protected void init() {
        super.init();
        mUrl = getArguments().getString(AGREEMENT_URL);
    }

    @Override
    protected void initData() {
        setCancelable(true);

        if (StringUtils.isNotEmpty(mUrl)) {
            mWvAgreement.loadUrl(mUrl);         // 加载url
        }

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

    public interface ClickListenerInterface {

        void doConfirm(AgreementDialog agreementDialog);

        void doCancel();
    }

    public void setOnclickListener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    @OnClick({R.id.tv_confirm_agreement, R.id.tv_cancel_agreement})
    public void onClick(View v) {super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_cancel_agreement:
                if (clickListenerInterface != null) {
                    clickListenerInterface.doCancel();
                }
                dismiss();
                break;

            case R.id.tv_confirm_agreement:
                if (clickListenerInterface != null) {
                    clickListenerInterface.doConfirm(AgreementDialog.this);
                }
                break;
        }
    }
}

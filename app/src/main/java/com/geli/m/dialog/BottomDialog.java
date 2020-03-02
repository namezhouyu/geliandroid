package com.geli.m.dialog;

import android.view.View;
import android.widget.EditText;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.dialog.base.BaseDialogFragment;

/**
 * Created by Steam_l on 2018/3/20.
 */

public class BottomDialog extends BaseDialogFragment implements View.OnClickListener {
    PayClickListener mPayClickListener;

    public interface PayClickListener {
        void weChat();

        void aliPay();

        void wallet();

        void xianxia();

        void geliPay();
    }

    @Override
    protected int getResId() {
        return R.layout.dialog_pay;
    }

    public static BottomDialog newInstance(PayClickListener listener) {
        BottomDialog bottomDialog = new BottomDialog(listener);
        return bottomDialog;
    }

    private BottomDialog(PayClickListener listener) {
        mPayClickListener = listener;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected EditText getEt() {
        return null;
    }

    @OnClick({R.id.tv_dialog_pay_wallet, R.id.tv_dialog_pay_xianxia,
            R.id.tv_dialog_pay_weixin, R.id.tv_dialog_pay_zhifubao,
            R.id.tv_dialog_pay_gelipay})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_dialog_pay_wallet:
                mPayClickListener.wallet();
                break;
            case R.id.tv_dialog_pay_xianxia:
                mPayClickListener.xianxia();
                break;
            case R.id.tv_dialog_pay_weixin:
                mPayClickListener.weChat();
                break;
            case R.id.tv_dialog_pay_zhifubao:
                mPayClickListener.aliPay();
                break;
            case R.id.tv_dialog_pay_gelipay:
                mPayClickListener.geliPay();
                break;
            default:
                break;
        }
    }
}

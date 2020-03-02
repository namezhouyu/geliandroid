package com.geli.m.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.coustomview.PasswordInputView;
import com.geli.m.dialog.base.BaseDialogFragment;
import com.geli.m.utils.KeyBoardUtils;
import com.geli.m.utils.Utils;

/**
 * Created by Steam_l on 2018/1/18.
 */
public class InputPassDialog extends BaseDialogFragment {


    public static final String ARGS_PRICE = "args_price";
    public static final String ARGS_SHOW_MESS = "args_show_mess";

    /** 密码输入框 */
    @BindView(R.id.piv_inputpass)
    PasswordInputView mInputView;
    /** 需要支付的金额 */
    @BindView(R.id.tv_dialog_inputpass_price)
    TextView mTvPrice;
    /** 提示信息 */
    @BindView(R.id.tv_dialog_inputpass_mess)
    TextView mTvMess;
    /** 取消 */
    @BindView(R.id.btn_dialog_inputpass_cancel)
    Button mBtnCancel;

    String mPrice = "";

    public static InputPassDialog newInstance(String payPrice) {
        return newInstance(payPrice, true);
    }

    /**
     *
     * @param payPrice      支付的金额
     * @param showMess      是否显示提示消息
     * @return
     */
    public static InputPassDialog newInstance(String payPrice, boolean showMess) {
        InputPassDialog inputPassDialog = new InputPassDialog();
        Bundle args = new Bundle();
        args.putString(ARGS_PRICE, payPrice);
        args.putBoolean(ARGS_SHOW_MESS, showMess);
        inputPassDialog.setArguments(args);
        return inputPassDialog;
    }

    public interface InputCompleteListener {
        void inputComplete(String pass, String Price);
    }

    InputCompleteListener mInputCompleteListener;

    public void setInputCompleteListener(InputCompleteListener listener) {
        this.mInputCompleteListener = listener;
    }

    @Override
    protected int getResId() {
        return R.layout.dialog_inputpass;
    }

    @Override
    protected void init() {
        super.init();
        mPrice = getArguments().getString(ARGS_PRICE);
        if (!getArguments().getBoolean(ARGS_SHOW_MESS, true)) {
            mTvMess.setVisibility(View.GONE);
        }
        // mTvPrice.setText(Utils.getPrice(mPrice));

        Double tempPrice = getPrice(mPrice);
        String textPrice = "";
        if (tempPrice > 0) {
            textPrice = Utils.getString(R.string.overseas_list_money, Utils.getFormatDoubleTwoDecimalPlaces(tempPrice, 2));
            mInputView.setVisibility(View.VISIBLE);
            mBtnCancel.setVisibility(View.GONE);
        }else {
            textPrice = Utils.getString(R.string.overseas_list_money, Utils.getString(R.string.inquiry));
            mInputView.setVisibility(View.GONE);
            mBtnCancel.setVisibility(View.VISIBLE);
        }
        mTvPrice.setText(textPrice);
    }


    public Double getPrice(Object price) {
        Double tempPrice = 0.0;
        if (price instanceof String) {
            tempPrice = Double.valueOf(price + "");
        } else if (price instanceof Double) {
            tempPrice = (Double) price;
        }
        return tempPrice;
    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            Window window = dialog.getWindow();
            window.setLayout((int) (dm.widthPixels * 0.85), ViewGroup.LayoutParams.MATCH_PARENT);
        }
        mInputView.setFocusable(true);
        mInputView.requestFocus();
        KeyBoardUtils.openKeybord(mInputView, mContext);
    }

    @Override
    protected void initData() {
        KeyBoardUtils.disableCopyAndPaste(getEt());
        mInputView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 6 && mInputCompleteListener != null) {
                    KeyBoardUtils.closeKeybord(mInputView, mContext);
                    mInputCompleteListener.inputComplete(s.toString().trim(), mPrice);
                    // dismiss();
                }
            }
        });
    }

    @Override
    protected void initEvent() {
    }

    @OnClick({R.id.iv_popup_buy_close})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_popup_buy_close:
                KeyBoardUtils.closeKeybord(mInputView, mContext);
                dismiss();
                break;

            case R.id.btn_dialog_inputpass_cancel:
                dismiss();
                break;

            default:
                break;
        }
    }

    @Override
    protected EditText getEt() {
        return mInputView;
    }

}

package com.geli.m.dialog.addcart;

import android.annotation.SuppressLint;
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
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.dialog.base.BaseDialogFragment;
import com.geli.m.utils.KeyBoardUtils;
import com.geli.m.utils.StringUtils;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;

/**
 * Created by Steam_l on 2018/1/5.
 * 添加购物车弹窗
 */

@SuppressLint("ValidFragment")
public class EditGoodsNumDialog extends BaseDialogFragment implements View.OnClickListener {

    private static String EDIT_NUMBER;

    EditGoodsNumListener mEditGoodsNumListener;
    String mEditNumber;
    @BindView(R.id.et_number_DialogEditGoodsNum)
    EditText mEtNumber;
    @BindView(R.id.iv_add_DialogEditGoodsNum)
    ImageView mIvAdd;
    @BindView(R.id.iv_cut_DialogEditGoodsNum)
    ImageView mIvCut;
    @BindView(R.id.btn_cancel_DialogEditGoodsNum)
    Button mBtnCancel;
    @BindView(R.id.btn_confirm_DialogEditGoodsNum)
    Button mBtnConfirm;

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
    }

    @SuppressLint("ValidFragment")
    public EditGoodsNumDialog(EditGoodsNumListener listener) {
        mEditGoodsNumListener = listener;
    }

    public static EditGoodsNumDialog newInstance(String number, EditGoodsNumListener listener) {
        EditGoodsNumDialog addCartDialog = new EditGoodsNumDialog(listener);
        Bundle args = new Bundle();
        args.putString(EDIT_NUMBER, number);
        addCartDialog.setArguments(args);
        return addCartDialog;
    }

    @Override
    protected int getResId() {
        return R.layout.dialog_edit_goods_num;
    }

    @Override
    protected void init() {
        super.init();
        Bundle arguments = getArguments();
        mEditNumber = arguments.getString(EDIT_NUMBER);
    }

    @Override
    protected void initData() {
        KeyBoardUtils.disableCopyAndPaste(getEt());
        setNumber(mEditNumber);
    }

    @Override
    protected void initEvent() {
    }

    @Override
    protected EditText getEt() {
        return mEtNumber;
    }


    @OnClick({R.id.iv_add_DialogEditGoodsNum, R.id.iv_cut_DialogEditGoodsNum,
            R.id.btn_cancel_DialogEditGoodsNum, R.id.btn_confirm_DialogEditGoodsNum})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_add_DialogEditGoodsNum:
                addNumber();
                break;

            case R.id.iv_cut_DialogEditGoodsNum:
                cutNumber();
                break;

            case R.id.btn_cancel_DialogEditGoodsNum:
                dismiss();
                break;

            case R.id.btn_confirm_DialogEditGoodsNum:
                confirmNum();
                break;
        }
    }

    private void confirmNum() {
        String number = mEtNumber.getText().toString().trim();
        if(StringUtils.isEmpty(number)){
            ToastUtils.showToast(mContext, Utils.getString(R.string.number_not_empty));
            return;
        }else {
            if(mEditGoodsNumListener != null){
                mEditGoodsNumListener.editNum(number);
                dismiss();
            }
        }
    }

    /**
     * 数量 +
     */
    private void addNumber() {
        Integer integer = Integer.valueOf(mEtNumber.getText().toString().trim());
        String number = ++integer + "";
        setNumber(number);
    }

    /**
     * 数量 -
     */
    private void cutNumber() {
        Integer integer = Integer.valueOf(mEtNumber.getText().toString().trim());
        String number = (--integer <= 0 ? 1 : integer) + "";
        setNumber(number);
    }


        /**
     * 设置 数量编辑框 和 当前价格
     * @param number
     */
    private void setNumber(String number){
        if(StringUtils.isEmpty(number)){
            return;
        }
        mEtNumber.setText(number);
        mEtNumber.setSelection(mEtNumber.getText().length());
    }

    /**
     * 编辑框的监听事件
     */
    private void setEditListener(){
        mEtNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                if (StringUtils.isEmpty(s.toString().trim())) {
//                    return;
//                }
//
//                // 最大购买量
//                if (Integer.valueOf(s.toString()) > maxNumber) {
//                    ToastUtils.showToast(mContext, Utils.getString(R.string.beyond_maxnumber));
//                    mEtNumber.setText(maxNumber + "");
//                }
//                mEtNumber.setSelection(mEtNumber.getText().length());
            }
        });
    }

    public interface EditGoodsNumListener {
        void editNum(String number);
    }

}
package com.geli.m.dialog;

import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import com.geli.m.R;
import com.geli.m.dialog.base.BaseDialogFragment;
import com.geli.m.utils.KeyBoardUtils;

/**
 * Created by Steam_l on 2018/3/6.
 */

public class CommentDialog extends BaseDialogFragment {
    @BindView(R.id.et_comment_dialog_content)
    EditText et_content;
    @BindView(R.id.bt_comment_dialog_send)
    Button bt_send;


    @Override
    protected int getResId() {
        return R.layout.dialog_comment;
    }

    @Override
    protected void initData() {
        KeyBoardUtils.disableCopyAndPaste(getEt());
    }

    @Override
    protected void initEvent() {
    }

    @Override
    protected EditText getEt() {
        return et_content;
    }

    public static CommentDialog newInstance() {
        return new CommentDialog();
    }

    @Override
    public void onStart() {
        super.onStart();
        et_content.setFocusable(true);
        et_content.requestFocus();
        KeyBoardUtils.openKeybord(et_content, mContext);
    }

}

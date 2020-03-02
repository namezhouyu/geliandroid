package com.geli.m.dialog.base;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.geli.m.R;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.utils.KeyBoardUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.TimeUnit;

/**
 * Created by Steam_l on 2018/1/6.
 */
public abstract class BaseDialogFragment extends DialogFragment
        implements View.OnClickListener, KeyBoardUtils.KeyBoardListener, DialogInterface.OnKeyListener {

    protected View mRootView;
    protected Unbinder mUnbinder;
    protected FragmentActivity mContext;

    /* 软键盘是否正在显示 */
    protected boolean keyBoardIsShow = false;
    // 如果有显示"软键盘" 这个会赋值为1， 退出状态框的时候"第一次隐藏软键盘"，"第二次退出对话框"
    int tag = 0;

    protected boolean isMyCancelable = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getResId(), container, false);
        mUnbinder = ButterKnife.bind(this, mRootView);
        mContext = getActivity();
        return mRootView;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public LayoutInflater getLayoutInflater(Bundle savedInstanceState) {
        setStyle(STYLE_NO_TITLE, 0);                        // 无标题 -- 默认样式
        return super.getLayoutInflater(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
        initData();
        initEvent();

        getDialog().setOnKeyListener(this);

        // "取消" 监听
        getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (mDismissListener != null) {
                    mDismissListener.dismiss();
                }
                dialog.dismiss();
                dialog.cancel();
                // dialogfragment的dismiss方法内部封装了移除fragment操作
                // 但是dialog.dismiss没有,所以手动
                if (getFragmentManager() != null) {
                    getFragmentManager().beginTransaction().remove(BaseDialogFragment.this).commit();
                }
            }
        });



        //        mRootView.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        ////                if (keyBoardIsShow) {
        ////                    closeKeyBoard();
        ////                } else {
        ////                    closeKeyBoard();
        ////                    dismiss();
        ////                }
        //                System.out.println("=============mRootView===============");
        //            }
        //        });
        //        getDialog().setOnCancelListener(new DialogInterface.OnCancelListener() {
        //            @Override
        //            public void onCancel(DialogInterface dialog) {
        //                dialog.dismiss();
        //                dialog.cancel();
        //            }
        //        });
    }

    private DialogDismissListener mDismissListener;

    public interface DialogDismissListener {
        void dismiss();
    }

    public void setDialogDismissListener(DialogDismissListener listener) {
        this.mDismissListener = listener;
    }


    protected abstract int getResId();

    protected void init() {
        setCancelable(false);
        // setCancelable(true);
        ((BaseActivity) mContext).setKeyBoardListener(this);
    }

    protected abstract void initData();

    protected abstract void initEvent();

    protected abstract EditText getEt();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @OnClick({R.id.dialog_contentview, R.id.dialog_rootview})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_contentview:               /* LinearLayout -- 是被R.id.dialog_rootview包裹个布局 */
                break;

            case R.id.dialog_rootview:                  /* 根布局 */

                if (keyBoardIsShow) {
                    closeKeyBoard();
                } else {
                    closeKeyBoard();

                    if(isCancelable() && isMyCancelable)
                        dismiss();
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void dismiss() {
        closeKeyBoard();
        super.dismiss();
    }

    /**
     * 如果有 -- "输入框"则关闭"软键盘"
     */
    private void closeKeyBoard() {
        if (getEt() != null) {
            KeyBoardUtils.closeKeybord(getEt(), mContext);
        }
    }


    // implements KeyBoardUtils.KeyBoardListener
    @Override
    public void hideKeyBoard() {
        tag = 1;
        keyBoardIsShow = false;
        Observable.timer(100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (tag == 1) {
                            tag++;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void showKeyBoard() {
        keyBoardIsShow = true;
    }


    public boolean isMyCancelable() {
        return isMyCancelable;
    }

    public void setMyCancelable(boolean myCancelable) {
        isMyCancelable = myCancelable;
    }

    // implements DialogInterface.OnKeyListener
    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && isMyCancelable) {
            if (tag == 1) {
                tag++;
            } else {
                closeKeyBoard();
                dismiss();
            }
        }
        return false;
    }
}

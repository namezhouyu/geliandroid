package com.geli.m.mvp.home.mine_fragment.feedback_activity;

import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.mvp.base.BaseView;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.select.SelectPhotoFragment;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.Utils;
import com.luck.picture.lib.tools.PictureFileUtils;

/**
 * Created by Steam_l on 2018/1/8.
 *
 * 意见反馈
 */

public class FeedbackActivity extends MVPActivity<FeedbackPresentImpl> implements View.OnClickListener, BaseView {

    /** 标题名称 */
    @BindView(R.id.tv_title_name)
    TextView mTvTitle;
    /** 反馈"单选框"组 */
    @BindView(R.id.rg_feedback_group)
    RadioGroup mRgProblem;
    /** 反馈文本编辑框 */
    @BindView(R.id.et_feedback_content)
    EditText mEtContent;
    /** 反馈文本字数(已输入字数/可输入总字数) */
    @BindView(R.id.tv_feedback_limit)
    TextView mTvLimit;

    int mType = 5;
    private SelectPhotoFragment mFragment;

    @Override
    protected int getResId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initData() {
        mRgProblem.check(R.id.rb_feedback_pay);
        mTvTitle.setText(Utils.getString(R.string.feedback));
        initSelectPhotoFragment();
    }

    @Override
    protected void initEvent() {
        initRgListener();
        initEtListener();
    }

    private void initSelectPhotoFragment(){
        mFragment = SelectPhotoFragment.newInstance(1, 3, 15);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_feedback_content, mFragment).commit();
    }

    private void initRgListener() {
        mRgProblem.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                // 5支付问题6商品问题7商家问题8物流问题
                if (checkedId == R.id.rb_feedback_pay) {
                    mType = 5;
                } else if (checkedId == R.id.rb_feedback_goods) {
                    mType = 6;
                } else if (checkedId == R.id.rb_feedback_shop) {
                    mType = 7;
                } else if (checkedId == R.id.rb_feedback_logistics) {
                    mType = 8;
                }
            }
        });
    }

    private void initEtListener() {
        mEtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = s.toString();
                if (content.length() > 200) {
                    s.delete(200, 201);
                }
                mTvLimit.setText(s.toString().length() + "/200");
            }
        });
    }

    @OnClick({R.id.bt_feedback_submit, R.id.iv_title_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;

            case R.id.bt_feedback_submit:                       /** 提交按钮 */
                submit();
                break;

            default:
                break;
        }
    }

    private void submit() {
        if (TextUtils.isEmpty(mEtContent.getText().toString().trim())) {
            ShowSingleToast.showToast(mContext, Utils.getString(R.string.message_pelasefillinfo));
            return;
        }
        mPresenter.feedback(mFragment.getPhotoModelList(),
                mType + "",
                mEtContent.getText().toString().trim(),
                mFragment.getPhotoModelList().size() + "",
                GlobalData.getUser_id());
    }

    @Override
    protected FeedbackPresentImpl createPresenter() {
        return new FeedbackPresentImpl(this);
    }

    @Override
    public void onSuccess(String msg) {
        ShowSingleToast.showToast(mContext, msg);
        PictureFileUtils.deleteCacheDirFile(mContext);      // 清理照片缓存
        finish();
    }

    @Override
    public void onError(String msg) {
        ShowSingleToast.showToast(mContext, msg);
    }

    @Override
    public void showLoading() {
        Utils.uProgressDialog(mContext);
    }

    @Override
    public void hideLoading() {
        Utils.dismissProgress();
    }
}

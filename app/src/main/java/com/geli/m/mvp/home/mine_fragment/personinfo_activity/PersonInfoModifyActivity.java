package com.geli.m.mvp.home.mine_fragment.personinfo_activity;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.PersonInfoBean;
import com.geli.m.coustomview.ShapeImageView;
import com.geli.m.mvp.base.BaseView;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.select.PictureSelector;
import com.geli.m.select.SelectPhotoFragment;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.geli.m.BuildConfig.GL_IMAGE_URL;
import static com.geli.m.config.Constant.ACTION_MODIFY;
import static com.geli.m.config.Constant.BROADCAST_DATA;
import static com.geli.m.config.Constant.INTENT_BEAN;

/**
 * Created by Steam_l on 2018/1/8.
 *
 * 个人中心
 */

public class PersonInfoModifyActivity extends MVPActivity<ModifyPersonInfoPresentImpl> implements View.OnClickListener, BaseView {

    @BindView(R.id.tv_title_name)
    TextView mTvTitle;

    /** 性别单选框组  */
    @BindView(R.id.rg_modify_personinfo)
    RadioGroup mRadioGroup;
    /** 头像 */
    @BindView(R.id.siv_modifyperinfo_img)
    ShapeImageView mIvImg;
    /** 用户名 -- 真实姓名 */
    @BindView(R.id.et_modifyperinfo_name)
    EditText mEtName;
    /** 昵称 */
    @BindView(R.id.et_modifyperinfo_nickname)
    EditText mEtNickName;
    /** 唯一id吧 */
    @BindView(R.id.tv_mofidyperinfo_id)
    TextView mTvId;

    /** 用户信息 */
    private PersonInfoBean.DataEntity mDataEntity;
    /** 1:男; 2:女*/
    private int sex = 1;
    public static final String AVATAR_TRANSITIONNAME = "avatar_transitionname";
    public static final String NAME_TRANSITIONNAME = "name_transitionname";

    @Override
    protected int getResId() {
        return R.layout.activity_modify_personinfo;
    }

    @Override
    protected void init() {
        super.init();
        mImmersionBar.statusBarColor(R.color.white).init();
        mDataEntity = getIntent().getParcelableExtra(INTENT_BEAN);
    }


    @Override
    protected void initData() {
        setTransitionView();                // 设置下过渡控件
        setView();
    }

    @Override
    protected void initEvent() {
        setRgListener();
    }



    /**
     * 设置下过渡控件 -- 界面切换的时候有控件共享的效果
     */
    private void setTransitionView() {
        // 主要的语句---将当前Activity的View和自己定义的Key绑定起来
//        ViewCompat.setTransitionName(mIvImg, AVATAR_TRANSITIONNAME);
//        ViewCompat.setTransitionName(mEtNickName, NAME_TRANSITIONNAME);
    }

    private void setView() {
        mTvTitle.setText(Utils.getString(R.string.title_personinfo));
        if (mDataEntity != null) {
            GlideUtils.loadAvatar(mContext, mDataEntity.getAvatar(), mIvImg, !mDataEntity.getAvatar().startsWith(GL_IMAGE_URL));
            int id;
            if (mDataEntity.getSex() == 1) {
                id = R.id.cb_modify_personinfo_male;
            } else {
                id = R.id.cb_modify_personinfo_female;
            }
            mRadioGroup.check(id);
            mEtNickName.setText(mDataEntity.getNickname());
            mEtNickName.setSelection(mEtNickName.getText().toString().toString().length());
            mEtName.setText(mDataEntity.getTruename());
            mTvId.setText(mDataEntity.getPhone());
        }
    }

    private void setRgListener() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.cb_modify_personinfo_female) {
                    sex = 2;
                } else {
                    sex = 1;
                }
            }
        });
    }

    @OnClick({R.id.iv_title_back, R.id.siv_modifyperinfo_img, R.id.bt_modify_save})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                ActivityCompat.finishAfterTransition(this);     // 使用这个代替 finish
                break;

            case R.id.bt_modify_save:
                save();
                break;

            case R.id.siv_modifyperinfo_img:
                SelectPhotoFragment.gotoSelectPhoto(this, null, 1);
                break;

            default:
                break;
        }
    }

    private void save() {
        if (TextUtils.isEmpty(mEtName.getText().toString().trim()) ||
                TextUtils.isEmpty(mEtNickName.getText().toString().trim())) {
            ShowSingleToast.showToast(mContext, Utils.getString(R.string.message_pelasefillinfo));
            return;
        }

        Map data = new HashMap();
        data.put("sex", sex + "");
        data.put("user_id", GlobalData.getUser_id());
        data.put("nickname", mEtNickName.getText().toString().trim());
        data.put("truename", mEtName.getText().toString().trim());
        mPresenter.modify(data);
    }


    @Override
    protected ModifyPersonInfoPresentImpl createPresenter() {
        return new ModifyPersonInfoPresentImpl(this);
    }

    @Override
    public void onSuccess(String msg) {
        ToastUtils.showToast(msg);
        mDataEntity.setNickname(mEtNickName.getText().toString().trim());
        mDataEntity.setSex(sex);
        mDataEntity.setTruename(mEtName.getText().toString().trim());
        Intent intent = new Intent(ACTION_MODIFY);
        intent.putExtra(BROADCAST_DATA, mDataEntity);
        sendBroadcast(intent);
        if (msg.equals(Utils.getString(R.string.upload_success))) {
            return;
        }
        ActivityCompat.finishAfterTransition(this);                 // 使用这个代替 finish
    }

    @Override
    public void onError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void showLoading() {
        Utils.uProgressDialog(mContext);
    }

    @Override
    public void hideLoading() {
        Utils.dismissProgress();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    for (LocalMedia media : selectList) {
                        mDataEntity.setAvatar(media.getPath());
                        GlideUtils.loadAvatar(mContext, media.getPath(), mIvImg, true);
                        mPresenter.doAvatar(media.getCompressPath());
                    }
                    break;
            }
        }
    }
}

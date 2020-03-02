package com.geli.m.mvp.home.mine_fragment.member_activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.MemberBean;
import com.geli.m.coustomview.CustomProgressBar;
import com.geli.m.coustomview.ShapeImageView;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.mvp.home.other.WebViewActivity;
import com.geli.m.utils.EasyRecyclerViewUtils;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.Utils;
import com.geli.m.viewholder.MemberCenterViewHolder;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import static com.geli.m.config.Constant.INTENT_LINKS;

/**
 * Created by Steam_l on 2018/1/29.
 *
 * 会员中心
 */

public class MemberCenterActivity extends MVPActivity<MemberCenterPresentImpl> implements View.OnClickListener, MemberView {

    /** 包裹"标题"的根布局 */
    @BindView(R.id.rl_title_rootlayout)
    RelativeLayout mRlTitleRoot;

    /** 返回按钮 */
    @BindView(R.id.iv_title_back)
    ImageView mIvBack;
    /** 标题名称 */
    @BindView(R.id.tv_title_name)
    TextView mTvTitle;
    /** 等级图片 */
    @BindView(R.id.iv_membercenter_grade)
    ImageView mIvGrade;
    /** 头像图片 */
    @BindView(R.id.siv_membercenter_img)
    ShapeImageView mIvImg;
    /** 用户名称 */
    @BindView(R.id.tv_membercenter_name)
    TextView mTvName;
    /** 等级进度条 */
    @BindView(R.id.cpb_membercenter_progress)
    CustomProgressBar mProgressBar;
    /** 积分xxx */
    @BindView(R.id.tv_membercenter_integral)
    TextView mTvIntegral;
    /** 等级"文字" 如: 铂金VIP */
    @BindView(R.id.tv_membercenter_grade)
    TextView mTvGrade;

    @BindView(R.id.erv_member_list)
    EasyRecyclerView erv_list;

    private MemberBean.DataEntity mEntity;
    private RecyclerArrayAdapter mAdapter;

    @Override
    protected int getResId() {
        return R.layout.activity_membercentre;
    }

    @Override
    protected void initData() {

        mImmersionBar.statusBarColor(R.color.zhusediao).init();
        mRlTitleRoot.setBackgroundColor(Utils.getColor(R.color.zhusediao));
        setView();
        setErv();
        mPresenter.getData(GlobalData.getUser_id());        // 获取用户等级信息
    }

    @Override
    protected void initEvent() {
        setErvAdapter();
    }


    private void setView() {
        mTvTitle.setTextColor(Utils.getColor(R.color.white));
        mTvTitle.setText(Utils.getString(R.string.member_centre));
        mIvBack.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.nav_btn_white_fanhui));
    }

    private void setErv() {
        erv_list.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new MemberCenterViewHolder(parent, mContext);                   // 会员的那些规则
            }
        };

        erv_list.setAdapterWithProgress(mAdapter);
        EasyRecyclerViewUtils.initEasyRecyclerView(erv_list);
    }

    private void setErvAdapter() {
        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {  // 使用WebViewActivity 显示那些规则
            @Override
            public void onItemClick(int position) {
                MemberBean.DataEntity.ArticleEntity entity = (MemberBean.DataEntity.ArticleEntity) mAdapter.getItem(position);
                startActivity(WebViewActivity.class, new Intent().putExtra(INTENT_LINKS, entity.getArt_url()));
            }
        });
    }


    @OnClick({R.id.iv_title_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;

            default:
                break;
        }
    }

    @Override
    protected MemberCenterPresentImpl createPresenter() {
        return new MemberCenterPresentImpl(this);
    }

    @Override
    public void onSuccess(String msg) {
        ShowSingleToast.showToast(mContext, msg);
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

    @Override
    public void showData(MemberBean.DataEntity dataEntity) {
        if(dataEntity != null) {
            mEntity = dataEntity;
            mAdapter.clear();
            if (null == dataEntity.getArticle() || dataEntity.getArticle().size() == 0) {
                erv_list.showEmpty();
            } else {
                mAdapter.addAll(dataEntity.getArticle());
            }

            MemberBean.DataEntity.UserEntity user = dataEntity.getUser();
            GlideUtils.loadAvatar(mContext, user.getAvatar(), mIvImg);
            mTvName.setText(user.getTruename());

            MemberBean.DataEntity.IntegralEntity integral = dataEntity.getIntegral();
            mProgressBar.setMaxProgress(integral.getRank_fraction());
            mProgressBar.setCurProgress(integral.getExperience_grade());
            mTvIntegral.setText(Utils.getString(R.string.integral, integral.getCurrent_integral()));
            GlideUtils.loadImg(mContext, integral.getRank_img(), mIvGrade);
            mTvGrade.setText(integral.getRank_name());
        }
    }
}

package com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.goodscomment_activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.CartBean;
import com.geli.m.bean.GoodsCommentBean;
import com.geli.m.bean.OrderListBean;
import com.geli.m.mvp.base.BaseView;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.select.PictureSelector;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;
import com.google.gson.Gson;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.geli.m.config.Constant.INTENT_COMMENT_GOODS;

/**
 * Created by Steam_l on 2018/1/4.
 *
 * 商品评论
 *
 */
public class GoodsCommentActivity extends MVPActivity<GoodsCommentPresentImpl>
        implements View.OnClickListener, BaseView, GoodsCommentView {

    /** 平论列表 */
    @BindView(R.id.erv_goodscomment_list)
    EasyRecyclerView mErvList;
    /** 标题名称 */
    @BindView(R.id.tv_title_name)
    TextView mTvTitle;

    /** 当前选择图片的"商品id" */
    private String mCurrGoodsId = "";
    /** 要评论的食品列表 */
    private List<OrderListBean.DataEntity.GoodsListEntity> mEntityList;
    /** 应该是"订单号" */
    private String order_id;

    private RecyclerArrayAdapter mAdapter;

    private CommentBottomVH mCommentBottomVH;

    @Override
    protected int getResId() {
        return R.layout.activity_goods_comment;
    }

    @Override
    protected void init() {
        super.init();
        mEntityList = getIntent().getParcelableArrayListExtra(INTENT_COMMENT_GOODS);
    }

    @Override
    protected void initData() {
        mTvTitle.setText(Utils.getString(R.string.title_goodscomment));
        List<GoodsCommentBean> data = setGoodsCommentBeans();       // 将GoodsListEntity 拆开 放到GoodsCommentBean
        setErvAdapter(data);
    }

    @Override
    protected void initEvent() {

    }

    /**
     * 将GoodsListEntity 拆开 放到GoodsCommentBean
     * @return
     */
    @NonNull
    private List<GoodsCommentBean> setGoodsCommentBeans() {
        List<GoodsCommentBean> data = new ArrayList();
        Gson gson = new Gson();

        for (OrderListBean.DataEntity.GoodsListEntity entity : mEntityList) {
            if (TextUtils.isEmpty(order_id)) {
                order_id = entity.getOrder_id() + "";
            }
            List<CartBean.DataEntity.CartListEntity.SpecificationEntity> goods_attr = entity.getGoods_attr();
            String json = gson.toJson(goods_attr);
            data.add(new GoodsCommentBean(
                    entity.getGoods_id() + "",
                    "",
                    "5",
                    json,
                    "1",
                    entity.getGoods_name(),
                    entity.getGoods_thumb(),
                    entity.getOrder_id() + ""));
        }
        return data;
    }

    private void setErvAdapter(final List<GoodsCommentBean> data) {
        mAdapter = new RecyclerArrayAdapter<GoodsCommentBean>(mContext, data) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                GoodsCommentViewHolder goodsCommentViewHolder = new GoodsCommentViewHolder(parent, mContext);
                goodsCommentViewHolder.setListener(new GoodsCommentViewHolder.ClickListener() {
                    @Override
                    public void deleteImg(String id) {
                        List<GoodsCommentBean> allData = mAdapter.getAllData();
                        for (GoodsCommentBean bean : allData) {
                            if (bean.getGoods_id().equals(id)) {
                                uploadImg(bean);
                                break;
                            }
                        }
                    }

                    @Override
                    public void setCurrSelectGoodsId(String id) {
                        mCurrGoodsId = id;
                    }
                });
                return goodsCommentViewHolder;
            }
        };

        // 添加"脚布局"
        mAdapter.addFooter(mCommentBottomVH = new CommentBottomVH(mContext, new CommentBottomVH.ClickListener() {
            @Override
            public void release() {
                releaseComment();
            }
        }));
        mErvList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        DividerDecoration itemDecoration = new DividerDecoration(Utils.getColor(R.color.erv_goodstitle_bgcolor), Utils.dip2px(mContext, 5));
        itemDecoration.setDrawLastItem(true);
        mErvList.addItemDecoration(itemDecoration);
        mErvList.setAdapterWithProgress(mAdapter);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    if (!TextUtils.isEmpty(mCurrGoodsId)) {
                        List<GoodsCommentBean> allData = mAdapter.getAllData();
                        int i = 0;
                        for (GoodsCommentBean bean : allData) {
                            if (bean.getGoods_id().equals(mCurrGoodsId)) {
                                // 从意图中获取 -- 图片列表
                                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                                bean.imgList = selectList;
                                bean.setImg_number(selectList.size() + "");
                                uploadImg(bean);                // 上传图片
                                mAdapter.update(bean, i);
                                break;
                            }
                            i++;
                        }
                    }
                    break;
            }
        }
    }

    /**
     * 选择图片就上传图片
     * @param bean
     */
    private void uploadImg(GoodsCommentBean bean) {
        mPresenter.goodsCommentImg(bean);
    }

    int mUploadImgNumber;

    /**
     * 提交评论
     */
    private void releaseComment() {
        //处理图片上传失败
        mUploadImgNumber = 0;
        List<GoodsCommentBean> allData = mAdapter.getAllData();
        for (GoodsCommentBean bean : allData) {
            Integer integer = new Integer(bean.getGoods_id());
            if (bean.imgList != null && bean.imgList.size() != 0) { // 可能没上传图片(如上传失败)
                mUploadImgNumber++;
                if (!mPresenter.mIntegers.contains(integer)) {
                    mPresenter.goodsCommentImg(bean);               // 没有上传的再上传一次
                }
            }
        }
        uploadImgSuccess();
    }


    @Override
    protected GoodsCommentPresentImpl createPresenter() {
        return new GoodsCommentPresentImpl(this);
    }

    @Override
    public void onSuccess(String msg) {
        ToastUtils.showToast(msg);
        PictureFileUtils.deleteCacheDirFile(mContext);
        finish();
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
    public void uploadImgSuccess() {
        if (mUploadImgNumber != mPresenter.mIntegers.size()) {
            return;
        }
        List<GoodsCommentBean> allData = mAdapter.getAllData();
        for (GoodsCommentBean bean : allData) {
            bean.imgList = null;
        }
        String json = new Gson().toJson(allData);
        Map data = new HashMap();
        data.put("user_id", GlobalData.getUser_id());
        data.put("order_id", order_id);
        data.put("data", json);
        data.put("logistics_grade", mCommentBottomVH.getLogisticsScore());
        data.put("serve_grade", mCommentBottomVH.getServiceScore());
        mPresenter.goodsComment(data);                                  // 评论
    }
}

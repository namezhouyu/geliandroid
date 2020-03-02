package com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.goodscomment_activity;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.GoodsCommentBean;
import com.geli.m.coustomview.RatingBar;
import com.geli.m.manager.FullyGridLayoutManager;
import com.geli.m.select.GridImageAdapter;
import com.geli.m.select.PictureSelector;
import com.geli.m.select.SelectPhotoFragment;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/26.
 *
 * 商品评论--每一个商品的
 */
public class GoodsCommentViewHolder extends BaseViewHolder<GoodsCommentBean> {

    Context mContext;
    /** 名字(规格xxx) */
    private final TextView mTv_name;
    /** 图片列表RecyclerView */
    private final RecyclerView mRv_lsit;
    /** 商品图片 */
    private final ImageView mIv_img;
    /** 评论总字数(已输入/总可输入) */
    private final TextView mTv_contentnumber;
    /** 评论编辑框 */
    private final EditText mEt_content;
    /** 冰鲜程度 */
    private final RatingBar mRb_score;
    /** 已选的图片列表 */
    private List<LocalMedia> selectList = new ArrayList<>();


    ClickListener mListener;

    /**
     * 暴露给子类的接口
     */
    public interface ClickListener {
        /**
         * 删除图片
         * @param id
         */
        void deleteImg(String id);

        /**
         * 设置当前要评论的商品
         * @param id
         */
        void setCurrSelectGoodsId(String id);
    }

    public void setListener(ClickListener listener) {
        mListener = listener;
    }

    public GoodsCommentViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_goodscomment);

        mContext = context;
        mTv_name = $(R.id.tv_itemview_goodscomment_name);
        mRv_lsit = $(R.id.rv_goodscomment_list);
        mTv_contentnumber = $(R.id.tv_itemview_goodscomment_conntentnumber);
        mEt_content = $(R.id.et_itemview_goodscomment_content);
        mIv_img = $(R.id.iv_itemview_goodscomment_img);
        mRb_score = $(R.id.rb_itemview_goodscomment_score);

        FullyGridLayoutManager manager = new FullyGridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false);
        mRv_lsit.setLayoutManager(manager);
        mRv_lsit.addItemDecoration(new SpaceDecoration(Utils.dip2px(getContext(), 15)));
    }

    @Override
    public void setData(final GoodsCommentBean data) {
        super.setData(data);
        mTv_name.setText(data.getGoods_name());
        mRb_score.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() { // "冰鲜程度"监听
            @Override
            public void onRatingChange(float ratingCount) {
                data.setGrade(ratingCount + "");
            }
        });

        GlideUtils.loadImg(mContext, data.getGoods_img(), mIv_img);
        final GridImageAdapter adapter = new GridImageAdapter(mContext, new GridImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                if (mListener != null) {
                    mListener.setCurrSelectGoodsId(data.getGoods_id() + "");
                }
                SelectPhotoFragment.gotoSelectPhoto(mContext, selectList, 3);
            }
        }, 3);

        adapter.setDeleteListener(new GridImageAdapter.DeleteListener() {
            @Override
            public void delete() {
                if (mListener != null) {
                    data.imgList = adapter.getList();                   // 从 GridImageAdapter 获取照片列表
                    data.setImg_number(adapter.getList().size() + "");
                    mListener.deleteImg(data.getGoods_id() + "");
                }
            }
        });

        // 初始化 -- 有数据的时候将其设置进去
        adapter.setSelectMax(3);
        selectList = data.imgList;
        if (data.imgList.size() != 0) {
            adapter.setList(data.imgList);
        }

        mRv_lsit.setAdapter(adapter);
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                LocalMedia media = selectList.get(position);
                String pictureType = media.getPictureType();
                int mediaType = PictureMimeType.pictureToVideo(pictureType);
                switch (mediaType) {
                    case 1:                         /* 预览图片 */
                        PictureSelector.create((GoodsCommentActivity) mContext).externalPicturePreview(position, selectList);
                        break;
                }
            }
        });

        mEt_content.addTextChangedListener(new TextWatcher() {              // 评论编辑框的监听事件
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = mEt_content.getText().toString().length();
                if (length > 200) {
                    ShowSingleToast.showToast(mContext, Utils.getString(R.string.limit_exceeded));
                    int editStart = mEt_content.getSelectionStart();
                    int editEnd = mEt_content.getSelectionEnd();
                    s.delete(editStart - 1, editEnd);
                    mEt_content.setText(s);
                    mEt_content.setSelection(s.length());
                    return;
                }
                mTv_contentnumber.setText(length + "/200");
                data.setCom_content(mEt_content.getText().toString().trim());
            }
        });
    }
}

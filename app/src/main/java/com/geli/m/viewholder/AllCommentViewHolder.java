package com.geli.m.viewholder;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.geli.m.R;
import com.geli.m.bean.CartBean;
import com.geli.m.bean.GoodsDetailsBean;
import com.geli.m.manager.FullyGridLayoutManager;
import com.geli.m.select.GridImageAdapter;
import com.geli.m.select.SelectPhotoFragment;
import com.geli.m.ui.activity.AllCommentActivity;
import com.geli.m.utils.DateFormatUtil;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import static com.geli.m.BuildConfig.GL_IMAGE_URL;

/**
 * Created by Steam_l on 2017/12/26.
 *
 * 全部评论 -- 项布局
 */
public class AllCommentViewHolder extends BaseViewHolder<GoodsDetailsBean.DataBean.GoodsCommentBean> {

    Context mContext;

    /** 评级 */
    private final RatingBar mRb_score;
    /** 评论用户的头像 */
    private final ImageView mIv_img;
    /** 评论的内容 */
    private final TextView mTv_content;
    /** 评论的时间 */
    private final TextView mTv_time;
    /** 规格 */
    private final TextView mTv_specifi;
    /** 评论的用户名 */
    private final TextView mTv_name;
    /** 点赞(有数量的) */
    private final CheckBox mCb_like;
    private final RecyclerView mErv_list;

    public AllCommentViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.itemview_allrecomment);

        mContext = context;
        mTv_name = $(R.id.tv_itemview_comment_name);
        mTv_specifi = $(R.id.tv_itemview_comment_specifi);
        mTv_time = $(R.id.tv_itemview_comment_date);
        mTv_content = $(R.id.tv_itemview_comment_content);
        mIv_img = $(R.id.iv_itemview_comment_avatr);
        mRb_score = $(R.id.rb_itemview_comment_score);
        mCb_like = $(R.id.cb_itemview_comment_like);
        mErv_list = $(R.id.rv_itemview_comment_list);

        FullyGridLayoutManager manager = new FullyGridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false);
        mErv_list.setLayoutManager(manager);
        mErv_list.addItemDecoration(new SpaceDecoration(Utils.dip2px(getContext(), 15)));
    }



    @Override
    public void setData(final GoodsDetailsBean.DataBean.GoodsCommentBean data) {
        super.setData(data);

        setTv_name(data);       // 设置用户名
        setIv_img(data);        // 设置用户头像
        setCb_like(data);       // 设置"点赞"的点击事件
        setTv_specifi(data);    // 设置"规格"
        setErv_list(data);      // 置"图片列表"数据

        mTv_time.setText(DateFormatUtil.transForDate(Integer.valueOf(data.getAdd_time())));
        mRb_score.setRating(data.getCom_grade());
        mTv_content.setText(data.getCom_content());
        mCb_like.setText("(" + data.getCom_like() + ")");
        mCb_like.setChecked(data.getIs_like() == 1);

    }

    /**
     * 设置"图片列表"数据
     * @param data
     */
    private void setErv_list(GoodsDetailsBean.DataBean.GoodsCommentBean data) {
        if (data.getCom_photo() != null && data.getCom_photo().size() > 0) {
            mErv_list.setVisibility(View.VISIBLE);
            final List<LocalMedia> mediaList = new ArrayList<>();
            for (String url : data.getCom_photo()) {
                LocalMedia media = new LocalMedia();
                if (!url.startsWith(GL_IMAGE_URL)) {
                    url = GL_IMAGE_URL + url;
                }
                media.setPath(url);
                mediaList.add(media);
            }
            final GridImageAdapter adapter = new GridImageAdapter(mContext, null, 3, SelectPhotoFragment.MODE_CHECK);
            adapter.setList(mediaList);
            adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    SelectPhotoFragment.picturePreview(mContext, position, mediaList);  // 预览图片
                }
            });
            mErv_list.setAdapter(adapter);
        } else {
            mErv_list.setVisibility(View.GONE);
        }
    }

    /**
     * 设置"规格"
     * @param data
     */
    private void setTv_specifi(GoodsDetailsBean.DataBean.GoodsCommentBean data) {
        if (data.getGoods_attr() != null) {
            String specifi = "";
            List<CartBean.DataEntity.CartListEntity.SpecificationEntity> goods_attr = data.getGoods_attr();
            for (CartBean.DataEntity.CartListEntity.SpecificationEntity entity : goods_attr) {
                specifi += entity.getValue() + ";";
            }
            if (!TextUtils.isEmpty(specifi)) {
                specifi.substring(0, specifi.length() - 1);
            }
            mTv_specifi.setText(specifi);
        } else {
            mTv_specifi.setText("");
        }
    }


    /**
     * 设置用户名
     * @param data
     */
    private void setTv_name(GoodsDetailsBean.DataBean.GoodsCommentBean data){
        if (TextUtils.isEmpty(data.getNickname())) {                // 名字为空的 -- 设为"匿名用户"
            mTv_name.setText(Utils.getString(R.string.anonymoususer));
        } else {
            mTv_name.setText(data.getNickname());
        }
    }

    /**
     * 设置"头像"
     * @param data
     */
    private void setIv_img(GoodsDetailsBean.DataBean.GoodsCommentBean data){
        if (TextUtils.isEmpty(data.getAvatar())) {
            mIv_img.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.img_touxiang50));
        } else {
            GlideUtils.loadAvatar(mContext, data.getAvatar(), mIv_img);
        }
    }

    /**
     * 设置"点赞"的点击事件
     * @param data
     */
    private void setCb_like(final GoodsDetailsBean.DataBean.GoodsCommentBean data) {
        mCb_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCb_like.setChecked(!mCb_like.isChecked());
                ((AllCommentActivity) mContext).toLike(data.getCom_id() + "");
            }
        });
    }

}

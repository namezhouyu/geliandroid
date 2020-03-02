package com.geli.m.mvp.home.find_fragment.findlist_fragment.vh;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.FindListBean;
import com.geli.m.manager.FullyGridLayoutManager;
import com.geli.m.mvp.home.find_fragment.findlist_fragment.FindListFragment;
import com.geli.m.select.GridImageAdapter;
import com.geli.m.select.SelectPhotoFragment;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.luck.picture.lib.entity.LocalMedia;
import com.makeramen.roundedimageview.RoundedImageView;
import java.util.ArrayList;
import java.util.List;

import static com.geli.m.BuildConfig.GL_IMAGE_URL;

/**
 * Created by Steam_l on 2018/3/21.
 */

public class FindListArticleViewHolder extends BaseViewHolder<FindListBean.DataEntity> {

    Context mContext;
    private final EasyRecyclerView mErv_img;
    private final RoundedImageView mRiv_avatar;
    private final CheckBox mCb_like;
    private final TextView mTv_viewnumber;
    private final TextView mTv_name;
    private final TextView mTv_title;
    FindListFragment mFragment;

    public FindListArticleViewHolder(ViewGroup parent, Context context, FindListFragment fragment) {
        super(parent, R.layout.itemview_find_article);
        mContext = context;
        mFragment = fragment;
        mTv_title = $(R.id.tv_itemview_find_article_title);
        mTv_name = $(R.id.tv_itemview_find_article_name);
        mTv_viewnumber = $(R.id.tv_itemview_find_article_viewnumber);
        mCb_like = $(R.id.cb_itemview_find_article_like);
        mRiv_avatar = $(R.id.riv_itemview_find_article_avatar);
        mErv_img = $(R.id.erv_itemview_find_article_list);


        setErv();
    }



    @Override
    public void setData(final FindListBean.DataEntity data) {
        super.setData(data);

        initAdapter(data);
        setView(data);
    }



    private void setErv() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false);
        mErv_img.setLayoutManager(manager);
        mErv_img.addItemDecoration(new SpaceDecoration(Utils.dip2px(getContext(), 15)));
    }

    private void initAdapter(FindListBean.DataEntity data) {
        final GridImageAdapter adapter = new GridImageAdapter(mContext, null, 3, SelectPhotoFragment.MODE_CHECK);
        final List<LocalMedia> mediaList = new ArrayList<>();
        for (String url : data.getCover_url()) {
            LocalMedia media = new LocalMedia();
            if (!url.startsWith(GL_IMAGE_URL)) {
                url = GL_IMAGE_URL + url;
            }
            media.setPath(url);
            mediaList.add(media);
        }
        adapter.setList(mediaList);
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                SelectPhotoFragment.picturePreview(mContext, position, mediaList);
            }
        });

        // 设置头像和名字
        FindListBean.DataEntity.AuthorResEntity author_res = data.getAuthor_res();
        if (adapter != null) {
            GlideUtils.loadImg(mContext, author_res.getAuthor_icon(), mRiv_avatar);
            mTv_name.setText(author_res.getAuthor_name());
        }

        mErv_img.setAdapterWithProgress(adapter);
    }

    private void setView(final FindListBean.DataEntity data) {
        mTv_title.setText(data.getArticle_title());
        SpannableString spannableString = new SpannableString(Utils.getString(R.string.view_number) + "(" + data.getView_num() + ")");
        spannableString.setSpan(new ForegroundColorSpan(Utils.getColor(R.color.zhusediao)), Utils.getString(R.string.view_number).length(), spannableString.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mTv_viewnumber.setText(spannableString);
        mCb_like.setText("(" + data.getLike_num() + ")");
        mCb_like.setChecked(data.getIs_like() == 1);
        mCb_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCb_like.isChecked()) {
                    data.setIs_like(1);
                    data.setLike_num(data.getLike_num() + 1);
                } else {
                    data.setIs_like(0);
                    data.setLike_num(data.getLike_num() - 1);
                }
                mCb_like.setText("(" + data.getLike_num() + ")");
                mFragment.like(getDataPosition());
            }
        });
    }
}

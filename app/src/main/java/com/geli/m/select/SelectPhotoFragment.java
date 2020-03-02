package com.geli.m.select;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.geli.m.R;
import com.geli.m.manager.FullyGridLayoutManager;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.base.BaseFragment;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * author：luck
 * project：PictureSelector
 * package：com.luck.pictureselector
 * email：893855882@qq.com
 * data：2017/5/30
 *
 * 选择图片
 */
public class SelectPhotoFragment extends BaseFragment {

    private final static String TAG = SelectPhotoFragment.class.getSimpleName();
    private List<LocalMedia> selectList = new ArrayList<>();
    private RecyclerView recyclerView;
    private GridImageAdapter adapter;
    private int maxSelectNum = 4;//最多选择
    private int rowNum = 4;//一行显示几张图片
    private int mPadding;//距离
    private int themeId;
    public static final String ARG_MAXSELECTNUMBER = "arg_maxselectnumber";
    public static final String ARG_ROWNUMBER = "arg_rownumber";
    public static final String ARG_PHOTOLIST = "arg_photolist";
    public static final String ARG_PADDING = "arg_padding";

    /** 模式 -- 选择图片 */
    public static final int MODE_SELECT = 1 << 0;
    /** 模式 -- 查看图片 */
    public static final int MODE_CHECK = 1 << 1;
    private int curr_mode = MODE_SELECT;
    private Context mContext;

    public void setMode(int mode) {
        curr_mode = mode;
    }

    public int getMode() {
        return curr_mode;
    }

    /**
     * @param maxSelectNum 最多可选择的照片数目
     * @return
     */
    public static SelectPhotoFragment newInstance(int maxSelectNum, int padding) {
        return newInstance(maxSelectNum, -1, padding);
    }

    /**
     * @param maxSelectNum 最多可选择的照片数目
     * @param rowNum       一行显示几张图片
     * @return
     */
    public static SelectPhotoFragment newInstance(int maxSelectNum, int rowNum, int padding) {
        return newInstance(maxSelectNum, rowNum, null, padding);
    }

    public static void gotoSelectPhoto(Context context, List<LocalMedia> selectList, int maxSelectNum) {
        initPictureSelector(PictureSelector.create((BaseActivity) context), maxSelectNum, selectList);
    }

    public static void gotoSelectPhoto(BaseFragment fragment, List<LocalMedia> selectList, int maxSelectNum) {
        initPictureSelector(PictureSelector.create(fragment), maxSelectNum, selectList);
    }

    public static void initPictureSelector(PictureSelector pictureSelector, int maxSelectNum, List<LocalMedia> selectList) {
        pictureSelector.openGallery(PictureMimeType.ofImage())
                .theme(R.style.picture_white_style)
                .maxSelectNum(maxSelectNum)
                .minSelectNum(1)
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .isCamera(true)
                .enableCrop(false)
                .compress(true)
                .glideOverride(160, 160)
                .previewEggs(true)
                .isGif(false)
                .openClickSound(false)
                .selectionMedia(selectList)
                .minimumCompressSize(100)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    public static void picturePreview(Context context, int position, List<LocalMedia> selectList) {
        picturePreview(PictureSelector.create((BaseActivity) context), position, selectList);
    }

    public static void picturePreview(BaseFragment fragment, int position, List<LocalMedia> selectList) {
        picturePreview(PictureSelector.create(fragment), position, selectList);
    }

    public static void picturePreview(PictureSelector pictureSelector, int position, List<LocalMedia> selectList) {
        pictureSelector.externalPicturePreview(position, "/gelifood", selectList);
    }


    /**
     * @param maxSelectNum 最多可选择的照片数目
     * @param rowNum       一行显示几张图片
     * @param photoList    网络图片集合，PhotoModel的originalPath要拼上图片地址前缀
     * @return
     */
    public static SelectPhotoFragment newInstance(int maxSelectNum, int rowNum, List<LocalMedia> photoList, int padding) {
        SelectPhotoFragment fragment = new SelectPhotoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MAXSELECTNUMBER, maxSelectNum);
        if (rowNum != -1) {
            args.putInt(ARG_ROWNUMBER, rowNum);
        }
        if (photoList != null) {
            args.putSerializable(ARG_PHOTOLIST, (Serializable) photoList);
        }
        args.putInt(ARG_PADDING, padding);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            maxSelectNum = getArguments().getInt(ARG_MAXSELECTNUMBER, 5);
            rowNum = getArguments().getInt(ARG_ROWNUMBER, 4);
            selectList = (List<LocalMedia>) getArguments().getSerializable(ARG_PHOTOLIST);
            mPadding = getArguments().getInt(ARG_PADDING, 0);
            if (selectList == null) {
                selectList = new ArrayList<>();
            }
        }
    }


    @Override
    public int getResId() {
        return R.layout.test_select;
    }

    @Override
    public void init() {
        mContext = getActivity();
        themeId = R.style.picture_white_style;
        recyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler);
        ViewCompat.setNestedScrollingEnabled(recyclerView, false);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(getActivity(), rowNum, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new SpaceDecoration(Utils.dip2px(getContext(), mPadding)));
        adapter = new GridImageAdapter(getActivity(), onAddPicClickListener, maxSelectNum, curr_mode);
        adapter.setList(selectList);
        adapter.setSelectMax(maxSelectNum);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                LocalMedia media = selectList.get(position);
                String pictureType = media.getPictureType();
                int mediaType = PictureMimeType.pictureToVideo(pictureType);
                switch (mediaType) {
                    case 1:
                        // 预览图片
                        picturePreview(SelectPhotoFragment.this, position, selectList);
                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            SelectPhotoFragment.gotoSelectPhoto(SelectPhotoFragment.this, selectList, maxSelectNum);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择
                    selectList = PictureSelector.obtainMultipleResult(data);
                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    public List<LocalMedia> getPhotoModelList() {
        return selectList;
    }
}

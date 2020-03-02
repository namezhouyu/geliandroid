package com.geli.m.select;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.geli.m.R;
import com.geli.m.utils.GlideUtils;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import static com.geli.m.BuildConfig.GL_IMAGE_URL;

/**
 * author：luck
 * project：PictureSelector
 * package：com.luck.pictureselector.adapter
 * email：893855882@qq.com
 * data：16/7/27
 *
 */
public class GridImageAdapter extends RecyclerView.Adapter<GridImageAdapter.ViewHolder> {

    public static final int TYPE_CAMERA = 1;
    public static final int TYPE_PICTURE = 2;
    private LayoutInflater mInflater;
    private List<LocalMedia> list = new ArrayList<>();
    private int selectMax = 9;
    private Context mContext;
    private int curr_mode;

    /** 点击添加图片跳转 */
    private onAddPicClickListener mOnAddPicClickListener;

    public interface onAddPicClickListener {
        void onAddPicClick();
    }

    public GridImageAdapter(Context context, onAddPicClickListener mOnAddPicClickListener, int maxSelectNum) {
        this(context, mOnAddPicClickListener, maxSelectNum, SelectPhotoFragment.MODE_SELECT);
    }

    public GridImageAdapter(Context context, onAddPicClickListener mOnAddPicClickListener, int maxSelectNum, int mode) {
        this.mContext = context;
        this.selectMax = maxSelectNum;
        this.mOnAddPicClickListener = mOnAddPicClickListener;
        curr_mode = mode;
        mInflater = LayoutInflater.from(context);
    }

    public void setSelectMax(int selectMax) {
        this.selectMax = selectMax;
    }

    public void setList(List<LocalMedia> list) {
        this.list = list;
    }

    public List<LocalMedia> getList() {
        return list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mImg;
        private final ImageView mAddImg;
        private final ImageView mIv_delimg;

        public ViewHolder(View view) {
            super(view);
            mImg = (ImageView) view.findViewById(R.id.iv_selectimg_img);
            mAddImg = (ImageView) view.findViewById(R.id.iv_selectimg_addimg);
            mIv_delimg = (ImageView) view.findViewById(R.id.iv_selectimg_delimg);
        }
    }

    @Override
    public int getItemCount() {
        if (curr_mode == SelectPhotoFragment.MODE_CHECK) {
            return list.size();
        }
        if (list.size() < selectMax) {
            return list.size() + 1;
        } else {
            return list.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (curr_mode == SelectPhotoFragment.MODE_CHECK) {
            return TYPE_PICTURE;
        }
        if (isShowAddItem(position)) {
            return TYPE_CAMERA;
        } else {
            return TYPE_PICTURE;
        }
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.itemview_selectimg,
                viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    private boolean isShowAddItem(int position) {
        int size = list.size() == 0 ? 0 : list.size();
        return position == size;
    }

    DeleteListener mDeleteListener;

    public interface DeleteListener {
        void delete();
    }

    public void setDeleteListener(DeleteListener listener) {
        mDeleteListener = listener;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        // 少于?张，显示继续添加的图标
        if (getItemViewType(position) == TYPE_CAMERA) {
            viewHolder.mAddImg.setVisibility(View.VISIBLE);
            viewHolder.mImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnAddPicClickListener != null) {
                        mOnAddPicClickListener.onAddPicClick();
                    }
                }
            });
            viewHolder.mIv_delimg.setVisibility(View.INVISIBLE);
        } else {
            final LocalMedia media = list.get(position);
            String path = media.getPath();
            if (path.startsWith(GL_IMAGE_URL)) {
                GlideUtils.loadImg(mContext, path, viewHolder.mImg);
            } else {
                GlideUtils.loadImg(mContext, path, viewHolder.mImg, true);
            }
            viewHolder.mAddImg.setVisibility(View.GONE);
            viewHolder.mIv_delimg.setVisibility(View.VISIBLE);
            viewHolder.mIv_delimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = viewHolder.getAdapterPosition();
                    // 这里有时会返回-1造成数据下标越界,具体可参考getAdapterPosition()源码，
                    // 通过源码分析应该是bindViewHolder()暂未绘制完成导致，知道原因的也可联系我~感谢
                    if (index != RecyclerView.NO_POSITION) {
                        list.remove(index);
                        notifyItemRemoved(index);
                        notifyItemRangeChanged(index, list.size());
                    }
                    if (mDeleteListener != null) {
                        mDeleteListener.delete();
                    }
                }
            });

        }
        // itemView 的点击事件
        if (mItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = viewHolder.getAdapterPosition();
                    mItemClickListener.onItemClick(adapterPosition, v);
                }
            });
        }
        if (curr_mode == SelectPhotoFragment.MODE_CHECK) {
            viewHolder.mIv_delimg.setVisibility(View.GONE);
        }
        //
        //
        //        //少于8张，显示继续添加的图标
        //        if (list.size() < maxSelectNum && getItemViewType(position) == TYPE_CAMERA) {
        //            viewHolder.mAddImg.setVisibility(View.VISIBLE);
        //            viewHolder.mImg.setOnClickListener(new View.OnClickListener() {
        //                @Override
        //                public void onClick(View v) {
        //                    mOnAddPicClickListener.onAddPicClick();
        //                }
        //            });
        //            viewHolder.mIv_delimg.setVisibility(View.INVISIBLE);
        //        } else {
        //
        //            final LocalMedia media = list.get(position);
        //            viewHolder.mAddImg.setVisibility(View.GONE);
        //            //            viewHolder.tipIv.setImageResource(R.drawable.icon_xiangji);
        //            viewHolder.mIv_delimg.setVisibility(View.VISIBLE);
        //            viewHolder.mIv_delimg.setOnClickListener(new View.OnClickListener() {
        //                @Override
        //                public void onClick(View view) {
        //                    int index = viewHolder.getAdapterPosition();
        //                    // 这里有时会返回-1造成数据下标越界,具体可参考getAdapterPosition()源码，
        //                    // 通过源码分析应该是bindViewHolder()暂未绘制完成导致，知道原因的也可联系我~感谢
        //                    if (index != RecyclerView.NO_POSITION) {
        //                        list.remove(index);
        //                        notifyItemRemoved(index);
        //                        if (list.size() == 0) {
        //                            notifyDataSetChanged();
        //                        }
        //                        notifyItemRangeChanged(index, list.size());
        //                        if (mListener != null) {
        //                            mListener.selectPicture(list);
        //                        }
        //
        //                    }
        //                }
        //            });
        //            System.out.println("=============" + media.getPath() + "===============");
        //            if (media.getPath().startsWith(UrlSet.HTTP)) {
        //                GlideUtils.loadImg(viewHolder.itemView.getContext(), media.getPath(), viewHolder.mImg);
        //            } else {
        //                GlideUtils.loadImg(viewHolder.itemView.getContext(), media.getPath(), viewHolder.mImg, true);
        //            }
        //            //            Glide.with(viewHolder.itemView.getContext())
        //            //                    .load(photoModel.getOriginalPath())
        //            //                    .into(viewHolder.mImg);
        //
        //            //itemView 的点击事件
        //            if (mItemClickListener != null) {
        //                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
        //                    @Override
        //                    public void onClick(View v) {
        //                        int adapterPosition = viewHolder.getAdapterPosition();
        //                        mItemClickListener.onItemClickVH(adapterPosition, v);
        //                    }
        //                });
        //            }
        //        }
    }

    protected OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

}

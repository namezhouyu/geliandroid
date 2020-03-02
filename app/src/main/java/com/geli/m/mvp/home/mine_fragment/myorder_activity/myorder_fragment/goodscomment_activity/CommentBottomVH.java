package com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.goodscomment_activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.geli.m.R;
import com.geli.m.coustomview.RatingBar;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * author:  shen
 * date:    2018/10/27
 *
 * 评论的布局
 */
public class CommentBottomVH implements RecyclerArrayAdapter.ItemView{

    Context mContext;
    ClickListener mListener;

    /** 物流程度 */
    private RatingBar mRbLogisticsScore;
    /** 服务程度 */
    private RatingBar mRbServiceScore;
    /** 发布按钮 */
    private Button mBtRelease;

    public CommentBottomVH(Context context, ClickListener listener){
        mContext = context;
        mListener = listener;
    }

    @Override
    public View onCreateView(ViewGroup parent) {
        return LayoutInflater.from(mContext).inflate(R.layout.include_goodscomment_bottom, parent, false);
    }

    @Override
    public void onBindView(View headerView) {
        mBtRelease = (Button) headerView.findViewById(R.id.bt_goodscomment_release);
        mRbServiceScore = (RatingBar) headerView.findViewById(R.id.rb_include_goodscomment_servicescore);
        mRbLogisticsScore = (RatingBar) headerView.findViewById(R.id.rb_include_goodscomment_logisticsscore);

        if(mListener != null){
            mBtRelease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.release();
                }
            });
        }
    }

    /**
     * 物流评价
     * @return
     */
    public String getLogisticsScore(){
        return mRbLogisticsScore.getStartStep() + "";
    }

    /**
     * 服务评价
     * @return
     */
    public String getServiceScore(){
        return mRbServiceScore.getStartStep() + "";
    }


    interface ClickListener{
        /**
         * 提交评论
         */
        void release();
    }
}

package com.geli.m.mvp.home.index_fragment.restaurantlist_activity.main;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.geli.m.R;
import com.geli.m.bean.RestaurantBean;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.ResourceUtil;
import com.geli.m.utils.StringUtils;
import com.geli.m.utils.ToFormatUtil;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 食品馆列表
 */

public class RestaurantListViewHolder extends BaseViewHolder<RestaurantBean.DataBean.LocalFoodResBean> {

    Context mContext;

    /** 食品馆图片 */
    @BindView(R.id.iv_restaurant_restaurantVH)
    ImageView mIvRestaurant;
    /** 食品馆名字 */
    @BindView(R.id.tv_restaurant_name_restaurantVH)
    TextView mTvRestaurantName;
    /** 主营 */
    @BindView(R.id.tv_mainProducts_restaurantVH)
    TextView mTvMainProducts;
    /** 距离 */
    @BindView(R.id.tv_distance_restaurantVH)
    TextView mTvDistance;
    /** 关键词 */
    @BindView(R.id.erv_KeyWord_restaurantVH)
    EasyRecyclerView mErvKeyWord;
    /** 食品馆地址 */
    @BindView(R.id.tv_restaurant_addr_restaurantVH)
    TextView mTvAddr;

    RecyclerArrayAdapter mAdapter;

    public RestaurantListViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.vh_restaurant);
        mContext = context;
        ButterKnife.bind(this, itemView);
        setErv();
    }

    @Override
    public void setData(RestaurantBean.DataBean.LocalFoodResBean data) {
        super.setData(data);

        GlideUtils.loadImgRect(mContext, data.getImg(), mIvRestaurant);
        highlightKeyword(data.getTitle(), data.getTitle_highlight());

        if(StringUtils.isNotEmpty(data.getMarket_main())){
            mTvMainProducts.setText(ResourceUtil.getStringFromResources(R.string.main_products) + data.getMarket_main());
        }else {
            mTvMainProducts.setText("");
        }

        if(StringUtils.isNotEmpty(data.getAddress_detail())){
            mTvAddr.setText(ResourceUtil.getStringFromResources(R.string.addr) + data.getAddress_detail());
        }else {
            mTvAddr.setText("");
        }

        String distance = "<" + 0.0 + "m";
        if(data.getDistance() < 1000){
            distance = "<" + ToFormatUtil.toDecimalFormat(data.getDistance(), 1) + "m";
        }else {
            distance = "<" + ToFormatUtil.toDecimalFormat((data.getDistance()/1000f), 1) + "km";
        }
        mTvDistance.setText(distance);

        ArrayList temp = new ArrayList();
        if(StringUtils.isNotEmpty(data.getScale())){
            temp.add(data.getScale());
        }
        if(StringUtils.isNotEmpty(data.getFeature())){
            temp.add(data.getFeature());
        }

        mAdapter.clear();
        mAdapter.addAll(temp);
    }


    private void setErv(){
        mErvKeyWord.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mErvKeyWord.addItemDecoration(new DividerDecoration(Utils.getColor(R.color.transparent),
                Utils.dip2px(mContext, 10f), 0, 0));
        mErvKeyWord.setAdapterWithProgress(initAdapter());
    }

    private RecyclerView.Adapter initAdapter() {
        mAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new RestaurantListKeyWorkVH(parent, mContext);
            }
        };
        return mAdapter;
    }



    //高亮字体颜色
    private void foregroundHight(String src, String foreground) {
        // 创建一个 SpannableString对象
        SpannableString sp = new SpannableString(src);
        //设置背景颜色
        sp.setSpan(new ForegroundColorSpan(Utils.getColor(R.color.zhusediao)), 3, 5,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvRestaurantName.setText(sp);
    }



    /**
     * 高亮某个关键字，如果有多个则全部高亮
     */
    private void highlightKeyword(String src, String foreground) {

        if(src.length() > 0 && foreground.length() > 0) {
            SpannableString sp = new SpannableString(src);

            Pattern p = Pattern.compile(foreground);
            Matcher m = p.matcher(src);

            while (m.find()) {    //通过正则查找，逐个高亮
                int start = m.start();
                int end = m.end();
                sp.setSpan(new ForegroundColorSpan(Utils.getColor(R.color.zhusediao)), start, end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            mTvRestaurantName.setText(sp);
        }else {
            mTvRestaurantName.setText(src);
        }
    }
}

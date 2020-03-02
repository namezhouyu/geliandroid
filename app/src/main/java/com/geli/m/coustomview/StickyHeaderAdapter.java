package com.geli.m.coustomview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.geli.m.R;
import com.geli.m.bean.ShopInfoBean;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.StickyHeaderDecoration;
import java.util.List;
import java.util.Map;


/**
 * "粘性标题" 适配器
 */
public class StickyHeaderAdapter implements StickyHeaderDecoration.IStickyHeaderAdapter<StickyHeaderAdapter.HeaderHolder> {

    private LayoutInflater mInflater;
    private RecyclerArrayAdapter mArrayAdapter;
    /** 分类(所有的包括一二级的，普通分类，特殊分类) k:分类号， v:分类名 */
    private Map<Integer, String> mLeftTitleMap;
    /** 第一层:分类 k:什么种类分类(其他/普通)， v:分类列表... 第二层:k:分类号， v:分类名 */
    private Map<Integer, Map<Integer, String>> mLeftTitleMapMap;


    public StickyHeaderAdapter(Context context, RecyclerArrayAdapter arrayAdapter) {
        mInflater = LayoutInflater.from(context);
        mArrayAdapter = arrayAdapter;
    }

    @Override
    public long getHeaderId(int position) {
        return setHeaderId(position);           // 需要显示"标题"的项id
    }

    @Override
    public HeaderHolder onCreateHeaderViewHolder(ViewGroup parent) {
        final View view = mInflater.inflate(R.layout.itemview_shopdetails_goods_header_title, parent, false);
        return new HeaderHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderHolder viewholder, int position) {
        viewholder.header.setText(setHeaderName(position));
    }

    /**
     * 获取分类
     * @param leftTitleMap 分类(所有的包括一二级的，普通分类，特殊分类) k:分类号， v:分类名
     */
    public void setLeftTitleMap(Map<Integer, String> leftTitleMap) {
        mLeftTitleMap = leftTitleMap;
    }

    public void setLeftTitleMapMap(Map<Integer, Map<Integer, String>> leftTitleMapMap) {
        mLeftTitleMapMap = leftTitleMapMap;
    }

    class HeaderHolder extends RecyclerView.ViewHolder {
        public TextView header;

        public HeaderHolder(View itemView) {
            super(itemView);
            header = (TextView) itemView.findViewById(R.id.tv_header_item);
        }
    }

    /**
     * 根据当前的"分类类型"，在所有数据中出现早的一个，设置标题
     * @param position
     * @return
     */
    private long setHeaderId(int position){
        List<ShopInfoBean.DataEntity.GoodsResEntity> list = mArrayAdapter.getAllData();

        int gsId = list.get(position).getGs_id();
        int catId = list.get(position).getCat_id();
        int i = 0;

        for (ShopInfoBean.DataEntity.GoodsResEntity entity : list){
            if(gsId != -1){
                if(gsId == entity.getGs_id()){
                    return i;
                }
                i++;
            }else {
                if(entity.getGs_id() != -1){            // 其他分类 去掉
                    i++;
                    continue;
                }
                if (catId == entity.getCat_id()) {
                    return i;
                }
                i++;
            }
        }
        return i;
    }

    /**
     * "标题的名称"
     * @param position
     * @return
     */
    private String setHeaderName(int position){
        List<ShopInfoBean.DataEntity.GoodsResEntity> list = mArrayAdapter.getAllData();

        int gsId = list.get(position).getGs_id();
        int catId = list.get(position).getCat_id();
        int i = 0;
        String headerName = "";

        for (ShopInfoBean.DataEntity.GoodsResEntity entity : list){ // 同一类型的有多少个
            if(gsId != -1) {
                if (gsId == entity.getGs_id() && !entity.dataNUll) {
                    i++;
                }
            }else {
                if(entity.getGs_id() != -1){                // 其他分类 去掉
                    continue;
                }
                if(catId == entity.getCat_id() && !entity.dataNUll){
                    i++;
                }
            }
        }


//        if(gsId != -1) {
//            if (mLeftTitleMap != null) {
//                for (Map.Entry<Integer, String> entry : mLeftTitleMap.entrySet()) {
//                    int key = entry.getKey();
//                    String value = entry.getValue();
//                    if ((key - 100000) == gsId) {
//                        return value + (i == 0 ? "" : " (" + i + ")");
//                    }
//                }
//            }
//        }else {
//            if(mLeftTitleMap != null){
//                for(Map.Entry<Integer, String> entry : mLeftTitleMap.entrySet()){
//                    int key = entry.getKey();
//                    String value = entry.getValue();
//                    if(key == catId){
//                        return value + (i == 0 ? "" : "(" + i + ")");
//                    }
//                }
//            }
//        }

        if(gsId != -1) {
            if(mLeftTitleMapMap != null){
                Map<Integer, String> map = mLeftTitleMapMap.get(1);
                if(map != null) {
                    for (Map.Entry<Integer, String> entry : map.entrySet()) {
                        int key = entry.getKey();
                        String value = entry.getValue();
                        if (key == gsId) {
                            return value + (i == 0 ? "" : " (" + i + ")");
                        }
                    }
                }
            }
        }else {
            if(mLeftTitleMapMap != null){
                Map<Integer, String> map = mLeftTitleMapMap.get(2);
                if(map != null) {
                    for (Map.Entry<Integer, String> entry : map.entrySet()) {
                        int key = entry.getKey();
                        String value = entry.getValue();
                        if (key == catId) {
                            return value + (i == 0 ? "" : "(" + i + ")");
                        }
                    }
                }
            }
        }

        return headerName;
    }
}
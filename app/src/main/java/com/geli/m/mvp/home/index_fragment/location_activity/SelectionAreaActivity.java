package com.geli.m.mvp.home.index_fragment.location_activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.bean.City;
import com.geli.m.bean.District;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.utils.EasyRecyclerViewUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import java.util.List;

import static com.geli.m.config.Constant.AREA_NAME;

public class SelectionAreaActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.erv_select_area_lsit)
    EasyRecyclerView mErvList;
    @BindView(R.id.tv_title_name)
    TextView mTvName;

    public static final String INTENT_DATA = "intent_data";
    /** 某个城市的"区列表" */
    private List<District> mDistricts;
    private RecyclerArrayAdapter mAdapter;

    @Override
    protected int getResId() {
        return R.layout.activity_selection_area;
    }

    @Override
    protected void init() {
        super.init();
        mDistricts = getIntent().getParcelableArrayListExtra(INTENT_DATA);
    }

    @Override
    protected void initData() {
        mTvName.setText(Utils.getString(R.string.selection_area));
        mErvList.setLayoutManager(new LinearLayoutManager(mContext));
        EasyRecyclerViewUtils.initEasyRecyclerView(mErvList);

        if (null == mDistricts || mDistricts.size() == 0) {
            mErvList.showEmpty();
        } else {
            mAdapter = new RecyclerArrayAdapter(mContext,mDistricts) {
                @Override
                public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                    return new SelectAreaViewHolder(parent, mContext);
                }

            };
            mErvList.setAdapterWithProgress(mAdapter);
        }

    }

    @Override
    protected void initEvent() {
        if (mAdapter != null) {

            // 项点击事件，将选择的"区名"返回去
            mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    District district = (District) mAdapter.getItem(position);
                    City city = new City();
                    city.setArea_id(district.getArea_id());
                    city.setArea_name(district.getArea_name());
                    Intent intent = new Intent();
                    intent.putExtra(AREA_NAME, district.getArea_name());
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            });
        }
    }

    @OnClick({R.id.iv_title_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;

        }
    }
}

package com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main.local_restaurant_search_activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.bean.RestaurantBean;
import com.geli.m.bean.RestaurantHotSearchBean;
import com.geli.m.coustomview.FluidLayout;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main.LocalRestaurantListViewHolder;
import com.geli.m.mvp.home.index_fragment.localrestaurantlist_activity.main.local_restaurant_activity.LocalRestaurantActivity;
import com.geli.m.utils.EasyRecyclerViewUtils;
import com.geli.m.utils.KeyBoardUtils;
import com.geli.m.utils.RxUtils;
import com.geli.m.utils.StringUtils;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.geli.m.config.Constant.INTENT_BEAN;
import static com.geli.m.config.Constant.type_market;

/**
 * author:  shen
 * date:    2019/1/14
 * <p>
 * 本地食品 -- 搜索
 */
public class LocalRestaurantSearchActivity extends MVPActivity<LocalRestaurantSearchPresentImpl> implements LocalRestaurantSearchView {

    @BindView(R.id.ll_search_root_restaurantSearchActivity)
    LinearLayout mLlSearchRoot;
    /**
     * 编辑框 -- 搜索内容
     */
    @BindView(R.id.et_search_content_restaurantSearchActivity)
    EditText mEtSearchContent;
    /**
     * 取消
     */
    @BindView(R.id.tv_search_cancel_restaurantSearchActivity)
    TextView mTvSearchCancel;
    /**
     * 获取的列表
     */
    @BindView(R.id.erv_list_restaurantSearchActivity)
    EasyRecyclerView mErvList;
    /**
     * 包裹 -- 热门搜索流布局 -- 的布局
     */
    @BindView(R.id.ll_search_hostsearch_restaurantSearchActivity)
    LinearLayout mLlSearchHostSearch;
    /**
     * 热门搜索流布局
     */
    @BindView(R.id.fl_hostsearch_restaurantSearchActivity)
    FluidLayout mFlHostSearch;


    RecyclerArrayAdapter mAdapter;
    private int mPage = 1;
    private int mLastOffset;
    private int mLastPosition;

    @Override
    protected LocalRestaurantSearchPresentImpl createPresenter() {
        return new LocalRestaurantSearchPresentImpl(this);
    }

    @Override
    protected int getResId() {
        return R.layout.activity_restaurantsearch;
    }

    @Override
    protected void initData() {
        showResult(false);
        initErv();
        setEtSearchOnEditorActionListener();
        mPresenter.getKeywords();
    }


    @Override
    protected void initEvent() {

    }


    /**
     * 显示结果
     *
     * @param isShow
     */
    private void showResult(boolean isShow) {
        if (isShow) {
            mErvList.setVisibility(View.VISIBLE);
            mLlSearchHostSearch.setVisibility(View.GONE);
        } else {
            mErvList.setVisibility(View.GONE);
            mLlSearchHostSearch.setVisibility(View.VISIBLE);
        }
    }

    private void initErv() {
        mErvList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mErvList.setAdapterWithProgress(initAdapter());

        EasyRecyclerViewUtils.initEasyRecyclerView(mErvList, R.layout.layout_erv_empty, -1);
        setEmptyMessage();

        EasyRecyclerViewUtils.initAdapter(mAdapter, new EasyRecyclerViewUtils.AdapterListener() {
            @Override
            public void onMoreShow() {
                mPage++;
                String str = mEtSearchContent.getText().toString().trim();
                if (StringUtils.isNotEmpty(str)) {
                    getData(str, mPage);
                }
            }

            @Override
            public void onErrorClick() {
                mAdapter.resumeMore();
            }
        });


        mErvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (recyclerView.getLayoutManager() != null) {
                    getPositionAndOffset(recyclerView.getLayoutManager());
                }
            }
        });
    }

    private RecyclerArrayAdapter initAdapter() {
        mAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new LocalRestaurantListViewHolder(parent, mContext);          // 发票内容
            }
        };

        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                RestaurantBean.DataBean.LocalFoodResBean bean = (RestaurantBean.DataBean.LocalFoodResBean) mAdapter.getItem(position);
                Intent intent = new Intent();
                intent.putExtra(INTENT_BEAN, bean);
                startActivity(LocalRestaurantActivity.class, intent);
            }
        });

        return mAdapter;
    }

    /**
     * 设置列表空数据的时候的显示
     */
    private void setEmptyMessage() {
        View emptyView = LayoutInflater.from(mContext).inflate(R.layout.layout_erv_empty,
                new LinearLayout(mContext), false);     // 为了点击事件，所以这里要填充出来获取控件

        TextView message = (TextView) emptyView.findViewById(R.id.tv_message_empty);
        message.setText(getString(R.string.empty_restaurant));
    }

    @OnClick({R.id.tv_search_cancel_restaurantSearchActivity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search_cancel_restaurantSearchActivity:             /* 取消 */
                finish();
                break;
        }
    }


    /**
     * 获取到的热门搜索 -- 添加到"流式布局控件中"
     */
    private void setHostSearch(final List<RestaurantHotSearchBean.DataBean> data) {
        Observable.create(new ObservableOnSubscribe<CheckBox>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<CheckBox> e) throws Exception {

                List<String> hostSearchList = new ArrayList<>();
                for (int j = 0; j < data.size(); j++) {
                    hostSearchList.add(data.get(j).getKeywords());
                }

                List<TextView> view = mFlHostSearch.getView(hostSearchList);
                for (int j = 0; j < view.size(); j++) {
                    CheckBox checkBox = (CheckBox) view.get(j);
                    checkBox.setTag(hostSearchList.get(j));
                    e.onNext(checkBox);
                }
                e.onComplete();
            }
        }).compose(RxUtils.<CheckBox>rxSchedulerHelper())
                .subscribe(new Observer<CheckBox>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CheckBox checkBox) {
                        mFlHostSearch.addView(checkBox);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        setFluidLayoutListener();
                    }
                });
    }

    /**
     * 设置"流式布局控件"的"控件的点击监听事件" -- 搜索
     */
    private void setFluidLayoutListener() {
        FluidLayout.CheckBoxClickListener listener = new FluidLayout.CheckBoxClickListener() {
            @Override
            public void onCheckBoxClick(View v) {
                String content = (String) v.getTag();
                ((CheckBox) v).setChecked(false);
                mEtSearchContent.setText(content.trim());
                mEtSearchContent.setSelection(content.length());   // 光标移动到最后
                mPage = 1;
                getData(content.trim(), 1);
            }
        };
        mFlHostSearch.setCheckBoxClickListener(listener);
    }


    /**
     * 需要注意的是
     * setOnEditorActionListener这个方法，并不是在我们点击EditText的时候触发，
     * 也不是在我们对EditText进行编辑时触发，而是在我们编辑完之后点击软键盘上的各种键才会触发。
     * 因为通过布局文件中的imeOptions可以控制软件盘右下角的按钮显示为不同按钮。
     * 所以和EditorInfo搭配起来可以实现各种软键盘的功能。
     * <p>
     * imeOptions=”actionSearch” (xml布局中的设置) –> EditorInfo.IME_ACTION_SEARCH    这是对应的!!
     */
    private void setEtSearchOnEditorActionListener() {
        mEtSearchContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String search = mEtSearchContent.getText().toString().trim();
                    if (TextUtils.isEmpty(search)) {
                        ToastUtils.showToast(Utils.getString(R.string.please_input_yousearch));
                        return false;
                    }
                    mPage = 1;
                    getData(search, 1);
                    KeyBoardUtils.closeKeybord(mEtSearchContent, mContext);
                    return true;
                }
                return false;       //  返回false表示点击后，隐藏软键盘。返回true表示保留软键盘。
            }
        });
    }

    @Override
    public void setHotSearch(List<RestaurantHotSearchBean.DataBean> data) {
        setHostSearch(data);
    }

    @Override
    public void setRestaurantShop(RestaurantBean.DataBean bean) {
        if (bean == null) {
            return;
        }

        List<RestaurantBean.DataBean.LocalFoodResBean> data = bean.getLocal_food_res();

        if (mPage == 1) {
            mAdapter.clear();
            if (data != null && data.size() == 0) {
                mErvList.showEmpty();
                return;
            }
        }

        if (data != null) {
            mAdapter.addAll(data);

            scrollToPosition();

            if (data.size() < 20) {       // 本次回的数据小余 20 说明后面没有数据了
                mAdapter.stopMore();
            }

        }

        showResult(data != null && (data.size() > 0));
    }


    @Override
    public void showLoading() {
        Utils.uProgressDialog(mContext);
    }

    @Override
    public void hideLoading() {
        Utils.dismissProgress();
    }


    /**
     * 记录RecyclerView当前位置
     *
     * @param layoutManager
     */
    private void getPositionAndOffset(RecyclerView.LayoutManager layoutManager) {

        View topView = layoutManager.getChildAt(0);         // 获取可视的第一个view
        if (topView != null) {
            mLastOffset = topView.getTop();                       // 获取与该view的顶部的偏移量
            mLastPosition = layoutManager.getPosition(topView);   // 得到该View的数组位置
        }
    }

    /**
     * 让RecyclerView滚动到指定位置
     */
    private void scrollToPosition() {
        if (mErvList.getRecyclerView().getLayoutManager() != null && mLastPosition >= 0) {
            ((LinearLayoutManager) mErvList.getRecyclerView().getLayoutManager())
                    .scrollToPositionWithOffset(mLastPosition, mLastOffset);
        }
    }


    private void getData(String searchStr, int page) {
        Map<String, String> map = new HashMap<>();
        map.put("keywords", searchStr);
        map.put("page", page + "");
        map.put("type", type_market);

        mPresenter.localFoodList(map);
    }
}

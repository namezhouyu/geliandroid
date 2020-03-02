package com.geli.m.mvp.home.index_fragment.location_activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.geli.m.R;
import com.geli.m.bean.AreaBean;
import com.geli.m.bean.City;
import com.geli.m.bean.District;
import com.geli.m.coustomview.SideBar;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.utils.KeyBoardUtils;
import com.geli.m.utils.LocationUtil;
import com.geli.m.utils.PinYinUtil;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.tbruyelle.rxpermissions2.RxPermissions;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.geli.m.config.Constant.AREA_NAME;
import static com.geli.m.config.Constant.INTENT_LOCATION;

/**
 * 定位，即搜索地区
 */
public class LocationActivity extends BaseActivity implements View.OnClickListener {

    /** 当前的定位 */
    @BindView(R.id.tv_location)
    TextView mTvCurrLocation;
    @BindView(R.id.iv_location_clear)
    ImageView mIvClear;
    /** 搜索 -- 编辑框 */
    @BindView(R.id.et_location_search)
    EditText mEtSearch;

    public final static int SELECT_ADDRESS_REQUEST_CODE = 1;

    /** 所有城市的集合 */
    public List<City> mLists;

    /** 区列表，选择城市后赋值 */
    private ArrayList<District> mDistricts;
    /** 排序(比较器/排序器) */
    private ComparatorUser comparator = new ComparatorUser();

    /** 侧边滑动栏 */
    @BindView(R.id.sidrbar)
    SideBar sideBar;
    /** 弹出的字母 */
    @BindView(R.id.tv_dialog)
    TextView TvDialog;
    @BindView(R.id.erv_city)
    EasyRecyclerView mErv;

    /** 目标项是否在最后一个可见项之后 */
    private boolean mShouldScroll;
    /** 记录目标项位置 */
    private int mToPosition;

    private LinearLayoutManager mLinearLayoutManager;

    public RecyclerArrayAdapter mAdapter;

    private AreaBean mAreaBean;

    @Override
    protected int getResId() {
        return R.layout.activity_location;
    }


    @Override
    protected void init() {
        super.init();
        mImmersionBar.keyboardEnable(false).init();

        // 注意这里传过来的只有名字没有id
        mAreaBean = getIntent().getParcelableExtra(INTENT_LOCATION);
    }

    @Override
    protected void initData() {
        mAreaBean = new AreaBean();
        mLists = new ArrayList<>();
        mDistricts = new ArrayList<>();

        new MyAsyncTask().execute();

        initErv();
        initSideBar();
    }

    @Override
    protected void initEvent() {
        mEtSearch.addTextChangedListener(new textViewWatcher());
    }



    private void initErv(){
        mLinearLayoutManager = new LinearLayoutManager(this);
        mErv.setLayoutManager(mLinearLayoutManager);
        mErv.setAdapter(mAdapter = new RecyclerArrayAdapter(this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new LocationViewHolder(parent, mContext);
            }
        });
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.itemview_location, new LinearLayout(mContext), false);
        inflate.findViewById(R.id.initials_tv).setVisibility(View.VISIBLE);
        ((TextView) inflate.findViewById(R.id.city_name_tv)).setText(Utils.getString(R.string.location_nodata));
        mErv.setEmptyView(inflate);

        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {    // 点击了那个项
            @Override
            public void onItemClick(int position) {
                List<City> allData = mAdapter.getAllData();
                City item = allData.get(position);
                mTvCurrLocation.setText(item.getArea_name());
                mDistricts = item.getDistrict_list();
                if(mAreaBean != null){
                    mAreaBean.setCityName(item.getArea_name());
                    mAreaBean.setId(item.getArea_id());
                }
            }
        });

        // 滑动监听 -- 拿到第一个可见的城市的拼音，设置下右边的"拼音导航"
        mErv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                City city = (City) mAdapter.getAllData().get(firstVisibleItemPosition);
                String str = new PinYinUtil().getStringPinYin(city.getArea_name()).toUpperCase().substring(0, 1);
                sideBar.setSelect(str);
            }
        });

        mErv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                KeyBoardUtils.closeKeybord(mEtSearch, mContext);
                return false;
            }
        });
    }

    private void initSideBar(){
        sideBar.setTextView(TvDialog);
        // 设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                KeyBoardUtils.closeKeybord(mEtSearch, mContext);
                // 该字母首次出现的位置
                City city = null;
                String firstWord;
                PinYinUtil pinYinUtil = new PinYinUtil();
                int allSize = mAdapter.getItemCount() - 1;          // 这里 减一 是减去头部的item
                for (int i = 0; i < allSize; i++) {
                    city = (City) mAdapter.getAllData().get(i);
                    firstWord = pinYinUtil.getStringPinYin(city.getArea_name()).toUpperCase().substring(0, 1);
                    if (s.equals(firstWord)) {          // 右侧索引栏选中字母在左侧数据集里面出现的的第一个位置
                        mLinearLayoutManager.scrollToPositionWithOffset(i, 0);//跳到该位置显示
                        // mLinearLayoutManager.setStackFromEnd(true);
                        return;
                    }
                }
            }
        });
    }





    /**
     * 异步加载，本地数据 -- 城市
     */
    class MyAsyncTask extends AsyncTask<String, Integer, List<City>> {

        @Override
        protected List<City> doInBackground(String... params) {
            final Gson gson = new Gson();
            String s = getFromAssets("city.json");
            List<City> citys = new ArrayList<>();
            try {
                citys = gson.fromJson(s, new TypeToken<List<City>>() {}.getType());
            } catch (Exception e) {
            }

            return citys;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List<City> cities) {
            super.onPostExecute(cities);
            Collections.sort(cities, comparator);       // 排序 -- 上网找下方法优化下 -- gc了 -- 手机不好的要3秒
            mLists = cities;
            mAdapter.clear();
            mAdapter.addAll(cities);
            if (TextUtils.isEmpty(mAreaBean.getCityName())) {
                requestPermissions();
            } else {
                mTvCurrLocation.setText(mAreaBean.getCityName());
                List<City> c = accurateSearchId(mAreaBean.getId(), mLists);
                for (City cityt : c) {
                    if (cityt.getArea_id().equals(mAreaBean.getId())) {
                        mDistricts = cityt.getDistrict_list();
                        return;
                    }
                }
            }
        }
    }


    /**
     * 定位，申请权限
     */
    private void requestPermissions() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            mTvCurrLocation.setText(Utils.getString(R.string.locationloading));
                            initLocal();
                        } else {
                            mTvCurrLocation.setText(Utils.getString(R.string.location_openlocapermission));
                            Utils.showMissPermissionDialog(mContext, Utils.getString(R.string.location), Utils.getString(R.string.message_location));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 返回结果给上一个页面
     */
    private void returnResult(AreaBean bean) {
        Intent intent = new Intent();
        intent.putExtra(INTENT_LOCATION, mAreaBean);
        setResult(Activity.RESULT_OK, intent);
        KeyBoardUtils.closeKeybord(mEtSearch, mContext);
        finish();
    }

    /**
     * 获取本地json数据 -- Assets中
     * @param fileName
     * @return
     */
    public String getFromAssets(String fileName) {
        String result = "";
        try {
            InputStream in = getResources().getAssets().open(fileName);
            int lenght = in.available();                // 获取文件的字节数
            byte[] buffer = new byte[lenght];           // 创建byte数组
            in.read(buffer);                            // 将文件中的数据读到byte数组中
            result = new String(buffer, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 初始化高德定位相关信息.
     */
    private void initLocal() {
        LocationUtil.getInstance().startLocation(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == aMapLocation.LOCATION_SUCCESS) {//定位成功

                        mAreaBean = new AreaBean();
                        mAreaBean.setCityName(aMapLocation.getCity());
                        mAreaBean.setLa(aMapLocation.getLatitude() + "");
                        mAreaBean.setLo(aMapLocation.getLongitude() + "");
//                        mAreaBean.setAddress(aMapLocation.getProvince() +
//                                aMapLocation.getCity() +
//                                aMapLocation.getStreet() +
//                                aMapLocation.getStreetNum());

                        String address = aMapLocation.getCity() + aMapLocation.getDistrict();
                        mAreaBean.setAddress(address);
                        // mTvCurrLocation.setText(mAreaBean.getCityName());
                        mTvCurrLocation.setText(address);
                        List<City> c = accurateSearch(mAreaBean.getCityName(), mLists);
                        for (City cityt : c) {
                            if (cityt.getArea_name().equals(mAreaBean.getCityName())) {
                                mDistricts = cityt.getDistrict_list();
                                mAreaBean.setId(cityt.getArea_id());
                                return;
                            }
                        }
                    }
                }
                mTvCurrLocation.setText(Utils.getString(R.string.location_nogetloca));
            }
        });
    }


    @OnClick({R.id.tv_location_selectarea, R.id.iv_location_back, R.id.iv_location_clear})
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_location_selectarea:
                if (TextUtils.isEmpty(mAreaBean.getCityName())) {
                    ToastUtils.showToast(Utils.getString(R.string.location_wait));
                    return;
                }
                Intent intent = new Intent(mContext, SelectionAreaActivity.class);
                intent.putParcelableArrayListExtra(SelectionAreaActivity.INTENT_DATA, mDistricts); // 将选定的"城市"的"区列表"
                startActivityForResult(intent, LocationActivity.SELECT_ADDRESS_REQUEST_CODE);
                break;


            case R.id.iv_location_back:                             /* 返回 */
                returnResult(mAreaBean);
                break;

            case R.id.iv_location_clear:                            /* 编辑框的清空按钮 */
                clearSearch();
                break;

            default:
                break;
        }
    }

    private void clearSearch() {
        mEtSearch.setText("");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            returnResult(mAreaBean);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LocationActivity.SELECT_ADDRESS_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            String cityName = data.getStringExtra(AREA_NAME);
            getAddAndLaLo(cityName);
        }
    }

    /**
     * 根据"区名/城市名" 使用"高德地图"重新获取"经纬度"
     * @param cityName
     */
    public void getAddAndLaLo(final String cityName) {
        if (mLists != null) {
            String code = "";
            String searchName = "";
            for (City chiled : mLists) {
                if (!TextUtils.isEmpty(mAreaBean.getCityName()) && chiled.getArea_id().equals(mAreaBean.getId())) {
                    for (District district : chiled.getDistrict_list()) {
                        if (district.getArea_name().equals(cityName)) {
                            code = district.getCode();
                            searchName = chiled.getArea_name() + district.getArea_name() + cityName;
                        }
                    }
                }
            }

            /* 高德地图，根据"这个区名字"获取"经纬度" */
            if (!TextUtils.isEmpty(code)) {
                GeocodeSearch geocodeSearch = new GeocodeSearch(mContext);
                geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
                    @Override
                    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                        mAreaBean = new AreaBean();
                        mAreaBean.setLa(regeocodeResult.getRegeocodeQuery().getPoint().getLatitude() + "");
                        mAreaBean.setLo(regeocodeResult.getRegeocodeQuery().getPoint().getLongitude() + "");

                        String addrssStr = regeocodeResult.getRegeocodeAddress().getCity() +
                                regeocodeResult.getRegeocodeAddress().getDistrict();
                        mAreaBean.setAddress(addrssStr);
                        // mAreaBean.setAddress(regeocodeResult.getRegeocodeAddress().getFormatAddress());
                        mAreaBean.setCityName(cityName);

                        returnResult(mAreaBean);
                    }

                    @Override
                    public void onGeocodeSearched(GeocodeResult geocResult, int i) {
                        try {
                            GeocodeAddress address = geocResult.getGeocodeAddressList().get(0);

                            mAreaBean = new AreaBean();
                            mAreaBean.setLa(address.getLatLonPoint().getLatitude() + "");
                            mAreaBean.setLo(address.getLatLonPoint().getLongitude() + "");

                            String addrssStr = address.getCity() + address.getDistrict();
                            mAreaBean.setAddress(addrssStr);
                            // mAreaBean.setAddress(address.getFormatAddress());
                            mAreaBean.setCityName(cityName);

                            returnResult(mAreaBean);
                        } catch (Exception e) {
                            returnResult(mAreaBean);
                        }
                    }
                });
                GeocodeQuery quert = new GeocodeQuery(searchName, code);
                geocodeSearch.getFromLocationNameAsyn(quert);
            }
        }
    }

    /**
     * 模糊查询，用于用户输入查询
     * @param name
     * @param list
     * @return
     */
    public List<City> search(String name, List<City> list) {
        PinYinUtil pinYinUtil = new PinYinUtil();

        String pinyin = pinYinUtil.getStringPinYin(name);                       // 把输入结果转为拼音
        List results = new ArrayList();

        try {
            Pattern pattern = Pattern.compile(pinyin, Pattern.CASE_INSENSITIVE);    // 忽略大小写
            for (int i = 0; i < list.size(); i++) {
                String string = pinYinUtil.getStringPinYin((list.get(i)).getArea_name());
                Matcher matcher = pattern.matcher(string);
                if (matcher.find()) {
                    results.add(list.get(i));
                }
            }
        }catch (Exception e){
            return new ArrayList();
        }

        return results;
    }


    /**
     * 精确查询,用于根据定位结果查找本地数据集中的城市 -- 根据"名字"
     * @param name
     * @param list
     * @return
     */
    public List accurateSearch(String name, List<City> list) {
        List results = new ArrayList();
        for (City city : list) {
            if (city.getArea_name().equals(name)) {
                results.add(city);
            }
        }
        return results;
    }

    /**
     * 精确查询,用于根据定位结果查找本地数据集中的城市 -- 根据"id"
     * @param id
     * @param list
     * @return
     */
    public List accurateSearchId(String id, List<City> list) {
        List results = new ArrayList();
        for (City city : list) {
            if (city.getArea_id().equals(id)) {
                results.add(city);
            }
        }
        return results;
    }


    /**
     * 输入框"文本变化监听"
     */
    class textViewWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String input = s.toString().trim();

            if (TextUtils.isEmpty(input)) {                 /* 用户输入为空时，显示所有的城市 */
                mIvClear.setVisibility(View.GONE);
                mAdapter.clear();
                mAdapter.addAll(mLists);

            } else {
                mIvClear.setVisibility(View.VISIBLE);
                List<City> search = search(input, mLists);
                if (search.size() == 0) {
                    mErv.showEmpty();
                } else {
                    mAdapter.clear();
                    mAdapter.addAll(search);
                }
            }
        }
    }


    /**
     * 排序(比较器/排序器) -- 按"城市拼音"首字母
     */
    public static class ComparatorUser implements Comparator<City> {

        @Override
        public int compare(City one, City two) {
            String city1Name = one.getArea_name();
            String city2Name = two.getArea_name();
            //            String oneName = new ChineseInital().(one.getArea_name()).substring(0, 1);
            //            String twoName = new ChineseInital().String2Alpha(two.getArea_name()).substring(0, 1);

            //            String oneName = ChineseInital.getFirstLetter(city1Name);
            //            String twoName = ChineseInital.getFirstLetter(city2Name);

            String oneName = new PinYinUtil().getStringPinYin(city1Name);
            String twoName = new PinYinUtil().getStringPinYin(city2Name);

            if ("#".equals(oneName) || "#".equals(twoName)) { // 把"#"排到后面，如果时繁体字或者生僻字就是“#”
                return twoName.compareTo(oneName);
            }
            int flag = oneName.compareTo(twoName);
            return flag;
        }
    }

}

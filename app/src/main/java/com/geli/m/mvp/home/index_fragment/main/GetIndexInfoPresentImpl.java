package com.geli.m.mvp.home.index_fragment.main;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.ViewGroup;
import com.geli.m.R;
import com.geli.m.api.ApiEngine;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.AreaBean;
import com.geli.m.bean.AtsBean;
import com.geli.m.bean.BrandBean;
import com.geli.m.bean.FactoryBean;
import com.geli.m.bean.IndexBean;
import com.geli.m.bean.InterestGoodsBean;
import com.geli.m.bean.NvaBean;
import com.geli.m.bean.RecommendGoodsBean;
import com.geli.m.bean.SellBean;
import com.geli.m.bean.base.IndexBaseBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;
import com.geli.m.mvp.home.index_fragment.view_holder_fragment.adv1.AdvertsingViewHolder1;
import com.geli.m.mvp.home.index_fragment.view_holder_fragment.adv2.AdvertsingViewHolder2;
import com.geli.m.mvp.home.index_fragment.view_holder_fragment.adv3.AdvertsingViewHolder3;
import com.geli.m.mvp.home.index_fragment.view_holder_fragment.banner.BannerViewHolder;
import com.geli.m.mvp.home.index_fragment.view_holder_fragment.banner1.Banner1ViewHolder;
import com.geli.m.mvp.home.index_fragment.view_holder_fragment.factory_direct.FactoryDirectViewHolder;
import com.geli.m.mvp.home.index_fragment.view_holder_fragment.factory_direct1or2.FactoryDirectViewHolder1or2;
import com.geli.m.mvp.home.index_fragment.view_holder_fragment.factory_direct3.FactoryDirectViewHolder3;
import com.geli.m.mvp.home.index_fragment.view_holder_fragment.factory_direct4.FactoryDirectViewHolder4;
import com.geli.m.mvp.home.index_fragment.view_holder_fragment.goods_product1.GoodsProductViewHolder1;
import com.geli.m.mvp.home.index_fragment.view_holder_fragment.goods_product2.GoodsProductViewHolder2;
import com.geli.m.mvp.home.index_fragment.view_holder_fragment.goods_product3.GoodsProductViewHolder3;
import com.geli.m.mvp.home.index_fragment.view_holder_fragment.may_you_like.MayYouLikeViewHolder;
import com.geli.m.mvp.home.index_fragment.view_holder_fragment.nav.NvaViewHolder;
import com.geli.m.mvp.home.index_fragment.view_holder_fragment.retail_center.RetailCenterViewHolder;
import com.geli.m.utils.ACache;
import com.geli.m.utils.RxUtils;
import com.geli.m.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.geli.m.config.Constant.Location_Key;

/**
 * Created by Steam_l on 2017/12/21.
 */

public class GetIndexInfoPresentImpl extends BasePresenter<IndexView, GetIndexInfoModelImpl> {

    /**
     * "获取缓存数据"观察者
     */
    private Observable<IndexBean> mCacheObservable;
    private String mKey_cache = "index_cachedata";


    /* 设置变量 */
    /**
     * 可重试次数
     */
    private int maxConnectCount = 5;
    /**
     * 当前已重试次数
     */
    private int currentRetryCount = 0;
    /**
     * 重试等待时间
     */
    private int waitRetryTime = 0;
    /**
     * 是否通过网络加载
     */
    private boolean isLoadNetword = true;


    @Override
    protected GetIndexInfoModelImpl createModel() {
        return new GetIndexInfoModelImpl();
    }


    /**
     * 构造函数  -- 一实例化，初始化"获取缓存数据"观察者 -- 用于下面的合并
     *
     * @param mvpView
     */
    public GetIndexInfoPresentImpl(IndexView mvpView) {
        super(mvpView);

        mCacheObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                String string = ACache.get(GlobalData.getInstance()).getAsString(mKey_cache);  // 获取缓存数据
                if (!TextUtils.isEmpty(string)) {              /* 有缓存数据 */
                    e.onNext(string);
                    isLoadNetword = false;
                }
                e.onComplete();
            }
        })
                .map(new Function<String, IndexBean>() {
                    @Override
                    public IndexBean apply(@NonNull String s) throws Exception {
                        return parseIndexData(s);
                    }
                })
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends IndexBean>>() {
                    @Override
                    public ObservableSource<? extends IndexBean> apply(@NonNull Throwable throwable) throws Exception {
                        return Observable.never();
                    }
                });
    }


    public void getIndexInfo(AreaBean areaBean) {
        if (areaBean != null) {
            Map data = new HashMap();
            for (String value : Location_Key) {
                String dataValue = "";

                if (value.equals("district")) {                 /* district:区域 */
                    dataValue = areaBean.getAddress();

                } else if (value.equals("latitude")) {          /* latitude:纬度 */
                    dataValue = areaBean.getLa();

                } else if (value.equals("longitude")) {         /* longitude:经度 */
                    dataValue = areaBean.getLo();
                }

                if (!TextUtils.isEmpty(dataValue)) {
                    data.put(value, dataValue);
                }
            }
            getIndexInfoNet(data);
        }
    }


    /**
     * 获取 -- 首页的数据
     *
     * @param data 根据地理位置(获取首页信息) -- 如: district:区域; latitude:纬度; longitude:经度
     */
    public void getIndexInfoNet(Map<String, String> data) {
        isLoadNetword = false;
        currentRetryCount = 0;
        clearDisposable();

        ApiEngine.getInstance().getApiService()
                .getIndexInfo(data)
//        ApiTestEngine.getInstance().getApiService()
//                .getTestBad()
                //                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends HomeShopPhoto>>() {
                //                    @Override
                //                    public ObservableSource<? extends HomeShopPhoto> apply(@NonNull Throwable throwable) throws Exception {
                //                        return Observable.error(throwable);
                //                    }
                //                })
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {

                    /* 请求失败 */
                    @Override
                    public ObservableSource<?> apply(@NonNull Observable<Throwable> throwableObservable) throws Exception {
                        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {
                                if (throwable instanceof IOException) {
                                    if (currentRetryCount < maxConnectCount) {  /* 记录重试次数 */
                                        currentRetryCount++;
                                        // System.out.println("重试次数 = " + currentRetryCount);
                                        /**
                                         * 需求2：实现重试
                                         * 通过返回的Observable发送的事件 = Next事件，从而使得retryWhen（）重订阅，最终实现重试功能
                                         *
                                         * 需求3：延迟1段时间再重试
                                         * 采用delay操作符 = 延迟一段时间发送，以实现重试间隔设置
                                         *
                                         * 需求4：遇到的异常越多，时间越长
                                         * 在delay操作符的等待时间内设置 = 每重试1次，增多延迟重试时间1s
                                         */
                                        waitRetryTime = 1000 + currentRetryCount * 1000;        // 设置等待时间
                                        return Observable.just(1).delay(waitRetryTime, TimeUnit.MILLISECONDS);
                                    }
                                }

                                isLoadNetword = true;           // 报错,设置为true -- 因为是通过网络加载的
                                return Observable.error(throwable);
                            }
                        });
                    }
                })
                /* 如果加载网络没报错就会走这 -- 报错就上面的重试 */
                .flatMap(new Function<ResponseBody, ObservableSource<IndexBean>>() {
                    @Override
                    public ObservableSource<IndexBean> apply(@NonNull ResponseBody homeShopPhoto) throws Exception {
                        isLoadNetword = true;
                        String string = homeShopPhoto.string();             // 拿到"请求的字符串"
                        setCache(string);                                   // 将数据缓存起来
                        IndexBean indexBean = parseIndexData(string);       // 解析
                        return Observable.just(indexBean);
                    }
                })
                .publish(new Function<Observable<IndexBean>, ObservableSource<IndexBean>>() {
                    @Override
                    public ObservableSource<IndexBean> apply(@NonNull Observable<IndexBean> nestword) throws Exception {
                        /* 将两个观察点变成一个单一的观察源，没有任何转变
                            你可以将多个观察点发出的物品组合起来，这样它们就可以作为一个单独的观察源出现。*/
                        // 第一个"Observable"是"网络获取的"！
                        // 第二个"Observable"是"获取缓存的"！

                        return Observable.merge(nestword,
                                mCacheObservable.subscribeOn(Schedulers.io()).takeUntil(nestword));
                    }
                })
                .compose(RxUtils.<IndexBean>rxSchedulerHelper())
                .subscribe(indexBeanObserver());
    }

    /**
     * 缓存数据
     *
     * @param string 要缓存的数据
     */
    // @SuppressLint("CheckResult")
    private void setCache(String string) {
        Observable.just(string)                             // 设置下"缓存"
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                               @Override
                               public void onSubscribe(Disposable d) {

                               }

                               @Override
                               public void onNext(String s) {
                                   if (GlobalData.getInstance() != null) {
                                       ACache.get(GlobalData.getInstance())
                                               .put(mKey_cache, s, 60 * 60 * 24 * 7);
                                   }
                               }

                               @Override
                               public void onError(Throwable e) {

                               }

                               @Override
                               public void onComplete() {

                               }
                           }
                );
    }


    /**
     * 观察者，等人家订阅 -- 获取数据成功了，解析也成功了
     *
     * @return
     */
    private BaseObserver<IndexBean> indexBeanObserver() {
        return new BaseObserver<IndexBean>(this, mvpView) {
            @Override
            protected void onEnd() {
                super.onEnd();
                if (isLoadNetword) {
                    mvpView.hideLoading();     // 避免第一次刷新缓存拿到后隐藏refresh控件
                }
            }

            @Override
            protected void onSuccess(final IndexBean data) {
                // System.out.println(Thread.currentThread().getName() + "========显示数据=====" + data.getData().getShop_info().getShop_list().get(0).getShop_name() + "===============");
                if (isLoadNetword) {
                    mvpView.onSuccess(Utils.getString(R.string.refresh_success));
                }
                mvpView.showIndexInfo(data.getAllList());
            }

        };
    }


    /**
     * 解析数据
     *
     * @param string
     * @return
     * @throws JSONException
     * @throws IOException
     */
    @android.support.annotation.NonNull
    private IndexBean parseIndexData(@NonNull String string) throws JSONException, IOException {

        JSONObject jsonObject = new JSONObject(string);

        IndexBean indexBean = new IndexBean();
        indexBean.setCode(jsonObject.getInt(Constant.KEY_CODE));                    // 返回码 -- "code"
        indexBean.setMessage(jsonObject.getString(Constant.KEY_MESSAGE));           // 返回消息 -- "message"

        if (jsonObject.getInt(Constant.KEY_CODE) == Constant.REQUEST_OK) {
            JSONArray jsonArray = jsonObject.getJSONArray(Constant.KEY_DATA);      // 返回的 -- "data" -- 应该是数组类型
            List<IndexBaseBean> allList = new ArrayList();

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);                        /* 这是个类 */
                String view_key = jsonObject.getString(Constant.KEY_VIEWKEY);   // view_key
                int view_type = jsonObject.getInt(Constant.KEY_VIEWTYPE);       // view_type
                String title = jsonObject.getString(Constant.KEY_TITLE);        // model_title  -- 模块标题
                int isshow = jsonObject.getInt(Constant.KEY_TITLE_ISSHOW);      // title_is_show -- 是否显示"标题"
                String json = jsonObject.getString(Constant.KEY_DATA);          // 这个"类"的 data
                setData(view_key, view_type, title, isshow, json, allList);     // 将数据解析(模块)，添加到"List<IndexBaseBean>"列表中
            }

//            // 还在前面插入一个 "模块"
//            if (allList.size() > 1) {
//                List<Integer> integerList = new ArrayList<>();
//                integerList.add(Other_Piling);
//                integerList.add(Other_Restaurant);
//                integerList.add(Other_ChangJia);
//                integerList.add(Other_Overseas);
//                integerList.add(Other_JinRong);
//                integerList.add(Other_Logistics);
//                IndexBaseBean element = new IndexBaseBean(0,
//                        Constant.Viewkey.ORTHER, "", 1, integerList);
//                allList.add(1, element);
//            }

            indexBean.setMessage(Utils.getString(R.string.refresh_success));
            indexBean.setAllList(allList);
        }

        return indexBean;
    }

    /**
     * 将数据解析(模块)，添加到"List<IndexBaseBean>"列表中
     *
     * @param view_key  模块类型
     * @param view_type
     * @param title
     * @param isShow
     * @param json
     * @param allList
     */
    public void setData(String view_key, int view_type, String title, int isShow, String json, List<IndexBaseBean> allList) {
        Gson gson = new Gson();

        switch (view_key) {
            case Constant.Viewkey.ADV: {                    /* 广告 */
                List<AtsBean> list = gson.fromJson(json, new TypeToken<List<AtsBean>>() {
                }.getType());
                IndexBaseBean<List<AtsBean>> advBeanIndexBaseBean = new IndexBaseBean<>(view_type, view_key, title, isShow, list);
                allList.add(advBeanIndexBaseBean);
            }
            break;

            case Constant.Viewkey.ATS: {                    /* 轮播图 */
                List<AtsBean> list = gson.fromJson(json, new TypeToken<List<AtsBean>>() {
                }.getType());
                IndexBaseBean<List<AtsBean>> atsBeanIndexBaseBean = new IndexBaseBean<>(view_type, view_key, title, isShow, list);
                allList.add(atsBeanIndexBaseBean);
            }
            break;

            case Constant.Viewkey.BRAND: {                  /* 品牌街 */
                List<BrandBean> list = gson.fromJson(json, new TypeToken<List<BrandBean>>() {
                }.getType());
                IndexBaseBean<List<BrandBean>> brandBean = new IndexBaseBean<>(view_type, view_key, title, isShow, list);
                allList.add(brandBean);
            }
            break;

            case Constant.Viewkey.FACTORY: {                /* 厂家直供 */
                List<FactoryBean> list = gson.fromJson(json, new TypeToken<List<FactoryBean>>() {
                }.getType());
                IndexBaseBean<List<FactoryBean>> factoryBeanIndexBaseBean = new IndexBaseBean<>(view_type, view_key, title, isShow, list);
                allList.add(factoryBeanIndexBaseBean);
            }
            break;

            case Constant.Viewkey.INTEREST_GOODS: {         /* 推荐商品 */
                List<InterestGoodsBean> list = gson.fromJson(json, new TypeToken<List<InterestGoodsBean>>() {
                }.getType());
                IndexBaseBean<List<InterestGoodsBean>> interestGoodsBeanIndexBaseBean = new IndexBaseBean<>(view_type, view_key, title, isShow, list);
                allList.add(interestGoodsBeanIndexBaseBean);
            }
            break;

            case Constant.Viewkey.RECOMMEND_GOODS: {        /* 商品推荐 */
                List<RecommendGoodsBean> list = gson.fromJson(json, new TypeToken<List<RecommendGoodsBean>>() {
                }.getType());
                IndexBaseBean<List<RecommendGoodsBean>> recommendGoodsBeanIndexBaseBean = new IndexBaseBean<>(view_type, view_key, title, isShow, list);
                allList.add(recommendGoodsBeanIndexBaseBean);
            }
            break;

            case Constant.Viewkey.SELL: {                   /* 批零中心 */
                List<SellBean> list = gson.fromJson(json, new TypeToken<List<SellBean>>() {
                }.getType());
                IndexBaseBean<List<SellBean>> sellBeanIndexBaseBean = new IndexBaseBean<>(view_type, view_key, title, isShow, list);
                allList.add(sellBeanIndexBaseBean);
            }
            break;

            case Constant.Viewkey.NAV: {                   /* 导航 */
                List<NvaBean> list = gson.fromJson(json, new TypeToken<List<NvaBean>>() {
                }.getType());
                IndexBaseBean<List<NvaBean>> nvaBeanIndexBaseBean = new IndexBaseBean<>(view_type, view_key, title, isShow, list);
                allList.add(nvaBeanIndexBaseBean);
            }
            break;

            default:
                break;
        }
    }


    /* 获取每个模块的类型 */

    //        05-18 06:28:38.177 3002-3002/com.shen.mytest I/shen: MODE_MASK : 448
    //        05-18 06:28:38.177 3002-3002/com.shen.mytest I/shen: makeIndexSpec(2, 3) : 3
    //        05-18 06:28:38.177 3002-3002/com.shen.mytest I/shen: getIndexViewKey(2) : 0
    //        05-18 06:28:38.177 3002-3002/com.shen.mytest I/shen: getIndexViewType(3) : 3

    //        05-18 06:28:38.177 3002-3002/com.shen.mytest I/shen: bannerFragment : 0
    //        05-18 06:28:38.177 3002-3002/com.shen.mytest I/shen: advFragment : 64
    //        05-18 06:28:38.177 3002-3002/com.shen.mytest I/shen: sellFragment : 128
    //        05-18 06:28:38.177 3002-3002/com.shen.mytest I/shen: recomGoodsFragment : 192
    //        05-18 06:28:38.177 3002-3002/com.shen.mytest I/shen: interGoodsFragment : 256
    //        05-18 06:28:38.177 3002-3002/com.shen.mytest I/shen: brandFragment : 320
    //        05-18 06:28:38.177 3002-3002/com.shen.mytest I/shen: factroyFragment : 384
    //        05-18 06:28:38.177 3002-3002/com.shen.mytest I/shen: ortherFragment : 448

    //有X个板块就X0,最多8个,超出另搞算法
    public static final int MODE_SHIFT = 70;
    public static final int MODE_MASK = 0x7 << MODE_SHIFT;

    public int makeIndexSpec(int viewKey, int viewType) {
        return (viewKey & MODE_MASK) | (viewType & ~MODE_MASK);
    }

    public int getIndexViewKey(int mode) {
        return (mode & MODE_MASK);
    }

    public int getIndexViewType(int mode) {
        return (mode & ~MODE_MASK);
    }


    /**
     * 轮播图
     */
    public static final int bannerFragment = 0 << MODE_SHIFT;
    /**
     * 广告
     */
    public static final int advFragment = 1 << MODE_SHIFT;
    /**
     * 批零中心
     */
    public static final int sellFragment = 2 << MODE_SHIFT;
    /**
     * 商品推荐
     */
    public static final int recomGoodsFragment = 3 << MODE_SHIFT;
    /**
     * 推荐商品
     */
    public static final int interGoodsFragment = 4 << MODE_SHIFT;
    /**
     * 品牌街
     */
    public static final int brandFragment = 5 << MODE_SHIFT;
    /**
     * 厂家直供
     */
    public static final int factroyFragment = 6 << MODE_SHIFT;
//    /** 其他 */
//    public static final int ortherFragment = 7 << MODE_SHIFT;
    /**
     * 导航
     */
    public static final int navFragment = 7 << MODE_SHIFT;

    @Nullable
    public Integer getViewType(IndexBaseBean indexBaseBean) {
        int view_type = Integer.valueOf(indexBaseBean.getView_type());
        String view_key = indexBaseBean.getView_key();
        if (view_key.equals(Constant.Viewkey.ATS)) {
            return makeIndexSpec(bannerFragment, view_type);
        } else if (view_key.equals(Constant.Viewkey.FACTORY)) {
            return makeIndexSpec(factroyFragment, view_type);
        } else if (view_key.equals(Constant.Viewkey.ADV)) {
            return makeIndexSpec(advFragment, view_type);
        } else if (view_key.equals(Constant.Viewkey.SELL)) {
            return makeIndexSpec(sellFragment, view_type);
        } else if (view_key.equals(Constant.Viewkey.RECOMMEND_GOODS)) {
            return makeIndexSpec(recomGoodsFragment, view_type);
        } else if (view_key.equals(Constant.Viewkey.INTEREST_GOODS)) {
            return makeIndexSpec(interGoodsFragment, view_type);
        } else if (view_key.equals(Constant.Viewkey.BRAND)) {
            return makeIndexSpec(brandFragment, view_type);
        } else if (view_key.equals(Constant.Viewkey.NAV)) {
            return makeIndexSpec(navFragment, view_type);
        }

//        else if (view_key.equals(Constant.Viewkey.ORTHER)) {
//            return makeIndexSpec(ortherFragment, view_type);
//        }

        return null;
    }


    public BaseViewHolder getViewHolder(ViewGroup parent, int viewType, BaseViewHolder viewHolder,
                                        Context context, IndexFragment fragment) {
        int viewKey = getIndexViewKey(viewType);
        int indexViewType = getIndexViewType(viewType);

//         LogUtils.i(" -- viewKey:" + viewKey);
//         LogUtils.i(" -- indexViewType:" + indexViewType);
        if (bannerFragment == viewKey) {                                       /* 轮播图 */
            if (indexViewType == 1) {   /* 会自动轮询播放的 */
                viewHolder = new BannerViewHolder(parent, context, fragment);
            } else {
                viewHolder = new Banner1ViewHolder(parent, context, fragment);
            }

        } else if (navFragment == viewKey) {                                 /* 导航 */
            if (indexViewType == 0) {
                viewHolder = new NvaViewHolder(parent, context);
            }

        } else if (advFragment == viewKey) {                                   /* 广告 */
            if (indexViewType == 1) {
                viewHolder = new AdvertsingViewHolder1(parent, context);
            } else if (indexViewType == 2) {
                viewHolder = new AdvertsingViewHolder2(parent, context);
            } else {
                viewHolder = new AdvertsingViewHolder3(parent, context);
            }

        } else if (sellFragment == viewKey) {                                  /* 批零中心 */
            if (indexViewType == 1) {
                viewHolder = new RetailCenterViewHolder(parent, context, fragment);
            }

        } else if (recomGoodsFragment == viewKey) {                            /* 商品推荐 */
            if (indexViewType == 1) {
                viewHolder = new GoodsProductViewHolder1(parent, context);
            } else if (indexViewType == 2) {
                viewHolder = new GoodsProductViewHolder2(parent, context);
            } else {
                viewHolder = new GoodsProductViewHolder3(parent, context);
            }

        } else if (interGoodsFragment == viewKey) {                            /* 推荐商品 */
            if (indexViewType == 1) {
                viewHolder = new MayYouLikeViewHolder(parent, context);
            }

        } else if (brandFragment == viewKey) {                                 /* 品牌街 */
            if (indexViewType == 1) {
                viewHolder = new FactoryDirectViewHolder(parent, context);
            }

        } else if (factroyFragment == viewKey) {                               /* 厂家直供 */
            if (indexViewType == 1) {
                viewHolder = new FactoryDirectViewHolder1or2(parent, context, true, null);
            } else if (indexViewType == 2) {
                viewHolder = new FactoryDirectViewHolder1or2(parent, context, false, null);
            } else if (indexViewType == 3) {
                viewHolder = new FactoryDirectViewHolder3(parent, context);
            } else {
                viewHolder = new FactoryDirectViewHolder4(parent, context);
            }

        }
//        else if (ortherFragment == viewKey) {                                 /* 其他 */
//            // viewHolder = new OrtherViewHolderOld(parent, context);
//            viewHolder = new OtherViewHolder(parent, context);
//
//        }
        return viewHolder;
    }

}

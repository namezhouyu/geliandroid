package com.geli.m.api;

import com.geli.m.bean.AbousUsBean;
import com.geli.m.bean.AccountManagementBean;
import com.geli.m.bean.AccountOrderBean;
import com.geli.m.bean.AddressListBean;
import com.geli.m.bean.AfterSoldDetailsBean;
import com.geli.m.bean.AlterAddressBean;
import com.geli.m.bean.AppVersion;
import com.geli.m.bean.BalanceBean;
import com.geli.m.bean.BankDetailsBean;
import com.geli.m.bean.BankListBean;
import com.geli.m.bean.BrowseBean;
import com.geli.m.bean.CollectionBean;
import com.geli.m.bean.ExpensesBean;
import com.geli.m.bean.ExpensesRecordBean;
import com.geli.m.bean.FindCatBean;
import com.geli.m.bean.FindDetailsBean;
import com.geli.m.bean.FindListBean;
import com.geli.m.bean.FwqTimeBean;
import com.geli.m.bean.GetCodeBean;
import com.geli.m.bean.GoodsDetailsBean;
import com.geli.m.bean.GoodsFromCatBean;
import com.geli.m.bean.HelpCenterBean;
import com.geli.m.bean.HelpCenterBottomBean;
import com.geli.m.bean.InvoiceBean;
import com.geli.m.bean.InvoiceMergeSuccessBean;
import com.geli.m.bean.InvoiceOrderBean;
import com.geli.m.bean.InvoiceRecordBean;
import com.geli.m.bean.InvoiceStateBean;
import com.geli.m.bean.KeyWordsBean;
import com.geli.m.bean.LogisticsDetailsBean;
import com.geli.m.bean.LogisticsPriceBean;
import com.geli.m.bean.MemberBean;
import com.geli.m.bean.MessBean;
import com.geli.m.bean.NewAddressBean;
import com.geli.m.bean.OrderContactBean;
import com.geli.m.bean.OrderDetailsBean;
import com.geli.m.bean.OrderListBean;
import com.geli.m.bean.OverseasCountrOutrBean;
import com.geli.m.bean.OverseasGoodsOuterBean;
import com.geli.m.bean.OverseasSortBean;
import com.geli.m.bean.PersonInfoBean;
import com.geli.m.bean.RegistrationProtocolBean;
import com.geli.m.bean.RestaurantAddrBean;
import com.geli.m.bean.RestaurantBean;
import com.geli.m.bean.RestaurantGoodsBean;
import com.geli.m.bean.RestaurantGoodsShopScreen;
import com.geli.m.bean.RestaurantHotSearchBean;
import com.geli.m.bean.RestaurantInfoBean;
import com.geli.m.bean.RestaurantShopBean;
import com.geli.m.bean.RestaurantSortBean;
import com.geli.m.bean.RetailCenterBean;
import com.geli.m.bean.ShPriceBean;
import com.geli.m.bean.ShopAPDetailBean;
import com.geli.m.bean.ShopAptitudeBean;
import com.geli.m.bean.ShopInfoBean;
import com.geli.m.bean.ShopInvoiceBean;
import com.geli.m.bean.ShopLogisticBean;
import com.geli.m.bean.StreetList;
import com.geli.m.bean.SubmitOrderBean;
import com.geli.m.bean.SubmitOrderNewBean;
import com.geli.m.bean.TransferBean;
import com.geli.m.bean.UserAPDetailBean;
import com.geli.m.bean.WalletBalanceBean;
import com.geli.m.bean.WalletBean;
import io.reactivex.Observable;
import java.util.Map;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @author Reims
 * @date 2017/10/18.
 */

public interface Api {
    /**
     * 通用有参数
     *
     * @param url
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<ResponseBody> universal(@retrofit2.http.Url String url, @FieldMap Map<String, String> data);

    /**
     * 通用无参数
     *
     * @param url
     * @return
     */
    @POST
    Observable<ResponseBody> universal(@retrofit2.http.Url String url);

    /**
     * body通用无参数
     *
     * @param url
     * @param Body
     * @return
     */
    @POST
    Observable<ResponseBody> universal(@retrofit2.http.Url String url, @Body RequestBody Body);

    /**
     * 登录
     *
     * @param phone
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.login)
    Observable<ResponseBody> doLogin(@Field("phone") String phone, @Field("password") String password);

    /**
     * 获取验证码
     *
     * @param phone
     * @param authtype
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.saveAuth)
    Observable<GetCodeBean> getCode(@Field("phone") String phone, @Field("authtype") String authtype, @Field("auth_user") String auth_user);

    /**
     * 注册
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<ResponseBody> regist(@retrofit2.http.Url String url, @FieldMap Map<String, String> map);

    /**
     * 获得注册协议
     *
     * @return
     */
    @POST(UrlSet.register)
    Observable<RegistrationProtocolBean> getProtocol();

    /**
     * 获取首页信息
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.GullyShopingHome)
    Observable<ResponseBody> getIndexInfo(@FieldMap Map<String, String> data);

    /**
     * 批零列表
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<RetailCenterBean> getRetailCenter(@retrofit2.http.Url String url, @FieldMap Map<String, String> data);

    /**
     * 地址列表
     *
     * @param user_id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.addlist)
    Observable<AddressListBean> getAddressList(@Field("user_id") String user_id);

    /**
     * 删除地址
     *
     * @param user_id
     * @param add_id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.deleadd)
    Observable<ResponseBody> deleteAddress(@Field("user_id") String user_id, @Field("add_id") String add_id);

    /**
     * 获得地址信息
     *
     * @param user_id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.getaddress)
    Observable<AlterAddressBean> getAddressInfo(@Field("user_id") String user_id, @Field("add_id") String address_id);

    /**
     * 保存地址
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.saveadd)
    Observable<NewAddressBean> saveAddress(@FieldMap Map<String, String> data);

    /**
     * 获取街道
     *
     * @param area_id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.getstreet)
    Observable<StreetList> getStreet(@Field("area_id") String area_id);

    /**
     * 获取商家信息
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.shopInformation)
    Observable<ShopInfoBean> getShopInfo(@FieldMap Map<String, String> data);


    /**
     * 获得发票
     *
     * @param user_id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.InvoiceList)
    Observable<InvoiceBean> getInvoice(@Field("user_id") String user_id);

    /**
     * 删除发票
     *
     * @param user_id
     * @param invoice_id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.InvoiceDel)
    Observable<ResponseBody> deleteInvoice(@Field("user_id") String user_id, @Field("invoice_id") String invoice_id);

    /**
     * 添加发票
     *
     * @param Body
     * @return
     */
    @POST
    Observable<ResponseBody> addInvoice(@retrofit2.http.Url String url, @Body RequestBody Body);

    /**
     * 获得购物车列表
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.CART_LIST)
    Observable<ResponseBody> getCartList(@FieldMap Map<String, String> data);

    /**
     * 添加编辑购物车
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.ADD_CART)
    Observable<ResponseBody> addOrEditCart(@FieldMap Map<String, String> data);

    /**
     * 获得商品规格
     *
     * @param goods_id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.goodsSpecifi)
    Observable<ResponseBody> getGoodsSpecifi(@Field("goods_id") String goods_id);

    /**
     * 删除购物车
     *
     * @param user_id
     * @param cart_id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.delCart)
    Observable<ResponseBody> deleteCart(@Field("user_id") String user_id, @Field("cart_id") String cart_id);


    /**
     * 获得商品详情
     *
     * @param user_id
     * @param goods_id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.GoodsDetail)
    Observable<GoodsDetailsBean> getGoodsDetails(@Field("user_id") String user_id, @Field("goods_id") String goods_id);

    /**
     * 收藏
     * col_type 收藏类型:1:商品,2:商家,3,文章,4:视频
     * id goods_id,shop_id,find_id
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.Collection)
    Observable<ResponseBody> collection(@FieldMap Map<String, String> data);

    /**
     * 领取优惠卷
     *
     * @param user_id
     * @param coupon_id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.CollectCoupons)
    Observable<ResponseBody> collectCoupons(@Field("user_id") String user_id, @Field("coupon_id") String coupon_id);

    /**
     * 获取订单信息 -- v3.0.3 之前的
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.orderInfo)
    Observable<SubmitOrderBean> getOrderInfo(@FieldMap Map<String, String> data);

    /**
     * 获取订单信息  -- v3.0.3
     *
     * @param user_id
     * @param cart_id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.settlement)
    Observable<SubmitOrderBean> settlement(@Field("user_id") String user_id,
                                           @Field("cart_id") String cart_id,
                                           @Field("is_sh") String is_sh);

    /**
     * 获得海外订单信息
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.buyNow)
    Observable<SubmitOrderBean> getOverseasOrderInfo(@FieldMap Map<String, String> data);

//    /**
//     * 提交订单
//     *
//     * @param user_id
//     * @param cart_id
//     * @return
//     */
//    @FormUrlEncoded
//    @POST(UrlSet.orderpayinfo)
//    Observable<ResponseBody> submitOrder(@Field("user_id") String user_id, @Field("data") String cart_id);


    /**
     * 提交订单 -- 普通、现货
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.submitOrder)
    Observable<SubmitOrderNewBean> submitOrderNew(@FieldMap Map<String, String> data);

    /**
     * 提交订单 -- 期货
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.submitFuturesOrder)
    Observable<SubmitOrderNewBean> submitFuturesOrder(@FieldMap Map<String, String> data);

    /**
     * 提交订单 -- 团购
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.submitGrouponOrder)
    Observable<SubmitOrderNewBean> submitGrouponOrder(@FieldMap Map<String, String> data);

//    /**
//     * 海外期货
//     *
//     * @param user_id
//     * @param cart_id
//     * @return
//     */
//    @FormUrlEncoded
//    @POST(UrlSet.submitOverseasOrder)
//    Observable<SubmitOrderNewBean> submitOverseasOrder(@Field("user_id") String user_id, @Field("data") String cart_id);
//
//    /**
//     * 海外团购
//     *
//     * @param user_id
//     * @param cart_id
//     * @return
//     */
//    @FormUrlEncoded
//    @POST(UrlSet.submitOverseasGrouponOrder)
//    Observable<SubmitOrderNewBean> submitOverseasGrouponOrder(@Field("user_id") String user_id, @Field("data") String cart_id);

    /**
     * 获取订单优惠卷情况
     *
     * @param userId
     * @param goods_ids goods_ids 值：多个｛3470,1;6,1 （商品id，购买数量；商品id，购买数量）｝单个｛3470，1｝
     * @param type      4可用 5不可用 6可使用总数
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.AvailableCoupons)
    Observable<ResponseBody> getOrderCoupons(@Field("user_id") String userId, @Field("goods_ids") String goods_ids, @Field("type") String type);


    /**
     * 获得海外列表数据
     *
     * @param data countries_id:国家id
     *             cat_id:分类id
     *             goods_type:商品type 2现货 3期货 4团购
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.countriesGoodsList)
    Observable<OverseasGoodsOuterBean> getOverseasGoodsList(@FieldMap Map<String, String> data);

    /**
     * 获取国家列表
     *
     * @return
     */
    @POST(UrlSet.OverseasCountriesList)
    Observable<OverseasCountrOutrBean> getOverseasCountriesList();

    /**
     * 获取国家对应的分类列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.OverseasCartList)
    Observable<OverseasSortBean> getOverseasSortList(@FieldMap Map<String, String> data);

    /**
     * 获得订单列表
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.orderListNew)
    Observable<OrderListBean> getOrderList(@FieldMap Map<String, String> data);

    /**
     * 订单详情
     *
     * @param user_id
     * @param order_id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.orderDetailNew)
    Observable<OrderDetailsBean> getOrderDetails(@Field("user_id") String user_id, @Field("order_id") String order_id);

    /**
     * 商品评论
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.giveComments)
    Observable<ResponseBody> goodsComment(@FieldMap Map<String, String> data);

    /**
     * 评论上传图片
     *
     * @return
     */
    @POST(UrlSet.goodscommentimg)
    Observable<ResponseBody> goodsCommmentImg(@Body RequestBody Body);

    /**
     * 评论点赞
     *
     * @param user_id
     * @param com_id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.toLike)
    Observable<ResponseBody> commentLike(@Field("user_id") String user_id, @Field("com_id") String com_id);


    /**
     * 获得用户信息
     *
     * @param user_id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.UsersInfo)
    Observable<PersonInfoBean> getUserInfo(@Field("user_id") String user_id);

    /**
     * 修改信息
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.DoUserInfo)
    Observable<ResponseBody> modifyUserInfo(@FieldMap Map<String, String> data);

    /**
     * 修改登录密码
     *
     * @param user_id
     * @param old_password
     * @param new_password
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.UsersUpdatePwd)
    Observable<ResponseBody> updatePass(@Field("user_id") String user_id, @Field("old_password") String old_password, @Field("new_password") String new_password);

    /**
     * 重置支付密码
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.findPwd)
    Observable<ResponseBody> resetPayPass(@FieldMap Map<String, String> data);

    /**
     * 上传头像
     *
     * @param Body
     * @return
     */
    @POST(UrlSet.doAvatar)
    Observable<ResponseBody> doAvatar(@Body RequestBody Body);


    /**
     * 获取商家和商品信息
     * 没有执照信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.goodsFromCat)
    Observable<GoodsFromCatBean> getGoodsFormCart(@FieldMap Map<String, String> data);

    /**
     * 根据地址获取物流费用信息
     *
     * @param user_id
     * @param address_id
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.getLogisticsInfo)
    Observable<LogisticsPriceBean> getLogisticsInfo(@Field("user_id") String user_id, @Field("address_id") String address_id, @Field("data") String data);

    /**
     * 钱包支付
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.balancePay)
    Observable<ResponseBody> balancePay(@FieldMap Map<String, String> data);

    /**
     * 订单支付
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.orderPay)
    Observable<ResponseBody> orderPay(@FieldMap Map<String, String> data);

    /**
     * 获得帮助中心数据
     *
     * @return
     */
    @POST(UrlSet.helpIndex)
    Observable<HelpCenterBean> getHelpCenterData();

    /**
     * 根据cat获得对应信息
     *
     * @param cat_id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.articleList)
    Observable<HelpCenterBottomBean> getHelpCenterForCat(@Field("cat_id") String cat_id);

    /**
     * 会员中心
     *
     * @param user_id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.member)
    Observable<MemberBean> MemberCenter(@Field("user_id") String user_id);

    /**
     * 获得银行信息
     *
     * @param user_id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.findBank)
    Observable<ResponseBody> getBankInfo(@Field("user_id") String user_id);

    /**
     * 上传转账图片
     *
     * @param Body
     * @return
     */
    @POST(UrlSet.payProof)
    Observable<ResponseBody> uploadPayProof(@Body RequestBody Body);


    /**
     * 搜索商品
     *
     * @param data shop_type  1批零，2厂家，3海外，4商家(shop_id)
     *             goods_type 1普通 2(海外)现货  3期货 4团购
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.searchgoods)
    Observable<OverseasGoodsOuterBean> searchGoods(@FieldMap Map<String, String> data);

    /**
     * 搜索商家
     *
     * @param data shop_name
     *             shop_type 批零1+厂家2
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.searchshop)
    Observable<RetailCenterBean> searchShops(@FieldMap Map<String, String> data);

    /**
     * 发现列表
     *
     * @param data cat_id     page   user_id   time
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.findlist)
    Observable<FindListBean> findList(@FieldMap Map<String, String> data);

    /**
     * 发现详情
     *
     * @param find_id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.finddetails)
    Observable<FindDetailsBean> findDetails(@Field("user_id") String user_id, @Field("find_id") String find_id);

    /**
     * 发现点赞
     *
     * @param user_id
     * @param find_id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.findlike)
    Observable<ResponseBody> findLike(@Field("user_id") String user_id, @Field("find_id") String find_id);

    /**
     * 绑定银行卡
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.bindBank)
    Observable<ResponseBody> binBank(@FieldMap Map<String, String> data);

    /**
     * 银行卡列表
     *
     * @param user_id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.bankList)
    Observable<BankListBean> bankList(@Field("user_id") String user_id);

    /**
     * 银行卡详情
     *
     * @param user_id
     * @param bk_id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.bankMinute)
    Observable<BankDetailsBean> bankDetails(@Field("user_id") String user_id, @Field("bk_id") String bk_id);

    /**
     * 解绑银行卡
     *
     * @param user_id
     * @param bk_id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.unbundle)
    Observable<ResponseBody> unBank(@Field("user_id") String user_id, @Field("bk_id") String bk_id, @Field("pay_pwd") String pay_pwd);

    /**
     * 获得浏览记录
     *
     * @param user_id
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.userBrowse)
    Observable<BrowseBean> getBrowse(@Field("user_id") String user_id, @Field("page") String page);


    /**
     * 查余额
     *
     * @param user_id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.myBalance)
    Observable<WalletBean> wallet(@Field("user_id") String user_id);

    /**
     * 提现
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.withdrawDeposit)
    Observable<ResponseBody> withdraw(@FieldMap Map<String, String> data);

    /**
     * 获取消费记录
     *
     * @param user_id
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.rechargeRecord)
    Observable<ExpensesBean> getExpenses(@Field("user_id") String user_id, @Field("page") String page);


    /**
     * 查余额(20180528修)
     *
     * @param user_id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.walletBalance)
    Observable<WalletBalanceBean> walletBalance(@Field("user_id") String user_id);

    /**
     * 金融消费列表 -- 全部 (20180528修)
     *
     * @param user_id
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.walletDetail)
    Observable<ExpensesRecordBean> walletDetailAll(@Field("user_id") String user_id, @Field("page") String page);

    /**
     * 金融消费列表 -- 部分 (20180528修)
     *
     * @param user_id
     * @param page
     * @param trade_type    1 为支出， 2 为转入
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.walletDetail)
    Observable<ExpensesRecordBean> walletDetail(@Field("user_id") String user_id, @Field("page") String page, @Field("trade_type") String trade_type);

    /**
     * 获取物流信息
     *
     * @param order_id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.LogisticsGetlist)
    Observable<LogisticsDetailsBean> getLogisticsList(@Field("user_id") String user_id, @Field("order_id") String order_id);

    /**
     * 关于我们
     *
     * @param code
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.aboutus)
    Observable<AbousUsBean> getAbousUs(@Field("code") String code);

    /**
     * cat_id  1系统消息 2通知中心 3物流消息 4商品详情 5商家首页 6 商家优惠劵 8支付提醒 9活动
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.mess)
    Observable<MessBean> getMess(@FieldMap Map<String, String> data);

    /**
     * 厂家资质
     *
     * @param shop_id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.shopAptitude)
    Observable<ShopAptitudeBean> shopAptitude(@Field("shop_id") String shop_id);

    /**
     * 版本更新
     *
     * @return
     */
    @POST(UrlSet.versionsUpdate)
    Observable<AppVersion> versionUpdated();

    /**
     * 海外条款
     *
     * @param goods_type
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.haiwaiTerms)
    Observable<ResponseBody> getTerms(@Field("goods_type") String goods_type);

    /**
     * 热门搜索
     *
     * @param search_type
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.hotKeywords)
    Observable<KeyWordsBean> hotKeywords(@Field("search_type") String search_type);

    /**
     * 收藏列表
     *
     * @param user_id
     * @param col_type
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.collList)
    Observable<CollectionBean> collList(@Field("user_id") String user_id, @Field("col_type") String col_type);


    /**
     * 发现分类
     *
     * @return
     */
    @POST(UrlSet.findCat)
    Observable<FindCatBean> findCatList();

    /**
     * 举报中兴
     * 1涉嫌刷单或好评，2商品实际与描述不符，3商家资质问题，4其他
     *
     * @param Body
     * @return
     */
    @POST(UrlSet.reportMerchant)
    Observable<ResponseBody> reportMerchant(@Body RequestBody Body);

    /**
     * 功能防窥
     * 1功能异赏2体验问题3功能建议4其它5支付问题6商品问题7商家问题8物流问题
     *
     * @param Body
     * @return
     */
    @POST(UrlSet.doAdd)
    Observable<ResponseBody> feedBack(@Body RequestBody Body);


    /**
     * 获取订单的联系人 user_id    order_id
     *
     * @param user_id       用户id
     * @param order_id      订单id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.orderContact)
    Observable<OrderContactBean> getContact(@Field("user_id") String user_id, @Field("order_id") String order_id);


    // user_id   type//1商品 2物流 3商家   order_id  content  contact  contact_method  img_number


//    /**
//     * 提交"售后反馈信息"
//     * @return
//     */
//    @POST(UrlSet.afterSoldSubmit)
//    Observable<MyListBaseBean> afterSoldSubmit(@Body RequestBody Body);

//    /**
//     * 提交"售后反馈信息"
//     * @return
//     */
//    @POST(UrlSet.afterSoldSubmit)
//    Observable<BaseBean> afterSoldSubmit(@Body RequestBody Body);

    /**
     * 提交"售后反馈信息"
     * @return
     */
    @POST(UrlSet.afterSoldSubmit)
    Observable<ResponseBody> afterSoldSubmit(@Body RequestBody Body);

    /**
     * 获取订单售后详情 user_id    order_id
     *
     * @param user_id       用户id
     * @param order_id      订单id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.afterSoldDetail)
    Observable<AfterSoldDetailsBean> getAfterSoldDetails(@Field("user_id") String user_id, @Field("order_id") String order_id);


    /* 账期 */
    /**
     * 获取订单售后详情 user_id    order_id
     *
     * @param shop_id       商店id
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.shopShDetail)
    Observable<ShopAPDetailBean> getShopShDetail(@Field("shop_id") String shop_id);

    /**
     * 获取图片 或是 文件
     * @param path
     * @return
     */
    @GET("{path}")
    Observable<ResponseBody> getDownAgreement(@Path("path") String path);

    /**
     * 账期申请
     *            接口名称：Sh/shApply
     *            请求参数
     *            |字段        |是否必须|说明|
     *            |:---------:|:-----:|:-----:|
     *            |user_id    |1  |   user_id|
     *            |shop_id    |1  |   商店Id|
     *            |sh_day     |1  |   申请天数|
     *            |apply_type |1  |   申请类型，1开通，2修改额度|
     *            |amount     |1  |   额度，修改额度的时候使用|
     *            |File       |1  |   上传的文件，name值为"file"|
     *
     *    返回数据    无数据返回，成功状态码100
     * @param Body
     * @return
     */
    @POST(UrlSet.shApply)
    Observable<ResponseBody> shApply(@Body RequestBody Body);


    /**
     * 用户账期详情
     *
     * @param shop_id
     * @param shop_id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.userShDetail)
    Observable<UserAPDetailBean> getUserShDetail(@Field("user_id") String user_id, @Field("shop_id") String shop_id);



    /**
     * 申请临时额度
     *
     * @param user_id
     * @param amount         要申请的额度
     * @param shop_id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.tempShApply)
    Observable<ResponseBody> tempShApply(@Field("user_id") String user_id,
                                                 @Field("amount") String amount,
                                                 @Field("shop_id") String shop_id);



    /**
     * 账期管理(未归还)
     *
     * @param user_id
     * @param type		类型  1已开通2未开通
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.userShManage)
    Observable<AccountManagementBean> userShManage(@Field("user_id") String user_id,
                                                   @Field("type") String type);


    /**
     * 查询是否可以格利支付
     *
     * @param user_id
     * @param order_sn       订单编号
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.isGeliPay)
    Observable<BalanceBean> isGeliPay(@Field("user_id") String user_id, @Field("order_sn") String order_sn);


    /**
     * 格利订单支付
     * @param user_id
     * @param order_sn      订单编号
     * @param pay_type      支付方式，1微信2支付宝3线下4余额6格利支付
     * @param pay_pwd       支付密码	(格利支付需要)
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.orderPayNew)
    Observable<ResponseBody> orderPayNew(@Field("user_id") String user_id,
                                         @Field("order_sn") String order_sn,
                                         @Field("pay_type") String pay_type,
                                         @Field("pay_pwd") String pay_pwd);

    /**
     * 物流支付
     * @param user_id
     * @param order_sn      订单编号
     * @param pay_type      支付方式，1微信2支付宝3线下4余额6格利支付
     * @param pay_pwd       支付密码	(格利支付需要)
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.logisticsPay)
    Observable<ResponseBody> logisticsPay(@Field("user_id") String user_id,
                                         @Field("order_sn") String order_sn,
                                         @Field("pay_type") String pay_type,
                                         @Field("pay_pwd") String pay_pwd);

    /**
     *
     * 账期价格
     * @param user_id
     * @param goods_id      商品id
     * @param shop_id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.shPrice)
    Observable<ShPriceBean> shPrice(@Field("user_id") String user_id,
                                    @Field("goods_id") String goods_id,
                                    @Field("shop_id") String shop_id);



    /**
     *
     * (用户-商店)账期详情
     * @param user_id
     * @param shop_id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.shDetail)
    Observable<AccountOrderBean> shDetail(@Field("user_id") String user_id,
                                          @Field("shop_id") String shop_id);


    /**
     * 获取服务器时间接口
     *
     * @return
     */
    @POST(UrlSet.getFwqTime)
    Observable<FwqTimeBean> getFwqTime();

    /**
     * 需要开具发票的 -- 商家列表
     * @param user_id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.shopInvoice)
    Observable<ShopInvoiceBean> shopInvoice(@Field("user_id") String user_id);

    /**
     * 需要开具发票的 -- 订单列表
     * @param user_id
     * @param order_id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.invoiceOrder)
    Observable<InvoiceOrderBean> invoiceOrder(@Field("user_id") String user_id, @Field("order_id") String order_id);


    /**
     * 提交合并发票
     * @param user_id
     * @param order_id
     * @param invoice_id
     * @param address_id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.invoiceMerge)
    Observable<InvoiceMergeSuccessBean> invoiceMerge(@Field("user_id") String user_id,
                                                     @Field("order_id") String order_id,
                                                     @Field("invoice_id") String invoice_id,
                                                     @Field("address_id") String address_id);


    /**
     * 提交合并发票
     * @param user_id
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.invoiceRecords)
    Observable<InvoiceRecordBean> invoiceRecords(@Field("user_id") String user_id,
                                                 @Field("page") String page);


    /**
     * 开票详情
     * @param user_id
     * @param invoice_id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.invoiceDetail)
    Observable<InvoiceStateBean> invoiceDetail(@Field("user_id") String user_id,
                                               @Field("invoice_id") String invoice_id);



    /**
     * 新批发列表
     * @param keywords      要搜索的关键字(不搜索不需要)
     * @param lnt           经度              非必填
     * @param lat           纬度              非必填
     * @param page          分页，默认为1
     * @param type          店铺类型，1新批发，2地方食品馆
     * @param city_id       地域id，为城市id    非必填
     * @param market_type   新批发类型id，类型筛选用，非必填
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.localFoodList)
    Observable<RestaurantBean> localFoodList(@FieldMap Map<String, String> data);

    /**
     * 新批发热门搜索关键字
     *
     * @return
     */
    @POST(UrlSet.getKeywords)
    Observable<RestaurantHotSearchBean> getKeywords();


    /**
     * 转账支付
     * @param   user_id     用户id
     * @param   order_sn
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.transfer)
    Observable<TransferBean> transfer(@Field("user_id") String user_id,
                                      @Field("order_sn") String order_sn);




    /**
     * 新批发商店列表
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.localFoodShops)
    Observable<RestaurantShopBean> localFoodShops(@FieldMap Map<String, String> data);

    /**
     * 新批发商品列表
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.localFoodGoods)
    Observable<RestaurantGoodsBean> localFoodGoods(@FieldMap Map<String, String> data);



    /**
     * 新批发详情
     * @param   lf_id     新批发id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.marketDetail)
    Observable<RestaurantInfoBean> marketDetail(@Field("lf_id") String lf_id);

    /**
     * 获取批发商店所有的物流商信息
     * @param   shop_id     商店id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.getLogisticInfo)
    Observable<ShopLogisticBean> getLogisticInfo(@Field("shop_id") String shop_id);

    /**
     * 获取商店筛选
     * @param   lf_id     新批发id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.getShopScreen)
    Observable<RestaurantGoodsShopScreen> getShopScreen(@Field("lf_id") String lf_id);

    /**
     * 获取商品筛选
     * @param   lf_id     新批发id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlSet.getGoodsScreen)
    Observable<RestaurantGoodsShopScreen> getGoodsScreen(@Field("lf_id") String lf_id);

    /**
     * 获取商店筛选  (省市)
     * @return
     */
    @POST(UrlSet.market_zone)
    Observable<RestaurantAddrBean> marketZone();

    /**
     * 获取批发市场类型   (种类筛选)
     * @return
     */
    @POST(UrlSet.market_type)
    Observable<RestaurantSortBean> marketType();
}

package com.geli.m.api;

import static com.geli.m.BuildConfig.GL_URL;

/**
 * Created by mrCTang on 2017/5/21.
 *
 */
public interface UrlSet {


    String HTTP = "http://";
    String HTTPS = "https://";
    String login = GL_URL + "Passport/doLogin";//登陆
    String deleadd = GL_URL + "Users/delAddress";//删除地址
    String addlist = GL_URL + "Users/addressList";//删除地址
    String saveadd = GL_URL + "Users/saveAddress";//删除地址
    String getstreet = GL_URL + "Address/getStreet";//删除地址
    String getaddress = GL_URL + "Users/getAddress";//删除地址
    String Sign_url = GL_URL + "Passport/doRes";//注册Passport/doRes
    String GullyShopingHome = GL_URL + "Index/index";//首页
    String GoodsDetail = GL_URL + "Goods/goodsDetail";
    String GoodsList = GL_URL + "Goods/goodsList";
    String ADD_CART = GL_URL + "Cart/addCart";
    String CART_LIST = GL_URL + "Cart/cartList";
    String GOODS_CATEGORY = GL_URL + "Goods/categoryList";//分类列表
    String RetailCenterShopList = GL_URL + "Shops/wholesaleList";//零售中心（批零中心）
    String factoryList = GL_URL + "Shops/factoryList";//厂家直供
    String ShopDetail = GL_URL + "Shops/shopDetail";//批零中心（工厂）商家详细
    String goodsFromCat = GL_URL + "Shops/shopCatGoods";//根据cat拿goods
    String CollectionList = GL_URL + "Users/goodsCollList";//收藏列表  Users/collection
    String Attention = GL_URL + "Users/attention";//关注列表
    String Collection = GL_URL + "Users/collection"; //收藏和取消收藏
    String delCart = GL_URL + "Cart/delCart"; //删除购物车
    String editCart = GL_URL + "Cart/editCart"; //编辑购物车
    String doForgetPwd = GL_URL + "Passport/doForgetPwd";//Passport/doForgetPwd
    String helpIndex = GL_URL + "Article/helpIndex";
    String articleList = GL_URL + "Article/articleList";

    String saveAuth = GL_URL + "Auth/sendAuth";//找回密码发送验证嘛
    String orderInfo = GL_URL + "Order/temporary";//获取订单信息
    String attentionList = GL_URL + "Users/attentionList";//获取关注点店铺列表
    String buyNow = GL_URL + "Order/buyNow";//立即购买
    String UsersInfo = GL_URL + "Users/userInfo";//获取个人信息
    String DoUserInfo = GL_URL + "Users/doUserInfo";//修改个人信息
    String OrderList = GL_URL + "Users/orderList";//获取我的订单列表
    String doAvatar = GL_URL + "Users/doAvatar";//上传头像
    String UsersUpdatePwd = GL_URL + "Users/updatePwd";//用户修改密码
    String OverseasCountriesList = GL_URL + "Overseas/countriesList";//全球直采的列表
    String countriesGoodsList = GL_URL + "Overseas/countriesGoodsList";//全球商品的列表
    String register = GL_URL + "Passport/register";//注册协议
    String shopGoodsSearch = GL_URL + "Shops/shopGoodsSearch";//商家搜索
    String orderDetail = GL_URL + "Users/orderDetail";//订单详情
    String versionsUpdate = GL_URL + "Index/versionsUpdate";//版本更新
    String cancelOrder = GL_URL + "Users/cancelOrder";//取消订单
    String confirmReceipt = GL_URL + "Users/confirmReceipt";//确认收货
    String orderPay = GL_URL + "Order/userOrderPay";//订单付款
    String remind = GL_URL + "Users/remind";//提醒卖家发货
    String searchGoods = GL_URL + "Shops/searchGoods";//商品搜索   商家类型1批零2工厂
    String reportMerchant = GL_URL + "Users/reportMerchant";//举报商家
    String userBrowse = GL_URL + "Browse/userBrowse";//浏览记录
    String clearLogBrowse = GL_URL + "Browse/clearLog";//删除浏览记录
    String hotKeywords = GL_URL + "Keyword/hotKeywords";//热门关键字
    String myBalance = GL_URL + "Users/myBalance";//用户余额
    String userToRecharge = GL_URL + "Users/userToRecharge";//用户充值
    String goodsComment = GL_URL + "Comment/goodsComment";//商品详情的评论列表
    String OverseasCartList = GL_URL + "Overseas/catList";//海外直采国家商品分类列表

    String rechargeRecord = GL_URL + "Users/rechargeRecord";//用户钱包记录(明细记录)
    String giveComments = GL_URL + "Users/giveComments";//用户发表评论
    String FuturesOrders = GL_URL + "Order/FuturesOrders";//期货购物车结算（立即下单）
    String CommentStar = GL_URL + "Comment/Star";//评论的数量（差评，满意，一般，非常满意，一般）

    String bindBank = GL_URL + "Bank/bindBank";//绑定银行卡
    String bankList = GL_URL + "Bank/bankList";//银行卡列表
    String bankMinute = GL_URL + "Bank/bankMinute";//银行卡详细信息
    String unbundle = GL_URL + "Bank/unbundle";//银行卡解绑
    String findPwd = GL_URL + "Users/findPwd";//忘记支付密码
    String withdrawDeposit = GL_URL + "Bank/withdrawDeposit";//提现申请
    String findBank = GL_URL + "Order/findBank";//线下支付获取商家银行卡信息接口
    String InvoiceList = GL_URL + "Invoice/InvoiceList";//发票列表接口
    String addInvoice = GL_URL + "Invoice/InvoiceAdd";//新增发票接口
    String InvoiceDel = GL_URL + "Invoice/del";//删除发票接口
    String editInvoice = GL_URL + "Invoice/editInvoice";//编辑修改发票接口
    String shopInformation = GL_URL + "Shops/shopInformation";//批零中心（工厂）店铺信息
    String CollectCoupons = GL_URL + "Coupon/CollectCoupons";//用户领取优惠卷
    String payProof = GL_URL + "Order/payProof";//上传转账图片
    String UserComments = GL_URL + "Users/UserComments";//评论他人的评论
    String getListCommentSum = GL_URL + "Comment/getListComment";//评论他人的评论
    String toLike = GL_URL + "Comment/toLike";//评论点赞
    String doAdd = GL_URL + "Feedback/doAdd";//功能反馈
    String Settlement = GL_URL + "Order/Settlement";//结算
    String LogisticsGetlist = GL_URL + "order/orderShipping";//物流信息
    String haiwaiTerms = GL_URL + "goods/terms";//海外条款
    String systemmessage = GL_URL + "push";//系统消息
    String downloadinvoice = GL_URL + "order_invoice/frequency";//下载发票
//    String orderpayinfo = GL_URL + "Order/orderInfo";//订单信息
//    String submitOverseasOrder = GL_URL + "Order/futuresOrderInfo";//海外订单信息
//    String submitOverseasGrouponOrder = GL_URL + "Order/grouponOrderInfo";//海外团购订单信息
    String goodsSpecifi = GL_URL + "Goods/GoodsSpec";//商品规格
    String goodscommentimg = GL_URL + "Users/commentPhotos";//商品评论图片
    String goodsCommentList = GL_URL + "Comment/goodsCommentList";//商品评论列表
    String shopCommentList = GL_URL + "Comment/shopCommentList";//片商家评论列表
    String getLogisticsInfo = GL_URL + "order/apiGetOrderLogisticsFee";//订单中获取物流信息
    String balancePay = GL_URL + "order/balancePay";//钱包支付
    String member = GL_URL + "users/member_center";//会员中心
    String searchshop = GL_URL + "shops/searchShops"; // 搜索+批零1+厂家2
    String searchgoods = GL_URL + "shops/searchGoods";//
    String findlist = GL_URL + "find/findlist";//发现列表
    String finddetails = GL_URL + "find/findDetail";//发现详情
    String findlike = GL_URL + "find/findToLike";//发现点赞
    String aboutus = GL_URL + "index/aboutus";//发现点赞
    String mess = GL_URL + "push/pushList";//物流,通知,公告
    String shopAptitude = GL_URL + "Overseas/shopAptitude";//物流,通知,公告
    String collList = GL_URL + "users/collectionList";//收藏
    String findCat = GL_URL + "find/findCatList";//发现分类

    /** 获取订单的联系人 */
    String orderContact = GL_URL + "order/orderContact";
    /** 售后反馈 */
    String afterSoldSubmit = GL_URL + "Order/Submit";
    /** 订单售后详情 */
    String afterSoldDetail = GL_URL + "order/afterSoldDetail";
    /** 余额 */
    String walletBalance = GL_URL + "Wallet/walletBalance";
    /** 金融消费列表 */
    String walletDetail = GL_URL + "Wallet/walletDetail";


    /* 优惠卷 */
    String myCoupon = GL_URL + "Coupon/myCoupon";                      // 个人中心优惠卷列表
    String AvailableCoupons = GL_URL + "Coupon/AvailableCoupons";      // 获取可用优惠卷

    /* 账期 */
    String shopShDetail = GL_URL + "shops/shopShDetail";               // 商店账期详情
    String shApply = GL_URL + "Sh/shApply";                            // 账期申请
    String userShDetail = GL_URL + "Sh/userShDetail";                  // 用户账期详情
    String tempShApply = GL_URL + "Sh/tempShApply";                    // 申请临时额度

    /* 账期详情 -- 某个用户的 */
    String shDetail = GL_URL + "Sh/shDetail";                          // (用户-商店)账期详情

    /* 我的fragment -- 账期管理 */
    String userShManage = GL_URL + "Sh/userShManage";                  // 申请临时额度

    /* 提交订单 */
    String settlement = GL_URL + "cart/settlement";                    // 购物车结算 -- v3.0.3
    String submitOrder = GL_URL + "Order/submitOrder";                 // 提交订单 -- 现货、普通
    String submitFuturesOrder = GL_URL + "Order/submitFuturesOrder";   // 提交订单 -- 期货
    String submitGrouponOrder = GL_URL + "Order/submitGrouponOrder";   // 提交订单 -- 团购

    /* 支付界面 */
    String isGeliPay = GL_URL + "Gelipay/isGeliPay";                   // 查询是否可以格利支付
    String orderPayNew = GL_URL + "Pay/orderPay";                      // 订单支付
    String logisticsPay = GL_URL + "Pay/logisticsPay";                 // 物流支付

    /* 商品详情 */
    String shPrice = GL_URL + "Goods/shPrice";                         // 账期价格

    /* 我的fragment -- 我的订单 */
    String orderListNew = GL_URL + "Order/orderList";                  // 获取我的订单列表 -- 废弃 User/orderList
    String orderDetailNew = GL_URL + "Order/orderDetail";              // 订单详情 -- 废弃 User/orderDetail



//    /* 格利支付 */
////    String domain = "geli.com";                                   // 线下  -- 格利支付绑定要用到的
////    String GeLiPayUrl = "http://pay.geli.com/";                   // 线下  -- 格利支付绑定要用到的
//
//    String domain = "www.gelifood.com";                             // 线上
//    String GeLiPayUrl = "https://pay.gelifood.com/";                // 线上

    String getFwqTime = "cinpay/getFwqTime.htm";                    // 获取服务器时间接口
    String specialLogin = "login/specialLogin.htm";                 // 免密码登录地址（GET请求跳转页面）

    /** 加密用户名 和 服务器时间的公钥  */
    String public_key ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhd8R0wJFOQYkLoibXg" +
            "G8PD70diCx3xu7KWojuEt4Hs8Vb5toDbUOlxzJP+dE/kpPNnAb2ZNGqYEKX0ZYonA2jXym1b94ur" +
            "F5h1bZFNUyxw2g2ooTIXoSI3FBLC4AxdP9Cn+0CU4NuGvC20OdWywHf0pq2uDWu7Avj+Ax5H2taw" +
            "xgTQ3K/LJOmbIeKROgjqaGLcyBLguOjQi1qM2XFSjn6FJ4YHhIrwhEgq5EO0prf45H/FMhC/cYO7hq3" +
            "TKaaXkE3A9PWtt5CESMLBq45ts3yDziuC3ZsmzeOjGgoaljiEwqKWv07DFa7OJtKnCxSSRQF38vNL0d" +
            "4x85YRtV/F3YIQIDAQAB";

    String transfer = "Gelipay/transfer";                           // 转账支付

    /* 发票合并 */
    String shopInvoice = "Invoice/shopInvoice";                     // 需要开具发票的 -- 商家列表
    String invoiceOrder = "Invoice/invoiceOrder";                   // 需要开具发票的 -- 订单列表
    String invoiceMerge = "Invoice/invoiceMerge";                   // 提交合并发票
    String invoiceRecords = "Invoice/invoiceRecords";               // 开票记录
    String invoiceDetail = "Invoice/invoiceDetail";                 // 开票详情

    /* 新批发 */
    String localFoodList = "Localfood/localFoodList";               // 新批发列表
    String localFoodShops = "Localfood/localFoodShops";             // 新批发 -- "商店"列表
    String getKeywords = "Localfood/getKeywords";                   // 新批发热门搜索关键字

    String localFoodGoods = "LocalFood/localFoodGoods";             // 新批发 -- "商品"列表
    String marketDetail = "LocalFood/marketDetail";                 // 新批发详情
    String getLogisticInfo = "LocalFood/getLogisticInfo";           // 获取批发商店 -- 所有的物流商信息


    String getGoodsScreen = "LocalFood/getGoodsScreen";             // 获取商品筛选
    String getShopScreen = "LocalFood/getShopScreen";               // 获取商店筛选

    String market_zone = "Market/market_zone";                      // 获取商店筛选  (省市)
    String market_type = "Market/market_type";                      // 获取批发市场类型   (种类筛选)
}


package com.geli.m.config;

import android.os.Environment;

/**
 * 全局变量
 */
public interface Constant {

    /* 本地文件路径 */
    /** 如果使用"/"==>File.separator<p> */
    /** 资源放置路径 -- /storage/emulated/0/ */
    String RootDir = Environment.getExternalStorageDirectory().getPath() + "/";
    String GeLiDir = RootDir + "格利食品网" + "/";
    String DownAgreementDir = GeLiDir + "下载合同" + "/";
    String UpLoadAgreementDir = GeLiDir + "/" + "上传合同" + "/";
    String InvoiceDir = GeLiDir + "下载发票" + "/";


    /* 4大fragment */
    /** 0：首页 */
    int IndexFragment = 0;
    /** 1：查询 */
    int FindFragment = 1;
//    /** 1：食品馆 */
//    int LocalRestaurantListActivity = 1;
    /** 2：购物车 */
    int CartFragment = 2;
    /** 3：我的 */
    int MineFragment = 3;


    /* 定位 */
    /** 三个值: district:区域; latitude:纬度; longitude:经度 */
    String[] Location_Key = {"district", "latitude", "longitude"};
    /** 定位 */
    String INTENT_LOCATION = "intent_location";
    /** 城市名 */
    String AREA_NAME = "area_name";


    /* 首页 其他 */
    /** 批零列表 */
    int Other_Piling = 1;
    /** 新批发(食品馆) */
    int Other_Restaurant = 2;
    /** 厂家直供 */
    int Other_ChangJia = 3;
    /** 海外 */
    int Other_Overseas = 4;
    /** 金融 */
    int Other_JinRong = 5;
    /** 物流 -- 冷运 */
    int Other_Logistics = 6;




    /* 收藏 */
    /** 食品 */
    int TYPE_GOODS = 1;
    /** 店铺 */
    int TYPE_SHOP = 2;
    /** 文章 */
    int TYPE_ARTICLE = 3;
    /** 视频 */
    int TYPE_VIDEO = 4;
    String ARGS_TYPE = "args_type";

    /* 优惠券 */
    /** 未使用 -- 管理列表界面 --("我的优惠券"模式 -- CouponManagerActivity加载CouponFragment) */
    int VIEWTYPE_NOUSE = 1;                     // 未使用
    /** 使用记录 -- 管理列表界面 -- ("我的优惠券"模式 -- CouponManagerActivity加载CouponFragment) */
    int VIEWTYPE_USEHISTORY = 2;                // 使用记录
    /** 过期 -- 管理列表界面 -- ("我的优惠券"模式 -- CouponManagerActivity加载CouponFragment) */
    int VIEWTYPE_EXPIRED = 3;                   // 过期
    /** 订单中可以使用 -- 管理列表界面 -- ("选择的优惠券"模式 -- CouponManagerActivity加载CouponFragment) */
    int VIEWTYPE_CANUSE = 4;                    // 订单中可以使用
    /** 不可用 */
    int VIEWTYPE_CANNOTUSE = 5;                 // 不可用
    /**
     * 商家可用,当前不请求网络 -- 商家列表界面 -- ("选择的优惠券"模式 -- ShopDetailsFragment加载CouponFragment)  <p></>
     *
     * 在ShopDetailsFragment(商家列表界面)中拿到优惠券列表"ShopDetailsFragment加载CouponFragment" <br></>
     * 而不是"CouponManagerActivity加载CouponFragment" <br></>
     *
     */
    int VIEWTYPE_SHOPALL = 1 << 3;              // 商家可用,当前不请求网络

    /* 消息 -- 1系统消息 2通知中心 3物流消息 4商品详情 5商家首页 6商家优惠劵  8支付提醒 9活动 */
    String Message_Title = "message_title";
    /** 消息 -- 1:系统公告 2:通知中心 3:交易物流 */
    enum MessageType {
        /** 1:系统公告 */
        system_notify(1, "系统公告"),
        /** 2:通知中心 */
        notify_center(2, "通知中心"),
        /** 3:交易物流 */
        logistics_transactions(3, "交易物流"),
        /** 4:商品详情 */
        commodity_details(4, "商品详情"),
        /** 5:商家首页 */
        shop_home(5, "商家首页"),
        /** 6:商家优惠劵 */
        merchant_coupon(6, "商家优惠劵"),
        /** 8:支付提醒 */
        payment_reminding(8, "支付提醒"),
        /** 9:活动 */
        activity(9, "活动");


        private final int value;
        private final String name;

        //构造方法必须是private或者默认
        private MessageType(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public MessageType valueOf(int value) {
            switch (value) {
                case 1:
                    return system_notify;
                case 2:
                    return notify_center;
                case 3:
                    return logistics_transactions;
                case 4:
                    return commodity_details;
                case 5:
                    return shop_home;
                case 6:
                    return merchant_coupon;
                case 8:
                    return payment_reminding;
                case 9:
                    return activity;

                    default:
                        return system_notify;
            }
        }

        public int getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }




    /* 意图中获取"链接"的key */
    /** 链接 */
    String INTENT_LINKS = "intent_links";
    /** 是否显示标题 */
    String INTENT_IS_SHOW_TITLE = "intent_is_show_title";
    /** 本地文件路径 */
    String INTENT_LOCAL_FILE = "intent_local_file";
    /** 内容 */
    String INTENT_CONTENT = "intent_content";
    /** 标题 */
    String INTENT_TITLE = "intent_title";
    /** 订单id */
    String INTENT_ORDER_ID = "intent_order_id";     //  String INTENT_ORDERID = "intent_orderid";
    /** 订单sn */
    String INTENT_ORDER_SN = "intent_order_sn";
    /** 食品编号 */
    String INTENT_GOODS_ID = "intent_goods_id";     // String INTENT_GOODSID = "intent_goodsid";
    /** 商店id */
    String INTENT_SHOP_ID = "intent_shop_id";       // String INTENT_SHOPID = "intent_shopid";
    /** 商店名称 */
    String INTENT_SHOP_NAME = "intent_shop_name";
    /** 商品分类 */
    String INTENT_CAT_ID = "intent_cat_id";
    /** 商家"有优惠"会传递这个 -- 商店详情也有这个是布局是否打开 */
    String INTENT_IS_OPEN = "intent_is_open";       // String INTENT_ISOPEN = "intent_isopen";
    /** 模式 -- 选择/管理 */
    String INTENT_MODE = "intent_mode";
    /** 类型 -- 目前有：设置支付密码、设置登录密码；  */
    String INTENT_TYPE = "intent_type";
    /** 设置支付密码、设置登录密码 -- 对应的验证码 */
    String INTENT_CODE = "intent_code";
    /** 主界面中的 指定哪一个fragment */
    String INTENT_ITEM = "intent_item";
    /** 天数 */
    String INTENT_DAY = "intent_day";
    /** 是不是账期 */
    String INTENT_IS_SH = "intent_is_sh";
    /** proof 线下汇款（1待提交凭证,2审核中，3重新提交凭证，4审核成功） */
    /** MyOrderFragment 中指定是哪个订单步骤 -- 待付款、待发货、待收货、待评价、售后、查看全部订单 */
    String INTENT_POSITION = "intent_position";
    /** 海外商品的数据 */
    String INTENT_OVERSEAS_DATA = "intent_overseas_data";
    /** 商品在购物车中的id  (多个 使用 ，分开) */
    String INTENT_CART_ID = "intent_cart_id";
    /** 传递一个类 */
    String INTENT_BEAN = "intent_bean";
    /** 评论的商品 */
    String INTENT_COMMENT_GOODS = "intent_comment_goods";
    /** 发现 id */
    String INTENT_FIND_ID = "intent_find_id";
    /** 接口 */
    String INTENT_INTERFACE = "intent_interface";

    /** 账期结算日 */
    String INTENT_AP_CLOSING_TIME = "intent_ap_closing_time";
    /** 是不是物流费 */
    String INTENT_IS_LOGISTICS = "intent_is_logistics";

    /** 金额 */
    String INTENT_MONEY = "intent_money";

    /** 发票id */
    String INTENT_INVOICE_ID = "intent_invoice_id";
    /** 食品馆id */
    String INTENT_RESTAURANT_ID = "intent_restaurant_id";
    /** 食品馆查询类型 -- 商品/商店 */
    String INTENT_SEARCH_TYPE = "intent_search_type";


    /** 商品数据 */
    String DIALOG_BEAN = "dialog_bean";
    /** 规格数据 */
    String DIALOG_SPECIFIC = "dialog_specific";


    /* 订单 -- INTENT_POSITION */
    /** 全部 */
    int POSITION_ALL = 0;
    /** 待付款 */
    int POSITION_PAY = 1;
    /** 待发货 */
    int POSITION_SHIP = 2;
    /** 待收货 */
    int POSITION_RECEIVING = 3;
    /** 待评价 */
    int POSITION_EVALUATION = 4;
    /** 售后 */
    int POSITION_AFTERMARKET = 5;

    // order_status + pay_status + shipping_status
    // proof 线下汇款（1待提交凭证,2审核中，3重新提交凭证，4审核成功）
    /** 全部 -- 0,0,0 */
    String[] STATE_ALL = new String[]{"0", "0", "0"};
    /** 确认(待付款) -- 1,0,0 */
    String[] STATE_PAY = new String[]{"1", "0", "0"};
    /** 待发货 -- 1,2,0 */
    String[] STATE_SHIP = new String[]{"1", "2", "0"};
    /** 已发货(待收货) -- 1,2,1 */
    String[] STATE_RECEIVING = new String[]{"1", "2", "1"};
    /** 已完成(待评价) -- 5,2,2 */
    String[] STATE_EVALUATION = new String[]{"5", "2", "2"};
    /** 售后 -- 6,2,2 */
    String[] STATE_AFTERMARKET = new String[]{"6", "2", "2"};
    /** 订单取消 -- 2,0,0 */
    String[] STATE_CANCELORDER = new String[]{"2", "0", "0"};

    /** 待发货(账期) -- 1,8,0 */
    String[] STATE_SHIP_AP_180 = new String[]{"1", "8", "0"};
    /** 已发货(账期) -- 未收货 -- 1,8,1 */
    String[] STATE_SHIP_AP_181 = new String[]{"1", "8", "1"};
    /** 已完成(账期) -- 待评价 -- 5,8,2 */
    String[] STATE_SHIP_AP_582 = new String[]{"5", "8", "2"};


    /* 订单类型 */
    // 订单以MO开头的是合并订单，以GL开头是单个订单
    /** 订单以MO开头的是合并订单 */
    String Order_Type_MO = "MO";
    /** 订单以GL开头的是单个订单 */
    String Order_Type_GL = "GL";

    /* 支付方式 */
    // 0未支付 1微信 2支付宝 3线下 4余额(支付方式) 6格利支付
    /** 未支付 */
    int Pay_Type_Unpaid = 0;
    /** 微信 */
    int Pay_Type_WeChat = 1;
    /** 支付宝 */
    int Pay_Type_AliPay = 2;
    /** 线下 */
    int Pay_Type_LineDown = 3;
    /** 余额 */
    int Pay_Type_Wallet = 4;
    /** 格利支付 */
    int Pay_Type_GeLi = 6;

    /** 转账支付 */
    int Pay_Type_TransferAccounts = 7;


    /* 线下支付 -- proof 线下汇款 */
    // proof 线下汇款（1待提交凭证,2审核中，3重新提交凭证，4审核成功）
    /** 待提交凭证 -- 上传凭证 */
    int Pay_Proof_WaitSubmit = 1;
    /** 审核中 */
    int Pay_Proof_Audit = 2;
    /** 重新提交凭证 -- 审核失败,请重新提交 */
    int Pay_Proof_AgainSubmit = 3;
    /** 审核成功 */
    int Pay_Proof_AuditSuccess = 4;

    /* 付款比例 -- 1:30%;  2:70%; 3:全部付款完 */
    /** 付款比例 -- 30% */
    int Pay_Percentage_30 = 1;
    /** 付款比例 -- 70% */
    int Pay_Percentage_70 = 2;
    /** 付款比例 -- 全部付款完 */
    int Pay_Percentage_All = 3;

    /* 支付 */
    /** 订单金额 */
    String ORDER_PRICE = "order_price";


    /* 售后 -- 0:申请售后;1:待受理;2:受理中;3:售后完成 */
    /** 申请售后 */
    int AfterSold_Status_Apply = 0;
    /** 待受理 */
    int AfterSold_Status_Admissible = 1;
    /** 受理中 */
    int AfterSold_Status_Admissibility = 2;
    /** 售后完成 */
    int AfterSold_Status_Completion = 3;

    /* 售后类型  --    1商品 2物流 3商家*/
    /** 商品 */
    int AfterSold_goods = 1;
    /** 物流 */
    int AfterSold_logistics = 2;
    /** 商家 */
    int AfterSold_shop = 3;

    /* 发票 */
    /** 发票数据 */
    String INTENT_INVOICE_DATA = "intent_invoice_data";
    /** 单位发票 */
    int INVOICE_UNIT = 1;
    /** 个人发票 -- 都是普通发票 */
    int INVOICE_PERSONAL = 2;

    /** 单位 -- 专用发票 */
    int INVOICE_UNIT_SPECIAL = 1;
    /** 单位 -- 普通发票 */
    int INVOICE_UNIT_COMMON = 2;

    /** 电子发票 */
    int INVOICE_ELECTRONIC = 1;
    /** 纸质发票 */
    int INVOICE_PAGER = 2;


    // 开票状态(1等待开票2开票成功)
    /** 1.等待开票/已申请 */
    int INVOICE_State_Wait = 1;
    /** 2.开票成功 */
    int INVOICE_State_Success = 2;

    /* 购买付款方式 */
    /** 国内的 */
    int Goods_Type_Domestic = 1;
    /** 海外直采-现货 */
    int Goods_Type_Spot = 2;
    /** 海外直采-期货 */
    int Goods_Type_Futures = 3;
    /** 海外直采-团购 */
    int Goods_Type_GroupBuy = 4;
    /** 新批发 */
    int Goods_Type_NewWholesale = 5;

    /** 可拼团/可零售 */
    int Gs_Id_resale = 6;

    /* 商品是否失效 */
    /** 商品未失效 */
    int Goods_State_Normal = 1;
    /** 商品失效 */
    int Goods_State_Invalid = 2;

    /* 账期 */
    // 说明:sh_status为商品账期信息，里面的status字段：
    // 1 用户可以账期支付，
    // 2用户已经申请账期支付，
    // 3店铺已经开通账期(可申请)，
    // 4不可以账期支付(店铺未开通或者商品未开通)，
    // 如果值为1，则有sh_day字段
    // AccountPeriod 账期
    /** 账期状态 -- key */
    String AP_STATUS = "AP_STATUS";
    /** 用户可以账期支付(可提升) */
    int AP_Status_AllowablePay = 1;
    /** 用户已经申请账期支付 -- 正在申请还没通过 */
    int AP_Status_Apply = 2;
    /** 店铺已经开通账期(可申请) */
    int AP_Status_AlreadyOpened = 3;
    /** 不可以账期支付(店铺未开通或者商品未开通) */
    int AP_Status_NotPayable = 4;


    /* 账期使用状态 -- sh_status */
    /** 使用账期付款(未确定收货) */
    int AP_UseStatus_InUse = 1;
    /** 账期已归还(未使用账期) */
    int AP_UseStatus_NotUsed = 2;
    /** 账期逾期 */
    int AP_UseStatus_BeOverdue = 3;
    /** 账期开始生效(确认收货) */
    int AP_UseStatus_TakeEffect = 4;

    /* 账期使用(另外) -- 状态 -- status */
    /** 取消账期权限 */
    int AP_OtherStatus_Cancel = 0;
    /** 开通权限 -- 正常使用 */
    int AP_OtherStatus_NormalUse = 1;
    /** 账期修改中 */
    int AP_OtherStatus_Revising = 2;

    /* 是不是账期 */
    /** 不是账期 */
    int AP_False = 0;
    /** 是账期 */
    int AP_True = 1;

    /* 临时账期状态 -- is_temp -- 是否申请过临时额度，1是0非  -- 如果是2就是申请中 */
    /** 没有申请过临时额度 */
    int AP_TempAp_No = 0;
    /** 申请过临时额度 */
    int AP_TempAp_Yes = 1;
    /** 临时额度申请中 */
    int AP_TempAp_Apply = 2;

    /* 有没有开通账期 */
    /** 有没有开通账期  -- key */
    String AP_Open_State = "AP_IS_OPEN";
    /** 已开通店铺 */
    int AP_Already_Opened = 1;
    /** 未开通店铺 */
    int AP_Not_Opened = 2;

    /* 商店 -- 是否有开通账期的权限 -- shop_sh_status */
    /** 不可以开通 */
    int AP_shop_status_on_open = 0;
    /** 可以开通 */
    int AP_shop_status_open = 1;

    /* 是否需要支付运费 */
    /** 需要支付运费 */
    int Logistics_Status_Yes = 0;
    /** 不需要支付运费/已付完 */
    int Logistics_Status_No = 1;

    /* 提交订单 */
    /** 广播数据(对应的KEY) -- 地址 */
    String BROADCAST_ADDRESS = "broadcast_address";
    /** 广播数据(对应的KEY) -- 优惠券 */
    String BROADCAST_COUPON = "broadcast_coupon";
    /** 广播数据(对应的KEY) -- 物流费用 */
    String BROADCAST_LOGISTICS = "broadcast_logistics";
    /** 广播数据(对应的KEY) -- 发票 */
    String BROADCAST_INVOICE = "broadcast_invoice";
    /** 广播数据(对应的KEY) -- 数据 */
    String BROADCAST_DATA = "broadcast_data";

    /* 个人中心/设置界面 */
    /** 退出用户/用户信息修改了 */
    String ACTION_MODIFY = "action_modify";

    /* 用户登录后 -- 发送广播到 MineFragment 加载登录用户的信息 */
    String ACTION_REQUEST = "action_request";
    /* 定位成功后 */
    String ACTION_MY_LOCATION = "action_my_location";


    /* 地图 */
    String MAP_BaiDu = "百度地图";
    String MAP_GaoDe = "高德地图";
    String MAP_Tencent = "腾讯地图";


    /* 新批发 -- 排序 */
    /** 新批发 -- 排序 -- 综合 */
    int Sort_Comprehensive = 1;
    /** 新批发 -- 排序 -- 销量 */
    int Sort_Sales = 2;

    /* 热门搜索 */
    /** 热门搜索 -- 商品 */
    String search_goods = "1";
    /** 热门搜索 -- 商店 */
    String search_shop = "2";

    /* 格利支付的转态 */
    /** 100：为可以格利支付 */
    int GeLiPay_can_pay = 100;
    /** 201：商家没有开通格利支付 */
    int GeLiPay_Not_Open = 201;
    /** 202 ：没有绑定格利支付 */
    int GeLiPay_No_Bindings = 202;
    /** 203 ：没有设置支付密码 */
    int GeLiPay_No_payment_password_was_set = 203;
    /** 209 ：格利支付异常 */
    int GeLiPay_error = 209;

    /* 去格利网页网页支付 -- 其中的一个参数 */
    /** 1 ：去绑定 */
    String jm_Bindings = "1";
    /** 2 ：设置支付密码 */
    String jm_set_password = "2";
    /** 4 ：充值 */
    String jm_recharge = "4";
    /** 5 ：提现 */
    String jm_withdraw = "5";

    /* 店铺类型，1新批发，2地方食品馆   */
    /** 1 ：新批发 */
    String type_market = "1";
    /** 2 ：地方食品馆 */
    String type_local_food = "2";


    /* 拼团/零售: 0:不拼团/不零售; 1:拼团/可零售   */
    /** 0:不拼团/不零售 */
    int resale_false = 0;
    /** 1:拼团/可零售 */
    int resale_true = 1;


    //code码：201：商家没有开通格利支付
//            202 ：没有绑定格利支付
//            203 ：没有设置支付密码
//            209 ：格利支付异常


    int REQUEST_OK = 100;
    int REQUEST_NODATA = 200;
    int REQUEST_NODATA_201 = 201;
    int PHONE_NOREGIST = 103;
    int PASS_OR_PHONE_FAILED = 104;
    int overseasId = 961724790;

    String KEY_CODE = "code";
    String KEY_MESSAGE = "message";
    String KEY_DATA = "data";
    String KEY_VIEWKEY = "view_key";
    String KEY_VIEWTYPE= "view_type";
    String KEY_TITLE = "model_title";
    String KEY_TITLE_ISSHOW = "title_is_show";


    interface Viewkey {
        /** 导航 */
        String NAV = "nav";
        /** 厂家直供 */
        String FACTORY = "factory";
        /** 批零中心 */
        String SELL = "sell";
        /** 轮播图 */
        String ATS = "ats";
        /** 商品推荐 */
        String RECOMMEND_GOODS = "recommend_goods";
        /** 推荐商品 */
        String INTEREST_GOODS = "interest_goods";
        /** 品牌街 */
        String BRAND = "brand";
        /** 广告 */
        String ADV = "adv";
        /** 其他 */
        String ORTHER = "orther";
    }

    interface Navkey {
        /** 批零中心 */
        String sell = "sell";
        /** 新批发 */
        String market = "market";
        /** 地方食品 */
        String localFood = "local";
        /** 厂家直供 */
        String factory = "factory";
        /** 海外直采 */
        String overseas = "overseas";
        /** 格利金融 */
        String pay = "pay";
        /** 格利冷运 */
        String logistics = "logistics";
    }
}

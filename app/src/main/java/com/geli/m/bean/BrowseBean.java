package com.geli.m.bean;

import java.util.List;

/**
 * Created by Steam_l on 2018/3/26.
 */

public class BrowseBean {

    /**
     * code : 100
     * data : [{"goods_name":"佳尚新蔬 青豆 500g*20/件 ","shop_price":"95.00","goods_thumb":"upload/201703/thumb_img/35074_thumb_G_1490604401697.jpg","goods_id":1755,"goods_type":1,"add_time":1524652695},{"goods_name":"测试商品(请勿购买，购买无效)","shop_price":"1.00","goods_thumb":"upload/goods/20180423/s152444844481767.jpg","goods_id":5463,"goods_type":1,"add_time":1524652692},{"goods_name":"安井 霞迷饺 2.5kg*4包/件","shop_price":"120.00","goods_thumb":"upload/goods/20180126/151695590937279.png","goods_id":5019,"goods_type":1,"add_time":1524646561},{"goods_name":"嘉佰农多啦A块（13g） 1kg*10袋/箱 ","shop_price":"200.00","goods_thumb":"upload/old_img/store_100002/goods_112/small_201503161508324212.jpg","goods_id":8,"goods_type":1,"add_time":1524646366},{"goods_name":"鸡油","shop_price":"5.00","goods_thumb":"upload/201611/thumb_img/34899_thumb_G_1478678956199.jpg","goods_id":783,"goods_type":1,"add_time":1524641406},{"goods_name":"万威客脆皮热狗肠原味1kg*10包/件 10件起订","shop_price":"260.00","goods_thumb":"upload/old_img/store_100330/goods_45/small_201512041554059604.jpg","goods_id":365,"goods_type":1,"add_time":1524641388},{"goods_name":"冬之味三混菜","shop_price":"90.00","goods_thumb":"upload/goods/20180418/s152403171556309.jpg","goods_id":5455,"goods_type":1,"add_time":1524641340},{"goods_name":"冬之味黄秋葵","shop_price":"150.00","goods_thumb":"upload/goods/20180413/s152359959826894.jpg","goods_id":5438,"goods_type":1,"add_time":1524641291},{"goods_name":"圣宝利酥脆春卷（豆沙）","shop_price":"80.00","goods_thumb":"upload/goods/20180418/s152403353891981.jpg","goods_id":5457,"goods_type":1,"add_time":1524641144},{"goods_name":"山东龙大经典培根 1.5kg*8包/件 ","shop_price":"250.00","goods_thumb":"upload/201603/thumb_img/34571_thumb_G_1458790918082.jpg","goods_id":1817,"goods_type":1,"add_time":1524640808},{"goods_name":"圣宝利畅享鸡排（奥尔良味）","shop_price":"158.00","goods_thumb":"upload/goods/20180413/s152362006547266.jpg","goods_id":5444,"goods_type":1,"add_time":1524640437},{"goods_name":"大成吮指炸翅中 2.5kg*4包/件 38-45g","shop_price":"410.00","goods_thumb":"upload/201705/thumb_img/35146_thumb_G_1493781068140.jpg","goods_id":2391,"goods_type":1,"add_time":1524624403},{"goods_name":"棒棒鲜 红薯粉煎饺锅贴 白菜猪肉馅 起订量100箱","shop_price":"230.00","goods_thumb":"upload/old_img/store_100020/goods_100/small_201504280945002344.jpg","goods_id":80,"goods_type":1,"add_time":1524623226},{"goods_name":"去骨安格斯西冷肉 PRS 级 20KG/箱 抄码","shop_price":"2960.00","goods_thumb":"upload/goods/20171124/151151751922938.png","goods_id":4888,"goods_type":2,"add_time":1524622457},{"goods_name":"安格斯腱子肉 PRS级 15KG/箱 抄码","shop_price":"825.00","goods_thumb":"upload/goods/20171124/151151926419692.png","goods_id":4897,"goods_type":2,"add_time":1524622053},{"goods_name":"海赢鑫 湛江本地干贝 10kg/件","shop_price":"2310.00","goods_thumb":"upload/goods/c1499933133.png","goods_id":3388,"goods_type":1,"add_time":1524216083},{"goods_name":"大成 汉堡47-50g/片","shop_price":"180.00","goods_thumb":"upload/201702/thumb_img/35033_thumb_G_1487832596984.jpg","goods_id":1496,"goods_type":1,"add_time":1524203594},{"goods_name":"大成汉堡","shop_price":"174.00","goods_thumb":"upload/old_img/store_100178/goods_75/small_201508191414352019.jpg","goods_id":528,"goods_type":1,"add_time":1524203585},{"goods_name":"冬之味甜玉米粒","shop_price":"102.00","goods_thumb":"upload/goods/20180418/s152403244146957.jpg","goods_id":5456,"goods_type":1,"add_time":1524194461},{"goods_name":"菠菜丸","shop_price":"11.00","goods_thumb":"upload//20171202/151218044580769.png","goods_id":4931,"goods_type":1,"add_time":1524192521},{"goods_name":"中粮万威客脆皮热狗肠 1kg*10包/箱 约30支/包","shop_price":"280.00","goods_thumb":"upload/201609/thumb_img/34588_thumb_G_1474276703541.jpg","goods_id":1834,"goods_type":1,"add_time":1524101012},{"goods_name":"恒兴三去开背罗非鱼 1*10kg/件","shop_price":"245.00","goods_thumb":"upload//20171110/151031639837994.png","goods_id":4852,"goods_type":1,"add_time":1524033347},{"goods_name":"恒兴三去开背罗非鱼 1*10kg/件","shop_price":"245.00","goods_thumb":"upload//20171110/151031639837994.png","goods_id":5312,"goods_type":1,"add_time":1524033285},{"goods_name":"恒兴鲷鱼片 1*4.54kg/件","shop_price":"148.00","goods_thumb":"upload//20171110/151031298372649.png","goods_id":5311,"goods_type":1,"add_time":1524023240},{"goods_name":"思念灌汤水饺（猪肉香菇馅） 2.5kg/袋","shop_price":"95.00","goods_thumb":"upload//20171107/151004617446851.png","goods_id":4827,"goods_type":1,"add_time":1524023156},{"goods_name":null,"shop_price":null,"goods_thumb":null,"goods_id":null,"goods_type":null,"add_time":1523954519},{"goods_name":"龙大辣炒鸡肉土豆","shop_price":"1000.00","goods_thumb":"upload/old_img/store_100004/goods_103/small_201503171715037531.jpg","goods_id":35,"goods_type":1,"add_time":1523953436},{"goods_name":"液态酥油","shop_price":"0.00","goods_thumb":"upload/201602/thumb_img/34565_thumb_G_1456715413647.jpg","goods_id":1308,"goods_type":1,"add_time":1523953429},{"goods_name":"测试商品1","shop_price":"0.05","goods_thumb":"upload/goods/20180417/s152394550114168.jpg","goods_id":5452,"goods_type":1,"add_time":1523946929},{"goods_name":"大成经典盐酥鸡（专供） 2.5kg*4包/件","shop_price":"245.00","goods_thumb":"upload/goods/20180203/151764024648752.png","goods_id":1878,"goods_type":1,"add_time":1523945173}]
     * message : 查询成功！
     */
    private int code;
    private List<DataEntity> data;
    private String message;
    private List<Object> dataList;

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public List<Object> getDataList() {
        return dataList;
    }

    public void setDataList(List<Object> dataList) {
        this.dataList = dataList;
    }

    public static class BrowseTime {
        private String time;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

    public static class DataEntity {
        /**
         * goods_name : 佳尚新蔬 青豆 500g*20/件
         * shop_price : 95.00
         * goods_thumb : upload/201703/thumb_img/35074_thumb_G_1490604401697.jpg
         * goods_id : 1755
         * goods_type : 1
         * add_time : 1524652695
         */
        private String goods_name;
        private String shop_price;
        private String goods_thumb;
        private String goods_id;
        private String goods_type;
        private long add_time;

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public void setShop_price(String shop_price) {
            this.shop_price = shop_price;
        }

        public void setGoods_thumb(String goods_thumb) {
            this.goods_thumb = goods_thumb;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public void setGoods_type(String goods_type) {
            this.goods_type = goods_type;
        }

        public void setAdd_time(long add_time) {
            this.add_time = add_time;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public String getShop_price() {
            return shop_price;
        }

        public String getGoods_thumb() {
            return goods_thumb;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public String getGoods_type() {
            return goods_type;
        }

        public long getAdd_time() {
            return add_time;
        }
    }
}

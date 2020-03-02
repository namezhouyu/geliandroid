package com.geli.m;

import com.geli.m.app.GlobalData;
import com.geli.m.utils.Utils;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        System.out.println("=============" + (Utils.md5(GlobalData.Md5Str + GlobalData.getUser_id() + Utils.md5("123456")).toUpperCase()) + "===============");
        //        assertEquals(4, 2 + 2);
        //        ApiEngine.getInstance()
        //                .getApiService()
        //                .getUserInfo(GlobalData.getUser_id())
        //                .subscribeOn(Schedulers.io())
        //                .subscribe(new Consumer<ResponseBody>() {
        //                    @Override
        //                    public void accept(ResponseBody responseBody) throws Exception {
        //                        System.out.println(responseBody.string());
        //                    }
        //                });
        //        Map<String,String> data = new HashMap<>();
        //        Map<String, String> groupbuydata = new HashMap<String, String>();
        //        Map<String, String> futuresdata = new HashMap<String, String>();
        //        Map<String, String> spotdata = new HashMap<String, String>();
        //        data.put("shop_type","1");
        //        data.put("key","呵呵");
        //        groupbuydata.putAll(data);
        //        futuresdata.putAll(data);
        //        spotdata.putAll(data);
        //        groupbuydata.put("type","1");
        //        futuresdata.put("type","2");
        //        spotdata.put("type","3");
        //        System.out.println("="+groupbuydata.toString()+"=\n=="+futuresdata.toString()+"=\n===="+spotdata.toString()+"==\n"+data.toString()+"=================");
        System.out.println("============="+ Utils.md5("123456")+"===============");
    }

    public class Bean {
        private String address_id = "";
        private String pay_type = "";
        private String shipping_type = "";
        private String door = "";
        private String invoice_id = "";
        private String pay_pwd = "";
        private String goods_id = "";
        private String sku_id = "";
        private String goods_number = "";
        private String cpl_id = "";
        private String postscript = "";

        public String getAddress_id() {
            return address_id;
        }

        public void setAddress_id(String address_id) {
            this.address_id = address_id;
        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public String getShipping_type() {
            return shipping_type;
        }

        public void setShipping_type(String shipping_type) {
            this.shipping_type = shipping_type;
        }

        public String getDoor() {
            return door;
        }

        public void setDoor(String door) {
            this.door = door;
        }

        public String getInvoice_id() {
            return invoice_id;
        }

        public void setInvoice_id(String invoice_id) {
            this.invoice_id = invoice_id;
        }

        public String getPay_pwd() {
            return pay_pwd;
        }

        public void setPay_pwd(String pay_pwd) {
            this.pay_pwd = pay_pwd;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getSku_id() {
            return sku_id;
        }

        public void setSku_id(String sku_id) {
            this.sku_id = sku_id;
        }

        public String getGoods_number() {
            return goods_number;
        }

        public void setGoods_number(String goods_number) {
            this.goods_number = goods_number;
        }

        public String getCpl_id() {
            return cpl_id;
        }

        public void setCpl_id(String cpl_id) {
            this.cpl_id = cpl_id;
        }

        public String getPostscript() {
            return postscript;
        }

        public void setPostscript(String postscript) {
            this.postscript = postscript;
        }
    }
}
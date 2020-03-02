package com.geli.m.bean;

import java.util.List;

/**
 * author:  shen
 * date:    2019/3/23
 */
public class RestaurantInfoBean {

    /**
     * code : 100
     * message : ok
     * data : {"lf_id":13,"title":"测试批发021901","img":"/upload/local_food/984ece51a6c922791573171550567859.jpg","note":"测试批发021901","content":"/upload/local_food/6543315c0fa730231c3f9c1550567862.jpg","sort":1,"is_show":1,"add_time":0,"longitude":"113.35207","latitude":"23.126441","user_id":7134,"province":6,"city":76,"district":693,"address":"","address_detail":"广东广州天河区","environment":["/upload/local_food/1725e40d852a434ae6fb3d1553841981.jpg","/upload/local_food/90527e77b57f41efe484ce1553841986.jpg","/upload/local_food/67158b26557dc6d5b390631553841988.jpg"],"qualification":["/upload/local_food/545efdf15e87beb7262c0a1553841998.jpg","/upload/local_food/5257375af037d0fab9a1b61553841998.jpg","/upload/local_food/379f85d5e26efd365eeb851553841998.jpg","/upload/local_food/1014bd9dbeb6796c86673d1553841998.jpg"],"market_desc":"<html><head><style>img{width:100%}<\/style><\/head><body><p><img src=\"http://php.gl.com/upload/editors/img/20190401/1554108186.jpg\" title=\"1554108186.jpg\" alt=\"29081c07ea33e35b4a09ade655e8b33e(1).jpg\"/><\/p><\/body><\/html>"}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * lf_id : 13
         * title : 测试批发021901
         * img : /upload/local_food/984ece51a6c922791573171550567859.jpg
         * note : 测试批发021901
         * content : /upload/local_food/6543315c0fa730231c3f9c1550567862.jpg
         * sort : 1
         * is_show : 1
         * add_time : 0
         * longitude : 113.35207
         * latitude : 23.126441
         * user_id : 7134
         * province : 6
         * city : 76
         * district : 693
         * address :
         * address_detail : 广东广州天河区
         * environment : ["/upload/local_food/1725e40d852a434ae6fb3d1553841981.jpg","/upload/local_food/90527e77b57f41efe484ce1553841986.jpg","/upload/local_food/67158b26557dc6d5b390631553841988.jpg"]
         * qualification : ["/upload/local_food/545efdf15e87beb7262c0a1553841998.jpg","/upload/local_food/5257375af037d0fab9a1b61553841998.jpg","/upload/local_food/379f85d5e26efd365eeb851553841998.jpg","/upload/local_food/1014bd9dbeb6796c86673d1553841998.jpg"]
         * market_desc : <html><head><style>img{width:100%}</style></head><body><p><img src="http://php.gl.com/upload/editors/img/20190401/1554108186.jpg" title="1554108186.jpg" alt="29081c07ea33e35b4a09ade655e8b33e(1).jpg"/></p></body></html>
         */

        private int lf_id;
        private String title;
        private String img;
        private String note;
        private String content;
        private int sort;
        private int is_show;
        private int add_time;
        private String longitude;
        private String latitude;
        private int user_id;
        private int province;
        private int city;
        private int district;
        private String address;
        private String address_detail;
        private String market_desc;
        private List<String> environment;
        private List<String> qualification;

        public int getLf_id() {
            return lf_id;
        }

        public void setLf_id(int lf_id) {
            this.lf_id = lf_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getIs_show() {
            return is_show;
        }

        public void setIs_show(int is_show) {
            this.is_show = is_show;
        }

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getProvince() {
            return province;
        }

        public void setProvince(int province) {
            this.province = province;
        }

        public int getCity() {
            return city;
        }

        public void setCity(int city) {
            this.city = city;
        }

        public int getDistrict() {
            return district;
        }

        public void setDistrict(int district) {
            this.district = district;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddress_detail() {
            return address_detail;
        }

        public void setAddress_detail(String address_detail) {
            this.address_detail = address_detail;
        }

        public String getMarket_desc() {
            return market_desc;
        }

        public void setMarket_desc(String market_desc) {
            this.market_desc = market_desc;
        }

        public List<String> getEnvironment() {
            return environment;
        }

        public void setEnvironment(List<String> environment) {
            this.environment = environment;
        }

        public List<String> getQualification() {
            return qualification;
        }

        public void setQualification(List<String> qualification) {
            this.qualification = qualification;
        }
    }
}

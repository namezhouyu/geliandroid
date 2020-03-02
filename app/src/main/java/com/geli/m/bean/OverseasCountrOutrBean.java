package com.geli.m.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/23.
 * 国家集合
 */

public class OverseasCountrOutrBean {

    /**
     * code : 100
     * data : [{"coun_name":"老挝","coun_img":"upload/countries/c1509694817.png","coun_id":13,"coun_enname":"Laos food"},{"coun_name":"爱尔兰","coun_img":"upload/countries/c1511405178.png","coun_id":20,"coun_enname":"Ireiand  Food"},{"coun_name":"澳大利亚","coun_img":"upload/countries/c1511405021.png","coun_id":18,"coun_enname":"Australia  Food"},{"coun_name":"新西兰","coun_img":"upload/countries/c1510815494.png","coun_id":17,"coun_enname":"New Zealand Food"},{"coun_name":"缅甸","coun_img":"upload/countries/c1510018744.png","coun_id":12,"coun_enname":"Burma food"},{"coun_name":"巴西","coun_img":"upload/countries/c1510917577.png","coun_id":15,"coun_enname":"Brazil Food"},{"coun_name":"泰国","coun_img":"upload/countries/c1509694849.png","coun_id":10,"coun_enname":"Thailand food"},{"coun_name":"厄瓜多尔","coun_img":"upload/countries/c1510815437.png","coun_id":16,"coun_enname":"Ecuador Food"},{"coun_name":"墨西哥","coun_img":"upload/countries/c1509694867.png","coun_id":3,"coun_enname":"Mexico food"},{"coun_name":"越南","coun_img":"upload/countries/c1509694884.png","coun_id":2,"coun_enname":"Vietnam food"}]
     * message : ok
     */
    private int code;
    private List<OverseasCountrBean> data;
    private String message;

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(List<OverseasCountrBean> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public List<OverseasCountrBean> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public static class OverseasCountrBean {
        /**
         * coun_name : 老挝
         * coun_img : upload/countries/c1509694817.png
         * coun_id : 13
         * coun_enname : Laos food
         */
        private String coun_name;
        private String coun_img;
        private int coun_id;
        private String coun_enname;

        public void setCoun_name(String coun_name) {
            this.coun_name = coun_name;
        }

        public void setCoun_img(String coun_img) {
            this.coun_img = coun_img;
        }

        public void setCoun_id(int coun_id) {
            this.coun_id = coun_id;
        }

        public void setCoun_enname(String coun_enname) {
            this.coun_enname = coun_enname;
        }

        public String getCoun_name() {
            return coun_name;
        }

        public String getCoun_img() {
            return coun_img;
        }

        public int getCoun_id() {
            return coun_id;
        }

        public String getCoun_enname() {
            return coun_enname;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof OverseasCountrBean) {
                return this.coun_id == ((OverseasCountrBean) obj).coun_id;
            }
            return super.equals(obj);
        }

    }
}

package com.geli.m.bean;

import java.util.List;

/**
 * Created by Steam_l on 2018/3/8.
 */

public class HelpCenterBottomBean {


    /**
     * code : 100
     * data : {"art_list":[{"art_id":13067,"art_url":"php.gl.com/index.php/store/article/articledetail/art_id/13067.html","art_title":"充值怎么操作？"},{"art_id":13084,"art_url":"php.gl.com/index.php/store/article/articledetail/art_id/13084.html","art_title":"支付方式有哪些？"},{"art_id":13085,"art_url":"php.gl.com/index.php/store/article/articledetail/art_id/13085.html","art_title":"支付常见问题"}]}
     * message : ok
     */
    private int code;
    private DataEntity data;
    private String message;

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public DataEntity getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public static class DataEntity {
        /**
         * art_list : [{"art_id":13067,"art_url":"php.gl.com/index.php/store/article/articledetail/art_id/13067.html","art_title":"充值怎么操作？"},{"art_id":13084,"art_url":"php.gl.com/index.php/store/article/articledetail/art_id/13084.html","art_title":"支付方式有哪些？"},{"art_id":13085,"art_url":"php.gl.com/index.php/store/article/articledetail/art_id/13085.html","art_title":"支付常见问题"}]
         */
        private List<HelpCenterBean.DataEntity.HelpListEntity> art_list;

        public void setArt_list(List<HelpCenterBean.DataEntity.HelpListEntity> art_list) {
            this.art_list = art_list;
        }

        public List<HelpCenterBean.DataEntity.HelpListEntity> getArt_list() {
            return art_list;
        }


    }
}

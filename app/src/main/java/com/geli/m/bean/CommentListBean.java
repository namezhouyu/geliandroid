package com.geli.m.bean;

import java.util.List;

/**
 * Created by Steam_l on 2018/2/1.
 */

public class CommentListBean {
    /**
     * code : 100
     * data : [{"com_like":0,"com_grade":5,"user_id":239,"is_like":0,"com_id":869,"com_content":"内容1","nickname":"","photo":null,"goods_attr":"","avatar":"","add_time":1517367480,"id_value":84},{"com_like":0,"com_grade":5,"user_id":239,"is_like":0,"com_id":862,"com_content":"第一个","nickname":"","photo":["upload/comment/1517367065_9391_comment.jpg","upload/comment/1517367065_44138_comment.jpg"],"goods_attr":"","avatar":"","add_time":1517367091,"id_value":84},{"com_like":0,"com_grade":5,"user_id":239,"is_like":0,"com_id":855,"com_content":"内容1","nickname":"","photo":["upload/comment/1517365529_14137_comment.jpg","upload/comment/1517365529_25646_comment.jpg","upload/comment/1517365529_89994_comment.jpg"],"goods_attr":"","avatar":"","add_time":1517365535,"id_value":84},{"com_like":0,"com_grade":5,"user_id":239,"is_like":0,"com_id":853,"com_content":"内容1","nickname":"","photo":null,"goods_attr":"","avatar":"","add_time":1517364344,"id_value":84},{"com_like":0,"com_grade":5,"user_id":239,"is_like":0,"com_id":845,"com_content":"内容1","nickname":"","photo":{"1":"upload/comment/1517046625_37716_comment.jpg","2":"upload/comment/1517046625_41479_comment.jpg"},"goods_attr":null,"avatar":"","add_time":1517046625,"id_value":84},{"com_like":0,"com_grade":5,"user_id":239,"is_like":0,"com_id":838,"com_content":"内容1","nickname":"","photo":{"1":"upload/comment/1517043939_15920_comment.jpg","2":"upload/comment/1517043939_7541_comment.jpg","3":"upload/comment/1517043939_67644_comment.jpg"},"goods_attr":null,"avatar":"","add_time":1517043939,"id_value":84},{"com_like":0,"com_grade":5,"user_id":239,"is_like":0,"com_id":831,"com_content":"内容1","nickname":"","photo":{"1":"upload/comment/1517043511_95105_comment.jpg","2":"upload/comment/1517043511_12755_comment.jpg"},"goods_attr":null,"avatar":"","add_time":1517043511,"id_value":84},{"com_like":0,"com_grade":5,"user_id":239,"is_like":0,"com_id":824,"com_content":"内容1","nickname":"","photo":{"1":"upload/comment/1517043172_66081_comment.jpg","2":"upload/comment/1517043172_70014_comment.jpg"},"goods_attr":null,"avatar":"","add_time":1517043172,"id_value":84},{"com_like":0,"com_grade":5,"user_id":239,"is_like":0,"com_id":816,"com_content":"内容1","nickname":"","photo":{"1":"upload/comment/1517041835_34759_comment.jpg","2":"upload/comment/1517041835_95621_comment.jpg","3":"upload/comment/1517041835_58976_comment.jpg"},"goods_attr":null,"avatar":"","add_time":1517041835,"id_value":84},{"com_like":0,"com_grade":5,"user_id":239,"is_like":0,"com_id":808,"com_content":"内容1","nickname":"","photo":null,"goods_attr":null,"avatar":"","add_time":1517041454,"id_value":84},{"com_like":0,"com_grade":5,"user_id":239,"is_like":0,"com_id":792,"com_content":"内容1","nickname":"","photo":null,"goods_attr":null,"avatar":"","add_time":1517039631,"id_value":84},{"com_like":0,"com_grade":5,"user_id":239,"is_like":0,"com_id":785,"com_content":"内容1","nickname":"","photo":null,"goods_attr":null,"avatar":"","add_time":1517039564,"id_value":84},{"com_like":0,"com_grade":5,"user_id":239,"is_like":0,"com_id":778,"com_content":"内容1","nickname":"","photo":null,"goods_attr":null,"avatar":"","add_time":1517039514,"id_value":84},{"com_like":0,"com_grade":5,"user_id":239,"is_like":0,"com_id":768,"com_content":"内容1","nickname":"","photo":null,"goods_attr":"123123","avatar":"","add_time":1517039358,"id_value":84},{"com_like":0,"com_grade":5,"user_id":239,"is_like":0,"com_id":761,"com_content":"内容1","nickname":"","photo":{"1":"upload/comment/1517039325_4679_comment.jpg","2":"upload/comment/1517039325_65375_comment.jpg"},"goods_attr":"123123","avatar":"","add_time":1517039325,"id_value":84},{"com_like":0,"com_grade":5,"user_id":239,"is_like":0,"com_id":754,"com_content":"内容1","nickname":"","photo":{"1":"upload/comment/1517038688_81117_comment.jpg","2":"upload/comment/1517038688_83009_comment.jpg"},"goods_attr":"123123","avatar":"","add_time":1517038688,"id_value":84},{"com_like":0,"com_grade":5,"user_id":239,"is_like":0,"com_id":747,"com_content":"内容1","nickname":"","photo":{"1":"upload/comment/1517038688_37504_comment.jpg","2":"upload/comment/1517038688_50229_comment.jpg"},"goods_attr":"123123","avatar":"","add_time":1517038688,"id_value":84},{"com_like":0,"com_grade":5,"user_id":239,"is_like":0,"com_id":740,"com_content":"内容1","nickname":"","photo":{"1":"upload/comment/1517038687_28553_comment.jpg","2":"upload/comment/1517038687_27164_comment.jpg"},"goods_attr":"123123","avatar":"","add_time":1517038687,"id_value":84},{"com_like":0,"com_grade":5,"user_id":239,"is_like":0,"com_id":733,"com_content":"内容1","nickname":"","photo":{"1":"upload/comment/1517038687_38377_comment.jpg","2":"upload/comment/1517038687_68460_comment.jpg"},"goods_attr":"123123","avatar":"","add_time":1517038687,"id_value":84},{"com_like":0,"com_grade":5,"user_id":239,"is_like":0,"com_id":726,"com_content":"内容1","nickname":"","photo":{"1":"upload/comment/1517038687_29219_comment.jpg","2":"upload/comment/1517038687_28060_comment.jpg"},"goods_attr":"123123","avatar":"","add_time":1517038687,"id_value":84}]
     * message : ok
     */
    private int code;
    private List<GoodsDetailsBean.DataBean.GoodsCommentBean> data;
    private String message;

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(List<GoodsDetailsBean.DataBean.GoodsCommentBean> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public List<GoodsDetailsBean.DataBean.GoodsCommentBean> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

}

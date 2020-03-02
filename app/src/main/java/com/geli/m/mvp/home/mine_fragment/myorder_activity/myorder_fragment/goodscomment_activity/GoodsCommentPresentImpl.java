package com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.goodscomment_activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.geli.m.R;
import com.geli.m.bean.BaseBean;
import com.geli.m.bean.GoodsCommentBean;
import com.geli.m.config.Constant;
import com.geli.m.mvp.base.BaseObserver;
import com.geli.m.mvp.base.BasePresenter;
import com.geli.m.utils.Utils;
import com.luck.picture.lib.entity.LocalMedia;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by Steam_l on 2018/1/31.
 */

public class GoodsCommentPresentImpl extends BasePresenter<GoodsCommentView, GoodsCommentModelImpl> {
    public GoodsCommentPresentImpl(GoodsCommentView mvpView) {
        super(mvpView);
    }

    /** 上传成功的id(某订单的某个商品id) */
    public List<Integer> mIntegers = new ArrayList<>(); //上传成功的id

    public void goodsCommentImg(final GoodsCommentBean bean) {
        goodsCommentImg(bean, false);
    }

    public void goodsCommentImg(final GoodsCommentBean bean, final boolean isFlash) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        FileInputStream fis = null;
        String path;
        int i = 0;
        for (LocalMedia media : bean.imgList) {
            path = media.getCompressPath();
            try {
                fis = new FileInputStream(path);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            i++;
            Bitmap bm = BitmapFactory.decodeStream(fis);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
            builder.addFormDataPart("photo_" + bean.getGoods_id() + "_" + i, path, RequestBody.create(MediaType.parse("image/jpeg"), byteArrayOutputStream.toByteArray()));
        }
        builder.addFormDataPart("order_id", bean.getOrder_id());
        builder.addFormDataPart("goods_id", bean.getGoods_id());
        builder.addFormDataPart("img_number", i + "");

        mModel.goodsCommentImg(builder.build(), new BaseObserver<ResponseBody>(this, mvpView, isFlash) {
            @Override
            protected void onSuccess(ResponseBody data) {
                try {
                    BaseBean baseBean = parseData(data.string());
                    if (baseBean.code == Constant.REQUEST_OK) {
                        Integer id = new Integer(bean.getGoods_id());
                        if (!mIntegers.contains(id)) {                  // 这个"订单"的商品已上传的就记录下
                            mIntegers.add(id);
                        }
                        if (isFlash) {
                            mvpView.uploadImgSuccess();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void goodsComment(Map data) {
        mModel.goodsComment(data, new BaseObserver<ResponseBody>(this, mvpView) {
            @Override
            protected void onSuccess(ResponseBody data) {
                try {
                    BaseBean baseBean = parseData(data.string());
                    if (baseBean.code == Constant.REQUEST_OK) {
                        mvpView.onSuccess(Utils.getString(R.string.comment_success));
                    } else {
                        mvpView.onError(baseBean.message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mvpView.onError(parseError(e));
                }
            }
        });
    }

    @Override
    protected GoodsCommentModelImpl createModel() {
        return new GoodsCommentModelImpl();
    }
}

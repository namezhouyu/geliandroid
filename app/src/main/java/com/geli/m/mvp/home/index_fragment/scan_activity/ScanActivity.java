package com.geli.m.mvp.home.index_fragment.scan_activity;

import android.content.Intent;
import android.view.View;
import butterknife.BindView;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import com.geli.m.R;
import com.geli.m.mvp.base.BaseActivity;
import com.geli.m.mvp.home.other.WebViewActivity;
import com.geli.m.mvp.home.other.goodsdetails_activity.GoodsDetailsActivity;
import com.geli.m.mvp.home.other.shopdetails_activity.ShopDetailsActivity;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.StringUtils;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;
import com.gyf.barlibrary.BarHide;

import static com.geli.m.config.Constant.INTENT_CAT_ID;
import static com.geli.m.config.Constant.INTENT_GOODS_ID;
import static com.geli.m.config.Constant.INTENT_LINKS;
import static com.geli.m.config.Constant.INTENT_SHOP_ID;

/**
 * Created by Steam_l on 2017/12/15.
 * 二维码扫描界面
 */
public class ScanActivity extends BaseActivity implements QRCodeView.Delegate {

    @BindView(R.id.scanview)
    ZXingView scanview;
    private boolean isOpenFlash = false;

    @Override
    protected int getResId() {
        return R.layout.activity_scan;
    }

    @Override
    protected void init() {
        super.init();
        mImmersionBar.reset()
                .hideBar(BarHide.FLAG_HIDE_STATUS_BAR)
                .init();
    }

    @Override
    protected void initData() {
        scanview.setDelegate(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        scanview.startCamera();
        scanview.startSpot();
    }

    @Override
    protected void initEvent() {

    }


    /**
     * 在"布局文件"中绑定了
     * @param v
     */
    public void back(View v) {
        finish();
    }

    /**
     * 打开/关闭 -- "闪光灯"
     * @param v
     */
    public void openFlash(View v) {
        if (isOpenFlash) {
            scanview.closeFlashlight();
        } else {
            scanview.openFlashlight();
        }
        isOpenFlash = !isOpenFlash;
    }

    /**
     *
     * 扫描二维码 -- 识别规则codes
     *
     * 拼接例子：code=101&goods_id=21&goods_type=1   <p>
     *
     * 产品二维码URL:
     * http://m.gelifood.com/goods/GOODS_ID/?code=101&goods_id=GOODS_ID&goods_type=GOODS_TYPE
     * 如 -- http://m.gelifood.com/goods/5522/?code=101&goods_id=5522&goods_type=2
     *
     * 店铺二维码URL:
     * http://m.gelifood.com/shop/SHOP_ID/?code=102&shop_id=SHOP_ID
     * 如 -- http://m.gelifood.com/shop/100558/?code=102&shop_id=100558
     *
     * 1、跳转商品详情 :
     *      code=101. 跳转商品详情
     *      goods_id=商品
     *      goods_type=类型        <br>
     *
     * 2、跳转商城
     *      102. 跳转商城
     *      shop_id=商店           <br>
     *      cat_id=商品分类 （非必填）
     *
     * 3、跳转URL
     *      103. 跳转URL
     *      http://               <br>
     *
     * @param result
     */
    private static final String RULE_CODE = "code=";
    private static final String RULE_GOODS_ID = "goods_id=";
    private static final String RULE_SHOP_ID = "shop_id=";
    private static final String RULE_CAT_ID = "cat_id=";
    private static final String URLE_URLS = "https://";
    private static final String URLE_URL = "http://";
    private static final String GOODS_CODE = "101";
    private static final String SHOP_CODE = "102";

    @Override
    public void onScanQRCodeSuccess(String result) {
        try {
            if (result.contains(RULE_CODE)) {                                   /* 包含 -- code= */
                parseCode(result);
            } else {
                if (result.startsWith(URLE_URLS) || result.startsWith(URLE_URL)) {  // 以"http" 或 "https" 开头的
                    startActivity(WebViewActivity.class, new Intent().putExtra(INTENT_LINKS, result));
                    finish();
                }
            }
        } catch (Exception e) {
            ShowSingleToast.showToast(mContext, Utils.getString(R.string.app_error));
        }
    }

    /**
     * 根据 code 字段 去跳到"商品界面"或"商店界面"
     * @param result
     * @return
     */
    private void parseCode(String result) {
        String ruleRes = getRuleRes(result, RULE_CODE); // 解析

        if (StringUtils.isNotEmpty(ruleRes)) {
            if (ruleRes.equals(GOODS_CODE)) {               /* 跳转商品详情  */
                String goods_id = getRuleRes(result, RULE_GOODS_ID);
                if (StringUtils.isNotEmpty(goods_id)) {
                    Intent intentGoods = new Intent().putExtra(INTENT_GOODS_ID, goods_id);
                    startActivity(GoodsDetailsActivity.class, intentGoods);
                    finish();
                    return;
                }
            } else if (ruleRes.equals(SHOP_CODE)) {         /* 跳转商城  */
                String shop_id = getRuleRes(result, RULE_SHOP_ID);
                String cat_id = getRuleRes(result, RULE_CAT_ID);
                if (StringUtils.isNotEmpty(shop_id)) {
                    Intent intentShop = new Intent().putExtra(INTENT_SHOP_ID, shop_id);
                    if(StringUtils.isNotEmpty(cat_id)){
                        intentShop.putExtra(INTENT_CAT_ID, cat_id);
                    }
                    startActivity(ShopDetailsActivity.class, intentShop);
                    finish();
                    return;
                }
            }

            ToastUtils.showToast(Utils.getString(R.string.rule_error));
        }
    }

    /**
     * 解析 "扫描" 的数据
     * @param url       扫描的得到的数据
     * @param rule      是否包含这个"字符串"
     * @return
     */
    public String getRuleRes(String url, String rule) {
        int ruleIndex = url.indexOf(rule);                          // 没有包含，说明这个二维码，不是我们需要的
        if (ruleIndex == -1) {
            return "";
        }

        int pinIndex = url.indexOf("&", ruleIndex);             // 找到"&"所在的下标
        if (pinIndex == -1) {
            pinIndex = url.length();
        }

        String substring = url.substring(ruleIndex, pinIndex);      // 截取
        String[] split = substring.split("=");                // 以等号分开
        if (split.length >= 1) {                                    // 有等号，取对应的值返回
            return split[1];
        }

        return "";
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        ToastUtils.showToast( Utils.getString(R.string.openCamearError));
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (scanview != null) {
            scanview.closeFlashlight();
            if (isOpenFlash) {
                scanview.closeFlashlight();
            }
        }
    }
}

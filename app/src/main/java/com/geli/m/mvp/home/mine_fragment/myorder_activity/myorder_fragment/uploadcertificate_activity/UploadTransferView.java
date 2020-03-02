package com.geli.m.mvp.home.mine_fragment.myorder_activity.myorder_fragment.uploadcertificate_activity;

import com.geli.m.bean.OfflinePaymentSuccess;
import com.geli.m.mvp.base.BaseView;

/**
 * Created by Administrator on 2017/12/6.
 */

public interface UploadTransferView extends BaseView {
    void showBankInfo(OfflinePaymentSuccess data);
    void showUploadReulst(OfflinePaymentSuccess data);
}

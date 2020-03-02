package com.geli.m.mvp.home.mine_fragment.invoice_activity.invoicetype_activity;

import com.geli.m.bean.InvoiceBean;
import com.geli.m.mvp.base.BaseView;
import java.util.List;

/**
 * Created by Steam_l on 2018/1/11.
 *
 */
public interface InvoiceTypeView extends BaseView {
    void showList(List<InvoiceBean.DataEntity> list);
}

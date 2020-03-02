package com.geli.m;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import com.geli.m.api.ApiEngine;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.GetCodeBean;
import com.geli.m.utils.RxUtils;
import com.geli.m.utils.Utils;
import io.reactivex.functions.Consumer;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        System.out.println("=============" + (Utils.md5(GlobalData.Md5Str + GlobalData.getUser_id() + Utils.md5("123456")).toUpperCase()) + "===============");
        ApiEngine.getInstance().getApiService()
                .getCode("13726595135", "4", Utils.md5("13726595135" + Utils.md5(GlobalData.Md5Str)))
                .compose(RxUtils.<GetCodeBean>rxSchedulerHelper())
                .subscribe(new Consumer<GetCodeBean>() {
                    @Override
                    public void accept(GetCodeBean getCodeBean) throws Exception {

                    }
                });


        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        //        assertEquals("com.geli.m", appContext.getPackageName());
        //        Map<String, String> data = new HashMap<>();
        //        data.put("shop_type","2");
        //        data.put("keywords ","公司");
        //        ApiEngine.getInstance().getApiService()
        //                .searchGoods(data)
        //                .compose(RxUtils.<OverseasGoodsOuterBean>rxSchedulerHelper())
        //                .subscribe(new Consumer<OverseasGoodsOuterBean>() {
        //                    @Override
        //                    public void accept(OverseasGoodsOuterBean overseasGoodsOuterBean) throws Exception {
        //
        //                    }
        //                });

        //        Map<String,String> data = new HashMap<>();
        //        Map<String, String> groupbuydata = new HashMap<String, String>();
        //        Map<String, String> futuresdata = new HashMap<String, String>();
        //        Map<String, String> spotdata = new HashMap<String, String>();
        //        data.put("shop_type","1");
        //        data.put("key","呵呵");
        //        data.putAll(groupbuydata);
        //        data.putAll(futuresdata);
        //        data.putAll(spotdata);
        //        groupbuydata.put("type","1");
        //        futuresdata.put("type","2");
        //        spotdata.put("type","3");
        //        System.out.println("="+groupbuydata.toString()+"=\n=="+futuresdata.toString()+"=\n===="+spotdata.toString()+"==\n"+data.toString()+"=================");
    }

}

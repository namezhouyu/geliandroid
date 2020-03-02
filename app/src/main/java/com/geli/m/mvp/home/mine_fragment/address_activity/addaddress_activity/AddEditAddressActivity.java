package com.geli.m.mvp.home.mine_fragment.address_activity.addaddress_activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import chihane.jdaddressselector.AddressProvider;
import chihane.jdaddressselector.AddressSelector;
import chihane.jdaddressselector.OnAddressSelectedListener;
import chihane.jdaddressselector.model.City;
import chihane.jdaddressselector.model.County;
import chihane.jdaddressselector.model.Province;
import chihane.jdaddressselector.model.Street;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.AddressBean;
import com.geli.m.bean.AddressListBean;
import com.geli.m.bean.AlterAddressBean;
import com.geli.m.bean.NewAddressBean;
import com.geli.m.bean.StreetList;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.ToastUtils;
import com.geli.m.utils.Utils;
import com.google.gson.Gson;
import com.tbruyelle.rxpermissions2.RxPermissions;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.geli.m.config.Constant.INTENT_BEAN;

/**
 * Created by Steam_l on 2018/1/5.
 */

public class AddEditAddressActivity extends MVPActivity<AddEditAddressPresenterImpl> implements View.OnClickListener, AddEditAddressView {

    /** 标题 */
    @BindView(R.id.tv_title_name)
    TextView mTvName;
    /** 收货人 */
    @BindView(R.id.et_addaddress_shippingname)
    EditText mEtShippingName;
    /** 电话 */
    @BindView(R.id.et_addaddress_phone)
    EditText mEtPhone;
    /** 通讯录 */
    @BindView(R.id.tv_addaddress_gotoaddbook)
    TextView tv_gotoaddbook;
    /** 所在的地区 -- 请选择地址 */
    @BindView(R.id.tv_addaddress_selectaddress)
    TextView mTvSelectAddress;
    /** 详细地址 */
    @BindView(R.id.et_addaddress_detailsadd)
    EditText mEtDetailsAddress;
    /** 详细地址--字数 */
    @BindView(R.id.tv_addaddress_detailsadd_number)
    TextView mTvDetailsAddressNum;
    /** 设置默认地址 */
    @BindView(R.id.cb_addaddress_isdefault)
    CheckBox mCbIsDefault;
    /** 保存按钮 */
    @BindView(R.id.bt_addaddress_save)
    Button mBtSave;

    private AlertDialog mAlertDialog;
    private AddressBean mAddressBean;
    private AddressProvider.AddressReceiver<Street> mAddressReceiver;
    private String mStreetId;
    private String mCountyId;
    private String mCityId;
    private String mCity;
    private String mProvinceId;
    private String mProvince;
    private String MessageX;//提示消息
    private AddressListBean.DataEntity mCurrBean;

    @Override
    protected int getResId() {
        return R.layout.activity_addaddress;
    }

    @Override
    protected void init() {
        super.init();
        mCurrBean = getIntent().getParcelableExtra(INTENT_BEAN);
    }

    @Override
    protected void initData() {
        setView();
        parse(setAddressSpinner());
    }



    @Override
    protected void initEvent() {
        setEtTextListener();
    }


    private void setView() {
        if (mCurrBean != null) {                                                /* 修改 */
            mTvName.setText(Utils.getString(R.string.add_shippingmodify));
            mBtSave.setText(Utils.getString(R.string.modify));
            mEtShippingName.setText(mCurrBean.getConsignee());
            mEtPhone.setText(mCurrBean.getMobile());
            mTvSelectAddress.setText(mCurrBean.getAdd());
            mEtDetailsAddress.setText(mCurrBean.getAddress());
            mTvDetailsAddressNum.setText(mCurrBean.getAddress().length() + "/50");
            mCbIsDefault.setChecked(mCurrBean.getIs_default() == 1);
            mProvinceId = mCurrBean.getP_id() + "";
            mCityId = mCurrBean.getC_id() + "";
            mCountyId = mCurrBean.getE_id() + "";
            mStreetId = mCurrBean.getD_id() + "";
        } else {                                                                /* 添加 */
            mTvName.setText(Utils.getString(R.string.add_shippingaddress));
        }
    }

    /**
     * 地址json数据转换成 bean
     * @param address
     */
    private void parse(String address) {
        Gson gson = new Gson();
        mAddressBean = gson.fromJson(address, AddressBean.class);
    }

    /**
     * 从本地文件中获取地址文件，将里面的数据读取出来组合成字符串
     */
    private String setAddressSpinner() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = mContext.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open("address.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 设置编辑框的字数限制
     */
    private void setEtTextListener() {
        mEtDetailsAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = mEtDetailsAddress.getText().toString().length();
                if (length > 50) {
                    ToastUtils.showToast(Utils.getString(R.string.limit_exceeded)); // 超出限制
                    int editStart = mEtDetailsAddress.getSelectionStart();
                    int editEnd = mEtDetailsAddress.getSelectionEnd();
                    s.delete(editStart - 1, editEnd);
                    mEtDetailsAddress.setText(s);
                    mEtDetailsAddress.setSelection(s.length());     // 设置光标位置
                    return;
                }
                mTvDetailsAddressNum.setText(length + "/50");
            }
        });
    }



    @OnClick({R.id.tv_addaddress_gotoaddbook, R.id.tv_addaddress_selectaddress, R.id.bt_addaddress_save, R.id.iv_title_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_addaddress_gotoaddbook:        /* 通讯录 */
                gotoAddressBook();
                break;

            case R.id.tv_addaddress_selectaddress:      /* 请选择地址 */
                showChoiceAddress();
                break;

            case R.id.bt_addaddress_save:               /* 保存 */
                saveOrEdit();
                break;

            case R.id.iv_title_back:
                finish();
                break;

            default:
                break;
        }
    }


    /**
     * 点击通讯录 -- 获取权限 -- 去到通讯录界面
     */
    private void gotoAddressBook() {
        new RxPermissions((Activity) mContext)
                .request(Manifest.permission.READ_CONTACTS, Manifest.permission.GET_ACCOUNTS)
                .subscribe(new Observer<Boolean>() {
                               @Override
                               public void onSubscribe(Disposable d) {

                               }

                               @Override
                               public void onNext(Boolean aBoolean) {
                                    if (aBoolean) {
                                        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                                        startActivityForResult(intent, 0);
                                    } else {
                                        Utils.showMissPermissionDialog(mContext, Utils.getString(R.string.addaddress_selectperson), Utils.getString(R.string.permissionselectcontent));
                                    }
                               }

                               @Override
                               public void onError(Throwable e) {

                               }

                               @Override
                               public void onComplete() {

                               }
                           });
    }


    /**
     * 请选择地址 -- 初始化对话框
     */
    private void showChoiceAddress() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.MyDialogStyleWhiteBg);
        mAlertDialog = builder.create();
        AddressSelector selector = new AddressSelector(mContext);
        selector.setAddressProvider(new MyAddressProvider());
        selector.setOnAddressSelectedListener(new MyOnAddressSelectedListener());
        View view = selector.getView();
        mAlertDialog.setView(view);
        mAlertDialog.show();

        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();              // 为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p;
        p = mAlertDialog.getWindow().getAttributes();   // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.5);         // 高度设置为屏幕的0.5
        p.width = -1;                                   // 宽度设置为屏幕
        p.gravity = Gravity.BOTTOM;                     // 位置底部
        mAlertDialog.getWindow().setAttributes(p);      // 设置生效
    }

    /**
     * 保存地址
     */
    private void saveOrEdit() {
        String phone = mEtPhone.getText().toString().trim();
        String name = mEtShippingName.getText().toString().trim();
        String detailsadd = mEtDetailsAddress.getText().toString().trim();
        if (TextUtils.isEmpty(phone) ||
                TextUtils.isEmpty(name) ||
                TextUtils.isEmpty(detailsadd) ||
                TextUtils.isEmpty(mProvinceId)) {
            ToastUtils.showToast(mContext, Utils.getString(R.string.message_pelasefillinfo));
            return;
        }
        Map data = new HashMap();
        data.put("user_id", GlobalData.getUser_id());
        data.put("consignee", mEtShippingName.getText().toString().trim());
        data.put("province", mProvinceId);
        data.put("city", mCityId);
        data.put("district", mCountyId);
        data.put("street", mStreetId);
        data.put("address", mEtDetailsAddress.getText().toString().trim());
        data.put("zipcode", "");
        data.put("mobile", mEtPhone.getText().toString().trim());
        data.put("tel", "");
        data.put("is_default", mCbIsDefault.isChecked() ? 1 : 0);

        if (mCurrBean != null) {                                // 修改
            data.put("add_id", mCurrBean.getAddress_id());
        }
        mPresenter.saveAddress(data);
    }




    @Override
    protected AddEditAddressPresenterImpl createPresenter() {
        return new AddEditAddressPresenterImpl(this);
    }

    @Override
    public void onSuccess(String msg) {
        if (mCurrBean == null) {
            MessageX = Utils.getString(R.string.message_addsuccess);
        } else {
            MessageX = Utils.getString(R.string.message_modifysuccess);
        }
        ShowSingleToast.showToast(mContext, MessageX);
        finish();
    }

    @Override
    public void onError(String msg) {
        ShowSingleToast.showToast(mContext, msg);
    }

    @Override
    public void showLoading() {
        Utils.uProgressDialog(mContext);
    }

    @Override
    public void hideLoading() {
        Utils.dismissProgress();
    }

    @Override
    public void getStreetSuccess(StreetList data) {
        if (data != null && data.getData() != null && data.getData().size() > 0) {
            List<Street> streets = new ArrayList<Street>();
            for (StreetList.DataBean dataBean : data.getData()) {
                Street street = new Street();
                street.id = dataBean.getArea_id();
                street.name = dataBean.getArea_name();
                streets.add(street);
            }
            mAddressReceiver.send(streets);         // 结束选择 -- 调用监听接口
            return;
        } else {
            mAddressReceiver.send(null);        //没有选择街道 -- 结束选择 -- 调用监听接口
        }
    }

    @Override
    public void onError() {
        if (mCurrBean == null) {
            MessageX = Utils.getString(R.string.message_addfailure);
        } else {
            MessageX = Utils.getString(R.string.message_modifyfailure);
        }
        ShowSingleToast.showToast(mContext, MessageX);
    }

    @Override
    public void saveSuccess(NewAddressBean data) {

    }

    @Override
    public void showAddressInfo(AlterAddressBean data) {

    }

    /**
     * 地址选择器 -- 结果监听 -- 全部选择完了才调用这一步
     */
    private class MyOnAddressSelectedListener implements OnAddressSelectedListener {

        @Override
        public void onAddressSelected(Province province, City city, County county, Street street) {
            String text = "";
            mCityId = "";
            mCountyId = "";
            mStreetId = "";
            if (!province.name.isEmpty()) {                         // 有选择了"省"
                text = getProvinceText(province, text);
            }

            if (city != null) {
                if (!city.name.isEmpty()) {                         // city有数据 -- 香港台湾之类的没有数据的
                    text += city.name;
                    mCity = city.name;
                    mCityId = String.valueOf(city.id);
                }

                if (county != null) {
                    text += county.name;
                    mCountyId = String.valueOf(county.id);
                }

                if (street != null) {
                    text += street.name;
                    mStreetId = String.valueOf(street.id);
                }
            }

            mTvSelectAddress.setText(text);
            if (mAlertDialog != null) {
                mAlertDialog.dismiss();
            }
        }

        @NonNull
        private String getProvinceText(Province province, String text) {
            text += province.name;
            mProvince = province.name;
            mProvinceId = String.valueOf(province.id);
            if (province.name.trim().equals("台湾省") ||
                    province.name.trim().equals("香港特别行政区") ||
                    province.name.trim().equals("澳门特别行政区")) {
                mCityId = "";
                mCountyId = "";
                mStreetId = "";
            }
            return text;
        }
    }

    /**
     * "京东地址选择器" 自定义数据源
     */
    private class MyAddressProvider implements chihane.jdaddressselector.AddressProvider {

        /* 省 */
        @Override
        public void provideProvinces(AddressReceiver<Province> addressReceiver) {
            List<Province> provinces = new ArrayList<Province>();       // jdaddressselector 里面的类
            for (AddressBean.Province province : mAddressBean.getData()) {
                Province p = new Province();
                p.id = province.getArea_id();
                p.name = province.getArea_name();
                provinces.add(p);
            }
            addressReceiver.send(provinces); // 调用"市" provideCitiesWith
        }

        /* 市 */
        @Override
        public void provideCitiesWith(int provinceId, AddressReceiver<City> addressReceiver) {
            List<City> cities = new ArrayList<City>();
            for (AddressBean.Province province : mAddressBean.getData()) {
                if (province.getArea_id() == provinceId) {
                    if (province.getCity_list().size() == 0) {
                        addressReceiver.send(null);             // 结束选择 -- 调用监听接口
                        return;
                    }
                    for (AddressBean.Province.City city : province.getCity_list()) {
                        City c = new City();
                        c.id = city.getArea_id();
                        c.name = city.getArea_name();
                        c.province_id = provinceId;
                        cities.add(c);
                    }
                    addressReceiver.send(cities);   // 调用"县/区" provideCountiesWith
                    return;
                }
            }
        }

        /* 县/区 */
        @Override
        public void provideCountiesWith(int cityId, AddressReceiver<County> addressReceiver) {
            List<County> counties = new ArrayList<County>();
            for (AddressBean.Province province : mAddressBean.getData()) {              // 省
                for (AddressBean.Province.City city : province.getCity_list()) {        // 市
                    if (city.getArea_id() == cityId) {
                        for (AddressBean.Province.City.County c : city.getDistrict_list()) {
                            County county = new County();
                            county.id = c.getArea_id();
                            county.name = c.getArea_name();
                            county.city_id = cityId;
                            counties.add(county);
                        }
                        addressReceiver.send(counties); // 调用"街道" provideStreetsWith
                        return;
                    }
                }

            }
            addressReceiver.send(null);
        }

        /* 街道 */
        @Override
        public void provideStreetsWith(int countyId, final AddressReceiver<Street> addressReceiver) {
            mAddressReceiver = addressReceiver;
            mPresenter.getStree(countyId + "");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            ContentResolver reContentResolverol = getContentResolver();
            Uri contactData = data.getData();

            @SuppressWarnings("deprecation")
            Cursor cursor = managedQuery(contactData, null, null, null, null);
            if (cursor.getCount() == 0) {
                ShowSingleToast.showToast(mContext, Utils.getString(R.string.message_contactinfomationavailable));
                return;
            }

            try {
                cursor.moveToFirst();
                String username = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor phoneCursor = reContentResolverol.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                        null,
                        null);

                while (phoneCursor.moveToNext()) {
                    String phone = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    mEtShippingName.setText(username + "");
                    mEtPhone.setText(phone + "");
                }
            } catch (Exception e) {
                e.printStackTrace();
                ShowSingleToast.showToast(mContext, Utils.getString(R.string.message_contactinfomationavailable));
            }
        }
    }
}

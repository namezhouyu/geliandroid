package com.geli.m.mvp.home.mine_fragment.mywallet_activity.withdraw_activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.geli.m.R;
import com.geli.m.app.GlobalData;
import com.geli.m.bean.BankListBean;
import com.geli.m.config.Constant;
import com.geli.m.dialog.InputPassDialog;
import com.geli.m.mvp.base.MVPActivity;
import com.geli.m.mvp.home.mine_fragment.mywallet_activity.banklist_activity.BankListActivity;
import com.geli.m.mvp.home.mine_fragment.mywallet_activity.banklist_activity.BankListView;
import com.geli.m.utils.GlideUtils;
import com.geli.m.utils.KeyBoardUtils;
import com.geli.m.utils.ShowSingleToast;
import com.geli.m.utils.Utils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Steam_l on 2018/1/18.
 * 提现
 */
public class WithdrawActivity extends MVPActivity<WithdrawPresentImpl> implements View.OnClickListener, BankListView {
    @BindView(R.id.tv_title_name)
    TextView tv_title;
    @BindView(R.id.et_withdraw_money)
    EditText et_money;
    @BindView(R.id.fl_itemview_banklist_rootlayout)
    FrameLayout rl_bank_root;
    @BindView(R.id.tv_itemview_banklist_bankname)
    TextView tv_bankname;
    @BindView(R.id.tv_itemview_banklist_banknumber)
    TextView tv_banknumber;
    @BindView(R.id.tv_itemview_banklist_banktype)
    TextView tv_banktype;
    @BindView(R.id.iv_itemview_banklist_img)
    ImageView iv_bankimg;
    @BindView(R.id.iv_itemview_banklist_typeimg)
    ImageView iv_banktypeimg;
    @BindView(R.id.rl_include_nobank)
    RelativeLayout rl_bank_no;
    @BindView(R.id.tv_withdraw_walletmoney)
    TextView tv_walletmoney;
    private List<BankListBean.DataEntity> mDataEntities;
    public static final String INTENT_MONEY = "intent_money";
    private String curr_money = "0";
    private String curr_selectbankid;
    private InputPassDialog mInputPassDialog;

    @Override
    protected int getResId() {
        return R.layout.activity_withdraw;
    }

    @Override
    protected void init() {
        super.init();
        curr_money = getIntent().getStringExtra(INTENT_MONEY);
    }

    @Override
    protected void initData() {
        tv_title.setText(Utils.getString(R.string.withdraw));
        tv_walletmoney.setText(Utils.getString(R.string.small_change_money, curr_money));
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (TextUtils.isEmpty(curr_selectbankid)) {
            mPresenter.getList(GlobalData.getUser_id());
        }
    }

    @OnClick({R.id.tv_withdraw_selectbank, R.id.tv_withdraw_all, R.id.bt_withdraw, R.id.tv_include_nobank_addbank, R.id.iv_title_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.tv_include_nobank_addbank:
                startActivityForResult(new Intent(mContext, BankListActivity.class).putExtra(BankListActivity.INTENT_TYPE, BankListActivity.TYPE_ADD), Constant.REQUEST_OK);
                break;
            case R.id.bt_withdraw:
                if (TextUtils.isEmpty(curr_selectbankid)) {
                    ShowSingleToast.showToast(mContext, Utils.getString(R.string.please_select_bank));
                    return;
                }
                final String money = et_money.getText().toString().trim();
                if (TextUtils.isEmpty(money)) {
                    ShowSingleToast.showToast(mContext, Utils.getString(R.string.please_input_money));
                    return;
                }
                mInputPassDialog = InputPassDialog.newInstance(money);
                mInputPassDialog.setInputCompleteListener(new InputPassDialog.InputCompleteListener() {
                    @Override
                    public void inputComplete(String pass, String price) {
                        Map data = new HashMap();
                        data.put("user_id", GlobalData.getUser_id());
                        data.put("sign", Utils.md5(GlobalData.Md5Str + GlobalData.getUser_id() + Utils.md5(pass)).toUpperCase());
                        data.put("pay_pwd", Utils.md5(pass));
                        data.put("bk_id", curr_selectbankid);
                        data.put("money", money);
                        mPresenter.withdraw(data);
                    }
                });
                mInputPassDialog.show(getSupportFragmentManager(), "");
                break;
            case R.id.tv_withdraw_all:
                et_money.setText(curr_money);
                break;
            case R.id.tv_withdraw_selectbank:
                if ((null == mDataEntities || mDataEntities.size() == 0) && TextUtils.isEmpty(curr_selectbankid)) {
                    ShowSingleToast.showToast(mContext, Utils.getString(R.string.no_have_bank));
                    return;
                }
                startActivityForResult(new Intent(mContext, BankListActivity.class).putExtra(BankListActivity.INTENT_TYPE, BankListActivity.TYPE_SELECT), Constant.REQUEST_OK);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_OK && requestCode == Constant.REQUEST_OK) {
            if (null != data && null != data.getParcelableExtra(BankListActivity.INTENT_DATA)) {
                setBankData((BankListBean.DataEntity) data.getParcelableExtra(BankListActivity.INTENT_DATA));
            }
        }
    }

    @Override
    protected WithdrawPresentImpl createPresenter() {
        return new WithdrawPresentImpl(this);
    }

    @Override
    public void onSuccess(String msg) {
        ShowSingleToast.showToast(mContext, msg);
        KeyBoardUtils.closeKeybord(et_money, mContext);
        mInputPassDialog.dismiss();
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
    public void showList(List<BankListBean.DataEntity> dataEntities) {
        if (null != dataEntities && dataEntities.size() > 0) {
            mDataEntities = dataEntities;
            BankListBean.DataEntity dataEntity = mDataEntities.get(0);
            setBankData(dataEntity);
        } else {
            rl_bank_no.setVisibility(View.VISIBLE);
            rl_bank_root.setVisibility(View.GONE);
        }
    }

    private void setBankData(BankListBean.DataEntity dataEntity) {
        rl_bank_no.setVisibility(View.GONE);
        rl_bank_root.setVisibility(View.VISIBLE);
        tv_bankname.setText(dataEntity.getBank_category_number());
        tv_banktype.setText(dataEntity.getCard_type());
        String bank_img = dataEntity.getBank_img();
        if (TextUtils.isEmpty(bank_img)) {
            bank_img = "";
        }
        GlideUtils.loadImg(mContext, bank_img, iv_bankimg);
        String banknumber = dataEntity.getUnion_pay_number();
        banknumber = banknumber.substring(banknumber.length() - 4, banknumber.length());
        tv_banknumber.setText(Utils.getString(R.string.bank_enctypt, banknumber));
        curr_selectbankid = dataEntity.getBk_id();
    }
}

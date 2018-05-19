package com.gaoyuan.televition.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaoyuan.televition.App;
import com.gaoyuan.televition.LineControlView;
import com.gaoyuan.televition.R;
import com.gaoyuan.televition.base.BaseActivity;
import com.gaoyuan.televition.retrofit.RetrofitClient;
import com.gaoyuan.televition.retrofit.RxUtils;
import com.gaoyuan.televition.utils.SharedPreferencesUtil;

public class MyActivity extends BaseActivity implements View.OnClickListener {

    private android.widget.LinearLayout mLlInformation;
    private android.widget.ImageView mIvAvatar;
    private android.widget.TextView mTvName;
    private android.widget.TextView mTvId;
    private android.widget.TextView mTvInvitate;
    private LineControlView mQrcodeMine;
    private LineControlView mWalletMine;
    private LineControlView mVipMine;
    private LineControlView mGotmonyMine;
    private  LineControlView mUimoduleItemMainListTv;
    @Override
    protected boolean hasActionBar() {
        return true;
    }

    @Override
    protected Object getIdOrView() {
        return R.layout.activity_my;
    }

    @Override
    protected void initView() {
        setToolbar("个人中心",true);

        mLlInformation = (LinearLayout) findViewById(R.id.ll_information);
        mIvAvatar = (ImageView) findViewById(R.id.iv_avatar);
        mTvName = (TextView) findViewById(R.id.tv_name);
        mTvId = (TextView) findViewById(R.id.tv_id);
        mTvInvitate = (TextView) findViewById(R.id.tv_invitate);
        mQrcodeMine = (LineControlView) findViewById(R.id.qrcode_mine);
        mWalletMine = (LineControlView) findViewById(R.id.wallet_mine);
        mVipMine = (LineControlView) findViewById(R.id.vip_mine);
        mGotmonyMine = (LineControlView) findViewById(R.id.gotmony_mine);
        mUimoduleItemMainListTv = (LineControlView) findViewById(R.id.uimodule_item_main_list_tv);

        mTvId.setText("ID:" + App.id);
        mTvName.setText(App.phone);
    }

    @Override
    protected void setListener() {
        mQrcodeMine.setOnClickListener(this);
        mWalletMine.setOnClickListener(this);
        mVipMine.setOnClickListener(this);
        mGotmonyMine.setOnClickListener(this);
        mUimoduleItemMainListTv.setOnClickListener(this);
        mIvAvatar.setOnClickListener(this);    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.qrcode_mine://二维码
//                showQrCode();
                break;

            case R.id.vip_mine://会员
//                ActivityUtils.startActivity(VipActivity.class);
                break;

            case R.id.uimodule_item_main_list_tv://设置
//                ActivityUtils.startActivity(SettingActivity.class);
                break;

        }
    }

//    private void getBaseInfo(){
//        RetrofitClient.getInstance().createApi().getUserInfo(App.token)
//                .compose(RxUtils.)
//    }
}

package com.gaoyuan.televition.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.gaoyuan.televition.App;
import com.gaoyuan.televition.MainActivity;
import com.gaoyuan.televition.R;
import com.gaoyuan.televition.base.BaseActivity;
import com.gaoyuan.televition.retrofit.BaseObjObserver;
import com.gaoyuan.televition.retrofit.CommonBean;
import com.gaoyuan.televition.retrofit.HttpResult;
import com.gaoyuan.televition.retrofit.RetrofitClient;
import com.gaoyuan.televition.retrofit.RxUtils;
import com.gaoyuan.televition.ui.bean.LoginBean;
import com.gaoyuan.televition.utils.ImageUtils;
import com.gaoyuan.televition.utils.SharedPreferencesUtil;
import com.gaoyuan.televition.utils.StatusBarUtil;
import com.gaoyuan.televition.utils.ToastUtils;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    //
    private android.widget.ImageView ivLogo;
    private android.widget.EditText etPhonenumber;
    private android.widget.EditText etVerificacode;
    private android.widget.TextView btnPhoneLoginSendCode;
    private android.widget.EditText etPassword;
    private android.widget.EditText etPasswordagain;
    private android.widget.EditText etRecommend;
    private android.widget.Button btnDologin;
    private String phone;
    private String pwd;

    @Override
    protected boolean hasActionBar() {
        return false;
    }

    @Override
    protected Object getIdOrView() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        StatusBarUtil.getInstance().darkMode(this);

        ivLogo = (ImageView) findViewById(R.id.iv_logo);
        etPhonenumber = (EditText) findViewById(R.id.et_phonenumber);
        etVerificacode = (EditText) findViewById(R.id.et_verificacode);
        btnPhoneLoginSendCode = (TextView) findViewById(R.id.btn_phone_login_send_code);
        etPassword = (EditText) findViewById(R.id.et_password);
        etPasswordagain = (EditText) findViewById(R.id.et_passwordagain);
        etRecommend = (EditText) findViewById(R.id.et_recommend);
        btnDologin = (Button) findViewById(R.id.btn_doReg);

    }

    @Override
    protected void setListener() {
        btnDologin.setOnClickListener(this);
        btnPhoneLoginSendCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_phone_login_send_code:
                getVerficaCode();
                break;
            case R.id.btn_doReg:
                register();
                break;
            default:
                break;
        }
    }

    private void getVerficaCode() {
        String phone = etPhonenumber.getText().toString();
        if (phone.length() != 11) {
            Toast.makeText(mContext, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    private void register() {
        phone = etPhonenumber.getText().toString();
        pwd = etPassword.getText().toString();
//        String pwdAgain = etPasswordagain.getText().toString();
        try {
            pwd = URLEncoder.encode(pwd, "UTF-8");
//            pwdAgain = URLEncoder.encode(pwdAgain, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        String verficaCode = etVerificacode.getText().toString();
        String recommend = etRecommend.getText().toString();
        if (phone.length() != 11) {
            Toast.makeText(mContext, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
//        if (verficaCode.isEmpty()) {
//            ToastUtils.showShort("请输入验证码");
//            return;
//        }
        if (pwd.length() < 6 || pwd.length() > 16) {
            Toast.makeText(mContext, "请输入6-16位密码", Toast.LENGTH_SHORT).show();
            return;
        }
//        if (!pwd.equals(pwdAgain)) {
//            ToastUtils.showShort("两次输入的密码不一样");
//            return;
//        }
//        if (recommend.isEmpty()) {
//            Toast.makeText(mContext, "请输入邀请人ID", Toast.LENGTH_SHORT).show();
//            return;
//        }

        RetrofitClient.getInstance().createApi().register(phone, pwd)
                .compose(RxUtils.<HttpResult<CommonBean>>io_main())
                .subscribe(new BaseObjObserver<CommonBean>(this, "注册中") {
                    @Override
                    protected void onHandleSuccess(CommonBean commonBean) {
                        ToastUtils.showToast("注册成功");
                        login();
                    }
                });
    }


    /**
     * 登录
     */
    private void login() {
        RetrofitClient.getInstance().createApi().login(phone, pwd)
                .compose(RxUtils.<HttpResult<LoginBean>>io_main())
                .subscribe(new BaseObjObserver<LoginBean>(this, "登录中") {
                    @Override
                    protected void onHandleSuccess(LoginBean loginBean) {
                        SharedPreferencesUtil.getInstance().putString("token", loginBean.getToken());
                        SharedPreferencesUtil.getInstance().putString("phone", loginBean.getNickname());
                        SharedPreferencesUtil.getInstance().putString("id", loginBean.getUid()+"");
                        App.phone = loginBean.getNickname();
                        App.token = loginBean.getToken();
                        App.token = loginBean.getUid()+"";
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        finish();
                    }
                });
    }
}

package com.gaoyuan.televition.ui;

import android.content.Intent;
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
import com.gaoyuan.televition.retrofit.HttpResult;
import com.gaoyuan.televition.retrofit.RetrofitClient;
import com.gaoyuan.televition.retrofit.RxUtils;
import com.gaoyuan.televition.ui.bean.LoginBean;
import com.gaoyuan.televition.utils.SharedPreferencesUtil;
import com.gaoyuan.televition.utils.StatusBarUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private android.widget.ImageView ivLogo;
    private android.widget.Button btnDologin;
    private android.widget.TextView tvFindPass;
    private android.widget.TextView btnDoReg;
    private android.widget.EditText etUsername;
    private android.widget.EditText etPassword;
    private String username;
    private String password;

    @Override
    protected boolean hasActionBar() {
        return false;
    }

    @Override
    protected Object getIdOrView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        StatusBarUtil.getInstance().darkMode(this);
        ivLogo = (ImageView) findViewById(R.id.iv_logo);
        btnDologin = (Button) findViewById(R.id.btn_dologin);
        tvFindPass = (TextView) findViewById(R.id.tv_findPass);
        btnDoReg = (TextView) findViewById(R.id.btn_doReg);
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        tvFindPass.setOnClickListener(this);
        btnDologin.setOnClickListener(this);
        btnDoReg.setOnClickListener(this);
    }

    private long firstTime = 0;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - firstTime > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            firstTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_dologin:
                login();
                break;
            case R.id.btn_doReg:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.tv_findPass:
                startActivity(new Intent(this, FindPassActivity.class));
                break;
            default:
                break;
        }
    }

    private void login() {
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();
        try {
            password = URLEncoder.encode(password, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (username.length() != 11) {
            Toast.makeText(mContext, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6 || password.length() > 16) {
            Toast.makeText(mContext, "请输入6-16位密码", Toast.LENGTH_SHORT).show();
            return;
        }

        RetrofitClient.getInstance().createApi().login(username, password)
                .compose(RxUtils.<HttpResult<LoginBean>>io_main())
                .subscribe(new BaseObjObserver<LoginBean>(this, "登录中") {
                    @Override
                    protected void onHandleSuccess(LoginBean loginBean) {
                        SharedPreferencesUtil.getInstance().putString("token", loginBean.getToken());
                        SharedPreferencesUtil.getInstance().putString("phone", loginBean.getNickname());
                        App.phone = loginBean.getNickname();
                        App.token = loginBean.getToken();

                        startActivity(new Intent(LoginActivity.this, MainActivity.class));

                        finish();
                    }
                });
    }


}

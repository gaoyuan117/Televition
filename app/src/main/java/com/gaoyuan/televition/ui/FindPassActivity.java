package com.gaoyuan.televition.ui;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.gaoyuan.televition.R;
import com.gaoyuan.televition.base.BaseActivity;

import java.lang.ref.WeakReference;
import java.util.List;


public class FindPassActivity extends BaseActivity implements View.OnClickListener {

    private android.widget.EditText mEtPhonenumber;
    private android.widget.EditText mEtVerificacode;
    private android.widget.TextView mBtnPhoneLoginSendCode;
    private android.widget.EditText mEtPassword;
    private android.widget.EditText mEtPasswordagain;
    private android.widget.Button mBtnConfirm;

    @Override
    protected boolean hasActionBar() {
        return true;
    }

    @Override
    protected Object getIdOrView() {
        return R.layout.activity_find_pass;
    }

    @Override
    protected void initView() {
        setToolbar("找回密码", true);
        mEtPhonenumber = (EditText) findViewById(R.id.et_phonenumber);
        mEtVerificacode = (EditText) findViewById(R.id.et_verificacode);
        mBtnPhoneLoginSendCode = (TextView) findViewById(R.id.btn_phone_login_send_code);
        mEtPassword = (EditText) findViewById(R.id.et_password);
        mEtPasswordagain = (EditText) findViewById(R.id.et_passwordagain);
        mBtnConfirm = (Button) findViewById(R.id.btn_confirm);
    }

    @Override
    protected void setListener() {
        mBtnPhoneLoginSendCode.setOnClickListener(this);
        mBtnConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_phone_login_send_code:
                getVerficaCode();
                break;
            case R.id.btn_doReg:
                confirm();
                break;
            default:
                break;
        }
    }

    private void confirm() {
        String phone = mEtPhonenumber.getText().toString();
        String pwd = mEtPassword.getText().toString();
        String pwdAgain = mEtPasswordagain.getText().toString();
        String verficaCode = mEtVerificacode.getText().toString();
        if (phone.length() != 11) {
            Toast.makeText(mContext, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pwd.length() < 6 || pwd.length() > 16) {
            Toast.makeText(mContext, "请输入6-16位密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!pwd.equals(pwdAgain)) {
            Toast.makeText(mContext, "两次输入的密码不一样", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    private void getVerficaCode() {
        String phone = mEtPhonenumber.getText().toString();
        if (phone.length() != 11) {
            Toast.makeText(mContext, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }

    }
}

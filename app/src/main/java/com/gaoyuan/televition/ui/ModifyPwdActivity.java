package com.gaoyuan.televition.ui;

import android.app.ProgressDialog;
import android.view.View;


import com.gaoyuan.televition.R;
import com.gaoyuan.televition.base.BaseActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class ModifyPwdActivity extends BaseActivity implements View.OnClickListener {

    private android.widget.EditText mEtOldpassword;
    private android.widget.EditText mEtNewpassword;
    private android.widget.EditText mEtConfirmpassword;
    private android.widget.Button mBtnConfirm;

    @Override
    protected boolean hasActionBar() {
        return true;
    }

    @Override
    protected Object getIdOrView() {
        return 1;
    }

    @Override
    protected void initView() {
//        mEtOldpassword = findViewById(R.id.et_oldpassword);
//        mEtNewpassword = findViewById(R.id.et_newpassword);
//        mEtConfirmpassword = findViewById(R.id.et_confirmpassword);
//        mBtnConfirm = findViewById(R.id.btn_confirm);
//        setToolbar("修改密码", true);
    }

    @Override
    protected void setListener() {
        mBtnConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String oldPassword = mEtOldpassword.getText().toString();
        String newpassword = mEtNewpassword.getText().toString();
        String confirmPassword = mEtConfirmpassword.getText().toString();

        if (oldPassword.length() < 6) {
//            ToastUtils.showShort("请输入6-16位老密码");
            return;
        }
        if (newpassword.length() < 6) {
//            ToastUtils.showShort("请输入6-16位新密码");
            return;
        }
        if (!confirmPassword.equals(newpassword)) {
//            ToastUtils.showShort("请再次输入新密码，确保一致");
            return;
        }


    }
}

package com.gaoyuan.televition.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gaoyuan.televition.R;
import com.gaoyuan.televition.base.BaseActivity;

public class MyActivity extends BaseActivity {

    @Override
    protected boolean hasActionBar() {
        return false;
    }

    @Override
    protected Object getIdOrView() {
        return R.layout.activity_my;
    }

    @Override
    protected void initView() {
    }
}

package com.gaoyuan.televition.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaoyuan.televition.R;
import com.gaoyuan.televition.ui.MyActivity;
import com.gaoyuan.televition.utils.StatusBarUtil;


public abstract class BaseActivity extends AppCompatActivity {
    protected final String TAG = this.getClass().getSimpleName();
    public Toolbar mToolbar;
    private TextView titleView;
    protected Context mContext;
    protected View rootView;
    private LinearLayout root;

    protected abstract boolean hasActionBar();

    protected abstract Object getIdOrView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        StatusBarUtil.getInstance().immersive(this);
        View view;
        if (getIdOrView() instanceof Integer) {
            view = View.inflate(this, (Integer) getIdOrView(), null);
        } else if (getIdOrView() instanceof View) {
            view = (View) getIdOrView();
        } else {
            throw new ClassCastException("getIdOrView only be int or View");
        }
        root = (LinearLayout) View.inflate(this, R.layout.layout_root, null);

        if (hasActionBar()) {
            root.addView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mToolbar = (Toolbar) root.findViewById(R.id.toolbar);
            mToolbar.setTitle("");
            titleView = (TextView) root.findViewById(R.id.toolbar_title);
            setSupportActionBar(mToolbar);
            StatusBarUtil.getInstance().setPaddingSmart(this, mToolbar);
            setContentView(root);
        } else {
            setContentView(view);
        }
        initView();
        initData();
        setListener();
    }

    public void setRight(View.OnClickListener listener){
        ImageView img = (ImageView) root.findViewById(R.id.img_wde);
        img.setVisibility(View.VISIBLE);
        img.setOnClickListener(listener);
    }

    protected void setListener() {
    }

    protected void initData() {
    }

    protected void initView() {
    }

    public void setToolbar(String title) {
        setToolbar(title, false);
    }

    @SuppressLint("RestrictedApi")
    public void setToolbar(String title, boolean needBack) {
        if (!hasActionBar()) {
            return;
        }
        if (needBack) {
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationIcon(R.mipmap.icon_back);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        titleView.setText(title);
    }
}

package com.gaoyuan.televition;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gaoyuan.televition.base.BaseActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WebViewActivity extends BaseActivity {

    private LinearLayout layout;
    private WebView webView;
    private String url;
    private String playUrl;
    private ProgressBar pbProgress;
    FrameLayout mVideoContainer;
    LineAdapter adapter;
    List<String> list = new ArrayList<>();
    ProgressDialog dialog;

    @Override
    protected boolean hasActionBar() {
        return true;
    }

    @Override
    protected Object getIdOrView() {
        return R.layout.activity_web_view;
    }


    @Override
    protected void initView() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("解析中");
        list.addAll(App.list);

        final String title = getIntent().getStringExtra("title");
        layout = (LinearLayout) findViewById(R.id.layout);
        pbProgress = (ProgressBar) findViewById(R.id.pb_progress);
        webView = (WebView) findViewById(R.id.webview);
        mVideoContainer = (FrameLayout) findViewById(R.id.videoContainer);

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.play://播放
                        setBackgroundAlpha(WebViewActivity.this, 0.8f);
                        lineListPop();
                        break;
                }
                return true;
            }
        });

        url = getIntent().getStringExtra("url");
        setToolbar(title, true);
        WebSettings localWebSettings = webView.getSettings();
        localWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        localWebSettings.setJavaScriptEnabled(true);
        localWebSettings.setSupportZoom(true);
        localWebSettings.setDefaultTextEncodingName("utf-8");
        localWebSettings.setLoadWithOverviewMode(true);
        localWebSettings.setAppCacheEnabled(true);
        localWebSettings.setDomStorageEnabled(true);
        localWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        localWebSettings.setBuiltInZoomControls(true);
        localWebSettings.setPluginState(WebSettings.PluginState.ON);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)//图片不显示
            localWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webView.loadUrl(url);
        webView.setWebViewClient(new myWebClient());
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    // 网页加载完成
                    pbProgress.setVisibility(View.GONE);
                } else {
                    // 加载中
                    pbProgress.setVisibility(View.VISIBLE);
                    pbProgress.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }


        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.webview, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public class myWebClient extends WebViewClient {


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.e("gy", "URL：" + url);
            playUrl = url;
            WebView.HitTestResult hitTestResult = view.getHitTestResult();

            if (!TextUtils.isEmpty(url) && hitTestResult == null) {
                view.loadUrl(url);
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            webView.stopLoading();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.e("gy", "onPageFinished：" + url);
            playUrl = url;
            super.onPageFinished(view, url);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void lineListPop() {
        View view = View.inflate(this, R.layout.pop_line, null);
        final ListView listView = (ListView) view.findViewById(R.id.pop_line_list);
        adapter = new LineAdapter(this, list);
        listView.setAdapter(adapter);
        final PopupWindow window = new PopupWindow(this);
        window.setContentView(view);
        window.setBackgroundDrawable(new ColorDrawable());
        window.setOutsideTouchable(true);
        window.setFocusable(true);
        window.showAtLocation(layout, Gravity.BOTTOM, 0, 0);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("gy", "当前地址：" + playUrl);
                dialog.show();
                Intent intent = new Intent(WebViewActivity.this, BrowserActivity.class);
                intent.putExtra("url", list.get(i) + playUrl);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                window.dismiss();
            }
        });

        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (this != null) {
                    setBackgroundAlpha(WebViewActivity.this, 1f);
                }
            }
        });
    }

    public static void setBackgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        if (bgAlpha == 1) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        activity.getWindow().setAttributes(lp);
    }

    @Override
    protected void onStop() {
        super.onStop();
        dialog.dismiss();
    }
}

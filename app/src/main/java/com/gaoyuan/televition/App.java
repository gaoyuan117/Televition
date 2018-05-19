package com.gaoyuan.televition;

import android.app.Application;
import android.content.SharedPreferences;

import com.gaoyuan.televition.utils.SharedPreferencesUtil;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;

import java.util.ArrayList;
import java.util.List;

public class App extends Application{

    private static App instance;
    public static String token,phone,id;
    public static List<String> list = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferencesUtil.init(this,"Honey",MODE_PRIVATE);
        instance = this;
        initTbs();
    }

    private void initTbs() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
            }

            @Override
            public void onCoreInitFinished() {
            }
        };

        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
            }

            @Override
            public void onInstallFinish(int i) {
            }

            @Override
            public void onDownloadProgress(int i) {
            }
        });

        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    public static App getsInstance() {
        return instance;
    }
}

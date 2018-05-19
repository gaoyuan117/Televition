package com.gaoyuan.televition;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.gaoyuan.televition.base.BaseActivity;
import com.gaoyuan.televition.retrofit.BaseObjObserver;
import com.gaoyuan.televition.retrofit.HttpResult;
import com.gaoyuan.televition.retrofit.RetrofitClient;
import com.gaoyuan.televition.retrofit.RxUtils;
import com.gaoyuan.televition.ui.MyActivity;
import com.gaoyuan.televition.ui.bean.AnalysisBean;
import com.gaoyuan.televition.utils.MyImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import static com.gaoyuan.televition.WebViewActivity.setBackgroundAlpha;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {


    GridView gridView;
    TelevitionAdapter adapter;
    Banner mBanner;
    TextView tvNotice;
    List<TelevitionBean> list = new ArrayList<>();
    private List<String> bannerList = new ArrayList<>();

    int[] logo = new int[]{R.mipmap.aiqiyi, R.mipmap.youku, R.mipmap.tengxun,
            R.mipmap.mangguo, R.mipmap.souhu, R.mipmap.leshi, R.mipmap.pptv,
            R.mipmap.dianyingwang, R.mipmap.kankan};

    String[] url = new String[]{"http://www.iqiyi.com/", "http://www.youku.com/",
            "https://v.qq.com/", "https://www.mgtv.com/", "https://tv.sohu.com/", "http://www.le.com/"
            , "http://www.pptv.com/", "http://www.1905.com/", "http://www.kankan.com/"};

    String[] name = new String[]{"爱奇艺", "优酷", "腾讯", "芒果", "搜狐", "乐视", "PPTV", "1905", "看看视频"};

    @Override
    protected boolean hasActionBar() {
        return true;
    }

    @Override
    protected Object getIdOrView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        setToolbar("首页");

        tvNotice = (TextView) findViewById(R.id.tv_notice);
        mBanner = (Banner) findViewById(R.id.banner);
        gridView = (GridView) findViewById(R.id.gridView);

        initDatas();

        adapter = new TelevitionAdapter(this, list);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(this);
        tvNotice.setSelected(true);
        tvNotice.setText(getResources().getString(R.string.notice));
        bannerList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1526324164588&di=be7d80fc5a7330aa410536e788b7bb92&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201508%2F19%2F20150819103524_umBTH.thumb.700_0.jpeg");
        bannerComplex();

        setRight(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MyActivity.class));
            }
        });

    }

    private void initDatas() {
        getIndex();
        for (int i = 0; i < logo.length; i++) {
            TelevitionBean bean = new TelevitionBean();
            bean.url = url[i];
            bean.title = name[i];
            bean.img = logo[i];
            list.add(bean);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TelevitionBean bean = list.get(position);
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("title", bean.title);
        intent.putExtra("url", bean.url);
        startActivity(intent);
    }

    private void bannerComplex() {
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBanner.setImageLoader(new MyImageLoader());
        mBanner.setImages(bannerList);
        mBanner.setBannerAnimation(Transformer.Default);
        mBanner.isAutoPlay(true);
        mBanner.setViewPagerIsScroll(true);
        mBanner.setDelayTime(3000);
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.start();
    }


    private void getIndex(){
        RetrofitClient.getInstance().createApi().index("")
                .compose(RxUtils.<HttpResult<AnalysisBean>>io_main())
                .subscribe(new BaseObjObserver<AnalysisBean>(this) {
                    @Override
                    protected void onHandleSuccess(AnalysisBean bean) {
                        if (bean.getList()!=null&&bean.getList().size()>0){
                            for (int i = 0; i < bean.getList().size(); i++) {
                                App.list.add(bean.getList().get(i).getContent());

                            }
                        }
                    }
                });
    }
}

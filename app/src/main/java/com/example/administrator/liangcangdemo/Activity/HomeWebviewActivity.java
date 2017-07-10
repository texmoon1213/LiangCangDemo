package com.example.administrator.liangcangdemo.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.liangcangdemo.R;
import com.example.administrator.liangcangdemo.bean.ShopHomeBean;
import com.example.administrator.liangcangdemo.bean.ShopSpecialBean;
import com.example.administrator.liangcangdemo.untils.DensityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeWebviewActivity extends AppCompatActivity {

    @BindView(R.id.webView_special)
    WebView webViewSpecial;

    @BindView(R.id.search_titlebar)
    ImageView searchTitlebar;

    @BindView(R.id.back_titlebar)
    ImageView backTitlebar;

    @BindView(R.id.tv_titlebar)
    TextView tvTitlebar;

    @BindView(R.id.shopcar_titlebar)
    ImageView shopcarTitlebar;

    @BindView(R.id.progressBar_webView_special)
    ProgressBar progressBarWebViewSpecial;
    private WebSettings settings;
    private ShopSpecialBean.DataBean.ItemsBean special_bean;
    private ShopHomeBean.DataBean.ItemsBean.ListBeanX home_bean;
    private String onte2four;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_webview);
        ButterKnife.bind(this);
        initData();
        initWebView();
        initView();
        initListener();
    }

    private void initListener() {
        backTitlebar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        tvTitlebar.setTextSize(DensityUtil.px2sp(HomeWebviewActivity.this, 60));
        searchTitlebar.setVisibility(View.GONE);
        backTitlebar.setVisibility(View.VISIBLE);
        shopcarTitlebar.setVisibility(View.GONE);
    }

    private void initData() {
//        String from = getIntent().getStringExtra("from");
        home_bean = (ShopHomeBean.DataBean.ItemsBean.ListBeanX) getIntent().getSerializableExtra("home_bean");
        onte2four = getIntent().getStringExtra("one2four");
    }

    private void initWebView() {
        settings = webViewSpecial.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);//双击页面变大变小
        settings.setBuiltInZoomControls(true);//添加变大变小按钮
        webViewSpecial.setBackgroundColor(Color.parseColor("#CCEED0"));//添加背景顔色，也可以用get获取js頁面传过来的背景
        //这个可以省去
//        webViewSpecial.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//            }
//        });
        webViewSpecial.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    progressBarWebViewSpecial.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    progressBarWebViewSpecial.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    progressBarWebViewSpecial.setProgress(newProgress);//设置进度值
                }
            }
        });
        switch (onte2four) {
            case "one":
                tvTitlebar.setText(home_bean.getOne().getTopic_name());
                url = home_bean.getOne().getTopic_url();
                break;
            case "two":
                tvTitlebar.setText(home_bean.getTwo().getTopic_name());
                url = home_bean.getTwo().getTopic_url();
                break;
            case "three":
                tvTitlebar.setText(home_bean.getThree().getTopic_name());
                url = home_bean.getThree().getTopic_url();
                break;
            case "four":
                tvTitlebar.setText(home_bean.getFour().getTopic_name());
                url = home_bean.getFour().getTopic_url();
                break;
        }
        webViewSpecial.loadUrl(url);
    }
}

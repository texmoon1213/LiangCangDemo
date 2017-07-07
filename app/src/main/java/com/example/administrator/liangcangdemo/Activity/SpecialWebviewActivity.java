package com.example.administrator.liangcangdemo.Activity;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.liangcangdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpecialWebviewActivity extends AppCompatActivity {

    @BindView(R.id.webView_special)
    WebView webViewSpecial;
    @BindView(R.id.ll_progressbar_special_webview)
    LinearLayout llProgressbarSpecialWebview;
    @BindView(R.id.search_titlebar)
    ImageView searchTitlebar;
    @BindView(R.id.back_titlebar)
    ImageView backTitlebar;
    @BindView(R.id.tv_titlebar)
    TextView tvTitlebar;
    @BindView(R.id.shopcar_titlebar)
    ImageView shopcarTitlebar;
    private WebSettings settings;
    private Uri dataUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_webview);
        ButterKnife.bind(this);
        initData();
        initWebView();
        initView();
    }

    private void initView() {
        tvTitlebar.setText("測試專題名");
        searchTitlebar.setVisibility(View.GONE);
        backTitlebar.setVisibility(View.VISIBLE);
        shopcarTitlebar.setVisibility(View.GONE);
    }

    private void initData() {
        dataUri = getIntent().getData();
    }

    private void initWebView() {
        settings = webViewSpecial.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);//双击页面变大变小
        settings.setBuiltInZoomControls(true);//添加变大变小按钮
        webViewSpecial.setBackgroundColor(Color.parseColor("#CCEED0"));//添加背景顔色，也可以用get获取js頁面传过来的背景
        webViewSpecial.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                llProgressbarSpecialWebview.setVisibility(View.GONE);
            }
        });
        webViewSpecial.loadUrl(dataUri.toString());
    }
}

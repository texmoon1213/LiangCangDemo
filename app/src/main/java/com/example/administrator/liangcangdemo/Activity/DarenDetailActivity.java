package com.example.administrator.liangcangdemo.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.liangcangdemo.R;
import com.example.administrator.liangcangdemo.bean.DarenBean;

public class DarenDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daren_detail);
        initView();
        initData();
        initListener();
    }

    private void initListener() {

    }

    private void initData() {
        String from = getIntent().getStringExtra("from");
        DarenBean.DataBean.ItemsBean itemsBean = (DarenBean.DataBean.ItemsBean) getIntent().getSerializableExtra(from);

    }

    private void initView() {

    }
}

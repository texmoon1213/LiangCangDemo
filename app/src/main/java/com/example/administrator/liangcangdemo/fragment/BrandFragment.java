package com.example.administrator.liangcangdemo.fragment;

import android.view.View;
import android.widget.TextView;

import com.example.administrator.liangcangdemo.base.BaseFragment;

/**
 * Created by Administrator on 2017/7/6.
 */

public class BrandFragment extends BaseFragment {
    @Override
    public View initView() {
        TextView textView = new TextView(context);
        textView.setText("品牌");
        return textView;
    }
}

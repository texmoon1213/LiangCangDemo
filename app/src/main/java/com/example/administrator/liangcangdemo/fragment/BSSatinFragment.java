package com.example.administrator.liangcangdemo.fragment;

import android.view.View;
import android.widget.TextView;

import com.example.administrator.liangcangdemo.base.BaseFragment;

/**
 * Created by Administrator on 2017/7/14.
 */

public class BSSatinFragment extends BaseFragment {
    @Override
    public View initView() {
        TextView textView = new TextView(context);
        textView.setText("段子");
        return textView;
    }
}

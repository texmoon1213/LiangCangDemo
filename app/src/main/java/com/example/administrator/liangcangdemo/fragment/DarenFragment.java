package com.example.administrator.liangcangdemo.fragment;

import android.view.View;
import android.widget.TextView;

import com.example.administrator.liangcangdemo.base.BaseFragment;

/**
 * Created by Administrator on 2017/7/6.
 */

public class DarenFragment extends BaseFragment {
    @Override
    public View initView() {
        TextView textView = new TextView(context);
        textView.setText("大人fg");
        textView.setTextSize(40);
        return textView;
    }
}

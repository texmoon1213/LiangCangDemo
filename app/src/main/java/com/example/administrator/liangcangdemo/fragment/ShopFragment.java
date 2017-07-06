package com.example.administrator.liangcangdemo.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.administrator.liangcangdemo.R;
import com.example.administrator.liangcangdemo.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/6.
 */

public class ShopFragment extends BaseFragment {

    @BindView(R.id.tablaout_fragment1)
    TabLayout tablaoutFragment1;
    @BindView(R.id.vp_fragment1)
    ViewPager vpFragment1;
    Unbinder unbinder;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment1_main, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

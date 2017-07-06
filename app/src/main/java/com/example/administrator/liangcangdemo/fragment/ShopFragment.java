package com.example.administrator.liangcangdemo.fragment;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;

import com.example.administrator.liangcangdemo.MainActivity;
import com.example.administrator.liangcangdemo.R;
import com.example.administrator.liangcangdemo.adapter.ShopViewPagerAdapter;
import com.example.administrator.liangcangdemo.base.BaseFragment;

import java.util.ArrayList;

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
    private ArrayList<BaseFragment> fragments;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment1_main, null);
        unbinder = ButterKnife.bind(this, view);
        MainActivity mainActivity = (MainActivity) context;
        initFragment();

        ShopViewPagerAdapter adapter = new ShopViewPagerAdapter(mainActivity.getSupportFragmentManager(), fragments);
        vpFragment1.setAdapter(adapter);
        tablaoutFragment1.setSelectedTabIndicatorColor(Color.WHITE);//下划线
        tablaoutFragment1.setTabTextColors(Color.GRAY, Color.WHITE);//默认颜色和选中颜色
//        tablayout.setTextDirection(); //没整出来
        tablaoutFragment1.setTabGravity(Gravity.FILL);
//        tablaoutFragment1.setTabMode(TabLayout.MODE_SCROLLABLE);//可以滚动
        tablaoutFragment1.setTabMode(TabLayout.MODE_FIXED);
        tablaoutFragment1.setupWithViewPager(vpFragment1);          //tablayout设置相关参数，最主要是关联viewpager

        return view;
    }

    @Override
    public void initData() {
        super.initData();
        switchFragment();
    }

    private void switchFragment() {
        vpFragment1.setCurrentItem(2);
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new TypeFragment());
        fragments.add(new BrandFragment());
        fragments.add(new HomeFragment());
        fragments.add(new SpecialFragment());
        fragments.add(new GiftFragment());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

package com.example.administrator.liangcangdemo.fragment;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.administrator.liangcangdemo.MainActivity;
import com.example.administrator.liangcangdemo.R;
import com.example.administrator.liangcangdemo.adapter.BSRecommendAdapter;
import com.example.administrator.liangcangdemo.base.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/6.
 */

public class ShareFragment extends BaseFragment {
    @BindView(R.id.tablaout_sharefragment)
    TabLayout tablaoutSharefragment;
    @BindView(R.id.vp_sharefragment)
    ViewPager vpSharefragment;
    Unbinder unbinder;
    private ArrayList<BaseFragment> fragments;

    @Override
    public View initView() {
        View inflate = View.inflate(context, R.layout.share_fragment, null);
        unbinder = ButterKnife.bind(this, inflate);
        initFragments();
        MainActivity mainActivity = (MainActivity) context;
        BSRecommendAdapter adapter = new BSRecommendAdapter(mainActivity.getSupportFragmentManager(), fragments);
        vpSharefragment.setAdapter(adapter);

        tablaoutSharefragment.setSelectedTabIndicatorColor(Color.WHITE);//下划线
        tablaoutSharefragment.setTabTextColors(Color.GRAY, Color.WHITE);//默认颜色和选中颜色
//        tablayout.setTextDirection(); //没整出来
//        tablaoutSharefragment.setTabGravity(Gravity.FILL);
//        tablaoutSharefragment.setTabMode(TabLayout.MODE_FIXED);
        tablaoutSharefragment.setupWithViewPager(vpSharefragment);          //tablayout设置相关参数，最主要是关联viewpager
        return inflate;
    }

    private void initFragments() {
        fragments = new ArrayList<>();
        fragments.add(new BSRecommendFragment());
        fragments.add(new BSSatinFragment());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

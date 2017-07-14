package com.example.administrator.liangcangdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.liangcangdemo.base.BaseFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/14.
 */

public class BSRecommendAdapter extends FragmentPagerAdapter {

    private final ArrayList<BaseFragment> fragments;
    private String[] mTitle = {"推荐", "段子"};

    public BSRecommendAdapter(FragmentManager fm, ArrayList<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position % mTitle.length];
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }
}

package com.example.administrator.liangcangdemo.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.cjj.MaterialRefreshLayout;
import com.example.administrator.liangcangdemo.R;
import com.example.administrator.liangcangdemo.adapter.ShopHomeRecycleAdapter;
import com.example.administrator.liangcangdemo.base.BaseFragment;
import com.example.administrator.liangcangdemo.bean.ShopHomeBean;
import com.example.administrator.liangcangdemo.untils.ConstantUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/7/6.
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.recycle_home_shop)
    RecyclerView recycleHomeShop;
    @BindView(R.id.refresh_home_shop)
    MaterialRefreshLayout refreshHomeShop;
    Unbinder unbinder;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_home_shop, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        getDataFromNet();
    }

    private void getDataFromNet() {
        //        .cacheKey("home1").cacheMode(CacheMode.DEFAULT)缓存加不上
        OkGo.<String>get(ConstantUtils.SHOP_HOME).tag(this).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                Log.e("TAG", "成功==" + s);
                processData(s);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                Log.e("TAG", "失败==" + e.getMessage());
            }
        });
    }

    private void processData(String s) {
        ShopHomeBean shopHomeBean = JSON.parseObject(s, ShopHomeBean.class);
        List<ShopHomeBean.DataBean.ItemsBean.ListBean> listBeen = shopHomeBean.getData().getItems().getList();
        if (listBeen != null && listBeen.size() > 0) {
            ShopHomeRecycleAdapter adapter = new ShopHomeRecycleAdapter(context, listBeen);
            recycleHomeShop.setAdapter(adapter);
            recycleHomeShop.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        } else {
            Toast.makeText(context, "联网失败", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

package com.example.administrator.liangcangdemo.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.cjj.MaterialRefreshLayout;
import com.example.administrator.liangcangdemo.Activity.BrandDetailActivity;
import com.example.administrator.liangcangdemo.R;
import com.example.administrator.liangcangdemo.adapter.ShopBrandRecycleAdapter;
import com.example.administrator.liangcangdemo.base.BaseFragment;
import com.example.administrator.liangcangdemo.bean.ShopBrandBean;
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

public class BrandFragment extends BaseFragment {
    @BindView(R.id.recycle_brand_shop)
    RecyclerView recycleBrandShop;
    @BindView(R.id.refresh_brand_shop)
    MaterialRefreshLayout refreshBrandShop;
    Unbinder unbinder;

    @Override
    public View initView() {
        View inflate = View.inflate(context, R.layout.fragment_brand_shop, null);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void initData() {
        super.initData();
        getDataFromNet();
    }

    private void getDataFromNet() {
        OkGo.<String>get(ConstantUtils.SHOP_BRAND).tag(this).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                Log.e("TAG", "品牌成功==" + s);
                processData(s);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                Log.e("TAG", "品牌失败==" + e.getMessage());
            }
        });
    }

    private void processData(String s) {
        ShopBrandBean shopSpecialBean = JSON.parseObject(s, ShopBrandBean.class);
        List<ShopBrandBean.DataBean.ItemsBean> itemsBeen = shopSpecialBean.getData().getItems();
        if (itemsBeen != null && itemsBeen.size() > 0) {
            ShopBrandRecycleAdapter adapter = new ShopBrandRecycleAdapter(context, itemsBeen);
            recycleBrandShop.setAdapter(adapter);
            recycleBrandShop.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            adapter.setOnItemClickListener(new BrandListener(itemsBeen));
        } else {
            Toast.makeText(context, "联网失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private class BrandListener implements ShopBrandRecycleAdapter.ItemClickListener {
        private final List<ShopBrandBean.DataBean.ItemsBean> itemBean;

        public BrandListener(List<ShopBrandBean.DataBean.ItemsBean> itemsBeen) {
            this.itemBean = itemsBeen;
        }

        @Override
        public void OnItemClick(View v, int position) {
            ShopBrandBean.DataBean.ItemsBean itemsBean = itemBean.get(position);
            Intent initent = new Intent(getContext(), BrandDetailActivity.class);
            initent.putExtra("brand_bean", itemsBean);
            startActivity(initent);
        }
    }
}

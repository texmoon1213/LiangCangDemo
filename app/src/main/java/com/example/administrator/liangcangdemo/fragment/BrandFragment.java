package com.example.administrator.liangcangdemo.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.administrator.liangcangdemo.Activity.BrandDetailActivity;
import com.example.administrator.liangcangdemo.R;
import com.example.administrator.liangcangdemo.adapter.ShopBrandRecycleAdapter;
import com.example.administrator.liangcangdemo.base.BaseFragment;
import com.example.administrator.liangcangdemo.bean.ShopBrandBean;
import com.example.administrator.liangcangdemo.untils.ConstantUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;

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
    private static final int SHANG = 0;
    private static final int XIA = 1;
    private int count = -1;
    private List<ShopBrandBean.DataBean.ItemsBean> datasBean;
    private ShopBrandRecycleAdapter adapter;

    @Override
    public View initView() {
        View inflate = View.inflate(context, R.layout.fragment_brand_shop, null);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void initData() {
        super.initData();
        getDataFromNet(XIA);
    }

    private void getDataFromNet(final int shangOrXia) {
        if (shangOrXia == XIA) {
            count = 1;
        } else if (shangOrXia == SHANG) {
            count = count + 1;
        }
        OkGo.getInstance().addCommonParams(new HttpParams("&page", count + ""))
                .<String>get(ConstantUtils.SHOP_BRAND).tag(this).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                Log.e("TAG", "品牌成功==" + s);
                processData(s, shangOrXia);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                Log.e("TAG", "品牌失败==" + e.getMessage());
            }
        });
    }

    private void processData(String s, int shangOrXia) {
        ShopBrandBean shopBrandBean = JSON.parseObject(s, ShopBrandBean.class);
        List<ShopBrandBean.DataBean.ItemsBean> itemsBeen = shopBrandBean.getData().getItems();
        if (shangOrXia == XIA) {
            datasBean = itemsBeen;
        } else if (shangOrXia == SHANG) {
            datasBean.addAll(itemsBeen);
        }
        if (datasBean != null && datasBean.size() > 0) {
            if (count == 1) {
                adapter = new ShopBrandRecycleAdapter(context, datasBean);
                recycleBrandShop.setAdapter(adapter);
                recycleBrandShop.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                adapter.setOnItemClickListener(new BrandListener(datasBean));
            } else {
                adapter.notifyDataSetChanged();
            }
            initReFresh(shopBrandBean);
            refreshBrandShop.finishRefresh();
            refreshBrandShop.finishRefreshLoadMore();
        } else {
            Toast.makeText(context, "联网失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void initReFresh(ShopBrandBean shopBrandBean) {
        refreshBrandShop.setLoadMore(shopBrandBean.getData().isHas_more());
        refreshBrandShop.setMaterialRefreshListener(new MaterialRefreshListener() {

            //下拉刷新
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
//                isLoadMore = false;
                getDataFromNet(XIA);
                Log.e("count", "getDataFromNet(XIA)");
            }

            //加载更多-上拉刷新
            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
//                isLoadMore = true;
                getDataFromNet(SHANG);
                Log.e("count", "getDataFromNet(SHANG)");
            }

            @Override
            public void onfinish() {
                super.onfinish();
                Toast.makeText(context, "加载完成", Toast.LENGTH_SHORT).show();
//                refreshTypeShop.finishRefreshing();调用这个方法后 才会执行onfinish()
            }
        });

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

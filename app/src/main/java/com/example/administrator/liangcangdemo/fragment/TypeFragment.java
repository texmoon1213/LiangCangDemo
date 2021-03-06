package com.example.administrator.liangcangdemo.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.administrator.liangcangdemo.Activity.TypeListActivity;
import com.example.administrator.liangcangdemo.R;
import com.example.administrator.liangcangdemo.adapter.ShopTypeRecycleAdapter;
import com.example.administrator.liangcangdemo.base.BaseFragment;
import com.example.administrator.liangcangdemo.bean.ShopTypeBean;
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

public class TypeFragment extends BaseFragment {
    @BindView(R.id.recycle_type_shop)
    RecyclerView recycleTypeShop;
    @BindView(R.id.refresh_type_shop)
    MaterialRefreshLayout refreshTypeShop;
    Unbinder unbinder;

    @Override
    public View initView() {
        View inflate = View.inflate(context, R.layout.fragment_type_shop, null);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void initData() {
        super.initData();
        getDataFromNet();
    }

    private void initReFresh(ShopTypeBean shopSpecialBean) {
        refreshTypeShop.setLoadMore(shopSpecialBean.getData().isHas_more());
        refreshTypeShop.setMaterialRefreshListener(new MaterialRefreshListener() {

            //下拉刷新
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
//                isLoadMore = false;
                getDataFromNet();
            }

            //加载更多-上拉刷新
            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
//                isLoadMore = true;
//                getMoreData();
                getDataFromNet();
            }

            @Override
            public void onfinish() {
                super.onfinish();
                Toast.makeText(context, "加载完成", Toast.LENGTH_SHORT).show();

//                refreshTypeShop.finishRefreshing();

            }
        });

    }

    private void getDataFromNet() {


        OkGo.<String>get(ConstantUtils.SHOP_TYPE).tag(this).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                Log.e("TAG", "分类成功==" + s);
                processData(s);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                Log.e("TAG", "分类失败==" + e.getMessage());
            }
        });
    }

    private void processData(String s) {
        ShopTypeBean shopSpecialBean = JSON.parseObject(s, ShopTypeBean.class);
        List<ShopTypeBean.DataBean.ItemsBean> itemsBeen = shopSpecialBean.getData().getItems();
        if (itemsBeen != null && itemsBeen.size() > 0) {
            ShopTypeRecycleAdapter adapter = new ShopTypeRecycleAdapter(context, itemsBeen);
            recycleTypeShop.setAdapter(adapter);
            recycleTypeShop.setLayoutManager(new GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false));
            adapter.setOnItemClickListener(new TypeListener(itemsBeen));
            initReFresh(shopSpecialBean);
            //加载关闭
            refreshTypeShop.finishRefresh();
            refreshTypeShop.finishRefreshLoadMore();
        } else {
            Toast.makeText(context, "联网失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private class TypeListener implements ShopTypeRecycleAdapter.ItemClickListener {
        private List<ShopTypeBean.DataBean.ItemsBean> itemsBean;

        public TypeListener(List<ShopTypeBean.DataBean.ItemsBean> itemsBeen) {
            this.itemsBean = itemsBeen;
        }

        @Override
        public void OnItemClick(View v, int position) {

            ShopTypeBean.DataBean.ItemsBean itemsBean = this.itemsBean.get(position);
            Intent initent = new Intent(getContext(), TypeListActivity.class);
            initent.putExtra("type_bean", itemsBean);
            startActivity(initent);
        }
    }
}

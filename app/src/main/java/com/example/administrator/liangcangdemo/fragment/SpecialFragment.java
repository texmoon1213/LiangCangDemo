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
import com.example.administrator.liangcangdemo.Activity.SpecialWebviewActivity;
import com.example.administrator.liangcangdemo.R;
import com.example.administrator.liangcangdemo.adapter.ShopSpecialRecycleAdapter;
import com.example.administrator.liangcangdemo.base.BaseFragment;
import com.example.administrator.liangcangdemo.bean.ShopSpecialBean;
import com.example.administrator.liangcangdemo.untils.ConstantUtils;
import com.example.administrator.liangcangdemo.view.DividerItemDecoration;
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

public class SpecialFragment extends BaseFragment {
    @BindView(R.id.recycle_special_shop)
    RecyclerView recycleSpecialShop;
    @BindView(R.id.refresh_special_shop)
    MaterialRefreshLayout refreshSpecialShop;
    Unbinder unbinder;
    private static final int SHANG = 0;
    private static final int XIA = 1;
    private int count = -1;
    private List<ShopSpecialBean.DataBean.ItemsBean> datasBean;
    private ShopSpecialRecycleAdapter adapter;

    @Override
    public View initView() {
        View inflate = View.inflate(context, R.layout.fragment_special_shop, null);
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
        OkGo.getInstance().addCommonParams(new HttpParams("page", count + ""))
                .<String>get(ConstantUtils.SHOP_SPECIAL).tag(this).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                Log.e("TAG", "專題成功==" + s);
                processData(s, shangOrXia);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                Log.e("TAG", "專題失败==" + e.getMessage());
            }
        });
    }

    private void processData(String s, int shangOrXia) {
        ShopSpecialBean shopSpecialBean = JSON.parseObject(s, ShopSpecialBean.class);
        List<ShopSpecialBean.DataBean.ItemsBean> itemsBeen = shopSpecialBean.getData().getItems();

        if (shangOrXia == XIA) {
            datasBean = itemsBeen;
        } else if (shangOrXia == SHANG) {
            datasBean.addAll(itemsBeen);
        }
        if (itemsBeen != null && itemsBeen.size() > 0) {
            if (count == 1) {
                adapter = new ShopSpecialRecycleAdapter(context, itemsBeen);
                recycleSpecialShop.setAdapter(adapter);
                recycleSpecialShop.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                recycleSpecialShop.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
                adapter.setOnItemClickListener(new SpecialListener(itemsBeen));
            } else {
                adapter.notifyDataSetChanged();
            }
            initReFresh(shopSpecialBean);
            refreshSpecialShop.finishRefresh();
            refreshSpecialShop.finishRefreshLoadMore();
        } else {
            Toast.makeText(context, "联网失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void initReFresh(ShopSpecialBean shopSpecialBean) {
        refreshSpecialShop.setLoadMore(shopSpecialBean.getData().isHas_more());
        refreshSpecialShop.setMaterialRefreshListener(new MaterialRefreshListener() {

            //下拉刷新
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
//                isLoadMore = false;
                getDataFromNet(XIA);
            }

            //加载更多-上拉刷新
            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
//                isLoadMore = true;
                getDataFromNet(SHANG);
            }

            @Override
            public void onfinish() {
                super.onfinish();
                Toast.makeText(context, "加载完成", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private class SpecialListener implements ShopSpecialRecycleAdapter.ItemClickListener {
        private List<ShopSpecialBean.DataBean.ItemsBean> itemsBean;

        public SpecialListener(List<ShopSpecialBean.DataBean.ItemsBean> itemsBeen) {
            this.itemsBean = itemsBeen;
        }

        @Override
        public void OnItemClick(View v, int position) {
//            String url = itemsBean.get(position).getTopic_url();
            ShopSpecialBean.DataBean.ItemsBean itemsBean = this.itemsBean.get(position);
            Intent initent = new Intent(getContext(), SpecialWebviewActivity.class);
            initent.putExtra("special_bean", itemsBean);
            initent.putExtra("from", "special_bean");
            startActivity(initent);
        }
    }
}

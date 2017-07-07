package com.example.administrator.liangcangdemo.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.cjj.MaterialRefreshLayout;
import com.example.administrator.liangcangdemo.Activity.SpecialWebviewActivity;
import com.example.administrator.liangcangdemo.R;
import com.example.administrator.liangcangdemo.adapter.ShopSpecialRecycleAdapter;
import com.example.administrator.liangcangdemo.base.BaseFragment;
import com.example.administrator.liangcangdemo.bean.ShopSpecialBean;
import com.example.administrator.liangcangdemo.untils.ConstantUtils;
import com.example.administrator.liangcangdemo.view.DividerItemDecoration;
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

public class SpecialFragment extends BaseFragment {
    @BindView(R.id.recycle_special_shop)
    RecyclerView recycleSpecialShop;
    @BindView(R.id.refresh_special_shop)
    MaterialRefreshLayout refreshSpecialShop;
    Unbinder unbinder;

    @Override
    public View initView() {
        View inflate = View.inflate(context, R.layout.fragment_special_shop, null);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void initData() {
        super.initData();
        getDataFromNet(ConstantUtils.SHOP_SPECIAL);
    }

    private void getDataFromNet(String urlspecial) {
        OkGo.<String>get(urlspecial).tag(this).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                Log.e("TAG", "專題成功==" + s);
                processData(s);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                Log.e("TAG", "專題失败==" + e.getMessage());
            }
        });
    }


    private void processData(String s) {
        ShopSpecialBean shopSpecialBean = JSON.parseObject(s, ShopSpecialBean.class);
        List<ShopSpecialBean.DataBean.ItemsBean> itemsBeen = shopSpecialBean.getData().getItems();
        if (itemsBeen != null && itemsBeen.size() > 0) {
            ShopSpecialRecycleAdapter adapter = new ShopSpecialRecycleAdapter(context, itemsBeen);
            recycleSpecialShop.setAdapter(adapter);
            recycleSpecialShop.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            recycleSpecialShop.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
            adapter.setOnItemClickListener(new SpecialListener(itemsBeen));
        } else {
            Toast.makeText(context, "联网失败", Toast.LENGTH_SHORT).show();
        }
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
            String url = itemsBean.get(position).getTopic_url();
            Intent initent = new Intent(getContext(), SpecialWebviewActivity.class);
            initent.setData(Uri.parse(url));
            startActivity(initent);
        }
    }
}

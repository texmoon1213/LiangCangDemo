package com.example.administrator.liangcangdemo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.cjj.MaterialRefreshLayout;
import com.example.administrator.liangcangdemo.R;
import com.example.administrator.liangcangdemo.adapter.ShopBrandDetailRecycleAdapter;
import com.example.administrator.liangcangdemo.bean.ShopBrandBean;
import com.example.administrator.liangcangdemo.bean.ShopBrandDetailBean;
import com.example.administrator.liangcangdemo.untils.ConstantUtils;
import com.example.administrator.liangcangdemo.view.ComprehensiveItemDecoration;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class BrandDetailActivity extends AppCompatActivity {

    @BindView(R.id.search_titlebar)
    ImageView searchTitlebar;
    @BindView(R.id.back_titlebar)
    ImageView backTitlebar;
    @BindView(R.id.tv_titlebar)
    TextView tvTitlebar;
    @BindView(R.id.shopcar_titlebar)
    ImageView shopcarTitlebar;
    @BindView(R.id.iv_brand_loge)
    ImageView ivBrandLoge;
    @BindView(R.id.tv_brand_name)
    TextView tvBrandName;
    @BindView(R.id.ll_brand_detail)
    LinearLayout llBrandDetail;
    @BindView(R.id.rb_brand_left)
    RadioButton rbBrandLeft;
    @BindView(R.id.rb_brand_right)
    RadioButton rbBrandRight;
    @BindView(R.id.rg_brand_detail)
    RadioGroup rgBrandDetail;
    @BindView(R.id.tv_brand_desc)
    TextView tvBrandDesc;
    @BindView(R.id.recycleview_brand_goodinfo)
    RecyclerView recycleviewBrandGoodinfo;
    @BindView(R.id.refresh_brand_detail_shop)
    MaterialRefreshLayout refreshBrandDetailShop;
    private ShopBrandBean.DataBean.ItemsBean brand_bean;
    private ShopBrandDetailBean shopDetailBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_detail);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        rgBrandDetail.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rb_brand_left) {
                    tvBrandDesc.setVisibility(View.VISIBLE);
                    recycleviewBrandGoodinfo.setVisibility(View.GONE);
                } else if (checkedId == R.id.rb_brand_right) {
                    tvBrandDesc.setVisibility(View.GONE);
                    recycleviewBrandGoodinfo.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void initData() {
        brand_bean = (ShopBrandBean.DataBean.ItemsBean) getIntent().getSerializableExtra("brand_bean");
        getDataFromNet();
    }


    private void getDataFromNet() {
        OkGo.getInstance()
                .addCommonParams(new HttpParams("brand_id", String.valueOf(brand_bean.getBrand_id())))
                .<String>get(ConstantUtils.SHOP_BRAND_DETAIL)
                .tag(this).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                Log.e("TAGS", "品牌详情页成功==" + s);
                progress(s);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                Log.e("TAG", "品牌详情页失败==" + e.getMessage());
            }
        });
    }

    private void progress(String s) {
        shopDetailBean = JSON.parseObject(s, ShopBrandDetailBean.class);
        setData();
    }

    private void setData() {
        Glide.with(BrandDetailActivity.this).load(brand_bean.getBrand_logo()).crossFade().into(ivBrandLoge);
        tvBrandName.setText(brand_bean.getBrand_name());
        tvBrandDesc.setText(shopDetailBean.getData().getItems().get(0).getBrand_info().getBrand_desc());
        ShopBrandDetailRecycleAdapter adapter = new ShopBrandDetailRecycleAdapter(BrandDetailActivity.this, shopDetailBean);
        recycleviewBrandGoodinfo.setAdapter(adapter);
        recycleviewBrandGoodinfo.setLayoutManager(new GridLayoutManager(BrandDetailActivity.this, 2));
        recycleviewBrandGoodinfo.addItemDecoration(new ComprehensiveItemDecoration(15));//设置间距
        adapter.setOnItemClickListener(new BrandItemListener());
    }

    private void initView() {
        tvTitlebar.setText("品牌产品");
        backTitlebar.setVisibility(View.VISIBLE);
        searchTitlebar.setVisibility(View.INVISIBLE);
        shopcarTitlebar.setVisibility(View.INVISIBLE);
        recycleviewBrandGoodinfo.setVisibility(View.GONE);
    }


    private class BrandItemListener implements ShopBrandDetailRecycleAdapter.ItemClickListener {
        @Override
        public void OnItemClick(View v, int position) {
            ShopBrandDetailBean.DataBean.ItemsBean itemsBean = shopDetailBean.getData().getItems().get(position);
//            OkGo.getInstance().addCommonParams(new HttpParams("goods_id", itemsBean.getGoods_id()))
//                    .<String>get(ConstantUtils.SHOP_TYPE_DETAIL)
//                    .execute(new StringCallback() {
//                        @Override
//                        public void onSuccess(String s, Call call, Response response) {
//                            Log.e("TAG", "品牌item點擊成功==" + s);
//                            progress1(s);
//                        }
//
//                        @Override
//                        public void onError(Call call, Response response, Exception e) {
//                            super.onError(call, response, e);
//                            Log.e("TAG", "品牌item點擊失败==" + e.getMessage());
//                        }
//                    });
//            ShopBrandDetailBean.DataBean.ItemsBean itemsBeans = itemsBean;
            Intent initent = new Intent(BrandDetailActivity.this, TypeDetialActivity.class);
            initent.putExtra("brand_list_bean", itemsBean);
            initent.putExtra("from", "brand_list_bean");
            startActivity(initent);
        }
    }
}

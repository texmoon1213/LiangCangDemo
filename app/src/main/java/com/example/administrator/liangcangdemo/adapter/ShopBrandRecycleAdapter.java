package com.example.administrator.liangcangdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.liangcangdemo.R;
import com.example.administrator.liangcangdemo.bean.ShopBrandBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/7.
 */

public class ShopBrandRecycleAdapter extends RecyclerView.Adapter {

    private List<ShopBrandBean.DataBean.ItemsBean> datas;
    private Context context;


    public ShopBrandRecycleAdapter(Context context, List<ShopBrandBean.DataBean.ItemsBean> itemsBeen) {
        this.context = context;
        this.datas = itemsBeen;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BrandHoder(View.inflate(context, R.layout.shop_brand_item, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BrandHoder typeHoder = (BrandHoder) holder;
        typeHoder.setData(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    protected class BrandHoder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_shop_brand_item)
        ImageView ivShopBrandItem;
        @BindView(R.id.tv_shop_brand_item)
        TextView tvShopBrandItem;
        @BindView(R.id.iv_k_shop_brand_item)
        ImageView ivKShopBrandItem;

        public BrandHoder(View inflate) {
            super(inflate);
            ButterKnife.bind(this, inflate);
        }


        public void setData(ShopBrandBean.DataBean.ItemsBean itemsBean) {
            Glide.with(context)
                    .load(itemsBean.getBrand_logo())
                    .crossFade()
                    .into(ivShopBrandItem);
            tvShopBrandItem.setText(itemsBean.getBrand_name());
        }
    }
}

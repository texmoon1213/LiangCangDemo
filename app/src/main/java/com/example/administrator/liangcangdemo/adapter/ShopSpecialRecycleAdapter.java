package com.example.administrator.liangcangdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.liangcangdemo.R;
import com.example.administrator.liangcangdemo.bean.ShopSpecialBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/6.
 */

public class ShopSpecialRecycleAdapter extends RecyclerView.Adapter {


    private List<ShopSpecialBean.DataBean.ItemsBean> datas;
    private Context context;

    public ShopSpecialRecycleAdapter(Context context, List<ShopSpecialBean.DataBean.ItemsBean> listBeen) {
        this.context = context;
        this.datas = listBeen;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        viewHolder = new SpecialHoder(View.inflate(context, R.layout.shop_special_item, null));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SpecialHoder viewHolder = (SpecialHoder) holder;
        viewHolder.setData(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    protected class SpecialHoder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_shop_special_item)
        ImageView ivShopSpecialItem;
        @BindView(R.id.tv_shop_special_item)
        TextView tvShopSpecialItem;

        public SpecialHoder(View inflate) {
            super(inflate);
            ButterKnife.bind(this, inflate);
        }

        public void setData(ShopSpecialBean.DataBean.ItemsBean listBean) {
            Glide.with(context).load(listBean.getCover_img_new()).crossFade().into(ivShopSpecialItem);
            tvShopSpecialItem.setText(listBean.getTopic_name());
        }
    }
}

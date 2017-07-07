package com.example.administrator.liangcangdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.liangcangdemo.R;
import com.example.administrator.liangcangdemo.bean.ShopTypeBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Administrator on 2017/7/7.
 */

public class ShopTypeRecycleAdapter extends RecyclerView.Adapter {
    private List<ShopTypeBean.DataBean.ItemsBean> datas;
    private Context context;

    public ShopTypeRecycleAdapter(Context context, List<ShopTypeBean.DataBean.ItemsBean> itemsBeen) {
        this.context = context;
        this.datas = itemsBeen;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TypeHoder(View.inflate(context, R.layout.shop_type_item, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        TypeHoder typeHoder = (TypeHoder) holder;
        typeHoder.setData(datas.get(position));
        typeHoder.ivShopTypeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.OnItemClick(v, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    protected class TypeHoder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_shop_type_item)
        ImageView ivShopTypeItem;

        public TypeHoder(View inflate) {
            super(inflate);
            ButterKnife.bind(this, inflate);
        }

        public void setData(ShopTypeBean.DataBean.ItemsBean itemsBean) {
            Glide.with(context)
                    .load(itemsBean.getCover_new_img())
                    .crossFade()
                    .bitmapTransform(new RoundedCornersTransformation(context, 20, 10))
                    .into(ivShopTypeItem);
        }
    }

    public interface ItemClickListener {
        void OnItemClick(View v, int position);
    }

    ItemClickListener mItemClickListener;

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
}

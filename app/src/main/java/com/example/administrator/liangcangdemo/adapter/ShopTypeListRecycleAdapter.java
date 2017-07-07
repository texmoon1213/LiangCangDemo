package com.example.administrator.liangcangdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.liangcangdemo.Activity.TypeListActivity;
import com.example.administrator.liangcangdemo.R;
import com.example.administrator.liangcangdemo.bean.ShopTypeListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/7.
 */

public class ShopTypeListRecycleAdapter extends RecyclerView.Adapter {


    private List<ShopTypeListBean.DataBean.ItemsBean> datas;
    private TypeListActivity context;

    public ShopTypeListRecycleAdapter(TypeListActivity typeListActivity, List<ShopTypeListBean.DataBean.ItemsBean> itemsBeen) {
        this.context = typeListActivity;
        this.datas = itemsBeen;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new TypeListHoder(View.inflate(context, R.layout.shop_type_list_item, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        TypeListHoder typeHoder = (TypeListHoder) holder;
        typeHoder.setData(datas.get(position));
        typeHoder.rlShopTypeListItem.setOnClickListener(new View.OnClickListener() {
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

    protected class TypeListHoder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_item_list_type)
        ImageView ivItemListType;
        @BindView(R.id.tv_name_item_list_type)
        TextView tvNameItemListType;
        @BindView(R.id.tv_author_item_list_type)
        TextView tvAuthorItemListType;
        @BindView(R.id.tv_like_item_list_type)
        TextView tvLikeItemListType;
        @BindView(R.id.tv_price_item_list_type)
        TextView tvPriceItemListType;
        @BindView(R.id.rl_shop_type_list_item)
        RelativeLayout rlShopTypeListItem;

        public TypeListHoder(View inflate) {
            super(inflate);
            ButterKnife.bind(this, inflate);
        }

        public void setData(ShopTypeListBean.DataBean.ItemsBean itemsBean) {
            Glide.with(context).load(itemsBean.getGoods_image()).crossFade().error(R.drawable.lc_error).into(ivItemListType);
            tvNameItemListType.setText(itemsBean.getGoods_name());
            tvAuthorItemListType.setText(itemsBean.getBrand_info().getBrand_name());
            tvLikeItemListType.setText(itemsBean.getLike_count());
            tvPriceItemListType.setText("￥" + itemsBean.getPrice());
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

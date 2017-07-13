package com.example.administrator.liangcangdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.liangcangdemo.R;
import com.example.administrator.liangcangdemo.bean.MSGBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/6.
 */

public class MsgRecycleAdapter extends RecyclerView.Adapter {

    private ArrayList<MSGBean> datas;
    private Context context;
    private int prePosition;

    public MsgRecycleAdapter(Context context, ArrayList<MSGBean> listBeen) {
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        SpecialHoder viewHolder = (SpecialHoder) holder;
        viewHolder.setData(datas.get(position));
        if (datas.get(position).getMonthInfo().equals(datas.get(prePosition).getMonthInfo())) {
            viewHolder.tvMsgMonth.setVisibility(View.GONE);
        } else {
            viewHolder.tvMsgMonth.setVisibility(View.VISIBLE);
        }
        if (position == 0) {
            viewHolder.tvMsgMonth.setVisibility(View.GONE);
        }
        viewHolder.rlShopSpecialItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.OnItemClick(v, position);
                }
            }
        });
        prePosition = position;
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
        @BindView(R.id.rl_shop_special_item)
        RelativeLayout rlShopSpecialItem;
        @BindView(R.id.tv_msg_month)
        TextView tvMsgMonth;

        public SpecialHoder(View inflate) {
            super(inflate);
            ButterKnife.bind(this, inflate);
        }

        public void setData(final MSGBean listBean) {
            Glide.with(context).load(listBean.getCover_img()).crossFade().into(ivShopSpecialItem);
            tvShopSpecialItem.setText(listBean.getTopic_name());
            tvMsgMonth.setText("一" + listBean.getMonthInfo() + "一");
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

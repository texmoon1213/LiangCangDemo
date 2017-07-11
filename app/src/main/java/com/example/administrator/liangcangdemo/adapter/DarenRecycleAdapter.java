package com.example.administrator.liangcangdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.liangcangdemo.R;
import com.example.administrator.liangcangdemo.bean.DarenBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/10.
 */

public class DarenRecycleAdapter extends RecyclerView.Adapter {


    private List<DarenBean.DataBean.ItemsBean> datas;
    private Context context;

    public DarenRecycleAdapter(Context context, List<DarenBean.DataBean.ItemsBean> items) {
        this.context = context;
        this.datas = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DarenHoder(LayoutInflater.from(context).inflate(R.layout.daren_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        DarenHoder darenHoder = (DarenHoder) holder;
        darenHoder.setData(datas.get(position));
        darenHoder.llDarenItem.setOnClickListener(new View.OnClickListener() {
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

    protected class DarenHoder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_daren_item)
        LinearLayout llDarenItem;
        @BindView(R.id.iv_daren_item)
        ImageView ivDarenItem;
        @BindView(R.id.tv_username_daren_item)
        TextView tvUsernameDarenItem;
        @BindView(R.id.tv_duty_daren_item)
        TextView tvDutyDarenItem;

        public DarenHoder(View inflate) {
            super(inflate);
            ButterKnife.bind(this, inflate);
        }

        public void setData(DarenBean.DataBean.ItemsBean itemsBean) {
            Glide.with(context).load(itemsBean.getUser_images().getOrig()).into(ivDarenItem);
            tvUsernameDarenItem.setText(itemsBean.getUsername());
            tvDutyDarenItem.setText(itemsBean.getDuty());
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

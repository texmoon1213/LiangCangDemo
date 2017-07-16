package com.example.administrator.liangcangdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.liangcangdemo.R;
import com.example.administrator.liangcangdemo.bean.BSSatinBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/16.
 */

public class BSSatinAdapter extends RecyclerView.Adapter {
    private List<BSSatinBean.ListBean> datas;
    private Context context;

    public BSSatinAdapter(Context context, List<BSSatinBean.ListBean> listBeen) {
        this.datas = listBeen;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TextHoder(View.inflate(context, R.layout.bs_recommend_text, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextHoder textHolder = (TextHoder) holder;
        textHolder.setData(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    protected class BaseHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_common_headpic)
        ImageView ivCommonHeadpic;
        @BindView(R.id.tv_iv_common_headpic_name)
        TextView tvIvCommonHeadpicName;
        @BindView(R.id.tv_iv_common_headpic_time_refresh)
        TextView tvIvCommonHeadpicTimeRefresh;

        @BindView(R.id.ll_common_user_info)
        LinearLayout llCommonUserInfo;

        @BindView(R.id.tv_ding)
        TextView tvDing;
        @BindView(R.id.tv_shenhe_ding_number)
        TextView tvShenheDingNumber;
        @BindView(R.id.ll_ding)
        LinearLayout llDing;
        @BindView(R.id.iv_cai)
        TextView ivCai;
        @BindView(R.id.tv_shenhe_cai_number)
        TextView tvShenheCaiNumber;
        @BindView(R.id.ll_cai)
        LinearLayout llCai;
        @BindView(R.id.tv_posts_number)
        TextView tvPostsNumber;
        @BindView(R.id.ll_share)
        LinearLayout llShare;
        @BindView(R.id.tv_download_number)
        TextView tvDownloadNumber;
        @BindView(R.id.ll_download)
        LinearLayout llDownload;

        public BaseHolder(View convertView) {
            super(convertView);
            ButterKnife.bind(this, convertView);
        }

        public void setData(BSSatinBean.ListBean listBean) {
            /**
             * 设置公共头部
             */
            if (listBean.getU() != null && listBean.getU().getHeader() != null && listBean.getU().getHeader().get(0) != null) {
                Glide.with(context).load(listBean.getU().getHeader().get(0)).into(ivCommonHeadpic);
            }
            if (listBean.getU() != null && listBean.getU().getName() != null) {
                tvIvCommonHeadpicName.setText(listBean.getU().getName());
            }
            tvIvCommonHeadpicTimeRefresh.setText(listBean.getPasstime());
            /**
             * 設置底部
             */
            tvShenheDingNumber.setText(listBean.getUp());
            tvShenheCaiNumber.setText(listBean.getDown() + "");
            tvPostsNumber.setText(listBean.getForward() + "");
        }
    }

    private class TextHoder extends BaseHolder {
        private TextView textView;

        public TextHoder(View inflate) {
            super(inflate);
            textView = (TextView) inflate.findViewById(R.id.tv_textmiddle_context);
        }

        @Override
        public void setData(BSSatinBean.ListBean listBean) {
            super.setData(listBean);
            textView.setText(listBean.getText() + listBean.getType());
        }
    }

}

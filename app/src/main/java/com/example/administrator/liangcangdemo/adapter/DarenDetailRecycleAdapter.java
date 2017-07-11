package com.example.administrator.liangcangdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.administrator.liangcangdemo.R;
import com.example.administrator.liangcangdemo.bean.DarenFensiBean;
import com.example.administrator.liangcangdemo.bean.DarenGuanZhuBean;
import com.example.administrator.liangcangdemo.bean.DarenLikeBean;
import com.example.administrator.liangcangdemo.bean.DarenRecommendBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/7/11.
 */

public class DarenDetailRecycleAdapter<T> extends RecyclerView.Adapter {
    public static final int LIKE = 0;

    public static final int GUANZHU = 1;

    public static final int FENSI = 2;
    public static final int RECOMMEND = 3;

    private String userId;
    public Context context;
    public List datas;
    private int itemType;

    public DarenDetailRecycleAdapter(Context c, String userId) {
        this.context = c;
        this.userId = userId;

    }

    public void refresh(String url, final int urlType) {
        itemType = urlType;
        OkGo.getInstance().addCommonParams(new HttpParams("owner_id", userId))
                .get(url)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("TAG", "大人详情recycleVIew成功" + s);
                        progress(s, urlType);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("TAG", "达人详情RecycleView失败" + e.getMessage());
                    }
                });
    }

    //根据传过来的url,去解析对应的Bean;
    private void progress(String s, int urlType) {
        if (urlType == LIKE) {
            DarenLikeBean darenLikeBean = JSON.parseObject(s, DarenLikeBean.class);
            List<DarenLikeBean.DataBean.ItemsBean.GoodsBean> goods = darenLikeBean.getData().getItems().getGoods();
            datas = goods;
        } else if (urlType == GUANZHU) {
            DarenGuanZhuBean darenGuanZhuBean = JSON.parseObject(s, DarenGuanZhuBean.class);
            List<DarenGuanZhuBean.DataBean.ItemsBean.UsersBean> users = darenGuanZhuBean.getData().getItems().getUsers();
            datas = users;
        } else if (urlType == FENSI) {
            DarenFensiBean darenFensiBean = JSON.parseObject(s, DarenFensiBean.class);
            List<DarenFensiBean.DataBean.ItemsBean.UsersBean> users = darenFensiBean.getData().getItems().getUsers();
            datas = users;
        } else if (urlType == RECOMMEND) {
            DarenRecommendBean darenRecommendBean = JSON.parseObject(s, DarenRecommendBean.class);
            List<DarenRecommendBean.DataBean.ItemsBean.GoodsBean> goods = darenRecommendBean.getData().getItems().getGoods();
            datas = goods;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        int itemViewType = -1;
        if (LIKE == itemType) {
            itemViewType = LIKE;
        } else if (GUANZHU == itemType) {
            itemViewType = GUANZHU;
        } else if (FENSI == itemType) {
            itemViewType = FENSI;
        } else if (RECOMMEND == itemType) {
            itemViewType = RECOMMEND;
        }
        return itemViewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case LIKE:
                viewHolder = new LikeHoder(View.inflate(context, R.layout.daren_like_detail_item, null));
                break;
            case RECOMMEND:
                viewHolder = new RecommendHoder(View.inflate(context, R.layout.daren_recommend_detail_item, null));
                break;
            case GUANZHU:
                viewHolder = new GuanZhuHoder(View.inflate(context, R.layout.daren_guanzhu_detail_item, null));
                break;
            case FENSI:
                viewHolder = new FenSiHoder(View.inflate(context, R.layout.daren_fensi_detail_item, null));
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == LIKE) {
            LikeHoder likeholder = (LikeHoder) holder;
            likeholder.setData(datas.get(position));
        } else if (getItemViewType(position) == RECOMMEND) {
            RecommendHoder recommendHolder = (RecommendHoder) holder;
            recommendHolder.setData(datas.get(position));
        } else if (getItemViewType(position) == GUANZHU) {
            GuanZhuHoder guanzhuHolder = (GuanZhuHoder) holder;
            guanzhuHolder.setData(datas.get(position));
        } else if (getItemViewType(position) == FENSI) {
            FenSiHoder fensiholder = (FenSiHoder) holder;
            fensiholder.setData(datas.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    protected class FenSiHoder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_daren_fensi)
        ImageView ivDarenFensi;
        @BindView(R.id.tv_daren_fensi_user_name)
        TextView tvDarenFensiUserName;
        @BindView(R.id.tv_daren_fensi_user_desc)
        TextView tvDarenFensiUserDesc;

        public FenSiHoder(View inflate) {
            super(inflate);
            ButterKnife.bind(this, inflate);
        }

        public void setData(Object o) {
            DarenFensiBean.DataBean.ItemsBean.UsersBean usersBean = (DarenFensiBean.DataBean.ItemsBean.UsersBean) o;
            Glide.with(context).load(usersBean.getUser_image().getOrig()).crossFade().into(ivDarenFensi);
            tvDarenFensiUserName.setText(usersBean.getUser_name());
            tvDarenFensiUserDesc.setText(usersBean.getUser_desc());
        }
    }

    protected class GuanZhuHoder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_daren_guanzhu)
        ImageView ivDarenGuanzhu;
        @BindView(R.id.tv_daren_guanzhu_user_name)
        TextView tvDarenGuanzhuUserName;
        @BindView(R.id.tv_daren_guanzhu_user_desc)
        TextView tvDarenGuanzhuUserDesc;

        public GuanZhuHoder(View inflate) {
            super(inflate);
            ButterKnife.bind(this, inflate);
        }

        public void setData(Object o) {
            DarenGuanZhuBean.DataBean.ItemsBean.UsersBean usersBean = (DarenGuanZhuBean.DataBean.ItemsBean.UsersBean) o;
            Glide.with(context).load(usersBean.getUser_image().getOrig()).crossFade().into(ivDarenGuanzhu);
            tvDarenGuanzhuUserName.setText(usersBean.getUser_name());
            tvDarenGuanzhuUserDesc.setText(usersBean.getUser_desc());
        }
    }

    protected class LikeHoder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_daren_like)
        ImageView ivDarenLike;

        public LikeHoder(View inflate) {
            super(inflate);
            ButterKnife.bind(this, inflate);
        }

        public void setData(Object o) {
            String goods_image = ((DarenLikeBean.DataBean.ItemsBean.GoodsBean) o).getGoods_image();
            Glide.with(context).load(goods_image).crossFade().into(ivDarenLike);
        }
    }

    protected class RecommendHoder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_daren_recommend)
        ImageView ivDarenRecommend;

        public RecommendHoder(View inflate) {
            super(inflate);
            ButterKnife.bind(this, inflate);
        }

        public void setData(Object o) {
            String goods_image = ((DarenRecommendBean.DataBean.ItemsBean.GoodsBean) o).getGoods_image();
            Glide.with(context).load(goods_image).crossFade().into(ivDarenRecommend);
        }
    }
}

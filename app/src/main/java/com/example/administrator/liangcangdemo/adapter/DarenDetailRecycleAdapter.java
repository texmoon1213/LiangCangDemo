package com.example.administrator.liangcangdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.administrator.liangcangdemo.Activity.DarenDetailActivity;
import com.example.administrator.liangcangdemo.R;
import com.example.administrator.liangcangdemo.bean.DarenFensiBean;
import com.example.administrator.liangcangdemo.bean.DarenGuanZhuBean;
import com.example.administrator.liangcangdemo.bean.DarenLikeBean;
import com.example.administrator.liangcangdemo.bean.DarenRecommendBean;
import com.example.administrator.liangcangdemo.untils.ConstantUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    private View VIEW_FOOTER;
    private View VIEW_HEADER;

    //Type
    private static final int TYPE_HEADER = 1001;
    private int TYPE_FOOTER = 1002;
    private RecyclerView mRecyclerView;
    private DarenRecommendBean.DataBean.ItemsBean titlebarDatas;
    private boolean isFirst;
    private RecyclerView.LayoutManager layoutManager;
    private boolean beforeFresh;


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
                        Log.e("TAG", "达人详情recycleVIew成功" + s);
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
            titlebarDatas = darenRecommendBean.getData().getItems();
            List<DarenRecommendBean.DataBean.ItemsBean.GoodsBean> goods = darenRecommendBean.getData().getItems().getGoods();
            datas = goods;
        }

        ifGridLayoutManager();//这里不加的话，点击后就会只占一个格子
        notifyDataSetChanged();
//        notifyItemRangeChanged(4, datas.size());
    }

    @Override
    public int getItemViewType(int position) {
        int itemViewType = -1;
        if (isHeaderView(position)) {
            itemViewType = TYPE_HEADER;
        } else if (LIKE == itemType) {
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

    //判断是不是头
    private boolean isHeaderView(int position) {
        return haveHeaderView() && position == 0;
    }

    //判断是否已经添加了头
    private boolean haveHeaderView() {
        return VIEW_HEADER != null;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        try {
            if (mRecyclerView == null && mRecyclerView != recyclerView) {
                mRecyclerView = recyclerView;
            }
            ifGridLayoutManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ifGridLayoutManager() {
        if (mRecyclerView == null) return;
        layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager.SpanSizeLookup originalSpanSizeLookup = ((GridLayoutManager) layoutManager).getSpanSizeLookup();
//            originalSpanSizeLookup.getSpanIndex()
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return isHeaderView(position) ? ((GridLayoutManager) layoutManager).getSpanCount() : 1;
                }
            });
        }
    }

    public void addHeaderView(View headerView) {
        if (haveHeaderView()) {
            throw new IllegalStateException("hearview has already exists!");
        } else {
            //避免出现宽度自适应
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            headerView.setLayoutParams(params);
            VIEW_HEADER = headerView;
            ifGridLayoutManager();
            notifyItemInserted(0);
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case LIKE:
                viewHolder = new LikeHoder(LayoutInflater.from(context).inflate(R.layout.daren_like_detail_item, parent, false));
                break;
            case RECOMMEND:
                viewHolder = new RecommendHoder(LayoutInflater.from(context).inflate(R.layout.daren_recommend_detail_item, parent, false));
                break;
            case GUANZHU:
                viewHolder = new GuanZhuHoder(LayoutInflater.from(context).inflate(R.layout.daren_guanzhu_detail_item, parent, false));
                break;
            case FENSI:
                viewHolder = new FenSiHoder(LayoutInflater.from(context).inflate(R.layout.daren_fensi_detail_item, parent, false));
                break;
            case TYPE_HEADER:
                viewHolder = new HeadHolder(LayoutInflater.from(context).inflate(R.layout.daren_head_detail, parent, false));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
//        if (datas == null) {
//            return;
//        }

        /**
         *   这里添加的一个字段用来判断是不是在刷新之前。因为刷新数据之前，已经设置了一次适配器，但此时适配器没有数据，
         *   所以需要return,不然会空指针。
         *   上面用了datas来判断，这样可以阻止空指针。不过也会导致，如果跳转的下一个用户，推荐里面没有数据。那么依然会阻塞。
         *   导致头部的数据都无法set进去
         *
         */

        if (!beforeFresh) {
            beforeFresh = true;
            return;
        }
        if (getItemViewType(position) == LIKE) {
            LikeHoder likeholder = (LikeHoder) holder;
            likeholder.setData(datas.get(position - 1));//-1才能拿到正确的数据
            likeholder.ivDarenLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.OnItemClick(v, position, "", "");
                    }
                }
            });
        } else if (getItemViewType(position) == RECOMMEND) {
            RecommendHoder recommendHolder = (RecommendHoder) holder;
            recommendHolder.setData(datas.get(position - 1));
            recommendHolder.ivDarenRecommend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.OnItemClick(v, position, "", "");
                    }
                }
            });
        } else if (getItemViewType(position) == GUANZHU) {
            GuanZhuHoder guanzhuHolder = (GuanZhuHoder) holder;
            guanzhuHolder.setData(datas.get(position - 1));
            guanzhuHolder.ivDarenGuanzhu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        String nextUrl = ((DarenGuanZhuBean.DataBean.ItemsBean.UsersBean) datas.get(position - 1)).getUser_id();
                        String nextName = ((DarenGuanZhuBean.DataBean.ItemsBean.UsersBean) datas.get(position - 1)).getUser_name();
                        mItemClickListener.OnItemClick(v, position, nextUrl, nextName);
                    }
                }
            });
        } else if (getItemViewType(position) == FENSI) {
            FenSiHoder fensiholder = (FenSiHoder) holder;
            fensiholder.setData(datas.get(position - 1));
            fensiholder.ivDarenFensi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        String nextUrl = ((DarenFensiBean.DataBean.ItemsBean.UsersBean) datas.get(position - 1)).getUser_id();
                        String nextName = ((DarenFensiBean.DataBean.ItemsBean.UsersBean) datas.get(position - 1)).getUser_name();
                        mItemClickListener.OnItemClick(v, position, nextUrl, nextName);
                    }
                }
            });
        } else if (getItemViewType(position) == TYPE_HEADER) {
            HeadHolder headHolder = (HeadHolder) holder;
            headHolder.setData(titlebarDatas);
        }
    }

    @Override
    public int getItemCount() {
        int count = (datas == null ? 0 : datas.size());
        if (VIEW_HEADER != null) {
            count++;
        }
        return count;
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

    public interface ItemClickListener {
        void OnItemClick(View v, int position, String nextUrl, String nextName);
    }

    ItemClickListener mItemClickListener;

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    protected class HeadHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_head)
        ImageView ivHead;
        @BindView(R.id.tv_username_daren_detail)
        TextView tvUsernameDarenDetail;
        @BindView(R.id.tv_duty_daren_detail)
        TextView tvDutyDarenDetail;
        @BindView(R.id.tv_sixin_daren_detail)
        TextView tvSixinDarenDetail;
        @BindView(R.id.tv_guanzhu_daren_detail)
        TextView tvGuanzhuDarenDetail;
        @BindView(R.id.rb_like)
        RadioButton rbLike;
        @BindView(R.id.rb_recommend)
        RadioButton rbRecommend;
        @BindView(R.id.rb_guanzhu)
        RadioButton rbGuanzhu;
        @BindView(R.id.rb_fensi)
        RadioButton rbFensi;
        @BindView(R.id.rg_daren_detail)
        RadioGroup rgDarenDetail;

        public HeadHolder(View inflate) {
            super(inflate);
            ButterKnife.bind(this, inflate);
        }

//        public void setData(Object o) {
//            initData();
//        }


//        private void initData() {
//            String userId = getIntent().getStringExtra("daren_userId");
//            String username = getIntent().getStringExtra("daren_userName");
//            tvTitlebar.setText(username);
//            getDataFromNet(userId);
//        }

//        private void getDataFromNet(String userId) {
//            OkGo.getInstance().addCommonParams(new HttpParams("owner_id", userId))
//                    .get(ConstantUtils.DAREN_DETAIL_RECOMMEND)
//                    .execute(new StringCallback() {
//                        @Override
//                        public void onSuccess(String s, Call call, Response response) {
//                            Log.e("TAG", "达人详情页联网成功" + s);
//                            progress(s);
//                        }
//
//                        @Override
//                        public void onError(Call call, Response response, Exception e) {
//                            super.onError(call, response, e);
//                            Log.e("TAG", "达人详情页联网失败" + e.getMessage());
//                        }
//                    });
//        }
//
//        private void progress(String s) {
//            DarenRecommendBean darenLikeBean = JSON.parseObject(s, DarenRecommendBean.class);
//            DarenRecommendBean.DataBean.ItemsBean items = darenLikeBean.getData().getItems();
//            setData(items);
//        }

        private void setData(DarenRecommendBean.DataBean.ItemsBean items) {
            if (!isFirst) {
                Glide.with(context).load(items.getUser_image().getOrig()).crossFade().into(ivHead);
                tvUsernameDarenDetail.setText(items.getUser_name());
                tvDutyDarenDetail.setText(items.getUser_desc());
                rbLike.setText("喜欢\n" + items.getLike_count());
                rbFensi.setText("粉丝\n" + items.getFollowed_count());
                rbGuanzhu.setText("关注\n" + items.getFollowing_count());
                rbRecommend.setText("推荐\n" + items.getRecommendation_count());
                rbRecommend.setBackgroundResource(R.color.text_qianhui);
//            adapter = new DarenDetailRecycleAdapter(context, items.getUser_id());
//            recycleDarenDetail.setAdapter(adapter);
//            notifyDataSetChanged();
//            refresh(ConstantUtils.DAREN_DETAIL_RECOMMEND, DarenDetailRecycleAdapter.RECOMMEND);
//            mRecyclerView.setLayoutManager(new GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false));
//            adapter.addHeaderView(View.inflate(DarenDetailActivity.this, R.layout.daren_head_detail, null));
                setOnItemClickListener(new DarenDetailListener());
                isFirst = true;
            }
        }


        @OnClick({R.id.rb_like, R.id.rb_recommend, R.id.rb_guanzhu, R.id.rb_fensi})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.rb_like:
                    Log.e("TAG", "喜歡-------------------》");
                    rbRecommend.setBackgroundResource(R.color.zhuti_huise);
                    rbFensi.setBackgroundResource(R.color.zhuti_huise);
                    rbGuanzhu.setBackgroundResource(R.color.zhuti_huise);
                    rbLike.setBackgroundResource(R.color.text_qianhui);
                    if (datas != null) {
                        datas.clear();//清空數據
                    }
                    refresh(ConstantUtils.DAREN_DETAIL_LIKE, DarenDetailRecycleAdapter.LIKE);
                    ((GridLayoutManager) layoutManager).setSpanCount(2);
//                    mRecyclerView.setLayoutManager(new GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false));
                    break;
                case R.id.rb_recommend:
                    Log.e("TAG", "推荐-------------------》");
                    rbRecommend.setBackgroundResource(R.color.text_qianhui);
                    rbFensi.setBackgroundResource(R.color.zhuti_huise);
                    rbGuanzhu.setBackgroundResource(R.color.zhuti_huise);
                    rbLike.setBackgroundResource(R.color.zhuti_huise);
                    if (datas != null) {
                        datas.clear();//清空數據
                    }
                    refresh(ConstantUtils.DAREN_DETAIL_RECOMMEND, DarenDetailRecycleAdapter.RECOMMEND);
                    ((GridLayoutManager) layoutManager).setSpanCount(2);
//                    mRecyclerView.setLayoutManager(new GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false));
                    break;
                case R.id.rb_guanzhu:
                    Log.e("TAG", "关注-------------------》");
                    rbRecommend.setBackgroundResource(R.color.zhuti_huise);
                    rbFensi.setBackgroundResource(R.color.zhuti_huise);
                    rbGuanzhu.setBackgroundResource(R.color.text_qianhui);
                    rbLike.setBackgroundResource(R.color.zhuti_huise);
                    if (datas != null) {
                        datas.clear();//清空數據
                    }
                    refresh(ConstantUtils.DAREN_DETAIL_GUANZHU, DarenDetailRecycleAdapter.GUANZHU);
                    ((GridLayoutManager) layoutManager).setSpanCount(3);
//                    mRecyclerView.setLayoutManager(new GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false));
                    break;
                case R.id.rb_fensi:
                    Log.e("TAG", "粉丝-------------------》");
                    rbRecommend.setBackgroundResource(R.color.zhuti_huise);
                    rbFensi.setBackgroundResource(R.color.text_qianhui);
                    rbGuanzhu.setBackgroundResource(R.color.zhuti_huise);
                    rbLike.setBackgroundResource(R.color.zhuti_huise);
                    if (datas != null) {
                        datas.clear();//清空數據
                    }
                    refresh(ConstantUtils.DAREN_DETAIL_FOLLOW, DarenDetailRecycleAdapter.FENSI);
                    ((GridLayoutManager) layoutManager).setSpanCount(3);
//                    mRecyclerView.setLayoutManager(new GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false));
                    break;
            }
        }

        private class DarenDetailListener implements DarenDetailRecycleAdapter.ItemClickListener {
            @Override
            public void OnItemClick(View v, int position, String nextUrl, String nextName) {
                if (TextUtils.isEmpty(nextUrl)) {
                    Toast.makeText(context, "這不是咱家的哦", Toast.LENGTH_SHORT).show();
                    return;
                }
//                isFirst = false;
                Intent initent = new Intent(context, DarenDetailActivity.class);
                initent.putExtra("daren_userId", nextUrl);
                initent.putExtra("daren_userName", nextName);
                context.startActivity(initent);
            }
        }

    }
}

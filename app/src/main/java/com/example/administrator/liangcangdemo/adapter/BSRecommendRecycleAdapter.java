package com.example.administrator.liangcangdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.liangcangdemo.Activity.ShowImageAndGifActivity;
import com.example.administrator.liangcangdemo.R;
import com.example.administrator.liangcangdemo.bean.BSRecommendBean;
import com.example.administrator.liangcangdemo.untils.TimeUntils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Administrator on 2017/7/14.
 */

public class BSRecommendRecycleAdapter extends RecyclerView.Adapter {

    private List<BSRecommendBean.ListBean> datas;
    private Context context;
    private static final int TYPE_VIDEO = 0;
    private static final int TYPE_TEXT = 1;
    private static final int TYPE_IMAGE = 2;
    private static final int TYPE_GIF = 3;

    public BSRecommendRecycleAdapter(Context context, List<BSRecommendBean.ListBean> listBeen) {
        this.context = context;
        this.datas = listBeen;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHodler = null;
        switch (viewType) {
            case TYPE_VIDEO:
                viewHodler = new VideoHoder(View.inflate(context, R.layout.bs_recommend_video, null));
                break;
            case TYPE_TEXT:
                viewHodler = new TextHoder(View.inflate(context, R.layout.bs_recommend_text, null));
                break;
            case TYPE_IMAGE:
                viewHodler = new ImageHoder(View.inflate(context, R.layout.bs_recommend_image, null));
                break;
            case TYPE_GIF:
                viewHodler = new GifHoder(View.inflate(context, R.layout.bs_recommend_gif, null));
                break;
        }
        return viewHodler;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_VIDEO) {
            VideoHoder videoHoder = (VideoHoder) holder;
            videoHoder.setData(datas.get(position));
        } else if (getItemViewType(position) == TYPE_TEXT) {
            TextHoder textHolder = (TextHoder) holder;
            textHolder.setData(datas.get(position));
        } else if (getItemViewType(position) == TYPE_IMAGE) {
            ImageHoder imageHolder = (ImageHoder) holder;
            imageHolder.setData(datas.get(position));
        } else if (getItemViewType(position) == TYPE_GIF) {
            GifHoder gifHolder = (GifHoder) holder;
            gifHolder.setData(datas.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
//        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        int itemViewType = -1;
        //根据位置，从列表中得到一个数据对象
        String type = datas.get(position).getType();
        if (type.equals("video")) {
            itemViewType = TYPE_VIDEO;
        } else if (type.equals("text")) {
            itemViewType = TYPE_TEXT;
        } else if (type.equals("image")) {
            itemViewType = TYPE_IMAGE;
        } else if (type.equals("gif")) {
            itemViewType = TYPE_GIF;
        }
        return itemViewType;
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

        public void setData(BSRecommendBean.ListBean listBean) {
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BSRecommendBean.ListBean listEntity = datas.get(getLayoutPosition());
                    if (listEntity != null) {
                        //3.传递视频列表
                        Intent intent = new Intent(context, ShowImageAndGifActivity.class);
                        if (listEntity.getType().equals("gif")) {
                            String url = listEntity.getGif().getImages().get(0);
                            intent.putExtra("url", url);
                            context.startActivity(intent);
                        } else if (listEntity.getType().equals("image")) {
                            String url = listEntity.getImage().getBig().get(0);
                            intent.putExtra("url", url);
                            context.startActivity(intent);
                        }
                    }
                }
            });
        }
    }

    protected class VideoHoder extends BaseHolder {
        @BindView(R.id.tv_videomiddle_context)
        TextView tvVideomiddleContext;
        @BindView(R.id.jcv_videoplayer)
        JCVideoPlayerStandard jcvVideoplayer;
        @BindView(R.id.tv_play_nums)
        TextView tvPlayNums;
        @BindView(R.id.tv_video_duration)
        TextView tvVideoDuration;
        @BindView(R.id.rl_videomiddle_holder)
        RelativeLayout rlVideomiddleHolder;
        private TimeUntils utils;

        public VideoHoder(View inflate) {
            super(inflate);
            ButterKnife.bind(this, inflate);
            utils = new TimeUntils();
        }

        @Override
        public void setData(BSRecommendBean.ListBean listBean) {
            super.setData(listBean);
            //设置文本-所有的都有,只有广告没有哦
            tvVideomiddleContext.setText(listBean.getText() + "_" + listBean.getType());
            //视频特有的------------------------
            //第一个参数是视频播放地址，第二个参数是显示封面的地址，第三参数是标题
            boolean setUp = jcvVideoplayer.setUp(
                    listBean.getVideo().getVideo().get(0), JCVideoPlayer.SCREEN_LAYOUT_LIST,
                    "videoTitle");
            //加载图片
            if (setUp) {
                Glide.with(context).load(listBean.getVideo().getThumbnail().get(0)).into(jcvVideoplayer.thumbImageView);
            }
            tvPlayNums.setText(listBean.getVideo().getPlaycount() + "次播放");
            tvVideoDuration.setText(utils.stringForTime(listBean.getVideo().getDuration() * 1000) + "");
        }
    }

    private class TextHoder extends BaseHolder {
        private TextView textView;

        public TextHoder(View inflate) {
            super(inflate);
            textView = (TextView) inflate.findViewById(R.id.tv_textmiddle_context);
        }

        @Override
        public void setData(BSRecommendBean.ListBean listBean) {
            super.setData(listBean);
            textView.setText(listBean.getText() + listBean.getType());
        }
    }

    private class ImageHoder extends BaseHolder {

        private TextView textView;
        private ImageView imageView;

        public ImageHoder(View inflate) {
            super(inflate);
            textView = (TextView) inflate.findViewById(R.id.tv_imagemiddle_context);
            imageView = (ImageView) inflate.findViewById(R.id.iv_imagemiddle_image_icon);
        }

        @Override
        public void setData(BSRecommendBean.ListBean listBean) {
            super.setData(listBean);
            textView.setText(listBean.getText() + listBean.getType());
            Glide.with(context).load(listBean.getImage().getBig().get(0)).into(imageView);
        }
    }

    private class GifHoder extends BaseHolder {

        private ImageView imageView;
        private TextView textView;

        public GifHoder(View inflate) {
            super(inflate);
            imageView = (ImageView) inflate.findViewById(R.id.iv_gitmiddle_image_gif);
            textView = (TextView) inflate.findViewById(R.id.tv_gitmiddle_context);
        }

        @Override
        public void setData(BSRecommendBean.ListBean listBean) {
            super.setData(listBean);
            textView.setText(listBean.getText() + listBean.getType());
            if (listBean.getGif() != null && listBean.getGif() != null && listBean.getGif().getImages() != null) {
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                Glide.with(context).load(listBean.getGif().getImages().get(0)).into(imageView);
            }
        }
    }

}

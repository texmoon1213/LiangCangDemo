package com.example.administrator.liangcangdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.liangcangdemo.R;
import com.example.administrator.liangcangdemo.bean.ShopHomeBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/6.
 */

public class ShopHomeRecycleAdapter extends RecyclerView.Adapter {
    private static final int TYPE_ONE = 0;

    private static final int TYPE_TWO = 1;

    private static final int TYPE_FOUR = 2;


    private Context context;
    private List<ShopHomeBean.DataBean.ItemsBean.ListBean> datas;

    @Override
    public int getItemViewType(int position) {
        int itemViewType = -1;
        //根据位置，从列表中得到一个数据对象
        ShopHomeBean.DataBean.ItemsBean.ListBean listBean = datas.get(position);
        String type = listBean.getHome_type();//得到类型

        if ("1".equals(type)) {
            itemViewType = TYPE_ONE;
        } else if ("2".equals(type)) {
            itemViewType = TYPE_TWO;
        } else if ("4".equals(type)) {
            itemViewType = TYPE_FOUR;
        }
        return itemViewType;
    }

    public ShopHomeRecycleAdapter(Context context, List<ShopHomeBean.DataBean.ItemsBean.ListBean> listBeen) {
        this.context = context;
        this.datas = listBeen;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_ONE://视频
                viewHolder = new OneHoder(View.inflate(context, R.layout.shop_home_item_one, null));
                break;
            case TYPE_TWO://图片
                viewHolder = new TwoHoder(View.inflate(context, R.layout.shop_home_item_two, null));
                break;
            case TYPE_FOUR://文字
                viewHolder = new FourHoder(View.inflate(context, R.layout.shop_home_item_four, null));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_ONE) {
            OneHoder videoHoder = (OneHoder) holder;
            videoHoder.setData(datas.get(position));
            videoHoder.ivShopHomeItemOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.OnItemClick(v, position, "one");
                    }
                }
            });
        } else if (getItemViewType(position) == TYPE_TWO) {
            TwoHoder imageHoder = (TwoHoder) holder;
            imageHoder.setData(datas.get(position));
            imageHoder.iv1ShopHomeItemTwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.OnItemClick(v, position, "one");
                    }
                }
            });
            imageHoder.iv2ShopHomeItemTwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.OnItemClick(v, position, "two");
                    }
                }
            });
        } else if (getItemViewType(position) == TYPE_FOUR) {
            FourHoder textHoder = (FourHoder) holder;
            textHoder.setData(datas.get(position));
            textHoder.iv1ShopHomeItemFour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.OnItemClick(v, position, "one");
                    }
                }
            });
            textHoder.iv2ShopHomeItemFour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.OnItemClick(v, position, "two");
                    }
                }
            });
            textHoder.iv3ShopHomeItemFour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.OnItemClick(v, position, "three");
                    }
                }
            });
            textHoder.iv4ShopHomeItemFour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.OnItemClick(v, position, "four");
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }


    protected class OneHoder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_shop_home_item_one)
        ImageView ivShopHomeItemOne;

        public OneHoder(View inflate) {
            super(inflate);
            ButterKnife.bind(this, inflate);
        }

        public void setData(ShopHomeBean.DataBean.ItemsBean.ListBean listBean) {
            Glide.with(context).load(listBean.getOne().getPic_url()).crossFade().into(ivShopHomeItemOne);
        }
    }

    protected class TwoHoder extends RecyclerView.ViewHolder {
        ImageView iv1ShopHomeItemTwo;
        ImageView iv2ShopHomeItemTwo;

        public TwoHoder(View inflate) {
            super(inflate);
            iv1ShopHomeItemTwo = (ImageView) inflate.findViewById(R.id.iv1_shop_home_item_two);
            iv2ShopHomeItemTwo = (ImageView) inflate.findViewById(R.id.iv2_shop_home_item_two);
            Log.e("TAG", "TwoHoder");
        }

        public void setData(ShopHomeBean.DataBean.ItemsBean.ListBean listBean) {
            Log.e("TAG", "TwoHoder.setData");
            Glide.with(context).load(listBean.getOne().getPic_url()).error(R.drawable.daren).into(iv1ShopHomeItemTwo);
            Glide.with(context).load(listBean.getTwo().getPic_url()).error(R.drawable.daren).into(iv2ShopHomeItemTwo);
        }
    }

    protected class FourHoder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv1_shop_home_item_four)
        ImageView iv1ShopHomeItemFour;
        @BindView(R.id.iv2_shop_home_item_four)
        ImageView iv2ShopHomeItemFour;
        @BindView(R.id.iv3_shop_home_item_four)
        ImageView iv3ShopHomeItemFour;
        @BindView(R.id.iv4_shop_home_item_four)
        ImageView iv4ShopHomeItemFour;

        public FourHoder(View inflate) {
            super(inflate);
            ButterKnife.bind(this, inflate);
        }

        public void setData(ShopHomeBean.DataBean.ItemsBean.ListBean listBean) {
//            text.setText(listBean.getOne().getTopic_name() + ",\n" + listBean.getTwo().getTopic_name() + ",\n"
//                    + listBean.getThree().getTopic_name() + ",\n" + listBean.getFour().getTopic_name());
            Glide.with(context).load(listBean.getOne().getPic_url()).error(R.drawable.daren).into(iv1ShopHomeItemFour);
            Glide.with(context).load(listBean.getTwo().getPic_url()).error(R.drawable.daren).into(iv2ShopHomeItemFour);
            Glide.with(context).load(listBean.getThree().getPic_url()).error(R.drawable.daren).into(iv3ShopHomeItemFour);
            Glide.with(context).load(listBean.getFour().getPic_url()).error(R.drawable.daren).into(iv4ShopHomeItemFour);

        }
    }

    public interface ItemClickListener {
        void OnItemClick(View v, int i, String position);
    }

    ItemClickListener mItemClickListener;

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
}

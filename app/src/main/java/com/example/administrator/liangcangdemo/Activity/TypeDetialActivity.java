package com.example.administrator.liangcangdemo.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.administrator.liangcangdemo.R;
import com.example.administrator.liangcangdemo.bean.ShopDetailBean;
import com.example.administrator.liangcangdemo.bean.ShopTypeListBean;
import com.example.administrator.liangcangdemo.untils.ConstantUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.youth.banner.transformer.AccordionTransformer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class TypeDetialActivity extends AppCompatActivity {

    @BindView(R.id.iv_back_item_detail)
    ImageView ivBackItemDetail;
    @BindView(R.id.iv_cart_item_detail)
    ImageView ivCartItemDetail;
    @BindView(R.id.banner_item_detail)
    Banner bannerItemDetail;
    @BindView(R.id.tv_owner_name_item_detail)
    TextView tvOwnerNameItemDetail;
    @BindView(R.id.tv_goods_name_item_detail)
    TextView tvGoodsNameItemDetail;
    @BindView(R.id.tv_promotion_note_item_detail)
    TextView tvPromotionNoteItemDetail;
    @BindView(R.id.tv_discount_price_item_detail)
    TextView tvDiscountPriceItemDetail;
    @BindView(R.id.tv_like_count_item_detail)
    TextView tvLikeCountItemDetail;
    @BindView(R.id.iv_share_item_detail)
    ImageView ivShareItemDetail;
    private String detailUrl;
    private ShopDetailBean.DataBean.ItemsBean items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_detial);
        ButterKnife.bind(this);
        initData();
    }


    private void initData() {
        getDataFromNet();
    }

    private void getDataFromNet() {
        ShopTypeListBean.DataBean.ItemsBean type_list_bean = (ShopTypeListBean.DataBean.ItemsBean) getIntent().getSerializableExtra("type_list_bean");
        detailUrl = ConstantUtils.SHOP_TYPE_DETAIL_START + type_list_bean.getGoods_id() + ConstantUtils.SHOP_TYPE_DETAIL_END;
        OkGo.<String>get(detailUrl).tag(this).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                Log.e("TAG", "分类item成功==" + s);
                processData(s);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                Log.e("TAG", "分类item失敗==" + e.getMessage());
            }
        });
    }

    private void processData(String s) {
        ShopDetailBean shopDetailBean = JSON.parseObject(s, ShopDetailBean.class);
        items = shopDetailBean.getData().getItems();
        initBanner();
        initView();
    }

    private void initView() {
        tvOwnerNameItemDetail.setText(items.getOwner_name());
        tvGoodsNameItemDetail.setText(items.getGoods_name());
        tvPromotionNoteItemDetail.setText(items.getPromotion_note());
        tvLikeCountItemDetail.setText(items.getLike_count());
        String price = TextUtils.isEmpty(items.getDiscount_price()) ? items.getPrice() : items.getDiscount_price();
        tvDiscountPriceItemDetail.setText("￥" + price);
    }

    private void initBanner() {
        //准备图片集合
        List<String> imageUrls = items.getImages_item();
        //简单使用
        bannerItemDetail.setImages(imageUrls)
                .setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        Glide.with(context)
                                .load(path)
                                .crossFade()     //淡入效果
                                .into(imageView);
                    }
                })
                .start();
        //设置动画效果-手风琴效果
        bannerItemDetail.setBannerAnimation(AccordionTransformer.class);
        //设置自动轮播，默认为true
        bannerItemDetail.isAutoPlay(false);
        //设置轮播时间
        bannerItemDetail.setDelayTime(1500);
        //设置点击事件
        bannerItemDetail.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });
    }
}

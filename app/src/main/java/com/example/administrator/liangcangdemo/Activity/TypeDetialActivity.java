package com.example.administrator.liangcangdemo.Activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.administrator.liangcangdemo.R;
import com.example.administrator.liangcangdemo.bean.ShopDetailBean;
import com.example.administrator.liangcangdemo.bean.ShopTypeListBean;
import com.example.administrator.liangcangdemo.untils.ConstantUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
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
    @BindView(R.id.iv_brandlogo_detail)
    ImageView ivBrandlogoDetail;
    @BindView(R.id.tv_brandname_detail)
    TextView tvBrandnameDetail;
    @BindView(R.id.ll_brandlogo_brandname)
    LinearLayout llBrandlogoBrandname;
    @BindView(R.id.rb_detail_left)
    RadioButton rbDetailLeft;
    @BindView(R.id.rb_detail_right)
    RadioButton rbDetailRight;
    @BindView(R.id.ll_detail_left)
    LinearLayout llDetailLeft;
    @BindView(R.id.tv_detail_right)
    TextView tvDetailRight;
    @BindView(R.id.rg_detail)
    RadioGroup rgDetail;
    @BindView(R.id.ll_detail_right)
    LinearLayout llDetailRight;
    private String detailUrl;
    private ShopDetailBean.DataBean.ItemsBean items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_detial);
        ButterKnife.bind(this);
        initData();
        initListener();
    }

    private void initListener() {
        rgDetail.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rb_detail_left) {
                    llDetailLeft.setVisibility(View.VISIBLE);
                    llDetailRight.setVisibility(View.INVISIBLE);
                } else if (checkedId == R.id.rb_detail_right) {
                    llDetailLeft.setVisibility(View.INVISIBLE);
                    llDetailRight.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void initData() {
        getDataFromNet();
    }

    private void getDataFromNet() {
        ShopTypeListBean.DataBean.ItemsBean type_list_bean = (ShopTypeListBean.DataBean.ItemsBean) getIntent().getSerializableExtra("type_list_bean");
        detailUrl = ConstantUtils.SHOP_TYPE_DETAIL;
        OkGo.getInstance().
                addCommonParams(new HttpParams("goods_id", type_list_bean.getGoods_id()))
                .<String>get(detailUrl).tag(this).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                Log.e("TAGS", "分类item成功==" + s);
                processData(s);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                Log.e("TAGS", "分类item失敗==" + e.getMessage());
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
        Glide.with(TypeDetialActivity.this).load(items.getBrand_info().getBrand_logo()).crossFade().into(ivBrandlogoDetail);
        tvBrandnameDetail.setText(items.getBrand_info().getBrand_name());
        llDetailRight.setVisibility(View.INVISIBLE);
        tvDetailRight.setText(items.getGood_guide().getContent());
        List<ShopDetailBean.DataBean.ItemsBean.GoodsInfoBean> goods_info = items.getGoods_info();

        for (int i = 0; i < goods_info.size(); i++) {
            int type = goods_info.get(i).getType();
            if (type == 1) {
                ImageView imageView = new ImageView(TypeDetialActivity.this);
                Glide.with(TypeDetialActivity.this).load(goods_info.get(i).getContent().getImg()).crossFade().into(imageView);
                llDetailLeft.addView(imageView);
            }
        }
        String contentText = null;
        for (int i = 0; i < goods_info.size(); i++) {
            int type = goods_info.get(i).getType();
            if (type == 0) {
//                tvDetailRight.setText(goods_info.get(i).getContent().get);
//                llDetailLeft.addView(imageView);
                String text = goods_info.get(i).getContent().getText();
                contentText += text + "\n";
            }
        }
        TextView textView = new TextView(TypeDetialActivity.this);
        textView.setText(contentText);
        textView.setTextColor(Color.WHITE);
        llDetailLeft.addView(textView);

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

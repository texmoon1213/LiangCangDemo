package com.example.administrator.liangcangdemo.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.liangcangdemo.R;
import com.example.administrator.liangcangdemo.bean.ShopDetailBean;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopCarActivity extends AppCompatActivity {

    @BindView(R.id.iv_goods_image_shopcar)
    ImageView ivGoodsImageShopcar;
    @BindView(R.id.tv_brand_name_shopcar)
    TextView tvBrandNameShopcar;
    @BindView(R.id.tv_goods_name_shopcar)
    TextView tvGoodsNameShopcar;
    @BindView(R.id.tv_price_shopcar)
    TextView tvPriceShopcar;
    @BindView(R.id.tv_type_name)
    TextView tvTypeName;
    @BindView(R.id.ll_attrList_shopcar)
    LinearLayout llAttrListShopcar;
    private ShopDetailBean.DataBean.ItemsBean shopcar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_car);
        ButterKnife.bind(this);
        initData();
        setData();
    }

    private void setData() {
        ShopDetailBean.DataBean.ItemsBean.SkuInfoBean skuInfoBean = shopcar.getSku_info().get(0);
        Glide.with(this).load(shopcar.getGoods_image()).crossFade().into(ivGoodsImageShopcar);
        tvBrandNameShopcar.setText(shopcar.getBrand_info().getBrand_name());
        tvGoodsNameShopcar.setText(shopcar.getGoods_name());
        tvPriceShopcar.setText("￥" + shopcar.getPrice());
        tvTypeName.setText(skuInfoBean.getType_name());
        final int count = skuInfoBean.getAttrList().size();
        for (int i = 0; i < count; i++) {
            final TextView textView = new TextView(ShopCarActivity.this);
            textView.setText(skuInfoBean.getAttrList().get(i).getAttr_name());
            textView.setTextColor(Color.WHITE);
            if (i == 0) {
                textView.setBackgroundColor(Color.BLUE);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(50, 0, 0, 0);
                llAttrListShopcar.addView(textView, layoutParams);
            } else {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(100, 0, 0, 0);
                llAttrListShopcar.addView(textView, layoutParams);
            }

            final int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    setType(skuInfoBean, num, count);
                    for (int j = 0; j < count; j++) {
                        llAttrListShopcar.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);
                    }
                    llAttrListShopcar.getChildAt(finalI).setBackgroundColor(Color.BLUE);
                }
            });
        }
    }

    private void initData() {
        shopcar = (ShopDetailBean.DataBean.ItemsBean) getIntent().getSerializableExtra("shopcar");
    }
}

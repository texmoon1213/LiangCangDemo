package com.example.administrator.liangcangdemo.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.liangcangdemo.Activity.TypeListActivity;
import com.example.administrator.liangcangdemo.R;
import com.example.administrator.liangcangdemo.base.BaseFragment;
import com.example.administrator.liangcangdemo.untils.ConstantUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/6.
 */

public class GiftFragment extends BaseFragment {
    @BindView(R.id.iv1_gift_home)
    ImageView iv1GiftHome;
    @BindView(R.id.iv2_gift_home)
    ImageView iv2GiftHome;
    @BindView(R.id.iv3_gift_home)
    ImageView iv3GiftHome;
    @BindView(R.id.iv4_gift_home)
    ImageView iv4GiftHome;
    @BindView(R.id.iv5_gift_home)
    ImageView iv5GiftHome;
    @BindView(R.id.iv6_gift_home)
    ImageView iv6GiftHome;
    @BindView(R.id.iv7_gift_home)
    ImageView iv7GiftHome;
    @BindView(R.id.tv_gift_home)
    TextView tvGiftHome;
    Unbinder unbinder;
    private String url;

    @Override
    public View initView() {
        View inflate = View.inflate(context, R.layout.fragment_gift_shop, null);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv1_gift_home, R.id.iv2_gift_home, R.id.iv3_gift_home, R.id.iv4_gift_home, R.id.iv5_gift_home, R.id.iv6_gift_home, R.id.iv7_gift_home, R.id.tv_gift_home})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv1_gift_home:
                url = ConstantUtils.SHOP_GIFT_1;
                break;
            case R.id.iv2_gift_home:
                url = ConstantUtils.SHOP_GIFT_FESTIVAL;
                break;
            case R.id.iv3_gift_home:
                url = ConstantUtils.SHOP_GIFT_LOVE;
                break;
            case R.id.iv4_gift_home:
                url = ConstantUtils.SHOP_GIFT_BRITHDAY;
                break;
            case R.id.iv5_gift_home:
                url = ConstantUtils.SHOP_GIFT_FRIEND;
                break;
            case R.id.iv6_gift_home:
                url = ConstantUtils.SHOP_GIFT_CHILD;
                break;
            case R.id.iv7_gift_home:
                url = ConstantUtils.SHOP_GIFT_PARENT;
                break;
        }
        Intent intent = new Intent(context, TypeListActivity.class);
        intent.putExtra("fromgift", url);
        startActivity(intent);
    }
}

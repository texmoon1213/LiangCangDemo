package com.example.administrator.liangcangdemo.Activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.administrator.liangcangdemo.R;
import com.example.administrator.liangcangdemo.adapter.ShopTypeListRecycleAdapter;
import com.example.administrator.liangcangdemo.bean.ShopTypeBean;
import com.example.administrator.liangcangdemo.bean.ShopTypeListBean;
import com.example.administrator.liangcangdemo.untils.ConstantUtils;
import com.example.administrator.liangcangdemo.view.ComprehensiveItemDecoration;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class TypeListActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.search_titlebar)
    ImageView searchTitlebar;
    @BindView(R.id.back_titlebar)
    ImageView backTitlebar;
    @BindView(R.id.tv_titlebar)
    TextView tvTitlebar;
    @BindView(R.id.shopcar_titlebar)
    ImageView shopcarTitlebar;
    @BindView(R.id.ll_typelist)
    LinearLayout llTypelist;
    @BindView(R.id.recycle_type_list)
    RecyclerView recycleTypeList;
    //    @BindView(R.id.pop_all)
//    TextView popAll;
//    @BindView(R.id.pop_200)
//    TextView pop200;
//    @BindView(R.id.pop_500)
//    TextView pop500;
//    @BindView(R.id.pop_1000)
//    TextView pop1000;
//    @BindView(R.id.pop_3000)
//    TextView pop3000;
    private ShopTypeBean.DataBean.ItemsBean type_bean;
    private String itemurl;
    private PopupWindow popupWindow;
    private TextView popAll;
    private TextView pop200;
    private TextView pop500;
    private TextView pop1000;
    private TextView pop3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_list);
        ButterKnife.bind(this);
        initData();
        initView();
        initListener();
    }

    private void initListener() {
        llTypelist.setOnClickListener(this);
        popAll.setOnClickListener(this);
        pop200.setOnClickListener(this);
        pop500.setOnClickListener(this);
        pop1000.setOnClickListener(this);
        pop3000.setOnClickListener(this);
    }

    private void showPopupWindow() {
        popupWindow.showAsDropDown(llTypelist);
    }

    private void initView() {
        searchTitlebar.setVisibility(View.GONE);
        backTitlebar.setVisibility(View.VISIBLE);
        tvTitlebar.setText("商店");

        popupWindow = new PopupWindow(TypeListActivity.this);

        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View inflate = LayoutInflater.from(this).inflate(R.layout.popuwindow_type_item, null);
        popupWindow.setContentView(inflate);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popAll = (TextView) inflate.findViewById(R.id.pop_all);
        pop200 = (TextView) inflate.findViewById(R.id.pop_200);
        pop500 = (TextView) inflate.findViewById(R.id.pop_500);
        pop1000 = (TextView) inflate.findViewById(R.id.pop_1000);
        pop3000 = (TextView) inflate.findViewById(R.id.pop_3000);
    }

    private void initData() {
        String fromgift = getIntent().getStringExtra("fromgift");
        if (!TextUtils.isEmpty(fromgift)) {
            itemurl = fromgift;
        } else {
            type_bean = (ShopTypeBean.DataBean.ItemsBean) getIntent().getSerializableExtra("type_bean");
            String cat_id = type_bean.getCat_id();
            int length = cat_id.length();
            //拿到联网请求数据的url
            if (length == 2) {
                itemurl = ConstantUtils.SHOP_TYPE_START + "00" + cat_id + ConstantUtils.SHOP_TYPE_END;
            } else if (length == 3) {
                itemurl = ConstantUtils.SHOP_TYPE_START + "0" + cat_id + ConstantUtils.SHOP_TYPE_END;
            }
        }
        getDataFromNet();
    }

    private void getDataFromNet() {
        OkGo.<String>get(itemurl).tag(this).execute(new StringCallback() {
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
        ShopTypeListBean shopTypeListBean = JSON.parseObject(s, ShopTypeListBean.class);
        List<ShopTypeListBean.DataBean.ItemsBean> itemsBeen = shopTypeListBean.getData().getItems();
        if (itemsBeen != null && itemsBeen.size() > 0) {
            ShopTypeListRecycleAdapter adapter = new ShopTypeListRecycleAdapter(TypeListActivity.this, itemsBeen);
            recycleTypeList.setAdapter(adapter);

            recycleTypeList.setLayoutManager(new GridLayoutManager(TypeListActivity.this, 2, LinearLayout.VERTICAL, false));
//            recycleTypeList.addItemDecoration(new DividerGridItemDecorationHY(TypeListActivity.this));
            recycleTypeList.addItemDecoration(new ComprehensiveItemDecoration(15));//设置间距
            adapter.setOnItemClickListener(new TypeListener(itemsBeen));

        } else {
            Toast.makeText(TypeListActivity.this, "联网失败", Toast.LENGTH_SHORT).show();
        }
    }


    //    @OnClick({R.id.ll_typelist, R.id.pop_all, R.id.pop_200, R.id.pop_500, R.id.pop_1000, R.id.pop_3000})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.ll_typelist:
//                showPopupWindow();
//                break;
//            case R.id.pop_all:
//                Toast.makeText(TypeListActivity.this, "all", Toast.LENGTH_SHORT).show();
//                popAll.setBackgroundResource(R.drawable.daren);
//                popupWindow.dismiss();
//                break;
//            case R.id.pop_200:
//                popAll.setBackgroundResource(R.drawable.daren);
//                popupWindow.dismiss();
//                break;
//            case R.id.pop_500:
//                popAll.setBackgroundResource(R.drawable.daren);
//                popupWindow.dismiss();
//                break;
//            case R.id.pop_1000:
//                popAll.setBackgroundResource(R.drawable.daren);
//                popupWindow.dismiss();
//                break;
//            case R.id.pop_3000:
//                popAll.setBackgroundResource(R.drawable.daren);
//                popupWindow.dismiss();
//                break;
//        }
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_typelist:
                showPopupWindow();
                break;
            case R.id.pop_all:
                Toast.makeText(TypeListActivity.this, "all", Toast.LENGTH_SHORT).show();
                popAll.setBackgroundResource(R.drawable.daren);
                break;
            case R.id.pop_200:
                pop200.setBackgroundResource(R.drawable.daren);
                break;
            case R.id.pop_500:
                pop500.setBackgroundResource(R.drawable.daren);
                break;
            case R.id.pop_1000:
                pop1000.setBackgroundResource(R.drawable.daren);
                break;
            case R.id.pop_3000:
                pop3000.setBackgroundResource(R.drawable.daren);
                Toast.makeText(TypeListActivity.this, "3000", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    private class TypeListener implements ShopTypeListRecycleAdapter.ItemClickListener {
        private final List<ShopTypeListBean.DataBean.ItemsBean> itemsBean;

        public TypeListener(List<ShopTypeListBean.DataBean.ItemsBean> itemsBeen) {
            this.itemsBean = itemsBeen;
        }

        @Override
        public void OnItemClick(View v, int position) {
            ShopTypeListBean.DataBean.ItemsBean itemsBean = this.itemsBean.get(position);
            Intent initent = new Intent(TypeListActivity.this, TypeDetialActivity.class);
            initent.putExtra("type_list_bean", itemsBean);
            initent.putExtra("from", "type_list_bean");
            startActivity(initent);
        }
    }

}

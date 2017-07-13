package com.example.administrator.liangcangdemo.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.cjj.MaterialRefreshLayout;
import com.example.administrator.liangcangdemo.R;
import com.example.administrator.liangcangdemo.adapter.DarenDetailRecycleAdapter;
import com.example.administrator.liangcangdemo.bean.DarenRecommendBean;
import com.example.administrator.liangcangdemo.untils.ConstantUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class DarenDetailActivity extends AppCompatActivity {

    @BindView(R.id.search_titlebar)
    ImageView searchTitlebar;
    @BindView(R.id.back_titlebar)
    ImageView backTitlebar;
    @BindView(R.id.tv_titlebar)
    TextView tvTitlebar;
    @BindView(R.id.shopcar_titlebar)
    ImageView shopcarTitlebar;
    @BindView(R.id.menu_titlebar)
    ImageView menuTitlebar;

    @BindView(R.id.recycle_daren_detail)
    RecyclerView recycleDarenDetail;
    @BindView(R.id.refresh_daren_detail)
    MaterialRefreshLayout refreshDarenDetail;
    private DarenDetailRecycleAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daren_detail);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }

    private void initView() {
    }

    private void initListener() {

    }

    private void initData() {
//        String from = getIntent().getStringExtra("from");
//        DarenBean.DataBean.ItemsBean itemsBean = (DarenBean.DataBean.ItemsBean) getIntent().getSerializableExtra(from);
//        getDataFromNet(itemsBean);
        String userId = getIntent().getStringExtra("daren_userId");
        String username = getIntent().getStringExtra("daren_userName");
        tvTitlebar.setText(username);
        getDataFromNet(userId);
    }

    private void getDataFromNet(String userId) {
        OkGo.getInstance().addCommonParams(new HttpParams("owner_id", userId))
                .get(ConstantUtils.DAREN_DETAIL_RECOMMEND)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("TAG", "DarenDetailActivity联网成功" + s);
                        progress(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("TAG", "DarenDetailActivity联网失败" + e.getMessage());
                    }
                });
    }

    private void progress(String s) {
        DarenRecommendBean darenRecommendBean = JSON.parseObject(s, DarenRecommendBean.class);
        DarenRecommendBean.DataBean.ItemsBean items = darenRecommendBean.getData().getItems();
        setData(items);
    }

    private void setData(DarenRecommendBean.DataBean.ItemsBean items) {
//        Glide.with(DarenDetailActivity.this).load(items.getUser_image().getOrig()).crossFade().into(ivHead);
//        tvUsernameDarenDetail.setText(items.getUser_name());
//        tvDutyDarenDetail.setText(items.getUser_desc());
//        rbLike.setText("喜欢\n" + items.getLike_count());
//        rbFensi.setText("粉丝\n" + items.getFollowed_count());
//        rbGuanzhu.setText("关注\n" + items.getFollowing_count());
//        rbRecommend.setText("推荐\n" + items.getRecommendation_count());
//        rbRecommend.setBackgroundResource(R.color.text_qianhui);
        adapter = new DarenDetailRecycleAdapter(DarenDetailActivity.this, items.getUser_id());//items.getUser_id()
        recycleDarenDetail.setAdapter(adapter);
        //第一次进来加载的页面和数据。

        recycleDarenDetail.setLayoutManager(new GridLayoutManager(DarenDetailActivity.this, 2, LinearLayoutManager.VERTICAL, false));
        adapter.addHeaderView(View.inflate(DarenDetailActivity.this, R.layout.daren_head_detail, null));
//        adapter.setOnItemClickListener(new DarenDetailListener());
        adapter.refresh(ConstantUtils.DAREN_DETAIL_RECOMMEND, DarenDetailRecycleAdapter.RECOMMEND);
    }
//    @OnClick({R.id.rb_like, R.id.rb_recommend, R.id.rb_guanzhu, R.id.rb_fensi})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.rb_like:
//                rbRecommend.setBackgroundResource(R.color.zhuti_huise);
//                rbFensi.setBackgroundResource(R.color.zhuti_huise);
//                rbGuanzhu.setBackgroundResource(R.color.zhuti_huise);
//                rbLike.setBackgroundResource(R.color.text_qianhui);
//                if (adapter.datas != null) {
//                    adapter.datas.clear();//清空數據
//                }
//                adapter.refresh(ConstantUtils.DAREN_DETAIL_LIKE, DarenDetailRecycleAdapter.LIKE);
//                recycleDarenDetail.setLayoutManager(new GridLayoutManager(DarenDetailActivity.this, 2, LinearLayoutManager.VERTICAL, false));
//                break;
//            case R.id.rb_recommend:
//                rbRecommend.setBackgroundResource(R.color.text_qianhui);
//                rbFensi.setBackgroundResource(R.color.zhuti_huise);
//                rbGuanzhu.setBackgroundResource(R.color.zhuti_huise);
//                rbLike.setBackgroundResource(R.color.zhuti_huise);
//                if (adapter.datas != null) {
//                    adapter.datas.clear();//清空數據
//                }
//                adapter.refresh(ConstantUtils.DAREN_DETAIL_RECOMMEND, DarenDetailRecycleAdapter.RECOMMEND);
//                recycleDarenDetail.setLayoutManager(new GridLayoutManager(DarenDetailActivity.this, 2, LinearLayoutManager.VERTICAL, false));
//                break;
//            case R.id.rb_guanzhu:
//                rbRecommend.setBackgroundResource(R.color.zhuti_huise);
//                rbFensi.setBackgroundResource(R.color.zhuti_huise);
//                rbGuanzhu.setBackgroundResource(R.color.text_qianhui);
//                rbLike.setBackgroundResource(R.color.zhuti_huise);
//                if (adapter.datas != null) {
//                    adapter.datas.clear();//清空數據
//                }
//                adapter.refresh(ConstantUtils.DAREN_DETAIL_GUANZHU, DarenDetailRecycleAdapter.GUANZHU);
//                recycleDarenDetail.setLayoutManager(new GridLayoutManager(DarenDetailActivity.this, 3, LinearLayoutManager.VERTICAL, false));
//                break;
//            case R.id.rb_fensi:
//                rbRecommend.setBackgroundResource(R.color.zhuti_huise);
//                rbFensi.setBackgroundResource(R.color.text_qianhui);
//                rbGuanzhu.setBackgroundResource(R.color.zhuti_huise);
//                rbLike.setBackgroundResource(R.color.zhuti_huise);
//                if (adapter.datas != null) {
//                    adapter.datas.clear();//清空數據
//                }
//                adapter.refresh(ConstantUtils.DAREN_DETAIL_FOLLOW, DarenDetailRecycleAdapter.FENSI);
//                recycleDarenDetail.setLayoutManager(new GridLayoutManager(DarenDetailActivity.this, 3, LinearLayoutManager.VERTICAL, false));
//                break;
//        }
//    }

//    private class DarenDetailListener implements DarenDetailRecycleAdapter.ItemClickListener {
//        @Override
//        public void OnItemClick(View v, int position, String nextUrl, String nextName) {
//            if (TextUtils.isEmpty(nextUrl)) {
//                Toast.makeText(DarenDetailActivity.this, "這不是咱家的哦", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            Intent initent = new Intent(DarenDetailActivity.this, DarenDetailActivity.class);
//            initent.putExtra("daren_userId", nextUrl);
//            initent.putExtra("daren_userName", nextName);
//            startActivity(initent);
//        }
//    }
}

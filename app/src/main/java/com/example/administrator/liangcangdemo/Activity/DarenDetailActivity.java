package com.example.administrator.liangcangdemo.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.cjj.MaterialRefreshLayout;
import com.example.administrator.liangcangdemo.R;
import com.example.administrator.liangcangdemo.adapter.DarenDetailRecycleAdapter;
import com.example.administrator.liangcangdemo.bean.DarenBean;
import com.example.administrator.liangcangdemo.bean.DarenRecommendBean;
import com.example.administrator.liangcangdemo.untils.ConstantUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    @BindView(R.id.rb_guanzhu)
    RadioButton rbGuanzhu;
    @BindView(R.id.rb_fensi)
    RadioButton rbFensi;
    @BindView(R.id.rg_daren_detail)
    RadioGroup rgDarenDetail;
    @BindView(R.id.recycle_daren_detail)
    RecyclerView recycleDarenDetail;
    @BindView(R.id.refresh_daren_detail)
    MaterialRefreshLayout refreshDarenDetail;
    @BindView(R.id.rb_recommend)
    RadioButton rbRecommend;
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

    private void initListener() {

    }

    private void initData() {
        String from = getIntent().getStringExtra("from");
        DarenBean.DataBean.ItemsBean itemsBean = (DarenBean.DataBean.ItemsBean) getIntent().getSerializableExtra(from);
        getDataFromNet(itemsBean);
    }

    private void getDataFromNet(DarenBean.DataBean.ItemsBean itemsBean) {
        OkGo.getInstance().addCommonParams(new HttpParams("owner_id", itemsBean.getUid()))
                .get(ConstantUtils.DAREN_DETAIL_RECOMMEND)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("TAG", "达人详情页联网成功" + s);
                        progress(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("TAG", "达人详情页联网失败" + e.getMessage());
                    }
                });
    }

    private void progress(String s) {
        DarenRecommendBean darenLikeBean = JSON.parseObject(s, DarenRecommendBean.class);
        DarenRecommendBean.DataBean.ItemsBean items = darenLikeBean.getData().getItems();
        setData(items);
    }

    private void setData(DarenRecommendBean.DataBean.ItemsBean items) {
        Glide.with(DarenDetailActivity.this).load(items.getUser_image().getOrig()).crossFade().into(ivHead);
        tvUsernameDarenDetail.setText(items.getUser_name());
        tvDutyDarenDetail.setText(items.getUser_desc());
        rbLike.setText("喜欢\n" + items.getLike_count());
        rbFensi.setText("粉丝\n" + items.getFollowed_count());
        rbGuanzhu.setText("关注\n" + items.getFollowing_count());
        rbRecommend.setText("推荐\n" + items.getRecommendation_count());
        adapter = new DarenDetailRecycleAdapter(DarenDetailActivity.this, items.getUser_id());
        recycleDarenDetail.setAdapter(adapter);

        adapter.refresh(ConstantUtils.DAREN_DETAIL_RECOMMEND, DarenDetailRecycleAdapter.RECOMMEND);
        recycleDarenDetail.setLayoutManager(new GridLayoutManager(DarenDetailActivity.this, 2, LinearLayoutManager.VERTICAL, false));
    }

    private void initView() {

    }

    @OnClick({R.id.rb_like, R.id.rb_recommend, R.id.rb_guanzhu, R.id.rb_fensi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_like:
                adapter.datas.clear();//清空數據
                adapter.refresh(ConstantUtils.DAREN_DETAIL_LIKE, DarenDetailRecycleAdapter.LIKE);
                recycleDarenDetail.setLayoutManager(new GridLayoutManager(DarenDetailActivity.this, 2, LinearLayoutManager.VERTICAL, false));
                break;
            case R.id.rb_recommend:
                adapter.datas.clear();//清空數據
                adapter.refresh(ConstantUtils.DAREN_DETAIL_RECOMMEND, DarenDetailRecycleAdapter.RECOMMEND);
                recycleDarenDetail.setLayoutManager(new GridLayoutManager(DarenDetailActivity.this, 2, LinearLayoutManager.VERTICAL, false));
                break;
            case R.id.rb_guanzhu:
                adapter.datas.clear();//清空數據
                adapter.refresh(ConstantUtils.DAREN_DETAIL_GUANZHU, DarenDetailRecycleAdapter.GUANZHU);
                recycleDarenDetail.setLayoutManager(new GridLayoutManager(DarenDetailActivity.this, 3, LinearLayoutManager.VERTICAL, false));
                break;
            case R.id.rb_fensi:
                adapter.datas.clear();//清空數據
                adapter.refresh(ConstantUtils.DAREN_DETAIL_FOLLOW, DarenDetailRecycleAdapter.FENSI);
                recycleDarenDetail.setLayoutManager(new GridLayoutManager(DarenDetailActivity.this, 3, LinearLayoutManager.VERTICAL, false));
                break;
        }
    }
}

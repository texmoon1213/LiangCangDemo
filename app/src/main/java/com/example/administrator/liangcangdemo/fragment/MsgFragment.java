package com.example.administrator.liangcangdemo.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.cjj.MaterialRefreshLayout;
import com.example.administrator.liangcangdemo.R;
import com.example.administrator.liangcangdemo.adapter.MsgRecycleAdapter;
import com.example.administrator.liangcangdemo.base.BaseFragment;
import com.example.administrator.liangcangdemo.bean.MsgBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/7/6.
 */

public class MsgFragment extends BaseFragment {
    @BindView(R.id.search_titlebar)
    ImageView searchTitlebar;
    @BindView(R.id.back_titlebar)
    ImageView backTitlebar;
    @BindView(R.id.tv_titlebar)
    TextView tvTitlebar;
    @BindView(R.id.shopcar_titlebar)
    ImageView shopcarTitlebar;
    @BindView(R.id.recycle_msg)
    RecyclerView recycleMsg;
    @BindView(R.id.refresh_msg)
    MaterialRefreshLayout refreshMsg;
    Unbinder unbinder;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_msg, null);
        unbinder = ButterKnife.bind(this, view);
        tvTitlebar.setText("杂志");
        backTitlebar.setVisibility(View.GONE);
        searchTitlebar.setVisibility(View.GONE);
        shopcarTitlebar.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        getDataFromNet();
    }

    private void getDataFromNet() {
        String url = "http://mobile.iliangcang.com/topic/magazineList?app_key=Android&author_id=1&sig=2FA0974FFF1BC3DFA562AA63C8B5A84F%7C118265010131868&v=1.0";
        OkGo.get(url)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("TAG", "杂志成功" + s);
                        process(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("TAG", "杂志失败" + e.getMessage());
                    }
                });
    }

    private void process(String s) {
        MsgBean msgBean = JSON.parseObject(s, MsgBean.class);
        List<MsgBean.DataBean.ItemsBean.InfosBean> infos = msgBean.getData().getItems().get(0).getInfos();
        Log.e("TAG", "infos.len==" + infos.size());
        MsgRecycleAdapter adapter = new MsgRecycleAdapter(context, infos);
        recycleMsg.setAdapter(adapter);
        recycleMsg.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

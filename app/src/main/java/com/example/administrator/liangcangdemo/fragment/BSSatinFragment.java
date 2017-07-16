package com.example.administrator.liangcangdemo.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.cjj.MaterialRefreshLayout;
import com.example.administrator.liangcangdemo.R;
import com.example.administrator.liangcangdemo.adapter.BSSatinAdapter;
import com.example.administrator.liangcangdemo.base.BaseFragment;
import com.example.administrator.liangcangdemo.bean.BSSatinBean;
import com.example.administrator.liangcangdemo.untils.ConstantUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/7/14.
 */

public class BSSatinFragment extends BaseFragment {
    @BindView(R.id.recycle_bs_satin)
    RecyclerView recycleBsSatin;
    @BindView(R.id.refresh_bs_satin)
    MaterialRefreshLayout refreshBsSatin;
    Unbinder unbinder;

    @Override
    public View initView() {
        View inflate = View.inflate(context, R.layout.bs_satin, null);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void initData() {
        super.initData();
        getDataFromNet();
    }

    private void getDataFromNet() {
        //        .cacheKey("home1").cacheMode(CacheMode.DEFAULT)缓存加不上
        OkGo.<String>get(ConstantUtils.BS_SATIN).tag(this).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                Log.e("TAG", "成功==" + s);
                processData(s);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                Log.e("TAG", "失败==" + e.getMessage());
            }
        });
    }

    private void processData(String s) {
        BSSatinBean bsSatinBean = JSON.parseObject(s, BSSatinBean.class);

        List<BSSatinBean.ListBean> listBeen = bsSatinBean.getList();
        if (listBeen != null && listBeen.size() > 0) {
            Log.e("TAG", "listBeen.size()==" + listBeen.size());
            BSSatinAdapter adapter = new BSSatinAdapter(context, listBeen);
            recycleBsSatin.setAdapter(adapter);
            recycleBsSatin.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
//            adapter.setOnItemClickListener(new HomeListener(listBeen));
        } else {
            Toast.makeText(context, "联网失败", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

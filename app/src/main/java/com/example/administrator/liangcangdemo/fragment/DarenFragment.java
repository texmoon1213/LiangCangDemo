package com.example.administrator.liangcangdemo.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.cjj.MaterialRefreshLayout;
import com.example.administrator.liangcangdemo.Activity.DarenDetailActivity;
import com.example.administrator.liangcangdemo.MainActivity;
import com.example.administrator.liangcangdemo.R;
import com.example.administrator.liangcangdemo.adapter.DarenRecycleAdapter;
import com.example.administrator.liangcangdemo.base.BaseFragment;
import com.example.administrator.liangcangdemo.bean.DarenBean;
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
 * Created by Administrator on 2017/7/6.
 */

public class DarenFragment extends BaseFragment {

    @BindView(R.id.recycle_daren)
    RecyclerView recycleDaren;
    @BindView(R.id.refresh_daren)
    MaterialRefreshLayout refreshDaren;
    Unbinder unbinder;
//    @BindView(R.id.menu_titlebar)
//    ImageView menuTitlebar;

    @Override
    public View initView() {
        View inflate = View.inflate(context, R.layout.fragment_daren, null);
        unbinder = ButterKnife.bind(this, inflate);
        MainActivity activity = (MainActivity) getActivity();
        activity.getTvTitlebar().setText("达人");
        activity.getBackTitlebar().setVisibility(View.GONE);
        activity.getSearchTitlebar().setVisibility(View.VISIBLE);
        activity.getMenuTitlebar().setVisibility(View.VISIBLE);
        activity.getShopcarTitlebar().setVisibility(View.GONE);
        return inflate;
    }

    //如果添加完成然后再两个Fragment交替show 和 hide ,那么只会调用 onHiddenChanged, 其中返回其是否显示的状态
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        MainActivity activity = (MainActivity) getActivity();
        activity.getTvTitlebar().setText("达人");
        activity.getBackTitlebar().setVisibility(View.GONE);
        activity.getSearchTitlebar().setVisibility(View.VISIBLE);
        activity.getMenuTitlebar().setVisibility(View.VISIBLE);
        activity.getShopcarTitlebar().setVisibility(View.GONE);
    }

    @Override
    public void initData() {
        super.initData();
        getDataFromData(ConstantUtils.DAREN_HOME);
    }

    private void getDataFromData(String url) {
        OkGo.get(url)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("TAG", "达人联网成功" + s);
                        process(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("TAG", "达人联网失败" + e.getMessage());
                    }
                });
    }

    private void process(String s) {
        DarenBean darenBean = JSON.parseObject(s, DarenBean.class);
        List<DarenBean.DataBean.ItemsBean> items = darenBean.getData().getItems();
        DarenRecycleAdapter adapter = new DarenRecycleAdapter(context, items);
        recycleDaren.setAdapter(adapter);
        recycleDaren.setLayoutManager(new GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false));
//        recycleDaren.addItemDecoration(new DividerGridItemDecorationHY(context));
//        recycleDaren.addItemDecoration(new RecyclerViewDivider(context, LinearLayout.HORIZONTAL, R.drawable.divider_bg));
        adapter.setOnItemClickListener(new DarenListener(items));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private class DarenListener implements DarenRecycleAdapter.ItemClickListener {
        private List<DarenBean.DataBean.ItemsBean> datas;

        public DarenListener(List<DarenBean.DataBean.ItemsBean> items) {
            this.datas = items;
        }

        @Override
        public void OnItemClick(View v, int position) {
            DarenBean.DataBean.ItemsBean itemsBean = datas.get(position);
            Intent initent = new Intent(getContext(), DarenDetailActivity.class);

//            initent.putExtra("daren_bean", itemsBean);
//            initent.putExtra("from", "daren_bean");
            String uid = itemsBean.getUid();
            String username = itemsBean.getUsername();
            initent.putExtra("daren_userId", uid);
            initent.putExtra("daren_userName", username);
            startActivity(initent);
        }
    }
}

package com.example.administrator.liangcangdemo.fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.administrator.liangcangdemo.Activity.DarenDetailActivity;
import com.example.administrator.liangcangdemo.MainActivity;
import com.example.administrator.liangcangdemo.R;
import com.example.administrator.liangcangdemo.adapter.DarenRecycleAdapter;
import com.example.administrator.liangcangdemo.base.BaseFragment;
import com.example.administrator.liangcangdemo.bean.DarenBean;
import com.example.administrator.liangcangdemo.untils.ConstantUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/7/6.
 */

public class DarenFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.recycle_daren)
    RecyclerView recycleDaren;
    @BindView(R.id.refresh_daren)
    MaterialRefreshLayout refreshDaren;
    Unbinder unbinder;
    private PopupWindow popupWindow;
    private TextView defaultr;
    private TextView most;
    private TextView welcome;
    private TextView fresh;
    private TextView freshJoin;
    private ImageView menuTitlebar;
    private String popUrl;
    private static final int SHANG = 0;
    private static final int XIA = 1;
    private int count = -1;
    private List<DarenBean.DataBean.ItemsBean> datas;
    private DarenRecycleAdapter adapter;

    @Override
    public View initView() {
        View inflate = View.inflate(context, R.layout.fragment_daren, null);
        unbinder = ButterKnife.bind(this, inflate);
        initTitlebar();
        initPopupWindow();
        return inflate;
    }

    private void initPopupWindow() {
        popupWindow = new PopupWindow(context);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View inflate = LayoutInflater.from(context).inflate(R.layout.popuwindow_type_item, null);
        popupWindow.setContentView(inflate);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        //设置item
        defaultr = (TextView) inflate.findViewById(R.id.pop_all);
        most = (TextView) inflate.findViewById(R.id.pop_200);
        welcome = (TextView) inflate.findViewById(R.id.pop_500);
        fresh = (TextView) inflate.findViewById(R.id.pop_1000);
        freshJoin = (TextView) inflate.findViewById(R.id.pop_3000);
        ColorDrawable dw = new ColorDrawable(0xFFFFFFFF);
        popupWindow.setBackgroundDrawable(dw);
        defaultr.setText("默认推荐");
        most.setText("最多推荐");
        welcome.setText("最多欢迎");
        fresh.setText("最新推荐");
        freshJoin.setText("最新加入");
        defaultr.setBackgroundResource(R.drawable.popwindow_bg);
        most.setBackgroundResource(R.drawable.popwindow_bg);
        welcome.setBackgroundResource(R.drawable.popwindow_bg);
        fresh.setBackgroundResource(R.drawable.popwindow_bg);
        freshJoin.setBackgroundResource(R.drawable.popwindow_bg);

        defaultr.setOnClickListener(this);
        most.setOnClickListener(this);
        welcome.setOnClickListener(this);
        freshJoin.setOnClickListener(this);
        fresh.setOnClickListener(this);
    }

    private void initTitlebar() {
        MainActivity activity = (MainActivity) getActivity();
        activity.getTvTitlebar().setText("达人");
        activity.getBackTitlebar().setVisibility(View.GONE);
        activity.getSearchTitlebar().setVisibility(View.VISIBLE);
        activity.getShopcarTitlebar().setVisibility(View.GONE);
        menuTitlebar = activity.getMenuTitlebar();
        menuTitlebar.setVisibility(View.VISIBLE);
        menuTitlebar.setOnClickListener(new MenuListener());
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
        getDataFromNet(ConstantUtils.DAREN_HOME, XIA);
        initListener();
    }

    private void initListener() {

    }

    private void getDataFromNet(final String url, final int shangOrXia) {
        if (shangOrXia == XIA) {
            count = 1;
        } else if (shangOrXia == SHANG) {
            count = count + 1;
        }
        OkGo.getInstance().addCommonParams(new HttpParams("page", count + ""))
                .get(url)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("TAG", "达人联网成功" + s);
                        process(s, shangOrXia, url);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("TAG", "达人联网失败" + e.getMessage());
                    }
                });
    }

    /***
     *
     * @param s  json串的数据
     * @param shangOrXia    判断上下刷新
     * @param url   记录跳转的接口，以便跳转之后 上拉下啦数据正常
     */
    private void process(String s, int shangOrXia, String url) {
        DarenBean darenBean = JSON.parseObject(s, DarenBean.class);
        List<DarenBean.DataBean.ItemsBean> items = darenBean.getData().getItems();
        if (shangOrXia == XIA) {
            datas = items;
        } else if (shangOrXia == SHANG) {
            datas.addAll(items);
        }
        if (count == 1) {
            adapter = new DarenRecycleAdapter(context, datas);
            recycleDaren.setAdapter(adapter);
            recycleDaren.setLayoutManager(new GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false));
            adapter.setOnItemClickListener(new DarenListener(datas));
        } else {
            adapter.notifyDataSetChanged();
        }
        initReFresh(darenBean, url);
        refreshDaren.finishRefresh();
        refreshDaren.finishRefreshLoadMore();
    }

    private void initReFresh(DarenBean darenBean, final String url) {
        refreshDaren.setLoadMore(darenBean.getData().isHas_more());
        refreshDaren.setMaterialRefreshListener(new MaterialRefreshListener() {

            //下拉刷新
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
//                isLoadMore = false;
                getDataFromNet(url, XIA);
            }

            //加载更多-上拉刷新
            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
//                isLoadMore = true;
                getDataFromNet(url, SHANG);
            }

            @Override
            public void onfinish() {
                super.onfinish();
                Toast.makeText(context, "加载完成", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pop_all:
                //默认推荐
                popUrl = "http://mobile.iliangcang.com/user/masterList?app_key=Android&count=18&page=1&sig=BF287AF953103F390674E73DDA18CFD8|639843030233268&v=1.0";
                break;
            case R.id.pop_200:
                //最多
                popUrl = "http://mobile.iliangcang.com/user/masterList?app_key=Android&count=18&orderby=goods_sum&page=1&sig=05D2057FE3D726A43A94505807516FC3%7C136072130089168&v=1.0";
                break;
            case R.id.pop_500:
                //最受欢迎
                popUrl = "http://mobile.iliangcang.com/user/masterList?app_key=Android&count=18&orderby=followers&page=9&sig=05D2057FE3D726A43A94505807516FC3|136072130089168&v=1.0";
                break;
            case R.id.pop_1000:
                //最新推荐
                popUrl = "http://mobile.iliangcang.com/user/masterList?app_key=Android&count=18&orderby=reg_time&page=9&sig=05D2057FE3D726A43A94505807516FC3|136072130089168&v=1.0";
                break;
            case R.id.pop_3000:
                //最新加入
                popUrl = "http://mobile.iliangcang.com/user/masterList?app_key=Android&count=18&orderby=action_time&page=9&sig=05D2057FE3D726A43A94505807516FC3|136072130089168&v=1.0";
                break;
        }
        getDataFromNet(popUrl, XIA);
        popupWindow.dismiss();
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

    private class MenuListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            showPopupWindow();
        }
    }

    private void showPopupWindow() {
        popupWindow.showAsDropDown(menuTitlebar);
    }
}

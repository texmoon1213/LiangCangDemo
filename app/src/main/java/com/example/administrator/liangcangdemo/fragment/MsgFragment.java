package com.example.administrator.liangcangdemo.fragment;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.cjj.MaterialRefreshLayout;
import com.example.administrator.liangcangdemo.MainActivity;
import com.example.administrator.liangcangdemo.R;
import com.example.administrator.liangcangdemo.adapter.MsgRecycleAdapter;
import com.example.administrator.liangcangdemo.base.BaseFragment;
import com.example.administrator.liangcangdemo.bean.MSGBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/7/6.
 */

public class MsgFragment extends BaseFragment {
    @BindView(R.id.recycle_msg)
    RecyclerView recycleMsg;
    @BindView(R.id.refresh_msg)
    MaterialRefreshLayout refreshMsg;
    Unbinder unbinder;
    private ArrayList<MSGBean> msgBeans;
    private MainActivity activity;
    private TextSwitcher textswitcher;
    private int prePosition = -1;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_msg, null);
        unbinder = ButterKnife.bind(this, view);
        activity = (MainActivity) getActivity();
        textswitcher = activity.getTextswitcher();

        activity.getTvTitlebar().setText("杂志");
        activity.getBackTitlebar().setVisibility(View.GONE);
        activity.getSearchTitlebar().setVisibility(View.GONE);
        textswitcher.setVisibility(View.VISIBLE);
        textswitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(context);
                textView.setTextColor(Color.BLUE);
                textView.setText("测试");
                return textView;
            }
        });
        textswitcher.setText("今天");
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        activity.getTvTitlebar().setText("杂志");
        activity.getBackTitlebar().setVisibility(View.GONE);
        activity.getSearchTitlebar().setVisibility(View.GONE);
        activity.getShopcarTitlebar().setVisibility(View.GONE);
        activity.getMenuTitlebar().setVisibility(View.GONE);
        textswitcher.setVisibility(View.VISIBLE);
        textswitcher.setText("今天");
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
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONObject datas = jsonObject.optJSONObject("data");
            JSONObject items = datas.optJSONObject("items");
            JSONArray keys = items.optJSONArray("keys");
            JSONObject infos = items.optJSONObject("infos");
            msgBeans = new ArrayList<>();

            for (int i = 0; i < keys.length(); i++) {
                JSONArray monthInfos = infos.getJSONArray(keys.get(i) + "");

                for (int j = 0; j < monthInfos.length(); j++) {
                    MSGBean msgBean = new MSGBean();
                    JSONObject objMGZ = monthInfos.getJSONObject(j);
                    msgBean.setAuthor_id(objMGZ.optString("monthInfos"));
                    msgBean.setTopic_name(objMGZ.optString("topic_name"));
                    msgBean.setTopic_url(objMGZ.optString("topic_url"));
                    msgBean.setCat_name(objMGZ.optString("cat_name"));
                    msgBean.setCover_img(objMGZ.optString("cover_img"));
                    msgBean.setMonthInfo(keys.get(i) + "");
                    msgBeans.add(msgBean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MsgRecycleAdapter adapter = new MsgRecycleAdapter(context, msgBeans);
        //监听recycleview状态改变，从而改变textSwicher

        recycleMsg.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager manager = recycleMsg.getLayoutManager();
                if (manager instanceof LinearLayoutManager) {
                    LinearLayoutManager mLinearManager = (LinearLayoutManager) manager;
                    //获取最后一个可见view的位置
                    int lastItemPosition = mLinearManager.findLastVisibleItemPosition();
                    //获取第一个可见view的位置
                    int firstItemPosition = mLinearManager.findFirstVisibleItemPosition();
                    if (firstItemPosition == 0 && firstItemPosition != prePosition && prePosition != -1) {
                        textswitcher.setText("今天");
                    } else if (firstItemPosition != prePosition) {
                        textswitcher.setText(msgBeans.get(firstItemPosition).getMonthInfo());
                    }
                    prePosition = firstItemPosition;
                }
            }
        });
        recycleMsg.setAdapter(adapter);
        recycleMsg.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

package com.example.administrator.liangcangdemo.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

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

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_msg, null);
        unbinder = ButterKnife.bind(this, view);
        MainActivity activity = (MainActivity) getActivity();
        activity.getTvTitlebar().setText("杂志");
        activity.getBackTitlebar().setVisibility(View.GONE);
        activity.getSearchTitlebar().setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        MainActivity activity = (MainActivity) getActivity();
        activity.getTvTitlebar().setText("杂志");
        activity.getBackTitlebar().setVisibility(View.GONE);
        activity.getSearchTitlebar().setVisibility(View.GONE);
        activity.getShopcarTitlebar().setVisibility(View.GONE);
        activity.getMenuTitlebar().setVisibility(View.GONE);
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
//        MSGBean msgBean = JSON.parseObject(s, MSGBean.class);
//
//        String monthInfo = msgBean.getMonthInfo();
//
//        int size = msgBean.getData().getItems().getKeys().size();
//        List<MSGBean.DataBean.ItemsBean.InfosBean> infos = new ArrayList<>();
////        for (int i = 0; i < size; i++) {
////            infos.add(msgBean.getData().getItems().getInfos().get(i));
////        }
//        Log.e("TAG", "infos.len==" + infos.size());
        MsgRecycleAdapter adapter = new MsgRecycleAdapter(context, msgBeans);
        Log.e("TAG", "msgBeans==" + msgBeans.size());
        recycleMsg.setAdapter(adapter);
        recycleMsg.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

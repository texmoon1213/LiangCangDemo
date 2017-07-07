package com.example.administrator.liangcangdemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.liangcangdemo.fragment.DarenFragment;
import com.example.administrator.liangcangdemo.fragment.MsgFragment;
import com.example.administrator.liangcangdemo.fragment.ShareFragment;
import com.example.administrator.liangcangdemo.fragment.ShopFragment;
import com.example.administrator.liangcangdemo.fragment.UserFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


//    @BindView(R.id.search_main_top)
//    ImageView searchMainTop;
//    @BindView(R.id.shopcar_main_top)
//    ImageView shopcarMainTop;

    @BindView(R.id.fl_main)
    FrameLayout flMain;
    @BindView(R.id.rg_main)
    RadioGroup rgMain;


    @BindView(R.id.search_titlebar)
    ImageView searchTitlebar;
    @BindView(R.id.back_titlebar)
    ImageView backTitlebar;
    @BindView(R.id.tv_titlebar)
    TextView tvTitlebar;
    @BindView(R.id.shopcar_titlebar)
    ImageView shopcarTitlebar;


    @BindView(R.id.rb1_main)
    RadioButton rb1Main;
    @BindView(R.id.rb2_main)
    RadioButton rb2Main;
    @BindView(R.id.rb3_main)
    RadioButton rb3Main;

    public TextView getTvTitlebar() {
        return tvTitlebar;
    }

    @BindView(R.id.rb4_main)

    RadioButton rb4Main;
    @BindView(R.id.rb5_main)
    RadioButton rb5Main;
    private ArrayList<Fragment> fragments;
    private int position = 0;
    private Fragment tempFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFragment();
        //设置RadioGroup的选中监听
        rgMain.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //设置默认选择首页
        rgMain.check(R.id.rb1_main);
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new ShopFragment());
        fragments.add(new MsgFragment());
        fragments.add(new DarenFragment());
        fragments.add(new ShareFragment());
        fragments.add(new UserFragment());
//        //一进入要显示的Fragment
//        switchFragment(fragments.get(position));
    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb1_main:
                    position = 0;
                    break;
                case R.id.rb2_main:
                    position = 1;
                    break;
                case R.id.rb3_main:
                    position = 2;
                    break;
                case R.id.rb4_main:
                    position = 3;
                    break;
                case R.id.rb5_main:
                    position = 4;
                    break;

            }
            Fragment currentFragment = fragments.get(position);
            switchFragment(currentFragment);
        }
    }

    private void switchFragment(Fragment currentFragment) {
        if (currentFragment != tempFragment) {//不是同一个
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            if (!currentFragment.isAdded()) {

                //把之前的隐藏
                if (tempFragment != null) {
                    ft.hide(tempFragment);
                }
                //把现在的添加
                ft.add(R.id.fl_main, currentFragment);

            } else {
                //把之前的隐藏
                if (tempFragment != null) {
                    ft.hide(tempFragment);
                }
                //把当前的显示
                ft.show(currentFragment);
            }
            //提交
            ft.commit();

            tempFragment = currentFragment;

        }

    }

    private boolean isExit = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (position != 0) {
                rgMain.check(R.id.rb1_main);
                return true;
            } else if (!isExit) {
                isExit = true;
                Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isExit = false;
                    }
                }, 2000);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}

package com.hzxmkuar.wumeihui.personal.integral;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.base.util.AppUtil;
import com.hzxmkuar.wumeihui.message.ChatActivity;
import com.hzxmkuar.wumeihui.personal.integral.fragment.MyIntegralFragment;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzxmkuar.com.applibrary.impl.PermissionListener;

/**
 * Created by Administrator on 2018/9/3.
 */

public class MyIntegralActivity extends BaseActivity {
    @BindView(R.id.all)
    TextView all;
    @BindView(R.id.waite_send)
    TextView waiteSend;
    @BindView(R.id.already_send)
    TextView alreadySend;
    @BindView(R.id.finish_send)
    TextView finishSend;
    @BindView(R.id.move_line)
    AutoRelativeLayout moveLine;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private List<MyIntegralFragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_integral);
        ButterKnife.bind(this);
        setTitleName(Constant.MY_INTEGRAL);
        initFragment();
    }


    private void initFragment() {
        fragmentList.add(new MyIntegralFragment(0));
        fragmentList.add(new MyIntegralFragment(1));
        fragmentList.add(new MyIntegralFragment(2));
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                moveLine.setX(getScreenWidth() / 3 * position + positionOffsetPixels / 3);
            }

            @Override
            public void onPageSelected(int position) {
                all.setTextColor(position == 0 ? Color.parseColor("#3bafd9") : Color.parseColor("#bbbbbb"));
                waiteSend.setTextColor(position == 1 ? Color.parseColor("#3bafd9") : Color.parseColor("#bbbbbb"));
                alreadySend.setTextColor(position == 2 ? Color.parseColor("#3bafd9") : Color.parseColor("#bbbbbb"));
                finishSend.setTextColor(position == 3 ? Color.parseColor("#3bafd9") : Color.parseColor("#bbbbbb"));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @OnClick({R.id.all, R.id.waite_send, R.id.already_send, R.id.finish_send, R.id.telephone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.all:
                viewPager.setCurrentItem(0);
                break;
            case R.id.waite_send:
                viewPager.setCurrentItem(1);
                break;
            case R.id.already_send:
                viewPager.setCurrentItem(2);
                break;
            case R.id.finish_send:
                viewPager.setCurrentItem(3);
                break;
            case R.id.telephone:

                Intent intent = new Intent(appContext, ChatActivity.class);
                intent.putExtra("UserId", getIntent().getStringExtra("Telephone"));
                intent.putExtra("Name","在线客服");
                startActivity(intent);
                goToAnimation(1);

                break;
        }
    }

    FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }
    };
}

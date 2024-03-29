package com.hzxmkuar.wumeihui.business.myself;

import android.graphics.Color;
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

import com.hzxmkuar.wumeihui.business.myself.fragment.MyQuoteFragment;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/9/3.
 **/

public class MyQuoteActivity extends BaseActivity {
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
    private List<MyQuoteFragment>fragmentList=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_quote);
        ButterKnife.bind(this);
        setTitleName("我的报价单");
        initFragment();
    }

    private void initFragment() {
        fragmentList.add(new MyQuoteFragment(0));
        fragmentList.add(new MyQuoteFragment(1));
        fragmentList.add(new MyQuoteFragment(2));
        fragmentList.add(new MyQuoteFragment(3));
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                moveLine.setX(getScreenWidth()/4*position+positionOffsetPixels/4);
            }

            @Override
            public void onPageSelected(int position) {
            all.setTextColor(position==0?Color.parseColor("#3bafd9"):Color.parseColor("#bbbbbb"));
            waiteSend.setTextColor(position==1?Color.parseColor("#3bafd9"):Color.parseColor("#bbbbbb"));
            alreadySend.setTextColor(position==2?Color.parseColor("#3bafd9"):Color.parseColor("#bbbbbb"));
            finishSend.setTextColor(position==3?Color.parseColor("#3bafd9"):Color.parseColor("#bbbbbb"));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(getIntent().getIntExtra("Index",0));

    }

    @OnClick({R.id.all, R.id.waite_send, R.id.already_send, R.id.finish_send})
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
        }
    }
    FragmentPagerAdapter adapter= new  FragmentPagerAdapter(getSupportFragmentManager()){

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

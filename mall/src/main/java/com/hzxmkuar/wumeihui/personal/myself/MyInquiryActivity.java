package com.hzxmkuar.wumeihui.personal.myself;

import android.content.Intent;
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

import com.hzxmkuar.wumeihui.personal.MainActivity;
import com.hzxmkuar.wumeihui.personal.myself.fragment.MyInquiryFragment;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/9/3.
 */

public class MyInquiryActivity extends BaseActivity {
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
    private List<MyInquiryFragment>fragmentList=new ArrayList<>();
    private List<TextView>timeViewList=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_inquiry);
        ButterKnife.bind(this);
        setTitleName(Constant.MY_INQUIRY);
        initFragment();
    }

    private void initFragment() {
        fragmentList.add(new MyInquiryFragment(0));
        fragmentList.add(new MyInquiryFragment(1));
        fragmentList.add(new MyInquiryFragment(2));
        fragmentList.add(new MyInquiryFragment(3));
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

    @OnClick({R.id.all, R.id.waite_send, R.id.already_send, R.id.finish_send,R.id.message})
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
            case R.id.message:
                Intent intent=new Intent(appContext, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Index",2);
                startActivity(intent);
                goToAnimation(1);
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

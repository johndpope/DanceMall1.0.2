package com.hzxmkuar.wumeihui.personal.myself;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.personal.myself.fragment.MyCouponFragment;
import com.hzxmkuar.wumeihui.personal.order.presenter.MyCouponPresenter;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzxmkuar.com.applibrary.domain.order.MyCouponTo;

/**
 * Created by Administrator on 2018/9/1.
 */

public class MyCouponActivity extends BaseActivity {

    @BindView(R.id.un_use)
    TextView unUse;
    @BindView(R.id.already_use)
    TextView alreadyUse;
    @BindView(R.id.out_date)
    TextView outDate;
    @BindView(R.id.move_line)
    AutoRelativeLayout moveLine;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private List<MyCouponFragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_coupon);
        ButterKnife.bind(this);
        setTitleName(Constant.COUPON);

        MyCouponPresenter presenter=new MyCouponPresenter(this);
    }

    private void initFragment(MyCouponTo couponTo) {
        fragmentList.add(new MyCouponFragment(0,couponTo.getUnuse_list()));
        fragmentList.add(new MyCouponFragment(1,couponTo.getUsed_list()));
        fragmentList.add(new MyCouponFragment(2,couponTo.getExpired_list()));
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                moveLine.setX(250 * getScreenWidth() / 750*position + positionOffsetPixels / 3);
            }

            @Override
            public void onPageSelected(int position) {
                unUse.setTextColor(position == 0 ? Color.parseColor("#000000") : Color.parseColor("#999999"));
                alreadyUse.setTextColor(position == 1 ? Color.parseColor("#000000") : Color.parseColor("#999999"));
                outDate.setTextColor(position == 2 ? Color.parseColor("#000000") : Color.parseColor("#999999"));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    PagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }
    };


    @OnClick({R.id.un_use, R.id.already_use, R.id.out_date,R.id.receiver_coupon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.un_use:
                viewPager.setCurrentItem(0);
                break;
            case R.id.already_use:
                viewPager.setCurrentItem(1);
                break;
            case R.id.out_date:
                viewPager.setCurrentItem(2);
                break;
            case R.id.receiver_coupon:
                Intent intent=new Intent(appContext,ReceiverCouponActivity.class);
                startActivity(intent);
                goToAnimation(1);
                break;
        }
    }

    @Override
    public void loadDataSuccess(Object data) {
        super.loadDataSuccess(data);
        MyCouponTo couponTo= (MyCouponTo) data;
        initFragment(couponTo);
    }
}

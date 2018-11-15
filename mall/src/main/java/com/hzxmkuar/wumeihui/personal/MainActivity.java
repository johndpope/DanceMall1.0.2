package com.hzxmkuar.wumeihui.personal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.ActivityManager;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.NoSlideViewPager;
import com.hzxmkuar.wumeihui.base.util.SpUtil;
import com.hzxmkuar.wumeihui.circle.fragment.CircleFragment;
import com.hzxmkuar.wumeihui.message.MessageFragment;
import com.hzxmkuar.wumeihui.personal.inquiry.SelectDemandActivity;
import com.hzxmkuar.wumeihui.personal.main.fragment.MyselfFragment;
import com.hzxmkuar.wumeihui.personal.main.fragment.ResourceFragment;
import com.hzxmkuar.wumeihui.personal.main.presenter.MainPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @BindView(R.id.resource_text)
    TextView resourceText;
    @BindView(R.id.resource_view)
    View resourceView;
    @BindView(R.id.communication_text)
    TextView communicationText;
    @BindView(R.id.communication_view)
    View communicationView;
    @BindView(R.id.message_text)
    TextView messageText;
    @BindView(R.id.message_view)
    View messageView;
    @BindView(R.id.myself_text)
    TextView myselfText;
    @BindView(R.id.myself_view)
    View myselfView;
    @BindView(R.id.fragment_layout)
    public NoSlideViewPager fragmentLayout;
    private ResourceFragment resourceFragment;
    private List<Fragment> fragmentList = new ArrayList<>();
    private MyselfFragment myselfFragment=new MyselfFragment();
    private MainPresenter presenter;
    private Handler handler = new Handler();
    private boolean canOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ActivityManager.mainActivity = this;

        presenter = new MainPresenter(this);

        SpUtil.put("IsMerchant", false);

    }

    @Override
    public void loadDataSuccess(Object data) {
        userInfoTo=userInfoHelp.getUserInfo();
        initFragment();
        SpUtil.put("ChatName", userInfoTo.getUsername());
        SpUtil.put("ChatPic", userInfoTo.getFace_url());

    }

    private void initFragment() {
        resourceFragment = new ResourceFragment();
        fragmentList.add(resourceFragment);
        fragmentList.add(new CircleFragment());
        fragmentList.add( new MessageFragment(this));
        fragmentList.add(myselfFragment);

        fragmentLayout.setAdapter(pagerAdapter);
        fragmentLayout.setOffscreenPageLimit(4);

        fragmentLayout.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position,float positionOffset,int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                resourceView.setBackgroundResource(fragmentLayout.getCurrentItem()==0?R.drawable.main_resource_select_icon:R.drawable.main_resource_un_select_icon);
                communicationView.setBackgroundResource(fragmentLayout.getCurrentItem()==1?R.drawable.main_communication_select:R.drawable.main_communication_un_select);
                messageView.setBackgroundResource(fragmentLayout.getCurrentItem()==2?R.drawable.main_message_select:R.drawable.service_message_ico);
                myselfView.setBackgroundResource(fragmentLayout.getCurrentItem()==3?R.drawable.main_myself_select:R.drawable.main_myself_un_select);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        fragmentLayout.setCurrentItem(getIntent().getIntExtra("Index",0));

    }

    @OnClick({R.id.add_demand, R.id.resource_layout, R.id.communication_layout, R.id.message_layout, R.id.myself_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_demand:
                Intent intent = new Intent(appContext, SelectDemandActivity.class);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.resource_layout:
                fragmentLayout.setCurrentItem(0);

                break;
            case R.id.communication_layout:
              fragmentLayout.setCurrentItem(1);
                break;
            case R.id.message_layout:
              fragmentLayout.setCurrentItem(2);
                break;
            case R.id.myself_layout:
                fragmentLayout.setCurrentItem(3);
                break;
        }
    }

    private FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.getUserInfo();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (resourceFragment != null && ((resourceFragment.typeLayout != null && resourceFragment.typeLayout.getVisibility() == View.VISIBLE) || (resourceFragment.selectScreenLayout != null && resourceFragment.selectScreenLayout.getVisibility() == View.VISIBLE) || (resourceFragment.sortSelectLayout != null && resourceFragment.sortSelectLayout.getVisibility() == View.VISIBLE))) {
                resourceFragment.typeLayout.setVisibility(View.GONE);
                resourceFragment.selectScreenLayout.setVisibility(View.GONE);
                resourceFragment.sortSelectLayout.setVisibility(View.GONE);
                return true;
            }
            showMessage("再返回一次退出舞美汇");
            if (canOut) {
                finish();
            }
            canOut = true;

            handler.postDelayed(() -> canOut = false, 5000);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

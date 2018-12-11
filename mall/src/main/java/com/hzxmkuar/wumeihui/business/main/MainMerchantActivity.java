package com.hzxmkuar.wumeihui.business.main;

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
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.util.SpUtil;
import com.hzxmkuar.wumeihui.business.main.fragment.WorkFragment;
import com.hzxmkuar.wumeihui.circle.fragment.CircleFragment;

import com.hzxmkuar.wumeihui.message.MessageFragment;
import com.hzxmkuar.wumeihui.personal.main.presenter.MainPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainMerchantActivity extends BaseActivity {

    @BindView(R.id.fragment_layout)
    ViewPager fragmentLayout;

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
    private List<Fragment> fragmentList = new ArrayList<>();



    private Handler handler = new Handler();
    private boolean canOut;
    private MessageFragment messageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_merchant);
        ButterKnife.bind(this);
        initFragment();


        SpUtil.put("IsMerchant", true);

//sdk初始化加载的聊天记录为20条，到顶时需要去db里获取更多

    }

    private void initFragment() {

        fragmentList.add(new WorkFragment());
        messageFragment = new MessageFragment(this);

        fragmentList.add(messageFragment);
        fragmentList.add(new CircleFragment());
        fragmentLayout.setAdapter(adapter);
        fragmentLayout.setOffscreenPageLimit(3);
        fragmentLayout.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                communicationView.setBackgroundResource(position==2?R.drawable.main_communication_select:R.drawable.main_communication_un_select);
                messageView.setBackgroundResource(position==1?R.drawable.main_message_select:R.drawable.service_message_ico);
                myselfView.setBackgroundResource(position==0?R.drawable.main_myself_select:R.drawable.main_myself_un_select);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        fragmentLayout.setCurrentItem(getIntent().getIntExtra("Index", 0));

    }

    private FragmentPagerAdapter adapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    };



    @OnClick({R.id.communication_layout, R.id.message_layout, R.id.myself_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.communication_layout:
                fragmentLayout.setCurrentItem(2);

                break;
            case R.id.message_layout:

                fragmentLayout.setCurrentItem(1);
                break;
            case R.id.myself_layout:

                fragmentLayout.setCurrentItem(0);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
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

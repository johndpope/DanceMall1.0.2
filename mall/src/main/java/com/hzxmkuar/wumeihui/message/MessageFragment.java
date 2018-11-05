package com.hzxmkuar.wumeihui.message;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BaseFragment;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by xzz on 2018/8/15.
 */

@SuppressLint("ValidFragment")
public class MessageFragment extends BaseFragment {

    @BindView(R.id.system_message)
    TextView systemMessage;
    @BindView(R.id.chat_message)
    TextView chatMessage;
    @BindView(R.id.move_line)
    AutoRelativeLayout moveLine;

    Unbinder unbinder;
    @BindView(R.id.message_fragment_layout)
    ViewPager messageFragmentLayout;
    private SystemMessageFragment systemMessageFragment;
    private boolean isViewCreate;
    private boolean isUiVisible;
    private FragmentTransaction transaction;
    private BaseActivity baseActivity;
    private EaseConversationListFragment chatFragment;
    private boolean firstLoad=true;
    private List<Fragment> fragmentList=new ArrayList<>();

    public MessageFragment(BaseActivity activity) {
        this.baseActivity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = View.inflate(appContext, R.layout.fragment_message, null);
        unbinder = ButterKnife.bind(this, mView);
        userInfoTo = userInfoHelp.getUserInfo();




        return mView;
    }



    @Override
    public void onStart() {
        super.onStart();
        if (!firstLoad) {
            return;
        }
        firstLoad = false;
        EMClient.getInstance().login(userInfoTo.getMobile(), "123456", new EMCallBack() {
            @Override
            public void onSuccess() {
                getActivity().runOnUiThread(() -> initFragment());
            }

            @Override
            public void onError(int i, String s) {
//                getActivity().runOnUiThread(() -> showMessage(i+"==="+s));
//             if (i==200)
//                 getActivity().runOnUiThread(() ->initFragment());

            }

            @Override
            public void onProgress(int i, String s) {

            }
        });

    }

    private void initFragment() {
        systemMessage.setEnabled(true);
        chatMessage.setEnabled(true);
        chatFragment = new EaseConversationListFragment();
        Bundle args = new Bundle();

        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID, userInfoTo.getMobile());
        chatFragment.setArguments(args);



        chatFragment.setConversationListItemClickListener(conversation -> {
            Intent intent = new Intent(appContext, ChatActivity.class);
            intent.putExtra("UserId", conversation.conversationId());
            intent.putExtra("Name", conversation.getLastMessage().getStringAttribute("name", ""));
            intent.putExtra("Pic", conversation.getLastMessage().getStringAttribute("pic", ""));
            startActivity(intent);
            goToAnimation(1);
        });
        systemMessageFragment = new SystemMessageFragment();
        fragmentList.add(systemMessageFragment);
        fragmentList.add(chatFragment);

        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
        messageFragmentLayout.setOffscreenPageLimit(2);
        messageFragmentLayout.setAdapter(pagerAdapter);
        messageFragmentLayout.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
               moveLine.setX((float) (position*getScreenWidth()*0.5+positionOffsetPixels*0.5));

            }

            @Override
            public void onPageSelected(int position) {
                systemMessage.setTextColor(position==0?Color.parseColor("#000000"):Color.parseColor("#999999"));
                chatMessage.setTextColor(position==1?Color.parseColor("#000000"):Color.parseColor("#999999"));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.system_message, R.id.chat_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.system_message:
               messageFragmentLayout.setCurrentItem(0);

                moveLine.setX(0);
                break;
            case R.id.chat_message:
                messageFragmentLayout.setCurrentItem(1);

                moveLine.setX(350 * getScreenWidth() / 750);
                break;
        }
    }
}
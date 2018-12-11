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
import android.text.TextUtils;
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
import com.hzxmkuar.wumeihui.base.util.SpUtil;
import com.hzxmkuar.wumeihui.personal.main.presenter.MainPresenter;
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
    @BindView(R.id.finish_layout)
    AutoRelativeLayout finishLayout;
    @BindView(R.id.delete_layout)
    AutoRelativeLayout deleteLayout;
    @BindView(R.id.delete_text)
    TextView deleteText;
    private SystemMessageFragment systemMessageFragment;
    private boolean isViewCreate;
    private boolean isUiVisible;
    private FragmentTransaction transaction;
    private BaseActivity baseActivity;
    private EaseConversationListFragment chatFragment;
    private boolean firstLoad = true;
    private List<Fragment> fragmentList = new ArrayList<>();
    private boolean init;
    private MainPresenter presenter;

    public MessageFragment(BaseActivity activity) {
        this.baseActivity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = View.inflate(appContext, R.layout.fragment_message, null);
        unbinder = ButterKnife.bind(this, mView);
        userInfoTo = userInfoHelp.getUserInfo();
        presenter = new MainPresenter(this);

        return mView;
    }


    @Override
    public void onStart() {
        super.onStart();

        if (!firstLoad || !init) {
            return;
        }
        firstLoad = false;

        EMClient.getInstance().logout(true);
        EMClient.getInstance().login(userInfoTo.getMobile(), "123456", new EMCallBack() {
            @Override
            public void onSuccess() {
                getActivity().runOnUiThread(() -> initFragment());
            }

            @Override
            public void onError(int i, String s) {


                if (i == 200)
                    getActivity().runOnUiThread(() -> initFragment());

            }

            @Override
            public void onProgress(int i, String s) {

            }
        });

    }

    public void init(String phone) {
        init = true;
        EMClient.getInstance().logout(true);
        EMClient.getInstance().login(phone, "123456", new EMCallBack() {
            @Override
            public void onSuccess() {
                getActivity().runOnUiThread(() -> initFragment());
            }

            @Override
            public void onError(int i, String s) {


                if (i == 200)
                    getActivity().runOnUiThread(() -> initFragment());

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
                moveLine.setX((float) (position * getScreenWidth() * 0.5 + positionOffsetPixels * 0.5));

            }

            @Override
            public void onPageSelected(int position) {
                systemMessage.setTextColor(position == 0 ? Color.parseColor("#000000") : Color.parseColor("#999999"));
                chatMessage.setTextColor(position == 1 ? Color.parseColor("#000000") : Color.parseColor("#999999"));
                deleteLayout.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        messageFragmentLayout.setCurrentItem(getActivity().getIntent().getIntExtra("MessageIndex", 0));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.system_message, R.id.chat_message, R.id.delete_layout, R.id.finish_layout})
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
            case R.id.delete_layout:
                if (deleteLayout.isSelected()) {
                    if (TextUtils.isEmpty(systemMessageFragment.presenter.getSelectId())) {
                        showMessage("请选择要删除的消息");
                        return;
                    }
                }
                deleteLayout.setSelected(!deleteLayout.isSelected());
                systemMessageFragment.setDelete(deleteLayout.isSelected());
                finishLayout.setVisibility(deleteLayout.isSelected() ? View.VISIBLE : View.GONE);
                deleteText.setText(deleteLayout.isSelected() ? "删除" : "选择");
                break;
            case R.id.finish_layout:
                systemMessageFragment.setFinishDelete(deleteLayout.isSelected());
                break;
        }
    }

    @Override
    public void loadDataSuccess(Object data) {
        userInfoTo = userInfoHelp.getUserInfo();

        SpUtil.put("ChatName", userInfoTo.getUsername());
        SpUtil.put("ChatPic", userInfoTo.getFace_url());
        init(userInfoTo.getMobile());

    }
}

package com.hzxmkuar.wumeihui.business.main;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainMerchantActivity extends BaseActivity {

    @BindView(R.id.fragment_layout)
    LinearLayout fragmentLayout;

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
    private FragmentTransaction transaction;


    private MessageFragment messageFragment;
    private WorkFragment myselfFragment;

    private CircleFragment circleFragment;
    private MainPresenter presenter;
    private Handler handler=new Handler();
    private boolean canOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_merchant);
        ButterKnife.bind(this);
        initFragment();
        presenter = new MainPresenter(this);

        SpUtil.put("IsMerchant",true);

//sdk初始化加载的聊天记录为20条，到顶时需要去db里获取更多

    }

    private void initFragment() {
//        new LoginPresenter().getVerificationCode();

        circleFragment = new CircleFragment();
        messageFragment = new MessageFragment(this);
        myselfFragment = new WorkFragment();

        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_layout, getIntent().getIntExtra("Index", 0) == 1 ? circleFragment : myselfFragment);
        transaction.commit();

    }

    @OnClick({R.id.communication_layout, R.id.message_layout, R.id.myself_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.communication_layout:
                modifyFragment(circleFragment);
                communicationView.setBackgroundResource(R.drawable.main_communication_select);
                messageView.setBackgroundResource(R.drawable.service_message_ico);
                myselfView.setBackgroundResource(R.drawable.main_myself_un_select);
                break;
            case R.id.message_layout:
                communicationView.setBackgroundResource(R.drawable.main_communication_un_select);
                messageView.setBackgroundResource(R.drawable.main_message_select);
                myselfView.setBackgroundResource(R.drawable.main_myself_un_select);
                modifyFragment(messageFragment);
                break;
            case R.id.myself_layout:
                communicationView.setBackgroundResource(R.drawable.main_communication_un_select);
                messageView.setBackgroundResource(R.drawable.service_message_ico);
                myselfView.setBackgroundResource(R.drawable.main_myself_select);
                modifyFragment(myselfFragment);
                break;
        }
    }

    private void modifyFragment(Fragment fragment) {
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_layout, fragment);
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.getUserInfo();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode==KeyEvent.KEYCODE_BACK){
            showMessage("再返回一次退出舞美汇");
            if (canOut){
                finish();
            }
            canOut=true;

            handler.postDelayed(() -> canOut=false,5000);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

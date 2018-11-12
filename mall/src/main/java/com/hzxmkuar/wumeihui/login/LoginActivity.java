package com.hzxmkuar.wumeihui.login;

import android.content.Intent;
import android.databinding.Observable;
import android.databinding.ObservableInt;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Event;
import com.hzxmkuar.wumeihui.base.WebActivity;
import com.hzxmkuar.wumeihui.login.presenter.LoginPresenter;
import com.hzxmkuar.wumeihui.personal.MainActivity;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzxmkuar.com.applibrary.domain.login.UserInfoTo;
import hzxmkuar.com.applibrary.domain.login.WechatLoginTo;
import hzxmkuar.com.applibrary.domain.login.WechatUserInfoTo;

/**
 * Created by xzz on 2018/8/14.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.get_verification)
    TextView getVerification;
    @BindView(R.id.phone_number)
    EditText phoneNumber;
    @BindView(R.id.verification_code)
    EditText verificationCode;
    @BindView(R.id.wechat_bg)
    View wechatBg;
    private LoginPresenter presenter;
    private ObservableInt countTime = new ObservableInt();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setView();
        presenter = new LoginPresenter(this);
        EventBus.getDefault().register(this);
    }

    private void setView() {
        getVerification.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    @OnClick({R.id.close, R.id.get_verification, R.id.login, R.id.wechat_login, R.id.person_agreement, R.id.user_agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.close:
                finish();
                goToAnimation(2);
                break;
            case R.id.get_verification:
                if (checkPhone(phoneNumber.getText().toString()))
                    presenter.getVerificationCode(phoneNumber.getText().toString());
                break;
            case R.id.login:
                if (!checkPhone(phoneNumber.getText().toString()))
                    return;
                if (TextUtils.isEmpty(verificationCode.getText().toString())) {
                    showMessage("请输入验证码");
                    return;
                }
                presenter.login(phoneNumber.getText().toString(), verificationCode.getText().toString());
                break;
            case R.id.wechat_login:
                wechatLogin();
                break;
            case R.id.user_agreement:
                Intent intent = new Intent(appContext, WebActivity.class);
                intent.putExtra("Type", 5);
                intent.putExtra("Title", "用户使用协议");
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.person_agreement:
                intent = new Intent(appContext, WebActivity.class);
                intent.putExtra("Type", 6);
                intent.putExtra("Title", "隐私条款");
                startActivity(intent);
                goToAnimation(1);
                break;
        }
    }

    public void wechatLogin() {
        // send oauth request
        IWXAPI api = WXAPIFactory.createWXAPI(appContext, "wx09143ad01dabb4c9", false);
        api.registerApp("wx09143ad01dabb4c9");
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
//        req.state = "wechat_sdk_demo_test";
        api.sendReq(req);

    }

    @Override
    public void loadDataSuccess(Object data) {
        getVerification.setEnabled(false);
        new Thread(() -> {
            for (int i = 60; i >= 0; i--) {
                SystemClock.sleep(1000);
                countTime.set(i);
            }
        }).start();
        countTime.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                runOnUiThread(() -> {
                    if (countTime.get() == 0) {
                        getVerification.setEnabled(true);
                        getVerification.setText("重发验证码");
                    } else
                        getVerification.setText(countTime.get() + "秒后重发");
                });

            }
        });
    }

    @Override
    protected void submitDataSuccess(Object data) {
        WechatLoginTo loginTo = new Gson().fromJson(JSON.toJSONString(data), WechatLoginTo.class);
        Intent intent = new Intent(appContext, MainActivity.class);
        intent.putExtra("IsSplash", true);
        userInfoHelp.saveUserLogin(true);
        UserInfoTo userInfoTo = new UserInfoTo();
        userInfoTo.setUid(loginTo.getUid());
        userInfoTo.setHashid(loginTo.getHashid());
        userInfoHelp.saveUserInfo(userInfoTo);
        startActivity(intent);
        finish();
        goToAnimation(1);
    }

    public void weChatLoginSuccess(WechatLoginTo data) {
        WechatLoginTo userInfoTo = data;
        if (userInfoTo.getUid() == 0) {
            Intent intent = new Intent(appContext, BindPhoneActivity.class);
            intent.putExtra("Oauth", userInfoTo.getOauth_id());
            startActivity(intent);


        } else {
            UserInfoTo userInfo = new UserInfoTo();
            userInfo.setUid(userInfoTo.getUid());
            userInfo.setHashid(userInfoTo.getHashid());
            userInfoHelp.saveUserInfo(userInfo);
            userInfoHelp.saveUserLogin(true);
            Intent intent = new Intent(appContext, MainActivity.class);
            intent.putExtra("IsSplash",true);
            startActivity(intent);


            goToAnimation(1);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void wechatLoginData(Event<WechatUserInfoTo> event) {
        if ("WechatLoginSuccess".equals(event.getType())) {
            wechatBg.setVisibility(View.VISIBLE);
            presenter.wechatLogin(event.getData());
        }
    }
}

package com.hzxmkuar.wumeihui.login;

import android.content.Intent;
import android.databinding.Observable;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.login.presenter.BindPhonePresenter;
import com.hzxmkuar.wumeihui.personal.MainActivity;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzxmkuar.com.applibrary.domain.login.UserInfoTo;
import hzxmkuar.com.applibrary.domain.login.WechatLoginTo;

/**
 * Created by xzz on 2018/8/14.
 **/

public class BindPhoneActivity extends BaseActivity {

    @BindView(R.id.get_verification)
    TextView getVerification;
    @BindView(R.id.phone_number)
    EditText phoneNumber;
    @BindView(R.id.verification_code)
    EditText verificationCode;
    private BindPhonePresenter presenter;
    private ObservableInt countTime=new ObservableInt();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_phone);
        ButterKnife.bind(this);
        setTitleName(Constant.bindPhoneNumber);
        presenter = new BindPhonePresenter(this);

    }

    @OnClick({R.id.get_verification, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.get_verification:
                if (checkPhone(phoneNumber.getText().toString()))
                presenter.getVerificationCode(phoneNumber.getText().toString());
                break;
            case R.id.submit:
               if (!checkPhone(phoneNumber.getText().toString()))
                   return;
                if (TextUtils.isEmpty(verificationCode.getText().toString())) {
                    showMessage("请输入验证码");
                    return;
                }
                presenter.bindPhone(phoneNumber.getText().toString(),verificationCode.getText().toString(),getIntent().getIntExtra("Oauth",0));
                break;
        }
    }

    @Override
    public void loadDataSuccess(Object data) {
        getVerification.setEnabled(false);
        new Thread(() -> {
            for (int i=60;i>=0;i--){
                SystemClock.sleep(1000);
                countTime.set(i);
            }
        }).start();
        countTime.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                runOnUiThread(() -> {
                    if(countTime.get()==0){
                        getVerification.setEnabled(true);
                        getVerification.setText("重发验证码");
                    }else
                        getVerification.setText(countTime.get()+"秒后重发");
                });

            }
        });
    }

    @Override
    protected void submitDataSuccess(Object data) {
        userInfoHelp.saveUserLogin(true);
        UserInfoTo userInfoTo=new UserInfoTo();
        userInfoTo.setUid(((WechatLoginTo)data).getUid());
        userInfoTo.setHashid(((WechatLoginTo)data).getHashid());
        userInfoHelp.saveUserInfo(userInfoTo);

        Intent intent=new Intent(appContext,MainActivity.class);
        intent.putExtra("IsSplash",true);
        startActivity(intent);
        goToAnimation(1);

    }
}

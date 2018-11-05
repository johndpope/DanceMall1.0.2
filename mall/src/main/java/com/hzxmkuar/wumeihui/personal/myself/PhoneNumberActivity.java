package com.hzxmkuar.wumeihui.personal.myself;

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

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.personal.myself.presenter.ModifyPhonePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/9/5.
 */

public class PhoneNumberActivity extends BaseActivity {
    @BindView(R.id.get_verification)
    TextView getVerification;
    @BindView(R.id.phone_number)
    EditText phoneNumber;
    @BindView(R.id.verification_code)
    EditText verificationCode;
    private ObservableInt countTime = new ObservableInt();
    private ModifyPhonePresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);
        ButterKnife.bind(this);
        setTitleName(Constant.PHONE_NUMBER);
        getVerification.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        presenter = new ModifyPhonePresenter(this);
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
                presenter.modifyPhone(phoneNumber.getText().toString(), verificationCode.getText().toString());
                break;
        }
    }

    @Override
    protected void submitDataSuccess(Object data) {
        Intent intent=new Intent(appContext,PersonSettingActivity.class);

        startActivity(intent);
        goToAnimation(1);
    }
}

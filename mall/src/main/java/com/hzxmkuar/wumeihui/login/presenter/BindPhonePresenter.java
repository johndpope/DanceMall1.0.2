package com.hzxmkuar.wumeihui.login.presenter;


import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.LoginApi;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.VerificationParam;
import hzxmkuar.com.applibrary.domain.login.BindPhoneParam;
import hzxmkuar.com.applibrary.domain.login.WechatLoginTo;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/1.
 **/

public class BindPhonePresenter extends BasePresenter {

    public BindPhonePresenter(BaseActivity activity) {
        initContext(activity);
    }

    public void getVerificationCode(String phone) {
        VerificationParam param = new VerificationParam();
        param.setMobile(phone);
        param.setType(3);
        param.setHash(getHashString(VerificationParam.class, param));
        ApiClient.create(LoginApi.class).getVerificationCode(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo>(this) {


                    @Override
                    public void onNext(MessageTo msg) {
                        if (msg.getCode() == 0)
                            getDataSuccess(msg.getData());
                        else
                            showMessage(msg.getMsg());
                    }
                }
        );
    }


    public void bindPhone(String phone, String sms, int oauthId) {

        BindPhoneParam param = new BindPhoneParam();
        param.setMobile(phone);
        param.setOauth_id(oauthId);
        param.setSms_code(sms);
        param.setJpush_id("Jpush");
        param.setHash(getHashString(BindPhoneParam.class, param));
        showLoadingDialog();
        ApiClient.create(LoginApi.class).bindPhone(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<WechatLoginTo>>(this) {
                    @Override
                    public void onNext(MessageTo<WechatLoginTo> msg) {
                        if (msg.getCode() == 0) {
                            chatRegister(phone);
                            submitDataSuccess(msg.getData());
                        } else
                            showMessage(msg.getMsg());
                    }


                }
        );
    }

    private void chatRegister(String phone) {

        new Thread(() -> {
            try {
                EMClient.getInstance().createAccount(phone, "123456");
            } catch (HyphenateException e) {
                e.printStackTrace();
            }
        }).start();

    }

}

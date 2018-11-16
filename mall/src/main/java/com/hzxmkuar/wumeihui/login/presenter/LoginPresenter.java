package com.hzxmkuar.wumeihui.login.presenter;

import android.widget.Toast;


import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;
import com.hzxmkuar.wumeihui.login.LoginActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.LoginApi;
import hzxmkuar.com.applibrary.domain.BaseParam;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.VerificationParam;
import hzxmkuar.com.applibrary.domain.login.LoginParam;
import hzxmkuar.com.applibrary.domain.login.UserInfoTo;
import hzxmkuar.com.applibrary.domain.login.WechatLoginParam;
import hzxmkuar.com.applibrary.domain.login.WechatLoginTo;
import hzxmkuar.com.applibrary.domain.login.WechatUserInfoTo;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import util.MD5;

/**
 * Created by Administrator on 2018/9/1.
 **/

public class LoginPresenter extends BasePresenter {
    private String jpushId;
    public LoginPresenter(BaseActivity activity) {
        initContext(activity);
        jpushId= JPushInterface.getRegistrationID(activity);
    }

    public void getVerificationCode(String phone) {
        VerificationParam param = new VerificationParam();
        param.setMobile(phone);
        param.setType(1);
        param.setHash(getHashString(VerificationParam.class, param));
        showLoadingDialog();
        ApiClient.create(LoginApi.class).getVerificationCode(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo>(this) {


                    @Override
                    public void onNext(MessageTo msg) {

                        if (msg.getCode() == 0) {
                            getDataSuccess(msg.getData());
                        }else
                            showMessage(msg.getMsg());
                    }
                }
        );
    }


    public void login(String phone, String code) {

        LoginParam param = new LoginParam();
        param.setSms_code(code);
        param.setMobile(phone);
        param.setJpush_id(jpushId);
        param.setInvite_code("android");
        param.setHash(getHashStringNoUser(LoginParam.class, param));
        showLoadingDialog();
        ApiClient.create(LoginApi.class).login(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo>(this) {
                    @Override
                    public void onNext(MessageTo msg) {
                        if (msg.getCode() == 0) {
                            chatRegister(phone);
                            submitDataSuccess(msg.getData());


                        } else
                            showMessage(msg.getMsg());
                    }


                }
        );
    }

    public void wechatLogin(WechatUserInfoTo wechatUserInfoTo) {

        WechatLoginParam param = new WechatLoginParam();
        param.setNickname(wechatUserInfoTo.getNickname());
        param.setSex(wechatUserInfoTo.getSex());
        param.setOpenid(wechatUserInfoTo.getOpenid());
        param.setHeadimgurl(wechatUserInfoTo.getHeadimgurl());
        param.setJpush_id(jpushId);
        param.setHash(getHashStringNoUser(WechatLoginParam.class, param));

        ApiClient.create(LoginApi.class).wechatLogin(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<WechatLoginTo>>(this) {
                    @Override
                    public void onNext(MessageTo<WechatLoginTo> msg) {
                        if (msg.getCode() == 0) {
                            ((LoginActivity)activity). weChatLoginSuccess(msg.getData());

                        } else
                            showMessage(msg.getMsg());
                    }


                }
        );
    }

    private void chatRegister(String phone){
        UserInfoTo userInfoTo = new UserInfoTo();
        userInfoTo.setMobile(phone);
        userInfoHelp.saveUserInfo(userInfoTo);
        new Thread(() -> {
            try {
                EMClient.getInstance().createAccount(phone, "123456");
            } catch (HyphenateException e) {
                e.printStackTrace();
            }
        }).start();

    }
}

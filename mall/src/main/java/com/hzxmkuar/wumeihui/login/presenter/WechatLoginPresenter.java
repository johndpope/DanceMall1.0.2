package com.hzxmkuar.wumeihui.login.presenter;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;
import com.hzxmkuar.wumeihui.login.LoginActivity;
import com.hzxmkuar.wumeihui.wxapi.WXEntryActivity;

import cn.sharesdk.wechat.utils.WechatHandlerActivity;
import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.LoginApi;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.VerificationParam;
import hzxmkuar.com.applibrary.domain.login.LoginParam;
import hzxmkuar.com.applibrary.domain.login.UserInfoTo;
import hzxmkuar.com.applibrary.domain.login.WechatLoginParam;
import hzxmkuar.com.applibrary.domain.login.WechatLoginTo;
import hzxmkuar.com.applibrary.domain.login.WechatUserInfoTo;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/1.
 **/

public class WechatLoginPresenter extends BasePresenter {
private WXEntryActivity activity;
 public WechatLoginPresenter(WXEntryActivity activity){
     this.activity=activity;
 }



    public void wechatLogin(WechatUserInfoTo wechatUserInfoTo) {

        WechatLoginParam param = new WechatLoginParam();
        param.setNickname(wechatUserInfoTo.getNickname());
        param.setSex(wechatUserInfoTo.getSex()==0?1:wechatUserInfoTo.getSex());
        param.setOpenid(wechatUserInfoTo.getOpenid());
        param.setHeadimgurl(wechatUserInfoTo.getHeadimgurl());
        param.setJpush_id("Jpush");
        param.setHash(getHashString(WechatLoginParam.class, param));

        ApiClient.create(LoginApi.class).wechatLogin(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<WechatLoginTo>>(this) {
                    @Override
                    public void onNext(MessageTo<WechatLoginTo> msg) {
                        if (msg.getCode() == 0) {
                            activity.loginSuccess(msg.getData());

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

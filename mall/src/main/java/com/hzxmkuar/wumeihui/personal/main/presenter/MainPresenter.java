package com.hzxmkuar.wumeihui.personal.main.presenter;

import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.MainApi;
import hzxmkuar.com.applibrary.domain.BaseParam;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.login.UserInfoTo;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/17.
 */

public class MainPresenter extends BasePresenter {

    public MainPresenter(BaseActivity activity) {
        initContext(activity);
        getUserInfo();
    }

    public void getUserInfo() {
        BaseParam param = new BaseParam();
        param.setHash(getHashString(BaseParam.class, param));
        ApiClient.create(MainApi.class).getUserInfo(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<UserInfoTo>>(this) {
                    @Override
                    public void onNext(MessageTo<UserInfoTo> msg) {
                      if (msg.getCode()==0){
                          if (msg.getData()!=null) {
                              msg.getData().setHashid(userInfoTo.getHashid());
                              msg.getData().setOauth_id(userInfoTo.getOauth_id());
                              userInfoHelp.saveUserInfo(msg.getData());
                              getDataSuccess(msg.getData());
                          }
                      }else
                          showMessage(msg.getMsg());
                    }
                }
        );
    }
}

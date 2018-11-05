package com.hzxmkuar.wumeihui.personal.myself.presenter;

import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.LoginApi;
import hzxmkuar.com.applibrary.api.UserApi;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.VerificationParam;
import hzxmkuar.com.applibrary.domain.user.ModifyPhoneParam;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/20.
 */

public class ModifyPhonePresenter extends BasePresenter {

    public ModifyPhonePresenter(BaseActivity activity){
        initContext(activity);
    }

    public void getVerificationCode(String phone) {
        VerificationParam param = new VerificationParam();
        param.setMobile(phone);
        param.setType(3);
        param.setHash(getHashStringNoUser(VerificationParam.class, param));
        ApiClient.create(LoginApi.class).getVerificationCode(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo>(this) {


                    @Override
                    public void onNext(MessageTo msg) {

                        if (msg.getCode() == 0) {
                            getDataSuccess(msg.getData());
                        }
                    }
                }
        );
    }

    public void modifyPhone(String phone,String code){
        ModifyPhoneParam param=new ModifyPhoneParam();
        param.setMobile(phone);
        param.setSms_code(code);
        param.setHash(getHashString(ModifyPhoneParam.class,param));
        showLoadingDialog();
        ApiClient.create(UserApi.class).modifyPhone(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo>(this) {
                    @Override
                    public void onNext(MessageTo msg) {
                        if (msg.getCode()==0)
                            submitDataSuccess(msg.getData());

                    }
                }
        );
    }
}

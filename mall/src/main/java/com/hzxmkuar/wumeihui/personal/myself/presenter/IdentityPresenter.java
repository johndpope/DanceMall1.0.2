package com.hzxmkuar.wumeihui.personal.myself.presenter;

import android.os.Handler;

import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;
import com.hzxmkuar.wumeihui.personal.main.fragment.MyselfFragment;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.UserApi;
import hzxmkuar.com.applibrary.domain.BaseParam;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.user.CompanyIdentityParam;
import hzxmkuar.com.applibrary.domain.user.IdentityResultTo;
import hzxmkuar.com.applibrary.domain.user.PersonIdentityParam;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/28.
 */

public class IdentityPresenter extends BasePresenter{

    public IdentityPresenter(BaseActivity activity){
        initContext(activity);
        getIdentityStatue();
    }

    public void getIdentityStatue() {
        BaseParam param = new BaseParam();
        param.setHash(getHashString(BaseParam.class, param));
        showLoadingDialog();
        ApiClient.create(UserApi.class).getIdentityResult(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<IdentityResultTo>>(this) {
                    @Override
                    public void onNext(MessageTo<IdentityResultTo> msg) {
                        if (msg.getCode() == 0)
                             getDataSuccess(msg.getData());
                    }
                }
        );
    }

    public void personIdentity(int font,int background){
        PersonIdentityParam param=new PersonIdentityParam();
        param.setAuth_type(2);
        param.setRealname(userInfoTo.getUsername());
        param.setFace1(font);
        param.setFace2(background);
        param.setHash(getHashString(PersonIdentityParam.class,param));
        showLoadingDialog();
        ApiClient.create(UserApi.class).personIdentity(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo>(this) {
                    @Override
                    public void onNext(MessageTo msg) {
                        if (msg.getCode()==0) {
                            showMessage("身份认证提交成功");
                            new Handler().postDelayed(() -> activity.finish(),2500);
                        }
                    }
                }
        );
    }

    public void companyIdentity(String companyName,int imageId){
        CompanyIdentityParam param=new CompanyIdentityParam();
        param.setAuth_type(1);
        param.setBusiness_name(companyName);
        param.setBusiness_license(imageId);

        param.setHash(getHashString(CompanyIdentityParam.class,param));
        showLoadingDialog();
        ApiClient.create(UserApi.class).companyIdentity(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo>(this) {
                    @Override
                    public void onNext(MessageTo msg) {
                        if (msg.getCode()==0) {
                            showMessage("身份认证提交成功");
                            new Handler().postDelayed(() -> activity.finish(),2500);
                        }
                    }
                }
        );
    }
}

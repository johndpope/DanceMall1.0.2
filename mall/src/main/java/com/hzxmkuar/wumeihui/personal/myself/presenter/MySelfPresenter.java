package com.hzxmkuar.wumeihui.personal.myself.presenter;

import com.hzxmkuar.wumeihui.base.BaseFragment;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;
import com.hzxmkuar.wumeihui.personal.main.fragment.MyselfFragment;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.UserApi;
import hzxmkuar.com.applibrary.domain.BaseParam;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.user.IdentityResultTo;
import hzxmkuar.com.applibrary.domain.user.MyselfUserTo;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/28.
 **/

public class MySelfPresenter extends BasePresenter {

    public MySelfPresenter(BaseFragment fragment) {
        initContext(fragment);
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
                            ((MyselfFragment) mFragment).setIdentityStatue(msg.getData());
                    }
                }
        );
    }

    public void getEnterStatue() {
        BaseParam param = new BaseParam();
        param.setHash(getHashString(BaseParam.class, param));
        showLoadingDialog();
        ApiClient.create(UserApi.class).getMerchantStatue(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<IdentityResultTo>>(this) {
                    @Override
                    public void onNext(MessageTo<IdentityResultTo> msg) {
                        if (msg.getCode()==0)
                            ((MyselfFragment)mFragment).setMerchatStatue(msg.getData().getStatus());
                    }
                }
        );
    }

    public void getMySelfInfo(){
        BaseParam param=new BaseParam();
        param.setHash(getHashString(BaseParam.class,param));
        ApiClient.create(UserApi.class).myselfInfo(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<MyselfUserTo>>(this) {
                    @Override
                    public void onNext(MessageTo<MyselfUserTo> msg) {
                        if (msg.getCode()==0)
                            ((MyselfFragment)mFragment).setMySelf(msg.getData());
                    }
                }
        );
    }
}

package com.hzxmkuar.wumeihui.personal.myself.presenter;

import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.UserApi;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.user.ModifyImageParam;
import hzxmkuar.com.applibrary.domain.user.ModifyNickParam;
import hzxmkuar.com.applibrary.domain.user.NickNameTo;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/20.
 */

public class ModifyNickPresenter extends BasePresenter {

    public ModifyNickPresenter(BaseActivity activity){
        initContext(activity);
    }

    public void modifyNickName(String userName){
        ModifyNickParam param=new ModifyNickParam();
        param.setUsername(userName);
        param.setHash(getHashString(ModifyNickParam.class,param));
        showLoadingDialog();
        ApiClient.create(UserApi.class).modifynick(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<NickNameTo>>(this) {
                    @Override
                    public void onNext(MessageTo<NickNameTo> msg) {
                        if (msg.getCode()==0)
                        getDataSuccess(msg.getData());
                    }
                }
        );
    }
}

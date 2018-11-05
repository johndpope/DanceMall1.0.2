package com.hzxmkuar.wumeihui.message.presenter;

import com.hzxmkuar.wumeihui.base.BaseFragment;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.MessageApi;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.message.SystemMessageParam;
import hzxmkuar.com.applibrary.domain.message.SystemMessageTo;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/11.
 */

public class SystemMessagePresenter extends BasePresenter {

    public SystemMessagePresenter(BaseFragment fragment){
        initContext(fragment);
        getSystemMessage();
    }

    public void getSystemMessage(){
        SystemMessageParam param=new SystemMessageParam();
        param.setPage(0);
        param.setHash(getHashString(SystemMessageParam.class,param));
        ApiClient.create(MessageApi.class).getSystemMessageList(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<SystemMessageTo>>(this) {
                    @Override
                    public void onNext(MessageTo<SystemMessageTo> msg) {
                   if (msg.getCode()==0)
                       setRecycleList(msg.getData().getLists());
                    }
                }
        );
    }
}

package com.hzxmkuar.wumeihui.circle.presenter;

import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.CircleApi;
import hzxmkuar.com.applibrary.domain.BaseParam;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.circle.CircleTo;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/27.
 */

public class MyCirclePresenter extends BasePresenter {

    public MyCirclePresenter(BaseActivity activity){
        initContext(activity);

    }

    public void getMyPost(){
        BaseParam param=new BaseParam();
        param.setHash(getHashString(BaseParam.class,param));
        ApiClient.create(CircleApi.class).getMyPost(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<CircleTo>>(this) {
                    @Override
                    public void onNext(MessageTo<CircleTo> msg) {
                        if (msg.getCode()==0)
                            setRecycleList(msg.getData().getLists());
                    }
                }
        );
    }

    public void getMyCollection(){
        BaseParam param=new BaseParam();
        param.setHash(getHashString(BaseParam.class,param));
        ApiClient.create(CircleApi.class).getMyCollection(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<CircleTo>>(this) {
                    @Override
                    public void onNext(MessageTo<CircleTo> msg) {
                        if (msg.getCode()==0)
                            setRecycleList(msg.getData().getLists());
                    }
                }
        );
    }


    public void getMyLook(){
        BaseParam param=new BaseParam();
        param.setHash(getHashString(BaseParam.class,param));
        ApiClient.create(CircleApi.class).getMyLook(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<CircleTo>>(this) {
                    @Override
                    public void onNext(MessageTo<CircleTo> msg) {
                        if (msg.getCode()==0)
                            setRecycleList(msg.getData().getLists());
                    }
                }
        );
    }
}

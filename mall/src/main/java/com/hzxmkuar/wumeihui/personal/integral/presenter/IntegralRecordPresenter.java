package com.hzxmkuar.wumeihui.personal.integral.presenter;

import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.IntegralApi;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.integral.IntegralRecordTo;
import hzxmkuar.com.applibrary.domain.integral.PageParam;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/28.
 */

public class IntegralRecordPresenter extends BasePresenter {

    public IntegralRecordPresenter(BaseActivity activity){
        initContext(activity);
        getRecord();
    }
    private void getRecord(){
        PageParam param=new PageParam();
        param.setPage(0);
        param.setHash(getHashString(PageParam.class,param));
        showLoadingDialog();
        ApiClient.create(IntegralApi.class).getOrderRecord(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<IntegralRecordTo>>(this) {
                    @Override
                    public void onNext(MessageTo<IntegralRecordTo> msg) {
                        if (msg.getCode()==0)
                            setRecycleList(msg.getData().getLists());
                    }
                }
        );

    }
}

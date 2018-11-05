package com.hzxmkuar.wumeihui.personal.inquiry.presenter;

import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;

import hzxmkuar.com.applibrary.IdParam;
import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.InquiryApi;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.inquery.ServiceDetailTo;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/26.
 **/

public class ServiceDetailPresenter extends BasePresenter {

    public ServiceDetailPresenter(BaseActivity activity){
        initContext(activity);
        getServiceDetail();
    }

    private void getServiceDetail(){

        IdParam param=new IdParam();
        param.setId(activity.getIntent().getIntExtra("Id",0));
        param.setHash(getHashString(IdParam.class,param));
        showLoadingDialog();
        ApiClient.create(InquiryApi.class).getServiceDetail(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<ServiceDetailTo>>(this) {
                    @Override
                    public void onNext(MessageTo<ServiceDetailTo> msg) {
                        if (msg.getCode()==0){
                            setRecycleList(msg.getData().getLists());
                        }
                    }
                }
        );
    }
}

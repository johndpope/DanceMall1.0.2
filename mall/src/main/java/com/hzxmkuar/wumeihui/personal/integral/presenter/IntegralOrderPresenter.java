package com.hzxmkuar.wumeihui.personal.integral.presenter;

import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.IntegralApi;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.integral.IntegralDetailTo;
import hzxmkuar.com.applibrary.domain.order.OrderIdParam;
import lombok.Data;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/28.
 */

public class IntegralOrderPresenter extends BasePresenter {

    public IntegralOrderPresenter(BaseActivity activity){
        initContext(activity);
        getOrderDetail();
    }

    private void getOrderDetail(){
        OrderIdParam param=new OrderIdParam();
        param.setOrder_id(activity.getIntent().getIntExtra("OrderId",0));
        param.setHash(getHashString(OrderIdParam.class,param));
        showLoadingDialog();
        ApiClient.create(IntegralApi.class).getOrderDetail(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<IntegralDetailTo>>(this) {
                    @Override
                    public void onNext(MessageTo<IntegralDetailTo> msg) {
                        if (msg.getCode()==0)
                            getDataSuccess(msg.getData());
                    }
                }
        );
    }
}

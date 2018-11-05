package com.hzxmkuar.wumeihui.personal.order.presenter;

import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.OrderApi;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.order.SelectCouponParam;
import hzxmkuar.com.applibrary.domain.order.SelectCouponTo;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/29.
 */

public class SelectCouponPresenter extends BasePresenter {
    public SelectCouponPresenter(BaseActivity activity){
        initContext(activity);
        getCoupon();
    }
    private void getCoupon(){

        SelectCouponParam param=new SelectCouponParam();
        param.setBusoffer_id(activity.getIntent().getIntExtra("BusofferId",0));
        param.setPayment_mode(activity.getIntent().getIntExtra("Mode",1));
        param.setHash(getHashString(SelectCouponParam.class,param));
        ApiClient.create(OrderApi.class).getSelectCouponList(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<SelectCouponTo>>(this) {
                    @Override
                    public void onNext(MessageTo<SelectCouponTo> msg) {
                        if (msg.getCode()==0)
                            setRecycleList(msg.getData().getLists());
                    }
                }
        );
    }
}

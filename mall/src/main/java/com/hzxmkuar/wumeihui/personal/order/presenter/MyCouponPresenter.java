package com.hzxmkuar.wumeihui.personal.order.presenter;

import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.OrderApi;
import hzxmkuar.com.applibrary.domain.BaseParam;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.order.MyCouponTo;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/18.
 */

public class MyCouponPresenter extends BasePresenter {

    public MyCouponPresenter(BaseActivity activity){
        initContext(activity);
        getMyCoupon();
    }

    public void getMyCoupon(){
        BaseParam param=new BaseParam();

        param.setHash(getHashString(BaseParam.class,param));
        showLoadingDialog();
        ApiClient.create(OrderApi.class).getMyCouponList(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<MyCouponTo>>(this) {
                    @Override
                    public void onNext(MessageTo<MyCouponTo> msg) {
                        if (msg.getCode()==0)
                      getDataSuccess(msg.getData());
                    }
                }
        );
    }
}

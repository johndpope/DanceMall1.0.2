package com.hzxmkuar.wumeihui.personal.order.presenter;

import android.text.TextUtils;

import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;
import com.hzxmkuar.wumeihui.personal.order.OrderSettleActivity;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.OrderApi;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.order.AddOrderParam;
import hzxmkuar.com.applibrary.domain.order.BusofferIdParam;
import hzxmkuar.com.applibrary.domain.order.OrderSettleTo;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/18.
 **/

public class OrderSettlePresenter extends BasePresenter {

    public OrderSettlePresenter(BaseActivity activity) {
        initContext(activity);
        getOrderDetail();
    }

    private void getOrderDetail() {
        BusofferIdParam param = new BusofferIdParam();
        param.setBusoffer_id(activity.getIntent().getIntExtra("BusofferId", 0));
        param.setHash(getHashString(BusofferIdParam.class, param));
        showLoadingDialog();
        ApiClient.create(OrderApi.class).getOrderInfo(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<OrderSettleTo>>(this) {
                    @Override
                    public void onNext(MessageTo<OrderSettleTo> msg) {
                        if (msg.getCode() == 0)
                            ((OrderSettleActivity) activity).setView(msg.getData());
                    }
                }
        );

    }

    public void settle(int inquiryId, int mode, int couponId, String remark) {

        AddOrderParam param = new AddOrderParam();

        param.setPayment_mode(mode);
        param.setCoupon_id(couponId);
        param.setBusoffer_id(inquiryId);
        param.setRemarks(TextUtils.isEmpty(remark)?"暂无备注":remark);
        param.setHash(getHashString(AddOrderParam.class, param));
        ApiClient.create(OrderApi.class).addOrder(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo>(this) {
                    @Override
                    public void onNext(MessageTo msg) {
                        if (msg.getCode() == 0)
                            submitDataSuccess(msg.getData());
                    }
                }
        );
    }
}

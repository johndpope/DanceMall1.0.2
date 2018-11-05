package com.hzxmkuar.wumeihui.business.myself.presenter;

import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.OrderApi;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.order.MerchantOrderDetailTo;
import hzxmkuar.com.applibrary.domain.order.OrderIdParam;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/27.
 */

public class MerchantOrderPresenter extends BasePresenter {

    public MerchantOrderPresenter(BaseActivity activity){
        initContext(activity);
        getOrderDetail();
    }

    private void getOrderDetail(){
        OrderIdParam param=new OrderIdParam();
        param.setOrder_id(activity.getIntent().getIntExtra("OrderId",0));
        param.setHash(getHashString(OrderIdParam.class,param));
        showLoadingDialog();
        ApiClient.create(OrderApi.class).merchantOrderDetail(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<MerchantOrderDetailTo>>(this) {
                    @Override
                    public void onNext(MessageTo<MerchantOrderDetailTo> msg) {
                       if (msg.getCode()==0)
                           getDataSuccess(msg.getData());
                    }
                }
        );
    }

    public void confirmReceiver(int id) {
        OrderIdParam param = new OrderIdParam();
        param.setOrder_id(id);
        param.setHash(getHashString(OrderIdParam.class, param));
        ApiClient.create(OrderApi.class).confirmReceiver(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo>(this) {
                    @Override
                    public void onNext(MessageTo msg) {
                        if (msg.getCode() == 0) {
                            showMessage("确定接单成功");
                            getOrderDetail();
                        }

                    }
                }
        );
    }
    public void cancelReceiver(int id) {
        OrderIdParam param = new OrderIdParam();
        param.setOrder_id(id);
        param.setHash(getHashString(OrderIdParam.class, param));
        ApiClient.create(OrderApi.class).cancelReceiver(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo>(this) {
                    @Override
                    public void onNext(MessageTo msg) {
                        if (msg.getCode() == 0) {
                            showMessage("取消接单成功");
                            getOrderDetail();
                        }

                    }
                }
        );
    }

    public void startOrder(int id) {
        OrderIdParam param = new OrderIdParam();
        param.setOrder_id(id);
        param.setHash(getHashString(OrderIdParam.class, param));
        ApiClient.create(OrderApi.class).startOrder(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo>(this) {
                    @Override
                    public void onNext(MessageTo msg) {
                        if (msg.getCode() == 0) {
                            showMessage("开始订单成功");
                            getOrderDetail();
                        }

                    }
                }
        );
    }

    public void finishOrder(int id) {
        OrderIdParam param = new OrderIdParam();
        param.setOrder_id(id);
        param.setHash(getHashString(OrderIdParam.class, param));
        ApiClient.create(OrderApi.class).finishOrder(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo>(this) {
                    @Override
                    public void onNext(MessageTo msg) {
                        if (msg.getCode() == 0) {
                            showMessage("完成订单成功");
                            getOrderDetail();
                        }

                    }
                }
        );
    }
}

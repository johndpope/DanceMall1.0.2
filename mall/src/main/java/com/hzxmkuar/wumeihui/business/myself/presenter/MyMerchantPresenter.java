package com.hzxmkuar.wumeihui.business.myself.presenter;

import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BaseFragment;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.OrderApi;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.order.MyMerchantOrderTo;
import hzxmkuar.com.applibrary.domain.order.OTypeParam;
import hzxmkuar.com.applibrary.domain.order.OrderIdParam;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/27.
 */

public class MyMerchantPresenter extends BasePresenter {
    private int type;

    public MyMerchantPresenter(BaseFragment fragment, int type) {

        initContext(fragment);
        this.type = type;
        getMerchantOrder();
    }

    public void getMerchantOrder() {
        OTypeParam param = new OTypeParam();
        param.setOtype(type);
        param.setPage(recyclePageIndex);
        param.setHash(getHashString(OTypeParam.class, param));
        ApiClient.create(OrderApi.class).merchantOrder(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<MyMerchantOrderTo>>(this) {
                    @Override
                    public void onNext(MessageTo<MyMerchantOrderTo> msg) {
                        if (msg.getCode() == 0)
                            setRecycleList(msg.getData().getLists());
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
                            getMerchantOrder();
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
                            getMerchantOrder();
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
                            getMerchantOrder();
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
                            getMerchantOrder();
                        }

                    }
                }
        );
    }

    @Override
    public void recycleViewRefresh() {
        super.recycleViewRefresh();
        getMerchantOrder();
    }

    @Override
    public void recycleViewLoadMore() {
        super.recycleViewLoadMore();
        getMerchantOrder();
    }
}

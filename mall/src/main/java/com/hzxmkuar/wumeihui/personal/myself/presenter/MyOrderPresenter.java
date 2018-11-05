package com.hzxmkuar.wumeihui.personal.myself.presenter;

import android.content.Intent;
import android.os.Handler;

import com.hzxmkuar.wumeihui.base.BaseFragment;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;
import com.hzxmkuar.wumeihui.business.merchant.EvaluateActivity;

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

public class MyOrderPresenter extends BasePresenter {
    private int type;
    public MyOrderPresenter(BaseFragment fragment,int type){

        initContext(fragment);
        this.type=type;
        getMerchantOrder();
    }

    public void getMerchantOrder(){
        OTypeParam param=new OTypeParam();
        param.setOtype(type);
        param.setPage(recyclePageIndex);
        showLoadingDialog();
        param.setHash(getHashString(OTypeParam.class,param));
        ApiClient.create(OrderApi.class).userOrder(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<MyMerchantOrderTo>>(this) {
                    @Override
                    public void onNext(MessageTo<MyMerchantOrderTo> msg) {
                        if (msg.getCode()==0)
                            setRecycleList(msg.getData().getLists());
                    }
                }
        );
    }

    public void confirmReceiver(int id){
        OrderIdParam param=new OrderIdParam();
        param.setOrder_id(id);
        param.setHash(getHashString(OTypeParam.class,param));
        ApiClient.create(OrderApi.class).confirmReceiver(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo>(this) {
                    @Override
                    public void onNext(MessageTo msg) {
                        if (msg.getCode()==0){
                            showMessage("确定接单成功");
                            getMerchantOrder();
                        }

                    }
                }
        );
    }

    public void cancelOrder(int id){
        OrderIdParam param=new OrderIdParam();
        param.setOrder_id(id);
        param.setHash(getHashString(OrderIdParam.class,param));
        ApiClient.create(OrderApi.class).cancelOrder(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo>(this) {
                    @Override
                    public void onNext(MessageTo msg) {
                        if (msg.getCode()==0){
                            showMessage("取消订单成功");
                            getMerchantOrder();
                        }

                    }
                }
        );
    }

    public void confirmFinish(int id){
        OrderIdParam param=new OrderIdParam();
        param.setOrder_id(id);
        param.setHash(getHashString(OrderIdParam.class,param));
        ApiClient.create(OrderApi.class).confirmFinish(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo>(this) {
                    @Override
                    public void onNext(MessageTo msg) {
                        if (msg.getCode()==0){
                            showMessage("确认完成成功");
                            getMerchantOrder();
                            new Handler().postDelayed(() -> {
                                Intent intent=new Intent(mFragment.getContext(), EvaluateActivity.class);
                                intent.putExtra("OrderId",id);
                                mFragment.startActivity(intent);

                            },2500);
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

package com.hzxmkuar.wumeihui.personal.order.presenter;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.MerchantApi;
import hzxmkuar.com.applibrary.api.OrderApi;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.integral.PageParam;
import hzxmkuar.com.applibrary.domain.order.BankPayTo;
import hzxmkuar.com.applibrary.domain.order.PayInfoTo;
import hzxmkuar.com.applibrary.domain.order.PayParam;
import hzxmkuar.com.applibrary.domain.order.WeChatPayTo;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/29.
 */

public class PayPresenter extends BasePresenter {

    public PayPresenter(BaseActivity activity){
        initContext(activity);
    }
    public void getPayInfo(int type){
        PayParam param=new PayParam();
        param.setPay_type(type);
        param.setOrder_id(activity.getIntent().getIntExtra("OrderId",0));
        param.setHash(getHashString(PayParam.class,param));
        showLoadingDialog();
        if (type==2) {
            ApiClient.create(OrderApi.class).getPayinfo(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                    new MyObserver<MessageTo<WeChatPayTo>>(this) {
                        @Override
                        public void onNext(MessageTo<WeChatPayTo> msg) {
                            if (msg.getCode() == 0)
                                getDataSuccess(msg.getData());
                            else
                                showMessage(msg.getMsg());
                        }
                    }
            );
        }else {
            ApiClient.create(OrderApi.class).getPayinfoAli(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                    new MyObserver<MessageTo>(this) {
                        @Override
                        public void onNext(MessageTo msg) {
                            if (msg.getCode() == 0) {
                                if (type==1||type==3)
                                getDataSuccess(new Gson().fromJson(JSON.toJSONString(msg.getData()), PayInfoTo.class));


                            }
                            else
                                showMessage(msg.getMsg());
                        }
                    }
            );
        }
    }

    public void payBond(int type){
        PayParam param=new PayParam();
        param.setPay_type(type);
        param.setOrder_id(activity.getIntent().getIntExtra("OrderId",0));
        param.setHash(getHashString(PayParam.class,param));
        showLoadingDialog();
        if (type==1) {
            ApiClient.create(MerchantApi.class).payBondAli(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                    new MyObserver<MessageTo<PayInfoTo>>(this) {
                        @Override
                        public void onNext(MessageTo<PayInfoTo> msg) {
                            if (msg.getCode() == 0)
                                getDataSuccess(msg.getData());
                            else
                                showMessage(msg.getMsg());
                        }
                    }
            );
        }else {
            ApiClient.create(MerchantApi.class).payBond(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                    new MyObserver<MessageTo<WeChatPayTo>>(this) {
                        @Override
                        public void onNext(MessageTo<WeChatPayTo> msg) {
                            if (msg.getCode() == 0)
                                getDataSuccess(msg.getData());
                            else
                                showMessage(msg.getMsg());
                        }
                    }
            );
        }
    }

    public void payMerchantTop(int type){
        PayParam param=new PayParam();
        param.setPay_type(type);
        param.setOrder_id(activity.getIntent().getIntExtra("OrderId",0));
        param.setHash(getHashString(PayParam.class,param));
        showLoadingDialog();
        if (type==1) {
            ApiClient.create(MerchantApi.class).payMerchantTopAli(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                    new MyObserver<MessageTo<PayInfoTo>>(this) {
                        @Override
                        public void onNext(MessageTo<PayInfoTo> msg) {
                            if (msg.getCode() == 0)
                                getDataSuccess(msg.getData());
                            else
                                showMessage(msg.getMsg());
                        }
                    }
            );
        }else {
            ApiClient.create(MerchantApi.class).payMerchantTop(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                    new MyObserver<MessageTo<WeChatPayTo>>(this) {
                        @Override
                        public void onNext(MessageTo<WeChatPayTo> msg) {
                            if (msg.getCode() == 0)
                                getDataSuccess(msg.getData());
                            else
                                showMessage(msg.getMsg());
                        }
                    }
            );
        }
    }

    public void payInquiryTop(int type){
        PayParam param=new PayParam();
        param.setPay_type(type);
        param.setOrder_id(activity.getIntent().getIntExtra("OrderId",0));
        param.setHash(getHashString(PayParam.class,param));
        showLoadingDialog();
        if (type==1) {
            ApiClient.create(MerchantApi.class).payInquiryTopAli(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                    new MyObserver<MessageTo<PayInfoTo>>(this) {
                        @Override
                        public void onNext(MessageTo<PayInfoTo> msg) {
                            if (msg.getCode() == 0)
                                getDataSuccess(msg.getData());
                            else
                                showMessage(msg.getMsg());
                        }
                    }
            );
        }else {
            ApiClient.create(MerchantApi.class).payInquiryTop(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                    new MyObserver<MessageTo<WeChatPayTo>>(this) {
                        @Override
                        public void onNext(MessageTo<WeChatPayTo> msg) {
                            if (msg.getCode() == 0)
                                getDataSuccess(msg.getData());
                            else
                                showMessage(msg.getMsg());
                        }
                    }
            );
        }
    }

    public void payPostTop(int type){
        PayParam param=new PayParam();
        param.setPay_type(type);
        param.setOrder_id(activity.getIntent().getIntExtra("OrderId",0));
        param.setHash(getHashString(PayParam.class,param));
        showLoadingDialog();
        if (type!=2) {
            ApiClient.create(MerchantApi.class).payPostTopAli(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                    new MyObserver<MessageTo>(this) {
                        @Override
                        public void onNext(MessageTo msg) {
                            if (msg.getCode() == 0)
                                getDataSuccess(new Gson().fromJson(JSON.toJSONString(msg.getData()), PayInfoTo.class));
                            else
                                showMessage(msg.getMsg());
                        }
                    }
            );
        }else {
            ApiClient.create(MerchantApi.class).payPostTop(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                    new MyObserver<MessageTo<WeChatPayTo>>(this) {
                        @Override
                        public void onNext(MessageTo<WeChatPayTo> msg) {
                            if (msg.getCode() == 0)
                                getDataSuccess(msg.getData());
                            else
                                showMessage(msg.getMsg());
                        }
                    }
            );
        }
    }
}

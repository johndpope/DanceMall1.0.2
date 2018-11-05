package com.hzxmkuar.wumeihui.business.myself.presenter;

import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.MerchantApi;
import hzxmkuar.com.applibrary.domain.BaseParam;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.merchant.ExtensionTo;
import hzxmkuar.com.applibrary.domain.order.OrderSettleInfoTo;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/10/15.
 */

public class ExtensionPresenter extends BasePresenter {
    public ExtensionPresenter(BaseActivity activity){
        initContext(activity);
        getExtension();
    }
    public void getExtension(){
        BaseParam param=new BaseParam();
        param.setHash(getHashString(BaseParam.class,param));
        showLoadingDialog();
        ApiClient.create(MerchantApi.class).extension(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<ExtensionTo>>(this) {
                    @Override
                    public void onNext(MessageTo<ExtensionTo> msg) {
                        if (msg.getCode()==0){
                            getDataSuccess(msg.getData());
                        }
                    }
                }
        );
    }

    public void merchantTopOrder(){
        BaseParam param=new BaseParam();
        param.setHash(getHashString(BaseParam.class,param));
        showLoadingDialog();
        ApiClient.create(MerchantApi.class).merchantTopOrder(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<OrderSettleInfoTo>>(this) {
                    @Override
                    public void onNext(MessageTo<OrderSettleInfoTo> msg) {
                        if (msg.getCode()==0){
                            submitDataSuccess(msg.getData());
                        }
                    }
                }
        );
    }
    public void inquiryTopOrder(){
        BaseParam param=new BaseParam();
        param.setHash(getHashString(BaseParam.class,param));
        showLoadingDialog();
        ApiClient.create(MerchantApi.class).inquiryTopOrder(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<OrderSettleInfoTo>>(this) {
                    @Override
                    public void onNext(MessageTo<OrderSettleInfoTo> msg) {
                        if (msg.getCode()==0){
                            submitDataSuccess(msg.getData());
                        }
                    }
                }
        );
    }

}

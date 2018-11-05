package com.hzxmkuar.wumeihui.business.myself.presenter;

import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.MerchantApi;
import hzxmkuar.com.applibrary.domain.BaseParam;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.merchant.BondOrderParam;
import hzxmkuar.com.applibrary.domain.merchant.BondPageTo;
import hzxmkuar.com.applibrary.domain.order.OrderSettleInfoTo;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/10/11.
 */

public class BondPresenter extends BasePresenter {

    public BondPresenter(BaseActivity activity){
        initContext(activity);

        getBondPage();
    }

    private void getBondPage(){
        BaseParam param=new BaseParam();
        param.setHash(getHashString(BaseParam.class,param));
        showLoadingDialog();
        ApiClient.create(MerchantApi.class).bondPage(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<BondPageTo>>(this) {
                    @Override
                    public void onNext(MessageTo<BondPageTo> msg) {
                        if (msg.getCode()==0)
                            getDataSuccess(msg.getData());
                    }
                }
        );
    }
    public void bondOrder(int type){

        BondOrderParam param=new BondOrderParam();
        param.setPay_mod(type);
        param.setHash(getHashString(BondOrderParam.class,param));
        showLoadingDialog();
        ApiClient.create(MerchantApi.class).bondOrder(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<OrderSettleInfoTo>>(this) {
                    @Override
                    public void onNext(MessageTo<OrderSettleInfoTo> msg) {
                        if (msg.getCode()==0)
                            submitDataSuccess(msg.getData());
                    }}
        );
    }

}

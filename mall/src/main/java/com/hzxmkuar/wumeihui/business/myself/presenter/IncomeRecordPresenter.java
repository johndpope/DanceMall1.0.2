package com.hzxmkuar.wumeihui.business.myself.presenter;

import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.MerchantApi;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.merchant.BondRecordTo;
import hzxmkuar.com.applibrary.domain.merchant.IncomeRecordParam;
import hzxmkuar.com.applibrary.domain.merchant.IncomeRecordTo;
import hzxmkuar.com.applibrary.domain.merchant.ShopIdParam;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/10/16.
 */

public class IncomeRecordPresenter extends BasePresenter {
    public IncomeRecordPresenter(BaseActivity activity){
        initContext(activity);
        getBondRecord();
    }
    private void getBondRecord(){
        IncomeRecordParam param=new IncomeRecordParam();
        param.setHash(getHashString(IncomeRecordParam.class,param));
        showLoadingDialog();
        ApiClient.create(MerchantApi.class).incomeRecord(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<IncomeRecordTo>>(this) {
                    @Override
                    public void onNext(MessageTo<IncomeRecordTo> msg) {
                        if (msg.getCode()==0){
                            setRecycleList(msg.getData().getLists());
                        }
                    }
                }
        );
    }
}

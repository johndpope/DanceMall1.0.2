package com.hzxmkuar.wumeihui.business.myself.presenter;

import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.MerchantApi;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.merchant.BondRecordTo;
import hzxmkuar.com.applibrary.domain.merchant.ShopIdParam;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/10/16.
 */

public class BondRecordPresenter extends BasePresenter {
    public BondRecordPresenter(BaseActivity activity){
        initContext(activity);
        getBondRecord();
    }
    private void getBondRecord(){
        ShopIdParam param=new ShopIdParam();
        param.setShop_id(activity.getIntent().getIntExtra("ShopId",0));
        param.setHash(getHashString(ShopIdParam.class,param));
        showLoadingDialog();
        ApiClient.create(MerchantApi.class).bondRecord(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<BondRecordTo>>(this) {
                    @Override
                    public void onNext(MessageTo<BondRecordTo> msg) {
                        if (msg.getCode()==0){
                            setRecycleList(msg.getData().getLists());
                        }
                    }
                }
        );
    }
}

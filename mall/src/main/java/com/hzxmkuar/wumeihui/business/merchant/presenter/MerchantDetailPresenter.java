package com.hzxmkuar.wumeihui.business.merchant.presenter;

import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;
import com.hzxmkuar.wumeihui.business.merchant.MerchantDetailActivity;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.MerchantApi;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.merchant.BusUidParam;
import hzxmkuar.com.applibrary.domain.merchant.MerchantCollectTo;
import hzxmkuar.com.applibrary.domain.merchant.MerchantDetailTo;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/25.
 **/

public class MerchantDetailPresenter extends BasePresenter {

    public MerchantDetailPresenter(BaseActivity activity){
        initContext(activity);
        getMerchantDetail();
    }

    private void getMerchantDetail() {
        BusUidParam param=new BusUidParam();
        param.setBus_uid(activity.getIntent().getIntExtra("MerchantId",0));
        param.setHash(getHashString(BusUidParam.class,param));
        ApiClient.create(MerchantApi.class).merchantDetail(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<MerchantDetailTo>>(this) {
                    @Override
                    public void onNext(MessageTo<MerchantDetailTo> msg) {
                      if (msg.getCode()==0)
                          getDataSuccess(msg.getData());

                    }
                }
        );
    }

    public void collectMerchant(){
        BusUidParam param=new BusUidParam();
        param.setBus_uid(activity.getIntent().getIntExtra("MerchantId",0));
        param.setHash(getHashString(BusUidParam.class,param));
        showLoadingDialog();
        ApiClient.create(MerchantApi.class).collectMerchant(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<MerchantCollectTo>>(this) {
                    @Override
                    public void onNext(MessageTo<MerchantCollectTo> msg) {
                        if (msg.getCode()==0){
                            ((MerchantDetailActivity)activity).setCollect(msg.getData());
                        }
                    }
                }
        );

    }


}

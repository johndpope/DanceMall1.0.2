package com.hzxmkuar.wumeihui.business.merchant.presenter;

import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.MerchantApi;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.merchant.BusUidParam;
import hzxmkuar.com.applibrary.domain.merchant.MerchantCaseTo;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/26.
 */

public class MerchantCasePresenter extends BasePresenter {

    public MerchantCasePresenter(BaseActivity activity){
        initContext(activity);
        getCaseList();
    }

    public void getCaseList() {
        BusUidParam param=new BusUidParam();
        param.setBus_uid(activity.getIntent().getIntExtra("MerchantId",0));
        param.setHash(getHashStringNoUser(BusUidParam.class,param));
        showLoadingDialog();
        ApiClient.create(MerchantApi.class).caseList(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<MerchantCaseTo>>(this) {
                    @Override
                    public void onNext(MessageTo<MerchantCaseTo> msg) {
                        if (msg.getCode()==0)
                            setRecycleList(msg.getData().getLists());
                    }
                }
        );
    }
}

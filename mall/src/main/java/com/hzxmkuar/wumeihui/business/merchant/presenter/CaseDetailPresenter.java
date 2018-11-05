package com.hzxmkuar.wumeihui.business.merchant.presenter;

import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.MerchantApi;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.merchant.CaseIdParam;
import hzxmkuar.com.applibrary.domain.merchant.MerchantCaseTo;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/26.
 */

public class CaseDetailPresenter extends BasePresenter {

    public CaseDetailPresenter(BaseActivity activity){
        initContext(activity);
        getCaseDetail();
    }
    private void getCaseDetail(){
        CaseIdParam param=new CaseIdParam();
        param.setCase_id(activity.getIntent().getIntExtra("CaseId",0));
        param.setHash(getHashStringNoUser(CaseIdParam.class,param));
        showLoadingDialog();
        ApiClient.create(MerchantApi.class).caseDetail(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<MerchantCaseTo.ListsBean>>(this) {
                    @Override
                    public void onNext(MessageTo<MerchantCaseTo.ListsBean> msg) {
                        if (msg.getCode()==0)
                            getDataSuccess(msg.getData());
                    }
                }
        );
    }
}

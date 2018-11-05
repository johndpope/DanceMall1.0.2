package com.hzxmkuar.wumeihui.personal.inquiry.presenter;

import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.InquiryApi;
import hzxmkuar.com.applibrary.domain.BaseParam;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.inquery.ConfirmInquiryParam;
import hzxmkuar.com.applibrary.domain.inquery.InquiryTo;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/11.
 */

public class SelectInquiryPresenter extends BasePresenter {

    public SelectInquiryPresenter(BaseActivity activity) {
        initContext(activity);
        getInquiryList();
    }

    private void getInquiryList() {
        BaseParam param = new BaseParam();

        param.setHash(getHashString(BaseParam.class, param));
        showLoadingDialog();
        ApiClient.create(InquiryApi.class).getInquiryList(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<InquiryTo>>(this) {
                    @Override
                    public void onNext(MessageTo<InquiryTo> msg) {
                        if (msg.getCode() == 0)
                            getDataSuccess(msg.getData());
                    }
                }
        );
    }

    public void confirmInquiry(String sid) {
        ConfirmInquiryParam param = new ConfirmInquiryParam();
        param.setHashid(userInfoTo.getHashid());
        param.setUid(userInfoTo.getUid());
        param.setSids(sid);
        param.setHash(getHashString(ConfirmInquiryParam.class, param));
        showLoadingDialog();
        ApiClient.create(InquiryApi.class).confirmInquiry(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo>(this) {
                    @Override
                    public void onNext(MessageTo msg) {
                        if (msg.getCode() == 0)
                            submitDataSuccess(msg.getData());
                    }
                }
        );
    }
}

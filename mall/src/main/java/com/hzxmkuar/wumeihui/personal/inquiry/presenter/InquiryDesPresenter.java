package com.hzxmkuar.wumeihui.personal.inquiry.presenter;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.InquiryApi;
import hzxmkuar.com.applibrary.domain.BaseParam;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.inquery.AddInquiryParam;
import hzxmkuar.com.applibrary.domain.inquery.ConfirmInquiryPageTo;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/11.
 */

public class InquiryDesPresenter extends BasePresenter {
     private int uploadCount;
    public InquiryDesPresenter(BaseActivity activity) {
        initContext(activity);
        getInquiryPage();
    }

    private void getInquiryPage() {
        BaseParam param = new BaseParam();

        param.setHash(getHashString(BaseParam.class, param));
        showLoadingDialog();
        ApiClient.create(InquiryApi.class).confirmInquiryPage(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<ConfirmInquiryPageTo>>(this) {
                    @Override
                    public void onNext(MessageTo<ConfirmInquiryPageTo> msg) {
                        if (msg.getCode() == 0)
                            getDataSuccess(msg.getData());
                    }
                }
        );
    }

    public void addInquiry(String serviceJson,String inquiryBus,String useTime,int contactId,String address,int sType) {
        AddInquiryParam param = new AddInquiryParam();
        param.setHashid(userInfoTo.getHashid());
        param.setUid(userInfoTo.getUid());
        param.setContact_id(contactId);
        param.setStype(sType);
        AddInquiryParam.UseTimeTo timeTo = new AddInquiryParam.UseTimeTo();
        timeTo.setStart_time(useTime.split("-")[0]);
        timeTo.setEnd_time(useTime.split("-")[1]);
        param.setUse_time(JSON.toJSONString(timeTo));
        param.setService_address(TextUtils.isEmpty(address)?"暂无地址":address);
        param.setInquiry_bus(inquiryBus);
        param.setService_list(serviceJson);
        param.setHash(getHashString(AddInquiryParam.class, param));

        System.out.println(JSON.toJSON(param) + "param");
        showLoadingDialog();
        ApiClient.create(InquiryApi.class).addInquiry(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo>(this) {
                    @Override
                    public void onNext(MessageTo msg) {
                    submitDataSuccess(msg);
                    }
                }
        );

    }




}

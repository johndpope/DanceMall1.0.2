package com.hzxmkuar.wumeihui.personal.inquiry.presenter;

import android.os.Handler;

import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;
import com.hzxmkuar.wumeihui.personal.inquiry.InquiryActivity;
import com.hzxmkuar.wumeihui.personal.main.fragment.ResourceFragment;

import hzxmkuar.com.applibrary.IdParam;
import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.InquiryApi;
import hzxmkuar.com.applibrary.api.MerchantApi;
import hzxmkuar.com.applibrary.domain.BaseParam;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.inquery.InquiryInfoParam;
import hzxmkuar.com.applibrary.domain.inquery.InquiryInfoTo;
import hzxmkuar.com.applibrary.domain.inquery.InquiryMerchantParam;
import hzxmkuar.com.applibrary.domain.main.MainMerchantTo;
import hzxmkuar.com.applibrary.domain.merchant.MerchantCityTo;
import hzxmkuar.com.applibrary.domain.merchant.PosCityParam;
import hzxmkuar.com.applibrary.domain.merchant.ServiceListTo;
import hzxmkuar.com.applibrary.main.DemandSearchTo;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/18.
 **/

public class InquiryPresenter extends BasePresenter {


    public InquiryMerchantParam param=new InquiryMerchantParam();

    public InquiryPresenter(BaseActivity activity){
        initContext(activity);

        getInquiryInfo();
        getInquiryMerchant();
        getCityList();
    }

    public void getInquiryInfo(){


         InquiryInfoParam param=new InquiryInfoParam();
        param.setId(activity.getIntent().getIntExtra("InquiryId",0));
        param.setHash(getHashString(InquiryInfoParam.class, param));
        showLoadingDialog();
        ApiClient.create(InquiryApi.class).getInquiryInfo(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<InquiryInfoTo>>(this) {
                    @Override
                    public void onNext(MessageTo<InquiryInfoTo> msg) {
                        if (msg.getCode()==0) {
                            ((InquiryActivity) activity).setView(msg.getData());
                        }
                    }
                }
        );
    }

    public void getInquiryMerchant(){


        param.setId(activity.getIntent().getIntExtra("InquiryId",0));

        param.setHash(getHashString(InquiryMerchantParam.class, param));
        showLoadingDialog();
        ApiClient.create(InquiryApi.class).getInquiryMerchant(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<MainMerchantTo>>(this) {
                    @Override
                    public void onNext(MessageTo<MainMerchantTo> msg) {
                        if (msg.getCode()==0)
                            ((InquiryActivity)activity).setMerchantLayout(msg.getData().getLists());


                    }
                }
        );
    }
    private void getServiceSort() {
        BaseParam param = new BaseParam();
        param.setHash(getHashString(BaseParam.class, param));
        showLoadingDialog();
        ApiClient.create(MerchantApi.class).getServiceList(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<ServiceListTo>>(this) {
                    @Override
                    public void onNext(MessageTo<ServiceListTo> msg) {
                        if (msg.getCode() == 0)
                            ((ResourceFragment) mFragment).setSort(msg.getData().getLists());
                    }
                }
        );
    }

    private void getCityList() {
        PosCityParam param = new PosCityParam();
        param.setPos_city(934);
        param.setHash(getHashString(PosCityParam.class, param));
        showLoadingDialog();
        ApiClient.create(MerchantApi.class).getCityList(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<MerchantCityTo>>(this) {
                    @Override
                    public void onNext(MessageTo<MerchantCityTo> msg) {
                        if (msg.getCode() == 0)
                            ((InquiryActivity) activity).setCityLayout(msg.getData().getLists());
                    }
                }
        );
    }

    public void cancelInquiry() {
        IdParam param = new IdParam();
        param.setId(activity.getIntent().getIntExtra("InquiryId",0));
        param.setHash(getHashString(IdParam.class, param));
        showLoadingDialog();
        ApiClient.create(InquiryApi.class).cancelInquiry(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<DemandSearchTo>>(this) {
                    @Override
                    public void onNext(MessageTo<DemandSearchTo> msg) {
                        if (msg.getCode() == 0) {
                            showMessage("放弃询价成功");
                            new Handler().postDelayed(() -> activity.finish(),2500);
                        }
                    }
                }
        );
    }

}

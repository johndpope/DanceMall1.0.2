package com.hzxmkuar.wumeihui.personal.merchant.presenter;

import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;

import com.hzxmkuar.wumeihui.base.util.SpUtil;
import com.hzxmkuar.wumeihui.personal.order.MerchantListActivity;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.MainApi;
import hzxmkuar.com.applibrary.api.MerchantApi;
import hzxmkuar.com.applibrary.domain.BaseParam;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.main.MainBannerTo;
import hzxmkuar.com.applibrary.domain.main.MainMerchantTo;
import hzxmkuar.com.applibrary.domain.main.MerchantParam;
import hzxmkuar.com.applibrary.domain.main.MerchantTo;
import hzxmkuar.com.applibrary.domain.main.SearchMerchantParam;
import hzxmkuar.com.applibrary.domain.merchant.MerchantCityTo;
import hzxmkuar.com.applibrary.domain.merchant.PosCityParam;
import hzxmkuar.com.applibrary.domain.merchant.ServiceListTo;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/16.
 */

public class MerchantListPresenter extends BasePresenter {

    public MerchantParam param = new MerchantParam();

    public MerchantListPresenter(BaseActivity activity) {
        initContext(activity);

        getMerchant();
        getServiceSort();
        getCityList();
    }


    public void getBanner() {
        BaseParam param = new BaseParam();

        param.setHash(getHashString(BaseParam.class, param));

        showLoadingDialog();
        ApiClient.create(MainApi.class).getMainBanner(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<MainBannerTo>>(this) {
                    @Override
                    public void onNext(MessageTo<MainBannerTo> msg) {
                        getDataSuccess(msg.getData());
                    }
                }
        );
    }

    public void getMerchant() {
        param.setPos_city(SpUtil.getInt("LocateCityId"));
        param.setService_cate(activity.getIntent().getIntExtra("CateId", 0));
        param.setHash(getHashString(MerchantParam.class, param));
        ApiClient.create(MainApi.class).getMerchantList(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<MerchantTo>>(this) {
                    @Override
                    public void onNext(MessageTo<MerchantTo> msg) {
                        if (msg.getCode() == 0)
                            getDataSuccess(msg.getData());

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
                            ((MerchantListActivity) activity).setSort(msg.getData().getLists());
                    }
                }
        );
    }

    private void getCityList() {
        PosCityParam param = new PosCityParam();
        param.setPos_city(SpUtil.getInt("LocateCityId"));
        param.setHash(getHashString(PosCityParam.class, param));
        showLoadingDialog();
        ApiClient.create(MerchantApi.class).getCityList(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<MerchantCityTo>>(this) {
                    @Override
                    public void onNext(MessageTo<MerchantCityTo> msg) {
                        if (msg.getCode() == 0)
                            ((MerchantListActivity) activity).setCityLayout(msg.getData().getLists());
                    }
                }
        );
    }
}

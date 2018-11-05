package com.hzxmkuar.wumeihui.business.merchant.presenter;

import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.MerchantApi;
import hzxmkuar.com.applibrary.domain.BaseParam;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.merchant.ModifyMerchantParam;
import hzxmkuar.com.applibrary.domain.merchant.ShopInfoTo;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/10/12.
 */

public class MerchantInfoPresenter extends BasePresenter {
  public   ModifyMerchantParam param = new ModifyMerchantParam();

    public MerchantInfoPresenter(BaseActivity activity) {
        initContext(activity);
        getShopInfo();
    }

    public void getShopInfo() {
        BaseParam param = new BaseParam();
        param.setHash(getHashString(BaseParam.class, param));
        showLoadingDialog();
        ApiClient.create(MerchantApi.class).shopInfo(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<ShopInfoTo>>(this) {
                    @Override
                    public void onNext(MessageTo<ShopInfoTo> msg) {
                        if (msg.getCode() == 0)
                            getDataSuccess(msg.getData());
                    }
                }
        );
    }

    public void modifyMerchant() {
        param.setHash(getHashString(ModifyMerchantParam.class, param));
        showLoadingDialog();
        ApiClient.create(MerchantApi.class).modifyMerchant(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo>(this) {
                    @Override
                    public void onNext(MessageTo msg) {
                        if (msg.getCode()==0){
                            submitDataSuccess(msg.getData());
                        }
                    }
                }
        );
    }
}

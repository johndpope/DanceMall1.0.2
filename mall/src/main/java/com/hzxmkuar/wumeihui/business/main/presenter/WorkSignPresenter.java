package com.hzxmkuar.wumeihui.business.main.presenter;

import com.hzxmkuar.wumeihui.base.BaseFragment;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;
import com.hzxmkuar.wumeihui.business.main.fragment.WorkFragment;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.UserApi;
import hzxmkuar.com.applibrary.domain.BaseParam;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.merchant.MerchantMySelfTo;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/10/16.
 */

public class WorkSignPresenter extends BasePresenter {

    public WorkSignPresenter(BaseFragment fragment){
        initContext(fragment);
    }
    public void getMyselfInfo(){
        BaseParam param=new BaseParam();
        param.setHash(getHashString(BaseParam.class,param));
        showLoadingDialog();
        ApiClient.create(UserApi.class).merchantMyselfInfo(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(new MyObserver<MessageTo<MerchantMySelfTo>>(this) {
            @Override
            public void onNext(MessageTo<MerchantMySelfTo> msg) {
                if (msg.getCode()==0)
                    ((WorkFragment)mFragment).setMySelfView(msg.getData());
            }
        });
    }
}

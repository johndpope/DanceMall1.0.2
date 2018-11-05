package com.hzxmkuar.wumeihui.personal.integral.presenter;

import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.IntegralApi;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.integral.GoodsDetailTo;
import hzxmkuar.com.applibrary.domain.integral.GoodsIdParam;
import hzxmkuar.com.applibrary.domain.integral.GoodsParam;
import hzxmkuar.com.applibrary.domain.integral.GoodsTo;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/10/13.
 */

public class IntegralDetailPresenter extends BasePresenter {
    public IntegralDetailPresenter(BaseActivity activity){
        initContext(activity);
        getDetail();
    }

    private void getDetail(){
        GoodsIdParam param=new GoodsIdParam();
        param.setGoods_id(activity.getIntent().getIntExtra("GoodsId",0));
        param.setHash(getHashString(GoodsIdParam.class,param));
        ApiClient.create(IntegralApi.class).goodsDetail(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<GoodsDetailTo>>(this) {
                    @Override
                    public void onNext(MessageTo<GoodsDetailTo> msg) {
                        if (msg.getCode()==0){
                            getDataSuccess(msg.getData());
                        }
                    }
                }
        );
    }
}

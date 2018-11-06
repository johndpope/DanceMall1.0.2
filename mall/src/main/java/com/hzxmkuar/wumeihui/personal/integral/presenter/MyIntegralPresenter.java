package com.hzxmkuar.wumeihui.personal.integral.presenter;

import com.hzxmkuar.wumeihui.base.BaseFragment;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.IntegralApi;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.integral.IntegralOrderTo;
import hzxmkuar.com.applibrary.domain.order.OTypeOnPageParam;
import hzxmkuar.com.applibrary.domain.order.OTypeParam;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/28.
 */

public class MyIntegralPresenter extends BasePresenter {
    private  int type;
    public MyIntegralPresenter(BaseFragment fragment,int type){
        initContext(fragment);
        this.type=type;
        getMyIntegral();
    }

    private void getMyIntegral(){

        OTypeOnPageParam param=new OTypeOnPageParam();
        param.setOtype(type);
        param.setHash(getHashString(OTypeOnPageParam.class,param));
        showLoadingDialog();
        ApiClient.create(IntegralApi.class).getMyOrder(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<IntegralOrderTo>>(this) {
                    @Override
                    public void onNext(MessageTo<IntegralOrderTo> msg) {
                        if (msg.getCode()==0)
                            setRecycleList(msg.getData().getLists());
                    }
                }
        );
    }
}

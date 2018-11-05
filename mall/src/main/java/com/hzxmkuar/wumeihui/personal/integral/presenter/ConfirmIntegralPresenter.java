package com.hzxmkuar.wumeihui.personal.integral.presenter;

import android.content.Intent;
import android.os.Handler;

import com.hzxmkuar.wumeihui.base.ActivityManager;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;
import com.hzxmkuar.wumeihui.personal.integral.IntegralOrderActivity;
import com.hzxmkuar.wumeihui.personal.integral.MyIntegralActivity;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.IntegralApi;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.integral.ConfirmIntegralParam;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/28.
 */

public class ConfirmIntegralPresenter extends BasePresenter {

    public ConfirmIntegralPresenter(BaseActivity activity){
        initContext(activity);
    }

    public void confirmOrder(int goodsId,String address,String telePhone,String userName){
        ConfirmIntegralParam param=new ConfirmIntegralParam();
        param.setGoods_id(goodsId);
        param.setAddress(address);
        param.setTelephone(telePhone);
        param.setConsignee(userName);
        param.setHash(getHashString(ConfirmIntegralParam.class,param));
        showLoadingDialog();
        ApiClient.create(IntegralApi.class).submitOrder(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo>(this) {
                    @Override
                    public void onNext(MessageTo msg) {
                        if (msg.getCode()==0) {
                            showMessage("提交订单成功");
                            new Handler().postDelayed(() -> {
                                if (ActivityManager.integrationDetailActivity!=null)
                                    ActivityManager.integrationDetailActivity.finish();
                                Intent intent=new Intent(activity, MyIntegralActivity.class);
                                activity.startActivity(intent);
                                activity.finish();
                            },2500);
                        }
                        else
                            showMessage(msg.getMsg());
                    }
                }
        );
    }
}

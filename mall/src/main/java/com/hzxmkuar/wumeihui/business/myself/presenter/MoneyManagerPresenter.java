package com.hzxmkuar.wumeihui.business.myself.presenter;

import android.content.Intent;
import android.os.Handler;

import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;
import com.hzxmkuar.wumeihui.business.myself.MoneyManagerActivity;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.MerchantApi;
import hzxmkuar.com.applibrary.api.OrderApi;
import hzxmkuar.com.applibrary.domain.BaseParam;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.merchant.MoneyManagerTo;
import hzxmkuar.com.applibrary.domain.order.PutForwardParam;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/10/15.
 */

public class MoneyManagerPresenter extends BasePresenter {

    public MoneyManagerPresenter(BaseActivity activity) {
        initContext(activity);
        getMoneyManager();

    }

    private void getMoneyManager() {
        BaseParam param = new BaseParam();
        param.setHash(getHashString(BaseParam.class, param));
        ApiClient.create(MerchantApi.class).moneyManager(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<MoneyManagerTo>>(this) {
                    @Override
                    public void onNext(MessageTo<MoneyManagerTo> msg) {
                        if (msg.getCode() == 0) {
                            getDataSuccess(msg.getData());
                        }
                    }
                }
        );
    }

    public void putForward(String cardNo, String userName, float money, int type) {
        PutForwardParam param = new PutForwardParam();
        param.setCash_type(type);
        param.setReal_name(userName);
        param.setBank_num(cardNo.replaceAll(" ",""));
        param.setCash_money(money+"");


        param.setHash(getHashString(PutForwardParam.class, param));
        showLoadingDialog();
        ApiClient.create(OrderApi.class).putForword(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(new MyObserver<MessageTo>(this) {
            @Override
            public void onNext(MessageTo msg) {
                if (msg.getCode() == 0) {
                    showMessage("提现申请成功");
                    new Handler().postDelayed(() -> {
                        Intent intent = new Intent(activity, MoneyManagerActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        activity.startActivity(intent);
                        activity.finish();

                    }, 2500);
                }else
                    showMessage(msg.getMsg());
            }
        });
    }
}

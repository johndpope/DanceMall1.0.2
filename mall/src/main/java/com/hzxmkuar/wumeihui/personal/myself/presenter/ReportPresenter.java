package com.hzxmkuar.wumeihui.personal.myself.presenter;

import android.os.Handler;

import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.UserApi;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.user.ReportParam;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/10/20.
 */

public class ReportPresenter extends BasePresenter {

    public ReportPresenter(BaseActivity activity){
        initContext(activity);
    }
    public void report(String content){
        ReportParam param=new ReportParam();
        param.setShop_id(20);
        param.setContent(content);
        param.setHash(getHashString(ReportParam.class,param));
        showLoadingDialog();
        ApiClient.create(UserApi.class).report(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo>(this) {
                    @Override
                    public void onNext(MessageTo msg) {
                        if (msg.getCode()==0){
                            showMessage("举报成功");
                            new Handler().postDelayed(() -> activity.finish(),2500);
                        }
                    }
                }
        );
    }
}

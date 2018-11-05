package com.hzxmkuar.wumeihui.personal.myself.presenter;

import android.os.Handler;

import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.UserApi;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.user.ContentParam;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/10/11.
 */

public class SuggestPresenter extends BasePresenter {

    public SuggestPresenter(BaseActivity activity){
        initContext(activity);
    }
    public void suggest(String content){
        ContentParam param=new ContentParam();
        param.setContent(content);
        param.setHash(getHashString(ContentParam.class,param));
        showLoadingDialog();
        ApiClient.create(UserApi.class).suggest(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo>(this) {
                    @Override
                    public void onNext(MessageTo msg) {
                        if (msg.getCode()==0) {
                            showMessage("建议成功");
                            new Handler().postDelayed(() ->activity.finish() ,2500);
                        }
                    }
                }
        );
    }
}

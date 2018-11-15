package com.hzxmkuar.wumeihui.personal.inquiry.presenter;

import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.InquiryApi;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.inquery.KeyWordParam;
import hzxmkuar.com.applibrary.main.DemandSearchTo;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/26.
 */

public class SearchServicePresenter extends BasePresenter {

    public SearchServicePresenter(BaseActivity activity){
        initContext(activity);
        getSearchService(activity.getIntent().getStringExtra("Search"));

    }

    public void getSearchService(String search){
        KeyWordParam param=new KeyWordParam();
        param.setKeyword(search);
        param.setHash(getHashString(KeyWordParam.class,param));
        ApiClient.create(InquiryApi.class).getSearchList(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<DemandSearchTo>>(this) {
                    @Override
                    public void onNext(MessageTo<DemandSearchTo> msg) {
                       if (msg.getCode()==0)
                           setRecycleList(msg.getData().getLists());
                    }
                }
        );
    }
}

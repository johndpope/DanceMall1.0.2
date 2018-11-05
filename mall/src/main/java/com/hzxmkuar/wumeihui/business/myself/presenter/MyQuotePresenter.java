package com.hzxmkuar.wumeihui.business.myself.presenter;

import com.hzxmkuar.wumeihui.base.BaseFragment;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;

import hzxmkuar.com.applibrary.IdParam;
import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.InquiryApi;
import hzxmkuar.com.applibrary.api.QuoteApi;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.inquery.MyInquiryParam;
import hzxmkuar.com.applibrary.domain.inquery.MyInquiryTo;
import hzxmkuar.com.applibrary.domain.quote.MyQuoteTo;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/18.
 */

public class MyQuotePresenter extends BasePresenter {
    private int type;

    public MyQuotePresenter(BaseFragment fragment,int type) {
        initContext(fragment);
        this.type=type;
        getMyQuote();
    }

    public void getMyQuote() {
        MyInquiryParam param = new MyInquiryParam();
        param.setDtype(type);
        param.setPage(recyclePageIndex);
        param.setHash(getHashString(MyInquiryParam.class, param));
        showLoadingDialog();
        ApiClient.create(QuoteApi.class).getMyQuote(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<MyQuoteTo>>(this) {
                    @Override
                    public void onNext(MessageTo<MyQuoteTo> msg) {
                        if (msg.getCode() == 0)
                            setRecycleList(msg.getData().getLists());
                    }
                }
        );
    }

    public void cancelQuote(int id) {
        IdParam param = new IdParam();
        param.setId(id);
        param.setHash(getHashString(IdParam.class, param));
        showLoadingDialog();
        ApiClient.create(QuoteApi.class).cancelQuote(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo>(this) {
                    @Override
                    public void onNext(MessageTo msg) {
                        if (msg.getCode() == 0) {
                            getMyQuote();
                            showMessage("放弃报价成功");
                        }
                    }
                }
        );
    }

    @Override
    public void recycleViewRefresh() {
        super.recycleViewRefresh();
        getMyQuote();
    }

    @Override
    public void recycleViewLoadMore() {
        super.recycleViewLoadMore();
        getMyQuote();
    }
}

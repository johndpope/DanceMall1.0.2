package com.hzxmkuar.wumeihui.personal.myself.presenter;

import com.hzxmkuar.wumeihui.base.BaseFragment;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;

import hzxmkuar.com.applibrary.IdParam;
import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.InquiryApi;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.inquery.ConfirmInquiryParam;
import hzxmkuar.com.applibrary.domain.inquery.MyInquiryParam;
import hzxmkuar.com.applibrary.domain.inquery.MyInquiryTo;
import hzxmkuar.com.applibrary.main.DemandSearchTo;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/18.
 */

public class MyInquiryPresenter extends BasePresenter {

    private int type;

    public MyInquiryPresenter(BaseFragment fragment, int type) {
        initContext(fragment);
        this.type = type;
        getMyInquiry();
    }

    public void getMyInquiry() {
        MyInquiryParam param = new MyInquiryParam();
        param.setDtype(type);
        param.setPage(recyclePageIndex);
        param.setHash(getHashString(MyInquiryParam.class, param));
        showLoadingDialog();
        ApiClient.create(InquiryApi.class).getMyInquiry(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<MyInquiryTo>>(this) {
                    @Override
                    public void onNext(MessageTo<MyInquiryTo> msg) {
                        if (msg.getCode() == 0)
                            setRecycleList(msg.getData().getLists());
                    }
                }
        );
    }

    @Override
    public void recycleViewRefresh() {
        super.recycleViewRefresh();
        getMyInquiry();
    }

    public void confirmInquiry(String sid) {
        ConfirmInquiryParam param = new ConfirmInquiryParam();
        param.setHashid(userInfoTo.getHashid());
        param.setUid(userInfoTo.getUid());
        param.setSids(sid);
        param.setHash(getHashString(ConfirmInquiryParam.class, param));
        showLoadingDialog();
        ApiClient.create(InquiryApi.class).confirmInquiry(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo>(this) {
                    @Override
                    public void onNext(MessageTo msg) {
                        if (msg.getCode() == 0)
                            submitDataSuccess(msg.getData());
                    }
                }
        );
    }

    public void cancelInquiry(int id) {
        IdParam param = new IdParam();
        param.setId(id);
        param.setHash(getHashString(IdParam.class, param));
        showLoadingDialog();
        ApiClient.create(InquiryApi.class).cancelInquiry(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<DemandSearchTo>>(this) {
                    @Override
                    public void onNext(MessageTo<DemandSearchTo> msg) {
                        if (msg.getCode() == 0) {
                            showMessage("放弃询价成功");
                            getMyInquiry();
                        }
                    }
                }
        );
    }

    @Override
    public void recyclerViewLoadMore() {
        super.recyclerViewLoadMore();
        getMyInquiry();
    }

    @Override
    public void recyclerViewRefresh() {
        super.recyclerViewRefresh();
        getMyInquiry();
    }
}

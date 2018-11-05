package com.hzxmkuar.wumeihui.personal.integral.presenter;

import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;

import java.util.ArrayList;
import java.util.List;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.IntegralApi;
import hzxmkuar.com.applibrary.domain.BaseParam;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.integral.GoodsParam;
import hzxmkuar.com.applibrary.domain.integral.GoodsTo;
import hzxmkuar.com.applibrary.domain.integral.IntegralInfoTo;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/28.
 */

public class GoodsPresenter extends BasePresenter {
    private List<GoodsTo.ListsBean> goodsList = new ArrayList<>();

    public GoodsPresenter(BaseActivity activity) {
        initContext(activity);
        getGoodsList();
        getIntegralCount();
    }

    public void getGoodsList() {
        GoodsParam param = new GoodsParam();
        param.setPage(recyclePageIndex);
        param.setSearch("");
        param.setHash(getHashString(GoodsParam.class, param));
        showLoadingDialog();
        ApiClient.create(IntegralApi.class).getGoodsList(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<GoodsTo>>(this) {
                    @Override
                    public void onNext(MessageTo<GoodsTo> msg) {
                        if (msg.getCode() == 0) {
                            if (recyclePageIndex == 1)
                                goodsList.clear();
                            goodsList.addAll(msg.getData().getLists());
                            setRecycleList(goodsList);
                        }

                    }
                }
        );
    }

    private void getIntegralCount() {
        BaseParam param = new BaseParam();
        param.setHash(getHashString(BaseParam.class, param));
        showLoadingDialog();
        ApiClient.create(IntegralApi.class).myIntegralCount(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<IntegralInfoTo>>(this) {
                    @Override
                    public void onNext(MessageTo<IntegralInfoTo> msg) {
                        if (msg.getCode() == 0)
                            submitDataSuccess(msg.getData());
                    }
                }
        );
    }

    @Override
    public void recycleViewLoadMore() {
        super.recycleViewLoadMore();
        getGoodsList();
    }

    @Override
    public void recycleViewRefresh() {
        super.recycleViewRefresh();
        getGoodsList();
    }
}

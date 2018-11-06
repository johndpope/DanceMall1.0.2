package com.hzxmkuar.wumeihui.personal.myself.presenter;

import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;

import java.util.ArrayList;
import java.util.List;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.MerchantApi;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.integral.PageParam;
import hzxmkuar.com.applibrary.domain.main.MainMerchantTo;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/10/15.
 */

public class CollectMerchantPresenter extends BasePresenter {
    private List<MainMerchantTo.ListsBean> merchantList=new ArrayList<>();
    public CollectMerchantPresenter(BaseActivity activity){
        initContext(activity);
        getMerchantList();
    }

    private void getMerchantList(){
        PageParam param=new PageParam();
        param.setPage(recyclePageIndex);
        param.setHash(getHashString(PageParam.class,param));
        showLoadingDialog();
        ApiClient.create(MerchantApi.class).collectMerchantList(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<MainMerchantTo>>(this) {
                    @Override
                    public void onNext(MessageTo<MainMerchantTo> msg) {
                        if (msg.getCode()==0) {
                            if (recyclePageIndex==1)
                                merchantList.clear();
                            if (msg.getData().getLists()!=null)
                            merchantList.addAll(msg.getData().getLists());
                            setRecycleList(merchantList);
                        }
                    }
                }
        );
    }

    @Override
    public void recycleViewLoadMore() {
        super.recycleViewLoadMore();
        getMerchantList();
    }

    @Override
    public void recycleViewRefresh() {
        super.recycleViewRefresh();
        getMerchantList();
    }
}

package com.hzxmkuar.wumeihui.message.presenter;

import android.text.TextUtils;

import com.hzxmkuar.wumeihui.base.BaseFragment;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;

import java.util.ArrayList;
import java.util.List;

import hzxmkuar.com.applibrary.IdParam;
import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.MessageApi;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.message.IdsParam;
import hzxmkuar.com.applibrary.domain.message.SystemMessageParam;
import hzxmkuar.com.applibrary.domain.message.SystemMessageTo;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/11.
 */

public class SystemMessagePresenter extends BasePresenter {
    public List<SystemMessageTo.ListsBean> messageList = new ArrayList<>();
    private String selectId;

    public SystemMessagePresenter(BaseFragment fragment) {
        initContext(fragment);
        getSystemMessage();
    }

    public void getSystemMessage() {
        SystemMessageParam param = new SystemMessageParam();
        param.setPage(recyclePageIndex);
        param.setHash(getHashString(SystemMessageParam.class, param));
        ApiClient.create(MessageApi.class).getSystemMessageList(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<SystemMessageTo>>(this) {
                    @Override
                    public void onNext(MessageTo<SystemMessageTo> msg) {
                        if (msg.getCode() == 0) {
                            if (recyclePageIndex == 1)
                                messageList.clear();
                            if (msg.getData().getLists() != null)
                                messageList.addAll(msg.getData().getLists());
                            setRecycleList(messageList);
                        }
                    }
                }
        );
    }

    @Override
    public void recyclerViewRefresh() {
        super.recyclerViewRefresh();
        getSystemMessage();
    }

    @Override
    public void recycleViewLoadMore() {
        super.recycleViewLoadMore();
        getSystemMessage();
    }

    public void setFinishDelete() {
        selectId = "";
        IdsParam param = new IdsParam();
        Observable.from(messageList).filter(SystemMessageTo.ListsBean::isSelect).subscribe(listsBean -> selectId = selectId + listsBean.getId() + ",");

        if (TextUtils.isEmpty(selectId)) {
            showMessage("请选择要删除的消息");
            return;
        }
        param.setIds(selectId.substring(0, selectId.length() - 1));
        param.setHash(getHashString(IdsParam.class,param));
        showLoadingDialog();
        ApiClient.create(MessageApi.class).deleteMessage(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo>(this) {
                    @Override
                    public void onNext(MessageTo msg) {
                        if (msg.getCode()==0){
                            recyclePageIndex=1;
                            getSystemMessage();
                        }else
                            showMessage(msg.getMsg());
                    }
                }
        );
    }
}

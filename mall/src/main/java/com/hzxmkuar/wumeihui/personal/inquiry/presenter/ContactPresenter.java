package com.hzxmkuar.wumeihui.personal.inquiry.presenter;

import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.InquiryApi;
import hzxmkuar.com.applibrary.domain.BaseParam;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.inquery.AddContactParam;
import hzxmkuar.com.applibrary.domain.inquery.ContactPeopleTo;
import hzxmkuar.com.applibrary.domain.inquery.DeleteContactParam;
import hzxmkuar.com.applibrary.domain.inquery.EditContactParam;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/11.
 */

public class ContactPresenter extends BasePresenter {

    public ContactPresenter(BaseActivity activity) {
        initContext(activity);

    }

    public void getContactList() {
        BaseParam param = new BaseParam();

        param.setHash(getHashString(BaseParam.class, param));
        ApiClient.create(InquiryApi.class).getcontactList(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<ContactPeopleTo>>(this) {
                    @Override
                    public void onNext(MessageTo<ContactPeopleTo> msg) {
                        if (msg.getCode() == 0) {
                            getDataSuccess(msg.getData());
                            setRecycleList(msg.getData().getLists());

                        }
                    }
                }
        );
    }

    public void addContact(String name, String phone) {
        AddContactParam param = new AddContactParam();

        param.setConsignee(name);
        param.setTelephone(phone);
        param.setHash(getHashString(AddContactParam.class, param));
        showLoadingDialog();
        ApiClient.create(InquiryApi.class).addContact(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo>(this) {
                    @Override
                    public void onNext(MessageTo msg) {
                        if (msg.getCode() == 0)
                            getDataSuccess(msg.getData());
                    }
                }
        );
    }

    public void deleteContact(int id) {
        DeleteContactParam param = new DeleteContactParam();
        param.setHashid(userInfoTo.getHashid());
        param.setUid(userInfoTo.getUid());
        param.setId(id);
        param.setHash(getHashString(DeleteContactParam.class, param));
        showLoadingDialog();
        ApiClient.create(InquiryApi.class).deleteContact(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo>(this) {
                    @Override
                    public void onNext(MessageTo msg) {
                        if (msg.getCode() == 0)
                          getContactList();
                    }
                }
        );
    }

    public void setDefault(int id) {
        DeleteContactParam param = new DeleteContactParam();
        param.setHashid(userInfoTo.getHashid());
        param.setUid(userInfoTo.getUid());
        param.setId(id);
        param.setHash(getHashString(DeleteContactParam.class, param));
        showLoadingDialog();
        ApiClient.create(InquiryApi.class).setDefaultContact(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo>(this) {
                    @Override
                    public void onNext(MessageTo msg) {
                        if (msg.getCode() == 0)
                            getContactList();
                    }
                }
        );
    }

    public void editContact(String name, String phone,int id) {
        EditContactParam param = new EditContactParam();
        param.setConsignee(name);
        param.setId(id);
        param.setTelephone(phone);
        param.setHash(getHashString(EditContactParam.class, param));
        showLoadingDialog();
        ApiClient.create(InquiryApi.class).editContact(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo>(this) {
                    @Override
                    public void onNext(MessageTo msg) {
                        if (msg.getCode() == 0)
                            getDataSuccess(msg.getData());
                    }
                }
        );
    }

    @Override
    public void recyclerViewRefresh() {
        super.recyclerViewRefresh();
        getContactList();
    }
}

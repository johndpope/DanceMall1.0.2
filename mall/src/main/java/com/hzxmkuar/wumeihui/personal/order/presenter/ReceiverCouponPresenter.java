package com.hzxmkuar.wumeihui.personal.order.presenter;

import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;

import java.util.ArrayList;
import java.util.List;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.OrderApi;
import hzxmkuar.com.applibrary.domain.BaseParam;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.order.CateIdParam;
import hzxmkuar.com.applibrary.domain.order.SelectCouponParam;
import hzxmkuar.com.applibrary.domain.order.SelectCouponTo;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/29.
 */

public class ReceiverCouponPresenter extends BasePresenter {
    private List<SelectCouponTo.ListsBean>couponList=new ArrayList<>();
    public ReceiverCouponPresenter(BaseActivity activity){
        initContext(activity);
        getCoupon();
    }
    private void getCoupon(){

        BaseParam param=new BaseParam();
        param.setHash(getHashString(BaseParam.class,param));
        ApiClient.create(OrderApi.class).receiverCouponList(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<SelectCouponTo>>(this) {
                    @Override
                    public void onNext(MessageTo<SelectCouponTo> msg) {
                        if (msg.getCode()==0) {
                            if (msg.getData().getLists()!=null)
                                Observable.from(msg.getData().getLists()).filter(listsBean -> listsBean.getGet_status()==1).subscribe(listsBean -> couponList.add(listsBean));
                            setRecycleList(couponList);
                        }
                    }
                }
        );
    }

    public void receiverCoupon(int cateId,int position){

        CateIdParam param=new CateIdParam();
        param.setCate_id(cateId);
        param.setHash(getHashString(CateIdParam.class,param));
        ApiClient.create(OrderApi.class).receiverCoupon(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo>(this) {
                    @Override
                    public void onNext(MessageTo msg) {
                        if (msg.getCode()==0){
                            couponList.remove(position);
                            activity.baseAdapter.notifyDataSetChanged();
                            showMessage("领取优惠券成功");
                        }

                    }
                }
        );
    }
}

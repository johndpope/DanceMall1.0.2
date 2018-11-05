package com.hzxmkuar.wumeihui.personal.order.presenter;

import android.content.Intent;
import android.os.Handler;

import com.hzxmkuar.wumeihui.MainApp;
import com.hzxmkuar.wumeihui.base.ActivityManager;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;
import com.hzxmkuar.wumeihui.base.impl.UploadImageListener;
import com.hzxmkuar.wumeihui.base.impl.UploadImageModel;
import com.hzxmkuar.wumeihui.personal.myself.MyOrderActivity;

import java.util.ArrayList;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.OrderApi;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.order.EvaluateParam;
import hzxmkuar.com.applibrary.domain.order.MerchantOrderDetailTo;
import hzxmkuar.com.applibrary.domain.order.OrderIdParam;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/10/10.
 */

public class EvaluatePresenter extends BasePresenter implements UploadImageListener{

    private EvaluateParam param;

    public EvaluatePresenter(BaseActivity activity){
        initContext(activity);
        getOrderDetail();
    }

    private void getOrderDetail(){
        OrderIdParam param=new OrderIdParam();
        param.setOrder_id(activity.getIntent().getIntExtra("OrderId",0));
        param.setHash(getHashString(OrderIdParam.class,param));
        showLoadingDialog();
        ApiClient.create(OrderApi.class).userOrderDetail(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<MerchantOrderDetailTo>>(this) {
                    @Override
                    public void onNext(MessageTo<MerchantOrderDetailTo> msg) {
                        if (msg.getCode()==0)
                            getDataSuccess(msg.getData());
                    }
                }
        );
    }

    public void addEvaluate(String content, String star, ArrayList<String>imageList){
        param = new EvaluateParam();
        param.setContent(content);
        param.setOrder_id(activity.getIntent().getIntExtra("OrderId",0));
        param.setStars(star);
        param.setPics("");
        if (imageList!=null&&imageList.size()>0) {
            showLoadingDialog();
            new UploadImageModel().uploadImage(imageList, this);
        }else
            addEvaluateData();


    }


    @Override
    public void uploadImageSuccess(String path) {
        dismissLoadingDialog();
        param.setPics(path);
        addEvaluateData();
    }

    private void addEvaluateData(){
        showLoadingDialog();
        param.setHash(getHashString(EvaluateParam.class, param));
        ApiClient.create(OrderApi.class).evaluate(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo>(this) {
                    @Override
                    public void onNext(MessageTo msg) {
                        if (msg.getCode()==0) {
                            showMessage("评价成功");
                            new Handler().postDelayed(() -> {
                                if (ActivityManager.personOrderActivity!=null)
                                    ActivityManager.personOrderActivity.finish();
                                if (ActivityManager.myOrderActivity!=null)
                                    ActivityManager.myOrderActivity.finish();

                                Intent intent=new Intent(MainApp.appContext, MyOrderActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                activity.startActivity(intent);
                                activity.finish();

                            },2000);
                        }
                    }
                }
        );
    }
}

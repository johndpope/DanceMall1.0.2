package com.hzxmkuar.wumeihui.personal.merchant.presenter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hzxmkuar.wumeihui.MainApp;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;
import com.hzxmkuar.wumeihui.base.impl.UploadImageListener;
import com.hzxmkuar.wumeihui.base.impl.UploadImageModel;

import java.io.File;
import java.util.ArrayList;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.LoginApi;
import hzxmkuar.com.applibrary.api.MerchantApi;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.login.UploadImageParam;
import hzxmkuar.com.applibrary.domain.main.UploadImageTo;
import hzxmkuar.com.applibrary.domain.merchant.MerchantEnterParam;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/20.
 */

public class MerchantEnterPresenter extends BasePresenter  {

    public   MerchantEnterParam enterParam;

    public MerchantEnterPresenter(BaseActivity activity){
        initContext(activity);
        enterParam= (MerchantEnterParam) activity.getIntent().getSerializableExtra("EnterParam");
        if (enterParam==null)
        enterParam = new MerchantEnterParam();
    }








    public void merchantEnter(){
        enterParam.setHash(getHashString(MerchantEnterParam.class,enterParam));
        showLoadingDialog();
        ApiClient.create(MerchantApi.class).merchantEnter(enterParam).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo>(this) {
                    @Override
                    public void onNext(MessageTo msg) {
                        if (msg.getCode()==0) {
                            showMessage("上传资料成功");
                            submitDataSuccess(msg.getData());
                        }
                    }
                }
        );
    }
}

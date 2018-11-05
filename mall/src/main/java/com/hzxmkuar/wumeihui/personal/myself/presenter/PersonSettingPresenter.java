package com.hzxmkuar.wumeihui.personal.myself.presenter;

import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;
import com.hzxmkuar.wumeihui.base.impl.UploadImageListener;
import com.hzxmkuar.wumeihui.base.impl.UploadImageModel;
import com.hzxmkuar.wumeihui.base.impl.UploadImagePathListener;
import com.hzxmkuar.wumeihui.personal.myself.PersonSettingActivity;

import java.io.File;
import java.util.ArrayList;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.LoginApi;
import hzxmkuar.com.applibrary.api.UserApi;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.login.UploadImageParam;
import hzxmkuar.com.applibrary.domain.main.UploadImageTo;
import hzxmkuar.com.applibrary.domain.user.AddUserInfoParam;
import hzxmkuar.com.applibrary.domain.user.ModifyImageParam;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/17.
 **/

public class PersonSettingPresenter extends BasePresenter implements UploadImagePathListener {

    private final UploadImageModel model;

    public PersonSettingPresenter(BaseActivity activity){
        initContext(activity);
        model = new UploadImageModel();
        addUserInfo();
    }

    private void addUserInfo() {
        if (userInfoTo.getFace()==0){
            AddUserInfoParam param=new AddUserInfoParam();
            param.setFace((int)((Math.random()*9+1)*100000));
            param.setNickname(userInfoTo.getUsername());
            param.setHash(getHashString(AddUserInfoParam.class,param));
            ApiClient.create(UserApi.class).setUserInfo(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                    new MyObserver<MessageTo>(this) {
                        @Override
                        public void onNext(MessageTo msg) {

                        }
                    }
            );
        }
    }

    public void modifyImage(String imagePaths){
        ArrayList<String>imageList=new ArrayList<>();
        imageList.add(imagePaths);
        UploadImageModel model=new UploadImageModel();
        model.uploadImage(imageList,this);


    }

    @Override
    public void uploadImageSuccess(String path,String key) {

        ModifyImageParam param=new ModifyImageParam();
        param.setFace(key);
        param.setHash(getHashString(ModifyImageParam.class,param));

        showLoadingDialog();
        ApiClient.create(UserApi.class).modifyHead(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo>(this) {
                    @Override
                    public void onNext(MessageTo msg) {
                        ((PersonSettingActivity)activity).uploadImageSuccess(path);
                    }
                }
        );
    }
}

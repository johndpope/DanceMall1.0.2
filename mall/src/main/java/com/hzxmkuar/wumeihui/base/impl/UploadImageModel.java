package com.hzxmkuar.wumeihui.base.impl;

import android.graphics.Bitmap;
import android.os.Environment;


import com.alibaba.fastjson.JSON;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.LoginApi;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.login.UploadImageParam;
import hzxmkuar.com.applibrary.domain.main.UploadImageTo;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import util.ImageCompressUtil;

/**
 * Created by xzz on 2018/5/17.
 **/

public class UploadImageModel extends BasePresenter {

    private ArrayList<String> imagePaths;
    private int uploadCount = 0;
    private UploadImageListener listener;
    private String pathKey;
    private String pathString;
    private List<String> keyRecordList = new ArrayList<>();


    private byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }


    public void getToken(ArrayList<String> imagePaths) {
        userInfoTo = userInfoHelp.getUserInfo();
        keyRecordList.clear();
        File file = new File(Environment.getExternalStorageDirectory() + "/test3.jpg");//访问手机端的文件资源，保证手机端sdcdrd中必须有这个文件
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

// MultipartBody.Part  和后端约定好Key，这里的partName是用image
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("fileName", file.getName(), requestFile);

// 添加描述
        String descriptionString = "hello, 这是文件描述";
        RequestBody description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), descriptionString);

        UploadImageParam param = new UploadImageParam();
        param.setUid(userInfoTo.getUid());
        param.setHashid(userInfoTo.getHashid());
        param.setFileName("fileName");
        param.setTags("face");
        param.setHash(getHashString(UploadImageParam.class, param));

        RequestBody time = RequestBody.create(MediaType.parse("text/plain"), param.getTime() + "");
        RequestBody hash = RequestBody.create(MediaType.parse("text/plain"), param.getHash() + "");
        RequestBody apiId = RequestBody.create(MediaType.parse("text/plain"), param.getApiId() + "");
        RequestBody terminal = RequestBody.create(MediaType.parse("text/plain"), param.getTerminal() + "");
        RequestBody uid = RequestBody.create(MediaType.parse("text/plain"), param.getUid() + "");
        RequestBody hashId = RequestBody.create(MediaType.parse("text/plain"), param.getHashid() + "");
        RequestBody fileName = RequestBody.create(MediaType.parse("text/plain"), param.getFileName() + "");
        RequestBody face = RequestBody.create(MediaType.parse("text/plain"), param.getTags() + "");


        ApiClient.create(LoginApi.class).uploadImage(time, hash, apiId, terminal, uid, hashId, fileName, face, description, body).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo>(this) {
                    @Override
                    public void onNext(MessageTo msg) {
                        System.out.println(msg + "msg");
                    }


                }

        );
    }

    public void uploadImage(ArrayList<String> imagePathList, UploadImagePathListener listener) {
        uploadCount = 0;
        pathKey = "";
        pathString = "";
        userInfoTo = userInfoHelp.getUserInfo();
        for (int i = 0; i < imagePathList.size(); i++) {
            String imagePath = imagePathList.get(i);
            keyRecordList.clear();
            File file = saveBitmapFile(ImageCompressUtil.compressPixel(imagePath),imagePath);//访问手机端的文件资源，保证手机端sdcdrd中必须有这个文件
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

            MultipartBody.Part body = MultipartBody.Part.createFormData("fileName", file.getName(), requestFile);

            String descriptionString = "hello, 这是文件描述";
            RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
            UploadImageParam param = new UploadImageParam();
            param.setUid(userInfoTo.getUid());
            param.setHashid(userInfoTo.getHashid());
            param.setFileName("fileName");
            param.setTags("face");
            param.setHash(getHashString(UploadImageParam.class, param));

            RequestBody time = RequestBody.create(MediaType.parse("text/plain"), param.getTime() + "");
            RequestBody hash = RequestBody.create(MediaType.parse("text/plain"), param.getHash() + "");
            RequestBody apiId = RequestBody.create(MediaType.parse("text/plain"), param.getApiId() + "");
            RequestBody terminal = RequestBody.create(MediaType.parse("text/plain"), param.getTerminal() + "");
            RequestBody uid = RequestBody.create(MediaType.parse("text/plain"), param.getUid() + "");
            RequestBody hashId = RequestBody.create(MediaType.parse("text/plain"), param.getHashid() + "");
            RequestBody fileName = RequestBody.create(MediaType.parse("text/plain"), param.getFileName() + "");
            RequestBody face = RequestBody.create(MediaType.parse("text/plain"), param.getTags() + "");


            ApiClient.create(LoginApi.class).uploadImage(time, hash, apiId, terminal, uid, hashId, fileName, face, description, body).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                    new MyObserver<MessageTo<UploadImageTo>>(this) {
                        @Override
                        public void onNext(MessageTo<UploadImageTo> msg) {
                            if (msg.getCode() == 0) {
                                uploadCount++;
                                pathKey = pathKey + msg.getData().getId() + ",";
                                pathString=pathString+ msg.getData().getUrl() + ",";
                                if (uploadCount == imagePathList.size()) {
                                    listener.uploadImageSuccess(pathString.substring(0,pathString.length()-1),pathKey.substring(0, pathKey.length() - 1));
                                }
                            }
                        }


                    }

            );
        }
    }
    public void uploadImage(ArrayList<String> imagePathList, UploadImageListener listener) {
        uploadCount = 0;
        pathKey = "";
        userInfoTo = userInfoHelp.getUserInfo();
        for (int i = 0; i < imagePathList.size(); i++) {
            String imagePath = imagePathList.get(i);
            keyRecordList.clear();
            File file = saveBitmapFile(ImageCompressUtil.compressPixel(imagePath),imagePath);//访问手机端的文件资源，保证手机端sdcdrd中必须有这个文件
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

            MultipartBody.Part body = MultipartBody.Part.createFormData("fileName", file.getName(), requestFile);

            String descriptionString = "hello, 这是文件描述";
            RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
            UploadImageParam param = new UploadImageParam();
            param.setUid(userInfoTo.getUid());
            param.setHashid(userInfoTo.getHashid());
            param.setFileName("fileName");
            param.setTags("face");
            param.setHash(getHashString(UploadImageParam.class, param));

            RequestBody time = RequestBody.create(MediaType.parse("text/plain"), param.getTime() + "");
            RequestBody hash = RequestBody.create(MediaType.parse("text/plain"), param.getHash() + "");
            RequestBody apiId = RequestBody.create(MediaType.parse("text/plain"), param.getApiId() + "");
            RequestBody terminal = RequestBody.create(MediaType.parse("text/plain"), param.getTerminal() + "");
            RequestBody uid = RequestBody.create(MediaType.parse("text/plain"), param.getUid() + "");
            RequestBody hashId = RequestBody.create(MediaType.parse("text/plain"), param.getHashid() + "");
            RequestBody fileName = RequestBody.create(MediaType.parse("text/plain"), param.getFileName() + "");
            RequestBody face = RequestBody.create(MediaType.parse("text/plain"), param.getTags() + "");


            ApiClient.create(LoginApi.class).uploadImage(time, hash, apiId, terminal, uid, hashId, fileName, face, description, body).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                    new MyObserver<MessageTo<UploadImageTo>>(this) {
                        @Override
                        public void onNext(MessageTo<UploadImageTo> msg) {
                            if (msg.getCode() == 0) {
                                uploadCount++;
                                pathKey = pathKey + msg.getData().getId() + ",";
                                if (uploadCount == imagePathList.size()) {
                                    listener.uploadImageSuccess(pathKey.substring(0, pathKey.length() - 1));
                                }
                            }
                        }


                    }

            );
        }
    }

    public void uploadImage(String imagePath, UploadImageIdListener listener) {
        uploadCount = 0;
        pathKey = "";
        userInfoTo = userInfoHelp.getUserInfo();

        keyRecordList.clear();
        File file = saveBitmapFile(ImageCompressUtil.compressPixel(imagePath),imagePath);//访问手机端的文件资源，保证手机端sdcdrd中必须有这个文件
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("fileName", file.getName(), requestFile);

        String descriptionString = "hello, 这是文件描述";
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
        UploadImageParam param = new UploadImageParam();
        param.setUid(userInfoTo.getUid());
        param.setHashid(userInfoTo.getHashid());
        param.setFileName("fileName");
        param.setTags("face");
        param.setHash(getHashString(UploadImageParam.class, param));

        RequestBody time = RequestBody.create(MediaType.parse("text/plain"), param.getTime() + "");
        RequestBody hash = RequestBody.create(MediaType.parse("text/plain"), param.getHash() + "");
        RequestBody apiId = RequestBody.create(MediaType.parse("text/plain"), param.getApiId() + "");
        RequestBody terminal = RequestBody.create(MediaType.parse("text/plain"), param.getTerminal() + "");
        RequestBody uid = RequestBody.create(MediaType.parse("text/plain"), param.getUid() + "");
        RequestBody hashId = RequestBody.create(MediaType.parse("text/plain"), param.getHashid() + "");
        RequestBody fileName = RequestBody.create(MediaType.parse("text/plain"), param.getFileName() + "");
        RequestBody face = RequestBody.create(MediaType.parse("text/plain"), param.getTags() + "");


        ApiClient.create(LoginApi.class).uploadImage(time, hash, apiId, terminal, uid, hashId, fileName, face, description, body).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<UploadImageTo>>(this) {
                    @Override
                    public void onNext(MessageTo<UploadImageTo> msg) {
                        if (msg.getCode() == 0) {

                            listener.uploadImageSuccess(msg.getData().getUrl(), msg.getData().getId());
                        }
                    }


                }

        );

    }

    public static File saveBitmapFile(Bitmap bitmap, String filepath) {
        File file = new File(filepath);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


}

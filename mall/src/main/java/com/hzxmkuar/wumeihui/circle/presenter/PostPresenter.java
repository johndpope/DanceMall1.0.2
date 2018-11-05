package com.hzxmkuar.wumeihui.circle.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;
import com.hzxmkuar.wumeihui.base.impl.UploadImageListener;
import com.hzxmkuar.wumeihui.base.impl.UploadImageModel;
import com.hzxmkuar.wumeihui.circle.PostActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.CircleApi;
import hzxmkuar.com.applibrary.domain.BaseParam;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.circle.AddPostParam;
import hzxmkuar.com.applibrary.domain.circle.DaysParam;
import hzxmkuar.com.applibrary.domain.circle.TopicTo;
import hzxmkuar.com.applibrary.domain.login.CityTo;
import hzxmkuar.com.applibrary.domain.order.OrderSettleInfoTo;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/16.
 **/

public class PostPresenter extends BasePresenter implements UploadImageListener {

    private AddPostParam param;
    private List<CityTo> cityList;

    public PostPresenter(BaseActivity activity) {
        initContext(activity);
        new Thread(this::initJson).start();
    }

    public void getTopic() {
        BaseParam param = new BaseParam();

        param.setHash(getHashString(BaseParam.class, param));
        showLoadingDialog();
        ApiClient.create(CircleApi.class).getTopicList(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<TopicTo>>(this) {
                    @Override
                    public void onNext(MessageTo<TopicTo> msg) {

                        if (msg.getCode() == 0) {
                            String[] left = new String[msg.getData().getLists().size()];
                            String[][] right = new String[msg.getData().getLists().size()][];
                            Integer[][] topicId = new Integer[msg.getData().getLists().size()][];
                            for (int i = 0; i < msg.getData().getLists().size(); i++) {
                                left[i] = msg.getData().getLists().get(i).getTopic_name();
                                if (msg.getData().getLists().get(i).getList2().size()>0) {
                                    right[i] = new String[msg.getData().getLists().get(i).getList2().size()];
                                    topicId[i] = new Integer[msg.getData().getLists().get(i).getList2().size()];
                                    for (int j = 0; j < msg.getData().getLists().get(i).getList2().size(); j++) {
                                        right[i][j] = msg.getData().getLists().get(i).getList2().get(j).getTopic_name();
                                        topicId[i][j] = msg.getData().getLists().get(i).getList2().get(j).getId();

                                    }
                                }else {
                                    right[i] = new String[1];
                                    topicId[i] = new Integer[1];
                                    right[i][0] = "";
                                    topicId[i][0] = 0;
                                }
                            }

                            ((PostActivity) activity).showSelectDateTimeDialog(left, right, topicId);
                        }
                    }
                }
        );
    }

    public void addPost(String content, int topic, ArrayList<String> imagePath, String address, String city,int day,int orderId) {
        param = new AddPostParam();
        param.setDays(day);
        param.setContent(content);
        param.setTopic_id(topic);
        param.setPos_city(getCityId(city));
        param.setOrder_id(orderId);
        param.setIs_top(orderId>0?1:0);
        param.setPics("");
        param.setPosition(address);

        if (imagePath != null && imagePath.size() > 0) {
            UploadImageModel model = new UploadImageModel();
            model.uploadImage(imagePath, this);
        } else
            addPostData();

    }

    public void addPostData() {
        param.setHash(getHashString(AddPostParam.class, param));
        showLoadingDialog();
        ApiClient.create(CircleApi.class).addPost(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo>(this) {
                    @Override
                    public void onNext(MessageTo msg) {
                        submitDataSuccess(msg.getData());
                    }
                }
        );
    }

    @Override
    public void uploadImageSuccess(String path) {
        param.setPics(path);
        addPostData();
    }

    private void initJson() {

        JSONObject mJsonObj = null;
        try {
            StringBuffer sb = new StringBuffer();
            InputStream is = activity.getClass().getClassLoader().getResourceAsStream("assets/" + "province.json");
            int len = -1;
            byte[] buf = new byte[1024];
            while ((len = is.read(buf)) != -1) {
                sb.append(new String(buf, 0, len, "utf-8"));
            }
            is.close();
            mJsonObj = new JSONObject(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            cityList = new Gson().fromJson(mJsonObj.getJSONArray("RECORD").toString(), new TypeToken<List<CityTo>>() {
            }.getType());


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private int getCityId(String cityName) {
        int cityId = 0;

        for (int i = 0; i < cityList.size(); i++) {
            if (cityList.get(i).getSub() != null) {
                for (CityTo cityTo : cityList.get(i).getSub()) {

                    if (cityTo.getArea().contains(cityName) || cityName.contains(cityTo.getArea())) {
                        cityId = cityTo.getId();
                    }
                }
            }
        }
        return cityId;
    }

    public void addTopOrder(int day){
        DaysParam param=new DaysParam();
        param.setDays(day);
        param.setHash(getHashString(DaysParam.class,param));
        showLoadingDialog();
        ApiClient.create(CircleApi.class).topOrder(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<OrderSettleInfoTo>>(this) {
                    @Override
                    public void onNext(MessageTo<OrderSettleInfoTo> msg) {
                        if (msg.getCode()==0)
                            ((PostActivity)activity).topOrderSuccess(msg.getData());
                    }
                }
        );
    }

}

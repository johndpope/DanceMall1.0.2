package com.hzxmkuar.wumeihui.circle.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hzxmkuar.wumeihui.base.BaseFragment;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;
import com.hzxmkuar.wumeihui.base.util.SpUtil;
import com.hzxmkuar.wumeihui.circle.PostActivity;
import com.hzxmkuar.wumeihui.circle.fragment.CircleFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.CircleApi;
import hzxmkuar.com.applibrary.domain.BaseParam;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.circle.CircleBannerTo;
import hzxmkuar.com.applibrary.domain.circle.CircleParam;
import hzxmkuar.com.applibrary.domain.circle.CircleTo;
import hzxmkuar.com.applibrary.domain.circle.TopicTo;
import hzxmkuar.com.applibrary.domain.login.CityTo;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/19.
 **/

public class CirclePresenter extends BasePresenter {
private List<CircleTo.ListsBean>circleList=new ArrayList<>();
    public CircleParam param = new CircleParam();
    private List<CityTo> cityList;

    public CirclePresenter(BaseFragment fragment){
        initContext(fragment);
        getBanner();
        getTopic();
        new Thread(this::initJson).start();
    }

    public void getPostList(int topic){
        param.setPage(recyclePageIndex);
        param.setTopic_id(topic);

        param.setPos_city(param.getIs_local()==1?getCityId(SpUtil.getString("LocateCity")):0);
        param.setHash(getHashString(CircleParam.class, param));
        showLoadingDialog();
        ApiClient.create(CircleApi.class).getPostList(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<CircleTo>>(this) {
                    @Override
                    public void onNext(MessageTo<CircleTo> msg) {
                   if (msg.getCode()==0){
                       if (recyclePageIndex==1)
                       circleList.clear();
                       circleList.addAll(msg.getData().getLists());
                       setRecycleList(circleList);
                   }
                    }
                }
        );
    }

    private void getBanner(){
        BaseParam param=new BaseParam();
        param.setHash(getHashString(BaseParam.class,param));
        showLoadingDialog();
        ApiClient.create(CircleApi.class).getBanner(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<CircleBannerTo>>(this) {
                    @Override
                    public void onNext(MessageTo<CircleBannerTo> msg) {
                     if (msg.getCode()==0)
                         getDataSuccess(msg.getData().getLists());
                    }
                }
        );
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
                            ((CircleFragment)mFragment).setSort(msg.getData().getLists());
                        }
                    }
                }
        );
    }

    private void initJson() {

        JSONObject mJsonObj = null;
        try {
            StringBuffer sb = new StringBuffer();
            InputStream is = mFragment.getClass().getClassLoader().getResourceAsStream("assets/" + "province.json");
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

    @Override
    public void recycleViewLoadMore() {
        super.recycleViewLoadMore();
        getPostList(param.getTopic_id());
    }

    @Override
    public void recycleViewRefresh() {
        super.recycleViewRefresh();
        getPostList(param.getTopic_id());
    }
}

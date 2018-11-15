package com.hzxmkuar.wumeihui.personal.inquiry.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;
import com.hzxmkuar.wumeihui.base.util.SpUtil;
import com.hzxmkuar.wumeihui.personal.inquiry.SelectMerchantActivity;
import com.hzxmkuar.wumeihui.personal.main.fragment.ResourceFragment;
import com.hzxmkuar.wumeihui.personal.order.MerchantListActivity;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.MainApi;
import hzxmkuar.com.applibrary.api.MerchantApi;
import hzxmkuar.com.applibrary.domain.BaseParam;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.login.CityTo;
import hzxmkuar.com.applibrary.domain.main.MainBannerTo;
import hzxmkuar.com.applibrary.domain.main.MainMerchantTo;
import hzxmkuar.com.applibrary.domain.main.MerchantParam;
import hzxmkuar.com.applibrary.domain.main.MerchantTo;
import hzxmkuar.com.applibrary.domain.main.SearchMerchantParam;
import hzxmkuar.com.applibrary.domain.merchant.MerchantCityTo;
import hzxmkuar.com.applibrary.domain.merchant.PosCityParam;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/16.
 **/

public class SelectMerchantPresenter extends BasePresenter {
    public MerchantParam param = new MerchantParam();
    private List<CityTo> cityList;

    public SelectMerchantPresenter(BaseActivity activity) {
        initContext(activity);
        getBanner();
        param.setPos_city(SpUtil.getInt("LocateCityId"));
        getMerchant();
        new Thread(this::initJson).start();
        getCityList(SpUtil.getInt("LocateCityId"));
    }

    public void getBanner() {
        BaseParam param = new BaseParam();
        showLoadingDialog();
        param.setHash(getHashString(BaseParam.class, param));
        ApiClient.create(MainApi.class).getMainBanner(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<MainBannerTo>>(this) {
                    @Override
                    public void onNext(MessageTo<MainBannerTo> msg) {
                        getDataSuccess(msg.getData());
                    }
                }
        );
    }

    public void getMerchant() {
         param.setSids(activity.getIntent().getStringExtra("ServiceId"));
        param.setHash(getHashString(MerchantParam.class, param));

        ApiClient.create(MainApi.class).getMerchantList(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<MerchantTo>>(this) {
                    @Override
                    public void onNext(MessageTo<MerchantTo> msg) {
                        if (msg.getCode() == 0) {
                            ((SelectMerchantActivity) activity).selectList.clear();
                            ((SelectMerchantActivity) activity).setCarefullyLayout(msg.getData().getLists().getChoiceness_merchant());
                            ((SelectMerchantActivity) activity).setMoreShopLayout(msg.getData().getLists().getMore_merchant());
                        }


                    }
                }
        );
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

    public int getCityId(String cityName) {
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

    public void getCityList(int cityId){
        PosCityParam param=new PosCityParam();
        param.setPos_city(cityId);
        param.setHash(getHashString(PosCityParam.class,param));
        showLoadingDialog();
        ApiClient.create(MerchantApi.class).getCityList(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<MerchantCityTo>>(this) {
                    @Override
                    public void onNext(MessageTo<MerchantCityTo> msg) {
                        if (msg.getCode()==0)
                            ((SelectMerchantActivity)activity).setCityLayout(msg.getData().getLists());
                    }
                }
        );
    }
}

package hzxmkuar.com.applibrary.api;

import hzxmkuar.com.applibrary.domain.BaseParam;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.login.UserInfoTo;
import hzxmkuar.com.applibrary.domain.main.MainBannerTo;
import hzxmkuar.com.applibrary.domain.main.MainMerchantTo;
import hzxmkuar.com.applibrary.domain.main.MerchantParam;
import hzxmkuar.com.applibrary.domain.main.MerchantTo;
import hzxmkuar.com.applibrary.domain.main.SearchMerchantParam;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2018/9/16.
 */

public interface MainApi {
    /**
     * 获取首页轮播图
     */
    @POST("Api/Content/index_top")
    Observable<MessageTo<MainBannerTo>>getMainBanner(@Body BaseParam param);


    /**
     * 获取首页商家
     */
    @POST("Api/Merchant/index_choiceness")
    Observable<MessageTo<MainMerchantTo>>getSelectMerchant(@Body SearchMerchantParam param);

    /**
     * 获取更多商家
     */
    @POST("Api/Merchant/index_list")
    Observable<MessageTo<MainMerchantTo>>getMoreMerchant(@Body BaseParam param);

    /**
     * 获取首页商家
     */
    @POST("Api/Merchant/search_list")
    Observable<MessageTo<MerchantTo>>getMerchantList(@Body MerchantParam param);

    /**
     * 获取用户信息
     */
    @POST("Api/User/getUserInfo")
    Observable<MessageTo<UserInfoTo>>getUserInfo(@Body BaseParam param);

    /**
     *
     */
    @POST("Api/Content/index_top")
    Observable<MessageTo<UserInfoTo>>getMenu(@Body BaseParam param);
}

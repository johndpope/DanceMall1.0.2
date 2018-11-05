package hzxmkuar.com.applibrary.api;

import hzxmkuar.com.applibrary.domain.BaseParam;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.integral.PageParam;
import hzxmkuar.com.applibrary.domain.main.MainMerchantTo;
import hzxmkuar.com.applibrary.domain.merchant.BondOrderParam;
import hzxmkuar.com.applibrary.domain.merchant.BondPageTo;
import hzxmkuar.com.applibrary.domain.merchant.BondRecordTo;
import hzxmkuar.com.applibrary.domain.merchant.BusUidParam;
import hzxmkuar.com.applibrary.domain.merchant.CaseIdParam;
import hzxmkuar.com.applibrary.domain.merchant.ExtensionTo;
import hzxmkuar.com.applibrary.domain.merchant.IncomeRecordParam;
import hzxmkuar.com.applibrary.domain.merchant.IncomeRecordTo;
import hzxmkuar.com.applibrary.domain.merchant.MerchantCaseTo;
import hzxmkuar.com.applibrary.domain.merchant.MerchantCityTo;
import hzxmkuar.com.applibrary.domain.merchant.MerchantCollectTo;
import hzxmkuar.com.applibrary.domain.merchant.MerchantCommentTo;
import hzxmkuar.com.applibrary.domain.merchant.MerchantDetailTo;
import hzxmkuar.com.applibrary.domain.merchant.MerchantEnterParam;
import hzxmkuar.com.applibrary.domain.merchant.ModifyMerchantParam;
import hzxmkuar.com.applibrary.domain.merchant.MoneyManagerTo;
import hzxmkuar.com.applibrary.domain.merchant.PosCityParam;
import hzxmkuar.com.applibrary.domain.merchant.ServiceListTo;
import hzxmkuar.com.applibrary.domain.merchant.ShopIdParam;
import hzxmkuar.com.applibrary.domain.merchant.ShopInfoTo;
import hzxmkuar.com.applibrary.domain.order.MerchantOrderDetailTo;
import hzxmkuar.com.applibrary.domain.order.OrderSettleInfoTo;
import hzxmkuar.com.applibrary.domain.order.OrderSettleTo;
import hzxmkuar.com.applibrary.domain.order.PayInfoTo;
import hzxmkuar.com.applibrary.domain.order.PayParam;
import hzxmkuar.com.applibrary.domain.order.WeChatPayTo;
import hzxmkuar.com.applibrary.domain.quote.QuoteDetailTo;
import hzxmkuar.com.applibrary.domain.user.ModifyPhoneParam;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2018/9/20.
 */

public interface MerchantApi {
    /**
     * 商家入驻
     */

    @POST("Api/User/merchantApply")
    Observable<MessageTo> merchantEnter(@Body MerchantEnterParam param);
    /**
     * 修改店铺资料
     */

    @POST("Api/Merchant/update_shop_info")
    Observable<MessageTo> modifyMerchant(@Body ModifyMerchantParam param);
    /**
     * 商家详情
     */

    @POST("Api/Merchant/detail_index")
    Observable<MessageTo<MerchantDetailTo>> merchantDetail(@Body BusUidParam param);

    /**
     * 案例列表
     */

    @POST("Api/Merchant/case_index")
    Observable<MessageTo<MerchantCaseTo>> caseList(@Body BusUidParam param);

    /**
     * 案例详情
     */

    @POST("Api/Merchant/case_detail")
    Observable<MessageTo<MerchantCaseTo.ListsBean>> caseDetail(@Body CaseIdParam param);

    /**
     * 商家评论列表
     */

    @POST("Api/Merchant/user_reviews_list")
    Observable<MessageTo<MerchantCommentTo>> commentList(@Body BusUidParam param);

    /**
     * 商家评论列表
     */

    @POST("Api/Merchant/search_cate_list")
    Observable<MessageTo<ServiceListTo>> getServiceList(@Body BaseParam param);

    /**
     * 商家共有城市
     */

    @POST("Api/Merchant/search_area_list")
    Observable<MessageTo<MerchantCityTo>> getCityList(@Body PosCityParam param);

    /**
     * 生成保证金
     */

    @POST("Api/Merchant/create_deposit_order")
    Observable<MessageTo<OrderSettleInfoTo>> bondOrder(@Body BondOrderParam param);

    /**
     * 支付信息
     */
    @POST("Api/Order/pay_deposit")
    Observable<MessageTo<WeChatPayTo>> payBond(@Body PayParam param);

    /**
     * 支付信息阿里
     */
    @POST("Api/Order/pay_deposit")
    Observable<MessageTo<PayInfoTo>> payBondAli(@Body PayParam param);


    /**
     * 店铺信息
     */
    @POST("Api/Merchant/shop_info")
    Observable<MessageTo<ShopInfoTo>> shopInfo(@Body BaseParam param);

    /**
     * 收藏商家列表
     */
    @POST("Api/User/my_collected_merchant")
    Observable<MessageTo<MainMerchantTo>> collectMerchantList(@Body PageParam param);

    /**
     * 收藏商家
     */
    @POST("Api/Merchant/merchant_docollect")
    Observable<MessageTo<MerchantCollectTo>> collectMerchant(@Body BusUidParam param);

    /**
     * 我的推广
     */
    @POST("Api/Merchant/merchant_promote")
    Observable<MessageTo<ExtensionTo>> extension(@Body BaseParam param);

    /**
     * 生成商家置顶订单
     */
    @POST("Api/Merchant/create_businesstop_order")
    Observable<MessageTo<OrderSettleInfoTo>> merchantTopOrder(@Body BaseParam param);

    /**
     * 生成商家置顶订单
     */
    @POST("Api/Merchant/create_offertop_order")
    Observable<MessageTo<OrderSettleInfoTo>> inquiryTopOrder(@Body BaseParam param);


    /**
     * 支付商家置顶
     */
    @POST("Api/Order/pay_businesstop")
    Observable<MessageTo<WeChatPayTo>> payMerchantTop(@Body PayParam param);

    /**
     * 支付信息阿里
     */
    @POST("Api/Order/pay_businesstop")
    Observable<MessageTo<PayInfoTo>> payMerchantTopAli(@Body PayParam param);


    /**
     * 支付商家报价置顶
     */
    @POST("Api/Order/pay_offertop")
    Observable<MessageTo<WeChatPayTo>> payInquiryTop(@Body PayParam param);

    /**
     * 支付信息阿里
     */
    @POST("Api/Order/pay_offertop")
    Observable<MessageTo<PayInfoTo>> payInquiryTopAli(@Body PayParam param);


    /**
     * 支付帖子置顶
     */
    @POST("Api/Order/pay_posttop_order")
    Observable<MessageTo<WeChatPayTo>> payPostTop(@Body PayParam param);

    /**
     * 支付信息阿里
     */
    @POST("Api/Order/pay_posttop_order")
    Observable<MessageTo> payPostTopAli(@Body PayParam param);

    /**
     * 我的资产
     */
    @POST("Api/Bank/my_assets")
    Observable<MessageTo<MoneyManagerTo>> moneyManager(@Body BaseParam param);


    /**
     * 保证金记录
     */
    @POST("Api/Bank/deposit_log")
    Observable<MessageTo<BondRecordTo>> bondRecord(@Body ShopIdParam param);

    /**
     * 收入明细
     */
    @POST("Api/Bank/accountLog")
    Observable<MessageTo<IncomeRecordTo>> incomeRecord(@Body IncomeRecordParam param);

    /**
     * 保证金页面
     */
    @POST("Api/Merchant/pay_deposit_show")
    Observable<MessageTo<BondPageTo>> bondPage(@Body BaseParam param);

}

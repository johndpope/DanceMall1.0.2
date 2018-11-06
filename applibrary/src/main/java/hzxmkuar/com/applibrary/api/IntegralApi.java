package hzxmkuar.com.applibrary.api;

import hzxmkuar.com.applibrary.domain.BaseParam;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.integral.ConfirmIntegralParam;
import hzxmkuar.com.applibrary.domain.integral.GoodsDetailTo;
import hzxmkuar.com.applibrary.domain.integral.GoodsIdParam;
import hzxmkuar.com.applibrary.domain.integral.GoodsParam;
import hzxmkuar.com.applibrary.domain.integral.GoodsTo;
import hzxmkuar.com.applibrary.domain.integral.IntegralDetailTo;
import hzxmkuar.com.applibrary.domain.integral.IntegralInfoTo;
import hzxmkuar.com.applibrary.domain.integral.IntegralOrderTo;
import hzxmkuar.com.applibrary.domain.integral.IntegralRecordTo;
import hzxmkuar.com.applibrary.domain.integral.PageParam;
import hzxmkuar.com.applibrary.domain.order.OTypeOnPageParam;
import hzxmkuar.com.applibrary.domain.order.OTypeParam;
import hzxmkuar.com.applibrary.domain.order.OrderIdParam;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2018/9/28.
 */

public interface IntegralApi {
    /**
     *商品列表
     */
    @POST("Api/Integralgoods/goods_list")
    Observable<MessageTo<GoodsTo>>getGoodsList(@Body GoodsParam param);

    /**
     *提交订单
     */
    @POST("Api/Integralgoods/place_order")
    Observable<MessageTo>submitOrder(@Body ConfirmIntegralParam param);

    /**
     *提交订单
     */
    @POST("Api/Integralgoods/order_list")
    Observable<MessageTo<IntegralOrderTo>>getMyOrder(@Body OTypeOnPageParam param);

    /**
     *订单详情
     */
    @POST("Api/Integralgoods/order_detail")
    Observable<MessageTo<IntegralDetailTo>>getOrderDetail(@Body OrderIdParam param);

    /**
     *积分使用记录
     */
    @POST("Api/Integralgoods/integral_usage_record")
    Observable<MessageTo<IntegralRecordTo>>getOrderRecord(@Body PageParam param);

    /**
     *我的积分数
     */
    @POST("Api/Integralgoods/my_integral")
    Observable<MessageTo<IntegralInfoTo>>myIntegralCount(@Body BaseParam param);
    /**
     *商品详情
     */
    @POST("Api/Integralgoods/goods_detail")
    Observable<MessageTo<GoodsDetailTo>>goodsDetail(@Body GoodsIdParam param);
}

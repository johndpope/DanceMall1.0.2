package hzxmkuar.com.applibrary.api;

import hzxmkuar.com.applibrary.domain.BaseParam;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.order.AddOrderParam;
import hzxmkuar.com.applibrary.domain.order.BusofferIdParam;
import hzxmkuar.com.applibrary.domain.order.CateIdParam;
import hzxmkuar.com.applibrary.domain.order.EvaluateParam;
import hzxmkuar.com.applibrary.domain.order.MerchantOrderDetailTo;
import hzxmkuar.com.applibrary.domain.order.MyCouponTo;
import hzxmkuar.com.applibrary.domain.order.MyMerchantOrderTo;
import hzxmkuar.com.applibrary.domain.order.OTypeParam;
import hzxmkuar.com.applibrary.domain.order.OrderIdParam;
import hzxmkuar.com.applibrary.domain.order.OrderSettleInfoTo;
import hzxmkuar.com.applibrary.domain.order.OrderSettleTo;
import hzxmkuar.com.applibrary.domain.order.PayInfoTo;
import hzxmkuar.com.applibrary.domain.order.PayParam;
import hzxmkuar.com.applibrary.domain.order.PutForwardParam;
import hzxmkuar.com.applibrary.domain.order.SelectCouponParam;
import hzxmkuar.com.applibrary.domain.order.SelectCouponTo;
import hzxmkuar.com.applibrary.domain.order.WeChatPayTo;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2018/9/18.
 */

public interface OrderApi {

    /**
     *获取优惠券
     */
    @POST("Api/Coupon/all_list")
    Observable<MessageTo<MyCouponTo>>getMyCouponList(@Body BaseParam param);
    /**
     *可领取的优惠券
     */
    @POST("Api/Coupon/getcoupon_list")
    Observable<MessageTo<SelectCouponTo>>receiverCouponList(@Body BaseParam param);

    /**
     *领取优惠券
     */
    @POST("Api/Coupon/receive_coupon")
    Observable<MessageTo>receiverCoupon(@Body CateIdParam param);

    /**
     *选择优惠券
     */
    @POST("Api/Coupon/can_use_list")
    Observable<MessageTo<SelectCouponTo>>getSelectCouponList(@Body SelectCouponParam param);
    /**
     *普通用户订单展示页
     */
    @POST("Api/Order/place_order_show")
    Observable<MessageTo<OrderSettleTo>>getOrderInfo(@Body BusofferIdParam param);

    /**
     *普通用户下单
     */
    @POST("Api/Order/place_order")
    Observable<MessageTo<OrderSettleInfoTo>>addOrder(@Body AddOrderParam param);

    /**
     *商家订单列表
     */
    @POST("Api/Order/businessOrderList")
    Observable<MessageTo<MyMerchantOrderTo>>merchantOrder(@Body OTypeParam param);

    /**
     *用户订单列表
     */
    @POST("Api/Order/orderList")
    Observable<MessageTo<MyMerchantOrderTo>>userOrder(@Body OTypeParam param);

    /**
     *商家订单详情
     */
    @POST("Api/Order/orderDetailForBusiness")
    Observable<MessageTo<MerchantOrderDetailTo>>merchantOrderDetail(@Body OrderIdParam param);

    /**
     *用户订单详情
     */
    @POST("Api/Order/orderDetailForUser")
    Observable<MessageTo<MerchantOrderDetailTo>>userOrderDetail(@Body OrderIdParam param);

    /**
     *支付信息
     */
    @POST("Api/Order/inquiryOrderPay")
    Observable<MessageTo<WeChatPayTo>>getPayinfo(@Body PayParam param);

    /**
     *支付信息支付宝
     */
    @POST("Api/Order/inquiryOrderPay")
    Observable<MessageTo>getPayinfoAli(@Body PayParam param);


    /**
     *确定接单
     */
    @POST("Api/Order/orderReceivingForBusiness")
    Observable<MessageTo>confirmReceiver(@Body OrderIdParam param);

    /**
     *取消接单
     */
    @POST("Api/Order/cancelOrderForBusiness")
    Observable<MessageTo>cancelReceiver(@Body OrderIdParam param);

    /**
     *开始订单
     */
    @POST("Api/Order/confirmStartForBusiness")
    Observable<MessageTo>startOrder(@Body OrderIdParam param);

    /**
     *完成订单
     */
    @POST("Api/Order/confirmCompletedForBusiness")
    Observable<MessageTo>finishOrder(@Body OrderIdParam param);

    /**
     *普通用户取消订单
     */
    @POST("Api/Order/cancelOrderForUser")
    Observable<MessageTo>cancelOrder(@Body OrderIdParam param);

    /**
     *普通用户确认完成
     */
    @POST("Api/Order/confirmCompletedForUser")
    Observable<MessageTo>confirmFinish(@Body OrderIdParam param);


    /**
     *提交评价
     */
    @POST("Api/Order/submitEvaluationForUser")
    Observable<MessageTo>evaluate(@Body EvaluateParam param);

    /**
     *提现申请
     */
    @POST("Api/Bank/apply_cash")
    Observable<MessageTo>putForword(@Body PutForwardParam param);
}

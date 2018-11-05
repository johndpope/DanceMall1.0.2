package hzxmkuar.com.applibrary.domain.order;

import java.util.List;

import lombok.Data;

/**
 * Created by Administrator on 2018/9/29.
 */
@Data
public class OrderSettleTo {

    /**
     * busoffer_id : 5
     * service_address : 浙江杭州下沙金沙湖
     * use_time : 06月22日12:00-06月23日12:00
     * contact_name : 大咖
     * contact_telphone : 15669985632
     * service_list : [{"sid":12,"service_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png"}]
     * service_num : 1
     * coupon_info : {"coupon_id":3,"coupon_name":"50元优惠券","coupon_amount":"50.00"}
     * price_detail : {"full_payment":{"pay_amount":949},"deposit_payment":{"pay_amount":49.9,"last_amount":899.1},"service_fee":999}
     */

    private int busoffer_id;
    private String service_address;
    private String use_time;
    private String contact_name;
    private String contact_telphone;
    private int service_num;
    private CouponInfoBean coupon_info;
    private PriceDetailBean price_detail;
    private List<ServiceListBean> service_list;


    @Data
    public static class CouponInfoBean {
        /**
         * coupon_id : 3
         * coupon_name : 50元优惠券
         * coupon_amount : 50.00
         */

        private int coupon_id;
        private String coupon_name;
        private double coupon_amount;


    }

    @Data
    public static class PriceDetailBean {
        /**
         * full_payment : {"pay_amount":949}
         * deposit_payment : {"pay_amount":49.9,"last_amount":899.1}
         * service_fee : 999
         */

        private FullPaymentBean full_payment;
        private DepositPaymentBean deposit_payment;
        private double service_fee;


        @Data
        public static class FullPaymentBean {
            /**
             * pay_amount : 949
             */

            private double pay_amount;


        }

        @Data
        public static class DepositPaymentBean {
            /**
             * pay_amount : 49.9
             * last_amount : 899.1
             */

            private double pay_amount;
            private double last_amount;


        }
    }

    @Data
    public static class ServiceListBean {
        /**
         * sid : 12
         * service_img : http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png
         */

        private int sid;
        private String service_img;


    }
}

package hzxmkuar.com.applibrary.domain.order;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * Created by Administrator on 2018/9/27.
 */
@Data
public class MerchantOrderDetailTo implements Serializable {


    /**
     * order_id : 3
     * order_sn : W180921163954537
     * service_address : 浙江杭州下沙金沙湖
     * contact_name : 大咖
     * contact_telphone : 15669985632
     * service_list : [{"sid":12,"service_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png"}]
     * service_num : 1
     * coupon_info : {"coupon_id":0,"coupon_name":"","coupon_amount":"0.00"}
     * price_detail : {"full_payment":{"pay_amount":49.9},"deposit_payment":{"pay_amount":49.9,"last_amount":899.1},"service_fee":999}
     * remarks :
     * status_txt : 客户确认完成
     * button_list : {"qxdd_btn":0,"qrjd_btn":0,"lxkh_btn":0,"qrks_btn":0,"qrwc_btn":0}
     */

    private int order_id;
    private String order_sn;
    private String service_address;
    private String contact_name;
    private String use_time;
    private String contact_telphone;
    private int service_num;
    private int payment_mode;
    private CouponInfoBean coupon_info;
    private PriceDetailBean price_detail;
    private String remarks;
    private String status_txt;
    private int new_status;
    private ButtonListBean button_list;

    private List<ServiceListBean> service_list;
    private ShopInfoTo business_info;
    private int remaining_pay_valid_time;
    private int remaining_confirm_valid_time;
    private String customer_mobile;
    private String customer_username;



    @Data
    public static class CouponInfoBean implements Serializable {
        /**
         * coupon_id : 0
         * coupon_name :
         * coupon_amount : 0.00
         */

        private int coupon_id;
        private String coupon_name;
        private String coupon_amount;

    }

    @Data
    public static class PriceDetailBean implements Serializable {
        /**
         * full_payment : {"pay_amount":49.9}
         * deposit_payment : {"pay_amount":49.9,"last_amount":899.1}
         * service_fee : 999
         */

        private FullPaymentBean full_payment;
        private DepositPaymentBean deposit_payment;
        private String service_fee;
        private RefundTo refund_data;

        /**
         * is_refund : 0
         * refund_amount : 0
         * refund_type :
         * liquidated_damages : 0
         * liquidated_damages_type :
         * is_liquidated_damages : 0
         */


        @Data
        public static class RefundTo {
            private int is_refund;
            private String refund_amount;
            private String refund_type;
            private String liquidated_damages;
            private String liquidated_damages_type;
            private int is_liquidated_damages;
        }

        @Data

        public static class FullPaymentBean implements Serializable {
            /**
             * pay_amount : 49.9
             */

            private String pay_amount;
            private String pay_amount_type;
            private int pay_amount_status;


        }

        @Data
        public static class DepositPaymentBean implements Serializable {
            /**
             * pay_amount : 49.9
             * last_amount : 899.1
             */

            private String pay_amount;
            private String last_amount;
            private String pay_amount_type;
            private String last_amount_type;
            private String last_amount_paytime;
            private int last_amount_status;


        }


    }

    @Data
    public static class ButtonListBean implements Serializable {
        /**
         * qxdd_btn : 0
         * qrjd_btn : 0
         * lxkh_btn : 0
         * qrks_btn : 0
         * qrwc_btn : 0
         */

        private int qxdd_btn;
        private int qrjd_btn;
        private int lxkh_btn;
        private int qrks_btn;
        private int qrwc_btn;
        private int zfdj_btn;


    }

    @Data
    public static class ServiceListBean implements Serializable {
        /**
         * sid : 12
         * service_img : http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png
         */

        private int sid;
        private String service_img;
        private String service_name;


    }

    @Data
    public static class ShopInfoTo {
        private String shop_name;
        private String merchant_telephone;
        private int bus_uid;
        private int shop_id;
    }
}

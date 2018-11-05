package hzxmkuar.com.applibrary.domain.order;

import java.util.List;

import lombok.Data;

/**
 * Created by Administrator on 2018/9/27.
 */
@Data
public class MyMerchantOrderTo {


    /**
     * total : 1
     * page : 1
     * limit : 100
     * remainder : 0
     * lists : [{"id":3,"pay_status1":1,"pay_status2":1,"pay_status":1,"status":4,"status_txt":"客户确认完成","order_sn":"W180921163954537","total_amount":"949.00","total_amount1":"49.90","total_amount2":"899.10","payment_mode":2,"notice":3,"service_list":[{"sid":12,"service_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png"}],"service_num":1,"button_list":{"qxdd_btn":0,"qrjd_btn":0,"lxkh_btn":0,"qrks_btn":0,"qrwc_btn":0}}]
     */

    private int total;
    private int page;
    private int limit;
    private int remainder;
    private List<ListsBean> lists;


    @Data
    public static class ListsBean {
        /**
         * id : 3
         * pay_status1 : 1
         * pay_status2 : 1
         * pay_status : 1
         * status : 4
         * status_txt : 客户确认完成
         * order_sn : W180921163954537
         * total_amount : 949.00
         * total_amount1 : 49.90
         * total_amount2 : 899.10
         * payment_mode : 2
         * notice : 3
         * service_list : [{"sid":12,"service_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png"}]
         * service_num : 1
         * button_list : {"qxdd_btn":0,"qrjd_btn":0,"lxkh_btn":0,"qrks_btn":0,"qrwc_btn":0}
         */

        private int id;
        private int pay_status1;
        private int pay_status2;
        private int pay_status;
        private int status;
        private String status_txt;
        private String order_sn;
        private String total_amount;
        private double total_amount1;
        private double total_amount2;
        private int payment_mode;
        private int notice;
        private int service_num;
        private int new_status;
        private ButtonListBean button_list;
        private List<ServiceListBean> service_list;
        private String customer_telephone;
        private String merchant_telephone;
        private String title;
        private int type;
        private int remaining_pay_valid_time;

        @Data
        public static class ButtonListBean {
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
}

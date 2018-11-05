package hzxmkuar.com.applibrary.domain.user;

import lombok.Data;

/**
 * Created by Administrator on 2018/10/15.
 */
@Data
public class MyselfUserTo {

    /**
     * uid : 88
     * face_url : http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-05-29/30bfc5eb7862333d1995f020d9f356da.jpg
     * is_store : 1
     * is_member : 0
     * account : 0.00
     * user_tag : 青铜
     * username : helloworld
     * kf_tel : 0571-66555555
     * my_enquiry : {"enquiry_yes":1,"enquiry_no":2}
     * my_order : {"unevaluate":1,"unpay":2,"uncomfirm":1,"unpay_retainage":1,"unservice":2}
     * my_wmgroup : {"published":1,"collected":2,"recent_browse":2}
     */

    private int uid;
    private String face_url;
    private int is_store;
    private int is_member;
    private String account;
    private String user_tag;
    private String username;
    private String kf_tel;
    private MyEnquiryBean my_enquiry;
    private MyOrderBean my_order;
    private MyWmgroupBean my_wmgroup;

    @Data

    public static class MyEnquiryBean {
        /**
         * enquiry_yes : 1
         * enquiry_no : 2
         */

        private int enquiry_yes;
        private int enquiry_no;


    }

    @Data
    public static class MyOrderBean {
        /**
         * unevaluate : 1
         * unpay : 2
         * uncomfirm : 1
         * unpay_retainage : 1
         * unservice : 2
         */

        private int unevaluate;
        private int unpay;
        private int uncomfirm;
        private int unpay_retainage;
        private int unservice;


    }

    @Data
    public static class MyWmgroupBean {
        /**
         * published : 1
         * collected : 2
         * recent_browse : 2
         */

        private int published;
        private int collected;
        private int recent_browse;


    }
}

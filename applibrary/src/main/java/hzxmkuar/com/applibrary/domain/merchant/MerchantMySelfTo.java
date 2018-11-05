package hzxmkuar.com.applibrary.domain.merchant;

import lombok.Data;

/**
 * Created by Administrator on 2018/10/16.
 */
@Data
public class MerchantMySelfTo {

    /**
     * uid : 88
     * face_url : http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-05-29/30bfc5eb7862333d1995f020d9f356da.jpg
     * user_tag : 青铜
     * username : helloworld
     * kf_tel : 15821708208
     * my_inquiry_busoffer : {"busoffer_yes":1,"busoffer_no":2}
     * my_order : {"uncomfirm":1,"unpay":2,"unservice":1,"servicing":1}
     * my_wmgroup : {"published":1,"collected":2,"recent_browse":2}
     * is_member : 0
     * is_deposit : 0
     */

    private int uid;
    private String face_url;
    private String user_tag;
    private String username;
    private String kf_tel;
    private MyInquiryBusofferBean my_inquiry_busoffer;
    private MyOrderBean my_order;
    private MyWmgroupBean my_wmgroup;
    private int is_member;
    private int is_deposit;

    @Data

    public static class MyInquiryBusofferBean {
        /**
         * busoffer_yes : 1
         * busoffer_no : 2
         */

        private int busoffer_yes;
        private int busoffer_no;

    }

    @Data
    public static class MyOrderBean {
        /**
         * uncomfirm : 1
         * unpay : 2
         * unservice : 1
         * servicing : 1
         */

        private int uncomfirm;
        private int unpay;
        private int unservice;
        private int servicing;

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

package hzxmkuar.com.applibrary.domain.order;

import java.util.List;

import lombok.Data;

/**
 * Created by Administrator on 2018/9/29.
 */
@Data
public class SelectCouponTo {

    /**
     * total : 2
     * lists : [{"id":1,"amount":"30.00","cate_name":"30元优惠券","cate_desc":"30元优惠券","start_time":1537249872,"end_time":1537509072},{"id":3,"amount":"50.00","cate_name":"50元优惠券","cate_desc":"50元优惠券","start_time":1537249872,"end_time":1537509072}]
     */

    private int total;
    private List<ListsBean> lists;


    @Data
    public static class ListsBean {
        /**
         * id : 1
         * amount : 30.00
         * cate_name : 30元优惠券
         * cate_desc : 30元优惠券
         * start_time : 1537249872
         * end_time : 1537509072
         */

        private int id;
        private String amount;
        private String cate_name;
        private String cate_desc;
        private long start_time;
        private long end_time;


        private int get_status;
        private int cate_id;
        private String discount_amount;
        private String use_times;

    }
}

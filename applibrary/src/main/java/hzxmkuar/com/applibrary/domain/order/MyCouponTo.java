package hzxmkuar.com.applibrary.domain.order;

import java.util.List;

import lombok.Data;

/**
 * Created by Administrator on 2018/9/18.
 */
@Data
public class MyCouponTo {

    private List<CouponTo> unuse_list;
    private List<CouponTo> used_list;
    private List<CouponTo> expired_list;


@Data
    public static class CouponTo {
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


    }
}

package hzxmkuar.com.applibrary.domain.merchant;

import java.util.List;

import lombok.Data;

/**
 * Created by Administrator on 2018/10/16.
 */
@Data
public class IncomeRecordTo  {

    /**
     * total : 14
     * page : 1
     * limit : 100
     * remainder : 0
     * lists : [{"id":35,"amount":"-1000","dateline":"2018-08-28","desc":"余额转积分","atype":2},{"id":33,"amount":"-10000","dateline":"2018-08-23","desc":"余额转积分","atype":2},{"id":32,"amount":"+10000","dateline":"2018-08-23","desc":"余额转账","atype":1},{"id":31,"amount":"-10000","dateline":"2018-08-23","desc":"余额转账","atype":2},{"id":28,"amount":"-1000","dateline":"2018-08-23","desc":"余额转积分","atype":2},{"id":27,"amount":"+1000","dateline":"2018-08-23","desc":"余额转账","atype":1},{"id":26,"amount":"-1000","dateline":"2018-08-23","desc":"余额转账","atype":2},{"id":24,"amount":"+1000","dateline":"2018-08-23","desc":"余额转账","atype":1},{"id":23,"amount":"-1000","dateline":"2018-08-23","desc":"余额转账","atype":2},{"id":22,"amount":"+250","dateline":"2018-08-23","desc":"余额转账","atype":1},{"id":21,"amount":"-250","dateline":"2018-08-23","desc":"余额转账","atype":2},{"id":20,"amount":"+500","dateline":"2018-08-23","desc":"余额转账","atype":1},{"id":19,"amount":"-500","dateline":"2018-08-23","desc":"余额转账","atype":2},{"id":18,"amount":"+10000","dateline":"2018-08-23","desc":"余额转账","atype":1}]
     */

    private int total;
    private int page;
    private int limit;
    private int remainder;
    private List<ListsBean> lists;

   @Data

    public static class ListsBean {
        /**
         * id : 35
         * amount : -1000
         * dateline : 2018-08-28
         * desc : 余额转积分
         * atype : 2
         */

        private int id;
        private String amount;
        private String dateline;
        private String desc;
        private int atype;


    }
}

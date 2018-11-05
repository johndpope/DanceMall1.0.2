package hzxmkuar.com.applibrary.domain.merchant;

import java.util.List;

import lombok.Data;

/**
 * Created by Administrator on 2018/10/16.
 */
@Data
public class BondRecordTo {

    /**
     * total : 2
     * page : 1
     * limit : 100
     * remainder : 0
     * lists : [{"id":2,"desc":"缴纳保证金","dateline":"2018-09-28","amount":"交保证金1500.00"},{"id":1,"desc":"缴纳保证金","dateline":"2018-09-28","amount":"交保证金500.00"}]
     */

    private int total;
    private int page;
    private int limit;
    private int remainder;
    private List<ListsBean> lists;


    @Data
    public static class ListsBean {
        /**
         * id : 2
         * desc : 缴纳保证金
         * dateline : 2018-09-28
         * amount : 交保证金1500.00
         */

        private int id;
        private String desc;
        private String dateline;
        private String amount;


    }
}

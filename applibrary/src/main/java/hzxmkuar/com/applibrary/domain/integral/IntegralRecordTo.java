package hzxmkuar.com.applibrary.domain.integral;

import java.util.List;

import lombok.Data;

/**
 * Created by Administrator on 2018/9/28.
 */
@Data
public class IntegralRecordTo {

    /**
     * total : 2
     * page : 1
     * limit : 100
     * remainder : 0
     * lists : [{"id":7,"dateline":"2018-09-27 18:42:10","rfrom_text":"兑换商品","rmode":2,"score":"-98.00"},{"id":6,"dateline":"2018-09-27 18:42:04","rfrom_text":"兑换商品","rmode":2,"score":"-100.00"}]
     */

    private int total;
    private int page;
    private int limit;
    private int remainder;
    private List<ListsBean> lists;

    @Data
    public static class ListsBean {
        /**
         * id : 7
         * dateline : 2018-09-27 18:42:10
         * rfrom_text : 兑换商品
         * rmode : 2
         * score : -98.00
         */

        private int id;
        private String dateline;
        private String rfrom_text;
        private int rmode;
        private String score;


    }
}

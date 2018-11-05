package hzxmkuar.com.applibrary.domain.message;

import java.util.List;

import lombok.Data;

/**
 * Created by Administrator on 2018/9/11.
 */
@Data
public class SystemMessageTo {

    /**
     * total : 1
     * page : 1
     * limit : 10
     * remainder : 0
     * lists : [{"id":10,"msg_title":"订单已发货","msg_desc":"您购买的[ORILAYA奥俐莱雅]已经发货啦。详情可以查看物流信息。","dateline":"2018-08-10 12:11:02"}]
     */

    private int total;
    private int page;
    private int limit;
    private int remainder;
    private List<ListsBean> lists;

  @Data
    public static class ListsBean {
        /**
         * id : 10
         * msg_title : 订单已发货
         * msg_desc : 您购买的[ORILAYA奥俐莱雅]已经发货啦。详情可以查看物流信息。
         * dateline : 2018-08-10 12:11:02
         */

        private int id;
        private String msg_title;
        private String msg_desc;
        private String dateline;

    }
}

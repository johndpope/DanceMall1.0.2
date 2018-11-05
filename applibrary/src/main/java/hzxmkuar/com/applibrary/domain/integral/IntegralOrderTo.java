package hzxmkuar.com.applibrary.domain.integral;

import java.util.List;

import lombok.Data;

/**
 * Created by Administrator on 2018/9/28.
 */
@Data
public class IntegralOrderTo {


    /**
     * total : 2
     * page : 1
     * limit : 100
     * remainder : 0
     * lists : [{"order_id":24,"status_txt":"待发货","order_sn":"E180927184210711","goods_name":"REnex植物精华洗发液500ml 无硅油去油滋润头皮","goods_image":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-07-03/1a0e65399abae44f7688f5da9a0b2c39.jpg","total_amount":"98.00"},{"order_id":23,"status_txt":"待发货","order_sn":"E180927184204723","goods_name":"阿道夫洗发水护发素组合装正品","goods_image":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-07-03/9793804086ab9ae9cec1699751476163.jpg","total_amount":"100.00"}]
     */

    private int total;
    private int page;
    private int limit;
    private int remainder;
    private List<ListsBean> lists;

    @Data
    public static class ListsBean {
        /**
         * order_id : 24
         * status_txt : 待发货
         * order_sn : E180927184210711
         * goods_name : REnex植物精华洗发液500ml 无硅油去油滋润头皮
         * goods_image : http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-07-03/1a0e65399abae44f7688f5da9a0b2c39.jpg
         * total_amount : 98.00
         */

        private int order_id;
        private String status_txt;
        private String order_sn;
        private String goods_name;
        private String goods_image;
        private String total_amount;


    }
}

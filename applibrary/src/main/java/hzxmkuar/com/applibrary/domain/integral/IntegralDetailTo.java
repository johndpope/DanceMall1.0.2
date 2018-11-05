package hzxmkuar.com.applibrary.domain.integral;

import lombok.Data;

/**
 * Created by Administrator on 2018/9/28.
 */
@Data
public class IntegralDetailTo  {

    /**
     * order_id : 23
     * status_txt : 待发货
     * order_sn : E180927184204723
     * goods_name : 阿道夫洗发水护发素组合装正品
     * goods_image : http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-07-03/9793804086ab9ae9cec1699751476163.jpg
     * total_amount : 100
     * consignee : 丁大大
     * telephone : 13738084686
     * address : 杭州下沙
     */

    private int order_id;
    private String status_txt;
    private String order_sn;
    private String goods_name;
    private String goods_image;
    private int total_amount;
    private String consignee;
    private String telephone;
    private String address;


}

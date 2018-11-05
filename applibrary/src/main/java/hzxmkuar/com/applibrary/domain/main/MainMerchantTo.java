package hzxmkuar.com.applibrary.domain.main;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * Created by Administrator on 2018/9/16.
 */
@Data
public class MainMerchantTo  {

    /**
     * total : 1
     * page : 1
     * limit : 10
     * remainder : 0
     * lists : [{"id":15,"shop_name":"天天开心","shop_logo":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-04-12/2dedfebf9949ae5cf1105f200b0ffbd8.jpg","ratings":"0.00","order_num":0,"stype_txt":"企业","service_exam":"服务1、服务2","area":"南京玄武"}]
     */

    private int total;
    private int page;
    private int limit;
    private int remainder;

    private List<ListsBean> lists;



    @Data
    public static class ListsBean implements Serializable{
        /**
         * id : 15
         * shop_name : 天天开心
         * shop_logo : http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-04-12/2dedfebf9949ae5cf1105f200b0ffbd8.jpg
         * ratings : 0.00
         * order_num : 0
         * stype_txt : 企业
         * service_exam : 服务1、服务2
         * area : 南京玄武
         */

        private int id;
        private String shop_name;
        private String shop_logo;
        private String ratings;
        private int order_num;
        private String stype_txt;
        private String service_exam;
        private String area;
        private boolean select;
        private String offer_amount;
        private String invoice_txt;
        private String merchant_telephone;
        private int bus_uid;
        private int is_offertop;
        private int busoffer_id;
    }
}

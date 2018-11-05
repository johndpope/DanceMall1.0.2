package hzxmkuar.com.applibrary.domain.main;

import lombok.Data;

/**
 * Created by Administrator on 2018/10/9.
 */
@Data
public class MerchantInfoTo {

    /**
     * id : 21
     * shop_name : 玛卡瑞纳
     * shop_logo : http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-30/2e60acf3599f972dc0964b94cb335ba1.pngcompress
     * ratings : 0.00
     * order_num : 0
     * stype_txt : 企业
     * service_exam : 服务1、服务2
     * city_tmp : 934
     * area_tmp : 939
     * bus_uid : 103
     * is_choiceness : 0
     * area : 杭州西湖
     */

    private int id;
    private String shop_name;
    private String shop_logo;
    private double ratings;
    private int order_num;
    private String stype_txt;
    private String service_exam;
    private int city_tmp;
    private int area_tmp;
    private int bus_uid;
    private int is_choiceness;
    private String area;
    private boolean select;

}

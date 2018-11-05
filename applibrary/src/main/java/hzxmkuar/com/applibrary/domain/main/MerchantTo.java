package hzxmkuar.com.applibrary.domain.main;

import java.util.List;

import lombok.Data;

/**
 * Created by Administrator on 2018/10/9.
 */
@Data
public class MerchantTo {

    /**
     * total : 7
     * page : 1
     * limit : 1000
     * remainder : 0
     * lists : {"choiceness_merchant":[{"id":15,"shop_name":"天天开心","shop_logo":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-18/bc51c91cf67b51550515209ea841fffd.jpg","ratings":"0.00","order_num":0,"stype_txt":"企业","service_exam":"服务1、服务2","city_tmp":821,"area_tmp":822,"bus_uid":88,"is_choiceness":1,"area":"南京玄武"}],"more_merchant":[{"id":16,"shop_name":"新苗1店","shop_logo":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-18/bc51c91cf67b51550515209ea841fffd.jpg","ratings":"5.00","order_num":0,"stype_txt":"企业","service_exam":"服务1、服务2","city_tmp":934,"area_tmp":935,"bus_uid":94,"is_choiceness":0,"area":"杭州上城"},{"id":17,"shop_name":"新苗个人2店","shop_logo":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-18/bc51c91cf67b51550515209ea841fffd.jpg","ratings":"0.00","order_num":0,"stype_txt":"个人","service_exam":"服务1、服务2","city_tmp":934,"area_tmp":939,"bus_uid":95,"is_choiceness":0,"area":"杭州西湖"},{"id":18,"shop_name":"店铺","shop_logo":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-25/6fe621ed04172d6f4a19e12320449411.jpg","ratings":"0.00","order_num":0,"stype_txt":"企业","service_exam":"服务1、服务2","city_tmp":934,"area_tmp":939,"bus_uid":93,"is_choiceness":0,"area":"杭州西湖"},{"id":19,"shop_name":"公司店铺","shop_logo":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-29/bbccd5d2b4fe8a66cbaa5419666b06c7.jpgcompress","ratings":"0.00","order_num":0,"stype_txt":"企业","service_exam":"服务1、服务2","city_tmp":934,"area_tmp":939,"bus_uid":99,"is_choiceness":0,"area":"杭州西湖"},{"id":20,"shop_name":"新苗","shop_logo":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-29/9adbaa4d73cd9068573d341a23c90488.JPEGcompress","ratings":"0.00","order_num":0,"stype_txt":"企业","service_exam":"服务1、服务2","city_tmp":934,"area_tmp":937,"bus_uid":101,"is_choiceness":0,"area":"杭州江干"},{"id":21,"shop_name":"玛卡瑞纳","shop_logo":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-30/2e60acf3599f972dc0964b94cb335ba1.pngcompress","ratings":"0.00","order_num":0,"stype_txt":"企业","service_exam":"服务1、服务2","city_tmp":934,"area_tmp":939,"bus_uid":103,"is_choiceness":0,"area":"杭州西湖"}]}
     */

    private int total;
    private int page;
    private int limit;
    private int remainder;
    private ListsBean lists;


    @Data
    public static class ListsBean {
        private List<MerchantInfoTo> choiceness_merchant;
        private List<MerchantInfoTo> more_merchant;


    }
}

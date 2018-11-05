package hzxmkuar.com.applibrary.domain.inquery;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * Created by Administrator on 2018/9/18.
 */
@Data
public class InquiryInfoTo implements Serializable{


    /**
     * id : 2
     * inquiry_sn : WMH20180906163758611
     * use_time : 06月08日12:00-06月11日12:00
     * contact_name : 丁大大
     * contact_telphone : 13738084688
     * status_arr : {"status":1,"status_txt":"暂无报价"}
     * service_list : [{"sid":8,"service_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png"},{"sid":13,"service_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png"}]
     * valid_time : 64169
     * service_num : 2
     */

    private int id;
    private String inquiry_sn;
    private String use_time;
    private String contact_name;
    private String contact_telphone;
    private StatusArrBean status_arr;
    private int valid_time;
    private int service_num;
    private List<ServiceListBean> service_list;
    private String service_address;


    @Data
    public static class StatusArrBean implements Serializable{
        /**
         * status : 1
         * status_txt : 暂无报价
         */

        private int status;
        private String status_txt;


    }

    @Data
    public static class ServiceListBean implements Serializable{
        /**
         * sid : 8
         * service_img : http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png
         */

        private int sid;
        private String service_img;


    }
}

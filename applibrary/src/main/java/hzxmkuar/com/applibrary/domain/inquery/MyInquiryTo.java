package hzxmkuar.com.applibrary.domain.inquery;

import java.util.List;

import lombok.Data;

/**
 * Created by Administrator on 2018/9/18.
 */
@Data
public class MyInquiryTo {

    /**
     * total : 54
     * page : 1
     * limit : 10
     * remainder : 44
     * lists : [{"id":65,"service_num":1,"inquiry_sn":"WMH20180918111943578","offer_num":3,"status_arr":{"status":1,"status_txt":"暂无报价"},"service_list":[{"sid":8,"service_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png"}],"valid_time":1179},{"id":64,"service_num":1,"inquiry_sn":"WMH20180918105925256","offer_num":3,"status_arr":{"status":1,"status_txt":"暂无报价"},"service_list":[{"sid":8,"service_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png"}],"valid_time":2397},{"id":63,"service_num":1,"inquiry_sn":"WMH20180918100454948","offer_num":3,"status_arr":{"status":1,"status_txt":"暂无报价"},"service_list":[{"sid":8,"service_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png"}],"valid_time":5668},{"id":62,"service_num":1,"inquiry_sn":"WMH20180917231421242","offer_num":3,"status_arr":{"status":1,"status_txt":"暂无报价"},"service_list":[{"sid":13,"service_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png"}],"valid_time":44701},{"id":61,"service_num":1,"inquiry_sn":"WMH20180917231421716","offer_num":3,"status_arr":{"status":1,"status_txt":"暂无报价"},"service_list":[{"sid":8,"service_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png"}],"valid_time":44701},{"id":60,"service_num":1,"inquiry_sn":"WMH20180917231132734","offer_num":3,"status_arr":{"status":1,"status_txt":"暂无报价"},"service_list":[{"sid":13,"service_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png"}],"valid_time":44870},{"id":59,"service_num":1,"inquiry_sn":"WMH20180917231132583","offer_num":3,"status_arr":{"status":1,"status_txt":"暂无报价"},"service_list":[{"sid":8,"service_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png"}],"valid_time":44870},{"id":58,"service_num":1,"inquiry_sn":"WMH20180917220023173","offer_num":3,"status_arr":{"status":1,"status_txt":"暂无报价"},"service_list":[{"sid":13,"service_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png"}],"valid_time":49139},{"id":57,"service_num":1,"inquiry_sn":"WMH20180917220023896","offer_num":3,"status_arr":{"status":1,"status_txt":"暂无报价"},"service_list":[{"sid":8,"service_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png"}],"valid_time":49139},{"id":56,"service_num":1,"inquiry_sn":"WMH20180917215444793","offer_num":3,"status_arr":{"status":1,"status_txt":"暂无报价"},"service_list":[{"sid":13,"service_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png"}],"valid_time":49478}]
     */

    private int total;
    private int page;
    private int limit;
    private int remainder;
    private List<ListsBean> lists;


    @Data
    public static class ListsBean {
        /**
         * id : 65
         * service_num : 1
         * inquiry_sn : WMH20180918111943578
         * offer_num : 3
         * status_arr : {"status":1,"status_txt":"暂无报价"}
         * service_list : [{"sid":8,"service_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png"}]
         * valid_time : 1179
         */

        private int id;
        private int service_num;
        private String inquiry_sn;
        private int offer_num;
        private StatusArrBean status_arr;
        private int valid_time;
        private List<ServiceListBean> service_list;

        @Data
        public static class StatusArrBean {
            /**
             * status : 1
             * status_txt : 暂无报价
             */

            private int status;
            private String status_txt;


        }

        @Data
        public static class ServiceListBean {
            /**
             * sid : 8
             * service_img : http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png
             */

            private int sid;
            private String service_img;


        }
    }
}

package hzxmkuar.com.applibrary.domain.quote;

import java.util.List;
import java.util.Objects;

import lombok.Data;

/**
 * Created by Administrator on 2018/9/28.
 */
@Data
public class QuoteDetailTo {


    /**
     * id : 86
     * inquiry_sn : WMH20180928223848367
     * use_time : 09月28日22:00-09月28日23:00
     * contact_name : 编辑
     * contact_telphone : 15168234206
     * service_num : 1
     * valid_time : 2675
     * status_arr : {"status":2,"status_txt":"已报价"}
     * service_list : [{"sid":8,"service_name":"服务1","service_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png","service_list":[{"id":18,"ptitle":"面积要求","ptype":1,"ptype_txt":"文本","pvalues":["我们"]},{"id":19,"ptitle":"服务要求","ptype":3,"ptype_txt":"复选","conflist":[{"id":"1","title":"包送货","status":true},{"id":"2","title":"包安装","status":false},{"id":"3","title":"包拆卸","status":false}],"pvalues":[1]},{"id":20,"ptitle":"文件说明","ptype":5,"ptype_txt":"图片","pvalues":[[],[]]},{"id":26,"ptitle":"补充要求","ptype":6,"ptype_txt":"备注","pvalues":["没有"]}]}]
     */

    private int id;
    private String inquiry_sn;
    private String use_time;
    private String contact_name;
    private String service_address;
    private String contact_telphone;
    private int service_num;
    private int valid_time;

    private StatusArrBean status_arr;
    private List<ServiceListTo> service_list;

    @Data

    public static class StatusArrBean {
        /**
         * status : 2
         * status_txt : 已报价
         */

        private int status;
        private String status_txt;


    }

    @Data
    public static class ServiceListTo {
        /**
         * sid : 8
         * service_name : 服务1
         * service_img : http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png
         * service_list : [{"id":18,"ptitle":"面积要求","ptype":1,"ptype_txt":"文本","pvalues":["我们"]},{"id":19,"ptitle":"服务要求","ptype":3,"ptype_txt":"复选","conflist":[{"id":"1","title":"包送货","status":true},{"id":"2","title":"包安装","status":false},{"id":"3","title":"包拆卸","status":false}],"pvalues":[1]},{"id":20,"ptitle":"文件说明","ptype":5,"ptype_txt":"图片","pvalues":[[],[]]},{"id":26,"ptitle":"补充要求","ptype":6,"ptype_txt":"备注","pvalues":["没有"]}]
         */

        private int sid;
        private String service_name;
        private String service_img;
        private List<ServiceListBean> service_list;

        @Data

        public static class ServiceListBean {
            /**
             * id : 18
             * ptitle : 面积要求
             * ptype : 1
             * ptype_txt : 文本
             * pvalues : ["我们"]
             * conflist : [{"id":"1","title":"包送货","status":true},{"id":"2","title":"包安装","status":false},{"id":"3","title":"包拆卸","status":false}]
             */

            private int id;
            private String ptitle;
            private int ptype;
            private String ptype_txt;
            private List<Object> pvalues;
            private List<ConflistBean> conflist;


            @Data
            public static class ConflistBean {
                /**
                 * id : 1
                 * title : 包送货
                 * status : true
                 */

                private String id;
                private String title;
                private boolean status;


            }
        }
    }
}

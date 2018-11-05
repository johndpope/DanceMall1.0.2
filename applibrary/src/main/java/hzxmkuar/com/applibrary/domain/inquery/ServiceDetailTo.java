package hzxmkuar.com.applibrary.domain.inquery;

import java.util.List;

import lombok.Data;

/**
 * Created by Administrator on 2018/9/26.
 */
@Data
public class ServiceDetailTo {

    /**
     * total : 2
     * lists : [{"id":8,"service_name":"服务1","service_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png","get_properties_list":[{"id":18,"ptitle":"面积要求","ptype":1,"desc":"","conflist":[{"id":1,"title":"需要屏幕面积","content":"哈哈"}]},{"id":19,"ptitle":"服务要求","ptype":3,"desc":"服务要求","conflist":[{"id":"1","title":"包送货","content":false},{"id":"2","title":"包安装","content":true},{"id":"3","title":"包拆卸","content":true}]},{"id":20,"ptitle":"文件说明","ptype":5,"desc":"文件说明","conflist":[{"id":"1","title":"舞台规格","content":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-06-29/62722e08e6f9a97070a0e970715b6396.jpg"},{"id":"2","title":"设计图片","content":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-06-29/25b9d2500cc39d1ae9b48ada7d887ba9.jpg"}]},{"id":26,"ptitle":"补充要求","ptype":6,"desc":"多些说明吧，嘎嘎","conflist":[{"id":1,"title":"多些说明吧，嘎嘎","content":""}]}]},{"id":13,"service_name":"服务13","service_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png","get_properties_list":[{"id":21,"ptitle":"场地面积","ptype":1,"desc":"场地面积","conflist":[{"id":1,"title":"","content":"要好一点的"}]},{"id":22,"ptitle":"服务要求","ptype":3,"desc":"","conflist":[{"id":"1","title":"包送货","content":false},{"id":"2","title":"包安装","content":false},{"id":"3","title":"包吃住","content":true}]}]}]
     */

    private int total;
    private List<ListsBean> lists;

    @Data
    public static class ListsBean {
        /**
         * id : 8
         * service_name : 服务1
         * service_img : http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png
         * get_properties_list : [{"id":18,"ptitle":"面积要求","ptype":1,"desc":"","conflist":[{"id":1,"title":"需要屏幕面积","content":"哈哈"}]},{"id":19,"ptitle":"服务要求","ptype":3,"desc":"服务要求","conflist":[{"id":"1","title":"包送货","content":false},{"id":"2","title":"包安装","content":true},{"id":"3","title":"包拆卸","content":true}]},{"id":20,"ptitle":"文件说明","ptype":5,"desc":"文件说明","conflist":[{"id":"1","title":"舞台规格","content":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-06-29/62722e08e6f9a97070a0e970715b6396.jpg"},{"id":"2","title":"设计图片","content":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-06-29/25b9d2500cc39d1ae9b48ada7d887ba9.jpg"}]},{"id":26,"ptitle":"补充要求","ptype":6,"desc":"多些说明吧，嘎嘎","conflist":[{"id":1,"title":"多些说明吧，嘎嘎","content":""}]}]
         */

        private int id;
        private String service_name;
        private String service_img;
        private List<GetPropertiesListBean> get_properties_list;

        @Data
        public static class GetPropertiesListBean {
            /**
             * id : 18
             * ptitle : 面积要求
             * ptype : 1
             * desc :
             * conflist : [{"id":1,"title":"需要屏幕面积","content":"哈哈"}]
             */

            private int id;
            private String ptitle;
            private int ptype;
            private String desc;
            private List<ConflistBean> conflist;

            @Data

            public static class ConflistBean {
                /**
                 * id : 1
                 * title : 需要屏幕面积
                 * content : 哈哈
                 */

                private int id;
                private String title;
                private String content;


            }
        }
    }
}

package hzxmkuar.com.applibrary.main;

import java.util.List;

import lombok.Data;

/**
 * Created by Administrator on 2018/8/29.
 */
@Data
public class DemandSearchTo {

    /**
     * lists : [{"cate_id":6,"cate_name":"二级分类a","lists":[{"id":8,"service_img":288,"service_name":"服务1"},{"id":9,"service_img":0,"service_name":"服务2"},{"id":10,"service_img":0,"service_name":"服务3"},{"id":11,"service_img":0,"service_name":"服务4"}]},{"cate_id":7,"cate_name":"二级分类b","lists":[{"id":12,"service_img":288,"service_name":"服务12"},{"id":13,"service_img":288,"service_name":"服务13"}]},{"cate_id":14,"cate_name":"二级分类121alal","lists":[{"id":17,"service_img":287,"service_name":"服务31"}]}]
     * total : 3
     */

    private int total;
    private List<ListsBeanX> lists;


    @Data
    public static class ListsBeanX {
        /**
         * cate_id : 6
         * cate_name : 二级分类a
         * lists : [{"id":8,"service_img":288,"service_name":"服务1"},{"id":9,"service_img":0,"service_name":"服务2"},{"id":10,"service_img":0,"service_name":"服务3"},{"id":11,"service_img":0,"service_name":"服务4"}]
         */

        private String cate_id;
        private String cate_name;
        private List<ListsBean> lists;


        @Data
        public static class ListsBean {
            /**
             * id : 8
             * service_img : 288
             * service_name : 服务1
             */

            private String id;
            private String service_img;
            private String service_name;


        }
    }
}

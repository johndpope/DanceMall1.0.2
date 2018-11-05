package hzxmkuar.com.applibrary.domain.merchant;

import java.util.List;

import lombok.Data;

/**
 * Created by Administrator on 2018/10/11.
 */
@Data
public class ServiceListTo {

    /**
     * lists : [{"id":1,"service_name":"大屏租赁","list2":[{"id":6,"service_name":"小间距LED显示屏"},{"id":7,"service_name":"舞台租赁LED显示屏"}]},{"id":2,"service_name":"灯光租赁","list2":[{"id":14,"service_name":"二级分类121alal"},{"id":15,"service_name":"二级分类21alal"},{"id":16,"service_name":"二级分类23aaa"}]},{"id":3,"service_name":"音响租赁","list2":[]},{"id":4,"service_name":"舞台搭建","list2":[]},{"id":5,"service_name":"喷绘花艺","list2":[]},{"id":6,"service_name":"小间距LED显示屏","list2":[{"id":8,"service_name":"p1.25超清"},{"id":9,"service_name":"p1.66超清"},{"id":10,"service_name":"p1.875超清"},{"id":11,"service_name":"p1.923超清"}]},{"id":7,"service_name":"舞台租赁LED显示屏","list2":[{"id":12,"service_name":"P4租赁全彩"},{"id":13,"service_name":"P5租赁全彩"},{"id":24,"service_name":"P2.5租赁全彩"},{"id":25,"service_name":"P4.81租赁全彩"},{"id":26,"service_name":"P3租赁全彩"},{"id":27,"service_name":"P6租赁全彩"}]},{"id":8,"service_name":"p1.25超清","list2":[]},{"id":9,"service_name":"p1.66超清","list2":[]},{"id":10,"service_name":"p1.875超清","list2":[]},{"id":11,"service_name":"p1.923超清","list2":[]},{"id":12,"service_name":"P4租赁全彩","list2":[]},{"id":13,"service_name":"P5租赁全彩","list2":[]},{"id":14,"service_name":"二级分类121alal","list2":[{"id":17,"service_name":"服务31"}]},{"id":15,"service_name":"二级分类21alal","list2":[{"id":21,"service_name":"服务aaa"}]},{"id":16,"service_name":"二级分类23aaa","list2":[{"id":22,"service_name":"服务cca"}]},{"id":17,"service_name":"服务31","list2":[]},{"id":18,"service_name":"摄影摄像","list2":[]},{"id":19,"service_name":"化妆主持","list2":[]},{"id":20,"service_name":"嘉宾明星","list2":[]},{"id":21,"service_name":"服务aaa","list2":[]},{"id":22,"service_name":"服务cca","list2":[]},{"id":23,"service_name":"桌椅家具","list2":[]},{"id":24,"service_name":"P2.5租赁全彩","list2":[]},{"id":25,"service_name":"P4.81租赁全彩","list2":[]},{"id":26,"service_name":"P3租赁全彩","list2":[]},{"id":27,"service_name":"P6租赁全彩","list2":[]}]
     * total : 27
     */

    private int total;
    private List<ListsBean> lists;


    @Data
    public static class ListsBean {
        /**
         * id : 1
         * service_name : 大屏租赁
         * list2 : [{"id":6,"service_name":"小间距LED显示屏"},{"id":7,"service_name":"舞台租赁LED显示屏"}]
         */

        private int id;
        private String service_name;
        private List<ListsBean> list2;
    }
}

package hzxmkuar.com.applibrary.domain.inquery;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import rx.Observable;

/**
 * Created by Administrator on 2018/9/13.
 */
@Data
public class ConfirmInquiryPageTo implements Serializable{

    /**
     * service_address : 浙江杭州市下沙
     * contact_name : ddd
     * contact_telphone : 1388888888
     * service_list : [{"id":8,"service_name":"服务1","service_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png","get_properties_list":[{"id":18,"ptitle":"面积要求","ptype":1,"desc":"","conflist":[{"id":1,"title":"需要屏幕面积"}]},{"id":19,"ptitle":"服务要求","ptype":3,"desc":"服务要求","conflist":[{"id":"1","title":"包送货"},{"id":"2","title":"包安装"},{"id":"3","title":"包拆卸"}]},{"id":20,"ptitle":"文件说明","ptype":5,"desc":"文件说明","conflist":[{"id":"1","title":"舞台规格"},{"id":"2","title":"设计图片"}]},{"id":23,"ptitle":"","ptype":0,"desc":"","conflist":[{"id":1,"title":""}]}]},{"id":13,"service_name":"服务13","service_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png","get_properties_list":[{"id":21,"ptitle":"场地面积","ptype":1,"desc":"场地面积","conflist":[{"id":1,"title":""}]},{"id":22,"ptitle":"服务要求","ptype":3,"desc":"","conflist":[{"id":"1","title":"包送货"},{"id":"2","title":"包安装"},{"id":"3","title":"包吃住"}]},{"id":24,"ptitle":"","ptype":0,"desc":"","conflist":[{"id":1,"title":""}]}]}]
     */

    private String service_address;
   private ContactInfoTo contact_info;
    private List<ServiceListBean> service_list;


    @Data
    public static class ServiceListBean implements Serializable{
        /**
         * id : 8
         * service_name : 服务1
         * service_img : http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png
         * get_properties_list : [{"id":18,"ptitle":"面积要求","ptype":1,"desc":"","conflist":[{"id":1,"title":"需要屏幕面积"}]},{"id":19,"ptitle":"服务要求","ptype":3,"desc":"服务要求","conflist":[{"id":"1","title":"包送货"},{"id":"2","title":"包安装"},{"id":"3","title":"包拆卸"}]},{"id":20,"ptitle":"文件说明","ptype":5,"desc":"文件说明","conflist":[{"id":"1","title":"舞台规格"},{"id":"2","title":"设计图片"}]},{"id":23,"ptitle":"","ptype":0,"desc":"","conflist":[{"id":1,"title":""}]}]
         */

        private int id;
        private String service_name;
        private String service_img;
        private List<GetPropertiesListBean> get_properties_list;

        @Data
        public static class GetPropertiesListBean implements Serializable{
            /**
             * id : 18
             * ptitle : 面积要求
             * ptype : 1
             * desc :
             * conflist : [{"id":1,"title":"需要屏幕面积"}]
             */

            private int id;
            private String ptitle;
            private int ptype;
            private String desc;
            private List<ConflistBean> conflist;



            @Data
            public static class ConflistBean implements Serializable{
                /**
                 * id : 1
                 * title : 需要屏幕面积
                 */

                private int id;
                private String title;
                private Observable<String> inputData;

            }
        }
    }
    @Data
    public class ContactInfoTo implements Serializable{
        private int contact_id;
        private String contact_name;
        private String contact_telphone;
    }
}

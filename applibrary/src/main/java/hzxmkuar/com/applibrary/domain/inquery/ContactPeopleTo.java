package hzxmkuar.com.applibrary.domain.inquery;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * Created by Administrator on 2018/9/12.
 */
@Data
public class ContactPeopleTo implements Serializable{

    /**
     * total : 2
     * lists : [{"id":15,"consignee":"赵世杰","telephone":"18511144335","finaladdress":"北京市北京市东城区朝阳广场","is_default":"2","province":"北京市","city":"北京市","area":"东城区","address":"朝阳广场"},{"id":14,"consignee":"曾祖贵","telephone":"18278979612","finaladdress":"广西壮族自治区南宁市青秀区金洲路33号广西人才大厦11楼","is_default":"1","province":"广西壮族自治区","city":"南宁市","area":"青秀区","address":"金洲路33号广西人才大厦11楼"}]
     */

    private int total;
    private List<ListsBean> lists;


    @Data
    public static class ListsBean implements Serializable{
        /**
         * id : 15
         * consignee : 赵世杰
         * telephone : 18511144335
         * finaladdress : 北京市北京市东城区朝阳广场
         * is_default : 2
         * province : 北京市
         * city : 北京市
         * area : 东城区
         * address : 朝阳广场
         */

        private int id;
        private String consignee;
        private String telephone;
        private String finaladdress;
        private String is_default;
        private String province;
        private String city;
        private String area;
        private String address;

    }
}

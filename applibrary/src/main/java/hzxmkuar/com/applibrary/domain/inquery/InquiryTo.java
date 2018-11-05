package hzxmkuar.com.applibrary.domain.inquery;

import java.util.List;

import lombok.Data;

/**
 * Created by Administrator on 2018/9/11.
 */
@Data
public class InquiryTo {

    /**
     * total : 5
     * page : 1
     * limit : 100
     * remainder : 0
     * lists : [{"id":1,"service_img":"","service_name":"大分类1","level":1,"list2":[{"id":6,"service_name":"二级分类a","service_img":"","level":2,"list3":[{"id":8,"service_name":"服务1","service_img":"","level":3},{"id":9,"service_name":"服务2","service_img":"","level":3},{"id":10,"service_name":"服务3","service_img":"","level":3},{"id":11,"service_name":"服务4","service_img":"","level":3}]},{"id":7,"service_name":"二级分类b","service_img":"","level":2,"list3":[{"id":12,"service_name":"服务12","service_img":"","level":3},{"id":13,"service_name":"服务13","service_img":"","level":3}]}]},{"id":2,"service_img":"","service_name":"大分类2","level":1,"list2":[{"id":14,"service_name":"二级分类121alal","service_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-01/c563aecbd01218f33270a5cad9ef6cb9.png","level":2,"list3":[{"id":17,"service_name":"服务31","service_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-01/c563aecbd01218f33270a5cad9ef6cb9.png","level":3}]},{"id":15,"service_name":"二级分类21alal","service_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-01/c563aecbd01218f33270a5cad9ef6cb9.png","level":2,"list3":[]},{"id":16,"service_name":"二级分类23aaa","service_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-01/c563aecbd01218f33270a5cad9ef6cb9.png","level":2,"list3":[]}]},{"id":3,"service_img":"","service_name":"大分类3","level":1,"list2":[]},{"id":4,"service_img":"","service_name":"大分类4","level":1,"list2":[]},{"id":5,"service_img":"","service_name":"大分类5","level":1,"list2":[]}]
     */

    private int total;
    private int page;
    private int limit;
    private int remainder;
    private List<InquiryListTo> lists;

}

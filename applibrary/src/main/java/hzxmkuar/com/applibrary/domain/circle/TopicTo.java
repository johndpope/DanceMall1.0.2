package hzxmkuar.com.applibrary.domain.circle;

import java.util.List;

import hzxmkuar.com.applibrary.domain.BaseParam;
import lombok.Data;

/**
 * Created by Administrator on 2018/9/16.
 */
@Data
public class TopicTo extends BaseParam {

    /**
     * lists : [{"id":1,"topic_name":"行业动态","topic_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-12/4992c1bb18486e5e49e83a32866368ac.png","list2":[{"id":9,"topic_name":"行业1","topic_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-12/daf4917de601d86a63c1509670e3a593.png"},{"id":10,"topic_name":"动态1","topic_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-12/daf4917de601d86a63c1509670e3a593.png"},{"id":11,"topic_name":"行业3","topic_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-12/daf4917de601d86a63c1509670e3a593.png"}]},{"id":2,"topic_name":"资讯问答","topic_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-12/d104fc0ad1816934622fb8d569adb569.png","list2":[{"id":12,"topic_name":"资讯1","topic_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-12/f6c133144e7a3b94b09b23a6c409d4c8.png"},{"id":13,"topic_name":"问答2","topic_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-12/2a1d39580e9ce17d6842ac70c8d87604.png"}]},{"id":3,"topic_name":"货品交易","topic_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-12/4992c1bb18486e5e49e83a32866368ac.png","list2":[]},{"id":4,"topic_name":"求职招聘","topic_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-12/364aa0639e0c48c9b9f2396ce6f923eb.png","list2":[]},{"id":5,"topic_name":"国际赛事","topic_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-12/a53ed96f94bd66d2c12e60d650ac7dc6.png","list2":[]},{"id":6,"topic_name":"舞美活动","topic_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-12/f6c133144e7a3b94b09b23a6c409d4c8.png","list2":[]},{"id":7,"topic_name":"线下交流","topic_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-12/2a1d39580e9ce17d6842ac70c8d87604.png","list2":[]},{"id":8,"topic_name":"舞美杂谈","topic_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-12/daf4917de601d86a63c1509670e3a593.png","list2":[]},{"id":9,"topic_name":"行业1","topic_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-12/daf4917de601d86a63c1509670e3a593.png","list2":[]},{"id":10,"topic_name":"动态1","topic_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-12/daf4917de601d86a63c1509670e3a593.png","list2":[]},{"id":11,"topic_name":"行业3","topic_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-12/daf4917de601d86a63c1509670e3a593.png","list2":[]},{"id":12,"topic_name":"资讯1","topic_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-12/f6c133144e7a3b94b09b23a6c409d4c8.png","list2":[]},{"id":13,"topic_name":"问答2","topic_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-12/2a1d39580e9ce17d6842ac70c8d87604.png","list2":[]}]
     * total : 13
     */

    private int total;
    private List<ListsBean> lists;


    @Data
    public static class ListsBean {
        /**
         * id : 1
         * topic_name : 行业动态
         * topic_img : http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-12/4992c1bb18486e5e49e83a32866368ac.png
         * list2 : [{"id":9,"topic_name":"行业1","topic_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-12/daf4917de601d86a63c1509670e3a593.png"},{"id":10,"topic_name":"动态1","topic_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-12/daf4917de601d86a63c1509670e3a593.png"},{"id":11,"topic_name":"行业3","topic_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-12/daf4917de601d86a63c1509670e3a593.png"}]
         */
        private int id;
        private String topic_name;
        private String topic_img;
        private List<ListsBean> list2;

    }
}

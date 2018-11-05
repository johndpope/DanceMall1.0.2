package hzxmkuar.com.applibrary.domain.circle;

import java.util.List;

import lombok.Data;

/**
 * Created by Administrator on 2018/9/20.
 */
@Data
public class CircleBannerTo {


    /**
     * lists : [{"id":1,"topic_name":"行业动态","topic_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-12/4992c1bb18486e5e49e83a32866368ac.png"},{"id":2,"topic_name":"资讯问答","topic_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-12/d104fc0ad1816934622fb8d569adb569.png"},{"id":3,"topic_name":"货品交易","topic_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-12/4992c1bb18486e5e49e83a32866368ac.png"},{"id":4,"topic_name":"求职招聘","topic_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-12/364aa0639e0c48c9b9f2396ce6f923eb.png"},{"id":5,"topic_name":"国际赛事","topic_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-12/a53ed96f94bd66d2c12e60d650ac7dc6.png"},{"id":6,"topic_name":"舞美活动","topic_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-12/f6c133144e7a3b94b09b23a6c409d4c8.png"},{"id":7,"topic_name":"线下交流","topic_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-12/2a1d39580e9ce17d6842ac70c8d87604.png"},{"id":8,"topic_name":"舞美杂谈","topic_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-12/daf4917de601d86a63c1509670e3a593.png"}]
     * total : 8
     */

    private int total;
    private List<ListsBean> lists;


    @Data
    public static class ListsBean {
        /**
         * id : 1
         * topic_name : 行业动态
         * topic_img : http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-12/4992c1bb18486e5e49e83a32866368ac.png
         */

        private int id;
        private String topic_name;
        private String topic_img;

    }
}

package hzxmkuar.com.applibrary.domain.circle;

import java.util.List;

import lombok.Data;

/**
 * Created by Administrator on 2018/9/19.
 */
@Data
public class CircleTo {

    /**
     * total : 3
     * page : 1
     * limit : 10
     * remainder : 0
     * lists : [{"id":18,"content":"将经济适用住房、限价商品住房整合为共有产权住房，实行政府与购房人按份共有产权，面向在本市无自有住房且5年内无住房登记信息","pic_list":[{"id":330,"pic":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-14/a6a1623f2aef34cc1e5181617f2a21b7.png"},{"id":331,"pic":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-14/562dd35546b417544e470f67870a1204.png"}],"is_top":0,"position":"杭州下沙","topic":"资讯1","views":0,"likes":0,"comments":0,"user_info":{"userid":88,"headimgurl":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-05-29/30bfc5eb7862333d1995f020d9f356da.jpg","username":"helloworld","user_tag":"荣耀王者"}},{"id":17,"content":"国家版权局相关负责人要求相关短视频平台企业要进一步提高版权保护意识，切实加强版权制度建设，全面履行企业主体责任。","pic_list":[{"id":334,"pic":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-14/062bf9273814298a410bdb194cf05cf0.png"},{"id":335,"pic":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-14/04b5499e9218e283e176d9ecaaf2020e.png"},{"id":336,"pic":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-14/d380b5f0c840d06dd55151909f5a5982.png"}],"is_top":0,"position":"","topic":"资讯1","views":0,"likes":0,"comments":0,"user_info":{"userid":88,"headimgurl":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-05-29/30bfc5eb7862333d1995f020d9f356da.jpg","username":"helloworld","user_tag":"荣耀王者"}},{"id":16,"content":"国家版权局相关负责人要求相关短视频平台企业要进一步提高版权保护意识，切实加强版权制度建设，全面履行企业主体责任。","pic_list":[{"id":334,"pic":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-14/062bf9273814298a410bdb194cf05cf0.png"},{"id":335,"pic":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-14/04b5499e9218e283e176d9ecaaf2020e.png"},{"id":336,"pic":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-14/d380b5f0c840d06dd55151909f5a5982.png"}],"is_top":0,"position":"","topic":"行业3","views":0,"likes":0,"comments":0,"user_info":{"userid":88,"headimgurl":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-05-29/30bfc5eb7862333d1995f020d9f356da.jpg","username":"helloworld","user_tag":"荣耀王者"}}]
     */

    private int total;
    private int page;
    private int limit;
    private int remainder;
    private List<ListsBean> lists;



    @Data
    public static class ListsBean {
        /**
         * id : 18
         * content : 将经济适用住房、限价商品住房整合为共有产权住房，实行政府与购房人按份共有产权，面向在本市无自有住房且5年内无住房登记信息
         * pic_list : [{"id":330,"pic":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-14/a6a1623f2aef34cc1e5181617f2a21b7.png"},{"id":331,"pic":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-14/562dd35546b417544e470f67870a1204.png"}]
         * is_top : 0
         * position : 杭州下沙
         * topic : 资讯1
         * views : 0
         * likes : 0
         * comments : 0
         * user_info : {"userid":88,"headimgurl":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-05-29/30bfc5eb7862333d1995f020d9f356da.jpg","username":"helloworld","user_tag":"荣耀王者"}
         */

        private int id;
        private String content;
        private int is_top;
        private String position;
        private String topic;
        private int views;
        private int likes;
        private int comments;
        private UserInfoBean user_info;
        private List<PicListBean> pic_list;
        private String dateline;


        @Data
        public static class UserInfoBean {
            /**
             * userid : 88
             * headimgurl : http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-05-29/30bfc5eb7862333d1995f020d9f356da.jpg
             * username : helloworld
             * user_tag : 荣耀王者
             */

            private int userid;
            private String headimgurl;
            private String username;
            private String user_tag;


        }

        @Data
        public static class PicListBean {
            /**
             * id : 330
             * pic : http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-14/a6a1623f2aef34cc1e5181617f2a21b7.png
             */

            private int id;
            private String pic;

        }
    }
}

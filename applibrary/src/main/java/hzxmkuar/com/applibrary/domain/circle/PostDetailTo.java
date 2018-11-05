package hzxmkuar.com.applibrary.domain.circle;

import java.util.List;

import lombok.Data;

/**
 * Created by Administrator on 2018/9/19.
 */
@Data
public class PostDetailTo {

    /**
     * id : 16
     * content : 国家版权局相关负责人要求相关短视频平台企业要进一步提高版权保护意识，切实加强版权制度建设，全面履行企业主体责任。
     * pic_list : [{"id":334,"pic":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-14/062bf9273814298a410bdb194cf05cf0.png"},{"id":335,"pic":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-14/04b5499e9218e283e176d9ecaaf2020e.png"},{"id":336,"pic":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-14/d380b5f0c840d06dd55151909f5a5982.png"}]
     * topic : 行业3
     * views : 8
     * likes : 1
     * comments : 11
     * user_info : {"userid":88,"headimgurl":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-05-29/30bfc5eb7862333d1995f020d9f356da.jpg","username":"helloworld","user_tag":"荣耀王者"}
     * comments_list : [{"id":28,"post_time":1536999344,"user_info":{"userid":88,"headimgurl":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-05-29/30bfc5eb7862333d1995f020d9f356da.jpg","username":"helloworld","user_tag":"荣耀王者"},"content":"开心啊放假了","list2":[{"id":29,"content":"就是这个feel，倍儿爽","from_user":{"userid":88,"username":"helloworld"},"to_user":{"userid":88,"username":"helloworld"}},{"id":30,"content":"哈哈哈，","from_user":{"userid":88,"username":"helloworld"},"to_user":{"userid":88,"username":"helloworld"}},{"id":31,"content":"呵呵","from_user":{"userid":88,"username":"helloworld"},"to_user":{"userid":88,"username":"helloworld"}}]}]
     */

    private int id;
    private String content;
    private String topic;
    private int views;
    private int likes;
    private int comments;
    private String dateline;
    private UserInfoBean user_info;
    private List<PicListBean> pic_list;
    private List<CommentsListBean> comments_list;
    private int is_collected;
    private int is_likes;


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
        private String telephone;
    }

    @Data
    public static class PicListBean {
        /**
         * id : 334
         * pic : http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-14/062bf9273814298a410bdb194cf05cf0.png
         */

        private int id;
        private String pic;

    }

    @Data
    public static class CommentsListBean {
        /**
         * id : 28
         * post_time : 1536999344
         * user_info : {"userid":88,"headimgurl":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-05-29/30bfc5eb7862333d1995f020d9f356da.jpg","username":"helloworld","user_tag":"荣耀王者"}
         * content : 开心啊放假了
         * list2 : [{"id":29,"content":"就是这个feel，倍儿爽","from_user":{"userid":88,"username":"helloworld"},"to_user":{"userid":88,"username":"helloworld"}},{"id":30,"content":"哈哈哈，","from_user":{"userid":88,"username":"helloworld"},"to_user":{"userid":88,"username":"helloworld"}},{"id":31,"content":"呵呵","from_user":{"userid":88,"username":"helloworld"},"to_user":{"userid":88,"username":"helloworld"}}]
         */

        private int id;
        private String post_time;
        private UserInfoTo user_info;
        private String content;
        private String dateline;
        private List<List2Bean> list2;

        @Data
        public static class UserInfoTo {
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
        public static class List2Bean {
            /**
             * id : 29
             * content : 就是这个feel，倍儿爽
             * from_user : {"userid":88,"username":"helloworld"}
             * to_user : {"userid":88,"username":"helloworld"}
             */

            private int id;
            private String content;
            private UserInfoTo from_user;
            private UserInfoTo to_user;


        }
    }
}

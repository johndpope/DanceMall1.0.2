package hzxmkuar.com.applibrary.domain.merchant;

import java.util.List;

import lombok.Data;

/**
 * Created by Administrator on 2018/9/26.
 */
@Data
public class MerchantCommentTo {


    /**
     * total : 4
     * lists : [{"id":31,"content":"呵呵","pic_list":[{"id":"349","pic":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-18/d1dc740bec2d2ac5aa74a9294d77cebb.png"},{"id":"351","pic":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-18/0b1b4fd723376237a988d2a22ba682db.png"}],"dateline":"2018-09","address":"地址待定","services":"服务待定","score":5,"user_info":{"userid":88,"headimgurl":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-05-29/30bfc5eb7862333d1995f020d9f356da.jpg","username":"helloworld","user_tag":"荣耀王者"}},{"id":30,"content":"哈哈哈，","pic_list":[{"id":"349","pic":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-18/d1dc740bec2d2ac5aa74a9294d77cebb.png"},{"id":"351","pic":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-18/0b1b4fd723376237a988d2a22ba682db.png"}],"dateline":"2018-09","address":"地址待定","services":"服务待定","score":5,"user_info":{"userid":88,"headimgurl":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-05-29/30bfc5eb7862333d1995f020d9f356da.jpg","username":"helloworld","user_tag":"荣耀王者"}},{"id":29,"content":"就是这个feel，倍儿爽","pic_list":[{"id":"349","pic":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-18/d1dc740bec2d2ac5aa74a9294d77cebb.png"},{"id":"351","pic":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-18/0b1b4fd723376237a988d2a22ba682db.png"}],"dateline":"2018-09","address":"地址待定","services":"服务待定","score":4,"user_info":{"userid":88,"headimgurl":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-05-29/30bfc5eb7862333d1995f020d9f356da.jpg","username":"helloworld","user_tag":"荣耀王者"}},{"id":28,"content":"开心啊放假了","pic_list":[{"id":"349","pic":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-18/d1dc740bec2d2ac5aa74a9294d77cebb.png"},{"id":"351","pic":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-18/0b1b4fd723376237a988d2a22ba682db.png"}],"dateline":"2018-09","address":"地址待定","services":"服务待定","score":5,"user_info":{"userid":88,"headimgurl":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-05-29/30bfc5eb7862333d1995f020d9f356da.jpg","username":"helloworld","user_tag":"荣耀王者"}}]
     */

    private int total;
    private List<ListsBean> lists;


    @Data
    public static class ListsBean {
        /**
         * id : 31
         * content : 呵呵
         * pic_list : [{"id":"349","pic":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-18/d1dc740bec2d2ac5aa74a9294d77cebb.png"},{"id":"351","pic":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-18/0b1b4fd723376237a988d2a22ba682db.png"}]
         * dateline : 2018-09
         * address : 地址待定
         * services : 服务待定
         * score : 5
         * user_info : {"userid":88,"headimgurl":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-05-29/30bfc5eb7862333d1995f020d9f356da.jpg","username":"helloworld","user_tag":"荣耀王者"}
         */

        private int id;
        private String content;
        private String dateline;
        private String address;
        private String services;
        private int score;
        private UserInfoBean user_info;
        private List<PicListBean> pic_list;


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
             * id : 349
             * pic : http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-18/d1dc740bec2d2ac5aa74a9294d77cebb.png
             */

            private String id;
            private String pic;


        }
    }
}

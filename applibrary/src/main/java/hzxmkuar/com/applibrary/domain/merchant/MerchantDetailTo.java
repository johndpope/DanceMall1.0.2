package hzxmkuar.com.applibrary.domain.merchant;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * Created by Administrator on 2018/9/25.
 **/
@Data
public class MerchantDetailTo implements Serializable{


    /**
     * bus_info : {"shop_id":15,"bus_uid":88,"shop_name":"天天开心","shop_banner":"","ratings":"0.00","stype":1,"order_num":0,"finaladdress":"解放大道109号","telephone":""}
     * service_list : [{"cate_id":6,"cate_name":"二级分类a","lists":[{"id":8,"service_img":288,"service_name":"服务1"},{"id":11,"service_img":0,"service_name":"服务4"}]},{"cate_id":7,"cate_name":"二级分类b","lists":[{"id":12,"service_img":288,"service_name":"服务12"}]}]
     * case_list : [{"id":28,"case_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-18/ec2b8b51217efcaf29fa59ee6546f216.jpg","case_desc":"开心啊放假了"},{"id":29,"case_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-18/2745e2bbe114e50ea5777113dacf2812.jpg","case_desc":"就是这个feel，倍儿爽"}]
     * user_reviews : {"total":4,"lists":[{"id":31,"content":"呵呵","pic_list":[{"id":"349","pic":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-18/d1dc740bec2d2ac5aa74a9294d77cebb.png"},{"id":"351","pic":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-18/0b1b4fd723376237a988d2a22ba682db.png"}],"dateline":"2018-09","address":"地址待定","services":"服务待定","score":5,"user_info":{"userid":88,"headimgurl":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-05-29/30bfc5eb7862333d1995f020d9f356da.jpg","username":"helloworld","user_tag":"荣耀王者"}},{"id":30,"content":"哈哈哈，","pic_list":[{"id":"349","pic":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-18/d1dc740bec2d2ac5aa74a9294d77cebb.png"},{"id":"351","pic":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-18/0b1b4fd723376237a988d2a22ba682db.png"}],"dateline":"2018-09","address":"地址待定","services":"服务待定","score":5,"user_info":{"userid":88,"headimgurl":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-05-29/30bfc5eb7862333d1995f020d9f356da.jpg","username":"helloworld","user_tag":"荣耀王者"}}]}
     */

    private BusInfoBean bus_info;
    private UserReviewsBean user_reviews;
    private List<ServiceListBean> service_list;
    private List<CaseListBean> case_list;
    private int is_collected;

    @Data
    public static class BusInfoBean implements Serializable{
        /**
         * shop_id : 15
         * bus_uid : 88
         * shop_name : 天天开心
         * shop_banner :
         * ratings : 0.00
         * stype : 1
         * order_num : 0
         * finaladdress : 解放大道109号
         * telephone :
         */

        private int shop_id;
        private int bus_uid;
        private String shop_name;
        private String shop_banner;
        private String ratings;
        private String stype;
        private int order_num;
        private String finaladdress;
        private String telephone;

    }

    @Data
    public static class UserReviewsBean implements Serializable{
        /**
         * total : 4
         * lists : [{"id":31,"content":"呵呵","pic_list":[{"id":"349","pic":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-18/d1dc740bec2d2ac5aa74a9294d77cebb.png"},{"id":"351","pic":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-18/0b1b4fd723376237a988d2a22ba682db.png"}],"dateline":"2018-09","address":"地址待定","services":"服务待定","score":5,"user_info":{"userid":88,"headimgurl":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-05-29/30bfc5eb7862333d1995f020d9f356da.jpg","username":"helloworld","user_tag":"荣耀王者"}},{"id":30,"content":"哈哈哈，","pic_list":[{"id":"349","pic":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-18/d1dc740bec2d2ac5aa74a9294d77cebb.png"},{"id":"351","pic":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-18/0b1b4fd723376237a988d2a22ba682db.png"}],"dateline":"2018-09","address":"地址待定","services":"服务待定","score":5,"user_info":{"userid":88,"headimgurl":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-05-29/30bfc5eb7862333d1995f020d9f356da.jpg","username":"helloworld","user_tag":"荣耀王者"}}]
         */

        private int total;
        private List<ListsBean> lists;


        @Data
        public static class ListsBean implements Serializable{
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
            public static class UserInfoBean implements Serializable{
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
            public static class PicListBean implements Serializable{
                /**
                 * id : 349
                 * pic : http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-18/d1dc740bec2d2ac5aa74a9294d77cebb.png
                 */

                private String id;
                private String pic;


            }
        }
    }

        @Data
        public static class ServiceListBean implements Serializable{
            /**
             * cate_id : 6
             * cate_name : 二级分类a
             * lists : [{"id":8,"service_img":288,"service_name":"服务1"},{"id":11,"service_img":0,"service_name":"服务4"}]
             */

            private int cate_id;
            private String cate_name;
            private List<ListsBeanX> lists;


            public static class ListsBeanX implements Serializable{
                /**
                 * id : 8
                 * service_img : 288
                 * service_name : 服务1
                 */

                private int id;
                private String service_img;
                private String service_name;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getService_img() {
                    return service_img;
                }

                public void setService_img(String service_img) {
                    this.service_img = service_img;
                }

                public String getService_name() {
                    return service_name;
                }

                public void setService_name(String service_name) {
                    this.service_name = service_name;
                }
            }
        }

        public static class CaseListBean implements Serializable{
            /**
             * id : 28
             * case_img : http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-18/ec2b8b51217efcaf29fa59ee6546f216.jpg
             * case_desc : 开心啊放假了
             */

            private int id;
            private String case_img;
            private String case_desc;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCase_img() {
                return case_img;
            }

            public void setCase_img(String case_img) {
                this.case_img = case_img;
            }

            public String getCase_desc() {
                return case_desc;
            }

            public void setCase_desc(String case_desc) {
                this.case_desc = case_desc;
            }
        }
    }

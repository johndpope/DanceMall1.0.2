package hzxmkuar.com.applibrary.domain.quote;

import java.util.List;

import lombok.Data;

/**
 * Created by Administrator on 2018/9/25.
 */
@Data
public class MyQuoteTo {


    /**
     * total : 5
     * page : 1
     * limit : 10
     * remainder : 0
     * lists : [{"id":70,"service_num":2,"inquiry_sn":"WMH20180920165142665","status_arr":{"status":1,"status_txt":"暂无报价"},"service_list":[{"sid":12,"service_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png"},{"sid":8,"service_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png"}],"valid_time":3094},{"id":69,"service_num":1,"inquiry_sn":"WMH20180920165050334","status_arr":{"status":2,"status_txt":"已报价"},"service_list":[{"sid":12,"service_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png"}],"valid_time":3146},{"id":68,"service_num":1,"inquiry_sn":"WMH20180920165050468","status_arr":{"status":1,"status_txt":"暂无报价"},"service_list":[{"sid":8,"service_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png"}],"valid_time":3146},{"id":67,"service_num":2,"inquiry_sn":"WMH20180920164909693","status_arr":{"status":1,"status_txt":"暂无报价"},"service_list":[{"sid":12,"service_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png"},{"sid":8,"service_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png"}],"valid_time":3247},{"id":66,"service_num":2,"inquiry_sn":"WMH20180920164333146","status_arr":{"status":1,"status_txt":"暂无报价"},"service_list":[{"sid":12,"service_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png"},{"sid":8,"service_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png"}],"valid_time":3583}]
     */

    private int total;
    private int page;
    private int limit;
    private int remainder;
    private List<ListsBean> lists;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getRemainder() {
        return remainder;
    }

    public void setRemainder(int remainder) {
        this.remainder = remainder;
    }

    public List<ListsBean> getLists() {
        return lists;
    }

    public void setLists(List<ListsBean> lists) {
        this.lists = lists;
    }

    public static class ListsBean {
        /**
         * id : 70
         * service_num : 2
         * inquiry_sn : WMH20180920165142665
         * status_arr : {"status":1,"status_txt":"暂无报价"}
         * service_list : [{"sid":12,"service_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png"},{"sid":8,"service_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png"}]
         * valid_time : 3094
         */

        private int id;
        private int service_num;
        private String inquiry_sn;
        private StatusArrBean status_arr;
        private int valid_time;
        private String offer_amount;
        private List<ServiceListBean> service_list;

        public String getOffer_amount() {
            return offer_amount;
        }

        public void setOffer_amount(String offer_amount) {
            this.offer_amount = offer_amount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getService_num() {
            return service_num;
        }

        public void setService_num(int service_num) {
            this.service_num = service_num;
        }

        public String getInquiry_sn() {
            return inquiry_sn;
        }

        public void setInquiry_sn(String inquiry_sn) {
            this.inquiry_sn = inquiry_sn;
        }

        public StatusArrBean getStatus_arr() {
            return status_arr;
        }

        public void setStatus_arr(StatusArrBean status_arr) {
            this.status_arr = status_arr;
        }

        public int getValid_time() {
            return valid_time;
        }

        public void setValid_time(int valid_time) {
            this.valid_time = valid_time;
        }

        public List<ServiceListBean> getService_list() {
            return service_list;
        }

        public void setService_list(List<ServiceListBean> service_list) {
            this.service_list = service_list;
        }

        public static class StatusArrBean {
            /**
             * status : 1
             * status_txt : 暂无报价
             */

            private int status;
            private String status_txt;

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getStatus_txt() {
                return status_txt;
            }

            public void setStatus_txt(String status_txt) {
                this.status_txt = status_txt;
            }
        }

        public static class ServiceListBean {
            /**
             * sid : 12
             * service_img : http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-04/7b46ad3b58d3d541d0eb343011cf82f1.png
             */

            private int sid;
            private String service_img;

            public int getSid() {
                return sid;
            }

            public void setSid(int sid) {
                this.sid = sid;
            }

            public String getService_img() {
                return service_img;
            }

            public void setService_img(String service_img) {
                this.service_img = service_img;
            }
        }
    }
}

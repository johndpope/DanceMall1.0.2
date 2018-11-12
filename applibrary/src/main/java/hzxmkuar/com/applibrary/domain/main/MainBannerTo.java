package hzxmkuar.com.applibrary.domain.main;

import java.util.List;

import lombok.Data;

/**
 * Created by Administrator on 2018/9/16.
 */
@Data
public class MainBannerTo {

    private List<IndexSlideshowBean> index_slideshow;
    private List<IndexNewsHotBean> index_news_hot;
    private List<IndexMenuBean> index_menu;



    @Data
    public static class IndexSlideshowBean {
        /**
         * id : 5
         * target_module : 0
         * target_id : 0
         * urls : #
         * pic : http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-08-19/803433eeb7603c4382fdce036ae0a16a.jpg
         */

        private int id;
        private int target_module;
        private String target_id;
        private String urls;
        private String pic;
        private String tag;


    }

    @Data
    public static class IndexNewsHotBean {
        /**
         * id : 1
         * news_title : 共享舞美招商大会启动中11
         */

        private int id;
        private String news_title;


    }
@Data
    public static class IndexMenuBean {
        /**
         * id : 1
         * service_name : 大屏租赁
         * service_img : http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-14/1a75d345f5b42463abd8c5b3589b7938.png
         */

        private int id;
        private String service_name;
        private String service_img;


    }
}

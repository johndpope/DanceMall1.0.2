package hzxmkuar.com.applibrary.domain.merchant;

import java.util.List;

import lombok.Data;

/**
 * Created by Administrator on 2018/9/26.
 */
@Data
public class MerchantCaseTo {

    /**
     * total : 4
     * lists : [{"id":28,"case_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-18/ec2b8b51217efcaf29fa59ee6546f216.jpg","case_desc":"开心啊放假了"},{"id":29,"case_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-18/2745e2bbe114e50ea5777113dacf2812.jpg","case_desc":"就是这个feel，倍儿爽"},{"id":30,"case_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-18/73df2eac823814a6fd0d0c541e0e201a.jpg","case_desc":"哈哈哈，"},{"id":31,"case_img":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-18/28a476303ba2068c209c427ba42f4606.jpg","case_desc":"呵呵"}]
     */

    private int total;
    private List<ListsBean> lists;


    @Data
    public static class ListsBean {
        /**
         * id : 28
         * case_img : http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-18/ec2b8b51217efcaf29fa59ee6546f216.jpg
         * case_desc : 开心啊放假了
         */

        private int id;
        private String case_img;
        private String case_desc;


    }
}

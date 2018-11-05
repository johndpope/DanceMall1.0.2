package hzxmkuar.com.applibrary.domain.merchant;

import java.util.List;

import lombok.Data;

/**
 * Created by Administrator on 2018/10/11.
 */
@Data
public class MerchantCityTo {

    /**
     * lists : [{"id":935,"area":"上城区","shortname":"上城"},{"id":937,"area":"江干区","shortname":"江干"},{"id":939,"area":"西湖区","shortname":"西湖"}]
     * total : 3
     */

    private int total;
    private List<ListsBean> lists;

   @Data
    public static class ListsBean {
        /**
         * id : 935
         * area : 上城区
         * shortname : 上城
         */

        private int id;
        private String area;
        private String shortname;


    }
}

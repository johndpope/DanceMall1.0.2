package hzxmkuar.com.applibrary.domain.merchant;

import lombok.Data;

/**
 * Created by Administrator on 2018/10/15.
 */
@Data
public class ExtensionTo {

    /**
     * sjzd : {"price_by_month":50,"valid_time":"2018-10-28","is_valid":1}
     * bjzd : {"price_by_month":20,"valid_time":"","is_valid":0}
     */

    private SjzdBean sjzd;
    private BjzdBean bjzd;



    @Data
    public static class SjzdBean {
        /**
         * price_by_month : 50
         * valid_time : 2018-10-28
         * is_valid : 1
         */

        private int price_by_month;
        private String valid_time;
        private int is_valid;


    }

    @Data
    public static class BjzdBean {
        /**
         * price_by_month : 20
         * valid_time :
         * is_valid : 0
         */

        private int price_by_month;
        private String valid_time;
        private int is_valid;


    }
}

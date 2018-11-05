package hzxmkuar.com.applibrary.domain.merchant;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * Created by Administrator on 2018/10/12.
 */
@Data
public class ShopInfoTo implements Serializable{

    /**
     * shop_id : 15
     * shop_logo : {"imgid":12,"pic":"/uploads/picture/2018-04-12/2dedfebf9949ae5cf1105f200b0ffbd8.jpg"}
     * shop_name : 天天开心
     * shop_banner : {"imgid":354,"pic":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-18/ec2b8b51217efcaf29fa59ee6546f216.jpg"}
     * main_service : 8,11,12
     * case_example : [{"id":28,"imgid":354,"pic":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-18/ec2b8b51217efcaf29fa59ee6546f216.jpg"},{"id":29,"imgid":352,"pic":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-18/2745e2bbe114e50ea5777113dacf2812.jpg"},{"id":30,"imgid":363,"pic":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-18/73df2eac823814a6fd0d0c541e0e201a.jpg"},{"id":31,"imgid":361,"pic":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-18/28a476303ba2068c209c427ba42f4606.jpg"}]
     * address : 820,821,822
     * detail_address : 解放大道109号
     * business_type : 1
     * aptitude_name :
     * aptitude_license :
     */

    private int shop_id;
    private ShopLogoBean shop_logo;
    private String shop_name;
    private ShopBannerBean shop_banner;
    private String main_service;
    private String address;
    private String detail_address;
    private int business_type;
    private String aptitude_name;
    private List<AptitudeTo> aptitude_license;
    private List<CaseExampleBean> case_example;

    @Data
    public static class ShopLogoBean implements Serializable{
        /**
         * imgid : 12
         * pic : /uploads/picture/2018-04-12/2dedfebf9949ae5cf1105f200b0ffbd8.jpg
         */

        private int imgid;
        private String pic;


    }

    @Data
    public static class ShopBannerBean implements Serializable{
        /**
         * imgid : 354
         * pic : http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-18/ec2b8b51217efcaf29fa59ee6546f216.jpg
         */

        private int imgid;
        private String pic;


    }

    @Data
    public static class CaseExampleBean implements Serializable{
        /**
         * id : 28
         * imgid : 354
         * pic : http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-09-18/ec2b8b51217efcaf29fa59ee6546f216.jpg
         */

        private int id;
        private int imgid;
        private String pic;
        private String desc;

    }
    @Data
    public static class AptitudeTo implements Serializable{
        private String pic;
        private String imgid;
    }
}

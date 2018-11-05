package hzxmkuar.com.applibrary.domain.integral;

import java.util.List;

import lombok.Data;

/**
 * Created by Administrator on 2018/9/28.
 */
@Data
public class GoodsTo {


    /**
     * total : 5
     * page : 1
     * limit : 100
     * remainder : 0
     * lists : [{"goods_id":5,"goods_name":"cd收纳盒 家用dvd收纳碟片光盘盒日本进口漫画专辑整理 ps4收纳箱","goods_image":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-10-13/0f7f143cdd961d4af25a6a1613406041.jpg","goods_integral":46},{"goods_id":4,"goods_name":"胖宠暖手宝|移动电源女学生可爱随身便携充电保暖防爆迷你电暖宝","goods_image":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-10-13/585ae9a3c2805f025951974f6e6e902e.jpg","goods_integral":148},{"goods_id":3,"goods_name":"女神迷你口袋五折超轻小晴雨伞两用女防晒防紫外线折叠遮阳太阳伞","goods_image":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-10-13/51b7627a3c732150d5e8d6692d4034bf.jpg","goods_integral":48.8},{"goods_id":2,"goods_name":"日式家居拖鞋女冬季厚底保暖棉拖鞋男情侣室内居家用防滑毛毛拖鞋","goods_image":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-10-13/1a30b103bb1252ec401bae961031a13b.jpg","goods_integral":32.8},{"goods_id":1,"goods_name":"Midea/美的 MK-H415E2J食品级304不锈钢防烫电热水壶开水煲烧水壶","goods_image":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-10-13/ea05c40478733569eb39dd13a9be1624.jpg","goods_integral":89}]
     */

    private int total;
    private int page;
    private int limit;
    private int remainder;
    private List<ListsBean> lists;


    @Data
    public static class ListsBean {
        /**
         * goods_id : 5
         * goods_name : cd收纳盒 家用dvd收纳碟片光盘盒日本进口漫画专辑整理 ps4收纳箱
         * goods_image : http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-10-13/0f7f143cdd961d4af25a6a1613406041.jpg
         * goods_integral : 46
         */

        private int goods_id;
        private String goods_name;
        private String goods_image;
        private String goods_integral;


    }
}

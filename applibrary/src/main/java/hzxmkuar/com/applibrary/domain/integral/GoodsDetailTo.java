package hzxmkuar.com.applibrary.domain.integral;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * Created by Administrator on 2018/9/28.
 */
@Data
public class GoodsDetailTo implements Serializable{





        private int goods_id;
        private String goods_name;
        private String goods_image;
        private String desc="暂无内容";
        private String purchase_notes="暂无内容";
        private int goods_integral;



}

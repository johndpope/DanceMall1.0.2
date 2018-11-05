package hzxmkuar.com.applibrary.domain.order;

import hzxmkuar.com.applibrary.domain.BaseParam;
import lombok.Data;

/**
 * Created by Administrator on 2018/9/18.
 */
@Data
public class AddOrderParam extends BaseParam{
    private int payment_mode;
    private int busoffer_id;
    private int coupon_id;
    private String remarks="备注";

}

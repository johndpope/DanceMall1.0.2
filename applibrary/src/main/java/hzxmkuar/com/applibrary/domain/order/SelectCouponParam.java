package hzxmkuar.com.applibrary.domain.order;

import hzxmkuar.com.applibrary.domain.BaseParam;
import lombok.Data;

/**
 * Created by Administrator on 2018/9/29.
 */
@Data
public class SelectCouponParam extends BaseParam {
    private int busoffer_id;
    private int payment_mode;
}

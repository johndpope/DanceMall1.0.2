package hzxmkuar.com.applibrary.domain.integral;

import hzxmkuar.com.applibrary.domain.BaseParam;
import lombok.Data;

/**
 * Created by Administrator on 2018/9/28.
 */
@Data
public class ConfirmIntegralParam extends BaseParam {
    private String consignee;
    private String telephone;
    private String address;
    private int goods_id;
}

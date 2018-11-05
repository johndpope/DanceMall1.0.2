package hzxmkuar.com.applibrary.domain.order;

import hzxmkuar.com.applibrary.domain.BaseParam;
import lombok.Data;

/**
 * Created by Administrator on 2018/10/26.
 */
@Data
public class PutForwardParam extends BaseParam {
    private String cash_money;
    private int cash_type;
    private String real_name;
    private String bank_num="x";
}

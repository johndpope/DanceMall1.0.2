package hzxmkuar.com.applibrary.domain.quote;

import hzxmkuar.com.applibrary.domain.BaseParam;
import lombok.Data;

/**
 * Created by Administrator on 2018/9/25.
 */
@Data
public class QuoteParam extends BaseParam {
    private int id;
    private int is_invoice;
    private String offer_amount;
}

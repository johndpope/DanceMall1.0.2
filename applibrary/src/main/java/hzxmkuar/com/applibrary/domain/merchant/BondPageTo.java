package hzxmkuar.com.applibrary.domain.merchant;

import lombok.Data;

/**
 * Created by Administrator on 2018/10/16.
 */
@Data
public class BondPageTo {

    /**
     * is_deposit : 0
     * need_pay_amount : 5000
     * min_deposit_amount : 100
     * deposit_desc : 保证金说明
     */

    private int is_deposit;
    private String need_pay_amount;
    private String min_deposit_amount;
    private String deposit_desc;

}

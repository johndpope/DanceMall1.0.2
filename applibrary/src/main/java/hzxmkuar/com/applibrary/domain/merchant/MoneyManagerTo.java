package hzxmkuar.com.applibrary.domain.merchant;

import lombok.Data;

/**
 * Created by Administrator on 2018/10/15.
 */
@Data
public class MoneyManagerTo  {


    /**
     * shop_id : 15
     * account_balance : 0
     * deposit : 0
     * is_deposit : 0
     */

    private int shop_id;
    private double account_balance;
    private double deposit;
    private int is_deposit;


}

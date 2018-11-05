package hzxmkuar.com.applibrary.domain.order;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by Administrator on 2018/10/9.
 */
@Data
public class OrderSettleInfoTo implements Serializable{

    /**
     * order_id : 3
     * total_amount : 49.9
     */

    private int order_id;
    private double total_amount;


}

package hzxmkuar.com.applibrary.domain.order;

import hzxmkuar.com.applibrary.domain.BaseParam;
import lombok.Data;

/**
 * Created by Administrator on 2018/10/10.
 */
@Data
public class EvaluateParam extends BaseParam {

    private int order_id;
    private String stars;
    private String content;
    private String pics;
}

package hzxmkuar.com.applibrary.domain.order;

import hzxmkuar.com.applibrary.domain.BaseParam;
import lombok.Data;

/**
 * Created by Administrator on 2018/9/27.
 */
@Data
public class OTypeParam extends BaseParam {
    private int otype;
    private int page=1;
}

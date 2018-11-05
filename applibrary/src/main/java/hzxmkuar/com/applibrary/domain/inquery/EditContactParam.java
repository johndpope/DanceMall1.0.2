package hzxmkuar.com.applibrary.domain.inquery;

import hzxmkuar.com.applibrary.domain.BaseParam;
import lombok.Data;

/**
 * Created by Administrator on 2018/9/12.
 */
@Data
public class EditContactParam extends BaseParam {
    private int id;
    private String consignee;
    private String telephone;

}

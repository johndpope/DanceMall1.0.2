package hzxmkuar.com.applibrary.domain.inquery;

import hzxmkuar.com.applibrary.domain.BaseParam;
import lombok.Data;

/**
 * Created by Administrator on 2018/9/12.
 */
@Data
public class AddContactParam extends BaseParam {

    private String consignee;
    private String telephone;

}

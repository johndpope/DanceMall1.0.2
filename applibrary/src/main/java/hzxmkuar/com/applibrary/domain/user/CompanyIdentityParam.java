package hzxmkuar.com.applibrary.domain.user;

import hzxmkuar.com.applibrary.domain.BaseParam;
import lombok.Data;

/**
 * Created by Administrator on 2018/9/28.
 */
@Data
public class CompanyIdentityParam extends BaseParam{
    private int auth_type;
    private int business_license;
    private String business_name;
}

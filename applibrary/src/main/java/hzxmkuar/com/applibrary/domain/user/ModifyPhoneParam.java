package hzxmkuar.com.applibrary.domain.user;

import hzxmkuar.com.applibrary.domain.BaseParam;
import lombok.Data;

/**
 * Created by Administrator on 2018/9/20.
 */
@Data
public class ModifyPhoneParam extends BaseParam {
    private String mobile;
    private String sms_code;
}

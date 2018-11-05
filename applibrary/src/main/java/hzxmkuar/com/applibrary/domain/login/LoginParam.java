package hzxmkuar.com.applibrary.domain.login;

import hzxmkuar.com.applibrary.domain.BaseParam;
import lombok.Data;

/**
 * Created by Administrator on 2018/9/10.
 */
@Data
public class LoginParam extends BaseParam{
    private String mobile;
    private String sms_code;
    private String jpush_id;
    private String invite_code;
}

package hzxmkuar.com.applibrary.domain.login;

import hzxmkuar.com.applibrary.domain.BaseParam;
import lombok.Data;

/**
 * Created by Administrator on 2018/9/10.
 **/
@Data
public class WechatLoginParam extends BaseParam{
    private String openid;
    private int auth_type=2;
    private String nickname;
    private int sex;
    private String headimgurl;
    private String jpush_id;
}

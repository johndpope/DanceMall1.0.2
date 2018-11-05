package hzxmkuar.com.applibrary.domain.login;

import lombok.Data;

/**
 * Created by Administrator on 2018/9/10.
 */
@Data
public class WeChatTokenTo {

    /**
     * access_token : ACCESS_TOKEN
     * expires_in : 7200
     * refresh_token : REFRESH_TOKEN
     * openid : OPENID
     * scope : SCOPE
     * unionid : o6_bmasdasdsad6_2sgVt7hMZOPfL
     */

    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
    private String unionid;

}

package hzxmkuar.com.applibrary.domain.user;

import lombok.Data;

/**
 * Created by Administrator on 2018/9/28.
 */
@Data
public class IdentityResultTo {


    /**
     * status : 1
     * auth_type : 2
     * auth_info : {"name":"手机4205用户","img1":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-10-30/5623c3761687eecf186a7866131c47bc.jpg","img2":"http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-10-30/855e6ec136c8a51c83b0d08ee9c745a2.jpg"}
     */

    private int status;
    private int auth_type;
    private AuthInfoBean auth_info;

    @Data
    public static class AuthInfoBean {
        /**
         * name : 手机4205用户
         * img1 : http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-10-30/5623c3761687eecf186a7866131c47bc.jpg
         * img2 : http://xmap18070031.php.hzxmnet.com/uploads/picture/2018-10-30/855e6ec136c8a51c83b0d08ee9c745a2.jpg
         */

        private String name;
        private String img1;
        private String img2;


    }
}

package hzxmkuar.com.applibrary.domain.user;

import hzxmkuar.com.applibrary.domain.BaseParam;
import lombok.Data;

/**
 * Created by Administrator on 2018/9/28.
 */
@Data
public class PersonIdentityParam extends BaseParam {
    private int auth_type;
    private int face1;
    private int face2;
    private String realname;
}

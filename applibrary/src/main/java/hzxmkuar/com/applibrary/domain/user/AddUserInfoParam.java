package hzxmkuar.com.applibrary.domain.user;

import hzxmkuar.com.applibrary.domain.BaseParam;
import lombok.Data;

/**
 * Created by Administrator on 2018/9/17.
 */
@Data
public class AddUserInfoParam extends BaseParam{

    private int face;
    private String nickname;

}

package hzxmkuar.com.applibrary.domain.login;

import hzxmkuar.com.applibrary.domain.BaseParam;
import lombok.Data;

/**
 * Created by Administrator on 2018/9/11.
 **/
@Data
public class UploadImageParam extends BaseParam{
    private int uid;
    private String hashid;
    private String fileName;
    private String tags;
}

package hzxmkuar.com.applibrary.domain;

import lombok.Data;

/**
 * Created by Administrator on 2018/9/1.
 */
@Data
public class VerificationParam extends BaseParam{
    private String mobile;
    private int type;
}

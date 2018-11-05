package hzxmkuar.com.applibrary.domain.user;

import hzxmkuar.com.applibrary.domain.BaseParam;
import lombok.Data;

/**
 * Created by Administrator on 2018/10/20.
 */
@Data
public class ReportParam extends BaseParam {

    private int shop_id;
    private String content;
}

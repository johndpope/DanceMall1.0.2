package hzxmkuar.com.applibrary.domain.merchant;

import hzxmkuar.com.applibrary.domain.BaseParam;
import lombok.Data;

/**
 * Created by Administrator on 2018/10/16.
 */
@Data
public class IncomeRecordParam extends BaseParam {
    private String search = "{}";
    private int page;
}

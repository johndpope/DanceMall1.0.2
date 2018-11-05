package hzxmkuar.com.applibrary.main;

import java.util.List;

import lombok.Data;

/**
 * Created by Administrator on 2018/8/28.
 */
@Data
public class SelectDemandTo {
    private String typeName;
    private List<SelectDemandChildTo>demandChildList;
}

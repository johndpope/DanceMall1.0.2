package hzxmkuar.com.applibrary.main;

import lombok.Data;

/**
 * Created by Administrator on 2018/8/29.
 */
@Data
public class DemandSearchChildTo {
    private String typeName;

    public DemandSearchChildTo(String typeName) {
        this.typeName = typeName;
    }
}

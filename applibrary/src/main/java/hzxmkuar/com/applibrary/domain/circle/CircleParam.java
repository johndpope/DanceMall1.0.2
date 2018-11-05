package hzxmkuar.com.applibrary.domain.circle;

import hzxmkuar.com.applibrary.domain.BaseParam;
import lombok.Data;

/**
 * Created by Administrator on 2018/9/19.
 */
@Data
public class CircleParam extends BaseParam {

    private int page;
    private int topic_id;
    private int sort_by;
    private int is_local;
    private int pos_city;
}

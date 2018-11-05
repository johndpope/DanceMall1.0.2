package hzxmkuar.com.applibrary.domain.circle;

import hzxmkuar.com.applibrary.domain.BaseParam;
import lombok.Data;

/**
 * Created by Administrator on 2018/9/18.
 */
@Data
public class AddPostParam extends BaseParam{

    private String content;
    private String pics;
    private String position;
    private int topic_id;
    private int is_top;
    private int days;
    private int pos_city;
    private int order_id;

}

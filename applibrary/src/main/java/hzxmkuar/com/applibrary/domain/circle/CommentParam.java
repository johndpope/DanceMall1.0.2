package hzxmkuar.com.applibrary.domain.circle;

import hzxmkuar.com.applibrary.domain.BaseParam;
import lombok.Data;

/**
 * Created by Administrator on 2018/9/20.
 */
@Data
public class CommentParam extends BaseParam {
    private int id;
    private String content;
    private int comment_id;

}

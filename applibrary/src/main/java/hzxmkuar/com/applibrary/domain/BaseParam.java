package hzxmkuar.com.applibrary.domain;

import lombok.Data;

/**
 * Created by Administrator on 2018/9/1.
 */
@Data
public class BaseParam {
    private long time=System.nanoTime();
    private String hash;
    private String apiId="7c13634bef78989a88dc90233f9d40f4";
    private String terminal="3";
    private int uid;
    private String hashid;





}

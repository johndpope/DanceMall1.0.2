package hzxmkuar.com.applibrary.domain.user;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by Administrator on 2018/10/11.
 */
@Data
public class VipTo implements Serializable{

    /**
     * uid : 89
     * status : 0
     * valid_time :
     * kf_tel : 0571-66555555
     */

    private int uid;
    private int status;
    private String valid_time;
    private String kf_tel;


}

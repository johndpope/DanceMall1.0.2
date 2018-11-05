package hzxmkuar.com.applibrary.domain.login;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * Created by Administrator on 2018/9/25.
 */
@Data
public class CityTo implements Serializable{


    /**
     * id : 1
     * pid : 0
     * area : 北京
     * level : 1
     * status : 1
     * pinyin : beijing
     * first_code : B
     * shortname : 北京
     * merger_name : 中国,北京
     * code :
     * zip_code :
     * lng : 116.405285
     * lat : 39.904989
     */

    private int id;
    private int pid;
    private String area;
    private int level;
    private int status;
    private String pinyin;
    private String first_code;
    private String shortname;
    private String merger_name;
    private String code;
    private String zip_code;
    private String lng;
    private String lat;
    private List<CityTo>sub;

}

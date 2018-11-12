package hzxmkuar.com.applibrary.domain.inquery;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import hzxmkuar.com.applibrary.domain.BaseParam;
import lombok.Data;

/**
 * Created by Administrator on 2018/9/13.
 */
@Data
public class AddInquiryParam extends BaseParam {


    /**
     * time : 1536316421
     * uid : 88
     * hashid : d0a24d183b1ee6d6b5279ebc548bd3b0
     * contact_id : 3
     * service_address : 浙江杭州下沙金沙湖
     * use_time : {"start_time":"6月12日12:00","end_time":"6月13日12:00"}
     */



    private int contact_id;
    private String service_address;
    private String use_time;
    private int stype;
    private String service_list;
    private String inquiry_bus = "[]";
    private int pos_city;

    @Data
    public static class UseTimeTo {
        private String start_time;
        private String end_time;
    }




}

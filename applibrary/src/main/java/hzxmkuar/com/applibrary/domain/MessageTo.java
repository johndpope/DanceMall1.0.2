package hzxmkuar.com.applibrary.domain;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import hzxmkuar.com.applibrary.domain.main.MainMerchantTo;
import lombok.Data;

/**
 * Created by Administrator on 2018/9/1.
 */
@Data
public class MessageTo<T> {

    /**
     * Code : 000000
     * Msg : 返回成功
     * Time : 2018-08-30 16:08:20
     * ApiUrl : http://xmap18070031.php.hzxmnet.com/api/Sms/sendcode
     * Data : {"mobile":"13738084633","sms_code":"637442"}
     */

    private int Code;
    private String Msg;
    private String Time;
    private String ApiUrl;
    private T Data ;

}

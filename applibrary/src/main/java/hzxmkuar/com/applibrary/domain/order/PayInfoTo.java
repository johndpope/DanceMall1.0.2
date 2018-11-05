package hzxmkuar.com.applibrary.domain.order;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by Administrator on 2018/9/29.
 */
@Data
public class PayInfoTo implements Serializable{

    /**
     * alipay :
     * wxpay : {"appid":"wx09143ad01dabb4c9","partnerid":"1513319621","prepayid":"wx29163908992661c6a30794493163363609","package":"Sign=WXPay","noncestr":"8c8hhjq9uzdmjlzj0vdx5545bo29z96x","timestamp":1538210349,"sign":"A751E80001F237F6BC96D122BB970A27"}
     */



    private String alipay;
    private Object lianlianpay;




}

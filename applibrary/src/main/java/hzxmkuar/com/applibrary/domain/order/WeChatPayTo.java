package hzxmkuar.com.applibrary.domain.order;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by Administrator on 2018/10/13.
 */
@Data
public class WeChatPayTo implements Serializable {

    private WxpayBean wxpay;



    @Data
    public static class WxpayBean implements Serializable {
        /**
         * appid : wx09143ad01dabb4c9
         * partnerid : 1513319621
         * prepayid : wx132358468444559874f7e27a1512067860
         * package : Sign=WXPay
         * noncestr : snuu9nx1lfpmwxhaa5zt95vswy8e9bjy
         * timestamp : 1539446326
         * sign : A2393AC930B71096827AB9482E9776CC
         */

        private String appid;
        private String partnerid;
        private String prepayid;
        @SerializedName("package")
        private String packageX;
        private String noncestr;
        private String timestamp;
        private String sign;


    }
}

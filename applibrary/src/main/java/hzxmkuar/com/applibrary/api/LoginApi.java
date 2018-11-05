package hzxmkuar.com.applibrary.api;

import hzxmkuar.com.applibrary.domain.BaseParam;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.VerificationParam;
import hzxmkuar.com.applibrary.domain.login.BindPhoneParam;
import hzxmkuar.com.applibrary.domain.login.LoginParam;
import hzxmkuar.com.applibrary.domain.login.UploadImageParam;
import hzxmkuar.com.applibrary.domain.login.WechatLoginParam;
import hzxmkuar.com.applibrary.domain.login.WechatLoginTo;
import hzxmkuar.com.applibrary.domain.main.UploadImageTo;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by Administrator on 2018/9/1.
 **/

public interface LoginApi {
    /**
     * 发送验证码
     */
    @POST("Api/Sms/sendCode")
    Observable<MessageTo>getVerificationCode(@Body VerificationParam param);

    /**
     * 微信登录
     */
    @POST("Api/User/oauthLogin")
    Observable<MessageTo<WechatLoginTo>>wechatLogin(@Body WechatLoginParam param);
    /**
     * 手机号绑定
     */
    @POST("Api/User/mobileSmsBind")
    Observable<MessageTo<WechatLoginTo>>bindPhone(@Body BindPhoneParam param);
    /**
     * 登录
     */
    @POST("Api/User/mobileSmsLogin")
    Observable<MessageTo>login(@Body LoginParam param);


    /**
     * 登录
     */
    @Multipart
    @POST("api/upload/uploadImg")
    Observable<MessageTo<UploadImageTo>>uploadImage(
            @Part("time") RequestBody timeBody,
            @Part("hash") RequestBody hash,
            @Part("apiId") RequestBody apiId,
            @Part("terminal") RequestBody terminal,
            @Part("uid") RequestBody uid,
            @Part("hashid") RequestBody hashid,
            @Part("fileName") RequestBody fileName,
            @Part("tags") RequestBody tags,
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file);
}

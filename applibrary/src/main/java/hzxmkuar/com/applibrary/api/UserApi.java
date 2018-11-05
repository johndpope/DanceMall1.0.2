package hzxmkuar.com.applibrary.api;

import hzxmkuar.com.applibrary.domain.BaseParam;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.main.UploadImageTo;
import hzxmkuar.com.applibrary.domain.merchant.MerchantMySelfTo;
import hzxmkuar.com.applibrary.domain.user.AddUserInfoParam;
import hzxmkuar.com.applibrary.domain.user.CompanyIdentityParam;
import hzxmkuar.com.applibrary.domain.user.ContentParam;
import hzxmkuar.com.applibrary.domain.user.IdentityResultTo;
import hzxmkuar.com.applibrary.domain.user.ModifyImageParam;
import hzxmkuar.com.applibrary.domain.user.ModifyNickParam;
import hzxmkuar.com.applibrary.domain.user.ModifyPhoneParam;
import hzxmkuar.com.applibrary.domain.user.MyselfUserTo;
import hzxmkuar.com.applibrary.domain.user.NickNameTo;
import hzxmkuar.com.applibrary.domain.user.PersonIdentityParam;
import hzxmkuar.com.applibrary.domain.user.ReportParam;
import hzxmkuar.com.applibrary.domain.user.VipTo;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by Administrator on 2018/9/17.
 */

public interface UserApi {

    /**
     * 修改用户头像
     */
    @Multipart
    @POST("api/upload/uploadImg")
    Observable<MessageTo<UploadImageTo>> modifyUser(
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

    /**
     * 设置用户数据
     */
    @POST("Api/User/setUserInfo")
    Observable<MessageTo>setUserInfo(@Body AddUserInfoParam param);

    /**
     * 修改用户头像
     */
    @POST("Api/User/updateUserFace")
    Observable<MessageTo>modifyHead(@Body ModifyImageParam param);

    /**
     * 修改手机号
     */
    @POST("Api/User/updateUserMobile")
    Observable<MessageTo>modifyPhone(@Body ModifyPhoneParam param);

    /**
     * 修改昵称
     */
    @POST("Api/User/updateUsername")
    Observable<MessageTo<NickNameTo>>modifynick(@Body ModifyNickParam param);

    /**
     * 个人身份认证
     */
    @POST("Api/User/authUserByPerson")
    Observable<MessageTo>personIdentity(@Body PersonIdentityParam param);

    /**
     * 企业身份验证
     */
    @POST("Api/User/authUserByEnterprise")
    Observable<MessageTo>companyIdentity(@Body CompanyIdentityParam param);

    /**
     * 企业身份验证
     */
    @POST("Api/User/get_authUserStatus")
    Observable<MessageTo<IdentityResultTo>>getIdentityResult(@Body BaseParam param);


    /**
     * 商家入驻状态
     */
    @POST("Api/User/getMerchantStatus")
    Observable<MessageTo<IdentityResultTo>>getMerchantStatue(@Body BaseParam param);

    /**
     * 意见建议
     */
    @POST("Api/Feedback/writeDo")
    Observable<MessageTo>suggest(@Body ContentParam param);

    /**
     * Vip状态
     */
    @POST("Api/User/get_vip_status")
    Observable<MessageTo<VipTo>>vipStatue(@Body BaseParam param);

    /**
     * 我的用户信息
     */
    @POST("Api/User/memberCenter")
    Observable<MessageTo<MyselfUserTo>>myselfInfo(@Body BaseParam param);

    /**
     * 商家用户用户信息
     */
    @POST("Api/User/merchantCenter")
    Observable<MessageTo<MerchantMySelfTo>>merchantMyselfInfo(@Body BaseParam param);
    /**
     * 举报
     */
    @POST("Api/Feedback/complaintDo")
    Observable<MessageTo>report(@Body ReportParam param);
}

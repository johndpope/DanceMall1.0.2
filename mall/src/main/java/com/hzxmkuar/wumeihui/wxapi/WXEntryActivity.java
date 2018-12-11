package com.hzxmkuar.wumeihui.wxapi;

import com.google.gson.Gson;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Event;
import com.hzxmkuar.wumeihui.login.BindPhoneActivity;
import com.hzxmkuar.wumeihui.login.presenter.LoginPresenter;
import com.hzxmkuar.wumeihui.personal.MainActivity;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.opensdk.modelmsg.WXAppExtendObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import cn.sharesdk.framework.utils.e;
import cn.sharesdk.wechat.utils.WechatHandlerActivity;
import cn.sharesdk.wechat.utils.k;
import hzxmkuar.com.applibrary.domain.login.UserInfoTo;
import hzxmkuar.com.applibrary.domain.login.WeChatTokenTo;
import hzxmkuar.com.applibrary.domain.login.WechatLoginTo;
import hzxmkuar.com.applibrary.domain.login.WechatUserInfoTo;


public class WXEntryActivity extends WechatHandlerActivity implements IWXAPIEventHandler {

    public static final String APP_ID = "wx09143ad01dabb4c9";
    public static final String APP_SECRET = "bfd7d21aa614d01259c1ca2d34ff0bc3";
    private IWXAPI mApi;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mApi = WXAPIFactory.createWXAPI(this, APP_ID, true);
        mApi.handleIntent(this.getIntent(), this);

    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {

        String code = ((SendAuth.Resp) baseResp).code;

        getToken(code);

    }


    private void getToken(String code) {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx09143ad01dabb4c9&secret=bfd7d21aa614d01259c1ca2d34ff0bc3&code=" + code + "&grant_type=authorization_code",
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        WeChatTokenTo tokenTo = new Gson().fromJson(responseInfo.result, WeChatTokenTo.class);
                        System.out.println(responseInfo.result+"tokenTo==");
                        getUSerInfo(tokenTo.getAccess_token(), tokenTo.getOpenid());
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {

                    }
                });
    }

    private void getUSerInfo(String access, String openId) {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, "https://api.weixin.qq.com/sns/userinfo?access_token=" + access + "&openid=" + openId,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        WechatUserInfoTo wechatUserInfoTo = new Gson().fromJson(responseInfo.result, WechatUserInfoTo.class);
                        EventBus.getDefault().post(new Event<>("WechatLoginSuccess",wechatUserInfoTo));
                        finish();

                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {

                    }
                });
    }



    @Override
    public void finish() {
        super.finish();
        //注释掉activity本身的过渡动画
        overridePendingTransition(0, 0);

    }
//    {"auth_type":2,"headimgurl":"headImage","jpush_id":"Jpush","nickname":"光辉","openid":"o8Qpt1UrWRb5Ay8wtNeDUjPeHcjk","sex":1,"apiId":"7c13634bef78989a88dc90233f9d40f4","hash":"9ce873d2b58839b377ed0da230091089","terminal":"3","time":518330089949875}

    public void onGetMessageFromWXReq(cn.sharesdk.wechat.utils.WXMediaMessage msg) {
        Intent iLaunchMyself = getPackageManager().getLaunchIntentForPackage(getPackageName());
        startActivity(iLaunchMyself);
    }

    /**
     * 处理微信向第三方应用发起的消息
     * <p>
     * 此处用来接收从微信发送过来的消息，比方说本demo在wechatpage里面分享
     * 应用时可以不分享应用文件，而分享一段应用的自定义信息。接受方的微信
     * 客户端会通过这个方法，将这个信息发送回接收方手机上的本demo中，当作
     * 回调。
     * <p>
     * 本Demo只是将信息展示出来，但你可做点其他的事情，而不仅仅只是Toast
     */
    public void onShowMessageFromWXReq(cn.sharesdk.wechat.utils.WXMediaMessage msg) {
        if (msg != null && msg.mediaObject != null
                && (msg.mediaObject instanceof cn.sharesdk.wechat.utils.WXAppExtendObject)) {
            cn.sharesdk.wechat.utils.WXAppExtendObject obj = (cn.sharesdk.wechat.utils.WXAppExtendObject) msg.mediaObject;
            Toast.makeText(this, obj.extInfo, Toast.LENGTH_SHORT).show();
        }
    }

}

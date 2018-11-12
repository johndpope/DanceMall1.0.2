package com.hzxmkuar.wumeihui.personal.order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.ActivityManager;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.base.Event;
import com.hzxmkuar.wumeihui.base.utils.BaseHelper;
import com.hzxmkuar.wumeihui.base.utils.Constants;
import com.hzxmkuar.wumeihui.base.utils.MobileSecurePayer;
import com.hzxmkuar.wumeihui.business.main.MainMerchantActivity;
import com.hzxmkuar.wumeihui.personal.order.presenter.PayPresenter;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.autolayout.AutoRelativeLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzxmkuar.com.applibrary.domain.order.BankPayTo;
import hzxmkuar.com.applibrary.domain.order.PayInfoTo;
import hzxmkuar.com.applibrary.domain.order.PayResult;
import hzxmkuar.com.applibrary.domain.order.WeChatPayTo;
import rx.Observable;

/**
 * Created by Administrator on 2018/9/1.
 */

public class SelectPayActivity extends BaseActivity {
    @BindView(R.id.pay_money)
    TextView payMoney;
    @BindView(R.id.ali_view)
    View aliView;
    @BindView(R.id.wechat_view)
    View wechatView;
    @BindView(R.id.bank_view)
    View bankView;
    @BindView(R.id.other_view)
    View otherView;
    @BindView(R.id.other_layout)
    AutoRelativeLayout otherLayout;
    private PayPresenter presenter;
    private int payType = 1;
    private int type;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {
                    if (payType == 1) {
                        Bundle bundle = msg.getData();

                        PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                        // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                        String resultStatus = payResult.getResultStatus();

                        // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                        if (TextUtils.equals(resultStatus, "9000")) {
//                        String payNo = resultInfo.substring(resultInfo.indexOf("out_trade_no=") + "out_trade_no=".length(),resultInfo.indexOf("&subject"));
                            Toast.makeText(appContext, "支付成功", Toast.LENGTH_SHORT).show();
                            //  submittedSuccessfullyDialog(bundle.getString("OlderSid"),payNo);
                            EventBus.getDefault().post(new Event<>("PayResultData", 10));
                            if (type == 10) {
                                Intent intent = new Intent(appContext, PayFinishActivity.class);
                                intent.putExtra("OrderId", getIntent().getIntExtra("OrderId", 0));
                                intent.putExtra("PayTypeModel",getIntent().getBooleanExtra("PayTypeModel",true));
                                startActivity(intent);
                                goToAnimation(1);

                            } else if (type == 2) {
                                Intent intent = new Intent(appContext, MainMerchantActivity.class);
                                startActivity(intent);
                                Observable.from(ActivityManager.activityList).subscribe(Activity::finish);
                                goToAnimation(2);

                            } else
                                finish();
                        } else {
                            // 判断resultStatus 为非“9000”则代表可能支付失败
                            // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                            if (TextUtils.equals(resultStatus, "8000")) {
                                Toast.makeText(appContext, "支付结果确认中", Toast.LENGTH_SHORT).show();
                            } else {
                                // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                                Toast.makeText(appContext, "支付失败", Toast.LENGTH_SHORT).show();

                            }

                        }

                        break;
                    }

                    if (payType==3){
                        String strRet = (String) msg.obj;
                        switch (msg.what) {
                            case Constants.RQF_PAY: {
                                JSONObject objContent = BaseHelper.string2JSON(strRet);
                                String retCode = objContent.optString("ret_code");
                                String retMsg = objContent.optString("ret_msg");

                                // 成功
                                if (Constants.RET_CODE_SUCCESS.equals(retCode)) {
                                    Intent intent = new Intent(appContext, PayFinishActivity.class);
                                    intent.putExtra("OrderId", getIntent().getIntExtra("OrderId", 0));
                                    startActivity(intent);
                                    goToAnimation(1);
                                } else if (Constants.RET_CODE_PROCESS.equals(retCode)) {
                                    // TODO 处理中，掉单的情形
                                    String resulPay = objContent.optString("result_pay");
                                    if (Constants.RESULT_PAY_PROCESSING
                                            .equalsIgnoreCase(resulPay)) {
                                        BaseHelper.showDialog(SelectPayActivity.this, "提示",
                                                objContent.optString("ret_msg") + "交易状态码："
                                                        + retCode +" 返回报文:"+strRet,
                                                android.R.drawable.ic_dialog_alert);
                                    }

                                } else {
                                    // TODO 失败
                                    BaseHelper.showDialog(SelectPayActivity.this, "提示", retMsg
                                                    + "，交易状态码:" + retCode +" 返回报文:"+strRet,
                                            android.R.drawable.ic_dialog_alert);
                                }
                            }
                            break;
                        }
                    }
                }
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pay);
        ButterKnife.bind(this);
        setTitleName(Constant.PAY);
        payMoney.setText(getIntent().getStringExtra("Money"));
        presenter = new PayPresenter(this);
        EventBus.getDefault().register(this);
        type = getIntent().getIntExtra("Type", 0);
        setView();
    }

    private void setView() {
        if (type == 5||type == 3||type == 4)
            otherLayout.setVisibility(View.GONE);

    }

    @OnClick({R.id.ali_layout, R.id.wechat_layouyt, R.id.bank_layout, R.id.other_layout, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ali_layout:
                payType = 1;
                aliView.setBackgroundResource(R.drawable.service_select);
                wechatView.setBackgroundResource(R.drawable.service_un_select);
                bankView.setBackgroundResource(R.drawable.service_un_select);
                otherView.setBackgroundResource(R.drawable.service_un_select);
                break;
            case R.id.wechat_layouyt:
                payType = 2;
                aliView.setBackgroundResource(R.drawable.service_un_select);
                wechatView.setBackgroundResource(R.drawable.service_select);
                bankView.setBackgroundResource(R.drawable.service_un_select);
                otherView.setBackgroundResource(R.drawable.service_un_select);

                break;
            case R.id.bank_layout:
                payType = 3;
                aliView.setBackgroundResource(R.drawable.service_un_select);
                wechatView.setBackgroundResource(R.drawable.service_un_select);
                bankView.setBackgroundResource(R.drawable.service_select);
                otherView.setBackgroundResource(R.drawable.service_un_select);
                break;
            case R.id.other_layout:
                if (userInfoTo.getIs_member()!=1){
                    showMessage("挂账只支持Vip会员");
                    return;
                }
                payType = 4;
                aliView.setBackgroundResource(R.drawable.service_un_select);
                wechatView.setBackgroundResource(R.drawable.service_un_select);
                bankView.setBackgroundResource(R.drawable.service_un_select);
                otherView.setBackgroundResource(R.drawable.service_select);
                break;
            case R.id.submit:
                if (type == 2)
                    presenter.payBond(payType);
                else if (type == 3)
                    presenter.payMerchantTop(payType);
                else if (type == 4)
                    presenter.payInquiryTop(payType);
                else if (type == 5)
                    presenter.payPostTop(payType);
                else if (type == 10)
                    presenter.getPayInfo(payType);
                break;
        }
    }

    @Override
    public void loadDataSuccess(Object data) {

        if (payType == 2) {
            WeChatPayTo payToInfo = (WeChatPayTo) data;
            WeChatPayTo.WxpayBean payTo = payToInfo.getWxpay();
            IWXAPI api = WXAPIFactory.createWXAPI(this, "wx09143ad01dabb4c9");
            PayReq request = new PayReq();
            request.appId = payTo.getAppid();
            request.partnerId = payTo.getPartnerid();
            request.prepayId = payTo.getPrepayid();
            request.packageValue = payTo.getPackageX();
            request.nonceStr = payTo.getNoncestr();
            request.timeStamp = payTo.getTimestamp();
            request.sign = payTo.getSign();
            api.sendReq(request);
        } else if (payType == 1) {
            PayInfoTo payInfoTo = (PayInfoTo) data;
            payMoney(payInfoTo.getAlipay());
        } else if (payType == 3) {

            PayInfoTo payInfoTo = (PayInfoTo) data;
            MobileSecurePayer msp = new MobileSecurePayer();
            boolean bRet = msp.pay(JSON.toJSONString(payInfoTo.getLianlianpay()), mHandler, Constants.RQF_PAY, SelectPayActivity.this, false);
        }else if (payType==4){
            Intent intent = new Intent(appContext, PayFinishActivity.class);
            intent.putExtra("OrderId", getIntent().getIntExtra("OrderId", 0));
            startActivity(intent);
            goToAnimation(1);
        }


    }


    protected void payMoney(String url) {


//
        // 客户端支付
        final String payPartner = url;
        Runnable payRunnable = () -> {
            // 构造PayTask 对象
            PayTask aliPay = new PayTask(this);
            // 调用支付接口，获取支付结果
            Map<String, String> result = aliPay.payV2(payPartner, true);
            Message msg1 = new Message();
            msg1.what = 1;
            msg1.obj = result;
            Bundle bundle = new Bundle();
            bundle.putString("OlderSid", url);
            //  bundle.putString("OlderNumber",olderNumber);
            System.out.println(result + "result");
            msg1.setData(bundle);
            System.out.println(result + "result");
            mHandler.sendMessage(msg1);
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void getPayResult(Event<Integer> event) {
        if ("PayResultDataWX".equals(event.getType())) {
            if (event.getData() == 10) {
                if (type == 10) {
                    Intent intent = new Intent(appContext, PayFinishActivity.class);
                    intent.putExtra("OrderId", getIntent().getIntExtra("OrderId", 0));
                    startActivity(intent);
                    goToAnimation(1);

                } else if (type == 2) {
                    Intent intent = new Intent(appContext, MainMerchantActivity.class);
                    startActivity(intent);
                    Observable.from(ActivityManager.activityList).subscribe(Activity::finish);
                    goToAnimation(2);

                } else
                    finish();
            }
        }
    }
    public static String toJSONString(Object obj)
    {
        JSONObject json = new JSONObject();
        try
        {
            Map<String, String> map = bean2Parameters(obj);
            for (Map.Entry<String, String> entry : map.entrySet())
            {
                json.put(entry.getKey(), entry.getValue());
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return json.toString();
    }

    public static Map<String, String> bean2Parameters(Object bean)
    {
        if (bean == null)
        {
            return null;
        }

        Map<String, String> parameters = new HashMap<String, String>();

        if(null != parameters) {
            // 取得bean所有public 方法
            Method[] Methods = bean.getClass().getMethods();
            for (Method method : Methods)
            {
                if (method != null && method.getName().startsWith("get")
                        && !method.getName().startsWith("getClass"))
                {
                    // 得到属性的类名
                    String value = "";
                    // 得到属性值
                    try
                    {
                        String className = method.getReturnType().getSimpleName();
                        if (className.equalsIgnoreCase("int"))
                        {
                            int val = 0;
                            try
                            {
                                val = (Integer) method.invoke(bean);
                            } catch (InvocationTargetException e)
                            {
                            }
                            value = String.valueOf(val);
                        } else if (className.equalsIgnoreCase("String"))
                        {
                            try
                            {
                                value = (String) method.invoke(bean);
                            } catch (InvocationTargetException e)
                            {
                            }
                        }
                        if (value != null && value != "")
                        {
                            // 添加参数
                            // 将方法名称转化为id，去除get，将方法首字母改为小写
                            String param = method.getName().replaceFirst("get", "");
                            if (param.length() > 0)
                            {
                                String first = String.valueOf(param.charAt(0)).toLowerCase();
                                param = first + param.substring(1);
                            }
                            parameters.put(param, value);
                        }
                    } catch (IllegalArgumentException e)
                    {
                    } catch (IllegalAccessException e)
                    {
                    }
                }
            }
        }

        return parameters;
    }
}

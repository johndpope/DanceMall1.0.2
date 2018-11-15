package com.hzxmkuar.wumeihui.personal.order;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.ActivityManager;
import com.hzxmkuar.wumeihui.base.AlertDialog;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.base.WebActivity;
import com.hzxmkuar.wumeihui.base.util.AppUtil;
import com.hzxmkuar.wumeihui.business.merchant.EvaluateActivity;
import com.hzxmkuar.wumeihui.message.ChatActivity;
import com.hzxmkuar.wumeihui.personal.MainActivity;
import com.hzxmkuar.wumeihui.personal.inquiry.ServiceDetailActivity;
import com.hzxmkuar.wumeihui.personal.order.presenter.PersonOrderPresenter;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;
import hzxmkuar.com.applibrary.domain.order.MerchantOrderDetailTo;
import hzxmkuar.com.applibrary.domain.order.RemarkTo;
import hzxmkuar.com.applibrary.impl.PermissionListener;
import rx.Observable;

/**
 * Created by Administrator on 2018/9/4.
 */

public class PersonOrderActivity extends BaseActivity {
    @BindView(R.id.order_sn)
    TextView orderSn;
    @BindView(R.id.statue)
    TextView statue;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.use_time)
    TextView useTime;
    @BindView(R.id.contact)
    TextView contact;
    @BindView(R.id.image_layout)
    GridLayout imageLayout;
    @BindView(R.id.phone)
    View phone;
    @BindView(R.id.chat)
    View chat;
    @BindView(R.id.service_num)
    TextView serviceNum;

    @BindView(R.id.insurance)
    TextView insurance;
    @BindView(R.id.insurance_type)
    TextView insuranceType;
    @BindView(R.id.service_fee)
    TextView serviceFee;
    @BindView(R.id.coupon_fee)
    TextView couponFee;
    @BindView(R.id.earnest)
    TextView earnest;
    @BindView(R.id.tail_money)
    TextView tailMoney;
    @BindView(R.id.order_tip)
    TextView orderTip;
    @BindView(R.id.pay_money)
    TextView payMoney;
    @BindView(R.id.pay_layout)
    AutoLinearLayout payLayout;
    @BindView(R.id.evaluate)
    TextView evaluate;
    @BindView(R.id.pay_text)
    TextView payText;
    @BindView(R.id.bond_pay_layout)
    AutoRelativeLayout bondPayLayout;
    @BindView(R.id.count_time)
    CountdownView countTime;
    @BindView(R.id.time_layout)
    AutoLinearLayout timeLayout;
    @BindView(R.id.select_view)
    View selectView;
    @BindView(R.id.pay_type)
    TextView payType;
    @BindView(R.id.shop_name)
    TextView shopName;
    @BindView(R.id.earnest_text)
    TextView earnestText;
    @BindView(R.id.breach_text)
    TextView breachText;
    @BindView(R.id.breach_money)
    TextView breachMoney;
    @BindView(R.id.breach_layout)
    AutoRelativeLayout breachLayout;
    @BindView(R.id.refund_text)
    TextView refundText;
    @BindView(R.id.refund_money)
    TextView refundMoney;
    @BindView(R.id.refund_layout)
    AutoRelativeLayout refundLayout;
    @BindView(R.id.last_pay_time)
    TextView lastPayTime;
    private MerchantOrderDetailTo mode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_merchant);
        ButterKnife.bind(this);
        setTitleName(Constant.ORDER_DETAIL);
        PersonOrderPresenter presenter = new PersonOrderPresenter(this);
        ActivityManager.personOrderActivity = this;
        setBack();

    }

    private void setBack() {
        findViewById(R.id.back).setOnClickListener(view -> {
            if (getIntent().getBooleanExtra("IsFinishPay", false)) {
                Intent intent = new Intent(appContext, MainActivity.class);
                startActivity(intent);
                finish();
                goToAnimation(1);
            } else {
                finish();
                goToAnimation(2);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void loadDataSuccess(Object data) {
        mode = (MerchantOrderDetailTo) data;
        orderSn.setText(mode.getOrder_sn());
        statue.setText(mode.getStatus_txt());
        address.setText(mode.getService_address());
        useTime.setText(mode.getUse_time());
        phone.setVisibility(TextUtils.isEmpty(mode.getContact_telphone())?View.GONE:View.VISIBLE);
        chat.setVisibility(TextUtils.isEmpty(mode.getContact_telphone())?View.GONE:View.VISIBLE);
        contact.setText(mode.getContact_name() + "  " + mode.getContact_telphone());
        if (mode.getService_list() != null) {
            List<String> imageList = new ArrayList<>();
            Observable.from(mode.getService_list()).subscribe(serviceListBean -> imageList.add(serviceListBean.getService_img()));
            setImageLayout(imageLayout, imageList, 120);
        }
        serviceNum.setText("共" + mode.getService_num() + "项服务");
        serviceFee.setText(mode.getPrice_detail().getService_fee());

        if (mode.getCoupon_info() != null) {
            couponFee.setText(mode.getCoupon_info().getCoupon_amount());
        }

        if ("确认完成".equals(mode.getStatus_txt()))
            evaluate.setVisibility(View.VISIBLE);
        if ("等待付款".equals(mode.getStatus_txt()) || "待付尾款".equals(mode.getStatus_txt())) {
            payLayout.setVisibility(View.VISIBLE);
            timeLayout.setVisibility(View.VISIBLE);
            countTime.start(mode.getRemaining_pay_valid_time() * 1000);
        }
        bondPayLayout.setVisibility(mode.getPayment_mode() == 2 ? View.VISIBLE : View.GONE);
        earnest.setText( mode.getPrice_detail().getDeposit_payment().getPay_amount());

        payType.setText(getIntent().getIntExtra("PayType", 0) == 1 ? "先定金后尾款" : "全款支付");
        tailMoney.setText("￥" + (getIntent().getIntExtra("PayType", 0) == 1 ? mode.getPrice_detail().getDeposit_payment().getLast_amount() : mode.getPrice_detail().getFull_payment().getPay_amount()));
        payMoney.setText("￥" + (mode.getNew_status() == 3 ? mode.getPrice_detail().getDeposit_payment().getLast_amount() : mode.getPrice_detail().getFull_payment().getPay_amount()));
        shopName.setText(mode.getBusiness_info().getShop_name());
        serviceNum.setOnClickListener(view -> {

            Intent intent = new Intent(appContext, ServiceDetailActivity.class);
            intent.putExtra("Id", mode.getOrder_id());
            startActivity(intent);
        });

        earnestText.setText("定金-" + mode.getPrice_detail().getDeposit_payment().getPay_amount_type());
        lastPayTime.setVisibility(mode.getPayment_mode() == 2 ? View.VISIBLE : View.GONE);
        if (mode.getPrice_detail().getDeposit_payment() != null)
            lastPayTime.setText("最晚支付时间" + mode.getPrice_detail().getDeposit_payment().getLast_amount_paytime());
        breachLayout.setVisibility(mode.getPrice_detail().getRefund_data().getIs_refund() == 0 ? View.GONE : View.VISIBLE);
        breachText.setText("已退款-" + mode.getPrice_detail().getRefund_data().getRefund_type());
        breachMoney.setText("￥" + mode.getPrice_detail().getRefund_data().getRefund_amount());
        refundLayout.setVisibility(mode.getPrice_detail().getRefund_data().getIs_liquidated_damages() == 0 ? View.GONE : View.VISIBLE);
        refundText.setText("违约金-" + mode.getPrice_detail().getRefund_data().getLiquidated_damages_type());
        refundMoney.setText("￥" + mode.getPrice_detail().getRefund_data().getLiquidated_damages());
        if (mode.getPayment_mode() == 1) {
            payText.setText(mode.getPrice_detail().getFull_payment().getPay_amount_status() == 0 ? "需支付" : "已支付");

        } else {
            payText.setText(mode.getPrice_detail().getDeposit_payment().getLast_amount_status() == 0 ? "尾款" : "尾款-" + mode.getPrice_detail().getDeposit_payment().getLast_amount_type());
            lastPayTime.setVisibility(mode.getPrice_detail().getDeposit_payment().getLast_amount_status() == 1 ? View.GONE : View.VISIBLE);
            tailMoney.setText("￥" + mode.getPrice_detail().getDeposit_payment().getLast_amount());
        }
    }


    @OnClick({R.id.submit, R.id.evaluate, R.id.chat, R.id.phone, R.id.service_agreement, R.id.select_view, R.id.remark_layout, R.id.insurance_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit:
                Intent intent = new Intent(appContext, SelectPayActivity.class);
                intent.putExtra("OrderId", getIntent().getIntExtra("OrderId", 0));
                intent.putExtra("Money", payMoney.getText().toString());
                intent.putExtra("Type", 10);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.evaluate:
                intent = new Intent(appContext, EvaluateActivity.class);

                intent.putExtra("OrderId", mode.getOrder_id());
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.phone:
                AlertDialog.show(this, "确认拨打电话").setOnClickListener(view1 -> {
                    AlertDialog.dismiss();
                    if (!AppUtil.readSIMCard(appContext,PersonOrderActivity.this))
                        return;
                    getPermission(Manifest.permission.CALL_PHONE, new PermissionListener() {
                        @Override
                        public void accept(String permission) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            Uri data = Uri.parse("tel:" + mode.getBusiness_info().getMerchant_telephone());
                            intent.setData(data);
                            startActivity(intent);
                        }

                        @Override
                        public void refuse(String permission) {

                        }
                    });
                });
                break;
            case R.id.chat:
                Intent intent1 = new Intent(appContext, ChatActivity.class);
                intent1.putExtra("UserId", mode.getBusiness_info().getMerchant_telephone());
                intent1.putExtra("Name", mode.getBusiness_info().getShop_name());
                startActivity(intent1);
                goToAnimation(1);
                break;
            case R.id.service_agreement:
                intent = new Intent(appContext, WebActivity.class);
                intent.putExtra("Type", 7);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.select_view:
                selectView.setSelected(!selectView.isSelected());
                selectView.setBackgroundResource(selectView.isSelected() ? R.drawable.position_un_select : R.drawable.position_select);
                break;
            case R.id.remark_layout:
                intent = new Intent(appContext, RemarkActivity.class);
                intent.putExtra("RemarkTo", new Gson().fromJson(mode.getRemarks(), RemarkTo.class));
                intent.putExtra("IsOrderEnter", true);
                startActivity(intent);

                break;
            case R.id.insurance_layout:
                intent = new Intent(appContext, WebActivity.class);
                intent.putExtra("Type", 9);
                startActivity(intent);
                goToAnimation(1);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getIntent().getBooleanExtra("IsFinishPay", false)) {
                Intent intent = new Intent(appContext, MainActivity.class);
                startActivity(intent);
                finish();
                goToAnimation(1);
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}

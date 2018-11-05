package com.hzxmkuar.wumeihui.personal.order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.AlertDialog;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;

import com.hzxmkuar.wumeihui.base.Event;
import com.hzxmkuar.wumeihui.base.WebActivity;
import com.hzxmkuar.wumeihui.personal.inquiry.ServiceDetailActivity;
import com.hzxmkuar.wumeihui.personal.order.presenter.OrderSettlePresenter;
import com.ruffian.library.RTextView;
import com.zhy.autolayout.AutoRelativeLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzxmkuar.com.applibrary.domain.inquery.InquiryInfoTo;
import hzxmkuar.com.applibrary.domain.main.MainMerchantTo;
import hzxmkuar.com.applibrary.domain.order.OrderSettleInfoTo;
import hzxmkuar.com.applibrary.domain.order.OrderSettleTo;
import hzxmkuar.com.applibrary.domain.order.RemarkTo;
import hzxmkuar.com.applibrary.domain.order.SelectCouponTo;

/**
 * Created by Administrator on 2018/8/31.
 **/

public class OrderSettleActivity extends BaseActivity {
    @BindView(R.id.image_layout)
    GridLayout imageLayout;
    @BindView(R.id.shop_name)
    TextView shopName;
    @BindView(R.id.statue)
    TextView statue;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.use_time)
    TextView useTime;
    @BindView(R.id.contact_name)
    TextView contactName;
    @BindView(R.id.service_num)
    TextView serviceNum;
    @BindView(R.id.pay_money)
    TextView payMoney;
    @BindView(R.id.submit)
    TextView submit;
    @BindView(R.id.back)
    AutoRelativeLayout back;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.title_layout)
    AutoRelativeLayout titleLayout;
    @BindView(R.id.all_pay)
    RTextView allPay;
    @BindView(R.id.separate_pay)
    RTextView separatePay;
    @BindView(R.id.service_fee)
    TextView serviceFee;
    @BindView(R.id.coupon_fee)
    TextView couponFee;
    @BindView(R.id.earnest_fee)
    TextView earnestFee;
    @BindView(R.id.earnest)
    AutoRelativeLayout earnest;
    @BindView(R.id.pay_text)
    TextView payText;
    @BindView(R.id.pay_fee)
    TextView payFee;
    @BindView(R.id.coupon_name)
    TextView couponName;

    private OrderSettlePresenter presenter;
    private MainMerchantTo.ListsBean merchantTo;
    private OrderSettleTo infoTo;
    private InquiryInfoTo inquiryTo;
    private RemarkTo remarkTo;
    private int couponId;
    private double couponMoney;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_settle);
        ButterKnife.bind(this);
        setTitleName(Constant.ORDER_SETTLE);

        presenter = new OrderSettlePresenter(this);
        inquiryTo = (InquiryInfoTo) getIntent().getSerializableExtra("InquiryTo");
        EventBus.getDefault().register(this);

    }

    @SuppressLint("SetTextI18n")
    public void setView(OrderSettleTo infoTo) {
        this.infoTo = infoTo;
        if (infoTo.getCoupon_info()!=null) {
            couponName.setText("优惠" + infoTo.getCoupon_info().getCoupon_amount() + "元");
            couponId=infoTo.getCoupon_info().getCoupon_id();
            couponMoney=infoTo.getCoupon_info().getCoupon_amount();
            couponFee.setText("-"+couponMoney);
        }else {
            couponName.setText("优惠0元");
            couponFee.setText("-0");
            couponId=0;
            couponMoney=0;
        }
        merchantTo = (MainMerchantTo.ListsBean) getIntent().getSerializableExtra("MerchantTo");
        shopName.setText(merchantTo.getShop_name());

        address.setText(infoTo.getService_address());
        statue.setText(inquiryTo.getStatus_arr().getStatus_txt());
        useTime.setText(infoTo.getUse_time());
        contactName.setText(infoTo.getContact_name() + "   " + infoTo.getContact_telphone());
        serviceNum.setText("共" + infoTo.getService_num() + "项服务");

        separatePay.setText("定金支付\n需先支付" + infoTo.getPrice_detail().getDeposit_payment().getPay_amount());
        payFee.setText(infoTo.getPrice_detail().getFull_payment().getPay_amount() + "");
        payMoney.setText(infoTo.getPrice_detail().getFull_payment().getPay_amount() + "");
        earnestFee.setText(infoTo.getPrice_detail().getDeposit_payment().getPay_amount() + "");
        serviceFee.setText(infoTo.getPrice_detail().getService_fee() + "");



    }


    @SuppressLint("SetTextI18n")
    @OnClick({R.id.service_layout, R.id.all_pay, R.id.separate_pay, R.id.coupon_layout, R.id.remark_layout, R.id.earnest, R.id.submit,R.id.insurance_layout,R.id.service_agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.all_pay:
                allPay.setBackgroundColorNormal(Color.parseColor("#3bafd9"));
                separatePay.setBackgroundColorNormal(Color.parseColor("#f7f7f7"));
                allPay.setTextColor(Color.parseColor("#ffffff"));
                separatePay.setTextColor(Color.parseColor("#999999"));
                payFee.setText((infoTo.getPrice_detail().getFull_payment().getPay_amount()+(infoTo.getCoupon_info()==null?0:infoTo.getCoupon_info().getCoupon_amount())-couponMoney) + "");
                payMoney.setText((infoTo.getPrice_detail().getFull_payment().getPay_amount()+(infoTo.getCoupon_info()==null?0:infoTo.getCoupon_info().getCoupon_amount())-couponMoney) + "");
                earnest.setVisibility(View.GONE);
                payText.setText("需支付");
                break;
            case R.id.separate_pay:

                allPay.setBackgroundColorNormal(Color.parseColor("#f7f7f7"));
                separatePay.setBackgroundColorNormal(Color.parseColor("#3bafd9"));

                allPay.setTextColor(Color.parseColor("#999999"));
                separatePay.setTextColor(Color.parseColor("#ffffff"));
                payText.setText("后续需支付");
                payFee.setText(infoTo.getPrice_detail().getDeposit_payment().getLast_amount() + "");
                payMoney.setText(infoTo.getPrice_detail().getDeposit_payment().getPay_amount()+(infoTo.getCoupon_info()==null?0:infoTo.getCoupon_info().getCoupon_amount())-couponMoney + "");
                earnest.setVisibility(View.VISIBLE);

                break;
            case R.id.coupon_layout:
                Intent intent = new Intent(appContext, SelectCouponActivity.class);
                intent.putExtra("Mode", earnest.getVisibility() == View.VISIBLE ? 2 : 1);
                intent.putExtra("BusofferId", merchantTo.getBusoffer_id());
                startActivityForResult(intent, 30);
                goToAnimation(1);
                break;
            case R.id.remark_layout:
                intent = new Intent(appContext, RemarkActivity.class);
                intent.putExtra("RemarkTo", remarkTo);
                startActivityForResult(intent, 40);
                goToAnimation(1);
                break;
            case R.id.earnest:
                break;
            case R.id.service_layout:
                intent = new Intent(appContext, ServiceDetailActivity.class);
                intent.putExtra("Id", inquiryTo.getId());
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.submit:
                NiftyDialogBuilder dialog = NiftyDialogBuilder.getInstance(this);
                dialog.setContentView(R.layout.order_tip_layout);
                dialog.findViewById(R.id.parent).setOnClickListener(view1 -> dialog.dismiss());
                dialog.findViewById(R.id.confirm).setOnClickListener(view1 -> {
                    dialog.dismiss();
                    if (remarkTo == null)
                        remarkTo = new RemarkTo();
                    presenter.settle(getIntent().getIntExtra("BusofferId", 0), earnest.getVisibility() == View.VISIBLE ? 2 : 1, couponId, JSON.toJSONString(remarkTo));
                });
                dialog.show();


                break;
            case R.id.insurance_layout:
                intent=new Intent(appContext, WebActivity.class);
                intent.putExtra("Type",9);
                intent.putExtra("Title","保险");
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.service_agreement:
                intent=new Intent(appContext, WebActivity.class);
                intent.putExtra("Type",7);
                intent.putExtra("Title","服务政策");
                startActivity(intent);
                goToAnimation(1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 40) {

            remarkTo = (RemarkTo) data.getSerializableExtra("RemarkTo");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void submitDataSuccess(Object data) {
        Intent intent = new Intent(appContext, SelectPayActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Money",((OrderSettleInfoTo) data).getTotal_amount()+"");
        intent.putExtra("OrderId", ((OrderSettleInfoTo) data).getOrder_id());
        intent.putExtra("Type",10);
        startActivity(intent);
        finish();
        goToAnimation(1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @SuppressLint("SetTextI18n")
    @Subscribe
    public void receiverCoupon(Event<SelectCouponTo.ListsBean> event){
        if ("NoUseCoupon".equals(event.getType())){

            couponName.setText("优惠0元");
            couponFee.setText("-0");
            couponMoney=0;
            if (infoTo.getCoupon_info()==null) {
                payFee.setText(infoTo.getPrice_detail().getFull_payment().getPay_amount() + "");
                payMoney.setText(infoTo.getPrice_detail().getFull_payment().getPay_amount() + "");
            }else {
                payFee.setText(infoTo.getPrice_detail().getFull_payment().getPay_amount() + infoTo.getCoupon_info().getCoupon_amount()+"");
                payMoney.setText(infoTo.getPrice_detail().getFull_payment().getPay_amount()+ infoTo.getCoupon_info().getCoupon_amount() + "");
            }
            earnestFee.setText(infoTo.getPrice_detail().getDeposit_payment().getPay_amount()+ couponMoney+ "");
        }
        if ("SelectCoupon".equals(event.getType())){
            SelectCouponTo.ListsBean mode=event.getData();
            couponMoney=Double.valueOf(mode.getAmount());
            couponName.setText("优惠"+mode.getAmount()+"元");
            couponFee.setText("-"+mode.getAmount());
            earnestFee.setText(infoTo.getPrice_detail().getDeposit_payment().getPay_amount() + "");
            if (infoTo.getCoupon_info()==null) {
                payFee.setText(infoTo.getPrice_detail().getFull_payment().getPay_amount() + "");
                payMoney.setText(infoTo.getPrice_detail().getFull_payment().getPay_amount() + "");
                earnestFee.setText(infoTo.getPrice_detail().getDeposit_payment().getPay_amount() + couponMoney+"");
            }else {
                payFee.setText(infoTo.getPrice_detail().getFull_payment().getPay_amount() + infoTo.getCoupon_info().getCoupon_amount()-Double.valueOf(mode.getAmount())+"");
                payMoney.setText(infoTo.getPrice_detail().getFull_payment().getPay_amount()+ infoTo.getCoupon_info().getCoupon_amount()-Double.valueOf(mode.getAmount()) + "");
                earnestFee.setText(infoTo.getPrice_detail().getDeposit_payment().getPay_amount() +infoTo.getCoupon_info().getCoupon_amount()-couponMoney+ "");
            }
        }
    }
}

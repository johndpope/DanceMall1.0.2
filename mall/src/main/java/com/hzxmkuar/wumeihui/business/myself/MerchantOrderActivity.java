package com.hzxmkuar.wumeihui.business.myself;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.gson.Gson;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.AlertDialog;
import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.Constant;
import com.hzxmkuar.wumeihui.base.WebActivity;
import com.hzxmkuar.wumeihui.base.util.AppUtil;
import com.hzxmkuar.wumeihui.business.myself.presenter.MerchantOrderPresenter;
import com.hzxmkuar.wumeihui.message.ChatActivity;
import com.hzxmkuar.wumeihui.personal.inquiry.ServiceDetailActivity;
import com.hzxmkuar.wumeihui.personal.order.RemarkActivity;
import com.ruffian.library.RTextView;
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

public class MerchantOrderActivity extends BaseActivity {
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
    @BindView(R.id.cancel)
    RTextView cancel;
    @BindView(R.id.confirm)
    RTextView confirm;
    @BindView(R.id.bond_pay_layout)
    AutoRelativeLayout bondPayLayout;
    @BindView(R.id.tail_money)
    TextView tailMoney;
    @BindView(R.id.pay_text)
    TextView payText;
    @BindView(R.id.count_time)
    CountdownView countTime;
    @BindView(R.id.time_layout)
    AutoLinearLayout timeLayout;
    @BindView(R.id.select_view)
    View selectView;
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

    private MerchantOrderPresenter presenter;
    private MerchantOrderDetailTo mode;
    private String serviceName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_person);
        ButterKnife.bind(this);
        setTitleName(Constant.ORDER_DETAIL);
        presenter = new MerchantOrderPresenter(this);

    }

    private void setButtomLayout(MerchantOrderDetailTo mode) {
        confirm.setOnClickListener(view -> buttomClick(mode));
        cancel.setOnClickListener(view -> {
            AlertDialog.show(MerchantOrderActivity.this, "取消订单").setOnClickListener(view1 -> {
                AlertDialog.dismiss();
                presenter.cancelReceiver(mode.getOrder_id());
            });
        });
        if ("等待付款".equals(mode.getStatus_txt()) || "服务完成".equals(mode.getStatus_txt())) {
            confirm.setVisibility(View.GONE);
            cancel.setVisibility(View.GONE);
            if ("等待付款".equals(mode.getStatus_txt()))
                timeLayout.setVisibility(View.VISIBLE);
            countTime.start(mode.getRemaining_pay_valid_time() * 1000);
        }
        if ("待服务".equals(mode.getStatus_txt())) {
            confirm.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.GONE);
            confirm.setText("确定开始");
        }
        if ("服务中".equals(mode.getStatus_txt())) {
            confirm.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.GONE);
            confirm.setText("确定完成");
        }
        if ("待付尾款".equals(mode.getStatus_txt())) {
            confirm.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.GONE);
            confirm.setText("联系客户");

        }
        if ("客户确认完成".equals(mode.getStatus_txt()) || "确认完成".equals(mode.getStatus_txt())) {
            confirm.setVisibility(View.GONE);
            cancel.setVisibility(View.GONE);
            confirm.setText("联系客户");

        }

    }

    @SuppressLint("SetTextI18n")
    private void buttomClick(MerchantOrderDetailTo mode) {
        if ("待确认".equals(mode.getStatus_txt())) {
            NiftyDialogBuilder dialog = NiftyDialogBuilder.getInstance(this);
            dialog.setContentView(R.layout.confirm_receiver_order_layout);
            dialog.findViewById(R.id.parent).setOnClickListener(view -> dialog.dismiss());
            dialog.findViewById(R.id.cancel).setOnClickListener(view -> dialog.dismiss());
            dialog.findViewById(R.id.confirm).setOnClickListener(view -> {
                presenter.confirmReceiver(mode.getOrder_id());
                dialog.dismiss();
            });
            ((TextView) dialog.findViewById(R.id.address)).setText(mode.getService_address());
            ((TextView) dialog.findViewById(R.id.use_time)).setText(mode.getUse_time());
            serviceName = "";
            Observable.from(mode.getService_list()).subscribe(serviceListBean -> serviceName = serviceName + serviceListBean.getService_name() + " ");
            ((TextView) dialog.findViewById(R.id.service_name)).setText(serviceName);
            dialog.show();


        } else {
            AlertDialog.show(this, "待确认".equals(mode.getStatus_txt()) ? "确认接收订单" :
                    "待付尾款".equals(mode.getStatus_txt()) ? "催付尾款" :
                            "待服务".equals(mode.getStatus_txt()) ? "确认开始订单" : "确认完成订单"
            ).setOnClickListener(view -> {
                AlertDialog.dismiss();

                if ("待服务".equals(mode.getStatus_txt()))
                    presenter.startOrder(mode.getOrder_id());
                if ("服务中".equals(mode.getStatus_txt()))
                    presenter.finishOrder(mode.getOrder_id());
                if ("待付尾款".equals(mode.getStatus_txt())) {
                    Intent intent = new Intent(appContext, ChatActivity.class);
                    intent.putExtra("UserId", mode.getContact_telphone());
                    intent.putExtra("Name", mode.getContact_name());
                    startActivity(intent);
                    goToAnimation(1);
                }
//                    getPermission(Manifest.permission.CALL_PHONE, new PermissionListener() {
//                        @Override
//                        public void accept(String permission) {
//                            Intent intent = new Intent(Intent.ACTION_CALL);
//                            Uri data = Uri.parse("tel:" + mode.getContact_telphone());
//                            intent.setData(data);
//                            startActivity(intent);
//                        }
//
//                        @Override
//                        public void refuse(String permission) {
//
//                        }
//                    });

            });
        }

        if (mode.getPrice_detail().getDeposit_payment() != null)
            lastPayTime.setText(mode.getPrice_detail().getDeposit_payment().getLast_amount_paytime());
        refundLayout.setVisibility(mode.getPrice_detail().getRefund_data().getIs_refund()==0?View.GONE:View.VISIBLE);
        refundText.setText("违约金-"+mode.getPrice_detail().getRefund_data().getRefund_type());
        refundMoney.setText("￥"+mode.getPrice_detail().getRefund_data().getRefund_amount());
        breachLayout.setVisibility(mode.getPrice_detail().getRefund_data().getIs_liquidated_damages()==0?View.GONE:View.VISIBLE);
        breachText.setText("已退款-"+mode.getPrice_detail().getRefund_data().getLiquidated_damages_type());
        breachMoney.setText("￥"+mode.getPrice_detail().getRefund_data().getLiquidated_damages());
        if (mode.getPayment_mode()==2){
            lastPayTime.setVisibility(mode.getPrice_detail().getDeposit_payment().getLast_amount_status()==1?View.GONE:View.VISIBLE);
        }

    }

    @Override
    public void loadDataSuccess(Object data) {
        mode = (MerchantOrderDetailTo) data;
        setButtomLayout(mode);
        orderSn.setText(mode.getOrder_sn());
        statue.setText(mode.getStatus_txt());
        address.setText(mode.getService_address());
        useTime.setText(mode.getUse_time());
        contact.setText(mode.getContact_name() + "  " + mode.getContact_telphone());
        imageLayout.removeAllViews();
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

        bondPayLayout.setVisibility(mode.getButton_list().getZfdj_btn() == 1 ? View.VISIBLE : View.GONE);
        earnest.setText("-" + mode.getPrice_detail().getDeposit_payment().getPay_amount());
        payText.setText(mode.getButton_list().getZfdj_btn() == 1 ? "后续需支付" : "需支付");
        tailMoney.setText("￥" + (mode.getButton_list().getZfdj_btn() == 1 ? mode.getPrice_detail().getDeposit_payment().getLast_amount() : mode.getPrice_detail().getFull_payment().getPay_amount()));

    }

    @OnClick({R.id.chat, R.id.phone, R.id.select_view, R.id.service_agreement, R.id.remark_layout, R.id.service_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.phone:
                if (!AppUtil.readSIMCard(appContext))
                    return;
                AlertDialog.show(this, "确认拨打电话").setOnClickListener(view1 -> {
                    AlertDialog.dismiss();
                    getPermission(Manifest.permission.CALL_PHONE, new PermissionListener() {
                        @Override
                        public void accept(String permission) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            Uri data = Uri.parse("tel:" + mode.getContact_telphone());
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
                intent1.putExtra("UserId", mode.getContact_telphone());
                intent1.putExtra("Name", mode.getContact_name());
                startActivity(intent1);
                goToAnimation(1);
                break;
            case R.id.select_view:
                selectView.setSelected(!selectView.isSelected());
                selectView.setBackgroundResource(selectView.isSelected() ? R.drawable.position_un_select : R.drawable.position_select);
                break;
            case R.id.service_agreement:
                Intent intent = new Intent(appContext, WebActivity.class);
                intent.putExtra("Type", 7);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.remark_layout:
                intent = new Intent(appContext, RemarkActivity.class);
                intent.putExtra("RemarkTo", new Gson().fromJson(mode.getRemarks(), RemarkTo.class));
                intent.putExtra("IsOrderEnter", true);
                startActivity(intent);
                break;
            case R.id.service_layout:
                intent = new Intent(appContext, ServiceDetailActivity.class);
                intent.putExtra("Id", mode.getOrder_id());
                startActivity(intent);
                break;
        }
    }
}

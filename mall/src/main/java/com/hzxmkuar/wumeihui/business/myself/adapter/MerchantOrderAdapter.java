package com.hzxmkuar.wumeihui.business.myself.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.adapter.BaseAdapter;
import com.hzxmkuar.wumeihui.base.adapter.BindingHolder;
import com.hzxmkuar.wumeihui.databinding.MerchantOrderItemBinding;
import com.hzxmkuar.wumeihui.databinding.MyMerchantOrderItemBinding;
import com.hzxmkuar.wumeihui.databinding.MyOrderItemBinding;
import com.hzxmkuar.wumeihui.databinding.MyQuoteItemBinding;

import java.util.ArrayList;
import java.util.List;

import hzxmkuar.com.applibrary.domain.order.MyMerchantOrderTo;
import hzxmkuar.com.applibrary.main.DemandSearchTo;
import rx.Observable;

/**
 * Created by Administrator on 2018/8/28.
 **/

public class MerchantOrderAdapter extends BaseAdapter<MyMerchantOrderTo.ListsBean, MyMerchantOrderItemBinding> {
    private String imagePath;


    public MerchantOrderAdapter(Activity context) {
        super(context);

    }

    @NonNull
    @Override
    public BindingHolder<MyMerchantOrderItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {

        MyMerchantOrderItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.my_merchant_order_item, parent, false);

        BindingHolder<MyMerchantOrderItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(BindingHolder<MyMerchantOrderItemBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        MyMerchantOrderItemBinding binding = holder.getBinding();
        MyMerchantOrderTo.ListsBean mode = mList.get(position);
        binding.cancel.setText("取消订单");
        binding.confirm.setText("确定接单");
        binding.inquirySn.setText(mode.getOrder_sn());
        binding.statue.setText(mode.getStatus_txt());
        binding.serviceNum.setText("共" + mode.getService_num() + "项服务");
        binding.countTime.start(mode.getRemaining_pay_valid_time() * 1000);
        binding.price.setText("总金额：" + mode.getTotal_amount() + "  " + (mode.getPayment_mode() == 1 ? "" : ("定金" + mode.getTotal_amount1())) + (mode.getTotal_amount2() > 0 ? ("  尾款 " + mode.getTotal_amount2()) : ""));
        if (mode.getService_list() != null) {
            imagePath = "";
            Observable.from(mode.getService_list()).subscribe(serviceListBean -> imagePath = imagePath + serviceListBean.getService_img() + ",");
            setImageLayout(imagePath.substring(0, imagePath.length() - 1), binding.imageLayout);
        }
        binding.cancel.setOnClickListener(view -> {
            if (listener != null)
                listener.cancleClick(mode);
        });
        binding.confirm.setOnClickListener(view -> {

            if (listener != null) {

                listener.confirmClick(mode);
            }
        });
        binding.countTimeLayout.setVisibility(("等待付款".equals(mode.getStatus_txt()) || "待付尾款".equals(mode.getStatus_txt())) ? View.VISIBLE : View.GONE);
        if ("等待付款".equals(mode.getStatus_txt()) || "服务完成".equals(mode.getStatus_txt())) {
            binding.confirm.setVisibility(View.GONE);
            binding.cancel.setVisibility(View.GONE);
        } else if ("待服务".equals(mode.getStatus_txt())) {
            binding.confirm.setVisibility(View.VISIBLE);
            binding.cancel.setVisibility(View.GONE);
            binding.confirm.setText("确定开始");
        } else if ("服务中".equals(mode.getStatus_txt())) {
            binding.confirm.setVisibility(View.VISIBLE);
            binding.cancel.setVisibility(View.GONE);
            binding.confirm.setText("确定完成");
        } else if ("待付尾款".equals(mode.getStatus_txt())) {
            binding.confirm.setVisibility(View.VISIBLE);
            binding.cancel.setVisibility(View.GONE);
            binding.confirm.setText("联系客户");
        } else if ("确认完成".equals(mode.getStatus_txt())||"客户确认完成".equals(mode.getStatus_txt())) {
            binding.confirm.setVisibility(View.GONE);
            binding.cancel.setVisibility(View.GONE);

        }
    }

    public OrderClickListener listener;

    public void setOrderClickListener(OrderClickListener listener) {
        this.listener = listener;
    }

    public interface OrderClickListener {

        void confirmClick(MyMerchantOrderTo.ListsBean mode);

        void cancleClick(MyMerchantOrderTo.ListsBean mode);
    }


}

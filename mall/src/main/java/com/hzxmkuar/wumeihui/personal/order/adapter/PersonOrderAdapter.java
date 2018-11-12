package com.hzxmkuar.wumeihui.personal.order.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.adapter.BaseAdapter;
import com.hzxmkuar.wumeihui.base.adapter.BindingHolder;
import com.hzxmkuar.wumeihui.databinding.MyQuoteItemBinding;
import com.hzxmkuar.wumeihui.databinding.PersonOrderItemBinding;

import hzxmkuar.com.applibrary.domain.order.MyMerchantOrderTo;
import rx.Observable;

/**
 * Created by Administrator on 2018/8/28.
 **/

public class PersonOrderAdapter extends BaseAdapter<MyMerchantOrderTo.ListsBean, PersonOrderItemBinding> {
    private String imagePath;
    private int type;



    public PersonOrderAdapter(Activity context) {
        super(context);

    }

    @NonNull
    @Override
    public BindingHolder<PersonOrderItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {

        PersonOrderItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.person_order_item, parent, false);

        BindingHolder<PersonOrderItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(BindingHolder<PersonOrderItemBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        PersonOrderItemBinding binding = holder.getBinding();
        MyMerchantOrderTo.ListsBean mode = mList.get(position);
        binding.cancel.setText("取消订单");
        binding.confirm.setText("确定接单");
        binding.inquirySn.setText(mode.getOrder_sn());
        binding.statue.setText(mode.getStatus_txt());
        binding.countTime.start(mode.getRemaining_pay_valid_time()*1000);
        binding.serviceNum.setText("共" + mode.getService_num() + "项服务");
        binding.countTimeLayout.setVisibility((mode.getNew_status()==1||mode.getNew_status()==3)?View.VISIBLE:View.GONE);
        binding.price.setText("总金额："+mode.getTotal_amount()+"  "+(mode.getPayment_mode()==1?"":("定金"+mode.getTotal_amount1()))+(mode.getTotal_amount2()>0?("  尾款 "+mode.getTotal_amount2()):""));
        if (mode.getService_list() != null) {
            imagePath = "";
            Observable.from(mode.getService_list()).subscribe(serviceListBean -> imagePath = imagePath + serviceListBean.getService_img() + ",");
            setImageLayout(imagePath.substring(0, imagePath.length() - 1), binding.imageLayout);
        }
        binding.cancel.setOnClickListener(view -> {
            if (listener!=null)
                listener.cancleClick(mode);
        });
        binding.confirm.setOnClickListener(view -> {

            if (listener!=null) {

                listener.confirmClick(mode);
            }
        });

            binding.confirm.setVisibility(View.GONE);
            binding.cancel.setVisibility(View.GONE);

        if ("等待付款".equals(mode.getStatus_txt())){
            binding.confirm.setVisibility(View.VISIBLE);
            binding.cancel.setVisibility(View.VISIBLE);
            binding.confirm.setText(mode.getPayment_mode()==1?"支付全款":"支付定金");

            type=1;
        }
        if ("待商家确认".equals(mode.getStatus_txt())){
            binding.confirm.setVisibility(View.GONE);
            binding.cancel.setVisibility(View.VISIBLE);
            type=2;

        }
        if ("待付尾款".equals(mode.getStatus_txt())){
            binding.confirm.setVisibility(View.VISIBLE);
            binding.cancel.setVisibility(View.VISIBLE);
            binding.confirm.setText("支付尾款");

            type=3;
        }
        if ("待服务".equals(mode.getStatus_txt())){
            binding.confirm.setVisibility(View.VISIBLE);
            binding.cancel.setVisibility(View.VISIBLE);
            binding.confirm.setText("联系商家");
            mode.setTitle("确认联系商家");
            type=4;
        }
        if ("服务中".equals(mode.getStatus_txt())){
            binding.confirm.setVisibility(View.GONE);
            binding.cancel.setVisibility(View.GONE);
            type=5;

        }
        if ("服务完成".equals(mode.getStatus_txt())){
            binding.confirm.setVisibility(View.VISIBLE);
            binding.cancel.setVisibility(View.GONE);
            binding.confirm.setText("确认完成");
            mode.setTitle("确认完成订单");
            type=6;
        }
        if ("确认完成".equals(mode.getStatus_txt())){
            binding.confirm.setVisibility(View.GONE);
            binding.cancel.setVisibility(View.GONE);
            binding.confirm.setText("评价晒单");

            type=7;
        }
        mode.setType(type);
    }
    public OrderClickListener listener;
    public void setOrderClickListener(OrderClickListener listener){
        this.listener=listener;
    }

    public interface OrderClickListener {

        void confirmClick(MyMerchantOrderTo.ListsBean mode);

        void cancleClick(MyMerchantOrderTo.ListsBean mode);
    }



}

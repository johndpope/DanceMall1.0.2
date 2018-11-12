package com.hzxmkuar.wumeihui.personal.order.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.adapter.BaseAdapter;
import com.hzxmkuar.wumeihui.base.adapter.BindingHolder;
import com.hzxmkuar.wumeihui.base.util.DateUtil;
import com.hzxmkuar.wumeihui.databinding.ReceiverCouponItemBinding;
import com.hzxmkuar.wumeihui.databinding.SelectCouponItemBinding;

import hzxmkuar.com.applibrary.domain.order.SelectCouponTo;

/**
 * Created by Administrator on 2018/8/28.
 **/

public class ReceiverCouponAdapter extends BaseAdapter<SelectCouponTo.ListsBean, ReceiverCouponItemBinding> {
    private int type;
    public ReceiverCouponAdapter(Activity context, int type) {
        super(context);
        this.type=type;
    }



    @NonNull
    @Override
    public BindingHolder<ReceiverCouponItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {

        ReceiverCouponItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.receiver_coupon_item, parent, false);

        BindingHolder<ReceiverCouponItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(BindingHolder<ReceiverCouponItemBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        ReceiverCouponItemBinding binding = holder.getBinding();
        SelectCouponTo.ListsBean mode=mList.get(position);
        String payMoney=mode.getDiscount_amount()+"";
        if (payMoney.length()>2) {
            payMoney = payMoney.substring(0, 3);
            if (payMoney.endsWith(".")){
                if (mode.getDiscount_amount()>10)
                    payMoney=payMoney.substring(0,2);


            }

        }

        binding.money.setText(payMoney);
        binding.couponName.setText(mode.getCate_name());
        binding.couponDes.setText(mode.getCate_desc());
        binding.useTime.setText(mode.getUse_times());
        binding.money.setTextColor(type==0? Color.parseColor("#3FB9FF"):Color.parseColor("#bbbbbb"));
        binding.moneySymbol.setTextColor(type==0? Color.parseColor("#3FB9FF"):Color.parseColor("#bbbbbb"));
        binding.receiver.setOnClickListener(view -> {
            if (listener!=null)
                listener.receiverClick(mode,position);
        });


    }

    public interface ReceiverCouponClickListener{
        void receiverClick(SelectCouponTo.ListsBean mode,int position);
    }

    public ReceiverCouponClickListener listener;

    public void setReceiverListener(ReceiverCouponClickListener listener){
        this.listener=listener;
    }


}

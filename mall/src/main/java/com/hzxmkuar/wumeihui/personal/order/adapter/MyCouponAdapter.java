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
import com.hzxmkuar.wumeihui.databinding.CouponItemBinding;

import hzxmkuar.com.applibrary.domain.order.MyCouponTo;

/**
 * Created by Administrator on 2018/8/28.
 **/

public class MyCouponAdapter extends BaseAdapter<MyCouponTo.CouponTo, CouponItemBinding> {
    private int type;
    public MyCouponAdapter(Activity context, int type) {
        super(context);
        this.type=type;
    }



    @NonNull
    @Override
    public BindingHolder<CouponItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {

        CouponItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.coupon_item, parent, false);

        BindingHolder<CouponItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(BindingHolder<CouponItemBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        CouponItemBinding binding = holder.getBinding();
        MyCouponTo.CouponTo mode=mList.get(position);
        String payMoney=mode.getAmount()+"";
        if (payMoney.length()>2)
            payMoney=payMoney.substring(0,3);
        binding.money.setText(payMoney.endsWith(".")?payMoney.substring(0,2):payMoney);
        binding.couponName.setText(mode.getCate_name());
        binding.couponDes.setText(mode.getCate_desc());
        binding.useTime.setText(DateUtil.longToString(mode.getStart_time(),DateUtil.mDateFormatString)+"-"+DateUtil.longToString(mode.getEnd_time(),DateUtil.mDateFormatString));
        binding.money.setTextColor(type==0? Color.parseColor("#3FB9FF"):Color.parseColor("#bbbbbb"));
        binding.moneySymbol.setTextColor(type==0? Color.parseColor("#3FB9FF"):Color.parseColor("#bbbbbb"));
        binding.couponName.setTextColor(type==0? Color.parseColor("#3FB9FF"):Color.parseColor("#bbbbbb"));
    }


}

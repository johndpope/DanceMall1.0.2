package com.hzxmkuar.wumeihui.personal.order.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.adapter.BaseAdapter;
import com.hzxmkuar.wumeihui.base.adapter.BindingHolder;
import com.hzxmkuar.wumeihui.base.util.DateUtil;
import com.hzxmkuar.wumeihui.databinding.CouponItemBinding;
import com.hzxmkuar.wumeihui.databinding.SelectCouponItemBinding;

import hzxmkuar.com.applibrary.domain.order.MyCouponTo;
import hzxmkuar.com.applibrary.domain.order.SelectCouponTo;

/**
 * Created by Administrator on 2018/8/28.
 **/

public class SelectCouponAdapter extends BaseAdapter<SelectCouponTo.ListsBean, SelectCouponItemBinding> {
    private int type;
    public SelectCouponAdapter(Activity context,int type) {
        super(context);
        this.type=type;
    }



    @NonNull
    @Override
    public BindingHolder<SelectCouponItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {

        SelectCouponItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.select_coupon_item, parent, false);

        BindingHolder<SelectCouponItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(BindingHolder<SelectCouponItemBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        SelectCouponItemBinding binding = holder.getBinding();
        SelectCouponTo.ListsBean mode=mList.get(position);
        binding.money.setText(mode.getAmount()+"");
        if (binding.money.getText().toString().endsWith(".00"))
            binding.money.setText(binding.money.getText().toString().substring(0,binding.money.getText().toString().length()-1));
        binding.couponName.setText(mode.getCate_name());
        binding.couponDes.setText(mode.getCate_desc());
        binding.useTime.setText(DateUtil.longToString(mode.getStart_time(),DateUtil.mDateFormatString)+"-"+DateUtil.longToString(mode.getEnd_time(),DateUtil.mDateFormatString));
        binding.useTime.setText(DateUtil.longToString(mode.getStart_time()*1000,DateUtil.mDateFormatString)+"-"+DateUtil.longToString(mode.getEnd_time()*1000,DateUtil.mDateFormatString));
        binding.money.setTextColor(type==0? Color.parseColor("#3FB9FF"):Color.parseColor("#bbbbbb"));
        binding.moneySymbol.setTextColor(type==0? Color.parseColor("#3FB9FF"):Color.parseColor("#bbbbbb"));
        binding.couponName.setTextColor(type==0? Color.parseColor("#3FB9FF"):Color.parseColor("#bbbbbb"));



    }


}

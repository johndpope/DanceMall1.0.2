package com.hzxmkuar.wumeihui.personal.integral.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.adapter.BaseAdapter;
import com.hzxmkuar.wumeihui.base.adapter.BindingHolder;
import com.hzxmkuar.wumeihui.databinding.MyIntegralItemBinding;
import com.hzxmkuar.wumeihui.databinding.MyIntegralOrderItemBinding;
import com.hzxmkuar.wumeihui.databinding.MyOrderItemBinding;

import java.util.ArrayList;
import java.util.List;

import hzxmkuar.com.applibrary.domain.integral.IntegralOrderTo;
import hzxmkuar.com.applibrary.main.DemandSearchTo;

/**
 * Created by Administrator on 2018/8/28.
 **/

public class MyIntegralAdapter extends BaseAdapter<IntegralOrderTo.ListsBean, MyIntegralOrderItemBinding> {
    public MyIntegralAdapter(Activity context) {
        super(context);
    }



    @NonNull
    @Override
    public BindingHolder<MyIntegralOrderItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {

        MyIntegralOrderItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.my_integral_order_item, parent, false);

        BindingHolder<MyIntegralOrderItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(BindingHolder<MyIntegralOrderItemBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        MyIntegralOrderItemBinding binding = holder.getBinding();
        IntegralOrderTo.ListsBean mode=mList.get(position);
        binding.integral.setText(mode.getTotal_amount());
        binding.goodsName.setText(mode.getGoods_name());
        binding.orderSn.setText(mode.getOrder_sn());
        binding.statue.setText(mode.getStatus_txt());
        disPlayImage(binding.goodsImage,mode.getGoods_image());

    }


}

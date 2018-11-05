package com.hzxmkuar.wumeihui.business.merchant.adapter;

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
import com.hzxmkuar.wumeihui.databinding.MyOrderItemBinding;

import java.util.ArrayList;
import java.util.List;

import hzxmkuar.com.applibrary.main.DemandSearchTo;

/**
 * Created by Administrator on 2018/8/28.
 **/

public class MyOrderAdapter extends BaseAdapter<DemandSearchTo, MyOrderItemBinding> {
    private List<TextView>typeNameList=new ArrayList<>();
    public MyOrderAdapter(Activity context) {
        super(context);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @NonNull
    @Override
    public BindingHolder<MyOrderItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {

        MyOrderItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.my_order_item, parent, false);

        BindingHolder<MyOrderItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(BindingHolder<MyOrderItemBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        MyOrderItemBinding binding = holder.getBinding();
    }


}

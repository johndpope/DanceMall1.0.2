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
import com.hzxmkuar.wumeihui.databinding.MyIntegrationItemBinding;

import java.util.ArrayList;
import java.util.List;

import hzxmkuar.com.applibrary.domain.integral.GoodsTo;

/**
 * Created by Administrator on 2018/8/28.
 **/

public class IntegrationAdapter extends BaseAdapter<GoodsTo.ListsBean, MyIntegrationItemBinding> {
    public IntegrationAdapter(Activity context) {
        super(context);
    }



    @NonNull
    @Override
    public BindingHolder<MyIntegrationItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {

        MyIntegrationItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.my_integration_item, parent, false);

        BindingHolder<MyIntegrationItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(BindingHolder<MyIntegrationItemBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        MyIntegrationItemBinding binding = holder.getBinding();
        GoodsTo.ListsBean mode=mList.get(position);
        binding.name.setText(mode.getGoods_name());
        binding.integral.setText(mode.getGoods_integral()+"积分");
        disPlayImage(binding.goodsImage,mode.getGoods_image());

    }


}

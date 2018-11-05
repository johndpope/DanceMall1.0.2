package com.hzxmkuar.wumeihui.personal.integral.adapter;

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
import com.hzxmkuar.wumeihui.databinding.IntegralRecordItemBinding;

import hzxmkuar.com.applibrary.domain.integral.IntegralRecordTo;
import hzxmkuar.com.applibrary.main.DemandSearchTo;

/**
 * Created by Administrator on 2018/8/28.
 **/

public class IntegralRecordAdapter extends BaseAdapter<IntegralRecordTo.ListsBean, IntegralRecordItemBinding> {
    public IntegralRecordAdapter(Activity context) {
        super(context);
    }



    @NonNull
    @Override
    public BindingHolder<IntegralRecordItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {

        IntegralRecordItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.integral_record_item, parent, false);

        BindingHolder<IntegralRecordItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(BindingHolder<IntegralRecordItemBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        IntegralRecordItemBinding binding = holder.getBinding();
        IntegralRecordTo.ListsBean mode=mList.get(position);
        if (mode.getDateline()!=null&&mode.getDateline().length()>18) {
            binding.timeUp.setText(mode.getDateline().substring(0, 10));
            binding.timeDown.setText(mode.getDateline().substring(11,19));
        }
        binding.statue.setText(mode.getRfrom_text());
        binding.integral.setText(mode.getScore());
        binding.integral.setTextColor(mode.getRmode()==1? Color.parseColor("#FFB867"):Color.parseColor("#999999"));


    }


}

package com.hzxmkuar.wumeihui.personal.main.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.adapter.BaseAdapter;
import com.hzxmkuar.wumeihui.base.adapter.BindingHolder;
import com.hzxmkuar.wumeihui.base.util.PingYinUtil;
import com.hzxmkuar.wumeihui.base.util.city.ProvinceTo;
import com.hzxmkuar.wumeihui.databinding.CommunicationItemBinding;
import com.hzxmkuar.wumeihui.databinding.SelectCityItemBinding;

import java.util.ArrayList;
import java.util.List;

import hzxmkuar.com.applibrary.main.DemandSearchTo;

/**
 * Created by Administrator on 2018/8/28.
 **/

public class SelectProvinceAdapter extends BaseAdapter<ProvinceTo,SelectCityItemBinding> {

    public SelectProvinceAdapter(Activity context) {
        super(context);
    }



    @NonNull
    @Override
    public BindingHolder<SelectCityItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {

        SelectCityItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.select_city_item, parent, false);

        BindingHolder<SelectCityItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(BindingHolder<SelectCityItemBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        SelectCityItemBinding binding = holder.getBinding();
        ProvinceTo mode=mList.get(position);
        binding.name.setText(mode.getProvinceName());

    }

    public int getSectionForPosition(String positionString) {

        int position=0;
        for (ProvinceTo cityTo:mList){
            if (positionString.equals(PingYinUtil.getPingYin(cityTo.getProvinceName()).substring(0,1).toUpperCase())){
                break;
            }
            position++;
        }

        return position;
    }
}

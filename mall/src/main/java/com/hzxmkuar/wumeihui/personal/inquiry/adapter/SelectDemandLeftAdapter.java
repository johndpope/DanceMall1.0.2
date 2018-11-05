package com.hzxmkuar.wumeihui.personal.inquiry.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.adapter.BaseAdapter;
import com.hzxmkuar.wumeihui.base.adapter.BindingHolder;
import com.hzxmkuar.wumeihui.databinding.SelectLeftDemandItemBinding;

import java.util.ArrayList;
import java.util.List;

import hzxmkuar.com.applibrary.domain.inquery.InquiryListTo;
import rx.Observable;

/**
 * Created by Administrator on 2018/8/28.
 **/

public class SelectDemandLeftAdapter extends BaseAdapter<InquiryListTo, SelectLeftDemandItemBinding> {
    private List<TextView>typeNameList=new ArrayList<>();
    public SelectDemandLeftAdapter(Activity context) {
        super(context);
    }

    @NonNull
    @Override
    public BindingHolder<SelectLeftDemandItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {

        SelectLeftDemandItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.select_left_demand_item, parent, false);

        BindingHolder<SelectLeftDemandItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(BindingHolder<SelectLeftDemandItemBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        InquiryListTo mode = mList.get(position);
        SelectLeftDemandItemBinding binding = holder.getBinding();
        if (!typeNameList.contains(binding.name))
            typeNameList.add(binding.name);
        binding.name.setText(mode.getService_name());
        binding.name.setTextColor(mode.isSelect()?Color.parseColor("#3FB9FF"):Color.parseColor("#999999"));
    }

public void setNameColor(int position){
    Observable.from(mList).subscribe(demandMenuTo -> demandMenuTo.setSelect(false));
    if (position<mList.size()) {
        mList.get(position).setSelect(true);
        notifyDataSetChanged();
    }
}
}

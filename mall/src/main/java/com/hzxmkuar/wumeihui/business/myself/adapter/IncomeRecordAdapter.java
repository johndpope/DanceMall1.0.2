
package com.hzxmkuar.wumeihui.business.myself.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.adapter.BaseAdapter;
import com.hzxmkuar.wumeihui.base.adapter.BindingHolder;
import com.hzxmkuar.wumeihui.databinding.BondRecordItemBinding;

import hzxmkuar.com.applibrary.domain.merchant.BondRecordTo;
import hzxmkuar.com.applibrary.domain.merchant.IncomeRecordTo;

/**
 * Created by Administrator on 2018/8/28.
 **/

public class IncomeRecordAdapter extends BaseAdapter<IncomeRecordTo.ListsBean, BondRecordItemBinding> {
    public IncomeRecordAdapter(Activity context) {
        super(context);
    }



    @NonNull
    @Override
    public BindingHolder<BondRecordItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {

        BondRecordItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.bond_record_item, parent, false);
        BindingHolder<BondRecordItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(BindingHolder<BondRecordItemBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        BondRecordItemBinding binding = holder.getBinding();
        IncomeRecordTo.ListsBean mode=mList.get(position);
        binding.content.setText(mode.getDesc());
        binding.time.setText(mode.getDateline());
        binding.money.setText(mode.getAmount());
    }


}

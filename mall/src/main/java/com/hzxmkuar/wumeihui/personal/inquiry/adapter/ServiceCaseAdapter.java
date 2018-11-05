package com.hzxmkuar.wumeihui.personal.inquiry.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.adapter.BaseAdapter;
import com.hzxmkuar.wumeihui.base.adapter.BindingHolder;
import com.hzxmkuar.wumeihui.base.util.GlideRoundTransform;
import com.hzxmkuar.wumeihui.databinding.ServiceCaseItemBinding;

import hzxmkuar.com.applibrary.domain.merchant.MerchantCaseTo;

/**
 * Created by Administrator on 2018/8/28.
 **/

public class ServiceCaseAdapter extends BaseAdapter<MerchantCaseTo.ListsBean, ServiceCaseItemBinding> {
    public ServiceCaseAdapter(Activity context) {
        super(context);
    }


    @NonNull
    @Override
    public BindingHolder<ServiceCaseItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {

        ServiceCaseItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.service_case_item, parent, false);

        BindingHolder<ServiceCaseItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(BindingHolder<ServiceCaseItemBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        ServiceCaseItemBinding binding = holder.getBinding();
        MerchantCaseTo.ListsBean mode = mList.get(position);
        Glide.with(mContext).load(mode.getCase_img()).transform(new GlideRoundTransform(mContext, 10)).into(binding.caseImage);
        binding.caseTitle.setText(mode.getCase_desc());
    }



}

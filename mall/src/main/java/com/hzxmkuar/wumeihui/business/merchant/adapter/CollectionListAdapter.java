
package com.hzxmkuar.wumeihui.business.merchant.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.adapter.BaseAdapter;
import com.hzxmkuar.wumeihui.base.adapter.BindingHolder;
import com.hzxmkuar.wumeihui.databinding.CollectionListItemBinding;

import hzxmkuar.com.applibrary.domain.main.MainMerchantTo;

/**
 * Created by Administrator on 2018/8/28.
 **/

public class CollectionListAdapter extends BaseAdapter<MainMerchantTo.ListsBean, CollectionListItemBinding> {
    public CollectionListAdapter(Activity context) {
        super(context);
    }


    @NonNull
    @Override
    public BindingHolder<CollectionListItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {

        CollectionListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.collection_list_item, parent, false);

        BindingHolder<CollectionListItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(BindingHolder<CollectionListItemBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        MainMerchantTo.ListsBean mode=mList.get(position);

        CollectionListItemBinding binding = holder.getBinding();
        disPlayImage(binding.image,mode.getShop_logo());
        binding.name.setText(mode.getShop_name());
        binding.score.setText(mode.getRatings()+"分");
        binding.shopType.setText(mode.getStype_txt());
        binding.service.setText(mode.getService_exam());
        binding.orderNum.setText("成交"+mode.getOrder_num()+"比");

    }


}

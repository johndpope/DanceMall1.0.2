
package com.hzxmkuar.wumeihui.message.adapter;

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
import com.hzxmkuar.wumeihui.databinding.SystemMessageItemBinding;

import hzxmkuar.com.applibrary.domain.message.SystemMessageTo;
import hzxmkuar.com.applibrary.main.DemandSearchTo;

/**
 * Created by Administrator on 2018/8/28.
 **/

public class SystemMessageAdapter extends BaseAdapter<SystemMessageTo.ListsBean, SystemMessageItemBinding> {
    public SystemMessageAdapter(Activity context) {
        super(context);
    }


    @NonNull
    @Override
    public BindingHolder<SystemMessageItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {

        SystemMessageItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.system_message_item, parent, false);

        BindingHolder<SystemMessageItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(BindingHolder<SystemMessageItemBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        SystemMessageItemBinding binding = holder.getBinding();
        SystemMessageTo.ListsBean mode=mList.get(position);

        binding.messageContent.setText(mode.getMsg_desc());
        binding.time.setText(mode.getDateline());
        binding.title.setText(mode.getMsg_title());
    }


}

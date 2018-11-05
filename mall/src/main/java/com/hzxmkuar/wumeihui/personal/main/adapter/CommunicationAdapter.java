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
import com.hzxmkuar.wumeihui.databinding.CommunicationItemBinding;

import java.util.ArrayList;
import java.util.List;

import hzxmkuar.com.applibrary.main.DemandSearchTo;

/**
 * Created by Administrator on 2018/8/28.
 **/

public class CommunicationAdapter extends BaseAdapter<DemandSearchTo, CommunicationItemBinding> {

    public CommunicationAdapter(Activity context) {
        super(context);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @NonNull
    @Override
    public BindingHolder<CommunicationItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {

        CommunicationItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.communication_item, parent, false);

        BindingHolder<CommunicationItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(BindingHolder<CommunicationItemBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        CommunicationItemBinding binding = holder.getBinding();
                    disPlayRoundImage(binding.headImage);
        List<Integer> imageList=new ArrayList<>();
        imageList.add(R.drawable.default_head_image);
        imageList.add(R.drawable.default_head_image);
        imageList.add(R.drawable.default_head_image);
        imageList.add(R.drawable.default_head_image);
        setImageLayout(imageList,binding.imageLayout);
    }


}

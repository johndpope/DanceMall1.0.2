package com.hzxmkuar.wumeihui.personal.inquiry.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.adapter.BaseAdapter;
import com.hzxmkuar.wumeihui.base.adapter.BindingHolder;
import com.hzxmkuar.wumeihui.databinding.ServiceCommentItemBinding;

import java.util.ArrayList;
import java.util.List;

import hzxmkuar.com.applibrary.domain.merchant.MerchantCommentTo;
import rx.Observable;

/**
 * Created by Administrator on 2018/8/28.
 **/

public class ServiceCommentAdapter extends BaseAdapter<MerchantCommentTo.ListsBean, ServiceCommentItemBinding> {
    public ServiceCommentAdapter(Activity context) {
        super(context);
    }


    @NonNull
    @Override
    public BindingHolder<ServiceCommentItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {

        ServiceCommentItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.service_comment_item, parent, false);

        BindingHolder<ServiceCommentItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(BindingHolder<ServiceCommentItemBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        ServiceCommentItemBinding binding = holder.getBinding();
        MerchantCommentTo.ListsBean mode=mList.get(position);
        disPlayImage(binding.headImage, mode.getUser_info().getHeadimgurl());
        binding.userName.setText(mode.getUser_info().getUsername());
        binding.userTag.setText(mode.getUser_info().getUser_tag());
        binding.time.setText(mode.getDateline());
        binding.address.setText(mode.getAddress());
        binding.service.setText(mode.getServices());
        binding.content.setText(mode.getContent());
        List<String> imageList = new ArrayList<>();
        Observable.from(mode.getPic_list()).subscribe(picListBean -> imageList.add(picListBean.getPic()));
        setImageLayout(binding.imageLayout,imageList,158);
    }


}

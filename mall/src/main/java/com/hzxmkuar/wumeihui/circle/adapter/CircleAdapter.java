package com.hzxmkuar.wumeihui.circle.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.adapter.BaseAdapter;
import com.hzxmkuar.wumeihui.base.adapter.BindingHolder;
import com.hzxmkuar.wumeihui.base.util.GlideCircleTransform;
import com.hzxmkuar.wumeihui.databinding.CommunicationItemBinding;

import java.util.ArrayList;
import java.util.List;

import hzxmkuar.com.applibrary.domain.circle.CircleTo;
import hzxmkuar.com.applibrary.main.DemandSearchTo;
import rx.Observable;

/**
 * Created by Administrator on 2018/8/28.
 **/

public class CircleAdapter extends BaseAdapter<CircleTo.ListsBean, CommunicationItemBinding> {

    private String imagePath;
    public CircleAdapter(Activity context) {
        super(context);
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

        CircleTo.ListsBean mode = mList.get(position);
        Glide.with(mContext).load(mode.getUser_info().getHeadimgurl()).transform(new GlideCircleTransform(mContext)).placeholder(R.drawable.user_default_icon).into(binding.headImage);
        binding.content.setText(mode.getContent());
        binding.nickName.setText(mode.getUser_info().getUsername());
        binding.userTag.setText(mode.getUser_info().getUser_tag());
        binding.address.setText(mode.getPosition());
        binding.topic.setText(mode.getTopic());
        binding.isTop.setVisibility(mode.getIs_top()==1? View.VISIBLE:View.GONE);
        binding.likeCount.setText(mode.getLikes()+"");
        binding.time.setText(mode.getDateline());
//       binding.praiseIcon.setBackgroundResource(mode.is);
        binding.viewCount.setText(mode.getViews()+"");
        binding.commnetCount.setText(mode.getComments()+"");
        imagePath="";
        if (mode.getPic_list()!=null&&mode.getPic_list().size()>0){
            Observable.from(mode.getPic_list()).subscribe(picListBean -> imagePath=imagePath+picListBean.getPic()+",");
            setImageLayout(imagePath.substring(0,imagePath.length()-1),binding.imageLayout);
        }

    }


}

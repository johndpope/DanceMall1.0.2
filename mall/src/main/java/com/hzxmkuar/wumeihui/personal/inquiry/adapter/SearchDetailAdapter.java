package com.hzxmkuar.wumeihui.personal.inquiry.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.ActivityManager;
import com.hzxmkuar.wumeihui.base.Event;
import com.hzxmkuar.wumeihui.base.adapter.BaseAdapter;
import com.hzxmkuar.wumeihui.base.adapter.BindingHolder;
import com.hzxmkuar.wumeihui.databinding.SearchDetailItemBinding;
import com.hzxmkuar.wumeihui.personal.inquiry.SelectDemandActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import hzxmkuar.com.applibrary.main.DemandSearchTo;

/**
 * Created by Administrator on 2018/8/28.
 **/

public class SearchDetailAdapter extends BaseAdapter<DemandSearchTo.ListsBeanX, SearchDetailItemBinding> {
    private List<TextView>typeNameList=new ArrayList<>();
    public SearchDetailAdapter(Activity context) {
        super(context);
    }

    @NonNull
    @Override
    public BindingHolder<SearchDetailItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {

        SearchDetailItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.search_detail_item, parent, false);

        BindingHolder<SearchDetailItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(BindingHolder<SearchDetailItemBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        DemandSearchTo.ListsBeanX mode = mList.get(position);
        SearchDetailItemBinding binding = holder.getBinding();
      binding.typeName.setText(mode.getCate_name());
        setChildLayout(binding.typeLayout,mode.getLists());
    }

    private void setChildLayout(GridLayout childLayout, List<DemandSearchTo.ListsBeanX.ListsBean>searchList){
        childLayout.removeAllViews();
        for (int i=0;i<searchList.size();i++){
            View mView=View.inflate(mContext,R.layout.search_detail_child_item,null);
            DemandSearchTo.ListsBeanX.ListsBean searchChildTo=searchList.get(i);
            ((TextView)mView.findViewById(R.id.type_name)).setText(searchChildTo.getService_name());
            disPlayImage(mView.findViewById(R.id.head_image),searchChildTo.getService_img(),R.drawable.service_default_icon);
            mView.findViewById(R.id.inquiry).setOnClickListener(view -> {
                ActivityManager.searchDemandActivity.finish();
                ActivityManager.searchDetailActivity.finish();
                EventBus.getDefault().post(new Event<>("SelectDemandTo",searchChildTo));
            });
            childLayout.addView(mView);
        }
    }

}

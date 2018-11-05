package com.hzxmkuar.wumeihui.personal.inquiry.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.adapter.BaseAdapter;
import com.hzxmkuar.wumeihui.base.adapter.BindingHolder;
import com.hzxmkuar.wumeihui.databinding.SelectRightMainDemandItemBinding;

import java.util.List;

import hzxmkuar.com.applibrary.domain.inquery.InquiryListTo;

/**
 * Created by Administrator on 2018/8/28.
 **/

public class SelectServiceRightAdapter extends BaseAdapter<InquiryListTo, SelectRightMainDemandItemBinding> {

   private String serviceIds;
    private boolean isService;
    public SelectServiceRightAdapter(Activity context , String serviceIds) {
        super(context);
        this.serviceIds=serviceIds;
    }
    public SelectServiceRightAdapter(Activity context , String serviceIds, boolean isService) {
        super(context);
        this.serviceIds=serviceIds;
        this.isService=isService;
    }


    @NonNull
    @Override
    public BindingHolder<SelectRightMainDemandItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {

        SelectRightMainDemandItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.select_right_main_demand_item, parent, false);

        BindingHolder<SelectRightMainDemandItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(BindingHolder<SelectRightMainDemandItemBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        InquiryListTo mode = mList.get(position);
        SelectRightMainDemandItemBinding binding = holder.getBinding();

        setMainLayout(binding.mainLayout,mode.getList2());
    }

    private void setMainLayout(GridLayout typeLayout, List<InquiryListTo> demandChildList) {
        typeLayout.removeAllViews();
        for (int i = 0; i < demandChildList.size(); i++) {
            View mView=View.inflate(mContext,R.layout.select_right_demand_item,null);
            InquiryListTo inquiryTo=   demandChildList.get(i);
            ((TextView)mView.findViewById(R.id.type_name)).setText(inquiryTo.getService_name());
            setChildLayout(mView.findViewById(R.id.type_layout), inquiryTo.getList3());
            typeLayout.addView(mView);
        }
    }

    private void setChildLayout(GridLayout typeLayout, List<InquiryListTo> demandChildList) {
        typeLayout.removeAllViews();
        for (int i = 0; i < demandChildList.size(); i++) {
            View mView = View.inflate(mContext, R.layout.select_right_demand_child_item, null);
            InquiryListTo childTo = demandChildList.get(i);
            ((TextView) mView.findViewById(R.id.type_name)).setText(childTo.getService_name());
            disPlayImage(mView.findViewById(R.id.type_image), childTo.getService_img(),R.drawable.service_default_icon);
            mView.findViewById(R.id.select_icon).setVisibility(childTo.isSelect()?View.VISIBLE:View.GONE);
            typeLayout.addView(mView);
            if (serviceIds!=null&&serviceIds.contains(childTo.getId()+"")) {
                childTo.setSelect(true);
                mView.findViewById(R.id.select_icon).setVisibility(View.VISIBLE);
            }
           mView.setOnClickListener(view -> {
               if (serviceIds!=null&&serviceIds.contains(childTo.getId()+"")&&!isService)
                   return;
               childTo.setSelect(!childTo.isSelect());
               mView.findViewById(R.id.select_icon).setVisibility(childTo.isSelect()?View.VISIBLE:View.GONE);
              if (listener!=null)
                  listener.setSelectInquiry(childTo);
           });

        }
    }

     public interface SelectInquiryListener {
        void setSelectInquiry(InquiryListTo inquiryListTo);
    }

    private SelectInquiryListener listener;

    public void setInquiryListener(SelectInquiryListener listener){
        this.listener=listener;
    }


}

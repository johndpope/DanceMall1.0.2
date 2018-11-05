package com.hzxmkuar.wumeihui.personal.inquiry.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.adapter.BaseAdapter;
import com.hzxmkuar.wumeihui.base.adapter.BindingHolder;
import com.hzxmkuar.wumeihui.databinding.ServiceDetailItemBinding;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import hzxmkuar.com.applibrary.domain.inquery.ServiceDetailTo;

/**
 * Created by Administrator on 2018/8/28.
 **/

public class ServiceDetailAdapter extends BaseAdapter<ServiceDetailTo.ListsBean, ServiceDetailItemBinding> {
    public ServiceDetailAdapter(Activity context) {
        super(context);
    }


    @NonNull
    @Override
    public BindingHolder<ServiceDetailItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {

        ServiceDetailItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.service_detail_item, parent, false);

        BindingHolder<ServiceDetailItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(BindingHolder<ServiceDetailItemBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        ServiceDetailTo.ListsBean mode = mList.get(position);
        ServiceDetailItemBinding binding = holder.getBinding();
         disPlayImage(binding.serviceImage,mode.getService_img());
        binding.serviceName.setText(mode.getService_name());
        binding.serviceLayout.removeAllViews();
        for (int i=0;i<mode.getGet_properties_list().size();i++){
            View typeView;
            ServiceDetailTo.ListsBean.GetPropertiesListBean serviceTo = mode.getGet_properties_list().get(i);
            if (serviceTo.getPtype() == 3 || serviceTo.getPtype() == 4) {
                typeView = View.inflate(mContext, R.layout.inquiry_des_type2, null);
                TagFlowLayout flowLayout = typeView.findViewById(R.id.flow_layout);


                flowLayout.setAdapter(new TagAdapter<ServiceDetailTo.ListsBean.GetPropertiesListBean.ConflistBean>(serviceTo.getConflist()) {
                    @Override
                    public View getView(FlowLayout parent, int position, ServiceDetailTo.ListsBean.GetPropertiesListBean.ConflistBean data) {
                        View mView = View.inflate(mContext, R.layout.inquiry_des_flow_item, null);
                        ((TextView) mView.findViewById(R.id.service_name)).setText(data.getTitle());
                        mView.findViewById(R.id.select_icon).setBackgroundResource("true".equals(data.getContent())?R.drawable.service_select:R.drawable.service_un_select);
                        return mView;
                    }
                });


            } else if (serviceTo.getPtype() == 5) {
                typeView = View.inflate(mContext, R.layout.inquiry_des_type3, null);

                for (int k=0;k<serviceTo.getConflist().size();k++){
                    View type3Child=View.inflate(mContext,R.layout.inquiry_des_type3_child,null);
                    ((TextView) type3Child.findViewById(R.id.image_title)).setText(serviceTo.getConflist().get(k).getTitle());
                    ImageView stageImage=type3Child.findViewById(R.id.stage_image);
                    disPlayImage(stageImage,serviceTo.getConflist().get(k).getContent());
                    ((GridLayout)typeView.findViewById(R.id.image_layout)).addView(type3Child);
                }






            } else if (serviceTo.getPtype() == 6) {
                typeView = View.inflate(mContext, R.layout.inquiry_des_type4, null);
                EditText inquiryContent=typeView.findViewById(R.id.add_inquiry_content);
                inquiryContent.setFocusable(false);
                inquiryContent.setText(serviceTo.getConflist().get(0).getContent());
            } else {
                typeView = View.inflate(mContext, R.layout.inquiry_des_type1, null);
                EditText inquiryContent=typeView.findViewById(R.id.inquiry_content);
                inquiryContent.setFocusable(false);
                inquiryContent.setText(serviceTo.getConflist().get(0).getContent());

            }
            ((TextView) typeView.findViewById(R.id.title_name)).setText(serviceTo.getPtitle());

            binding.serviceLayout.addView(typeView);
        }



}
}

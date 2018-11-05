
package com.hzxmkuar.wumeihui.personal.inquiry.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.adapter.BaseAdapter;
import com.hzxmkuar.wumeihui.base.adapter.BindingHolder;
import com.hzxmkuar.wumeihui.databinding.MyInquiryItemBinding;

import hzxmkuar.com.applibrary.domain.inquery.MyInquiryTo;
import rx.Observable;

/**
 * Created by Administrator on 2018/8/28.
 **/

public class MyInquiryAdapter extends BaseAdapter<MyInquiryTo.ListsBean, MyInquiryItemBinding> {
    private String imagePath="";
    public MyInquiryAdapter(Activity context) {
        super(context);
    }



    @NonNull
    @Override
    public BindingHolder<MyInquiryItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {

        MyInquiryItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.my_inquiry_item, parent, false);
        BindingHolder<MyInquiryItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);

        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(BindingHolder<MyInquiryItemBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        MyInquiryItemBinding binding = holder.getBinding();
        MyInquiryTo.ListsBean mode=mList.get(position);
        binding.inquirySn.setText(mode.getInquiry_sn());
        binding.statue.setText(mode.getStatus_arr().getStatus_txt());
        binding.serviceNum.setText("共"+ mode.getService_num() +"项服务"+((mode.getStatus_arr().getStatus()==2||mode.getStatus_arr().getStatus()==3)?("   已有 "+mode.getOffer_num()+" 家竞标" ):""));
        if (mode.getService_list()!=null) {
            imagePath="";
            Observable.from(mode.getService_list()).subscribe(serviceListBean -> imagePath=imagePath+serviceListBean.getService_img()+",");
            setImageLayout(imagePath.substring(0,imagePath.length()-1),binding.imageLayout);
        }
        binding.confirm.setVisibility(mode.getStatus_arr().getStatus()==1? View.GONE:View.VISIBLE);
        binding.cancel.setVisibility((mode.getStatus_arr().getStatus()==1||mode.getStatus_arr().getStatus()==2)?View.VISIBLE:View.GONE);
        binding.confirm.setText(mode.getStatus_arr().getStatus()==2?"查看报价":"再次询价");
        binding.countTime.start(mode.getValid_time()*1000);

        binding.countTimeLayout.setVisibility((mode.getStatus_arr().getStatus()==1||mode.getStatus_arr().getStatus()==2)?View.VISIBLE:View.GONE);
        binding.cancel.setOnClickListener(view -> {
            if (listener!=null)
                listener.cancelClick(mode);
        });
        if (mode.getStatus_arr().getStatus()==3||mode.getStatus_arr().getStatus()==5){
            binding.confirm.setOnClickListener(view -> {
                if (listener!=null)
                    listener.confirmClick(mode);
            });
        }
    }

    public interface CancelClickListener{
        void cancelClick(MyInquiryTo.ListsBean mode);

        void confirmClick(MyInquiryTo.ListsBean mode);
    }

    public CancelClickListener listener;

    public void setCancelListener(CancelClickListener listener){
        this.listener=listener;
    }

}
